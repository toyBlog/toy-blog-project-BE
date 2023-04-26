package com.toy.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    Long id;

    @NotNull
    @Size(min = 2, max = 20)
    String title;

    @NotNull
    @Size(min = 2, max = 200)
    String content;

    @Column(name = "view_count")
    @NotNull
    Integer viewCount;

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

    @OneToMany(mappedBy = "article",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id desc")
    @JsonManagedReference
    List<Comment> commentList = new ArrayList<>();

    /**
     * [변경 메서드]
     * */

    public void changeStatus(Status.Article status) {
        this.status = status;
    }

    public void changeTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

