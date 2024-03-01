package com.itmo.simaland.service;


import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final UserService userService;

    public Order createOrder(Order order) {
        System.out.println(order.getCustomer().toString());
        return orderRepository.save(enrichOrder(order));
    }

    private Order enrichOrder(Order order) {
        System.out.println(order.getCustomer().toString());
        userService.findUserById(order.getCustomer().getId())
                .ifPresentOrElse(
                        order::setCustomer,
                        () -> {
                            throw new EntityNotFoundException("Юзера с таким id не существует");
                        }
                );
       order.setItems(
        itemService.getAllItemsByIds( order.getItems()
               .stream().map(Item::getId)
               .collect(Collectors.toList()))
       );
       order.setOrderDate(LocalDate.now());
       return order;
    }

    public Page<Order> getOrders(PageRequest pageRequest){
        return orderRepository.findAll(pageRequest);
    }



}
