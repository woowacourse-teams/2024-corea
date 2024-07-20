package corea.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

@Converter
public class StringToListConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = ", ";

    @Override
    public String convertToDatabaseColumn(List<String> source) {
        return String.join(DELIMITER, source);
    }

    @Override
    public List<String> convertToEntityAttribute(String source) {
        return Arrays.stream(source.split(DELIMITER))
                .toList();
    }
}
