package iuh.fit.demo.exception;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AppException extends RuntimeException{
    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode){
        super();
        this.errorCode = errorCode;
    }
}
