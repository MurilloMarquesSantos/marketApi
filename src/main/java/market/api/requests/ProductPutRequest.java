package market.api.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPutRequest {
    @NotNull(message = "ID cannot be null")
    @Min(value = 1, message = "ID must be greater than or equal to 1")
    private Long id;

    @NotEmpty(message = "The product name cannot be empty")
    private String name;

    @Min(value = 1, message = "The minimum quantity must be 1")
    private Long quantity;
}
