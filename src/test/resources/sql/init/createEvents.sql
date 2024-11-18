INSERT INTO "event" (
    title,
    description,
    max_participants,
    event_type,
    user_id,
    register_start_at,
    register_end_at,
    event_start_at,
    event_end_at,
    created_at,
    updated_at,
    deleted_at
)
VALUES
    ('title01', 'description01', 10, 'REGULAR', 1, '2024-11-15', '2024-11-16', '2024-12-01', '2024-12-01', NOW(), null, null),
    ('title02', 'description02', 10, 'REGULAR', 1, '2024-11-15', '2024-11-16', '2024-12-01', '2024-12-01', NOW(), null, null),
    ('title03', 'description03', 10, 'REGULAR', 1, '2024-11-15', '2024-11-16', '2024-12-01', '2024-12-01', NOW(), null, null),
    ('title04', 'description04', 10, 'REGULAR', 1, '2024-11-15', '2024-11-16', '2024-12-01', '2024-12-01', NOW(), null, null),
    ('title05', 'description05', 10, 'REGULAR', 1, '2024-11-15', '2024-11-16', '2024-12-01', '2024-12-01', NOW(), null, null);