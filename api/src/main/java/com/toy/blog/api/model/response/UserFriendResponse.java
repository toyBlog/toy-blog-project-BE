package com.toy.blog.api.model.response;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.UserFriend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserFriendResponse {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class UserFriendInfo {

        Status.UserFriend status;

        public static UserFriendInfo of(UserFriend userFriend){

            return  UserFriendInfo.builder()
                                  .status(userFriend.getStatus())
                                  .build();
        }
    }
}
