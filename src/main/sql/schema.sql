
-- 创建用户信息表
CREATE TABLE user_info (
id bigint NOT NULL,
gmt_create datetime DEFAULT CURRENT_TIMESTAMP,
gmt_modified datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
user_id varchar(10) NOT NULL COMMENT 'DuerOS 生成的 user id',
nick_name varchar(30) NOT NULL COMMENT '昵称',
PRIMARY KEY (id),
UNIQUE KEY user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- 创建任务表
CREATE TABLE task (
id bigint NOT NULL,
gmt_create datetime DEFAULT CURRENT_TIMESTAMP,
gmt_modified datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
user_id varchar(10) NOT NULL COMMENT '任务所属的 user id',
bind_type tinyint NOT NULL COMMENT '1:主任务, 2:附属任务',
bind_task_id bigint NOT NULL DEFAULT -1 COMMENT '此任务绑定的其他任务 id, 两个任务可以绑定',
type tinyint NOT NULL COMMENT '任务的类型：1 表示 单次任务；2 表示一段时间内的任务；3 表示长期任务',
start_time date NOT NULL DEFAULT '0000-01-01' COMMENT '时间段任务的开始日期',
end_time date NOT NULL DEFAULT '0000-01-01' COMMENT '时间段任务的结束日期',
single_time datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '单次任务的具体日期时间',
time_in_day time NOT NULL DEFAULT '00:00:00' COMMENT '每天执行任务的具体时间，如10点',
content varchar(50) NOT NULL COMMENT '任务的具体内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';