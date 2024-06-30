CREATE TABLE if not exists users
(
    user_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject       VARCHAR(255),
    picture_url   VARCHAR(255),
    email         VARCHAR(255),
    password      VARCHAR(255),
    name          VARCHAR(255),
    given_name    VARCHAR(255),
    family_name   VARCHAR(255),
    last_login_at DATETIME,
    enabled       TINYINT(1),
    authorities   VARCHAR(255),
    created_at    DATETIME,
    updated_at    DATETIME,
    created_by    VARCHAR(255),
    updated_by    VARCHAR(255)
);