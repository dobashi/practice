-- For H2 Database
create table users (
  id bigserial not null primary key,
  name varchar(512) not null,
  email varchar(512) not null,
  created_at timestamp not null,
  updated_at timestamp not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
