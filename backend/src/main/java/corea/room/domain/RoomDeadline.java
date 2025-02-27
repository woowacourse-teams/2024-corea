package corea.room.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomDeadline {

    @Column(name = "recruitment_deadline")
    private LocalDateTime recruitmentDeadline;

    @Column(name = "review_deadline")
    private LocalDateTime reviewDeadline;

    public RoomDeadline(LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline) {
        validateDeadline(recruitmentDeadline, reviewDeadline);
        this.recruitmentDeadline = recruitmentDeadline;
        this.reviewDeadline = reviewDeadline;
    }

    private void validateDeadline(LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (isInvalidRecruitmentDeadline(recruitmentDeadline, currentDateTime)) {
            throw new CoreaException(ExceptionType.INVALID_RECRUITMENT_DEADLINE);
        }

        if (isInvalidReviewDeadline(reviewDeadline, recruitmentDeadline)) {
            throw new CoreaException(ExceptionType.INVALID_REVIEW_DEADLINE);
        }
    }

    private boolean isInvalidRecruitmentDeadline(LocalDateTime recruitmentDeadline, LocalDateTime currentDateTime) {
        return !recruitmentDeadline.isAfter(currentDateTime);
    }

    private boolean isInvalidReviewDeadline(LocalDateTime reviewDeadline, LocalDateTime recruitmentDeadline) {
        return !reviewDeadline.isAfter(recruitmentDeadline);
    }
}
