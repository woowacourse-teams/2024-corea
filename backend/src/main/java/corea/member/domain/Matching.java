package corea.member.domain;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

import static lombok.AccessLevel.PROTECTED;

@Component
@NoArgsConstructor(access = PROTECTED)
public class Matching {

    public Map<Long, List<Long>> matchGroup(final List<Long> memberIds, final int matchingSize) {
        final Map<Long, List<Long>> matchedGroup = new HashMap<>();

        Collections.shuffle(memberIds);

        final List<List<Long>> groupedMemberIds = IntStream.range(0, (memberIds.size() + matchingSize - 1) / matchingSize)
                .mapToObj(i -> memberIds.subList(i * matchingSize, Math.min(i * matchingSize + matchingSize, memberIds.size())))
                .toList();

        long groupId = 1L;
        for (final List<Long> l : groupedMemberIds) {
            matchedGroup.put(groupId++, l);
        }

        return matchedGroup;
    }
}
