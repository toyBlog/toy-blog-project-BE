package com.toy.blog.auth.config;

import com.toy.blog.auth.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * CustomPasswordEncoder 정리
 *
 * - PasswordEncoder ?
 *   - 사용자의 비멀번호를 암호화 하는 인터페이스
 *   - 사용자의 비밀번호를 텍스트 그대로 저장하는 것은 보안상 매우 취약하므로, 비밀번호를 암호화 하여 저장해야함
 *   - 제공하는 메서드
 *     1. encode(CharSequence rawPassword): 주어진 비밀번호를 암호화하여 반환합니다.
 *     2. matches(CharSequence rawPassword, String encodedPassword): 주어진 비밀번호(rawPassword)가
 *     암호화된 비밀번호(encodedPassword)와 일치하는지 여부를 반환합니다.
 *
 */

@Slf4j
@Component("passwordEncoder")
@RequiredArgsConstructor
public class CustomPasswordEncoder implements PasswordEncoder {

    private final Pbkdf2Properties pbkdf2Properties;

    @Override
    public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw(getCipherText(rawPassword), BCrypt.gensalt());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(getCipherText(rawPassword), encodedPassword.replace("$2y$", "$2a$"));
    }

    @SneakyThrows({NoSuchAlgorithmException.class, InvalidKeySpecException.class})
    private String getCipherText(CharSequence rawPassword) {
        return AuthUtils.pbkdf2(
                rawPassword.toString(),
                pbkdf2Properties.getSalt(),
                pbkdf2Properties.getIterationCount(),
                pbkdf2Properties.getKeyLength()
        );
    }
}