package corea.participation.domain;

import corea.global.BaseTimeEntity;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.room.domain.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Participation extends BaseTimeEntity {

    private static final Logger log = LogManager.getLogger(Participation.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;

    @Enumerated(value = EnumType.STRING)
    private ParticipationStatus status;

    private int matchingSize;

    public Participation(Room room, Member member, MemberRole role, int matchingSize) {
        this(null, room, member, role, ParticipationStatus.PARTICIPATED, matchingSize);
        debug(room.getId(), member.getId());
    }

    public Participation(Room room, Member member) {
        this(null, room, member, MemberRole.REVIEWER, ParticipationStatus.MANAGER, room.getMatchingSize());
        debug(room.getId(), member.getId());
    }

    public boolean isNotMatchingMemberId(long memberId) {
        return this.member.getId() != memberId;
    }

    public void cancel() {
        room.cancelParticipation();
    }

    public void invalidate() {
        status = ParticipationStatus.PULL_REQUEST_NOT_SUBMITTED;
    }

    public void participate() {
        room.participate();
    }

    public long getRoomsId() {
        return room.getId();
    }

    public long getMembersId() {
        return member.getId();
    }

    public String getMemberGithubId() {
        return member.getGithubUserId();
    }

    public boolean isReviewer() {
        return memberRole.isReviewer();
    }

    private static void debug(long roomId, long memberId) {
        log.debug("참가자 생성[방 ID={}, 멤버 ID={}", roomId, memberId);
    }
}
