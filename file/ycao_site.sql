-- ----------------------------
--  Table structure for `t_users`
-- ----------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users` (
                           `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
                           `username` varchar(32) DEFAULT NULL,
                           `password` varchar(64) DEFAULT NULL,
                           `email` varchar(200) DEFAULT NULL,
                           PRIMARY KEY (`uid`),
                           UNIQUE KEY `name` (`username`),
                           UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
-- ----------------------------
--  Table structure for `t_contents`
-- ----------------------------
DROP TABLE IF EXISTS `t_contents`;
CREATE TABLE `t_contents` (
                              `cid` int(10) unsigned NOT NULL AUTO_INCREMENT,
                              `title` varchar(200) DEFAULT NULL,
                              `titlePic` varchar(200) DEFAULT NULL,
                              `content` text COMMENT 'content of file',
                              `status` varchar(16) DEFAULT 'publish',
                              `tags` varchar(200) DEFAULT NULL,
                              `categories` varchar(200) DEFAULT NULL,
                              `dr` int(1) DEFAULT '0',
                              `uid` int(10) unsigned DEFAULT '0',
                              PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
-- ----------------------------
--  Table structure for `t_attach`
-- ----------------------------
DROP TABLE IF EXISTS `t_attach`;
CREATE TABLE `t_attach` (
                            `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                            `fname` varchar(100) NOT NULL DEFAULT '',
                            `ftype` varchar(50) DEFAULT '',
                            `fkey` text NOT NULL,
                            `authorId` int(10) DEFAULT NULL,
                            `created` int(10) NOT NULL,
                            `dr` varchar(2) default 0,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
-- ----------------------------
--  Table structure for `t_category`
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
                            `cid` int(11) unsigned NOT NULL AUTO_INCREMENT,
                            `cname` varchar(100) NOT NULL DEFAULT '',
                            `creation_time` varchar(100) NOT NULL,
                            `author_id` int(10) DEFAULT NULL,
                            `ts` varchar(100) NOT NULL,
                            `dr` varchar(2) default 0,
                            PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
-- ----------------------------
--  Table structure for `t_markdown`
-- ----------------------------
DROP TABLE IF EXISTS `t_markdown`;
CREATE TABLE `t_markdown` (
                              `mid` int(11) unsigned NOT NULL AUTO_INCREMENT,
                              `mname` varchar(100) NOT NULL DEFAULT '',
                              `creation_time` varchar(100) NOT NULL,
                              `author_id` int(10) DEFAULT NULL,
                              `content` text DEFAULT NULL,
                              `html_text` text DEFAULT NULL,
                              `rate` varchar(10) DEFAULT NULL,
                              `ts` varchar(100) NOT NULL,
                              `dr` varchar(2) default 0,
                              `pk_category` int(11) unsigned NOT NULL,
                              foreign key(`pk_category`) references t_category(`cid`),
                              PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
-- ----------------------------
--  Table structure for `t_constant`
-- ----------------------------
DROP TABLE IF EXISTS `t_constant`;
CREATE TABLE `t_constant` (
                              `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                              `code` varchar(100) NOT NULL DEFAULT '',
                              `name` varchar(100) NOT NULL DEFAULT '',
                              `creation_time` varchar(100) NOT NULL,
                              `creator_id` int(10) DEFAULT NULL,
                              `content` text DEFAULT NULL,
                              `domain` varchar(100),
                              `ts` varchar(100) NOT NULL,
                              `dr` varchar(2) default 0,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
-- ----------------------------
--  trigger for t_category
--  after delete a category(dr=1) we will delete its related file(dr=1)
--  attention!!! 没有定义查询界定符导致，导致编译器将;识别为全部语句的结束，导致BEGIN 匹配不到END，于是报错
-- ----------------------------
DELIMITER |
create trigger delete_md
    after update on t_category for each row
begin
    if (new.dr = 1) then
        update t_markdown set dr=1 where t_markdown.pk_category=old.cid;
end if;
end; |


