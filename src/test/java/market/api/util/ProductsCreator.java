package market.api.util;

import market.api.domain.Products;

public class ProductsCreator {

    public static Products createValidProduct() {
        return Products.builder()
                .id(1L)
                .name("Tomato")
                .quantity(30L)
                .build();
    }

    public static Products createValidProductToBeSaved() {
        return Products.builder()
                .name("Tomato")
                .quantity(30L)
                .build();
    }

    public static Products createInvalidProduct(){
        return Products.builder()
                .id(1L)
                .name("")
                .quantity(30L)
                .build();
    }


}
