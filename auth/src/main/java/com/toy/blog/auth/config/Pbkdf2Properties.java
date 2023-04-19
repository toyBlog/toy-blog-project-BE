package com.toy.blog.auth.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
