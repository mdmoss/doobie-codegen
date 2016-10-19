-- Types mostly lifted from
-- http://www.postgresql.org/docs/9.4/static/datatype.html

CREATE TABLE test_types (
    bigint_t     BIGINT,
    boolean_t    BOOLEAN,
    double_t     DOUBLE PRECISION,
    integer_t    INTEGER,
    text_t       TEXT,
    timestamp_t  TIMESTAMP,
    timestampz_t TIMESTAMP WITH TIME ZONE,
    jsonb_t      JSONB,
    time_t       TIME,
    short_t      SMALLINT,
    uuid_t       UUID
);

-- We've got to have a separate test because both JSON and JSONB use argonaut.Json on the other side and can't coexist.
CREATE TABLE test_json_type(
  data JSON
);

CREATE SCHEMA schema;

CREATE TABLE schema.test (
    id BIGINT
);


CREATE TABLE test_nullible (
    always_string    TEXT NOT NULL,
    sometimes_string TEXT
);

INSERT INTO test_nullible VALUES ('always_string', 'sometimes_string');

UPDATE test_nullible SET sometimes_string='sometimes_string1';

CREATE TABLE test_pk (
    id BIGINT PRIMARY KEY
);


CREATE TABLE test_skip_bigserial (
    id1 BIGSERIAL PRIMARY KEY,
    id2 BIGINT NOT NULL
);

CREATE TABLE test_fk_1 (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE test_fk_2 (
  id BIGSERIAL PRIMARY KEY,
  fk BIGINT NOT NULL REFERENCES test_fk_1(id)
);

CREATE TABLE test_Table_With_Caps (
  id BIGSERIAL PRIMARY KEY,
  someValue TEXT
);

CREATE SCHEMA s1;

CREATE SCHEMA IF NOT EXISTS s2;

CREATE TABLE s1.test (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE s2.test2 (
  id BIGSERIAL PRIMARY KEY,
  partner BIGINT NOT NULL REFERENCES s1.test(id)
);

CREATE TABLE test_drop_1 (
  id BIGSERIAL PRIMARY KEY,
  thing INT NOT NULL
);

ALTER TABLE test_drop_1 DROP COLUMN id;

CREATE TABLE test_pk_name (
  some_complicated_name TEXT PRIMARY KEY
);

DELETE FROM test_pk_name WHERE true;

CREATE TABLE test_foreign_pk(
  name TEXT PRIMARY KEY references test_pk_name(some_complicated_name)
);

CREATE TABLE test_composite_unique(
  a TEXT NOT NULL,
  b TEXT NOT NULL,
  UNIQUE (a, b)
);

CREATE TABLE test_composite_unique_multi(
  a TEXT NOT NULL,
  b TEXT NOT NULL,
  c TEXT NOT NULL,
  UNIQUE (a, b),
  UNIQUE (a, c)
);

CREATE TABLE test_composite_foreign_key(
  a TEXT NOT NULL,
  b TEXT NOT NULL,
  FOREIGN KEY (a, b) REFERENCES test_composite_unique (a, b)
);

CREATE VIEW test_view AS SELECT * FROM test_composite_unique;

DROP VIEW test_view;

CREATE TABLE test_typical_table(
  id    BIGSERIAL PRIMARY KEY,
  words TEXT NOT NULL
);

CREATE EXTENSION postgis;

CREATE TABLE test_points(
  lat DOUBLE PRECISION NOT NULL,
  lon DOUBLE PRECISION NOT NULL,
  geom GEOMETRY NOT NULL DEFAULT ST_MakePoint(0, 0)
);

CREATE TABLE test_mutual_ref_a(
  id BIGINT PRIMARY KEY
);

CREATE TABLE test_mutual_ref_b(
  id BIGINT PRIMARY KEY,
  other BIGINT NOT NULL REFERENCES test_mutual_ref_a(id)
);

ALTER TABLE test_mutual_ref_a ADD COLUMN other BIGINT NOT NULL REFERENCES test_mutual_ref_b(id);

CREATE TABLE test_uuid_multiget (
  uuid UUID PRIMARY KEY
);

-- doobie-codegen: off

CREATE OR REPLACE FUNCTION update_modified_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.modified = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- doobie-codegen: on

-- doobie-codegen: off

CREATE OR REPLACE FUNCTION update_modified_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.modified = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- doobie-codegen: on

CREATE TABLE test_gen_options(
  id BIGSERIAL PRIMARY KEY,

  -- this should be set to never be writeable, and hence should always use the default
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- this should be given 'scala-default hello' and should use that as the default value
  thing_with_default TEXT NOT NULL DEFAULT 'HELLO',

  -- this should remain without a default
  other_thing_with_default TEXT NOT NULL DEFAULT 'HELLO',

  -- this should be given 'scala-default Some(hello)' and should use that as the default value
  nullible_thing_with_default TEXT DEFAULT 'HELLO'
);