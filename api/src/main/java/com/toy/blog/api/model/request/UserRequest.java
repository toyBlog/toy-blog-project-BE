package com.toy.blog.api.model.request;

import com.toy.blog.domain.common.CommonConstant;
import com.toy.blog.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@UtilityClass
public class UserRequest {

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserIdList {

        List<Long> userIdList;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Join {

        @NotNull
        @Size(max = 50)
        @Pattern(regexp = CommonConstant.RegExp.EMAIL)
        String email;

        @NotNull
        @Size(max = 255)
        String password;

        @NotNull
        String nickname;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .build();
        }
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Login {

        @NotNull
        @Size(max = 50)
        @Pattern(regexp = CommonConstant.RegExp.EMAIL)
        String email;

        @NotNull
        @Size(max = 255)
        String password;

    }
    
}

