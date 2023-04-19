package com.toy.blog.domain.repository;

import com.toy.blog.domain.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {
}
