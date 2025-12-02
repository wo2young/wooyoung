-- ================================
-- board_user
-- ================================
CREATE TABLE board_user (
    user_id      VARCHAR PRIMARY KEY,
    user_pw      VARCHAR NOT NULL,
    username     VARCHAR NOT NULL,
    created_at   TIMESTAMP
);


-- ================================
-- board
-- ================================
CREATE TABLE board (
    board_id    INT PRIMARY KEY,
    title       VARCHAR NOT NULL,
    user_id     VARCHAR,
    views       INT,
    like        INT,
    created_at  TIMESTAMP,
    content     VARCHAR,
    updated_at  TIMESTAMP,
    status      VARCHAR,
    CONSTRAINT fk_board_user
        FOREIGN KEY (user_id)
        REFERENCES board_user (user_id)
);


-- ================================
-- board_like
-- ================================
CREATE TABLE board_like (
    like_id     INT PRIMARY KEY,
    board_id    INT,
    user_id     VARCHAR,
    status      VARCHAR,
    created_at  TIMESTAMP,
    
    -- UNQIUE: 한 유저가 한 게시글에 좋아요 1번만 가능
    CONSTRAINT uq_board_like UNIQUE (board_id, user_id),

    -- FK: 게시글
    CONSTRAINT fk_boardlike_board
        FOREIGN KEY (board_id)
        REFERENCES board (board_id),

    -- FK: 유저
    CONSTRAINT fk_boardlike_user
        FOREIGN KEY (user_id)
        REFERENCES board_user (user_id)
);


-- ================================
-- board_comment
-- ================================
CREATE TABLE board_coment (
    coment_id   INT PRIMARY KEY,
    board_id    INT,
    content     VARCHAR,
    parent_id   INT,
    user_id     VARCHAR,
    status      VARCHAR,
    created_at  TIMESTAMP,

    -- FK: 게시글
    CONSTRAINT fk_comment_board
        FOREIGN KEY (board_id)
        REFERENCES board (board_id),

    -- FK: 작성자
    CONSTRAINT fk_comment_user
        FOREIGN KEY (user_id)
        REFERENCES board_user (user_id),

    -- FK: 부모 댓글 (대댓글)
    CONSTRAINT fk_comment_parent
        FOREIGN KEY (parent_id)
        REFERENCES board_coment (coment_id)
        ON DELETE CASCADE
);
