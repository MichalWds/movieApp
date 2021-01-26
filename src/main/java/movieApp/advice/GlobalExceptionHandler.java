package movieApp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request){
//        return ResponseEntity.badRequest().build();
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex){
        return new ResponseEntity<>("RuntimeException", HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleRuntimeException(IllegalArgumentException ex, WebRequest request){
        return ResponseEntity.badRequest().build();
    }
}
