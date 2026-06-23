package com.example.Multi.Vender.Food.Order.Web.Service;



import com.example.Multi.Vender.Food.Order.Web.Dtos.AddressRequest;
import com.example.Multi.Vender.Food.Order.Web.Dtos.AddressResponseDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface AddressService {

    AddressResponseDto addAddress(AddressRequest request);

    AddressResponseDto updateAddress(
            Long addressId,
            AddressRequest request
    );

    List<AddressResponseDto> getUserAddresses(Long userId);

    void deleteAddress(Long addressId);

    String setDefault(long adressId);
}