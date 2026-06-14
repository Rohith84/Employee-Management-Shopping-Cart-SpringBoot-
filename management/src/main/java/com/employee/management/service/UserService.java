package com.employee.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.employee.management.dto.AddressDTO;
import com.employee.management.dto.UserRequest;
import com.employee.management.dto.UserResponse;
import com.employee.management.model.Address;
import com.employee.management.model.User;
import com.employee.management.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    public String addUser(User s) {
        userRepository.save(s);
        return "User Added Successfully";
    }

    public UserResponse getUser(Long id) {
        return userRepository.findById(id).map(this::mapToUserResponse).orElse(null);
    }

    public UserResponse updateUser(Long id, UserRequest s) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        user.setFirstName(s.getFirstName());
        user.setLastName(s.getLastName());
        user.setPhone(s.getPhone());
        user.setEmail(s.getEmail());
        user.setRole(s.getRole());   
        if (s.getAddress() != null) {
            Address address = user.getAddress();
            if(address==null){
                address=new Address();
            }
            address.setStreet(s.getAddress().getStreet());
        }
        User updatedUser=userRepository.save(user);
        return mapToUserResponse(updatedUser);
    }

    public String deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User Deleted Successfully";
        }

        return "User Not Found";
    }
    private UserResponse mapToUserResponse(User user){
        UserResponse response=new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        if(user.getAddress()!=null){
            AddressDTO addressDTO=new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setZipCode(user.getAddress().getZipcode());
        }
        return response;
    }

}