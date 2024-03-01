package com.itmo.simaland.controller;

import com.itmo.simaland.dto.filter.ItemFilter;
import com.itmo.simaland.dto.item.ItemRequest;
import com.itmo.simaland.dto.item.ItemResponse;
import com.itmo.simaland.dto.mapper.ItemMapper;
import com.itmo.simaland.dto.paging.PaginationRequest;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @GetMapping
    public ResponseEntity<Page<ItemResponse>> getAllItems(
            PaginationRequest paginationRequest,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice) {
        PageRequest pageRequest = paginationRequest.toPageRequest();
        ItemFilter itemFilter = new ItemFilter(name, minPrice, maxPrice);
        Page<Item> page = itemService.getAllItems(pageRequest, itemFilter);

        Page<ItemResponse> responsePage = page.map(itemMapper::toItemResponse);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        Item item = itemMapper.toItem(itemRequest);
        Item savedItem = itemService.createItem(item);
        ItemResponse itemResponse = itemMapper.toItemResponse(savedItem);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }
}
