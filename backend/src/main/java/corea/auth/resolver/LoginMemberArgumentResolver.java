package corea.auth.resolver;

import corea.auth.RequestHandler;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.auth.service.TokenService;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static corea.global.config.Constants.ANONYMOUS;

@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String ID = "id";
    private static final Logger log = LogManager.getLogger(LoginMemberArgumentResolver.class);

    private final RequestHandler requestHandler;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public AuthInfo resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = requestHandler.extract(request);
        if (accessToken.equals(ANONYMOUS)) {
            throw new CoreaException(ExceptionType.AUTHORIZATION_ERROR);
        }
        log.info("로그인 시도[토큰={}]", accessToken);

        long memberId = tokenService.findMemberIdByToken(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTHORIZATION_ERROR));

        return AuthInfo.from(member);
    }
}
