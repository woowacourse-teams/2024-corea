package corea.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

@Converter
public class StringToListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        strings.forEach(s -> sb.append(s).append(", "));
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(", "))
                .toList();
    }
}
