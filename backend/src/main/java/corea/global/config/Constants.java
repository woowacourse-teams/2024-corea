package corea.global.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ANONYMOUS = "ANONYMOUS";
    public static final String TOKEN_TYPE = "Bearer ";
    public static final String REFRESH_COOKIE = "refreshToken";
    public static final long COOKIE_EXPIRATION = 1209600;
}
