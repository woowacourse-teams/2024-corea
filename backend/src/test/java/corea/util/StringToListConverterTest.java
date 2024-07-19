package corea.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StringToListConverterTest {
    private StringToListConverter sut = new StringToListConverter();

    @Test
    @DisplayName("배열을 문자열로 변환한다.")
    void listToString(){
        List<String> list = List.of("TDD","클린코드");
        String s = sut.convertToDatabaseColumn(list);
        assertThat(s).isEqualTo("TDD, 클린코드");
    }
    @Test
    @DisplayName("문자열을 배열로 변환한다.")
    void StringToList(){
        String s = "TDD, 클린코드";
        List<String> list  = sut.convertToEntityAttribute(s);
        assertThat(list).containsExactly("TDD","클린코드");
    }
}
