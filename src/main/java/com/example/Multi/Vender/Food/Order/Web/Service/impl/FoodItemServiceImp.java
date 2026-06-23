package com.example.Multi.Vender.Food.Order.Web.Service.impl;

import com.example.Multi.Vender.Food.Order.Web.Dtos.FoodItemDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.FoodItemResDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import com.example.Multi.Vender.Food.Order.Web.Entity.Restorent;
import com.example.Multi.Vender.Food.Order.Web.Repository.FoodItemRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.RestorentRepository;
import com.example.Multi.Vender.Food.Order.Web.Service.FoodItemService;
import com.example.Multi.Vender.Food.Order.Web.Service.SupabaseStorageService;
import com.example.Multi.Vender.Food.Order.Web.config.FoodCategery;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.FoodItemMapper;
import com.example.Multi.Vender.Food.Order.Web.config.mapper.FoodType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FoodItemServiceImp implements FoodItemService {

    private final RestorentRepository restorentRepository;
    private final FoodItemRepository foodItemRepository;
    private final FoodItemMapper foodItemMapper;
    private final SupabaseStorageService storageService;

    @Override
    public String addFoodItem(String foodname, String description, FoodCategery foodCategory, FoodType foodType, double price, long restaurantId, MultipartFile file) throws Exception {
        Restorent restorent = restorentRepository.findById(restaurantId).orElseThrow(() ->
                new RuntimeException("Restorent not found with this Id : " + restaurantId));

        if(restorent.getStatus() != RestorentStatus.APPROVED){
            throw new RuntimeException("This Restorent is " + restorent.getStatus() + " so you cant add food in this restorent");
        }

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            throw new RuntimeException("Only image files are allowed");
        }
        if(foodItemRepository.existsByFoodnameAndRestorentId(foodname,restaurantId)){
            throw new RuntimeException("Food item allredy exist");
        }

        String imageUrl =
                storageService.uploadFile(file);

        FoodItem foodItem = FoodItem.builder()
                .foodname(foodname)
                .description(description)
                .foodCategery(foodCategory)
                .foodType(foodType)
                .price(price)
                .createdAt(LocalDateTime.now())
                .restorent(restorent)
                .imageUrl(imageUrl).build();

        foodItemRepository.save(foodItem);

        return "Food Item added Successfully";
    }

    @Override
    public List<FoodItemResDto> getAllFoodItmes(int page, int size) {

        Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.ASC, "createdAt"));

        return foodItemRepository
                .findByIsAvailableTrueAndRestorent_Status(
                        RestorentStatus.APPROVED,
                        pageable
                )
                .stream()
                .map(item -> new FoodItemResDto(
                        foodItemMapper.toDto(item),
                        item.getRestorent().getRestorentName()
                ))
                .toList();
    }


    @Override
    public String deleteFoodItem(long id) {
        FoodItem foodItem = foodItemRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Food Item is not found with this id : " + id));

        foodItemRepository.deleteById(id);
        return "food Item Deleted Successfully";
    }

    @Override
    public List<FoodItemDto> getAllFoodByHotelId(long hotelId) {
        List<FoodItem> foodItemList = foodItemRepository.findByRestorentId(hotelId);
        return foodItemList.stream().map(foodItemMapper::toDto).toList();
    }

    @Override
    public List<FoodItemDto> getAllFoodByCategery(FoodCategery categery,int page, int size) {

        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.ASC, "createdAt"));

        Page<FoodItem> foodItemList = foodItemRepository.findByFoodCategeryAndIsAvailableTrue(categery,pageable);
        return foodItemList.getContent().stream().map(item -> {
            if(item.isAvailable() && item.getRestorent().getStatus() == RestorentStatus.APPROVED){
                return foodItemMapper.toDto(item);
            }
            return null;
        }).filter(Objects::nonNull).toList();
    }

    @Override
    public FoodItemDto getFoodItemById(long foodId) {
        FoodItem foodItem = foodItemRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food not found with this Id : " + foodId));
        return foodItemMapper.toDto(foodItem);
    }

    @Override
    public boolean updateIsAvailable(long foodId) {
        FoodItem foodItem = foodItemRepository.findById(foodId).orElseThrow(() ->
                new RuntimeException("Food not found with this Id : " + foodId));

        if(foodItem.getRestorent().getStatus() != RestorentStatus.APPROVED){
            throw new RuntimeException("The restorent is " + foodItem.getRestorent().getStatus() + " so you can't chenge the Availablity status");
        }

        foodItem.setAvailable(!foodItem.isAvailable());
        foodItemRepository.save(foodItem);
        return foodItem.isAvailable();
    }

    public List<FoodItemResDto> getAllFoodItmesForOwner(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return foodItemRepository
                .findAll(pageable)
                .stream()
                .map(item -> new FoodItemResDto(
                        foodItemMapper.toDto(item),
                        item.getRestorent().getRestorentName()
                ))
                .toList();
    }


}
