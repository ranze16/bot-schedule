
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