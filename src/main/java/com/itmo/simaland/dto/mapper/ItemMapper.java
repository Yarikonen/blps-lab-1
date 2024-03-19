package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.dto.item.ItemRequest;
import com.itmo.simaland.dto.item.ItemResponse;
import com.itmo.simaland.model.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(source = "quantity", target = "quantity")
    Item toItem(ItemRequest itemRequest);

    Item toItemWithId(ItemRequest itemRequest, Long id);

    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "number", target = "pageNumber")
    ListResponse<ItemResponse> pageToItemListResponse(Page<Item> page);

    ItemResponse toItemResponse(Item item);
}
