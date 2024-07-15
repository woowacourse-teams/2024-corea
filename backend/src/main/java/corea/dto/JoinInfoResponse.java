package corea.dto;

import corea.domain.JoinInfo;

public record JoinInfoResponse(
        long id,
        long memberId,
        long roomId
) {

    public static JoinInfoResponse from(final JoinInfo joinInfo) {
        return new JoinInfoResponse(
                joinInfo.getId(),
                joinInfo.getMemberId(),
                joinInfo.getRoomId()
        );
    }
}
