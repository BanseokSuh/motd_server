-- Disable foreign key checks
SET REFERENTIAL_INTEGRITY FALSE;

-- Truncate the user and post tables
TRUNCATE TABLE "user" RESTART IDENTITY;
TRUNCATE TABLE "post" RESTART IDENTITY;

-- Re-enable foreign key checks
SET REFERENTIAL_INTEGRITY TRUE;
