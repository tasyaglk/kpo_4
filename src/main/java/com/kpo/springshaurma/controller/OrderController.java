package com.kpo.springshaurma.controller;

import com.kpo.springshaurma.entity.Order;
import com.kpo.springshaurma.entity.OrderDTO;
import com.kpo.springshaurma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        if (!validateOrderDTO(orderDTO)) {
            return ResponseEntity.badRequest().build();
        }
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setSpecialRequests(orderDTO.getSpecialRequests());
        order.setStatus(orderDTO.getStatus());

        Order savedOrder = orderService.saveOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }


    private boolean validateOrderDTO(OrderDTO orderDTO) {
        return orderDTO.getUserId() != null && orderDTO.getStatus() != null;
    }
}
