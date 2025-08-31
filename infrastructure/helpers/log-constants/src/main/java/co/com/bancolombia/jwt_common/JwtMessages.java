package co.com.bancolombia.jwt_common;

public interface JwtMessages{
    String TOKEN_NO_FOUNDS = "no token was found";
    String TOKEN_INVALID = "invalid token";
    String TOKEN_EXPIRED = "token expired";
    String TOKEN_UNSUPPORTED= "token unsupported";
    String TOKEN_MALFORMED = "token malformed";
    String ILLEGAL_ARGS = "illegal arguments";
    String INVALID_ROL = "Invalid role in token";
    String ROL_NO_FOUNDS = "No roles found in token";

}
