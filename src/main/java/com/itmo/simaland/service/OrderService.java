package com.itmo.simaland.service;


import com.itmo.simaland.exception.handler.AttributeMissingException;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.model.enums.AddressType;
import com.itmo.simaland.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final UserService userService;

    private final PickUpPointService pickUpPointService;

    public Order createOrder(Order order) {
        return orderRepository.save(enrichOrder(order));
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order getOrderById(Long id) {
        return findOrderById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    public Page<Order> getOrders(PageRequest pageRequest){
        return orderRepository.findAll(pageRequest);
    }

    public void removeOrderById(Long id) {
       orderRepository.delete(getOrderById(id));
    }

    private Order enrichOrder(Order order) {
        if (order.getAddressType()== AddressType.PICK_UP){
            Long pickUpPointId = order.getPickUpPointId();
            if (pickUpPointId==null) {
                throw new AttributeMissingException("Pick up point is not presented");
            }
            order.setPickUpAddress(pickUpPointService.getById(pickUpPointId).getAddress());
        }
        else {
            if (order.getPickUpAddress().isEmpty()){
                throw new AttributeMissingException("Pick up address is not presented");
            }
        }
        order.setCustomer(userService.getUserById(order.getCustomer().getId()));
        order.setItems(
                itemService.getAllItemsByIds( order.getItems()
                        .stream().map(Item::getId)
                        .collect(Collectors.toList()))
        );
        order.setOrderDate(LocalDate.now());
        return order;
    }

    public Order updateOrderPaidStatus(Long orderId, boolean paid) {
        Order order = getOrderById(orderId);
        order.setPaid(paid);
        return orderRepository.save(order);
    }
}
