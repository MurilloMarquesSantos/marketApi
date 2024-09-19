package market.api.handler;

import market.api.exception.ExceptionDetails;
import market.api.exception.SQLExceptionDetails;
import market.api.exception.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<SQLExceptionDetails> handleSQLException(SQLException sql) {
        return new ResponseEntity<>(
                SQLExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .title("SQL EXCEPTION")
                        .details(sql.getMessage())
                        .developersMessage(sql.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST
        );

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @Nullable HttpHeaders headers, @Nullable HttpStatusCode status,
            @Nullable WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String field = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));

        String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .title("Bad requestException, check the fields")
                        .details("Check field errors")
                        .developersMessage(ex.getClass().getName())
                        .fields(field)
                        .fieldsMessages(fieldMessage)
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, @Nullable HttpHeaders headers, HttpStatusCode statusCode, @Nullable WebRequest request) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .title(ex.getCause().getMessage())
                .status(HttpStatus.valueOf(statusCode.value()))
                .details(ex.getMessage())
                .developersMessage(ex.getClass().getName())
                .build();
        return new ResponseEntity<>(exceptionDetails, headers, statusCode);

    }
}
