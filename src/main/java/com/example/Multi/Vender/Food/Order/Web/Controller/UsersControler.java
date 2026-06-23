package com.example.Multi.Vender.Food.Order.Web.Controller;

import com.example.Multi.Vender.Food.Order.Web.Dtos.SingleUserInfoForOwnerDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.UserInfoDto;
import com.example.Multi.Vender.Food.Order.Web.Dtos.UsersInfoForOwnerDto;
import com.example.Multi.Vender.Food.Order.Web.Service.UsersService;
import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersControler {

    private final UsersService usersService;

    @GetMapping("/userInfo/{userId}")
    public ResponseEntity<UserInfoDto> getUserData(@PathVariable Long userId){
        return ResponseEntity.ok(usersService.getUserInfo(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsersInfoForOwnerDto>> getAllUsers(){
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @PatchMapping("/roll/{userId}")
    public ResponseEntity<String> updateUserRolll(@PathVariable long userId, @RequestParam UserRole userRole){
        return ResponseEntity.ok(usersService.updateUserRolll(userId,userRole));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId){
        return ResponseEntity.ok(usersService.deleteUser(userId));
    }

    @GetMapping("/{userId}/info/owner")
    public ResponseEntity<SingleUserInfoForOwnerDto> getUserInfoForOwner(@PathVariable long userId){
        return ResponseEntity.ok(usersService.getUserInfoForOwner(userId));
    }

}
