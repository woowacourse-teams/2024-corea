package corea.feedback.domain;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ReceivedFeedbacks {

    private final List<List<FeedbackKeyword>> reviewerFeedbacks;
    private final List<List<FeedbackKeyword>> revieweeFeedbacks;

    public List<String> findTopFeedbackKeywords(int maxSize) {
        List<FeedbackKeyword> feedbackKeywords = sortBySatisfaction();

        return feedbackKeywords.stream()
                .filter(FeedbackKeyword::isPositive)
                .map(FeedbackKeyword::getMessage)
                .limit(maxSize)
                .toList();
    }

    private List<FeedbackKeyword> sortBySatisfaction() {
        return Stream.concat(
                        reviewerFeedbacks.stream().flatMap(List::stream),
                        revieweeFeedbacks.stream().flatMap(List::stream)
                )
                .sorted(Comparator.comparingInt((FeedbackKeyword keywords) -> keywords.getSatisfaction().getValue()).reversed())
                .toList();
    }
}
