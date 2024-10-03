package corea.matching.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DynamicSizeMatchingStrategy {

    private final MemberRepository memberRepository;

    public List<Pair> matchPairs(List<Participation> participations) {
        //memberCache: 일일이 memberRepository.findById() 하는 상황을 피하기 위한, member.id -> member 로의 Map 자료구조
        Map<Long, Member> memberCache = participations.stream()
                .map(participation -> memberRepository.findById(participation.getMemberId())
                        .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND)))
                .collect(Collectors.toMap(Member::getId, Function.identity()));

        List<Pair> pairs = new ArrayList<>();
        generatePairs(participations, pairs, memberCache);

        return pairs;
    }

    private void generatePairs(List<Participation> participations, List<Pair> pairs, Map<Long, Member> memberCache) {
        //reviewerCount: 각 멤버가 리뷰어로 배정될 수 있는 횟수, 각 participation 의 matchingSize 부터 0까지 리뷰어로 매칭될 때 마다 감소
        Map<Participation, Integer> reviewerCount = participations.stream()
                .collect(Collectors.toMap(Function.identity(), Participation::getMatchingSize));
        //revieweeCount: 각 멤버가 리뷰이로 배정될 수 있는 횟수, 각 participation 의 matchingSize 부터 0까지 리뷰이로 매칭될 때 마다 감소
        //MemberRole == REVIEWER 인 참여자는 리뷰이로 매칭되면 안되므로 이에 대한 처리를 getRevieweeMatchingCount() 가 진행
        Map<Participation, Integer> revieweeCount = participations.stream()
                .collect(Collectors.toMap(Function.identity(), this::getRevieweeMatchingCount));

        //needToMatch: 리뷰어 혹은 리뷰이로 매칭될 수 있는 가능성을 가지는 멤버들의 Set 자료구조
        Set<Participation> needToMatch = new HashSet<>(participations);

        while (needToMatch.size() > 1) {
            //매칭을 할 수 있는 인원이 1보다 많으면(2명 이상이면) 리뷰어-리뷰이 짝을 지을 수 있다고 판단해 Pair 생성 진행
            pairs.addAll(generateRandomPairs(reviewerCount, revieweeCount, needToMatch, memberCache));
            //이번 매칭으로 needToMatch 에서 reviewerCount, revieweeCount 가 0 이 된 참여자는 더 이상 매칭에 참여하지 못하도록 제거
            removeMatchedParticipation(reviewerCount, revieweeCount, needToMatch);
        }
    }

    private int getRevieweeMatchingCount(Participation participation) {
        if (participation.getMemberRole().isReviewer()) {
            return 0;
        }
        return participation.getMatchingSize();
    }

    private List<Pair> generateRandomPairs(Map<Participation, Integer> reviewerCount, Map<Participation, Integer> revieweeCount, Set<Participation> needToMatch, Map<Long, Member> memberCache) {
        //랜덤 매칭을 위해 needToMatch 를 두 배열로 복사해 셔플
        List<Participation> shuffledReviewer = new ArrayList<>(needToMatch);
        List<Participation> shuffledReviewee = new ArrayList<>(needToMatch);

        //같은 인덱스에 같은 참여자가 존재하지 않을 때까지 셔플(리뷰어-리뷰이가 동일인이 배정되는 상황을 막기 위함)
        Collections.shuffle(shuffledReviewer);
        do {
            Collections.shuffle(shuffledReviewee);
        } while (hasSameValue(shuffledReviewer, shuffledReviewee));

        // 참여 가능한 모든 사람을 한 번 씩 매칭(모두가 각각 리뷰어로 한 번, 리뷰이로 한 번 등장하도록)
        return IntStream.range(0, needToMatch.size())
                //리뷰이인데 MemberRole == REVIEWER 인 경우 제외
                .filter(i -> !shuffledReviewee.get(i).getMemberRole().isReviewer())
                .mapToObj(i -> makePair(shuffledReviewer.get(i), shuffledReviewee.get(i), reviewerCount, revieweeCount, memberCache))
                .toList();
    }

    private boolean hasSameValue(List<Participation> shuffledFirst, List<Participation> shuffledSecond) {
        //두 리스트를 순회하면서 같은 인덱스에 같은 사람이 있으면 true, 아니면 false 반환
        return IntStream.range(0, shuffledFirst.size())
                .anyMatch(i -> shuffledFirst.get(i).equals(shuffledSecond.get(i)));
    }

    private Pair makePair(Participation reviewer, Participation reviewee, Map<Participation, Integer> reviewerCount, Map<Participation, Integer> revieweeCount, Map<Long, Member> memberCache) {
        //생성된 리뷰어-리뷰이는 위에서 정의했던 reviewerCount, revieweeCount 를 1 씩 감소시켜 줘야함
        reviewerCount.put(reviewer, reviewerCount.get(reviewer) - 1);
        revieweeCount.put(reviewee, revieweeCount.get(reviewee) - 1);
        //리뷰어-리뷰이를 Pair 로 만들어 반환, 여기서 memberRepository.findById() 를 하게 되면 DB 통신이 너무 많아져 memberCache 에서 찾아옴
        return new Pair(memberCache.get(reviewer.getMemberId()), memberCache.get(reviewee.getMemberId()));
    }

    private void removeMatchedParticipation(Map<Participation, Integer> reviewerCount, Map<Participation, Integer> revieeweeCount, Set<Participation> needToMatch) {
        //reviewerCount 가 0 이 된 참여자는 더 이상 리뷰어로 참여할 수 없기 때문에 needToMatch 에서 제외
        reviewerCount.entrySet().stream()
                .filter(entry -> entry.getValue() == 0)
                .forEach(entry -> needToMatch.remove(entry.getKey()));
        //revieweeCount 가 0 이 된 참여자는 더 이상 리뷰이로 참여할 수 없기 때문에 needToMatch 에서 제외, 단 MemberRole == REVIEWER 인 사람은 논외
        revieeweeCount.entrySet().stream()
                .filter(entry -> entry.getValue() == 0 && !entry.getKey().getMemberRole().isReviewer())
                .forEach(entry -> needToMatch.remove(entry.getKey()));
    }
}
