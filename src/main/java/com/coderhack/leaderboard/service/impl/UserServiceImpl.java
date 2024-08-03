package com.coderhack.leaderboard.service.impl;

import com.coderhack.leaderboard.entity.UserEntity;
import com.coderhack.leaderboard.exception.ResourceNotFoundException;
import com.coderhack.leaderboard.model.Badges;
import com.coderhack.leaderboard.model.dto.UserDTO;
import com.coderhack.leaderboard.model.dto.UserRegiatrationDTO;
import com.coderhack.leaderboard.model.dto.UserScoreUpdateDTO;
import com.coderhack.leaderboard.repository.UserRepository;
import com.coderhack.leaderboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserRegiatrationDTO registerUser(UserRegiatrationDTO userRegiatrationDTO) {
        UserEntity user = modelMapper.map(userRegiatrationDTO, UserEntity.class);
        user.setScore(0);
        user.setBadges(new HashSet<>());
        log.debug("Registering new user: {}",user.getUserName());
        return modelMapper.map(userRepository.save(user),UserRegiatrationDTO.class);
    }

    @Override
    public UserDTO updateUserScore(String userId, UserScoreUpdateDTO scoreUpdateDTO) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->{
                   log.error("user with Id:{} not found ",userId);
                   return new ResourceNotFoundException("User not found with id: " + userId);
                   });
        user.setScore(scoreUpdateDTO.getScore());
        assignBadges(user);
        log.info("Updating score for user: {} with userID {}. New score: {}", user.getUserName(),user.getUserId(),user.getScore());
        return modelMapper.map(userRepository.save(user),UserDTO.class);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
        log.info("Deleting the user with userID : {} ",userId);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Retrieving all users");
       return userRepository.findAll()
               .stream()
               .sorted(Comparator.comparingInt(UserEntity::getScore).reversed())
               .map(user->modelMapper.map(user,UserDTO.class))
               .toList();
    }

    @Override
    public UserDTO getUserById(String userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->{
            log.error("user with Id: {} not found ",userId);
            return new ResourceNotFoundException("User not found with id: " + userId);
        });
        return modelMapper.map(user,UserDTO.class);
    }

    private void assignBadges(UserEntity user) {
        int score = user.getScore();
        if (score >= 1 && score < 30) {
            user.getBadges().add(Badges.CODE_NINJA);
        }
        else if(score >= 30 && score < 60) {
            user.getBadges().add(Badges.CODE_CHAMP);
        }
        else if (score >= 60 && score <= 100) {
            user.getBadges().add(Badges.CODE_MASTER);
        }
    }
}
