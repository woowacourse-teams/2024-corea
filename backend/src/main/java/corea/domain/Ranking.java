package corea.domain;

import corea.room.domain.Classification;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Ranking {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long userId;

    private int rank;

    private LocalDate localDate;

    private Classification classification;
}
