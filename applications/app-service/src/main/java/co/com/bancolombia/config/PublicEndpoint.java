package co.com.bancolombia.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PublicEndpoint {
    LOGIN("/api/v1/login"),
    SWAGGER_UI("/swagger-ui"),
    SWAGGER_UI_HTML("/swagger-ui.html"),
    API_DOCS("/v3/api-docs"),
    SWAGGER_RESOURCES("/swagger-resources"),
    WEBJARS("/webjars");

    private final String path;

    public static boolean isPublic(String requestPath) {
        return Arrays.stream(values())
                .anyMatch(endpoint -> requestPath.startsWith(endpoint.getPath()));
    }
}