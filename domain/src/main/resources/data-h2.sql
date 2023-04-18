INSERT INTO "user" ("id", "email", "password", "nickname", "phone", "status","authority", "created_at", "updated_at")
VALUES (1, 'a@naver.com', '1234', '김유성', '01012345677','NORMAL', 'ROLE_ADMIN', '2020-12-31 05:51:47', '2020-12-31 05:52:29'),
       (2, 'b@naver.com', '1234', '김가영', '01012345678','NORMAL', 'ROLE_USER', '2020-12-31 05:51:47', '2020-12-31 05:52:29'),
       (3, 'c@naver.com', '1234', '강현준', '01012345679','NORMAL', 'ROLE_USER', '2020-12-31 05:51:47', '2020-12-31 05:52:29'),
       (4, 'd@naver.com', '1234', '홍길동', '01012345670','NORMAL', 'ROLE_USER', '2020-12-31 05:51:47', '2020-12-31 05:52:29'),
       (5, 'e@naver.com', '1234', '아무개', '01012345671','DELETE', 'ROLE_USER', '2020-12-31 05:51:47', '2020-12-31 05:52:29');