package corea.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String userName;

    private String thumbnailUrl;

    private String name;

    private String email;

    private boolean isEmailAccepted;

    private float attitude;

    private String profileLink;

    public Member(String userName, String thumbnailUrl, String name, String email, boolean isEmailAccepted, float attitude) {
        this(null, userName, thumbnailUrl, name, email, isEmailAccepted, attitude, null);
    }
}
