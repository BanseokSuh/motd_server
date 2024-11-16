INSERT INTO "user" (
    login_id,
    user_name,
    nick_name,
    email,
    password,
    gender,
    user_role,
    user_status,
    profile_image_url,
    created_at,
    updated_at,
    deleted_at
)
VALUES
     ('login1', 'User One', 'user1', 'user1@example.com', 'password1', 'MALE', 'USER', 'ACTIVE', 'http://example.com/user1.png', NOW(), null, null),
     ('login2', 'User Two', 'user2', 'user2@example.com', 'password2', 'FEMALE', 'USER', 'ACTIVE', 'http://example.com/user2.png', NOW(), null, null),
     ('login3', 'User Three', 'user3', 'user3@example.com', 'password3', 'MALE', 'USER', 'ACTIVE', 'http://example.com/user3.png', NOW(), null, null),
     ('login4', 'User Four', 'user4', 'user4@example.com', 'password4', 'FEMALE', 'USER', 'ACTIVE', 'http://example.com/user4.png', NOW(), null, null),
     ('login5', 'User Five', 'user5', 'user5@example.com', 'password5', 'MALE', 'USER', 'ACTIVE', 'http://example.com/user5.png', NOW(), null, null);
