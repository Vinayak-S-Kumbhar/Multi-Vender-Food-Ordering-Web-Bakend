package com.example.Multi.Vender.Food.Order.Web.Service.impl;

import com.example.Multi.Vender.Food.Order.Web.Dtos.*;
import com.example.Multi.Vender.Food.Order.Web.Entity.*;
import com.example.Multi.Vender.Food.Order.Web.Repository.FoodItemRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.RestorentRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.UsersRepository;
import com.example.Multi.Vender.Food.Order.Web.Service.RestorentService;
import com.example.Multi.Vender.Food.Order.Web.config.OrderStatus;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.AddressMapper;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.FoodItemMapper;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.RestorentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestorentImp implements RestorentService {

    private final RestorentRepository restorentRepository;
    private final RestorentMapper restorentMapper;
    private final UsersRepository usersRepository;
    private final FoodItemRepository foodItemRepository;
    private final FoodItemMapper foodItemMapper;
    private final AddressMapper addressMapper;

    @Override
    @Transactional
    public String addRestorent(AddRestorentDto restorentDto) {


        User user = usersRepository.findById(restorentDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found. Please login first."));

        if (user.getUserRole() == UserRole.BLOCKED) {
            throw new RuntimeException("This user is blocked.");
        }

        Address address = user.getAddresses()
                .stream()
                .filter(a -> Boolean.TRUE.equals(a.getIsDefault()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Please add restaurant address first."));

        if (restorentRepository.existsByEmail(restorentDto.getEmail())) {
            throw new RuntimeException("Restaurant already registered with this email.");
        }

        if (restorentRepository.existsByFssaiNumber(restorentDto.getFssaiNumber())) {
            throw new RuntimeException("Restaurant already registered with this FSSAI number.");
        }

        if (restorentDto.getFssaiExpiry() != null &&
                restorentDto.getFssaiExpiry().isBefore(LocalDate.now())) {
            throw new RuntimeException("FSSAI license has expired.");
        }

        Restorent restorent = restorentMapper.toEntity(restorentDto);
        restorent.setUser(user);
        restorent.setAddress(address);
        restorent.setStatus(RestorentStatus.PENDING);
        restorent.setCreatedAt(LocalDateTime.now());


        restorentRepository.save(restorent);

        return "Restaurant registration submitted successfully. Please wait for approval.";
    }

    @Override
    public List<RestorentInfoDto> getUserRestorents(long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not Found with this Id : " +userId));

        List<Restorent> restorentList = user.getRestorentList();
        return restorentList.stream().map(restorent ->{
            if(restorent.getStatus() != RestorentStatus.PENDING){
                return new RestorentInfoDto(restorent.getId(),restorent.getUser().getId(),restorent.getRestorentName(),restorent.getStatus(),addressMapper.toDto(restorent.getAddress()));

            }
            return null;
        }).filter(Objects::nonNull).toList();
    }

    @Override
    public RestorentWithFoodListDto getRestorentAndFoodListFromFoodId(long foodId) {
        FoodItem foodItem = foodItemRepository.findById(foodId).orElseThrow(() ->
                new RuntimeException("Food no found with this id : " + foodId));
        Restorent restorent = foodItem.getRestorent();

        if(restorent.getStatus() != RestorentStatus.APPROVED){
            throw new RuntimeException("This Restorent is " + restorent.getStatus() + "! Please try some other food");
        }
        List<FoodItemDto> foodItemDtoList = restorent.getFoodItemList().stream().map(food ->{
            if(food.isAvailable()){
                return foodItemMapper.toDto(food);
            }
            return null;
        }).filter(Objects::nonNull).toList();
        AddressResponseDto addressResponseDto = addressMapper.toDto(restorent.getAddress());

        return RestorentWithFoodListDto.builder()
                .id(restorent.getId())
                .restorentName(restorent.getRestorentName())
                .businessType(restorent.getBusinessType())
                .productCategerys(restorent.getProductCategerys())
                .yearOfEstablish(restorent.getYearOfEstablish())
                .ownerName(restorent.getOwnerName())
                .mobileNumber(restorent.getMobileNumber())
                .alternativeMobNumber(restorent.getAlternativeMobNumber())
                .email(restorent.getEmail())
                .averageRating(restorent.getAverageRating())
                .totalRatings(restorent.getTotalRatings())
                .createdAt(restorent.getCreatedAt())
                .status(restorent.getStatus())
                .address(addressResponseDto)
                .foodItemList(foodItemDtoList)
                .build();
    }

    @Override
    public List<RestorentDto> getAllRestorents() {
        List<Restorent> restorentList = restorentRepository.findByStatus(RestorentStatus.APPROVED);
        restorentList.sort(Comparator.comparingInt((Restorent resrorent) -> resrorent.getOrderList().size()).reversed());
        return restorentList.stream().map(restorentMapper::toDto).toList();
    }

    @Override
    public RestorentWithFoodListDto getRestorentAndFoodListFromRestorentId(long restorentId) {
        Restorent restorent = restorentRepository.findById(restorentId).orElseThrow(() ->
                new RuntimeException("RestorentNot Found With This Id : " + restorentId));

        List<FoodItemDto> foodItems = restorent.getFoodItemList().stream().map(foodItemMapper::toDto).toList();
        AddressResponseDto RestorentAddress = addressMapper.toDto(restorent.getAddress());

        List<Order> orderList = restorent.getOrderList().stream().filter(order -> order.getOrderStatus().equals(OrderStatus.DELIVERED)).toList();

        return RestorentWithFoodListDto.builder()
                .id(restorent.getId())
                .restorentName(restorent.getRestorentName())
                .businessType(restorent.getBusinessType())
                .productCategerys(restorent.getProductCategerys())
                .yearOfEstablish(restorent.getYearOfEstablish())
                .ownerName(restorent.getOwnerName())
                .mobileNumber(restorent.getMobileNumber())
                .alternativeMobNumber(restorent.getAlternativeMobNumber())
                .email(restorent.getEmail())
                .averageRating(restorent.getAverageRating())
                .totalRatings(restorent.getTotalRatings())
                .status(restorent.getStatus())
                .totalOrders(orderList.size())
                .address(RestorentAddress)
                .foodItemList(foodItems).build();
    }

    @Override
    @Transactional
    public String updateRestorentStatus(long restorentId,RestorentStatus status) {
        Restorent restorent = restorentRepository.findById(restorentId).orElseThrow(() ->
                new RuntimeException("Restorent not found with this id : " + restorentId));


        if(restorent.getStatus() == status){
            return "Restorent Allredy " + status;
        }
        restorent.setStatus(status);

        User user = restorent.getUser();

        boolean hasVendorRestaurant =  user.getRestorentList().stream().anyMatch(r ->
                r.getStatus() == RestorentStatus.APPROVED || r.getStatus() == RestorentStatus.SUSPENDED);


        if (hasVendorRestaurant) {
            user.setUserRole(UserRole.VENDER);
        } else {
            user.setUserRole(UserRole.CUSTOMER);
        }

        return "Restorent Status Updated Successfully";
    }

    @Override
    public List<RestorentDto> getAllRestorentsForOwner() {
        List<Restorent> restorentList = restorentRepository.findAll();
        restorentList.sort(Comparator.comparingInt((Restorent resrorent) -> resrorent.getOrderList().size()).reversed());
        return restorentList.stream().map(restorentMapper::toDto).toList();
    }

    @Override
    public List<RequestedRestorentDto> getUserAllRestorents(long userId) {
        usersRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with this id : " + userId));

        List<Restorent> restorentList = restorentRepository.findByUserIdOrderByCreatedAtDesc(userId);

        return restorentList.stream().map(restorent -> new RequestedRestorentDto(restorentMapper.toAddRestorentDto(restorent),addressMapper.toDto(restorent.getAddress()),restorent.getStatus())).toList();
    }
}
