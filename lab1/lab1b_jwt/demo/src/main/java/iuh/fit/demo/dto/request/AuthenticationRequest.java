package iuh.fit.demo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class AuthenticationRequest {
    private String username;
    private String password;
}
