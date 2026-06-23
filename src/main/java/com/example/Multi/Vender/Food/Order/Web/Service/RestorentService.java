package com.example.Multi.Vender.Food.Order.Web.Service;

import com.example.Multi.Vender.Food.Order.Web.Dtos.*;
import com.example.Multi.Vender.Food.Order.Web.config.RestorentStatus;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestorentService {
    String addRestorent(AddRestorentDto restorentDto);

    List<RestorentInfoDto> getUserRestorents(long userId);

    RestorentWithFoodListDto getRestorentAndFoodListFromFoodId(long foodId);

    List<RestorentDto> getAllRestorents();

    RestorentWithFoodListDto getRestorentAndFoodListFromRestorentId(long restorentId);

    String updateRestorentStatus(long restorentId, RestorentStatus status);

    List<RestorentDto> getAllRestorentsForOwner();

    List<RequestedRestorentDto> getUserAllRestorents(long userId);
}
