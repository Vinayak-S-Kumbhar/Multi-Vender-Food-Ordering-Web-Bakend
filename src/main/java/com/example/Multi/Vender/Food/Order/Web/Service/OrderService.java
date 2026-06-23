package com.example.Multi.Vender.Food.Order.Web.Service;

import com.example.Multi.Vender.Food.Order.Web.Dtos.*;
import com.example.Multi.Vender.Food.Order.Web.config.OrderStatus;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    String addOrder(OrderDto orderDto);

    List<UserOrdersResDto> getUserOrders(long userId);

    List<OrderResponceDto> getAllRestorentOrders(long restorentId);

    List<OrderRespForOwner> getAllOrders(int page,int size);

    SingleOrderDetailsResDto getSingleOrderDetailsForOwner(long orderId);

    String updateOrderStatus(Long orderId, OrderStatus status);

    List<OrderInfoDto> getUserOrderInfo(long userId);
}
