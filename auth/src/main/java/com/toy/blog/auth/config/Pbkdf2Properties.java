package com.toy.blog.auth.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Pbkdf2Properties 정리
 * <p>
 * - 비밀번호로 부터 암호화 키를 생성하기 위한 키 파생 함수입니다.
 * - 주로 비밀번호 기반 암호화에서 사용합니다.
 * - PBKDF2 는 입력으로 비밀번호와 솔트를 받아서 파생된 키를 출력합니다.
 * - 솔트란 ?
 * - 단방향 해시 함수에서 다이제스틀르 생성할 때 추가되는 바이트 단위의 임의의 문자열
 * - 이 원본의 메시지에 문자열을 추가해서 다이제스를 생성하는 것을 솔팅(salting) 이라고함
 * <p>
 * - 더 자세한 정보는 https://d2.naver.com/helloworld/318732 참고
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.pbkdf2")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pbkdf2Properties {

    String salt;

    Integer iterationCount;

    Integer keyLength;
}
