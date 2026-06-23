package com.example.Multi.Vender.Food.Order.Web.Service.impl;

import com.example.Multi.Vender.Food.Order.Web.Dtos.AddFoodRetingDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.AddRetorentRatingDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.RatingsDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.RatingsResponceDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.FoodItem;
import com.example.Multi.Vender.Food.Order.Web.Entity.Ratings;
import com.example.Multi.Vender.Food.Order.Web.Entity.Restorent;
import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import com.example.Multi.Vender.Food.Order.Web.Repository.FoodItemRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.RatingsRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.RestorentRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.UsersRepository;
import com.example.Multi.Vender.Food.Order.Web.Service.RatingsService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingsServiceImp implements RatingsService {
    private final RatingsRepository retingsRepository;
    private final UsersRepository usersRepository;
    private final FoodItemRepository foodItemRepository;
    private final RestorentRepository restorentRepository;

    @Override
    @Transactional
    public String addFoodReting(AddFoodRetingDto addFoodRetingDto) {

        User user = usersRepository.findById(addFoodRetingDto.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found with this User id : " +addFoodRetingDto.getUserId()));

        FoodItem foodItem = foodItemRepository.findById(addFoodRetingDto.getFoodId()).orElseThrow(() ->
                new RuntimeException("Food not found with this food id : " +addFoodRetingDto.getFoodId()));

        Ratings retings = retingsRepository.findByUserAndFoodItem(user,foodItem).orElse(null);

        if(retings != null){
            if(retings.getRatings() == addFoodRetingDto.getRating() &&
                    retings.getReview().equals(addFoodRetingDto.getReview())){
                return "Thank you! Your rating has already been submitted ";
            }
            double oldRatings = retings.getRatings();
            retings.setRatings(addFoodRetingDto.getRating());
            retings.setReview(addFoodRetingDto.getReview());
            retings.setCreatedAt(LocalDateTime.now());
            retingsRepository.save(retings);

            double totalScore =
                    foodItem.getAverageRating()
                            * foodItem.getTotalRatings();

            totalScore = totalScore - oldRatings
                            + addFoodRetingDto.getRating();


            foodItem.setAverageRating(totalScore / foodItem.getTotalRatings());
            foodItemRepository.save(foodItem);
            return "Thank you for Rate this food";
        }

        Ratings retings1 = new Ratings();
        retings1.setRatings(addFoodRetingDto.getRating());
        retings1.setReview(addFoodRetingDto.getReview());
        retings1.setUser(user);
        retings1.setFoodItem(foodItem);
        retings1.setCreatedAt(LocalDateTime.now());

        retingsRepository.save(retings1);

        double totalScore = foodItem.getAverageRating() * foodItem.getTotalRatings();

        totalScore += addFoodRetingDto.getRating();
        foodItem.setTotalRatings(foodItem.getTotalRatings() + 1);
        foodItem.setAverageRating(totalScore / foodItem.getTotalRatings());
        foodItemRepository.save(foodItem);
        return "Thank you for Rate this food";
    }

    @Override
    @Transactional
    public String addRestorentReting(AddRetorentRatingDto addRetorentRatingDto) {

        User user = usersRepository.findById(addRetorentRatingDto.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found with this User id : " +addRetorentRatingDto.getUserId()));

        Restorent restorent = restorentRepository.findById(addRetorentRatingDto.getRestorentId()).orElseThrow(() ->
                new RuntimeException("Restorent not found with this Restorent id : " +addRetorentRatingDto.getRestorentId()));

        Ratings retings = retingsRepository.findByUserAndRestorent(user,restorent).orElse(null);

        if(retings != null){
            if(retings.getRatings() == addRetorentRatingDto.getRating() &&
            retings.getReview().equals(addRetorentRatingDto.getReview())){
                return "Thank you! Your rating has already been submitted ";
            }
            double oldRatings = retings.getRatings();
            retings.setRatings(addRetorentRatingDto.getRating());
            retings.setReview(addRetorentRatingDto.getReview());
            retings.setCreatedAt(LocalDateTime.now());
            retingsRepository.save(retings);

            double totalScore =
                    restorent.getAverageRating()
                            * restorent.getTotalRatings();

            totalScore = totalScore - oldRatings
                    + addRetorentRatingDto.getRating();


            restorent.setAverageRating(totalScore / restorent.getTotalRatings());
            restorentRepository.save(restorent);
            return "Thank you for Rate this Restorent";
        }

        Ratings retings1 = new Ratings();
        retings1.setRatings(addRetorentRatingDto.getRating());
        retings1.setReview(addRetorentRatingDto.getReview());
        retings1.setUser(user);
        retings1.setRestorent(restorent);
        retings1.setCreatedAt(LocalDateTime.now());

        retingsRepository.save(retings1);

        double totalScore = restorent.getAverageRating() * restorent.getTotalRatings();

        totalScore += addRetorentRatingDto.getRating();
        restorent.setTotalRatings(restorent.getTotalRatings() + 1);
        restorent.setAverageRating(totalScore / restorent.getTotalRatings());
        restorentRepository.save(restorent);
        return "Thank you for Rate this Restorent";
    }

    @Override
    public RatingsResponceDto getFoodRatings(long foodId,int page ,int size) {
        FoodItem foodItem = foodItemRepository.findById(foodId).orElseThrow(() ->
                new RuntimeException("Food Not Found With this Id : "+ foodId));
        List<Ratings> ratingsList = foodItem.getRetings();

        int[] arr = {0,0,0,0,0};
        ratingsList.forEach(rating -> {
            if(rating.getRatings() == 1) arr[0] += 1;
            if(rating.getRatings() == 2) arr[1] += 1;
            if(rating.getRatings() == 3) arr[2] += 1;
            if(rating.getRatings() == 4) arr[3] += 1;
            if(rating.getRatings() == 5) arr[4] += 1;
        });

        int totalPages = (int) Math.ceil((double) ratingsList.size() / size);

        boolean last = page >= totalPages - 1;

        if(ratingsList.isEmpty()){
            return new RatingsResponceDto(0.0,0, Collections.emptyList(),page,
                    totalPages,
                    true,arr[0], arr[1],arr[2], arr[3],arr[4]);
        }

        List<Ratings> SortedratingsList = ratingsList.stream().sorted(Comparator.comparing(Ratings::getCreatedAt).reversed()).toList();

        int start = page * size;

        if (start >= ratingsList.size()) {
            return new RatingsResponceDto(
                    foodItem.getAverageRating(),
                    foodItem.getTotalRatings(),
                    Collections.emptyList(),
                    page,
                    totalPages,
                    true
                    ,arr[0], arr[1],arr[2], arr[3],arr[4]
            );
        }
        int end = Math.min(start+size,ratingsList.size());


        List<RatingsDto> ratingsListDto = SortedratingsList.subList(start,end).stream().map(ratings -> RatingsDto.builder()
                .ratingId(ratings.getId())
                .ratings(ratings.getRatings())
                .review(ratings.getReview())
                .name(ratings.getUser().getName())
                .createdAt(ratings.getCreatedAt())
                .build()).toList();


        return new RatingsResponceDto(foodItem.getAverageRating(),foodItem.getTotalRatings(),ratingsListDto,page,totalPages,last,arr[0], arr[1],arr[2], arr[3],arr[4]);
    }

    @Override
    public RatingsResponceDto getRetorentRatings(long restorentId,int page,int size) {
        Restorent restorent = restorentRepository.findById(restorentId).orElseThrow(() ->
                new RuntimeException("Retorent Not Found With this Id : "+ restorentId));
        List<Ratings> ratingsList = restorent.getRetings();

        int[] arr = {0,0,0,0,0};
        ratingsList.forEach(rating -> {
            if(rating.getRatings() == 1) arr[0] += 1;
            if(rating.getRatings() == 2) arr[1] += 1;
            if(rating.getRatings() == 3) arr[2] += 1;
            if(rating.getRatings() == 4) arr[3] += 1;
            if(rating.getRatings() == 5) arr[4] += 1;

        });

        int totalPages = (int) Math.ceil((double) ratingsList.size() / size);

        boolean last = page >= totalPages - 1;

        if(ratingsList.isEmpty()){
            return new RatingsResponceDto(0.0,0, Collections.emptyList(),page,
                    totalPages,
                    true,arr[0], arr[1],arr[2], arr[3],arr[4]);
        }

        List<Ratings> SortedratingsList = ratingsList.stream().sorted(Comparator.comparing(Ratings::getCreatedAt).reversed()).toList();

        int start = page * size;
        if (start >= ratingsList.size()) {
            return new RatingsResponceDto(
                    restorent.getAverageRating(),
                    restorent.getTotalRatings(),
                    Collections.emptyList(),
                    page,
                    totalPages,
                    true,arr[0], arr[1],arr[2], arr[3],arr[4]
            );
        }
        int end = Math.min(start+size,ratingsList.size());


        List<RatingsDto> ratingsListDto = SortedratingsList.subList(start,end).stream().map(ratings -> RatingsDto.builder()
                .ratingId(ratings.getId())
                .ratings(ratings.getRatings())
                .review(ratings.getReview())
                .name(ratings.getUser().getName())
                .createdAt(ratings.getCreatedAt())
                .build()).toList();


        return new RatingsResponceDto(restorent.getAverageRating(),restorent.getTotalRatings(),ratingsListDto,page,totalPages,last,arr[0], arr[1],arr[2], arr[3],arr[4]);
    }

}
