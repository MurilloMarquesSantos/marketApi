package market.api.requests;

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
    private String roleName;
}
