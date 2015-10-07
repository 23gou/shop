/*
Navicat MySQL Data Transfer

Source Server         : 221.231.6.226
Source Server Version : 50525
Source Host           : 221.231.6.226:3306
Source Database       : a0826103329

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2015-09-01 10:10:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blacklist
-- ----------------------------
DROP TABLE IF EXISTS `blacklist`;
CREATE TABLE `blacklist` (
  `ID` varchar(32) NOT NULL,
  `WORD` varchar(100) DEFAULT NULL COMMENT '黑名单关键字',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blacklist
-- ----------------------------
INSERT INTO `blacklist` VALUES ('1', '禾苑');
INSERT INTO `blacklist` VALUES ('2', '莫兰蒂');

-- ----------------------------
-- Table structure for item_source
-- ----------------------------
DROP TABLE IF EXISTS `item_source`;
CREATE TABLE `item_source` (
  `ID` varchar(32) NOT NULL,
  `TITLE` varchar(100) DEFAULT NULL COMMENT '标题',
  `TYPE` varchar(100) DEFAULT NULL COMMENT '货源来源类型（淘宝联盟，京东联盟，1688等等）',
  `SOURCE_ID` varchar(32) DEFAULT NULL COMMENT '货源来源唯一标示，如淘宝客就是淘宝商品ID',
  `SOURCE_DETAIL_URL` varchar(2000) DEFAULT NULL COMMENT '货源来源的商品详情URL',
  `ITEM_ID` varchar(32) DEFAULT NULL COMMENT '店铺中的ID',
  `SALE_STATUS` varchar(100) DEFAULT NULL COMMENT '店铺中的状态',
  `PRICE` decimal(8,2) DEFAULT NULL COMMENT '店铺销售价格',
  `PURCHASE_PRICE` decimal(8,2) DEFAULT NULL COMMENT '进货价',
  `PURCHASE_DISCOUNT_PRICE` decimal(8,2) DEFAULT NULL COMMENT '进货折扣价（淘宝客就是佣金）',
  `PROFIT` decimal(8,2) DEFAULT NULL COMMENT '利润',
  `SHOP_ID` varchar(32) DEFAULT NULL COMMENT '店铺ID',
  `END_DATE` datetime DEFAULT NULL COMMENT '截止日，过了这个时间就不能进货',
  `IMG` varchar(2000) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_source
-- ----------------------------
INSERT INTO `item_source` VALUES ('0b224487a6724eaebdc52974f94f86b8', '夏季薄款针织衫女开衫防晒衫外搭短款外套沙滩防晒衣9dcX6A7U', '淘宝联盟', '520493803037', 'https://detail.tmall.com/item.htm?id=520493803037&spm=a219t.7664554.1998457203.3682.DIr2j4&sku_properties=20509:871820763', '521662511991', '上架', '199.00', '300.00', '163.50', '62.50', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1T6tnIFXXXXXsXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('0eb2a332ef5d47f388127747051f49ff', '魅迪芬秋季新款2015韩版女装针织开衫 针织衫气质时尚外套潮新品', '淘宝联盟', '521527917316', 'https://detail.tmall.com/item.htm?id=521527917316&spm=a219t.7664554.1998457203.4042.MKCCU5&sku_properties=20509:28383;-1:-1', '521677348336', '上架', '88.00', '108.00', '53.46', '33.46', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1DsT_JXXXXXXTapXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('104579378f544d108b047ce22ccc2de6', '薄款纯色开衫女韩范春夏针织外套长袖带帽加肥加大中长款空调衫', '淘宝联盟', '38483463349', 'https://detail.tmall.com/item.htm?id=38483463349&spm=a219t.7664554.1998457203.3825.DIr2j4&sku_properties=20509:28383;-1:-1', '521672453509', '上架', '119.00', '168.00', '91.56', '42.56', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/T1wexkFOdaXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('121f08d48f444e59912d347cd0464568', '2014秋冬新款羊绒衫女装双层领气质打底衫鄂尔多斯市针织衫毛衣潮', '淘宝联盟', '40314539876', 'https://detail.tmall.com/item.htm?id=40314539876&spm=a219t.7664554.1998457203.3996.MKCCU5', '521672337657', '上架', '199.00', '278.00', '151.51', '72.51', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1vI9.FVXXXXXWXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('1970bfb01147472f881acdfcaeb04636', '2015秋新款甜美碎花拼接网纱绣花连衣裙女装优雅假两件套长袖短裙', '淘宝联盟', '520845129432', 'https://detail.tmall.com/item.htm?id=520845129432&spm=a219t.7664554.1998457203.5163.MKCCU5&sku_properties=-1:-1', '521661599589', '上架', '86.00', '99.90', '44.46', '30.56', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1hmoGIFXXXXa7apXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('1bb47230f5504fb29152a24ffa56a06a', '蕾丝衫长袖上衣OL通勤气质网纱t恤女 秋冬高领打底衫加绒保暖内衣', '淘宝联盟', '41205023696', 'https://detail.tmall.com/item.htm?id=41205023696&spm=a219t.7664554.1998457203.6401.DIr2j4', '521677240420', '上架', '66.00', '79.00', '43.06', '30.06', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1NZPLGXXXXXazXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('2fb442a0085148b98f92c5d701168175', '2015秋装新款韩版立领修身连衣裙子女长袖针织打底裙气质大码女装', '淘宝联盟', '521020128012', 'https://detail.tmall.com/item.htm?id=521020128012&spm=a219t.7664554.1998457203.3836.MKCCU5&sku_properties=-1:-1', '521671157901', '上架', '119.00', '168.00', '91.56', '42.56', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1hk5dIVXXXXXdXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('38966b24332e43b38e998f4fa5d03228', '瑞妮姿春秋装韩版宽松长袖t恤女式外穿秋衣大码女装中长款打底衫', '淘宝联盟', '40824669989', 'https://detail.tmall.com/item.htm?id=40824669989&spm=a219t.7664554.1998457203.696.DIr2j4&sku_properties=-1:-1', '521675992582', '上架', '72.00', '89.00', '48.51', '31.51', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1OlKIGFXXXXbkaFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('38dd4b29abf243959bd46eb09f405a6e', '2015秋韩版百搭淑女针织衫女菱形图圆领长袖毛衣外套宽松大码上衣', '淘宝联盟', '520828715769', 'https://detail.tmall.com/item.htm?id=520828715769&spm=a219t.7664554.1998457203.4071.DIr2j4&sku_properties=20509:28383;-1:-1', '521676068500', '上架', '75.00', '87.90', '43.51', '30.61', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1W4EzHVXXXXbRXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('412a2fab7b604b9187ef32f563f42d76', '2015牛仔外套女春夏短款长袖新款翻领牛仔上衣韩版牛仔夹克衫潮', '淘宝联盟', '520536450682', 'https://detail.tmall.com/item.htm?id=520536450682&spm=a219t.7664554.1998457203.7036.DIr2j4&sku_properties=-1:-1', '521671185876', '上架', '79.00', '99.00', '53.96', '33.96', '1', null, 'http://img4.tbcdn.cn/tfscom/i1/TB1GkN2IFXXXXb5XXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('566bac32871e4340a2da5e5b3ac2faac', '2015秋装新款长袖连衣裙PU两件套秋季大码女装皮裙马甲裙套装修身', '淘宝联盟', '35406511669', 'https://detail.tmall.com/item.htm?id=35406511669&spm=a219t.7664554.1998457203.4024.MKCCU5', '521677178768', '上架', '148.00', '198.00', '98.01', '48.01', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1de1JGXXXXXXFXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('66e379ecc61c42e5ab5bdac248df43e2', 'earthmusic 15夏季新款 清新蕾丝泡泡袖连衣裙 14151H11058', '淘宝联盟', '45118321557', 'https://detail.tmall.com/item.htm?id=45118321557&spm=a219t.7664554.1998457203.2828.MKCCU5&sku_properties=20509:28522410', '521677034997', '上架', '185.00', '205.00', '50.23', '30.23', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1yC7THFXXXXX7XVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('688f1c96ff564fbb87704d6a63a80182', '2015秋季新款韩版大码女装修身中长款毛衣春秋长袖薄开衫针织衫', '淘宝联盟', '44376344094', 'https://detail.tmall.com/item.htm?id=44376344094&spm=a219t.7664554.1998457203.3726.DIr2j4&sku_properties=20509:415284607', '521677272029', '上架', '89.00', '118.00', '64.31', '35.31', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1dUkLJXXXXXcFXpXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('69933e4d8a3e4cf986a6abdbfe6e431a', 'MOSAIMAGI欧美秋冬OL特色时尚潮流羊毛A字开衫针织拼接女装外套', '淘宝联盟', '521475628218', 'https://detail.tmall.com/item.htm?id=521475628218&spm=a219t.7664554.1998457203.3706.DIr2j4', '521678534612', '上架', '239.00', '329.00', '179.31', '89.31', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1CWHJJXXXXXaxXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('6a48e68d3f0242b3af11beb58c0a3af9', '2015新款中年妈妈装衬衫女长袖文艺棉麻衬衣大码中老年秋装薄外套', '淘宝联盟', '43624669667', 'https://detail.tmall.com/item.htm?id=43624669667&spm=a219t.7664554.1998457203.4016.MKCCU5', '521661367865', '上架', '96.00', '128.00', '69.76', '37.76', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1DFiVHXXXXXXAXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('7e513a68e2094eefb7053099dbd7c4cd', '2015秋新款个性绣花纯棉格子衬衫女拼接撞色气质显瘦衬衣长袖上衣', '淘宝联盟', '520795955142', 'https://detail.tmall.com/item.htm?id=520795955142&spm=a219t.7664554.1998457203.4778.DIr2j4&sku_properties=-1:-1', '521676978924', '下架', '59.00', '74.90', '40.82', '24.92', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1UilGIVXXXXc2apXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('82eada4ec71a480c80cb033c83dd80d1', '秋装连衣裙2015秋季女装韩版针织套装短袖蓬蓬裙修身显瘦连衣裙女', '淘宝联盟', '521089812224', 'https://detail.tmall.com/item.htm?id=521089812224&spm=a219t.7664554.1998457203.5083.MKCCU5&sku_properties=-1:-1', '521671253738', '上架', '66.00', '78.00', '42.51', '30.51', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1kl3kIVXXXXbeXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('851766b9b9f44b80ba199d01e9fddba7', 'earthmusic 15夏季新款 荷叶袖格纹高腰连衣裙 16151H11028', '淘宝联盟', '45130448176', 'https://detail.tmall.com/item.htm?id=45130448176&spm=a219t.7664554.1998457203.2832.MKCCU5', '521676036642', '上架', '215.00', '245.00', '60.03', '30.03', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1sDMRHFXXXXbgXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('86e891c702fa4ed99cf94f359f10b991', '麦素2015秋季新品女装外套秋装女毛衣外套中长款针织衫女开衫', '淘宝联盟', '521117497840', 'https://detail.tmall.com/item.htm?id=521117497840&spm=a219t.7664554.1998457203.1509.DIr2j4&sku_properties=-1:-2', '521675796962', '上架', '299.00', '398.00', '177.11', '78.11', '1', null, 'http://img4.tbcdn.cn/tfscom/i1/TB1TYTvIVXXXXXeXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('92f43ab8126447bca2daf0df73c2f37b', '长袖牛仔外套2015秋季新品韩版百搭短款学生牛仔小外套上衣女显瘦', '淘宝联盟', '43763366313', 'https://detail.tmall.com/item.htm?id=43763366313&spm=a219t.7664554.1998457203.1525.DIr2j4', '521671449335', '上架', '109.00', '139.00', '59.08', '29.08', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1DBcbHXXXXXXsXXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('95e9d62a456b48aca99524048a115b20', '牛仔外套女秋季新款韩版修身弹力显瘦短款外套百搭纯色长袖牛仔衣', '淘宝联盟', '521021757510', 'https://detail.tmall.com/item.htm?id=521021757510&spm=a219t.7664554.1998457203.7014.DIr2j4&sku_properties=-1:-1', '521661735408', '上架', '66.00', '79.00', '43.06', '30.06', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1W2aAIVXXXXcfXXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('980c3858f4c9448890a37a2838ada4a7', '外套女针织衫2015秋装新款民族风中长款口袋宽松毛衣针织开衫女潮', '淘宝联盟', '521585627895', 'https://detail.tmall.com/item.htm?id=521585627895&spm=a219t.7664554.1998457203.3784.MKCCU5&sku_properties=20509:28383;-1:-1', '521671497078', '上架', '85.00', '105.00', '57.23', '37.23', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1epDEJXXXXXacaXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('a4308e4c106c4f8ea8ba3e86421dbfbb', 'MOSAIMAGI2015欧美秋季时尚拼接编织羊毛套头女装针织衫上衣新品', '淘宝联盟', '521494297891', 'https://detail.tmall.com/item.htm?id=521494297891&spm=a219t.7664554.1998457203.1480.DIr2j4', '521662779713', '上架', '396.00', '545.00', '297.03', '148.03', '1', null, 'http://img4.tbcdn.cn/tfscom/i1/TB18MrKJXXXXXXdapXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('a4be075127c844af966af4c906403143', '2015秋韩版撞色绣花毛呢气质连衣裙女圆领长袖收腰显瘦A裙短裙潮', '淘宝联盟', '521260407082', 'https://detail.tmall.com/item.htm?id=521260407082&spm=a219t.7664554.1998457203.5070.MKCCU5&sku_properties=-1:-1', '521661787186', '上架', '79.00', '99.90', '54.45', '33.55', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB10sRuJXXXXXanXpXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('a4db6263438d432db011657616687f15', '小西装女秋季新品女装棉麻白色小西服2015夏新款小西装薄短外套女', '淘宝联盟', '520726487296', 'https://detail.tmall.com/item.htm?id=520726487296&spm=a219t.7664554.1998457203.1530.DIr2j4&sku_properties=-1:-1', '521671241834', '上架', '109.00', '129.00', '50.96', '30.96', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1MGlEJXXXXXafXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('a8c025e967734f118c1467878d7ab61a', '妃笆莎2015秋季新款女装韩版修身圆领提花套头针织衫毛衣 女', '淘宝联盟', '521605159802', 'https://detail.tmall.com/item.htm?id=521605159802&spm=a219t.7664554.1998457203.3751.DIr2j4&sku_properties=20509:28383;-1:-1', '521661415922', '上架', '68.00', '78.00', '42.51', '32.51', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1Ih8bJpXXXXXpXpXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('a8e1184ac47d4e0ba3f4738f54184640', '2015秋装新款长袖连衣裙两件套大码女装春秋季金丝绒打底裙皮裙PU', '淘宝联盟', '42148582606', 'https://detail.tmall.com/item.htm?id=42148582606&spm=a219t.7664554.1998457203.5120.MKCCU5', '521676212250', '上架', '179.00', '238.00', '117.81', '58.81', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1YOgaIFXXXXXFaXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('b074957abba5497c9ae0c8d9a64b42a0', '2015秋韩版新款甜美棉麻印花衬衫女装 长袖裙摆上衣收腰修身衬衣', '淘宝联盟', '520797951393', 'https://detail.tmall.com/item.htm?id=520797951393&spm=a219t.7664554.1998457203.4029.MKCCU5&sku_properties=-1:-1', '521677202527', '上架', '69.00', '84.90', '42.03', '26.13', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1KVsdIFXXXXchaXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('b0f6706b501c41fe8dba2ba53397475a', '2015韩版新款拼接绣花百搭针织衫女长袖圆领镂空蕾丝衫显瘦T恤潮', '淘宝联盟', '520822379177', 'https://detail.tmall.com/item.htm?id=520822379177&spm=a219t.7664554.1998457203.3994.DIr2j4&sku_properties=-1:-1', '521662623932', '上架', '59.00', '69.90', '38.10', '27.20', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB11kNAIVXXXXawXXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('b49452fc50f54b139b37285a76f2ad11', '麦素秋季新品针织衫女开衫长款毛衣外套女装口袋长袖V领开衫', '淘宝联盟', '521204452399', 'https://detail.tmall.com/item.htm?id=521204452399&spm=a219t.7664554.1998457203.1517.DIr2j4&sku_properties=20509:28383;-1:-1', '521677318346', '上架', '278.00', '358.00', '159.31', '79.31', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1bBctIVXXXXcAXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('be296ec2067f43aeacc79159b732ee04', '衬衫领拼接针织2015秋装新款女装套头中长款假两件毛衣外套针织衫', '淘宝联盟', '44027927430', 'https://detail.tmall.com/item.htm?id=44027927430&spm=a219t.7664554.1998457203.3958.MKCCU5', '521671417136', '上架', '76.00', '89.00', '48.51', '35.51', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1I_umHpXXXXXwXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('c0658022ba20445a86806ecedb36556e', '鑫青竹2015新款秋装韩版针织衫中长款针织开衫修身显瘦毛衣女外套', '淘宝联盟', '521554248700', 'https://detail.tmall.com/item.htm?id=521554248700&spm=a219t.7664554.1998457203.3807.MKCCU5&sku_properties=-1:-1', '521678414980', '上架', '99.00', '129.00', '70.31', '40.31', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1Eh.yJXXXXXXJXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('c1d87532f62d4fa8971e225b2ca9baf6', '2015秋装新款女连衣裙韩版气质女装蝙蝠袖针织修身显瘦打底裙套装', '淘宝联盟', '41808607118', 'https://detail.tmall.com/item.htm?id=41808607118&spm=a219t.7664554.1998457203.5145.MKCCU5', '521675864868', '上架', '109.00', '139.00', '61.86', '31.86', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1rI25GFXXXXXsXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('cd58c07d60994dafaf761dd828d7a3f9', '2015秋装新款长袖连衣裙两件套大码女装秋冬季蕾丝打底裙皮裙修身', '淘宝联盟', '40825686300', 'https://detail.tmall.com/item.htm?id=40825686300&spm=a219t.7664554.1998457203.4037.MKCCU5', '521675968793', '上架', '164.00', '218.00', '107.91', '53.91', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1ZW1wHXXXXXXtaXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('d0e0ea4c68b64259aa0d43f0aa1ed804', '2015秋季新品短外套女夹克衫韩版时尚印花卫衣修身棒球服女士上衣', '淘宝联盟', '520770385774', 'https://detail.tmall.com/item.htm?id=520770385774&spm=a219t.7664554.1998457203.1553.DIr2j4', '521671361529', '上架', '129.00', '149.00', '58.86', '38.86', '1', null, 'http://img4.tbcdn.cn/tfscom/i1/TB16yCFIFXXXXaGXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('d226848186f14ea989bb664c21c37dab', '2015秋新款气质欧美性感包臀露肩毛衣连衣裙女显瘦短裙打底连衣裙', '淘宝联盟', '521220640227', 'https://detail.tmall.com/item.htm?id=521220640227&spm=a219t.7664554.1998457203.5092.MKCCU5&sku_properties=-1:-1', '521675964831', '上架', '69.00', '89.90', '49.00', '28.10', '1', null, 'http://img4.tbcdn.cn/tfscom/i1/TB1kG.DIVXXXXbbXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('da48f36eb84a481a86133a9c4e9549d3', '纯棉格子保暖衬衫女长袖韩版修身 秋冬新款女装加绒加厚大码衬衣', '淘宝联盟', '35466932599', 'https://detail.tmall.com/item.htm?id=35466932599&spm=a219t.7664554.1998457203.4799.DIr2j4&sku_properties=-1:-1', '521661599428', '上架', '79.00', '99.00', '53.96', '33.96', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1Z99qJXXXXXaKXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('de81c4596e5142fcae1b38432ae49369', '妃笆莎2015秋季新款女装韩版修身中长款拼色针织衫开衫毛衣外套女', '淘宝联盟', '521587887555', 'https://detail.tmall.com/item.htm?id=521587887555&spm=a219t.7664554.1998457203.3769.MKCCU5&sku_properties=20509:28383;-1:-1', '521661723221', '上架', '77.00', '97.00', '52.87', '32.87', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB1mrAMJXXXXXcRXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('e57cbd1cac114961b26b14d25e1071a6', '艺之兰卡牛仔外套女秋装新款韩版修身短款外套百搭纯色长袖牛仔衣', '淘宝联盟', '520951666961', 'https://detail.tmall.com/item.htm?id=520951666961&spm=a219t.7664554.1998457203.3832.MKCCU5&sku_properties=-1:-1', '521661491895', '上架', '66.00', '79.00', '43.06', '30.06', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1QdRXJXXXXXaDaXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('e7ba8eb49c7545669dd3414c6454e593', 'MOSAIMAGI欧美站夏日清爽透明长袖针织上衣 天然素材环保女装上衣', '淘宝联盟', '520623635875', 'https://detail.tmall.com/item.htm?id=520623635875&spm=a219t.7664554.1998457203.3668.DIr2j4&sku_properties=-1:-1', '521672321948', '上架', '499.00', '998.00', '543.91', '44.91', '1', null, 'http://img4.tbcdn.cn/tfscom/i1/TB10zOTIFXXXXaBXXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('e814ada3cc0b453390a98466b6c83bf1', '鄂尔多斯市产女装秋冬新款2014拖领中长款修身羊绒衫长袖针织毛衣', '淘宝联盟', '40240379903', 'https://detail.tmall.com/item.htm?id=40240379903&spm=a219t.7664554.1998457203.3799.MKCCU5', '521662787344', '上架', '275.00', '378.00', '206.01', '103.01', '1', null, 'http://img4.tbcdn.cn/tfscom/i4/TB16QiJFVXXXXXNXVXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('ecda3550ce9544c7936510b03ef39513', '2015新款早秋季韩版大码女装套装裙中长款针织长袖休闲时尚连衣裙', '淘宝联盟', '40613835128', 'https://detail.tmall.com/item.htm?id=40613835128&spm=a219t.7664554.1998457203.3681.MKCCU5&sku_properties=20509:28383;-1:-1', '521677042897', '上架', '68.00', '78.00', '42.51', '32.51', '1', null, 'http://img4.tbcdn.cn/tfscom/i1/T1gh5LFeJeXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('edb049b7503d46d4a44c873615c59415', 'MOSAIMAGI欧美风秋冬季新款大码毛衣外套女针织衫披肩开衫女装', '淘宝联盟', '521498120912', 'https://detail.tmall.com/item.htm?id=521498120912&spm=a219t.7664554.1998457203.3781.DIr2j4', '521661707267', '上架', '499.00', '699.00', '380.96', '180.96', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1byUbJXXXXXazXXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('edcfebf4a71840b69a176aab46ed7d57', '2014秋冬新款女装一字领水钻时尚女士羊绒衫鄂尔多斯市针织毛衣潮', '淘宝联盟', '40830413067', 'https://detail.tmall.com/item.htm?id=40830413067&spm=a219t.7664554.1998457203.3977.MKCCU5', '521677096430', '上架', '238.00', '328.00', '178.76', '88.76', '1', null, 'http://img4.tbcdn.cn/tfscom/i1/TB1Y34BGXXXXXX6XXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('f38036a3ca854fc5ba6f7800fd9b0636', '碧淑黛芙2015年秋季新品通勤淑女圆领短袖清新碎花气质连衣裙', '淘宝联盟', '521034129257', 'https://detail.tmall.com/item.htm?id=521034129257&spm=a219t.7664554.1998457203.1535.DIr2j4', '521671173819', '上架', '209.00', '266.00', '105.07', '48.07', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB125SCIVXXXXXuXFXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('f96c75ab7c2d490ebd3e5d7923c7d3d2', '2015新款 春秋印花碎花长袖气质修身波西米复古图腾亚雪纺连衣裙', '淘宝联盟', '39152406810', 'https://detail.tmall.com/item.htm?id=39152406810&spm=a219t.7664554.1998457203.5159.MKCCU5', '521671577092', '上架', '108.00', '139.00', '61.86', '30.86', '1', null, 'http://img4.tbcdn.cn/tfscom/i2/TB1ALRoGFXXXXayaXXXXXXXXXXX_!!0-item_pic.jpg');
INSERT INTO `item_source` VALUES ('fef684dd6f7e4d3b87bffecd7001baf0', '2015秋天女装新款韩版长袖印花圆领加厚拼接蕾丝中长款打底衫秋冬', '淘宝联盟', '44204892615', 'https://detail.tmall.com/item.htm?id=44204892615&spm=a219t.7664554.1998457203.6418.DIr2j4&sku_properties=-1:-1', '521661783067', '上架', '98.00', '118.80', '54.05', '33.25', '1', null, 'http://img4.tbcdn.cn/tfscom/i3/TB1eeFtHXXXXXXMapXXXXXXXXXX_!!0-item_pic.jpg');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `ID` varchar(32) NOT NULL COMMENT 'sessionkey_taobao',
  `NAME` varchar(100) DEFAULT NULL COMMENT '店铺名称',
  `SESSION_KEY` varchar(100) DEFAULT NULL COMMENT 'sessionkey_taobao',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('1', '9间铺子', '6102120348fad8eeb731fb84dfa714421c658a17a8cf977405179875');
