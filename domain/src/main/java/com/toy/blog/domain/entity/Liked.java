package com.toy.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toy.blog.domain.common.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "liked")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Liked extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_id")
    Long id;

    @Enumerated(EnumType.STRING)
    Status.Like status;

    @JoinColumn(name = "user_id")
    @ManyToOne
    @JsonBackReference
    User user;

    @JoinColumn(name = "article_id")
    @ManyToOne
    @JsonBackReference
    Article article;

    /**
     * [변경 메서드]
     * */
    public void changeStatus(Status.Like status) {
        this.status = status;
    }
}
