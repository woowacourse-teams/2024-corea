package corea.global.customizer;

import corea.exception.ErrorResponse;
import corea.exception.ExceptionType;
import corea.exception.ExceptionTypeGroup;
import corea.global.annotation.ApiErrorResponse;
import corea.global.annotation.ApiErrorResponses;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class ErrorResponseCustomizer implements OperationCustomizer {
    private final Schema<ErrorResponse> errorEntitySchema = ModelConverters.getInstance()
            .readAllAsResolvedSchema(ErrorResponse.class).schema;

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiResponses apiResponses = operation.getResponses();
        ApiErrorResponse apiErrorResponse = handlerMethod.getMethodAnnotation(ApiErrorResponse.class);
        ApiErrorResponses apiResponseCodes = handlerMethod.getMethodAnnotation(ApiErrorResponses.class);
        if (apiErrorResponse != null) {
            putApiErrorResponseCode(apiResponses, apiErrorResponse);
        }
        if (apiResponseCodes != null) {
            putApiErrorResponsesCode(apiResponses, apiResponseCodes);
        }
        return operation;
    }

    private void putApiErrorResponseCode(ApiResponses apiResponses, ApiErrorResponse apiErrorResponse) {
        ExceptionType type = apiErrorResponse.value();
        apiResponses.put(type.name(), convertErrorResponse(type));
    }


    private void putApiErrorResponsesCode(ApiResponses apiResponses, ApiErrorResponses apiErrorResponses) {
        Set<ExceptionType> types = Stream.concat(
                        Arrays.stream(apiErrorResponses.value()),
                        Arrays.stream(apiErrorResponses.groups())
                                .map(ExceptionTypeGroup::getErrorTypes)
                                .flatMap(Collection::stream))
                .collect(Collectors.toSet());

        putApiErrorResponseWithGroupStatus(apiResponses, types);
    }

    private void putApiErrorResponseWithGroupStatus(ApiResponses apiResponses, Set<ExceptionType> types) {
        Map<HttpStatus, List<ExceptionType>> map = types.stream()
                .collect(Collectors.groupingBy(ExceptionType::getHttpStatus));

        map.entrySet()
                .stream()
                .forEach(entry -> putApiErrorResponseCode(apiResponses, entry.getKey(), entry.getValue()));
    }

    private void putApiErrorResponseCode(ApiResponses apiResponses, HttpStatus status, List<ExceptionType> types) {
        apiResponses.put(status.toString(), convertErrorResponses(types));
    }


    private ApiResponse convertErrorResponse(ExceptionType type) {
        return convertResponseInner(
                type
        );
    }

    private ApiResponse convertResponseInner(ExceptionType code) {
        MediaType mediaType = new MediaType()
                .schema(errorEntitySchema.description(code.getMessage()));

        mediaType.addExamples(code.name(), new Example().value(ErrorResponse.from(code)));

        return new ApiResponse()
                .content(
                        new Content()
                                .addMediaType(
                                        APPLICATION_JSON_VALUE,
                                        mediaType
                                )
                )
                .description(code.name());
    }

    private ApiResponse convertErrorResponses(List<ExceptionType> types) {
        return convertResponseInner(
                types
        );
    }

    private ApiResponse convertResponseInner(List<ExceptionType> codes) {
        MediaType mediaType = new MediaType()
                .schema(errorEntitySchema.description("ErrorResponses Type"));
        codes.forEach(code -> mediaType.addExamples(code.name(), new Example().value(ErrorResponse.from(code))));
        return new ApiResponse()
                .content(
                        new Content()
                                .addMediaType(
                                        APPLICATION_JSON_VALUE,
                                        mediaType
                                )
                );
    }
}
