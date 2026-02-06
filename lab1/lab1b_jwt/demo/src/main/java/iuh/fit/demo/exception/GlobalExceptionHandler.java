package iuh.fit.demo.exception;

import iuh.fit.demo.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handleRunTimeException(RuntimeException e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException){
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setMessage(appException.getErrorCode().getMessage());
        apiResponse.setCode(appException.getErrorCode().getCode());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResponseEntity.internalServerError().body(e.getFieldError().getDefaultMessage());
    }
}
