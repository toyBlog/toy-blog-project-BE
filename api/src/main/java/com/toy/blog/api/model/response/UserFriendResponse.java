package com.toy.blog.api.model.response;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.UserFriend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserFriendResponse {

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    public static class UserFriendBase {

        Long id;

        Status.UserFriend status;
    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Detail extends UserFriendBase{

        public static Detail of(UserFriend userFriend) {

            return Detail.builder()
                    .id(userFriend.getId())
                    .status(userFriend.getStatus())
                    .build();
        }
    }

}
