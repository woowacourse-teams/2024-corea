package corea.ranking.repository;

import corea.evaluation.domain.EvaluateClassification;
import corea.ranking.domain.Ranking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    @Query("SELECT r FROM Ranking r WHERE r.classification = :classification AND r.date = :date ORDER BY r.rank ASC")
    List<Ranking> findTopRankingsByClassificationAndDate(EvaluateClassification classification, LocalDate date, Pageable pageable);
}
