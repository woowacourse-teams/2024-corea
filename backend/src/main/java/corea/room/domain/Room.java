package corea.room.domain;

import corea.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room {

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

    private String keyword;

    private int currentParticipantsSize;

    private int limitedParticipantsSize;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member manager;

    private LocalDateTime recruitmentDeadline;

    private LocalDateTime reviewDeadline;

    public Room(String title, String content, int matchingSize, String repositoryLink, String thumbnailLink, String keyword, int currentParticipantsSize, int limitedParticipantsSize, Member manager, LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline) {
        this(null,title,content,matchingSize,repositoryLink,thumbnailLink,keyword,currentParticipantsSize,limitedParticipantsSize,manager,recruitmentDeadline,reviewDeadline);
    }
}

