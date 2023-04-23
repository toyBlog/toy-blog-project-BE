package com.toy.blog.api.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import javax.validation.constraints.NotNull;

@UtilityClass
public class UserFriendRequest {

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class FollowUserFriend {

        @NotNull(message = "friendId는 필수값 입니다.")
        Long friendId;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class BlockUserFriend {

        @NotNull(message = "friendId는 필수값 입니다.")
        Long friendId;
    }
}
