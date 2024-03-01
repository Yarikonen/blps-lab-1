package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.item.ItemRequest;
import com.itmo.simaland.dto.item.ItemResponse;
import com.itmo.simaland.model.entity.Item;
import org.springframework.data.domain.Page;

public class ItemMapper {

    public static Item toItem(ItemRequest itemRequest) {
        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setPrice(itemRequest.getPrice());
        return item;
    }

    public static ItemRequest toItemRequest(Item item) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName(item.getName());
        itemRequest.setPrice(item.getPrice());
        return itemRequest;
    }

    public static ItemResponse toItemResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(item.getId());
        itemResponse.setName(item.getName());
        itemResponse.setPrice(item.getPrice());
        return itemResponse;
    }

    public static Page<ItemResponse> mapPageToItemResponse(Page<Item> items) {
        return items.map(ItemMapper::toItemResponse);
    }
}
