package market.api.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected LocalDateTime timestamp;
    protected String title;
    protected HttpStatus status;
    protected String details;
    protected String developersMessage;
}
