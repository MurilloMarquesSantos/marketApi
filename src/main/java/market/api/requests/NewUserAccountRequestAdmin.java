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

public class NewUserAccountRequestAdmin {
    private String name;
    private String username;
    private String password;
    @NotEmpty(message = "The user must have a role")
    private String roleName;
}
