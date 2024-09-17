package market.api.util;

import market.api.requests.ProductsPutRequest;

import static market.api.util.ProductCreator.createValidProduct;

public class ProductPutRequestCreator {

    public static ProductsPutRequest createValidPutProduct() {
        return ProductsPutRequest.builder()
                .id(createValidProduct().getId())
                .name(createValidProduct().getName())
                .quantity(createValidProduct().getQuantity())
                .build();
    }

}
