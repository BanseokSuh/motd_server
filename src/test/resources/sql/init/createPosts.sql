INSERT INTO "post" (
    content,
    image_urls,
    created_at,
    updated_at,
    deleted_at,
    user_id
)
VALUES
    ('content1', ARRAY['imageUrl001', 'imageUrl001-1'], NOW(), null, null, 1),
    ('content2', ARRAY['imageUrl002', 'imageUrl002-1'], NOW(), null, null, 1),
    ('content3', ARRAY['imageUrl003', 'imageUrl003-1'], NOW(), null, null, 1),
    ('content4', ARRAY['imageUrl004', 'imageUrl004-1'], NOW(), null, null, 2),
    ('content5', ARRAY['imageUrl005', 'imageUrl005-1'], NOW(), null, null, 2),
    ('content6', ARRAY['imageUrl006', 'imageUrl006-1'], NOW(), null, null, 3),
    ('content7', ARRAY['imageUrl007', 'imageUrl007-1'], NOW(), null, null, 3),
    ('content8', ARRAY['imageUrl008', 'imageUrl008-1'], NOW(), null, null, 4),
    ('content9', ARRAY['imageUrl009', 'imageUrl009-1'], NOW(), null, null, 5),
    ('content10', ARRAY['imageUrl010', 'imageUrl010-1'], NOW(), null, null, 5);


