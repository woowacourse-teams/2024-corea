package corea.feedback.repository;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.SocialFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SocialFeedbackRepository extends JpaRepository<SocialFeedback, Long> {

    @Query("SELECT r.keywords FROM SocialFeedback r WHERE r.receiver.id = :receiverId")
    List<List<FeedbackKeyword>> findAllKeywordsByReceiverId(long receiverId);

    Optional<SocialFeedback> findByRoomIdAndDeliverIdAndReceiverUsername(long roomId, long deliverId, String username);

    boolean existsByRoomIdAndDeliverIdAndReceiverId(long roomId, long deliverId, long receiverId);

    List<SocialFeedback> findByDeliverId(long deliverId);

    List<SocialFeedback> findByReceiverId(long receiverId);

    List<SocialFeedback> findAllByRoomId(long roomId);
}
