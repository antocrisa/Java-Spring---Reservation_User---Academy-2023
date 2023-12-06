package com.example.demo.controller;

import com.example.demo.constant.Path;
import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.dto.UserCreateRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateRequestDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.service.ValidationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@OpenAPIDefinition
@AllArgsConstructor
@RestController
@RequestMapping(Path.BASE)
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ValidationService validationService;

    @Operation(description = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        validationService.doValidate(userCreateRequestDto);
        UserModel userModel = userService.createUser(userMapper.modelFromCreateRequest(userCreateRequestDto));
        URI uri = new DefaultUriBuilderFactory(Path.BASE).builder()
                .pathSegment(userModel.getId()+"")
                .build();
        return ResponseEntity.created(uri).body(userMapper.responseFromModel(userModel));
    }

    @Operation(description = "Read all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> userResponseDtos = userService.findAll().stream()
                .map(userMapper::responseFromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponseDtos);
    }

    @Operation(description = "Find a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @GetMapping("/{userId}")
    public UserResponseDto findById(@PathVariable("userId") long userId) {
        UserModel userModel = userService.findById(userId);
        return userMapper.responseFromModel(userModel);
    }

    @Operation(description = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@PathVariable("userId") long userId,
                                                      @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        validationService.doValidate(userUpdateRequestDto);
        UserModel userModel = userService.updateUser(userId, userMapper.modelFromUpdateRequest(userUpdateRequestDto));
        return userMapper.responseFromModel(userModel);
    }

    @Operation(description = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
