package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.item.ItemRequest;
import com.itmo.simaland.dto.item.ItemResponse;
import com.itmo.simaland.model.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item toItem(ItemRequest itemRequest);

    Item toItemWithId(ItemRequest itemRequest, Long id);

    ItemResponse toItemResponse(Item item);
}
