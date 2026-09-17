-- utun 城市地下综合管廊运维与入廊作业管理 -- schema (jia-026)
-- 列名与基线实体契约（@TableName/@TableField）逐列对齐，改列必须同步实体。
-- 库：jia_026

CREATE TABLE IF NOT EXISTS t_utun_chamber (
  id bigint NOT NULL COMMENT '主键',
  site_no varchar(64) DEFAULT NULL COMMENT '舱室编号',
  site_name varchar(128) DEFAULT NULL COMMENT '舱室名称',
  site_type varchar(32) DEFAULT NULL COMMENT '舱室类型',
  road_name varchar(128) DEFAULT NULL COMMENT '所属舱段',
  th1_max decimal(12,2) DEFAULT NULL COMMENT '巡检时限一档上限(分钟)',
  th2_max decimal(12,2) DEFAULT NULL COMMENT '巡检时限二档上限(分钟)',
  th3_max decimal(12,2) DEFAULT NULL COMMENT '巡检时限三档上限(分钟)',
  status int DEFAULT NULL COMMENT '档案状态 0在用 1停用',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管廊舱室档案';

CREATE TABLE IF NOT EXISTS t_utun_entry_order (
  id bigint NOT NULL COMMENT '主键',
  biz_no varchar(64) DEFAULT NULL COMMENT '入廊作业工单号',
  stage int DEFAULT NULL COMMENT '当前环节 0..5',
  status int DEFAULT NULL COMMENT '工单状态 0待发起 1在办 2已办结',
  content varchar(255) DEFAULT NULL COMMENT '处置记录',
  last_action varchar(64) DEFAULT NULL COMMENT '最近一次推进动作',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='入廊作业工单';

CREATE TABLE IF NOT EXISTS t_utun_entry_plan (
  id bigint NOT NULL COMMENT '主键',
  bill_no varchar(64) DEFAULT NULL COMMENT '方案编号',
  node_no int DEFAULT NULL COMMENT '当前签核层 0..2',
  sign_mode int DEFAULT NULL COMMENT '签核模式 0或签 1会签',
  need_count int DEFAULT NULL COMMENT '本层应签人数',
  sign_count int DEFAULT NULL COMMENT '本层已签人数',
  status int DEFAULT NULL COMMENT '方案状态 0签核中 1已通过 2已否决',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='入廊作业方案';

CREATE TABLE IF NOT EXISTS t_utun_gas_rule (
  id bigint NOT NULL COMMENT '主键',
  rule_code varchar(64) DEFAULT NULL COMMENT '规则编号',
  rule_name varchar(128) DEFAULT NULL COMMENT '规则名称',
  th1_max decimal(12,2) DEFAULT NULL COMMENT '安全档上限',
  th2_max decimal(12,2) DEFAULT NULL COMMENT '通风档上限',
  th3_max decimal(12,2) DEFAULT NULL COMMENT '禁入档上限',
  eff_start datetime DEFAULT NULL COMMENT '生效起始时刻',
  eff_end datetime DEFAULT NULL COMMENT '生效截止时刻(不含)',
  priority int DEFAULT NULL COMMENT '优先级(数值越大越优先)',
  status int DEFAULT NULL COMMENT '规则状态 0启用 1停用',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='有限空间气体分级规则';

CREATE TABLE IF NOT EXISTS t_utun_monitor_row (
  id bigint NOT NULL COMMENT '主键',
  batch_no varchar(64) DEFAULT NULL COMMENT '批次号',
  row_no int DEFAULT NULL COMMENT '原始行号',
  item_code varchar(64) DEFAULT NULL COMMENT '明细编码',
  qty decimal(12,2) DEFAULT NULL COMMENT '检测读数',
  status int DEFAULT NULL COMMENT '行状态 0待处理 1成功 2失败',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='舱段监测数据明细';

CREATE TABLE IF NOT EXISTS t_utun_patrol_bill (
  id bigint NOT NULL COMMENT '主键',
  bill_no varchar(64) DEFAULT NULL COMMENT '巡检单号',
  site_id int DEFAULT NULL COMMENT '所属舱室',
  site_no varchar(64) DEFAULT NULL COMMENT '舱室编号',
  qty decimal(12,2) DEFAULT NULL COMMENT '巡检时长(分钟)',
  fine_amt decimal(12,2) DEFAULT NULL COMMENT '考核分值',
  grade_level int DEFAULT NULL COMMENT '考核等级',
  status int DEFAULT NULL COMMENT '状态 0待处理 1已处理 2已办结',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管廊巡检单';

CREATE TABLE IF NOT EXISTS t_utun_patrol_task (
  id bigint NOT NULL COMMENT '主键',
  item_no varchar(64) DEFAULT NULL COMMENT '巡检任务编号',
  due_at datetime DEFAULT NULL COMMENT '应巡时刻',
  amount decimal(12,2) DEFAULT NULL COMMENT '待巡时长(分钟)',
  status int DEFAULT NULL COMMENT '状态 0待巡 1已巡 2失败',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管廊巡检任务条目';
