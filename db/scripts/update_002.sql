--Create a table for Candidate info
create table if not exists candidate (
                      id SERIAL PRIMARY KEY,
                      name TEXT,
                      description TEXT,
                      date_created date,
                      photo_data bytea
);