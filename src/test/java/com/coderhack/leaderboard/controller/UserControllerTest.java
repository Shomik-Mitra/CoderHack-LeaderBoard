package com.coderhack.leaderboard.controller;

import com.coderhack.leaderboard.model.dto.UserDTO;
import com.coderhack.leaderboard.model.dto.UserRegiatrationDTO;
import com.coderhack.leaderboard.model.dto.UserScoreUpdateDTO;
import com.coderhack.leaderboard.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserRegiatrationDTO userRegDto;
    private UserDTO userDTO;
    private UserScoreUpdateDTO userScoreUpdateDTO;

    @BeforeEach
    void setUp() {
        userRegDto = new UserRegiatrationDTO("1", "User1");
        userDTO = UserDTO.builder().userId("1").userName("User1").score(0).badges(Collections.emptySet()).build();
        userScoreUpdateDTO = new UserScoreUpdateDTO(50);
    }


    @Test
    @DisplayName("Register a new user")
    void registerUser() throws Exception {

        when(userService.registerUser(any(UserRegiatrationDTO.class))).thenReturn(userRegDto);

        mockMvc.perform(MockMvcRequestBuilders.post(UserController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(userRegDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", is(userRegDto.getUserId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", is(userRegDto.getUserName())));

        verify(userService, times(1)).registerUser(any(UserRegiatrationDTO.class));
    }

    @Test
    @DisplayName("Get all registered users")
    void getAllRegisterUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDTO));

        mockMvc.perform(MockMvcRequestBuilders.get(UserController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName", is("User1")));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Get specific user by ID")
    void getSpecificUser() throws Exception {
        when(userService.getUserById(anyString())).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(UserController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", is("User1")));

        verify(userService, times(1)).getUserById(anyString());
    }

    @Test
    @DisplayName("Update score for specific user")
    void updateScoreForSpecificUser() throws Exception {
        when(userService.updateUserScore(anyString(), any(UserScoreUpdateDTO.class))).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(UserController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userScoreUpdateDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deregisterUser() throws Exception {
        doNothing().when(userService).deleteUser(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete(UserController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).deleteUser(anyString());
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}