package com.toy.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toy.blog.domain.common.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Liked extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_id")
    Long id;

    @Enumerated(EnumType.STRING)
    Status.Like status;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    User user;

    @JoinColumn(name = "article_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    Article article;
}
