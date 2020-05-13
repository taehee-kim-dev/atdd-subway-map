package wooteco.subway.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import wooteco.subway.admin.dto.ErrorResponse;
import wooteco.subway.admin.exception.DuplicatedValueException;
import wooteco.subway.admin.exception.NotFoundValueException;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {

    @ExceptionHandler({DuplicatedValueException.class})
    public ResponseEntity<ErrorResponse> errorDuplicatedValue(DuplicatedValueException error) {
        return errorResponseResponseEntity(error);
    }

    @ExceptionHandler({NotFoundValueException.class})
    public ResponseEntity<ErrorResponse> errorNotFoundValue(NotFoundValueException error) {
        return errorResponseResponseEntity(error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception error) {
        return errorResponseResponseEntity(error);
    }

    private ResponseEntity<ErrorResponse> errorResponseResponseEntity(Exception error) {
        final ErrorResponse response = ErrorResponse.of(error.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
