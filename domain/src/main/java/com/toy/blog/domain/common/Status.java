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

    public enum ArticleImage {
        ACTIVE,
        INACTIVE
    }


    public enum Like{
        ACTIVE,
        INACTIVE
    }

    public enum UserFriend {
        FOLLOW,
        UNFOLLOW,
        BLOCKED
    }

    public enum Comment {
        ACTIVE,
        INACTIVE,
        BLOCKED
    }
}
