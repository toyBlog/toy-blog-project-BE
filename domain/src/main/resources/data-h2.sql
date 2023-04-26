INSERT INTO "user" ("user_id", "email", "password", "nickname", "created_at", "updated_at", "status")
VALUES (1, 'a@naver.com', '1234', '김유성',  '2020-12-31 05:51:47', '2020-12-31 05:52:29', 'ACTIVE'),
       (2, 'b@naver.com', '1234', '김가영',  '2020-12-31 05:51:47', '2020-12-31 05:52:29', 'ACTIVE'),
       (3, 'c@naver.com', '1234', '강현준',  '2020-12-31 05:51:47', '2020-12-31 05:52:29', 'ACTIVE'),
       (4, 'd@naver.com', '1234', '홍길동',  '2020-12-31 05:51:47', '2020-12-31 05:52:29', 'ACTIVE'),
       (5, 'e@naver.com', '1234', '아무개',  '2020-12-31 05:51:47', '2020-12-31 05:52:29', 'ACTIVE');

INSERT INTO "article" ("title", "content", "view_count", "status", "user_id", "created_at", "updated_at")
VALUES  ('제목1', '내용1', 0, 'ACTIVE', 1, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
        ('제목3', '내용2', 0, 'ACTIVE', 2, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
        ('제목3', '내용3', 0, 'ACTIVE', 3, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
        ('제목4', '내용4', 0, 'ACTIVE', 4, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
        ('제목5', '내용5', 0, 'ACTIVE', 5, '2020-12-31 05:51:47', '2020-12-31 05:51:47');

INSERT INTO "article_image" ("path", "article_id", "created_at", "updated_at")
VALUES  ('/download/image1.png', 4, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
        ('/download/image2.png', 4, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
        ('/download/image3.png', 4, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
        ('/download/image4.png', 2, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
        ('/download/image5.png', 2, '2020-12-31 05:51:47', '2020-12-31 05:51:47');


INSERT INTO "liked" ("status", "user_id", "article_id", "created_at", "updated_at")
VALUES ('ACTIVE', 2, 3, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
       ('ACTIVE', 1, 3, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
       ('ACTIVE', 1, 2, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
       ('ACTIVE', 2, 1, '2020-12-31 05:51:47', '2020-12-31 05:51:47');

INSERT INTO "user_friend" ("status", "user_id", "friend_id", "created_at", "updated_at")
VALUES     ('FOLLOW', 3, 4, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
           ('FOLLOW', 4, 3, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
           ('FOLLOW', 1, 2, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
           ('FOLLOW', 2, 1, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
           ('FOLLOW', 1, 3, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
           ('FOLLOW', 1, 4, '2020-12-31 05:51:47', '2020-12-31 05:51:47'),
           ('FOLLOW', 5, 1, '2020-12-31 05:51:47', '2020-12-31 05:51:47');

