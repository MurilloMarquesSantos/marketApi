package market.api.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserAccountRequest {

    @NotEmpty(message = "The user must have a name")
    private String name;

    @NotEmpty(message = "The user must have a username")
    private String username;

    @NotEmpty(message = "The user must have a password")
    private String password;

    private String roleName = "ROLE_USER";
}
