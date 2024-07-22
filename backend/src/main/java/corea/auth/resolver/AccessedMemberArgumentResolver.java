package corea.auth.resolver;

import corea.auth.RequestHandler;
import corea.auth.annotation.AccessedMember;
import corea.auth.domain.AuthInfo;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccessedMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final RequestHandler requestHandler;
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AccessedMember.class);
    }

    @Override
    public AuthInfo resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Optional<Member> memberOpt = memberRepository.findByEmail(requestHandler.extract(request));

        return memberOpt.map(AuthInfo::from)
                .orElse(AuthInfo.getAnonymous());
    }
}
