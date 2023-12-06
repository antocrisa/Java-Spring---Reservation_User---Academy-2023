package com.example.demo.controller;

import com.example.demo.constant.Path;
import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.dto.ReservationCreateRequestDto;
import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.dto.ReservationUpdateRequestDto;
import com.example.demo.mapper.ReservationMapper;
import com.example.demo.model.ReservationModel;
import com.example.demo.service.ReservationService;
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
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
    private final ValidationService validationService;

    @Operation(description = "Find all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public List<ReservationResponseDto> findAll(@RequestParam(value = "userId", required = false) String userId) {
        return reservationService.findAll(userId).stream()
                .map(reservationMapper::responseFromModel)
                .collect(Collectors.toList());
    }

    @Operation(description = "Find a specific reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ReservationResponseDto findById(@Schema(description = "The id of the reservation")
                                               @PathVariable("id") long id) {
        return reservationMapper.responseFromModel(reservationService.findById(id));
    }

    @Operation(description = "Create a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@Schema(description = "The create request", implementation = ReservationCreateRequestDto.class)
                                                             @RequestBody ReservationCreateRequestDto reservationCreateRequestDto) {
        validationService.doValidate(reservationCreateRequestDto);
        ReservationModel reservationModel = reservationService.save(reservationMapper.modelFromCreateRequest(reservationCreateRequestDto));
        URI uri = new DefaultUriBuilderFactory(Path.BASE).builder()
                .pathSegment(reservationModel.getId()+"")
                .build();
        return ResponseEntity.created(uri).body(reservationMapper.responseFromModel(reservationModel));
    }

    @Operation(description = "Update a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public ReservationResponseDto update(@Schema(description = "The update request", implementation = ReservationUpdateRequestDto.class)
                                             @RequestBody ReservationUpdateRequestDto reservationUpdateRequestDto,
                                         @Schema(description = "The id of the reservation")
                                         @PathVariable("id") long id) {
        validationService.doValidate(reservationUpdateRequestDto);
        ReservationModel reservationModel = reservationService.update(reservationMapper.modelFromUpdateRequest(reservationUpdateRequestDto), id);
        return reservationMapper.responseFromModel(reservationModel);
    }

    @Operation(description = "Delete a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Schema(description = "The id of the reservation")
                                           @PathVariable("id") long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
