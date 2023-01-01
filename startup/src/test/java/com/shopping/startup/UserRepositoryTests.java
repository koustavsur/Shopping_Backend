package com.shopping.startup;

import com.shopping.startup.entity.Roles;
import com.shopping.startup.entity.UserAddress;
import com.shopping.startup.entity.Users;
import com.shopping.startup.model.LoginSource;
import com.shopping.startup.model.Role;
import com.shopping.startup.repository.RoleRepository;
import com.shopping.startup.repository.UserAddressRepository;
import com.shopping.startup.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest

public class UserRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Test
    @Transactional
    public void testGetUserByEmail(){

        Roles role = Roles.builder()
                .role(Role.ROLE_ADMIN)
                .build();

       // roleRepository.save(role);
        Roles uRole = roleRepository.findByRole(Role.ROLE_ADMIN);

       /* Users user = Users.builder()
                .email("koustavsur114@gmail.com")
                .name("Koustav Sur")
                .loginSource(LoginSource.GOOGLE)
                .role(uRole)
                .imageUrl("http:/Url")
                .build();

        userRepository.save(user);*/

        Users fetchedUser = userRepository.findByEmail("koustavsur114@gmail.com");
        Assert.notNull(fetchedUser);

    }

    @Test
    @Transactional
    public void testDeleteUserByEmail(){
        userRepository.deleteUsersByEmail("koustavsur113@gmail.com");

        Users fetchedUser = userRepository.findByEmail("koustavsur113@gmail.com");
        Assert.isNull(fetchedUser);
    }

    @Test
    public void testGetUserAddress() {

        Roles uRole = roleRepository.findByRole(Role.ROLE_ADMIN);

        Users user = Users.builder()
                .email("koustavsur114@gmail.com")
                .name("Koustav Sur")
                .loginSource(LoginSource.GOOGLE)
                .role(uRole)
                .imageUrl("http://Url/image.jpg")
                .build();

        userRepository.save(user);


        Users fetchedUser = userRepository.findByEmail("koustavsur114@gmail.com");

        UserAddress userAddress1 = UserAddress.builder()
                .addressStreetOne("Street One")
                .addressStreetTwo("Street two")
                .city("City")
                .pincode("560100")
                .country("India")
                .phoneNumber("7406560606")
                .user(fetchedUser)
                .build();


        UserAddress userAddress2 = UserAddress.builder()
                .addressStreetOne("Street again")
                .city("City")
                .pincode("560103")
                .country("India")
                .phoneNumber("8583066969")
                .user(fetchedUser)
                .build();

        userAddressRepository.save(userAddress1);
        userAddressRepository.save(userAddress2);

        List<UserAddress> userAddresses = userAddressRepository.findByUser(fetchedUser);
        Assert.isTrue(userAddresses.size() == 2);

        userRepository.delete(fetchedUser);

    }


}
