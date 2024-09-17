package market.api.util;

import market.api.requests.ProductsPostRequest;

import static market.api.util.ProductCreator.createInvalidProduct;
import static market.api.util.ProductCreator.createValidProductToBeSaved;

public class ProductPostRequestCreator {

    public static ProductsPostRequest createValidPostProduct() {
        return ProductsPostRequest.builder()
                .name(createValidProductToBeSaved().getName())
                .quantity(createValidProductToBeSaved().getQuantity())
                .build();
    }

    public static ProductsPostRequest createInvalidPostProduct() {
        return ProductsPostRequest.builder()
                .name(createInvalidProduct().getName())
                .quantity(createInvalidProduct().getQuantity())
                .build();
    }

}
