package com.berkcankarabulut.SpringEcom.service;

import com.berkcankarabulut.SpringEcom.model.Order;
import com.berkcankarabulut.SpringEcom.model.OrderItem;
import com.berkcankarabulut.SpringEcom.model.Product;
import com.berkcankarabulut.SpringEcom.model.dto.OrderItemRequest;
import com.berkcankarabulut.SpringEcom.model.dto.OrderItemResponse;
import com.berkcankarabulut.SpringEcom.model.dto.OrderRequest;
import com.berkcankarabulut.SpringEcom.model.dto.OrderResponse;
import com.berkcankarabulut.SpringEcom.repo.OrderRepo;
import com.berkcankarabulut.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrderResponses() {
        List<Order> orders = orderRepo.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItemResponse> itemResponses = new ArrayList<>();
            for(OrderItem item : order.getOrderItems()) {
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                itemResponses.add(orderItemResponse);
            }
            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            );
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        Order order = createOrder(request);
        List<OrderItem> orderItems = processOrderItems(request.items(), order);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepo.save(order);
        return buildOrderResponse(savedOrder);
    }

    private Order createOrder(OrderRequest request) {
        String orderId = generateOrderId();
        return Order.builder()
                .orderId(orderId)
                .customerName(request.customerName())
                .email(request.email())
                .status("PLACED")
                .orderDate(LocalDate.now())
                .build();
    }

    private String generateOrderId() {
        return "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private List<OrderItem> processOrderItems(List<OrderItemRequest> itemRequests, Order order) {
        return itemRequests.stream()
                .map(itemReq -> createOrderItem(itemReq, order))
                .toList();
    }

    private OrderItem createOrderItem(OrderItemRequest itemRequest, Order order) {
        Product product = findAndUpdateProduct(itemRequest);
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity()));

        return OrderItem.builder()
                .product(product)
                .quantity(itemRequest.quantity())
                .totalPrice(totalPrice)
                .order(order)
                .build();
    }

    private Product findAndUpdateProduct(OrderItemRequest itemRequest) {
        Product product = productRepo.findById(itemRequest.productId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.productId()));

        validateStock(product, itemRequest.quantity());
        updateProductStock(product, itemRequest.quantity());
        return product;
    }

    private void validateStock(Product product, int requestedQuantity) {
        if (product.getStockQuantity() < requestedQuantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }
    }

    private void updateProductStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepo.save(product);
    }

    private OrderResponse buildOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(this::mapToOrderItemResponse)
                .toList();

        return new OrderResponse(
                order.getOrderId(),
                order.getCustomerName(),
                order.getEmail(),
                order.getStatus(),
                order.getOrderDate(),
                itemResponses
        );
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getTotalPrice()
        );
    }
}