package com.toy.blog.domain.entity;

import com.toy.blog.domain.common.CommonConstant;
import com.toy.blog.domain.common.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @NotNull @Size(min = 10, max = 50)
    @Pattern(regexp = CommonConstant.RegExp.EMAIL)
    String email;

    @NotNull @Size(min = 8, max = 20)
    @Pattern(regexp = CommonConstant.RegExp.PASSWORD)
    String password;

    @NotNull @Size(min = 2, max = 10)
    String nickname;

    @Enumerated(EnumType.STRING)
    Status.User status;

    @OneToMany(mappedBy = "user")
    List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<UserFriend> userFriendList = new ArrayList<>();
}

