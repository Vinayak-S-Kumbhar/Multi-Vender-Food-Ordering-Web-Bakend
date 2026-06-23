package com.example.Multi.Vender.Food.Order.Web.Controller;

import com.example.Multi.Vender.Food.Order.Web.Dtos.*;
import com.example.Multi.Vender.Food.Order.Web.Service.OrderService;
import com.example.Multi.Vender.Food.Order.Web.config.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody OrderDto orderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(orderDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserOrdersResDto>> getUserOrders(@PathVariable long userId){
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/restorent/{restorentId}")
    public ResponseEntity<List<OrderResponceDto>> getAllRestorentOrders(@PathVariable long restorentId){
        return ResponseEntity.ok(orderService.getAllRestorentOrders(restorentId));
    }
    @GetMapping("/all")
    public ResponseEntity<List<OrderRespForOwner>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size){
        return ResponseEntity.ok(orderService.getAllOrders(page,size));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<SingleOrderDetailsResDto> getSingleOrderDetailsForOwner(@PathVariable long orderId){
        return ResponseEntity.ok(orderService.getSingleOrderDetailsForOwner(orderId));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(orderService.updateOrderStatus(orderId,status));
    }
    @GetMapping("/info/{userId}")
    public ResponseEntity<List<OrderInfoDto>> getUserOrderInfo(@PathVariable long userId){
        return ResponseEntity.ok(orderService.getUserOrderInfo(userId));
    }
}



