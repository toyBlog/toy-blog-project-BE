package com.toy.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toy.blog.domain.common.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

// Article 단건조회 시 -> 연관된 여러 댓글 조회시 -> Article 속성을 함꼐 가져오게 되는데 - 그 Article에 또 commentList가 있끼 떄문에 순환참조
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

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne //TODO : LAZT LOADING 문제 해결 해야함
    @JsonBackReference
    User user;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference
    List<ArticleImage> articleImageList = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    @JsonManagedReference
    List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "article",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

