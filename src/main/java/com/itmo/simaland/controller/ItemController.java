package com.itmo.simaland.controller;

import com.itmo.simaland.dto.filter.ItemFilter;
import com.itmo.simaland.dto.item.ItemRequest;
import com.itmo.simaland.dto.item.ItemResponse;
import com.itmo.simaland.dto.mapper.ItemMapper;
import com.itmo.simaland.dto.paging.PaginationRequest;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Item Controller", description = "Item Controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @GetMapping
    @Operation(summary = "Get all items")
    @ApiResponse(responseCode = "200", description = "Items list", content =  @Content)
    public Page<ItemResponse> getAllItems(
            PaginationRequest paginationRequest,
            ItemFilter itemFilter) {

        PageRequest pageRequest = paginationRequest.toPageRequest();
        Page<Item> page = itemService.getAllItems(pageRequest, itemFilter);

        return page.map(itemMapper::toItemResponse);
    }

    @PostMapping
    @Operation(summary = "Create new item")
    @ApiResponse(responseCode = "201", description = "Item created", content =  @Content)
    public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemRequest itemRequest) {
        Item item = itemMapper.toItem(itemRequest);
        Item savedItem = itemService.createItem(item);
        ItemResponse itemResponse = itemMapper.toItemResponse(savedItem);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }
}
