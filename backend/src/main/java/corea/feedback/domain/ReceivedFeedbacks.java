package corea.feedback.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
public class ReceivedFeedbacks {

    private final List<List<FeedbackKeyword>> reviewerFeedbacks;
    private final List<List<FeedbackKeyword>> revieweeFeedbacks;

    public List<String> findTopFeedbackKeywords(int maxSize) {
        Map<FeedbackKeyword, Long> feedbackKeywords = countPositiveFeedbackKeywords();
        List<FeedbackKeyword> sortedFeedbackKeywords = sortByValue(feedbackKeywords);

        return getTopFeedbackKeywordMessages(sortedFeedbackKeywords, maxSize);
    }

    private Map<FeedbackKeyword, Long> countPositiveFeedbackKeywords() {
        return Stream.concat(
                        reviewerFeedbacks.stream().flatMap(List::stream),
                        revieweeFeedbacks.stream().flatMap(List::stream)
                )
                .filter(FeedbackKeyword::isPositive)
                .collect(groupingBy(Function.identity(), counting()));
    }

    private List<FeedbackKeyword> sortByValue(Map<FeedbackKeyword, Long> feedbackKeywords) {
        return feedbackKeywords.entrySet()
                .stream()
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<String> getTopFeedbackKeywordMessages(List<FeedbackKeyword> sortedFeedbackKeywords, int maxSize) {
        return sortedFeedbackKeywords.stream()
                .limit(maxSize)
                .map(FeedbackKeyword::getMessage)
                .toList();
    }
}
