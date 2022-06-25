--Create a table for Post info
create table if not exists post (
                     id SERIAL PRIMARY KEY,
                      name TEXT,
                      city int references cities(id) not null,
                      description TEXT,
                      is_published boolean,
                      date_created date,
                      date_updated date
);