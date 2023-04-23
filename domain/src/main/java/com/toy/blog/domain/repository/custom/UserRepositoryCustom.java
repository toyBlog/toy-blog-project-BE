package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findUserList(List<Long> userIdList, Pageable pageable);
}
