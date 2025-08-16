-- Drop the wrong FK (if it exists) and add the correct one
ALTER TABLE time_off
DROP CONSTRAINT IF EXISTS time_off_provider_id_fkey;

ALTER TABLE time_off
    ADD CONSTRAINT time_off_provider_id_fkey
        FOREIGN KEY (provider_id) REFERENCES providers(id);