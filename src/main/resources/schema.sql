CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    surname VARCHAR(100),
    role VARCHAR(20) NOT NULL CHECK (role IN ('admin', 'attendee', 'guest')),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE TABLE conferences (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    location VARCHAR(255),
    description TEXT
);
CREATE TABLE event_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(20) DEFAULT '#3f51b5',
    icon VARCHAR(50)
);
CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    conference_id INTEGER NOT NULL REFERENCES conferences(id) ON DELETE CASCADE,
    type_id INTEGER REFERENCES event_types(id) ON DELETE SET NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    room VARCHAR(100),
    max_attendees INTEGER,
    speaker_info TEXT
);
CREATE TABLE bookmarks (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(user_id, event_id)
);
CREATE TABLE event_attendance (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    attended BOOLEAN NOT NULL DEFAULT false,
    check_in_time TIMESTAMP,
    UNIQUE(user_id, event_id)
);
CREATE TABLE reports (
    id SERIAL PRIMARY KEY,
    conference_id INTEGER NOT NULL REFERENCES conferences(id) ON DELETE CASCADE,
    generated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    report_data JSONB NOT NULL,
    report_type VARCHAR(50) NOT NULL
);
CREATE INDEX idx_events_conference ON events(conference_id);
CREATE INDEX idx_events_type ON events(type_id);
CREATE INDEX idx_bookmarks_user ON bookmarks(user_id);
CREATE INDEX idx_attendance_event ON event_attendance(event_id);