package com.toy.blog.domain.repository;

import com.toy.blog.domain.entity.UserFriend;
import com.toy.blog.domain.repository.custom.UserFriendRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFriendRepository extends JpaRepository<UserFriend, Long>, UserFriendRepositoryCustom {

    Optional<UserFriend> findByUserIdAndFriendId(Long userId, Long friendId);

}
