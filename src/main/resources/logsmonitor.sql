create table powerapps_selogreport(
  id BIGINT PRIMARY KEY not null,
  line_number BIGINT,
  log_name VARCHAR(255),
  date TIMESTAMP,
  time TIME,
  where_occurred VARCHAR(255),
  user_pc_name VARCHAR(255),
  notification_sent VARCHAR(255)
);
create table powerapps_dclogreport(
  id BIGINT PRIMARY KEY not null,
  line_number BIGINT,
  log_name VARCHAR(255),
  date TIMESTAMP,
  time TIME,
  where_occurred VARCHAR(255),
  user_pc_name VARCHAR(255),
  notification_sent VARCHAR(255)
);
create table powerapps_bmlogreport(
  id BIGINT PRIMARY KEY not null,
  line_number BIGINT,
  log_name VARCHAR(255),
  date TIMESTAMP,
  time TIME,
  where_occurred VARCHAR(255),
  user_pc_name VARCHAR(255),
  notification_sent VARCHAR(255)
);

create SEQUENCE powerapps_selogreport_id;
CREATE SEQUENCE powerapps_dclogreport_id;
CREATE  SEQUENCE powerapps_bmlogreport_id;

create table powerapps_logsettings(
  id BIGINT PRIMARY KEY not null,
  log_file_location VARCHAR(255),
  log_file_namen VARCHAR(255),
  regular_expression VARCHAR(255),
  last_line_processed VARCHAR(255)
);

CREATE  SEQUENCE powerapps_logsettings_id;

create table powerapps_fixed_interval(
  id BIGINT PRIMARY KEY not null,
  time_in_milliseconds BIGINT
);

create table powerapps_email_scheduler_config(
  id BIGINT PRIMARY KEY not null,
  year BIGINT,
  month BIGINT,
  day BIGINT,
  hour bigint,
  minute BIGINT,
  second bigint
);