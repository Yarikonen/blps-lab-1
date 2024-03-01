package com.itmo.simaland.service;

import com.itmo.simaland.dto.filter.ItemFilter;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

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
}
