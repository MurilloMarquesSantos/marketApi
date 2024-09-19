package market.api.mapper;

import market.api.domain.Users;
import market.api.requests.NewUserAccountRequest;
import market.api.requests.NewUserAccountRequestAdmin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UsersMapper {

    public static final UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    public abstract Users toUser(NewUserAccountRequestAdmin accountRequestAdmin);

    public abstract Users toUser(NewUserAccountRequest accountRequest);
}
