package org.example.deliverysystemapi.converters;

import org.example.deliverysystemapi.dto.CreateOrderResponse;
import org.example.deliverysystemapi.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {

    private OrderConverter() {
    }

    public static CreateOrderResponse convertOrderToCreateOrderResponse(Order order) {
        CreateOrderResponse createOrderResponse = null;
        if (order != null) {
            createOrderResponse = new CreateOrderResponse();
            createOrderResponse.setId(order.getId());
            createOrderResponse.setOrderDate(order.getOrderDate());
            createOrderResponse.setStatus(order.getStatus());
            createOrderResponse.setDeliveryDate(order.getDeliveryDate());
            createOrderResponse.setDeliveryAddress(order.getDeliveryAddress());
            createOrderResponse.setPickupAddress(order.getPickupAddress());
            createOrderResponse.setCustomerId(order.getCustomer().getId());
        }
        return createOrderResponse;
    }

    public static List<CreateOrderResponse> convertOrdersToCreateOrderResponses(List<Order> orders) {
        List<CreateOrderResponse> createOrderResponses = null;
        if (orders != null) {
            createOrderResponses = new ArrayList<>();
            for (Order order : orders) {
                createOrderResponses.add(convertOrderToCreateOrderResponse(order));
            }
        }
        return createOrderResponses;
    }
}
