package iuh.fit.demo.controller;

import iuh.fit.demo.dto.request.CreateUserRequest;
import iuh.fit.demo.dto.request.UpdateUserRequest;
import iuh.fit.demo.dto.response.ApiResponse;
import iuh.fit.demo.dto.response.UserResponseDTO;
import iuh.fit.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<UserResponseDTO>> findAll(){
        ApiResponse<List<UserResponseDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.getAll());
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> findById(@PathVariable("id") String id){
        ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.getById(id));
        return apiResponse;
    }

    @PostMapping("/create")
    public UserResponseDTO create(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.create(createUserRequest);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable String id, @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.update(id, updateUserRequest);
    }
}
