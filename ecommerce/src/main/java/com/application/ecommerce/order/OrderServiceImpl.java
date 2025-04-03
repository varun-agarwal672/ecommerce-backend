package com.application.ecommerce.order;

import com.application.ecommerce.model.Order;
import com.application.ecommerce.model.OrderItem;
import com.application.ecommerce.model.Product;
import com.application.ecommerce.model.User;
import com.application.ecommerce.order.dto.OrderRequest;
import com.application.ecommerce.order.dto.OrderResponse;
import com.application.ecommerce.orderItem.dto.OrderItemResponse;
import com.application.ecommerce.product.ProductRepository;
import com.application.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with given id"));

        Order order = new Order();
        order.setOrderDate(ZonedDateTime.now());
        order.setUser(user);
        order.setStatus("CREATED");

        List<OrderItem> orderItems = orderRequest.getItems().stream().map(item -> {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(product.getPrice() * item.getQuantity());

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);



        return mapToResponse(savedOrder);
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(item -> new OrderItemResponse(item.getProduct().getId(), item.getQuantity(), item.getTotalPrice()))
                .collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus());
        response.setOrderItems(itemResponses);

        return response;
    }
}
