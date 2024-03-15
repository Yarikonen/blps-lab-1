package com.itmo.simaland.dto.mapper;


import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.dto.user.UserRequest;
import com.itmo.simaland.dto.user.UserResponse;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest userDTO);

    UserResponse toUserResponse(User user);

    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "number", target = "pageNumber")
    @Mapping(source="id", target="id")
    ListResponse<OrderResponse> pageToUserListResponse(Page<Order> page);

}
