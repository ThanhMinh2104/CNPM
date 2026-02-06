package iuh.fit.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequest {
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*", message = "Username must start by characters")
    @Size(min = 5, max = 10, message = "Min least 5 characters and max is 10 characters")
    private String username;
    private String password;
    @Email(message = "Email is invalid")
    private String email;
    private LocalDate dob;
}
