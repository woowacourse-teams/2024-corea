package corea.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StringToListConverterTest {

    private StringToListConverter sut = new StringToListConverter();

    @Test
    @DisplayName("배열을 문자열로 변환한다.")
    void listToString() {
        List<String> list = List.of("TDD", "클린코드");
        String result = sut.convertToDatabaseColumn(list);
        assertThat(result).isEqualTo("TDD, 클린코드");
    }

    @Test
    @DisplayName("문자열을 배열로 변환한다.")
    void StringToList() {
        String source = "TDD, 클린코드";
        List<String> list = sut.convertToEntityAttribute(source);
        assertThat(list).containsExactly("TDD", "클린코드");
    }
}
