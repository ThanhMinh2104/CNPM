package iuh.fit.demo.dto.request;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String email;
    private LocalDate dob;
}
