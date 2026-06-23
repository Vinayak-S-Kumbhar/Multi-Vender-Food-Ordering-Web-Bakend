package com.example.Multi.Vender.Food.Order.Web.Service;

import com.example.Multi.Vender.Food.Order.Web.Dtos.SingleUserInfoForOwnerDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.UserInfoDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.UsersInfoForOwnerDto;
import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {
    UserInfoDto getUserInfo(Long userId);

    List<UsersInfoForOwnerDto> getAllUsers();

    String deleteUser(long userId);

    String updateUserRolll(long userId, UserRole userRole);

    SingleUserInfoForOwnerDto getUserInfoForOwner(long userId);
}
