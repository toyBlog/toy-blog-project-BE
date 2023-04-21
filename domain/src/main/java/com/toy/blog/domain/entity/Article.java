package com.toy.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toy.blog.domain.common.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Article extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    Long id;

    @NotNull
    @Size(min = 2, max = 20)
    String title;

    @NotNull @Size(min = 10, max = 200)
    String content;

    @Column(name = "view_count")
    @NotNull
    Integer viewCount;

    @Column(name = "liked_count")
    @NotNull
    Integer likedCount;

    @Enumerated(EnumType.STRING)
    Status.Article status;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    User user;

    @OneToMany(mappedBy = "article")
    List<ArticleImage> articleImageList = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    List<Liked> likedList = new ArrayList<>();
}

