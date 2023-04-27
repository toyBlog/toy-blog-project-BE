package com.toy.blog.domain.entity;

import com.toy.blog.domain.common.CommonConstant;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.value.Authority;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @NotNull @Size(min = 10, max = 50)
    @Pattern(regexp = CommonConstant.RegExp.EMAIL)
    String email;

    @NotNull @Size(min = 8, max = 20)
    //@Pattern(regexp = CommonConstant.RegExp.PASSWORD)
    String password;

    @NotNull @Size(min = 2, max = 10)
    String nickname;

    @Enumerated(EnumType.STRING)
    Status.User status;

    @NotNull
    @Enumerated(EnumType.STRING)
    Authority authority;

    @OneToMany(mappedBy = "user")
    List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<UserFriend> userFriendList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getAuthority().name()));
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

