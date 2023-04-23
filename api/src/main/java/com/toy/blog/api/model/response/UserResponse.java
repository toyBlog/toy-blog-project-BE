package com.toy.blog.api.model.response;


import com.toy.blog.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserResponse {

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    public static class UserBase{

        Long id;

        String email;

        String nickname;

    }

    @Getter
    @Setter
    @SuperBuilder
    public static class SummaryInfo extends UserBase {

        public static SummaryInfo of(User user) {

            return SummaryInfo.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .build();
        }

        public static List<SummaryInfo> of(List<User> userList) {
            return userList.stream().map(SummaryInfo::of).collect(Collectors.toList());
        }

    }


    @Getter
    @Setter
    @SuperBuilder
    public static class Info extends UserBase {

        List<Long> followIdList = new ArrayList<>();
        List<Long> followingIdList = new ArrayList<>();
        List<Long> connectingIdList = new ArrayList<>();

        public static Info of(User user) {

            return Info.builder()
                       .id(user.getId())
                       .email(user.getEmail())
                       .nickname(user.getNickname())
                       .build();
        }

        public static Info of(User user, List<Long> followIdList, List<Long> followingIdList, List<Long> connectingIdList) {

           return Info.builder()
                   .id(user.getId())
                   .email(user.getEmail())
                   .nickname(user.getNickname())
                   .followIdList(followIdList)
                   .followingIdList(followingIdList)
                   .connectingIdList(connectingIdList)
                   .build();
        }

        public static List<Info> of(List<User> userList) {

            return userList.stream().map(Info::of).collect(Collectors.toList());
        }
    }

}
