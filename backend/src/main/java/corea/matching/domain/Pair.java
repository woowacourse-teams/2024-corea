package corea.matching.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Pair {

    private final Long fromMemberId;
    private final Long toMemberId;
}
