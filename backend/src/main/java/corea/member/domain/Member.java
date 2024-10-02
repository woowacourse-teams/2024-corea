package corea.member.domain;

import corea.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String username;

    @Column(length = 32768)
    private String thumbnailUrl;

    private String name;

    private String email;

    private boolean isEmailAccepted;

    private String profileLink;

    private String githubUserId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Profile profile;

    public Member(String username, String thumbnailUrl, String name, String email, boolean isEmailAccepted, String githubUserId) {
        this(null, username, thumbnailUrl, name, email, isEmailAccepted, "", githubUserId, new Profile());
    }

    public Member(String username, String thumbnailUrl, String name, String email, boolean isEmailAccepted, String githubUserId, Profile profile) {
        this(null, username, thumbnailUrl, name, email, isEmailAccepted, "", githubUserId, profile);
    }

    public void increaseReviewCount(MemberRole memberRole) {
        profile.increaseReviewCount(memberRole);
    }

    public boolean isMatchingId(long memberId) {
        return this.id == memberId;
    }

    public boolean isNotMatchingId(long memberId) {
        return !isMatchingId(memberId);
    }

    public void updateAverageRating(int evaluatePoint) {
        profile.updateAverageRating(evaluatePoint);
    }
}
