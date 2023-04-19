package com.toy.blog.domain.common;

public class Status {

    public enum User{
        ACTIVE,
        INACTIVE
    }

    public enum Article{
        ACTIVE,
        INACTIVE
    }

    public enum Like{
        ACTIVE,
        INACTIVE
    }

    public enum Follow{
        FOLLOW,
        UNFOLLOW,
        BLOCKED
    }
}
