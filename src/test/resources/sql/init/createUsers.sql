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
     ('login_admin', 'Admin Choe', 'admin_nick', 'admin@example.com', 'password01', 'FEMALE', 'ADMIN', 'ACTIVE', 'http://example.com/admin.png', NOW(), null, null),
     ('login01', 'User One', 'user01', 'user01@example.com', 'password01', 'FEMALE', 'USER', 'ACTIVE', 'http://example.com/user01.png', NOW(), null, null),
     ('login02', 'User Two', 'user02', 'user02@example.com', 'password02', 'FEMALE', 'USER', 'ACTIVE', 'http://example.com/user02.png', NOW(), null, null),
     ('login03', 'User Three', 'user03', 'user03@example.com', 'password03', 'MALE', 'USER', 'ACTIVE', 'http://example.com/user03.png', NOW(), null, null),
     ('login04', 'User Four', 'user04', 'user04@example.com', 'password04', 'FEMALE', 'USER', 'ACTIVE', 'http://example.com/user04.png', NOW(), null, null),
     ('login05', 'User Five', 'user05', 'user05@example.com', 'password05', 'MALE', 'USER', 'ACTIVE', 'http://example.com/user05.png', NOW(), null, null);
