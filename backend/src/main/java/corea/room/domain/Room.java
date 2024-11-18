package corea.room.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.global.BaseTimeEntity;
import corea.member.domain.Member;
import corea.util.StringToListConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String content;

    private int matchingSize;

    @Column(length = 32768)
    private String repositoryLink;

    @Column(length = 32768)
    private String thumbnailLink;

    @Convert(converter = StringToListConverter.class)
    private List<String> keyword;

    private int reviewerCount;

    private int bothCount;

    private int limitedParticipantsSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member manager;

    @Embedded
    private RoomDeadline roomDeadline;

    @Enumerated(value = EnumType.STRING)
    private RoomClassification classification;

    /**
     * RoomStatus가 변경될 수 있는 경우 (OPENED -> CLOSED)
     * 1. 방장이 모집 마감을 한 경우
     * 2. 제한 인원이 다 찼을 경우 (방에 참여할 때 같이 검증)
     * 3. 모집 기간이 끝난 경우
     * <p>
     * 1, 2의 경우 때문에 방 상태를 가지는 필드를 가져야 될듯.
     **/
    @Enumerated(value = EnumType.STRING)
    private RoomStatus status;

    public Room(String title, String content, int matchingSize, String repositoryLink, String thumbnailLink, List<String> keyword, int reviewerCount, int bothCount, int limitedParticipantsSize, Member manager, LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline, RoomClassification classification, RoomStatus status) {
        this(null, title, content, matchingSize, repositoryLink, thumbnailLink, keyword, reviewerCount, bothCount, limitedParticipantsSize, manager, new RoomDeadline(recruitmentDeadline, reviewDeadline), classification, status);
    }

    public void increaseReviewerCount() {
        validateOpened();
        this.reviewerCount++;
    }

    public void increaseBothCount() {
        validateOpened();
        if (bothCount >= limitedParticipantsSize) {
            throw new CoreaException(ExceptionType.ROOM_PARTICIPANT_EXCEED);
        }
        this.bothCount++;
    }

    public void decreaseReviewerCount() {
        validateOpened();
        reviewerCount = Math.max(0, reviewerCount - 1);
    }

    public void decreaseBothCount() {
        validateOpened();
        bothCount = Math.max(0, bothCount - 1);
    }

    private void validateOpened() {
        if (status.isNotOpened()) {
            throw new CoreaException(ExceptionType.ROOM_STATUS_INVALID);
        }
    }

    public void updateStatusToProgress() {
        validateOpened();
        status = RoomStatus.PROGRESS;
    }

    public void updateStatusToClose() {
        status = RoomStatus.CLOSE;
    }

    public void updateStatusToFail() {
        status = RoomStatus.FAIL;
    }

    public boolean isNotOpened() {
        return status.isNotOpened();
    }

    public boolean isClosed() {
        return status.isClosed();
    }

    public boolean isNotClosed() {
        return !isClosed();
    }

    public boolean isStatus(RoomStatus status) {
        return this.status == status;
    }

    public boolean isNotMatchingManager(long memberId) {
        return manager.isNotMatchingId(memberId);
    }

    public boolean isManagedBy(long managerId) {
        return manager.isMatchingId(managerId);
    }

    public long getManagerId() {
        return manager.getId();
    }

    public String getRoomStatus() {
        return status.getStatus();
    }

    public String getManagerName() {
        return manager.getUsername();
    }

    public LocalDateTime getRecruitmentDeadline() {
        return roomDeadline.getRecruitmentDeadline();
    }

    public LocalDateTime getReviewDeadline() {
        return roomDeadline.getReviewDeadline();
    }
}
