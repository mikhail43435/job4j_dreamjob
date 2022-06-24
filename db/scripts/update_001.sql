create table if not exists cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50)
);

insert into cities(name)
values
        ('<>'),
        ('Москва'),
        ('СПб'),
        ('Екб');

create table if not exists post (
                      id SERIAL PRIMARY KEY,
                      name TEXT,
                      city int references cities(id) not null,
                      description TEXT,
                      is_published boolean,
                      date_created date,
                      date_updated date
);

create table if not exists log4j_messages (
                      id SERIAL PRIMARY KEY,
                      message TEXT,
                      class TEXT,
                      priority TEXT,
                      log_date TIMESTAMP
);