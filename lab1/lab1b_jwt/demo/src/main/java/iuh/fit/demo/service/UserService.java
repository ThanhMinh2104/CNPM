package iuh.fit.demo.service;

import iuh.fit.demo.dto.request.CreateUserRequest;
import iuh.fit.demo.dto.request.UpdateUserRequest;
import iuh.fit.demo.dto.response.UserResponseDTO;
import iuh.fit.demo.entity.User;
import iuh.fit.demo.exception.AppException;
import iuh.fit.demo.exception.ErrorCode;
import iuh.fit.demo.mapper.UserMapper;
import iuh.fit.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> getAll() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOs = new ArrayList<UserResponseDTO>();
        users.forEach(user -> userResponseDTOs.add(userMapper.toDTO(user)));
        return userResponseDTOs;
    }

    public UserResponseDTO getById(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOTEXISTED));
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO create(CreateUserRequest createUserRequest){
        User user = userMapper.toUser(createUserRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        return userMapper.toDTO(userRepository.save(user));
    }

    @Transactional
    public void delete(String id){
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponseDTO update(String id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        return userMapper.toDTO(userMapper.toUser(user, updateUserRequest));
    }
}
