package bccontrol;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuditTrailException.class)
    public final ResponseEntity<Object> handleAllExceptions(AuditTrailException ex) {
        AuditTrailExSchema exceptionResponse =
                new AuditTrailExSchema(ex.getRemId(),ex.getRemStatus(),ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
