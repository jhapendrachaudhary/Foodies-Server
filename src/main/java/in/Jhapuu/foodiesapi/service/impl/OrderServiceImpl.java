package in.Jhapuu.foodiesapi.service.impl;

import in.Jhapuu.foodiesapi.entity.OrderEntity;
import in.Jhapuu.foodiesapi.io.OrderRequest;
import in.Jhapuu.foodiesapi.io.OrderResponse;
import in.Jhapuu.foodiesapi.repository.CartRepository;
import in.Jhapuu.foodiesapi.repository.OrderRepository;
import in.Jhapuu.foodiesapi.service.OrderService;
import in.Jhapuu.foodiesapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) {
        OrderEntity newOrder = convertToEntity(request);
        newOrder = orderRepository.save(newOrder);
        String loggedInUserId = userService.findByUserId();
        newOrder.setUserId(loggedInUserId);
        newOrder = orderRepository.save(newOrder);
        return convertToResponse(newOrder);
    }

    @Override
    public void verifyPayment(Map<String, String> paymentData, String status) {
        String paymentReferenceId = paymentData.get("paymentReferenceId");
        OrderEntity existingOrder = orderRepository.findByPaymentReferenceId(paymentReferenceId)
                .orElseThrow(()->new RuntimeException("Order not found"));
        existingOrder.setPaymentStatus(status);
        orderRepository.save(existingOrder);
        if ("paid".equalsIgnoreCase(status)){
            cartRepository.deleteByUserId(existingOrder.getUserId());
        }
    }

    @Override
    public List<OrderResponse> getUserOrders() {
        String loggedInUserId = userService.findByUserId();
        List<OrderEntity> list = orderRepository.findByUserId(loggedInUserId);
        return list.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public void removeOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderResponse> getOrdersOfAllUsers() {
        List<OrderEntity> list = orderRepository.findAll();
        return list.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
      OrderEntity entity=  orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));
      entity.setOrderStatus(status);
      orderRepository.save(entity);
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .id(newOrder.getId())
                .amount(newOrder.getAmount())
                .userId(newOrder.getUserId())
                .userAddress(newOrder.getUserAddress())
                .orderItems(newOrder.getOrderItems())
                .paymentReferenceId(newOrder.getPaymentReferenceId())
                .paymentStatus(newOrder.getPaymentStatus())
                .orderStatus(newOrder.getOrderStatus())
                .email(newOrder.getEmail())
                .phoneNumber(newOrder.getPhoneNumber())
                .build();
    }

    private OrderEntity convertToEntity(OrderRequest request) {
        return OrderEntity.builder()
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .orderItems(request.getOrderItems())
                .email(request.getEmail())
                .paymentReferenceId(request.getPaymentReferenceId())
                .phoneNumber(request.getPhoneNumber())
                .orderStatus(request.getOrderStatus())
                .build();
    }
}
