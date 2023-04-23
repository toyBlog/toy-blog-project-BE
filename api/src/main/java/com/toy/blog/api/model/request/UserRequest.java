package com.toy.blog.api.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserRequest {

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserIdList {

        List<Long> userIdList;
    }
}
