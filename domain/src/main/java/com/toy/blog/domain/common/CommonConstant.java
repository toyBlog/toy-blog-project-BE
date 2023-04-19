package com.toy.blog.domain.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstant {

    @UtilityClass
    public static class RegExp {
        public static final String EMAIL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]+$";
    }
}
