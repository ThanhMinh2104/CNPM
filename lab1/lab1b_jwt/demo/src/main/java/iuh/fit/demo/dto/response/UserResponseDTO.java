package iuh.fit.demo.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private String id;
    private String username;
    private String email;
    private LocalDate dob;
}
