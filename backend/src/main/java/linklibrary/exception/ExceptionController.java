package linklibrary.exception;

import linklibrary.dto.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    /**
     * @NotBlank 예외 캐치
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("validation 예외 캐치");
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return new ResponseEntity<>(new ResponseData(errorMessage, null), HttpStatus.BAD_REQUEST);
    }

    //조회시 엔티티가 없을 때, 생성시 이미 존재할 때
    @ExceptionHandler({EntityNotFoundException.class, IllegalStateException.class})
    public ResponseEntity<ResponseData> handleEntityNotFoundExceptionIllegalStateException(Exception e) {
        return new ResponseEntity<>(new ResponseData(e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    //파라미터가 제대로 넘어오지 않았을 때 (파일업로드)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseData> handleIllegalArgumentException(Exception e) {
        return new ResponseEntity<>(new ResponseData(e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ResponseData> handleJsonParseException(JsonParseException e) {
        return new ResponseEntity<>(new ResponseData(e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseData> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(new ResponseData(e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
