package com.example.demo;

import com.example.demo.constant.Path;
import com.example.demo.enumeration.ErrorCode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationTests {

    @Autowired
    MockMvc mockMvc;

    @Value("classpath:data/ReservationCreateRequest.json")
    Resource reservationCreateRequest;
    @Value("classpath:data/ReservationUpdateRequest.json")
    Resource reservationUpdateRequest;

    private String createdResourceLocation;

    @BeforeAll
    void createResource() throws Exception {
        String content = StreamUtils.copyToString(reservationCreateRequest.getInputStream(), Charset.defaultCharset());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(Path.BASE)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION))
                .andReturn();
        createdResourceLocation = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);
    }

    @Test
    void shouldCreateAReservation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(createdResourceLocation))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldReadAllReservations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(Path.BASE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
    }

    @Test
    void shouldUpdateReservation() throws Exception {
        String content = StreamUtils.copyToString(reservationUpdateRequest.getInputStream(), Charset.defaultCharset());
        mockMvc.perform(MockMvcRequestBuilders.put(createdResourceLocation)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteAReservation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(createdResourceLocation))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        createResource();
    }

    @Test
    void shouldReturnDayAlreadyBooked() throws Exception {
        String content = StreamUtils.copyToString(reservationCreateRequest.getInputStream(), Charset.defaultCharset());
        mockMvc.perform(MockMvcRequestBuilders.post(Path.BASE)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is(ErrorCode.DAY_ALREADY_BOOKED.toString())));
    }
}
