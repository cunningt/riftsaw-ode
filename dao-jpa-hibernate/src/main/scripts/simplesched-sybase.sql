-- Apache ODE - SimpleScheduler Database Schema
-- 
-- Apache Derby scripts by Maciej Szefler.
-- 
-- 

CREATE TABLE ODE_JOB (
  jobid CHAR(64)  NOT NULL DEFAULT '',
  ts BIGINT  NOT NULL DEFAULT 0,
  nodeid char(64),
  scheduled int  NOT NULL DEFAULT 0,
  transacted int  NOT NULL DEFAULT 0,

  instanceId BIGINT,
  mexId varchar(255),
  processId varchar(255),
  type varchar(255),
  channel varchar(255),
  correlatorId varchar(255),
  correlationKeySet varchar(255),
  retryCount int,
  inMem int,
  detailsExt IMAGE null,

  PRIMARY KEY(jobid));

CREATE INDEX IDX_ODE_JOB_TS ON ODE_JOB(ts);
CREATE INDEX IDX_ODE_JOB_NODEID ON ODE_JOB(nodeid);