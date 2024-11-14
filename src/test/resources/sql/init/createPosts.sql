INSERT INTO "post" (
    content,
    image_urls,
    created_at,
    updated_at,
    deleted_at,
    user_id
)
VALUES
    ('content1', '{"imageUrl001", "imageUrl002"}', NOW(), null, null, 1),
    ('content1', '{"imageUrl001", "imageUrl002"}', NOW(), null, null, 1),
    ('content1', '{"imageUrl001", "imageUrl002"}', NOW(), null, null, 1),
    ('content1', '{"imageUrl001", "imageUrl002"}', NOW(), null, null, 2),
    ('content1', '{"imageUrl001", "imageUrl002"}', NOW(), null, null, 2);