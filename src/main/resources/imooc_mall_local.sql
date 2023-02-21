
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table imooc_mall_cart
# ------------------------------------------------------------

DROP TABLE IF EXISTS `imooc_mall_cart`;

CREATE TABLE `imooc_mall_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '購物車id',
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `user_id` int(11) NOT NULL COMMENT '用戶id',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '商品數量',
  `selected` int(11) NOT NULL DEFAULT '1' COMMENT '是否已勾選：0代表未勾選，1代表已勾選',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='購物車';



# Dump of table imooc_mall_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `imooc_mall_category`;

CREATE TABLE `imooc_mall_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '分類目錄名稱',
  `type` int(11) NOT NULL COMMENT '分類目錄級別，例如1代表一級，2代表二級，3代表三級',
  `parent_id` int(11) NOT NULL COMMENT '父id，也就是上一級目錄的id，如果是一級目錄，那麼父id為0',
  `order_num` int(11) NOT NULL COMMENT '目錄展示時的排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='商品分類 ';

LOCK TABLES `imooc_mall_category` WRITE;
/*!40000 ALTER TABLE `imooc_mall_category` DISABLE KEYS */;

INSERT INTO `imooc_mall_category` (`id`, `name`, `type`, `parent_id`, `order_num`, `create_time`, `update_time`)
VALUES
	(3,'新鮮水果',1,0,1,'2019-12-18 01:17:00','2019-12-28 17:11:26'),
	(4,'橘子橙子',2,3,1,'2019-12-18 01:17:00','2019-12-28 16:25:10'),
	(5,'海鮮水產',1,0,2,'2019-12-18 01:17:00','2019-12-28 16:25:20'),
	(6,'精選肉類',1,0,3,'2019-12-18 01:17:00','2019-12-28 16:25:21'),
	(7,'螃蟹',2,5,1,'2019-12-18 01:17:00','2019-12-28 16:25:15'),
	(8,'魚類',2,5,2,'2019-12-18 01:17:00','2019-12-28 16:25:16'),
	(9,'冷飲凍食',1,0,4,'2019-12-20 13:45:28','2019-12-28 16:25:22'),
	(10,'蔬菜蛋品',1,0,5,'2019-12-20 13:45:28','2019-12-28 16:25:23'),
	(11,'草莓',2,3,2,'2019-12-18 01:17:00','2019-12-28 15:44:42'),
	(12,'奇異果',2,3,3,'2019-12-18 01:17:00','2019-12-28 16:25:12'),
	(13,'海參',2,5,3,'2019-12-18 01:17:00','2019-12-28 16:25:17'),
	(14,'車厘子',2,3,4,'2019-12-18 01:17:00','2019-12-28 16:25:12'),
	(15,'火鍋食材',2,27,5,'2019-12-18 01:17:00','2020-02-11 00:42:33'),
	(16,'牛羊肉',2,6,1,'2019-12-18 01:17:00','2019-12-28 16:25:18'),
	(17,'冰淇淋',2,9,1,'2019-12-18 01:17:00','2019-12-28 16:25:18'),
	(18,'蔬菜綜合',2,10,1,'2019-12-18 01:17:00','2020-02-11 00:48:27'),
	(19,'果凍橙',3,4,1,'2019-12-18 01:17:00','2020-02-11 00:37:02'),
	(27,'美味菌菇',1,0,7,'2019-12-20 13:45:28','2020-02-10 23:20:36'),
	(28,'其他水果',2,3,4,'2019-12-18 01:17:00','2019-12-28 16:25:12');

/*!40000 ALTER TABLE `imooc_mall_category` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table imooc_mall_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `imooc_mall_order`;

CREATE TABLE `imooc_mall_order` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '主鍵id',
  `order_no` varchar(128) NOT NULL DEFAULT '' COMMENT '訂單號（非主鍵id）',
  `user_id` int(64) NOT NULL COMMENT '用戶id',
  `total_price` int(64) NOT NULL COMMENT '訂單總價格',
  `receiver_name` varchar(32) NOT NULL COMMENT '收貨人姓名快照',
  `receiver_mobile` varchar(32) NOT NULL COMMENT '收貨人手機號快照',
  `receiver_address` varchar(128) NOT NULL DEFAULT '' COMMENT '收貨地址快照',
  `order_status` int(10) NOT NULL DEFAULT '10' COMMENT '訂單狀態: 0用戶已取消，10未付款（初始狀態），20已付款，30已發貨，40交易完成',
  `postage` int(10) DEFAULT '0' COMMENT '運費，默認為0',
  `payment_type` int(4) NOT NULL DEFAULT '1' COMMENT '支付類型,1-在線支付',
  `delivery_time` timestamp NULL DEFAULT NULL COMMENT '發貨時間',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付時間',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '交易完成時間',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='訂單表;';



# Dump of table imooc_mall_order_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `imooc_mall_order_item`;

CREATE TABLE `imooc_mall_order_item` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '主鍵id',
  `order_no` varchar(128) NOT NULL DEFAULT '' COMMENT '歸屬訂單id',
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `product_name` varchar(100) NOT NULL DEFAULT '' COMMENT '商品名稱',
  `product_img` varchar(128) NOT NULL DEFAULT '' COMMENT '商品圖片',
  `unit_price` int(11) NOT NULL COMMENT '單價（下單時的快照）',
  `quantity` int(10) NOT NULL DEFAULT '1' COMMENT '商品數量',
  `total_price` int(11) NOT NULL DEFAULT '0' COMMENT '商品總價',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='訂單的商品表 ';



# Dump of table imooc_mall_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `imooc_mall_product`;

CREATE TABLE `imooc_mall_product` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '商品主鍵id',
  `name` varchar(100) NOT NULL COMMENT '商品名稱',
  `image` varchar(500) NOT NULL DEFAULT '' COMMENT '產品圖片,相對路徑地址',
  `detail` varchar(500) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '商品詳情',
  `category_id` int(11) NOT NULL COMMENT '分類id',
  `price` int(11) NOT NULL COMMENT '價格,單位-分',
  `stock` int(11) NOT NULL COMMENT '庫存數量',
  `status` int(6) NOT NULL DEFAULT '1' COMMENT '商品上架狀態：0-下架，1-上架',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='商品表';

LOCK TABLES `imooc_mall_product` WRITE;
/*!40000 ALTER TABLE `imooc_mall_product` DISABLE KEYS */;

INSERT INTO `imooc_mall_product` (`id`, `name`, `image`, `detail`, `category_id`, `price`, `stock`, `status`, `create_time`, `update_time`)
VALUES
	(2,'澳洲進口大黑車厘子大櫻桃包甜黑櫻桃大果多汁 500g 特大果','http://111.231.103.117:8081/images/chelizi2.jpg','商品毛重：1.0kg貨號：608323093445原產地：智利類別：美早熱賣時間：1月，11月，12月國產/進口：進口售賣方式：單品',14,50,100,1,'2019-12-18 16:08:15','2020-02-11 00:08:25'),
	(3,'茶樹菇 美味菌菇 東北山珍 500g','http://111.231.103.117:8081/images/chashugu.jpg','商品名：茶樹菇 商品特點：美味菌菇 東北山珍 500g',15,1000,6,1,'2019-12-18 16:10:50','2020-02-11 00:42:42'),
	(14,'Zespri佳沛 新西蘭陽光金奇異果 6個裝','http://111.231.103.117:8081/images/mihoutao2.jpg','商品編號：4635056商品毛重：0.71kg商品產地：新西蘭類別：金果包裝：簡裝國產/進口：進口原產地：新西蘭',12,39,77,1,'2019-12-18 16:11:13','2020-02-10 23:36:48'),
	(17,'紅顏奶油草莓 約重500g/20-24顆 新鮮水果','http://111.231.103.117:8081/images/caomei2.jpg','商品毛重：0.58kg商品產地：丹東/南通/武漢類別：紅顏草莓包裝：簡裝國產/進口：國產',11,99,84,1,'2019-12-18 16:11:13','2020-02-10 23:37:48'),
	(21,'智利原味三文魚排（大西洋鮭）240g/袋 4片裝','http://111.231.103.117:8081/images/sanwenyu2.jpg','商品毛重：260.00g商品產地：中國大陸保存狀態：冷凍國產/進口：進口包裝：簡裝類別：三文魚海水/淡水：海水烹飪建議：煎炸，蒸菜，燒烤原產地：智利',8,499,1,1,'2019-12-28 15:13:07','2020-02-10 23:38:46'),
	(22,'即食海參大連野生遼刺參 新鮮速食 特級生鮮海產 60~80G','http://111.231.103.117:8081/images/haishen.jpg','商品毛重：1.5kg商品產地：中國大陸貯存條件：冷凍重量：50-99g國產/進口：國產適用場景：養生滋補包裝：袋裝原產地：遼寧年限：9年以上等級：特級食品工藝：冷凍水產熱賣時間：9月類別：即食海參固形物含量：70%-90%特產品類：大連海參售賣方式：單品',13,699,3,1,'2019-12-28 15:16:29','2020-02-11 00:04:29'),
	(23,'澳大利亞直采鮮橙 精品澳橙12粒 單果130-180g','http://111.231.103.117:8081/images/chengzi.jpg','商品毛重：2.27kg商品產地：澳大利亞類別：臍橙包裝：簡裝國產/進口：進口原產地：澳大利亞',4,12,12,1,'2019-12-28 16:02:13','2020-02-11 00:40:15'),
	(24,'智利帝王蟹禮盒裝4.4-4.0斤/只 生鮮活鮮熟凍大螃蟹','http://111.231.103.117:8081/images/diwangxie.jpg','商品毛重：3.0kg商品產地：智利大閘蟹售賣方式：公蟹重量：2000-4999g套餐份量：5人份以上國產/進口：進口海水/淡水：海水烹飪建議：火鍋，炒菜，燒烤，刺身，加熱即食包裝：簡裝原產地：智利保存狀態：冷凍公單蟹重：5.5兩及以上分類：帝王蟹特產品類：其它售賣方式：單品',7,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:05:05'),
	(25,'新疆庫爾勒克倫生無籽紅提 國產新鮮紅提葡萄 提子 5斤裝','http://111.231.103.117:8081/images/hongti.jpg','商品毛重：2.5kg商品產地：中國大陸貨號：XZL201909002重量：2000-3999g套餐份量：2人份國產/進口：國產是否有機：非有機單箱規格：3個裝，4個裝，5個裝類別：紅提包裝：簡裝原產地：中國大陸售賣方式：單品',28,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:44:05'),
	(26,'越南進口紅心火龍果 4個裝 紅肉中果 單果約330-420g','http://111.231.103.117:8081/images/hongxinhuolongguo.jpg','商品毛重：1.79kg商品產地：越南重量：1000-1999g類別：紅心火龍果包裝：簡裝國產/進口：進口',28,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:44:11'),
	(27,'內蒙古羔羊肉串 500g/袋（約20串）鮮凍羊肉串 BBQ燒烤食材','http://111.231.103.117:8081/images/yangrouchuan.jpg','商品毛重：0.585kg商品產地：內蒙古巴彥淖爾市保存狀態：冷凍重量：500-999g套餐份量：3人份國產/進口：國產烹飪建議：燒烤原產地：內蒙古品種：其它熱賣時間：4月，5月，6月，7月，8月，9月，10月，11月，12月飼養方式：圈養類別：羊肉串包裝：簡裝套餐周期：12個月',16,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:11:30'),
	(28,'瑪琪摩爾新西蘭進口冰淇淋大桶裝','http://111.231.103.117:8081/images/bingqilin.jpg','商品毛重：1.04kg商品產地：新西蘭國產/進口：進口包裝：量販裝',17,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:10:40'),
	(29,'西蘭花沙拉菜 350g 甜玉米粒 青豆豌豆 胡蘿蔔冷凍方便蔬菜','http://111.231.103.117:8081/images/shalacai.jpg','商品毛重：370.00g商品產地：浙江寧波重量：500g以下套餐份量：家庭裝類別：速凍玉米/豌豆包裝：簡裝烹飪建議：炒菜，燉菜，煎炸，蒸菜售賣方式：單品',18,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:34:01'),
	(36,'四川果凍橙 吹彈可破','http://111.231.103.117:8081/images/guodongcheng.jpg','商品毛重：370.00g商品產地：四川 重量：1000g',19,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:38:14'),
	(37,'進口牛油果 中果6粒裝 單果約130-160g ','http://111.231.103.117:8081/images/niuyouguo.jpg','商品名稱：京覓進口牛油果 6個裝商品編號：3628240商品毛重：1.2kg商品產地：秘魯、智利、墨西哥重量：1000g以下國產/進口：進口',28,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:47:42'),
	(38,'中街1946網紅雪糕冰淇淋','http://111.231.103.117:8081/images/bingqilin2.jpg','商品名稱：中街1946網紅雪糕冰淇淋樂享系列半巧*5牛乳*5阿棕*2冰激凌冷飲冰棍冰棒商品編號：52603405444店鋪： 中街1946官方旗艦店商品毛重：1.3kg商品產地：中國大陸國產/進口：國產包裝：量販裝售賣方式：組合',17,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:50:54'),
	(39,'福建六鰲紅薯5斤','http://111.231.103.117:8081/images/hongshu.jpg','商品名稱：京覓福建六鰲紅薯5斤商品編號：4087121商品毛重：2.8kg商品產地：福建省漳浦縣六鰲鎮重量：2500g及以上烹飪建議：煎炸，蒸菜，燒烤包裝：簡裝分類：地瓜/紅薯售賣方式：單品',18,40,222,1,'2019-12-28 16:06:34','2020-02-11 00:51:59'),
	(40,'胡蘿蔔','http://111.231.103.117:8081/images/huluobo.jpg','商品名稱：綠鮮知胡蘿蔔商品編號：4116192商品毛重：1.07kg商品產地：北京包裝：簡裝分類：蘿蔔烹飪建議：火鍋，炒菜，燉菜',18,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:53:25'),
	(41,'羊肉卷 內蒙羔羊肉 鮮嫩 500g/袋 首農出品 羊排肉卷 火鍋食材','http://111.231.103.117:8081/images/yangroujuan.jpg','商品名稱：首食惠羊排片商品編號：4836347商品毛重：0.51kg商品產地：遼寧省大連市保存狀態：冷凍品種：其它國產/進口：進口飼養方式：散養類別：羊肉片/卷包裝：簡裝烹飪建議：火鍋，炒菜，燉菜原產地：新西蘭',16,222,222,1,'2019-12-28 16:06:34','2020-02-11 00:48:03'),
	(42,'甜玉米 切好 香甜','http://111.231.103.117:8081/images/tianyumi.jpg','品牌： 綠鮮知（greenseer）\n商品名稱：綠鮮知甜玉米商品編號：4983604商品毛重：1.1kg商品產地：雲南玉溪類別：玉米',18,240,222,1,'2019-12-28 16:06:34','2020-02-11 00:52:19');

/*!40000 ALTER TABLE `imooc_mall_product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table imooc_mall_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `imooc_mall_user`;

CREATE TABLE `imooc_mall_user` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '用戶id',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用戶名',
  `password` varchar(50) NOT NULL COMMENT '用戶密碼，MD5加密',
  `personalized_signature` varchar(50) NOT NULL DEFAULT '' COMMENT '個性簽名',
  `role` int(4) NOT NULL DEFAULT '1' COMMENT '角色，1-普通用戶，2-管理員',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='用戶表 ';

LOCK TABLES `imooc_mall_user` WRITE;
/*!40000 ALTER TABLE `imooc_mall_user` DISABLE KEYS */;

INSERT INTO `imooc_mall_user` (`id`, `username`, `password`, `personalized_signature`, `role`, `create_time`, `update_time`)
VALUES
	(1,'1','1','3',1,'2019-12-16 02:37:33','2020-02-09 18:41:12'),
	(2,'xiaomu','AWRuqaxc6iryhHuA4OnFag==','更新了我的簽名',2,'2019-12-17 15:11:32','2020-02-10 09:52:12'),
	(3,'你好','AWRuqaxc6iryhHuA4OnFag==','',1,'2019-12-20 13:41:03','2020-02-10 09:52:15'),
	(4,'111','G72IZGCCcBXl1gXtRCUiUQ==','',1,'2019-12-27 19:34:56','2019-12-27 19:34:56'),
	(5,'444','uFfu1clAXB8rmASKrlBnkg==','cecc',1,'2019-12-27 19:38:03','2019-12-28 01:04:06'),
	(6,'你好2','JdVa0oOqQAr0ZMdtcTwHrQ==','',1,'2020-02-08 17:47:06','2020-02-08 17:47:06'),
	(7,'你好3','JdVa0oOqQAr0ZMdtcTwHrQ==','',1,'2020-02-08 17:49:15','2020-02-08 17:49:15'),
	(8,'你好4','12345678','',1,'2020-02-09 19:49:54','2020-02-09 19:49:54'),
	(9,'xiaomu2','AWRuqaxc6iryhHuA4OnFag==','祝你今天好心情',2,'2020-02-09 20:39:47','2020-02-11 00:56:02');

/*!40000 ALTER TABLE `imooc_mall_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
