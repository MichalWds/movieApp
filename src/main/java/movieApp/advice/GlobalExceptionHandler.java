package movieApp.advice;

import movieApp.exception.AuthorException;
import movieApp.exception.MovieException;
import movieApp.exception.RatingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(RatingException.class)
    public ResponseEntity<Object> handleRatingException(RatingException ex){
        return new ResponseEntity<>("RatingException: Incorrect Rating!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        return new ResponseEntity<>("IllegalArgumentException: Incorrect arguments.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorException.class)
    public ResponseEntity<Object> handleAuthorException(AuthorException ex, WebRequest request){
        return new ResponseEntity<>("AuthorException: Not found any author with given id.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieException.class)
    public ResponseEntity<Object> handleMovieException(MovieException ex, WebRequest request){
        return new ResponseEntity<>("MovieException: Not found any movie with given id.", HttpStatus.NOT_FOUND);
    }
}
