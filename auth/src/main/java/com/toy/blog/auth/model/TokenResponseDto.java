package com.toy.blog.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResponseDto {

    final String tokenType;

    final String accessToken;

    final Long expiresIn;

    final String refreshToken;

    final Long refreshTokenExpiresIn;

    final Boolean tmpPasswordUsed;

    final Boolean isJoin;

}
