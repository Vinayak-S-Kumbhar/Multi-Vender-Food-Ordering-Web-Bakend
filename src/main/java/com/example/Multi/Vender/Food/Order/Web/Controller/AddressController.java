package com.example.Multi.Vender.Food.Order.Web.Controller;

import com.example.Multi.Vender.Food.Order.Web.Dtos.AddressRequest;
import com.example.Multi.Vender.Food.Order.Web.Dtos.AddressResponseDto;
import com.example.Multi.Vender.Food.Order.Web.Service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add")
    public AddressResponseDto addAddress(
            @RequestBody AddressRequest request
    ) {
        return addressService.addAddress(request);
    }

    @PutMapping("/update/{addressId}")
    public AddressResponseDto updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressRequest request
    ) {
        return addressService.updateAddress(addressId, request);
    }

    @GetMapping("/user/{userId}")
    public List<AddressResponseDto> getUserAddresses(
            @PathVariable Long userId
    ) {
        return addressService.getUserAddresses(userId);
    }

    @DeleteMapping("/delete/{addressId}")
    public void deleteAddress(
            @PathVariable Long addressId
    ) {
        addressService.deleteAddress(addressId);
    }

    @PatchMapping("update/isDefault/{adressId}")
    public ResponseEntity<String> setDefault(@PathVariable long adressId){
        return ResponseEntity.ok(addressService.setDefault(adressId));
    }
}