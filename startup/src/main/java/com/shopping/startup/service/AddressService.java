package com.shopping.startup.service;

import com.shopping.startup.entity.UserAddress;
import com.shopping.startup.entity.Users;
import com.shopping.startup.model.AddressRequest;
import com.shopping.startup.repository.UserAddressRepository;
import com.shopping.startup.repository.UserRepository;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final UserAddressRepository addressRepository;
    private final UserRepository userRepository;
    private final Validation validation;


    public List<UserAddress> getAllAddress() {
        return addressRepository.findAll();
    }

    public UserAddress addAddress(AddressRequest addressRequest) {

        if(isValidRequest(addressRequest)) {
            Users user = userRepository.findByEmail(addressRequest.getEmail().strip());
            if (user != null) {
                UserAddress address = UserAddress.builder()
                        .addressStreetOne(addressRequest.getStreetOne().strip())
                        .addressStreetTwo(addressRequest.getStreetTwo().strip())
                        .city(addressRequest.getCity().strip())
                        .state(addressRequest.getState().strip())
                        .pincode(addressRequest.getPin().strip())
                        .country(addressRequest.getCountry().strip())
                        .phoneNumber(addressRequest.getPhone().strip())
                        .user(user)
                        .build();

                return addressRepository.save(address);
            }
        }
        return null;
    }

    @Transactional
    public UserAddress updateAddress(AddressRequest addressRequest) {
        if(addressRequest.getId() == null || addressRequest.getId().strip().length() == 0){
            return null;
        }
        Optional<UserAddress> address = addressRepository.findById(Long.valueOf(addressRequest.getId().strip()));
        if(address.isPresent()){
            UserAddress updatedAddress = address.get();
            if(isValidRequest(addressRequest)){
                Users user = userRepository.findByEmail(addressRequest.getEmail().strip());
                if (user != null) {
                    updatedAddress.setAddressStreetOne(addressRequest.getStreetOne().strip());
                    updatedAddress.setAddressStreetTwo(addressRequest.getStreetTwo().strip());
                    updatedAddress.setCity(addressRequest.getCity().strip());
                    updatedAddress.setState(addressRequest.getState().strip());
                    updatedAddress.setPincode(addressRequest.getPin().strip());
                    updatedAddress.setCountry(addressRequest.getCountry().strip());
                    updatedAddress.setPhoneNumber(addressRequest.getPhone().strip());
                    updatedAddress.setUser(user);

                    return addressRepository.save(updatedAddress);
                }
            }
        }
        return null;
    }

    private boolean isValidRequest(AddressRequest addressRequest) {
        if(addressRequest.getEmail() != null && addressRequest.getEmail().strip().length() !=0){
            return validation.isCurrentUser(addressRequest.getEmail().strip())
                    || validation.isAdmin();
        } else {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            addressRequest.setEmail(principal.getUsername());
        }
        return true;
    }

    @Transactional
    public UserAddress deleteAddress(AddressRequest addressRequest) {
        if(addressRequest.getId() == null || addressRequest.getId().strip().length() == 0){
            return null;
        }
        Optional<UserAddress> address = addressRepository.findById(Long.valueOf(addressRequest.getId().strip()));
        if(address.isPresent()){
            if(validation.isAdmin() || validation.isCurrentUser(address.get().getUser().getEmail())){
                addressRepository.deleteById(Long.valueOf(addressRequest.getId().strip()));
                return UserAddress.builder().build();
            }
        }
        return null;
    }

}
