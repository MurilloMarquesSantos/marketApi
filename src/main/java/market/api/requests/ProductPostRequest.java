package market.api.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPostRequest {
    @NotEmpty(message = "The product name cannot be empty")
    private String name;

    @NotEmpty(message = "The product quantity cannot be empty")
    private Long quantity;
}
