package com.coderhack.leaderboard.service;

import com.coderhack.leaderboard.model.dto.UserDTO;
import com.coderhack.leaderboard.model.dto.UserRegiatrationDTO;
import com.coderhack.leaderboard.model.dto.UserScoreUpdateDTO;

import java.util.List;

public interface UserService {

    UserRegiatrationDTO registerUser(UserRegiatrationDTO user);

    UserDTO updateUserScore(String userId, UserScoreUpdateDTO scoreUpdateDTO);

    void deleteUser(String userId);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(String userId);
}
