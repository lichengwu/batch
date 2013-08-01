-- create table config
CREATE TABLE `config` (
  `id` int(11) NOT NULL,
  `namespace` varchar(20) NOT NULL COMMENT '命名空间',
  `name` varchar(45) NOT NULL COMMENT '配置名',
  `value` varchar(500) DEFAULT NULL COMMENT '配置值',
  `mod_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '添加时间',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS current_states (id SMALLINT PRIMARY KEY, state_code CHAR(2), name VARCHAR(50));
INSERT INTO current_states (id, state_code, name) VALUES (1, 'MA', 'Massachusetts')
ON DUPLICATE KEY UPDATE id = id;
INSERT INTO current_states (id, state_code, name) VALUES (2, 'NH', 'New Hampshire')
ON DUPLICATE KEY UPDATE id = id;
INSERT INTO current_states (id, state_code, name) VALUES (3, 'ME', 'Maine')
ON DUPLICATE KEY UPDATE id = id;
INSERT INTO current_states (id, state_code, name) VALUES (4, 'VT', 'Vermont')
ON DUPLICATE KEY UPDATE id = id;