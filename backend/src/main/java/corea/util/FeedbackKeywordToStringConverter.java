package corea.util;

import corea.feedback.domain.FeedbackKeyword;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class FeedbackKeywordToStringConverter implements AttributeConverter<List<FeedbackKeyword>, String> {

    private static final String DELIMITER = ", ";

    @Override
    public String convertToDatabaseColumn(List<FeedbackKeyword> feedbackKeywords) {
        return feedbackKeywords.stream()
                .map(Enum::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<FeedbackKeyword> convertToEntityAttribute(String source) {
        return Arrays.stream(source.split(DELIMITER))
                .map(FeedbackKeyword::classify)
                .toList();
    }
}
