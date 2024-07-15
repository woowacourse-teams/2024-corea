package corea.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private long memberId;

    @Column(length = 25000)
    private String repositoryLink;

    @Column(length = 25000)
    private String thumbnailLink;

    private int matchingSize;

    private String keyword;

    private LocalDateTime submissionDeadline;

    private LocalDateTime reviewDeadline;

    protected Room() {
    }

    public Room(final String title,
                final long memberId,
                final String repositoryLink,
                final String thumbnailLink,
                final int matchingSize,
                final String keyword,
                final LocalDateTime submissionDeadline,
                final LocalDateTime reviewDeadline) {
        this.title = title;
        this.memberId = memberId;
        this.repositoryLink = repositoryLink;
        this.thumbnailLink = thumbnailLink;
        this.matchingSize = matchingSize;
        this.keyword = keyword;
        this.submissionDeadline = submissionDeadline;
        this.reviewDeadline = reviewDeadline;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public int getMatchingSize() {
        return matchingSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public LocalDateTime getSubmissionDeadline() {
        return submissionDeadline;
    }

    public LocalDateTime getReviewDeadline() {
        return reviewDeadline;
    }
}

