package com.example.Multi.Vender.Food.Order.Web.Service.impl;

import com.example.Multi.Vender.Food.Order.Web.Dtos.AddressRequest;
import com.example.Multi.Vender.Food.Order.Web.Dtos.AddressResponseDto;
import com.example.Multi.Vender.Food.Order.Web.Entity.Address;
import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import com.example.Multi.Vender.Food.Order.Web.Repository.AddressRepository;
import com.example.Multi.Vender.Food.Order.Web.Repository.UsersRepository;
import com.example.Multi.Vender.Food.Order.Web.Service.AddressService;
import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UsersRepository userRepository;

    public AddressResponseDto addAddress(AddressRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserRole() == UserRole.BLOCKED){
            throw new RuntimeException("User is Blocked!");
        }

        Address address = Address.builder()
                .user(user)
                .addressType(request.getAddressType())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .landmark(request.getLandmark())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        List<Address> addresses = user.getAddresses();

        // First address becomes default automatically
        if (addresses.isEmpty()) {
            address.setIsDefault(true);

        }
        // User selected "Set as Default"
        else if (Boolean.TRUE.equals(request.getIsDefault())) {

            Address currentDefault = addresses.stream()
                    .filter(Address::getIsDefault)
                    .findFirst()
                    .orElse(null);

            if (currentDefault != null) {
                currentDefault.setIsDefault(false);
                addressRepository.save(currentDefault);
            }
            address.setIsDefault(true);
        }
        // Normal address
        else {
            address.setIsDefault(false);
        }
        Address saved = addressRepository.save(address);

        return mapToDto(saved);
    }

    @Transactional
    public AddressResponseDto updateAddress(
            Long addressId,
            AddressRequest request
    ) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        boolean wasDefault = Boolean.TRUE.equals(address.getIsDefault());

        address.setAddressType(request.getAddressType());
        address.setFullName(request.getFullName());
        address.setPhone(request.getPhone());

        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setLandmark(request.getLandmark());

        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());

        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());

        Address updated = addressRepository.save(address);

        if (Boolean.TRUE.equals(request.getIsDefault()) && !wasDefault) {

            setDefault(addressId);
        }

        return mapToDto(updated);
    }

    @Override
    @Transactional
    public String setDefault(long addressId) {

        Address newDefault = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        User user = newDefault.getUser();

        user.getAddresses().stream()
                .filter(Address::getIsDefault)
                .forEach(address -> address.setIsDefault(false));

        newDefault.setIsDefault(true);

        userRepository.save(user);

        return "Set as Default";
    }

    @Override
    public List<AddressResponseDto> getUserAddresses(Long userId) {

        return addressRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void deleteAddress(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }


    private AddressResponseDto mapToDto(Address address) {

        return AddressResponseDto.builder()
                .id(address.getId())
                .addressType(address.getAddressType())
                .fullName(address.getFullName())
                .phone(address.getPhone())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .landmark(address.getLandmark())
                .city(address.getCity())
                .state(address.getState())
                .pincode(address.getPincode())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .isDefault(address.getIsDefault())
                .build();
    }
}