package market.api.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserAccountRequest {

    private String name;
    private String username;
    private String password;
    private String roleName;
}
