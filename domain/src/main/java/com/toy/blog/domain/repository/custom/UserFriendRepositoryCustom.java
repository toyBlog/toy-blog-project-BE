package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.UserFriend;

import java.util.List;

public interface UserFriendRepositoryCustom {

    List<UserFriend> findFollowList(Long userId);

    List<UserFriend> findFollowingList(Long friendId);
}
