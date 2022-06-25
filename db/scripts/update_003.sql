--Create a table for Cities info
create table if not exists cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50)
);
--Fill table for Cities
insert into cities(name)
values
        ('<>'),
        ('Москва'),
        ('СПб'),
        ('Екб');