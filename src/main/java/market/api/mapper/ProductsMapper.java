package market.api.mapper;

import market.api.domain.Products;
import market.api.requests.ProductsPostRequest;
import market.api.requests.ProductsPutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@SuppressWarnings("java:S6548")
@Mapper(componentModel = "spring")
public abstract class ProductsMapper {

    public static final ProductsMapper INSTANCE = Mappers.getMapper(ProductsMapper.class);

    public abstract Products toProduct(ProductsPostRequest productPostRequest);

    public abstract Products toProduct(ProductsPutRequest productPutRequest);
}
