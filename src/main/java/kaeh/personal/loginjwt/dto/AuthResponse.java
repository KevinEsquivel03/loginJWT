package kaeh.personal.loginjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthResponse class.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    String token;
}
