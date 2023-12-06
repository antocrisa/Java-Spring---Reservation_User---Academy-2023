package com.example.demo;

import com.example.demo.constant.Path;
import com.example.demo.dto.ReservationClientResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:data/UserCreateRequest.json")
    Resource userCreateRequest;
    @Value("classpath:data/ReservationResponseStub.json")
    Resource reservationResponseStub;

    private String createdResourceLocation;

    @BeforeAll
    void preloadUser() throws Exception {
        String content = StreamUtils.copyToString(userCreateRequest.getInputStream(), Charset.defaultCharset());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(Path.BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION))
                .andReturn();
        createdResourceLocation = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);
    }

    @Test
    void shouldReadAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(Path.BASE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(0)));
    }

    @Test
    void shouldReadUser() throws Exception {
        ReservationClientResponseDto stub = reservationClientResponseDto();
        Mockito.when(restTemplate.exchange(Mockito.any(), Mockito.any(), Mockito.any(),
                        Mockito.any(ParameterizedTypeReference.class), Mockito.any(Object[].class)))
                .thenReturn(ResponseEntity.ok(Collections.singletonList(stub)));
        mockMvc.perform(MockMvcRequestBuilders.get(createdResourceLocation))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservations.length()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservations[0].id", Matchers.is(1)));
    }

    private ReservationClientResponseDto reservationClientResponseDto() throws Exception {
        String content = StreamUtils.copyToString(reservationResponseStub.getInputStream(), Charset.defaultCharset());
        return objectMapper.readValue(content, ReservationClientResponseDto.class);
    }
}
