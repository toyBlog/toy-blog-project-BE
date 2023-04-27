package com.toy.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toy.blog.domain.common.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String comments;

    @Enumerated(EnumType.STRING)
    Status.Comments status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference
    Article article;

    public void changeStatus(Status.Comments status) {
        this.status = status;
    }

    public void changeComments(String comments) {
        this.comments = comments;
    }
}
