package sg.edu.nus.iss.project_backend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.json.Json;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleApiException(ApiException ex){
            return ResponseEntity.status(400)
                .body(
                    Json.createObjectBuilder()
                        .add("message", "failure")
                        .build().toString()
                );
    }
}
