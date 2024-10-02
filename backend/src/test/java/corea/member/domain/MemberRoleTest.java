package corea.member.domain;

import corea.exception.CoreaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static corea.exception.ExceptionType.NOT_FOUND_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberRoleTest {

    @ParameterizedTest
    @CsvSource({"reviewer, REVIEWER", "REVIEWEE, REVIEWEE", "boTh, BOTH"})
    @DisplayName("클라이언트에서 전달한 String 값 role을 기반으로 해당하는 MemberRole을 찾아온다.")
    void from(String role, MemberRole memberRole) {
        MemberRole foundMemberRole = MemberRole.from(role);

        assertThat(foundMemberRole).isEqualTo(memberRole);
    }

    @Test
    @DisplayName("MemberRole에 해당하지 않는 역할의 요청이 들어오면 MEMBER_NOT_FOUND 에러를 반환한다.")
    void from_notSupportedMemberRole() {
        assertThatThrownBy(() -> MemberRole.from("error"))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(NOT_FOUND_ERROR);
                });
    }
}
