CREATE TABLE IF NOT EXISTS "user" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    login_id VARCHAR(100) NOT NULL UNIQUE,
    user_name VARCHAR(100) NOT NULL,
    nick_name VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    user_role VARCHAR(10) NOT NULL,
    user_status VARCHAR(10) NOT NULL,
    profile_image_url VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT uk_user_login_id UNIQUE (login_id)
);

CREATE TABLE IF NOT EXISTS "post" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    image_urls VARCHAR(255) ARRAY NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE IF NOT EXISTS "event" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    image_url VARCHAR(100),
    description TEXT NOT NULL,
    max_participants INT NOT NULL,
    event_type VARCHAR(10) NOT NULL,
    user_id BIGINT NOT NULL,
    register_start_at TIMESTAMP NOT NULL,
    register_end_at TIMESTAMP NOT NULL,
    event_start_at TIMESTAMP NOT NULL,
    event_end_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);

