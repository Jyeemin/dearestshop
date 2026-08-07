package dearest.dearestshop.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> runtime(RuntimeException e){

        return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                        false,
                        e.getMessage(),
                        null
                )
        );

    }
}
