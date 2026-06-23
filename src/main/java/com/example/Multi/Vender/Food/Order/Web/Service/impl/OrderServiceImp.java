package com.example.Multi.Vender.Food.Order.Web.Service.impl;

import com.example.Multi.Vender.Food.Order.Web.Dtos.*;
import com.example.Multi.Vender.Food.Order.Web.Entity.*;
import com.example.Multi.Vender.Food.Order.Web.Repository.*;
import com.example.Multi.Vender.Food.Order.Web.Service.OrderService;
import com.example.Multi.Vender.Food.Order.Web.config.OrderStatus;
import com.example.Multi.Vender.Food.Order.Web.config.PaymentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.AddressMapper;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final UsersRepository usersRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestorentRepository restorentRepository;
    private final CartRepository cartRepository;
    private final AddressMapper addressMapper;

    @Override
    @Transactional
    public String addOrder(OrderDto orderDto) {

        User user = usersRepository.findById(orderDto.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found"));

        if(user.getUserRole() == UserRole.BLOCKED){
            throw new RuntimeException("User is Blocked");
        }

        Restorent restorent = restorentRepository.findById(orderDto.getRestorentId()).orElseThrow(() ->
                new RuntimeException("Hotel not found with this Id : " + orderDto.getRestorentId()));

        if(restorent.getStatus() != RestorentStatus.APPROVED){
            throw new RuntimeException("This Restorent is " + restorent.getStatus() + "So you cant buy food from this Restorent Please try from diffrent Restorent");
        }

        Address address = user.getAddresses().stream().filter(address1 -> address1.getIsDefault() == true)
                .findFirst().orElseThrow(() -> new RuntimeException("Please add an Address"));

        if (user.getCart().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = Order.builder()
                .deliveryFee(orderDto.getDeliveryFee())
                .note(orderDto.getNote())
                .totalAmount(orderDto.getTotalAmount())
                .savings(orderDto.getSavings())
                .orderStatus(OrderStatus.PLACED)
                .paymentStatus(PaymentStatus.PENDING)
                .dateTime(LocalDateTime.now())
                .adress(address)
                .user(user)
                .restorent(restorent)
                .build();

        List<Cart> cartList = user.getCart().stream().filter(cart -> Objects.equals(
                cart.getRestorentId(),
                orderDto.getRestorentId())).toList();

        if(cartList.isEmpty())
            throw new RuntimeException("No cart items found for selected restaurant");

        List<OrderItems> orderItemsList = cartList.stream().map(cart1 -> OrderItems.builder()
                .foodItem(cart1.getFoodItem())
                .order(order)
                .price(cart1.getFoodItem().getPrice())
                .quantity(cart1.getQuantity()).build()
        ).toList();

        order.setOrderItems(orderItemsList);

        orderRepository.save(order);

        user.getCart().removeAll(cartList);
        return "Order Placed Successfully";
    }

    @Override
    public List<UserOrdersResDto> getUserOrders(long userId) {

         usersRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with this user id : " + userId));

        List<Order> orderList = orderRepository.findByUserIdOrderByDateTimeDesc(userId);

        return orderList.stream()
                .map(order -> {

                    List<FoodItemDto> foodList = order.getOrderItems()
                            .stream()
                            .map(orderItem -> {
                                FoodItem food = orderItem.getFoodItem();

                                FoodItemDto dto = new FoodItemDto();
                                dto.setId(food.getId());
                                dto.setFoodname(food.getFoodname());
                                dto.setDescription(food.getDescription());
                                dto.setFoodCategery(food.getFoodCategery());
                                dto.setPrice(food.getPrice());
                                dto.setRestorentId(food.getRestorent().getId());

                                return dto;
                            })
                            .toList();

                    return UserOrdersResDto.builder()
                            .orderId(order.getId())
                            .restorentId(order.getRestorent().getId())
                            .restorentName(order.getRestorent().getRestorentName()) // change getter if needed
                            .orderAddress(addressMapper.toDto(order.getAdress()))
                            .foodList(foodList)
                            .dateTime(order.getDateTime())
                            .totalAmount(order.getTotalAmount())
                            .orderStatus(order.getOrderStatus())
                            .deliveryFee(order.getDeliveryFee())
                            .note(order.getNote())
                            .paymentStatus(order.getPaymentStatus())
                            .build();
                })
                .toList();
    }
    @Override
    public List<OrderResponceDto> getAllRestorentOrders(long restorentId) {
        Restorent restorent = restorentRepository.findById(restorentId).orElseThrow(() ->
                new RuntimeException("Restorent not Found With this id : " + restorentId));

        List<Order> orderList = restorent.getOrderList();

         return orderList.stream().map(order -> {
             List<OrderItemsDto> orderItemsDto = order.getOrderItems().stream().map(orderItem -> {
                 if(orderItem.getFoodItem().getRestorent().getId() == restorentId) {
                     return OrderItemsDto.builder()
                             .foodId(orderItem.getFoodItem().getId())
                             .foodName(orderItem.getFoodItem().getFoodname())
                             .quantity(orderItem.getQuantity())
                             .price(orderItem.getPrice())
                             .build();
                 }
                 return null;
             }).filter(Objects::nonNull).toList();

            return OrderResponceDto.builder()
                    .orderId(order.getId())
                    .addressResponse(addressMapper.toDto(order.getAdress()))
                    .totalAmount(order.getTotalAmount())
                    .orderStatus(order.getOrderStatus())
                    .paymentStatus(order.getPaymentStatus())
                    .note(order.getNote())
                    .orderTime(order.getDateTime())
                    .restorentStatus(restorent.getStatus())
                    .itemList(orderItemsDto)
                    .build();
        }).toList();

    }

    @Override
    public List<OrderRespForOwner> getAllOrders(int page,int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "dateTime"));

        Page<Order> orderList = orderRepository.findAll(pageable);

        return orderList.stream().map(order -> {
            List<OrderItemsDto> orderItemsDto = order.getOrderItems().stream().map(orderItem -> {
                return OrderItemsDto.builder()
                    .foodId(orderItem.getFoodItem().getId())
                    .foodName(orderItem.getFoodItem().getFoodname())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .build();

            }).toList();
            return OrderRespForOwner.builder()
                    .orderId(order.getId())
                    .restorentId(order.getRestorent().getId())
                    .userId(order.getUser().getId())
                    .customerName(order.getUser().getName())
                    .venderName(order.getRestorent().getOwnerName())
                    .totalAmount(order.getTotalAmount())
                    .orderStatus(order.getOrderStatus())
                    .time(order.getDateTime())
                    .city(order.getAdress().getCity())
                    .orderItems(orderItemsDto).build();
        }).toList();
    }

    @Override
    public SingleOrderDetailsResDto getSingleOrderDetailsForOwner(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new RuntimeException("Order not fund with this id : " + orderId));

        List<OrderItemsDto> orderItemsDto = order.getOrderItems().stream().map(orderItem ->
                OrderItemsDto.builder()
                        .foodId(orderItem.getFoodItem().getId())
                        .foodName(orderItem.getFoodItem().getFoodname())
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPrice())
                        .build()
        ).toList();

        OrderResponceDto orderResponceDto = OrderResponceDto.builder()
                .orderId(order.getId())
                .addressResponse(addressMapper.toDto(order.getAdress()))
                .totalAmount(order.getTotalAmount())
                .savings(order.getSavings())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .note(order.getNote())
                .orderTime(order.getDateTime())
                .itemList(orderItemsDto)
                .build();

        UserInfoDto userInfo =  new UserInfoDto(order.getUser().getId(),order.getUser().getName(),order.getUser().getUsername(),order.getUser().getUserRole(),order.getUser().getCart().size());

        VendorDto vendorDto = VendorDto.builder()
                .venderId(order.getRestorent().getId())
                .ownerName(order.getRestorent().getOwnerName())
                .restorentName(order.getRestorent().getRestorentName())
                .mobileNumber(order.getRestorent().getMobileNumber())
                .email(order.getRestorent().getEmail())
                .businessType(order.getRestorent().getBusinessType())
                .totalRatings(order.getRestorent().getTotalRatings())
                .averageRating(order.getRestorent().getAverageRating())
                .address(addressMapper.toDto(order.getRestorent().getAddress())).build();

        return new SingleOrderDetailsResDto(orderResponceDto,userInfo,vendorDto);
    }

    @Override
    public String updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getOrderStatus() == OrderStatus.DELIVERED){
            throw new RuntimeException("Order is DELIVERED! we can't Change it");
        }

        if(order.getOrderStatus() == status){
            return "Status changed Successfully to : " + status;
        }
        if(status == OrderStatus.DELIVERED){
            throw new RuntimeException("You can't set status DELIVERED");
        }
        order.setOrderStatus(status);

        Order savedOrder = orderRepository.save(order);
        return "Status changed Successfully to : " + savedOrder.getOrderStatus();
    }

    @Override
    public List<OrderInfoDto> getUserOrderInfo(long userId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orderList = orderRepository.findByUserIdOrderByDateTimeDesc(userId);
        return orderList.stream().map(order -> {
            return new OrderInfoDto(order.getId(),order.getRestorent().getRestorentName(),order.getOrderItems().size(),order.getTotalAmount(),order.getOrderStatus(),order.getDateTime());
        }).toList();
    }
}
