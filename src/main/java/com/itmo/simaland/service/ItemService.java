package com.itmo.simaland.service;

import com.itmo.simaland.dto.filter.ItemFilter;
import com.itmo.simaland.dto.item.UpdateItemRequest;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private Specification<Item> createSpecification(ItemFilter itemFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (itemFilter.getName() != null && !itemFilter.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + itemFilter.getName().toLowerCase() + "%"));
            }
            if (itemFilter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), itemFilter.getMinPrice()));
            }
            if (itemFilter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), itemFilter.getMaxPrice()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<Item> getAllItems(PageRequest pageRequest, ItemFilter itemFilter) {

        Specification<Item> spec = createSpecification(itemFilter);
        return itemRepository.findAll(spec, pageRequest);
    }


    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAllItemsByIds(List<Long> ids) {
        return itemRepository.findAllById(ids);
    }

    public Item updateItem(Long id, UpdateItemRequest updateRequest) {
        return itemRepository.findById(id).map(existingItem -> {
            if (updateRequest.getName() != null) existingItem.setName(updateRequest.getName());
            if (updateRequest.getPrice() != null) existingItem.setPrice(updateRequest.getPrice());
            return itemRepository.save(existingItem);
        }).orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
    }


    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
        itemRepository.delete(item);
    }
}
