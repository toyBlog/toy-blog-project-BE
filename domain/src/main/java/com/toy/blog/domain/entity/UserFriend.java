package com.toy.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toy.blog.domain.common.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFriend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_friend_id")
    Long id;

    @Enumerated(EnumType.STRING)
    Status.UserFriend status;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    User user;

    @Column(name = "friend_id")
    @Positive
    Long friendId;

    /**
     * [변경 메서드]
     * */
    public void changeStatus(Status.UserFriend status){
        this.status = status;
    }

    /**
     * [비교 메서드]
     * */
    public boolean isConnecting(UserFriend userFriend) {

        if (this.getUser().getId().equals(userFriend.friendId) && this.getFriendId().equals(userFriend.getUser().getId())
                && this.status.equals(Status.UserFriend.FOLLOW) && userFriend.getStatus().equals(Status.UserFriend.FOLLOW)) {
            return true;
        } else {
            return false;
        }
    }

}

