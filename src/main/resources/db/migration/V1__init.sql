CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name TEXT NOT NULL,
                       email TEXT UNIQUE NOT NULL,
                       password_hash TEXT NOT NULL,
                       role TEXT NOT NULL CHECK (role IN ('ADMIN','PROVIDER','CUSTOMER')),
                       created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE providers (
                           id BIGSERIAL PRIMARY KEY,
                           user_id BIGINT NOT NULL REFERENCES users(id),
                           display_name TEXT NOT NULL,
                           location TEXT,
                           phone TEXT
);

CREATE TABLE services (
                          id BIGSERIAL PRIMARY KEY,
                          provider_id BIGINT NOT NULL REFERENCES providers(id),
                          name TEXT NOT NULL,
                          duration_min INT NOT NULL CHECK (duration_min > 0),
                          price NUMERIC(10,2) NOT NULL DEFAULT 0,
                          is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE availability (
                              id BIGSERIAL PRIMARY KEY,
                              provider_id BIGINT NOT NULL REFERENCES providers(id),
                              weekday INT NOT NULL CHECK (weekday BETWEEN 0 AND 6),
                              start_time TIME NOT NULL,
                              end_time TIME NOT NULL
);

CREATE TABLE time_off (
                          id BIGSERIAL PRIMARY KEY,
                          provider_id BIGINT NOT NULL REFERENCES users(id),
                          start_datetime TIMESTAMP NOT NULL,
                          end_datetime TIMESTAMP NOT NULL,
                          reason TEXT
);

CREATE TABLE bookings (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL REFERENCES users(id),
                          provider_id BIGINT NOT NULL REFERENCES providers(id),
                          service_id BIGINT NOT NULL REFERENCES services(id),
                          start_datetime TIMESTAMP NOT NULL,
                          end_datetime TIMESTAMP NOT NULL,
                          status TEXT NOT NULL CHECK (status IN ('PENDING','CONFIRMED','CANCELLED')),
                          created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE preferences (
                             id BIGSERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL REFERENCES users(id),
                             preferred_days_of_week INT[],
                             preferred_hours_range INT[],
                             notes TEXT
);

CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL REFERENCES users(id),
                               type TEXT NOT NULL CHECK (type IN ('EMAIL','SMS')),
                               payload_json TEXT,
                               status TEXT NOT NULL DEFAULT 'SCHEDULED',
                               scheduled_for TIMESTAMP,
                               sent_at TIMESTAMP
);

CREATE TABLE audit_logs (
                            id BIGSERIAL PRIMARY KEY,
                            actor_user_id BIGINT REFERENCES users(id),
                            action TEXT NOT NULL,
                            entity TEXT NOT NULL,
                            entity_id BIGINT,
                            meta_json TEXT,
                            created_at TIMESTAMP NOT NULL DEFAULT NOW()
);