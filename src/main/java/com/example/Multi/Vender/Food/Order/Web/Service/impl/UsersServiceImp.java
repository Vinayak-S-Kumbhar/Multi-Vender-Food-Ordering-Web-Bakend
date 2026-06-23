package com.example.Multi.Vender.Food.Order.Web.Service.impl;

import com.example.Multi.Vender.Food.Order.Web.Dtos.*;
import com.example.Multi.Vender.Food.Order.Web.Entity.Order;
import com.example.Multi.Vender.Food.Order.Web.Entity.Restorent;
import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import com.example.Multi.Vender.Food.Order.Web.Repository.UsersRepository;
import com.example.Multi.Vender.Food.Order.Web.Service.UsersService;
import com.example.Multi.Vender.Food.Order.Web.config.OrderStatus;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsersServiceImp implements UsersService {

    private final UsersRepository usersRepository;
    private final AddressMapper addressMapper;

    @Override
    public UserInfoDto getUserInfo(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with this id : " + userId));

        return new UserInfoDto(user.getId(),user.getName(),user.getUsername(),user.getUserRole(),user.getCart().size());

    }


    @Override
    public List<UsersInfoForOwnerDto> getAllUsers() {

        List<User> userList = usersRepository.findAll();

        return userList.stream()
                .sorted((u1, u2) ->
                        Integer.compare(
                                u2.getOrderList().size(),
                                u1.getOrderList().size()
                        )
                )
                .map(user -> {

                    double totalSpend = user.getOrderList()
                            .stream()
                            .filter(order -> order.getOrderStatus() == OrderStatus.DELIVERED)
                            .mapToDouble(Order::getTotalAmount)
                            .sum();

                    return UsersInfoForOwnerDto.builder()
                            .userId(user.getId())
                            .customerName(user.getName())
                            .email(user.getUsername())
                            .city(user.getAddresses().isEmpty()
                                    ? null
                                    : user.getAddresses().getFirst().getCity())
                            .registerdAt(user.getRegisterdAt())
                            .totalOrders(user.getOrderList().size())
                            .userRole(user.getUserRole())
                            .totalSpend(totalSpend)
                            .build();
                })
                .toList();
    }

    @Override
    public String deleteUser(long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with this id : " + userId));

        usersRepository.delete(user);
        return "User Deleted Successfully!";
    }

    @Override
    public String updateUserRolll(long userId,UserRole userRole) {
        User user = usersRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with this id : " + userId));

        if(user.getUserRole() == UserRole.VENDER || user.getUserRole() == UserRole.OWNER){
            throw new RuntimeException("This User is a " + user.getUserRole() + " So we cant chenge her roal");
        }

        if(userRole == UserRole.OWNER || userRole == UserRole.VENDER){
            throw new RuntimeException("You can't make User to " + userRole + " directly");
        }

        if(user.getUserRole() == userRole){
            return "User already " + userRole;
        }
        user.setUserRole(userRole);
        usersRepository.save(user);
        return "User role updated successfully to " + userRole;
    }

    @Override
    public SingleUserInfoForOwnerDto getUserInfoForOwner(long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with this id : " + userId));

        List<Restorent> restorentList = user.getRestorentList();
        List<RestorentResDto> restorentInfo = restorentList.stream().map(restorent ->{
                return RestorentResDto.builder()
                        .id(restorent.getId())
                        .userId(restorent.getUser().getId())
                        .restorentName(restorent.getRestorentName())
                        .status(restorent.getStatus())
                        .address(addressMapper.toDto(restorent.getAddress()))
                        .build();

        }).toList();

        UsersInfoForOwnerDto usersInfoForOwnerDto = UsersInfoForOwnerDto.builder()
                .userId(user.getId())
                .customerName(user.getName())
                .email(user.getUsername())
                .registerdAt(user.getRegisterdAt())
                .totalOrders(user.getOrderList().size())
                .userRole(user.getUserRole())
                .address(user.getAddresses().stream().map(addressMapper::toDto).toList())
                .build();

        return new SingleUserInfoForOwnerDto(usersInfoForOwnerDto,restorentInfo);
    }

}
