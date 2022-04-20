-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.26 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 bootshop 的数据库结构
CREATE DATABASE IF NOT EXISTS `bootshop` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `bootshop`;

-- 导出  表 bootshop.book 结构
CREATE TABLE IF NOT EXISTS `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `img` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '书的图片',
  `sketch` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '简述',
  `info` varchar(500) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '详细介绍',
  `tag` set('文学','生活','计算机','外语','经营','励志','社科','少儿','艺术','原版','科技','考试','生活百科') COLLATE utf8_unicode_ci NOT NULL DEFAULT '文学' COMMENT '书的类型',
  `stock` int(10) unsigned DEFAULT '0' COMMENT '库存',
  `price` int(10) unsigned DEFAULT '0',
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  bootshop.book 的数据：4 rows
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` (`id`, `name`, `img`, `sketch`, `info`, `tag`, `stock`, `price`) VALUES
	(1, 'jvav重入门到入土', 'http://img3m2.ddimg.cn/39/35/28486002-1_u_72.jpg', '看了这本就会写jvav了（大概）', '产品特色\r\n\r\n\r\n\r\n![img](http://img59.ddimg.cn/99999990075375659.jpg)\r\n\r\n编辑推荐\r\n\r\n\r\n\r\n1.古人在我国湖光山色中都留下了哪些脍炙人口的诗词？追寻古人的足迹，踏着春花夏草，赏名山大川，品味诗风词韵。\r\n2.贴心的旅游小贴士，对旅行地的地理位置、*旅行时间和当地的特色美食，给予了详细的介绍和合理的推荐，方便读者在旅行前做好充足的准备，享受一趟完美的旅程。\r\n3.本书的图片质量上乘，精美雅致，带领读者欣赏山水间的极致美景。\r\n\r\n内容简介\r\n\r\n\r\n\r\n本书以诗词为“向导”，精选中国30余个非常具有代表性的山水风光与旅行胜地，让读者在诗词营造的唯美意境里感受祖国大好河山的清婉与壮美，在青山绿水、碧树繁花间感悟诗词的无穷魅力。书中运用了大量的精美图片，不但给人一种视觉上的审美享受，更让人有一种身临其境之感。贴心的旅行小贴士更是书中的一抹亮色，力求帮助读者更为全面了解山东。让你在对的时间，遇见对的风景。\r\n\r\n作者简介\r\n\r\n\r\n\r\n郝娟菡，女，南京师范大学文学硕士。长期从事业余撰稿工作，酷爱读书，广泛涉猎人文社科、历史地理、', '计算机', 999, 100),
	(2, '测试图书1', 'http://img3m2.ddimg.cn/39/35/28486002-4_u_81.jpg', '简略介绍', '详细介绍', '文学', 999, 50),
	(3, '测试图书2', 'https://s1.ax1x.com/2022/04/12/LnkReI.jpg', '我是原P', '', '生活', 999, 2),
	(4, '测试图书3', 'https://s1.ax1x.com/2022/04/12/LnkReI.jpg', '简略介绍2', '', '外语', 0, 0);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

-- 导出  表 bootshop.bookorder 结构
CREATE TABLE IF NOT EXISTS `bookorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单编号',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `bookid` int(11) DEFAULT NULL COMMENT '书id',
  `num` int(11) DEFAULT NULL COMMENT '数量',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收件名',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `addr` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `cod` set('未支付','已支付','已发货','已签收') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '未支付',
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  bootshop.bookorder 的数据：2 rows
/*!40000 ALTER TABLE `bookorder` DISABLE KEYS */;
INSERT INTO `bookorder` (`id`, `orderid`, `userid`, `bookid`, `num`, `name`, `phone`, `addr`, `cod`) VALUES
	(8, 'df68e714-8bd3-49e5-bb0e-ec0dab2f0cfd', 1, 3, 1, '', '', '', '未支付'),
	(9, 'ab924205-54b9-4f37-bb5c-7ca5ccd88b12', 5, 2, 1, '小徐', '13393280310', '阿不啵啵啵啵啵啵试试', '已支付');
/*!40000 ALTER TABLE `bookorder` ENABLE KEYS */;

-- 导出  表 bootshop.notice 结构
CREATE TABLE IF NOT EXISTS `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='公告';

-- 正在导出表  bootshop.notice 的数据：2 rows
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` (`id`, `title`, `content`, `time`) VALUES
	(1, '施工进度', '## 施工进度  \r\n\r\n* 用户系统  \r\n  \r\n   - [x] 登录与注册  \r\n   - [ ] 密码找回  \r\n   - [ ] 购物车  \r\n  \r\n* 主页  \r\n  \r\n  - [ ] 首页轮播图  \r\n  - [ ] 搜索  \r\n  - [ ] 公告  \r\n  - [ ] 热卖手机  \r\n\r\n* 书籍\r\n - [ ] 书籍介绍页  ', '2022-04-08 21:31:23'),
	(2, '测试标题', '测试内容', '2022-04-08 21:49:37');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;

-- 导出  表 bootshop.shop 结构
CREATE TABLE IF NOT EXISTS `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `unitprice` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `Totalprice` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='购物车系统';

-- 正在导出表  bootshop.shop 的数据：0 rows
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;

-- 导出  表 bootshop.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_password` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mail` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` set('男','女') COLLATE utf8_unicode_ci DEFAULT '男',
  `introduce` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mailcod` set('未验证','验证') COLLATE utf8_unicode_ci DEFAULT '未验证',
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  UNIQUE KEY `user_name` (`user_name`),
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  bootshop.user 的数据：2 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `user_name`, `user_password`, `mail`, `sex`, `introduce`, `mailcod`, `phone`) VALUES
	(1, 'admin', '123456', '2180323481@qq.com', '男', '我是管理！', '验证', '1008611'),
	(5, 'text0', '1234567', '2180323481@qq.com', '男', '', '验证', '');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
