package corea.feedback.repository;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.DevelopFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DevelopFeedbackRepository extends JpaRepository<DevelopFeedback, Long> {

    @Query("SELECT r.keywords FROM DevelopFeedback r WHERE r.receiver.id = :receivedId")
    List<List<FeedbackKeyword>> findAllKeywordsByReceiverId(long receivedId);

    Optional<DevelopFeedback> findByRoomIdAndDeliverIdAndReceiverUsername(long roomId, long deliverId, String username);

    boolean existsByRoomIdAndDeliverIdAndReceiverId(long roomId, long deliverId, long receiverId);

    List<DevelopFeedback> findByDeliverId(long deliverId);

    List<DevelopFeedback> findByReceiverId(long receiverId);
}