package market.api.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPutRequest {
    @NotEmpty(message = "The product id cannot be empty")
    private Long id;

    @NotEmpty(message = "The product name cannot be empty")
    private String name;

    @Min(value = 1, message = "The minimum quantity must be 1")
    private Long quantity;
}
