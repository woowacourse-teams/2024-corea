package corea.feedback.util;

import corea.feedback.domain.FeedbackKeyword;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackKeywordConverter {

    private static final Map<String, FeedbackKeyword> MESSAGE_CLASSIFIER = Arrays.stream(FeedbackKeyword.values())
            .collect(Collectors.toMap(FeedbackKeyword::getMessage, Function.identity()));

    public static List<String> convertToMessages(List<FeedbackKeyword> keywords) {
        return keywords.stream()
                .map(FeedbackKeyword::getMessage)
                .toList();
    }

    public static List<FeedbackKeyword> convertToKeywords(List<String> messages) {
        return messages.stream()
                .map(MESSAGE_CLASSIFIER::get)
                .filter(Objects::nonNull)
                .toList();
    }
}
