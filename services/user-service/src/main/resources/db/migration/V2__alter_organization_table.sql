-- Add new columns
ALTER TABLE organizations
ADD COLUMN description VARCHAR(500);

ALTER TABLE organizations
ADD COLUMN status VARCHAR(20);

ALTER TABLE organizations
ADD COLUMN version BIGINT NOT NULL DEFAULT 0;

-- Make email mandatory
ALTER TABLE organizations
ALTER COLUMN email SET NOT NULL;

-- Change column lengths
ALTER TABLE organizations
ALTER COLUMN name TYPE VARCHAR(100);

ALTER TABLE organizations
ALTER COLUMN code TYPE VARCHAR(30);

ALTER TABLE organizations
ALTER COLUMN email TYPE VARCHAR(100);

ALTER TABLE organizations
ALTER COLUMN phone TYPE VARCHAR(20);

-- Remove old unique constraint on code
ALTER TABLE organizations
DROP CONSTRAINT IF EXISTS organizations_code_key;

-- Add named unique constraints
ALTER TABLE organizations
ADD CONSTRAINT uk_organization_name UNIQUE (name);

ALTER TABLE organizations
ADD CONSTRAINT uk_organization_code UNIQUE (code);

ALTER TABLE organizations
ADD CONSTRAINT uk_organization_email UNIQUE (email);

-- Optional: initialize status for existing records
UPDATE organizations
SET status = 'ACTIVE'
WHERE status IS NULL;

-- Make status mandatory
ALTER TABLE organizations
ALTER COLUMN status SET NOT NULL;
