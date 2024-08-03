package com.coderhack.leaderboard.repository;

import com.coderhack.leaderboard.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity,String> {
}
