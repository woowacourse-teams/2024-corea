package corea.member.domain;

import corea.domain.Member;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

import static lombok.AccessLevel.PROTECTED;

@Component
@NoArgsConstructor(access = PROTECTED)
public class Matching {

    public Map<Long, List<Long>> matchGroup(final List<Member> members, final int matchingSize) {
        final Map<Long, List<Long>> matchedGroup = new HashMap<>();

        final List<Long> memberIds = new ArrayList<>(members.stream().map(Member::getId).toList());
        Collections.shuffle(memberIds);

        final List<List<Long>> groupedMemberIds = IntStream.range(0, (members.size() + matchingSize - 1) / matchingSize)
                .mapToObj(i -> memberIds.subList(i * matchingSize, Math.min(i * matchingSize + matchingSize, memberIds.size())))
                .toList();

        long groupId = 1L;
        for (List<Long> l : groupedMemberIds) {
            matchedGroup.put(groupId++, l);
        }

        return matchedGroup;
    }
}
