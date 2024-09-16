package market.api.mapper;

import market.api.domain.Products;
import market.api.requests.ProductPostRequest;
import market.api.requests.ProductPutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@SuppressWarnings("java:S6548")
@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public abstract Products toProduct(ProductPostRequest productPostRequest);

    public abstract Products toProduct(ProductPutRequest productPutRequest);
}
