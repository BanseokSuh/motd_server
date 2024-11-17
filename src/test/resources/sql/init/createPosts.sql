INSERT INTO "post" (
    content,
    image_urls,
    created_at,
    updated_at,
    deleted_at,
    user_id
)
VALUES
    ('content01', ARRAY['imageUrl001', 'imageUrl001-1'], NOW(), null, null, 1),
    ('content02', ARRAY['imageUrl002', 'imageUrl002-1'], NOW(), null, null, 1),
    ('content03', ARRAY['imageUrl003', 'imageUrl003-1'], NOW(), null, null, 1),
    ('content04', ARRAY['imageUrl004', 'imageUrl004-1'], NOW(), null, null, 2),
    ('content05', ARRAY['imageUrl005', 'imageUrl005-1'], NOW(), null, null, 2),
    ('content06', ARRAY['imageUrl006', 'imageUrl006-1'], NOW(), null, null, 3),
    ('content07', ARRAY['imageUrl007', 'imageUrl007-1'], NOW(), null, null, 3),
    ('content08', ARRAY['imageUrl008', 'imageUrl008-1'], NOW(), null, null, 4),
    ('content09', ARRAY['imageUrl009', 'imageUrl009-1'], NOW(), null, null, 5),
    ('content10', ARRAY['imageUrl010', 'imageUrl010-1'], NOW(), null, null, 5);


