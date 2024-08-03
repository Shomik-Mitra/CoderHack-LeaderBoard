package com.coderhack.leaderboard.service.impl;

import com.coderhack.leaderboard.entity.UserEntity;
import com.coderhack.leaderboard.exception.ResourceNotFoundException;
import com.coderhack.leaderboard.model.Badges;
import com.coderhack.leaderboard.model.dto.UserDTO;
import com.coderhack.leaderboard.model.dto.UserRegiatrationDTO;
import com.coderhack.leaderboard.model.dto.UserScoreUpdateDTO;
import com.coderhack.leaderboard.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Register a new user")
    void registerUser() {
        //Arrange
        UserRegiatrationDTO userRegDto = new UserRegiatrationDTO("1", "User1");
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("1");
        userEntity.setUserName("User1");
        userEntity.setScore(0);
        userEntity.setBadges(new HashSet<>());

        when(modelMapper.map(userRegDto, UserEntity.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserRegiatrationDTO.class)).thenReturn(userRegDto);

        // Act
        UserRegiatrationDTO result = userService.registerUser(userRegDto);

        // Assert
        assertEquals(userRegDto, result);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    @DisplayName("Update user score")
    void updateUserScore() {
        // Arrange
        String userId = "1";
        UserScoreUpdateDTO scoreUpdateDto = new UserScoreUpdateDTO(50);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setUserName("User1");
        userEntity.setScore(0);
        userEntity.setBadges(new HashSet<>());

        UserDTO userDTO=UserDTO.builder()
                        .score(scoreUpdateDto.getScore())
                                .badges(userEntity.getBadges())
                                        .build();


        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDTO.class)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.updateUserScore(userId, scoreUpdateDto);

        // Assert
        assertEquals(50, result.getScore());
        assertTrue(result.getBadges().contains((Badges.CODE_CHAMP)));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    @DisplayName("Delete user")
    void deleteUser() {
        // Arrange
        String userId = "1";
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Assert
        assertNotNull(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Get all users")
    void getAllUsers() {
        // Arrange
        UserEntity user1 = new UserEntity();
        user1.setUserId("1");
        user1.setUserName("User1");
        user1.setScore(50);
        user1.setBadges(new HashSet<>());

        UserEntity user2 = new UserEntity();
        user2.setUserId("2");
        user2.setUserName("User2");
        user2.setScore(100);
        user2.setBadges(new HashSet<>());

        List<UserEntity> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);
        when(modelMapper.map(user1, UserDTO.class)).thenReturn(new UserDTO());
        when(modelMapper.map(user2, UserDTO.class)).thenReturn(new UserDTO());

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Get user by ID")
    void getUserById() {
        // Arrange
        String userId = "1";
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setUserName("User1");
        userEntity.setScore(50);
        userEntity.setBadges(new HashSet<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userEntity, UserDTO.class)).thenReturn(new UserDTO());

        // Act
        UserDTO result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Get user by ID - not found")
    void getUserById_NotFound() {
        // Arrange
        String userId = "1";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }
}