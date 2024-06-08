package com.itmo.simaland.service;


import com.itmo.simaland.exception.handler.AttributeMissingException;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.model.entity.OrderItem;
import com.itmo.simaland.model.entity.PickUpPoint;
import com.itmo.simaland.model.enums.AddressType;
import com.itmo.simaland.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final WarehouseItemService warehouseItemService;
    private final UserService userService;

    private final PickUpPointService pickUpPointService;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Order createOrderWithItem(Item item, int quantity, Long userId) {
        Order order = new Order();
        order.setCustomer(userService.getUserById(userId));
        order.setOrderDate(LocalDate.now());
        order.setPickUpAddress("no address");
        order.setPaid(false);

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setQuantity(quantity);
        orderItem.setOrder(order);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);

        warehouseItemService.reserveItems(orderItems);

        return orderRepository.save(order);
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Order createOrder(Order order) {
        logger.info("order items {}", order.getOrderItems());
        Order enrichedOrder = enrichOrder(order);
        warehouseItemService.reserveItems(order.getOrderItems());
        return orderRepository.save(enrichedOrder);
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order getOrderById(Long id) {
        return findOrderById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    public Boolean checkIfPaid(Long id){
        return getOrderById(id).getPaid();
    }

    public Page<Order> getOrders(PageRequest pageRequest){
        return orderRepository.findAll(pageRequest);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addItemToOrder(Long orderId, Item item, int quantity) {
        Order order = getOrderById(orderId);

        boolean itemExists = order.getOrderItems().stream()
                .anyMatch(orderItem -> orderItem.getItem().getId().equals(item.getId()));

        if (itemExists) {
            order.getOrderItems().stream()
                    .filter(orderItem -> orderItem.getItem().getId().equals(item.getId()))
                    .findFirst()
                    .ifPresent(orderItem -> orderItem.setQuantity(orderItem.getQuantity() + quantity));
        } else {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setQuantity(quantity);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.save(order);
    }
    public void removeOrderById(Long id) {
       orderRepository.delete(getOrderById(id));
    }

    private Order enrichOrder(Order order) {
        if (order.getAddressType()== AddressType.PICK_UP){
            PickUpPoint pickUpPoint = pickUpPointService.getById(order.getPickUpPointId());
            if (pickUpPoint==null) {
                throw new AttributeMissingException("Pick up point is not presented");
            }
            order.setPickUpAddress(pickUpPoint.getAddress());
        }
        else {
            if (order.getPickUpAddress().isEmpty()){
                throw new AttributeMissingException("Pick up address is not presented");
            }
        }
        order.setCustomer(userService.getUserById(order.getCustomer().getId()));
        List<OrderItem> orderItems = order.getOrderItems().stream()
                .map(itemQuantity -> {
                    Item item = itemQuantity.getItem();
                    OrderItem orderItem = new OrderItem();
                    orderItem.setItem(item);
                    orderItem.setQuantity(itemQuantity.getQuantity());
                    orderItem.setOrder(order);
                    return orderItem;
                }).collect(Collectors.toList());
        logger.info("List of order items is {}", orderItems);
        order.setOrderItems(orderItems);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    public Order updateOrderPaidStatus(Long orderId, boolean paid) {
        Order order = getOrderById(orderId);
        order.setPaid(paid);
        return orderRepository.save(order);
    }
}
