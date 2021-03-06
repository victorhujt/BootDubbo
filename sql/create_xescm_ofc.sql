SET FOREIGN_KEY_CHECKS=0;


CREATE TABLE `ofc_attachment` (
  `serial_no` varchar(20) NOT NULL COMMENT '附件流水号',
  `ref_no` varchar(20) DEFAULT NULL COMMENT '上传附件的相关业务流水号',
  `name` varchar(255) DEFAULT NULL COMMENT '附件名称',
  `path` varchar(255) DEFAULT NULL COMMENT '附件存储相对路径',
  `type` varchar(255) DEFAULT NULL COMMENT '附件类型',
  `format` varchar(255) DEFAULT NULL COMMENT '附件格式',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `last_operator` varchar(50) DEFAULT NULL COMMENT '最后操作人',
  `last_operator_id` varchar(50) DEFAULT NULL COMMENT '最后操作人id',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
  `yn` int(11) DEFAULT '0' COMMENT '删除标识(1-已删除；0-未删除)',
  `pic_param` varchar(100) DEFAULT NULL COMMENT '图片操作命令',
  PRIMARY KEY (`serial_no`),
  UNIQUE KEY `serial_no` (`serial_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务附件表';


CREATE TABLE `ofc_create_order_error_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_order_code` varchar(255) DEFAULT '' COMMENT '客户订单编号',
  `order_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `cust_code` varchar(255) DEFAULT '' COMMENT '货主编码',
  `error_log` varchar(1000) DEFAULT '' COMMENT '错误信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1414 DEFAULT CHARSET=utf8 COMMENT='对接鲜易网创建订单错误日志记录';

CREATE TABLE `ofc_distribution_basic_info` (
  `trans_code` varchar(50) DEFAULT NULL COMMENT '运输单号',
  `urgent` int(1) DEFAULT NULL COMMENT '是否加急',
  `departure_place` varchar(255) DEFAULT NULL COMMENT '出发地',
  `departure_province` varchar(255) DEFAULT NULL COMMENT '出发省份',
  `departure_city` varchar(255) DEFAULT NULL COMMENT '出发城市',
  `departure_district` varchar(255) DEFAULT NULL COMMENT '出发区县',
  `departure_towns` varchar(255) DEFAULT NULL COMMENT '出发乡镇',
  `departure_place_code` varchar(255) DEFAULT NULL COMMENT '出发地区域编码',
  `destination` varchar(255) DEFAULT '' COMMENT '目的地',
  `destination_province` varchar(255) DEFAULT NULL COMMENT '目的省份',
  `destination_city` varchar(255) DEFAULT NULL COMMENT '目的城市',
  `destination_district` varchar(255) DEFAULT NULL COMMENT '目的区县',
  `destination_towns` varchar(255) DEFAULT NULL COMMENT '目的乡镇',
  `destination_code` varchar(255) DEFAULT NULL COMMENT '目的地区域编码',
  `quantity` decimal(18,3) DEFAULT NULL COMMENT '数量',
  `weight` decimal(18,3) DEFAULT NULL COMMENT '重量',
  `cubage` varchar(20) DEFAULT NULL COMMENT '体积',
  `total_standard_box` int(8) DEFAULT NULL COMMENT '合计标准箱',
  `trans_require` varchar(500) DEFAULT NULL COMMENT '运输要求',
  `pickup_time` datetime DEFAULT NULL COMMENT '取货时间',
  `expected_arrived_time` datetime DEFAULT NULL COMMENT '期望送货时间',
  `consignor_code` varchar(50) DEFAULT NULL COMMENT '发货方编码',
  `consignor_name` varchar(100) DEFAULT NULL,
  `consignee_code` varchar(50) DEFAULT NULL COMMENT '收货方编码',
  `consignee_name` varchar(100) DEFAULT NULL,
  `carrier_code` varchar(30) DEFAULT NULL COMMENT '承运商编码',
  `carrier_name` varchar(50) DEFAULT NULL COMMENT '承运商名称',
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `plate_number` varchar(30) DEFAULT NULL COMMENT '车牌号',
  `driver_name` varchar(30) DEFAULT NULL COMMENT '司机姓名',
  `contact_number` varchar(30) DEFAULT NULL COMMENT '联系电话',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `consignor_contact_name` varchar(250) DEFAULT NULL COMMENT '收货方联系人名称',
  `consignor_contact_code` varchar(250) DEFAULT NULL COMMENT '发货方联系人编码',
  `consignee_contact_name` varchar(250) DEFAULT NULL COMMENT '收货方联系人名称',
  `consignee_contact_code` varchar(250) DEFAULT NULL COMMENT '收货方联系人编码',
  `consignor_type` varchar(5) DEFAULT NULL COMMENT '发货人类型1、企业公司；2、个人'',',
  `consignee_type` varchar(5) DEFAULT NULL COMMENT '收货人类型1、企业公司；2、个人'',',
  `base_id` varchar(50) DEFAULT NULL COMMENT '基地ID(电商)',
  `consignor_contact_phone` varchar(250) DEFAULT NULL COMMENT '发货方联系人电话',
  `consignee_contact_phone` varchar(250) DEFAULT NULL COMMENT '收货方联系人电话',
  `goods_type` varchar(20) DEFAULT NULL,
  `goods_type_name` varchar(20) DEFAULT NULL,
  KEY `index:order_code` (`order_code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单中心配送基本信息';


CREATE TABLE `ofc_finance_information` (
  `service_charge` decimal(18,2) DEFAULT NULL COMMENT '服务费',
  `order_amount` decimal(18,2) DEFAULT NULL COMMENT '订单金额',
  `payment_amount` decimal(18,2) DEFAULT NULL COMMENT '货款金额',
  `collect_loan_amount` decimal(18,2) DEFAULT NULL COMMENT '代收贷款金额',
  `collect_service_charge` decimal(18,2) DEFAULT NULL COMMENT '代收服务费',
  `collect_flag` varchar(2) DEFAULT NULL COMMENT '代收标记',
  `count_flag` varchar(2) DEFAULT NULL COMMENT '结算标记',
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `notes` varchar(300) DEFAULT NULL COMMENT '备注',
  `creation_time` datetime DEFAULT NULL COMMENT '创建日期',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `print_invoice` varchar(10) DEFAULT NULL COMMENT '是否打印发票(电商)',
  `buyer_payment_method` varchar(100) DEFAULT NULL COMMENT '买家支付方式(电商)',
  `insure` varchar(10) DEFAULT NULL COMMENT '是否保价(电商)',
  `insure_value` varchar(100) DEFAULT NULL COMMENT '保价金额(电商)',
  `pick_up_goods` varchar(10) DEFAULT NULL COMMENT '是否上门提货',
  `home_delivery_fee` decimal(18,2) DEFAULT NULL COMMENT '上门提货费用',
  `cargo_insurance_fee` decimal(18,2) DEFAULT NULL COMMENT '货物保险费用',
  `insurance` decimal(18,2) DEFAULT NULL COMMENT '保险金额',
  `two_distribution` varchar(10) DEFAULT '' COMMENT '是否二次配送',
  `two_distribution_fee` decimal(18,2) DEFAULT NULL COMMENT '二次配送费用',
  `return_list` varchar(10) DEFAULT '' COMMENT '是否签单返回',
  `return_list_fee` decimal(18,2) DEFAULT NULL COMMENT '签单返回费用',
  `expense_payment_party` varchar(30) DEFAULT NULL COMMENT '费用支付方',
  `payment` varchar(30) DEFAULT NULL COMMENT '支付方式',
  `current_amount` decimal(18,2) DEFAULT NULL COMMENT '现结金额',
  `to_pay_amount` decimal(18,2) DEFAULT NULL COMMENT '到付金额 ',
  `return_amount` decimal(18,2) DEFAULT NULL COMMENT '回付金额 ',
  `monthly_amount` decimal(18,2) DEFAULT NULL COMMENT '月结金额',
  `luggage` decimal(18,2) DEFAULT NULL COMMENT '运费',
  `open_invoices` varchar(10) DEFAULT '' COMMENT '是否开发票(货主)',
  KEY `index:order_code` (`order_code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='财务信息表';


CREATE TABLE `ofc_fundamental_information` (
  `order_code` varchar(30) NOT NULL COMMENT '订单编号',
  `order_batch_number` varchar(30) DEFAULT NULL COMMENT '订单批次号',
  `order_time` datetime DEFAULT NULL COMMENT '订单日期',
  `cust_code` varchar(50) DEFAULT NULL COMMENT '货主编码',
  `cust_name` varchar(50) DEFAULT NULL COMMENT '货主名称',
  `sec_cust_code` varchar(30) DEFAULT NULL COMMENT '二级客户编码',
  `sec_cust_name` varchar(30) DEFAULT NULL COMMENT '二级客户名称',
  `order_type` varchar(20) DEFAULT NULL COMMENT '订单类型',
  `business_type` varchar(20) DEFAULT NULL COMMENT '业务类型',
  `cust_order_code` varchar(30) DEFAULT NULL COMMENT '客户订单编号',
  `notes` varchar(255) DEFAULT NULL COMMENT '备注',
  `store_code` varchar(30) DEFAULT NULL COMMENT '店铺编码',
  `store_name` varchar(255) DEFAULT '' COMMENT '店铺名称',
  `platform_type` varchar(50) DEFAULT NULL COMMENT '平台类型',
  `order_source` varchar(50) DEFAULT NULL COMMENT '订单来源',
  `product_type` varchar(50) DEFAULT NULL COMMENT '服务产品类型',
  `product_name` varchar(50) DEFAULT NULL COMMENT '服务产品名称',
  `finished_time` datetime DEFAULT NULL COMMENT '完成日期',
  `abolish_mark` int(1) DEFAULT NULL COMMENT '作废标记',
  `abolish_time` datetime DEFAULT NULL COMMENT '作废时间',
  `abolisher` varchar(50) DEFAULT NULL COMMENT '作废人',
  `creation_time` datetime DEFAULT NULL COMMENT '创建日期',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `sale_organization` varchar(100) DEFAULT NULL COMMENT '销售组织(众品SAP专用)',
  `product_group` varchar(100) DEFAULT NULL COMMENT '产品组(众品SAP专用)',
  `sale_department` varchar(100) DEFAULT NULL COMMENT '销售部门(众品SAP专用)',
  `sale_group` varchar(100) DEFAULT NULL COMMENT '销售组(众品SAP专用)',
  `sale_department_desc` varchar(100) DEFAULT NULL COMMENT '销售部门描述(众品SAP专用)',
  `sale_group_desc` varchar(100) DEFAULT NULL COMMENT '销售组描述(众品SAP专用)',
  `creator_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `operator_name` varchar(50) DEFAULT NULL COMMENT '操作人名称',
  `abolisher_name` varchar(50) DEFAULT NULL COMMENT '作废人名称',
  `merchandiser` varchar(20) DEFAULT NULL COMMENT '开单员',
  `transport_type` varchar(20) DEFAULT NULL COMMENT '运输类型',
  `base_code` varchar(50) DEFAULT NULL COMMENT '基地编码',
  `base_name` varchar(50) DEFAULT NULL COMMENT '基地名称',
  `area_code` varchar(50) DEFAULT NULL COMMENT '大区编码',
  `area_name` varchar(50) DEFAULT NULL COMMENT '大区名称',
  PRIMARY KEY (`order_code`),
  UNIQUE KEY `index:order_code` (`order_code`) USING BTREE,
  KEY `ndex_order_time` (`order_time`),
  KEY `index_base_code` (`base_code`) USING BTREE,
  KEY `index_area_code` (`area_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单中心基本信息';


CREATE TABLE `ofc_goods_details_info` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `goods_code` varchar(30) DEFAULT NULL COMMENT '货品代码',
  `goods_name` varchar(1000) DEFAULT NULL COMMENT '货品名称',
  `goods_spec` varchar(50) DEFAULT NULL COMMENT '货品规格',
  `unit` varchar(6) DEFAULT NULL COMMENT '单位',
  `quantity` decimal(18,2) DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(18,2) DEFAULT NULL COMMENT '单价',
  `production_batch` varchar(100) DEFAULT NULL COMMENT '生产批次',
  `production_time` datetime DEFAULT NULL COMMENT '生产日期',
  `invalid_time` datetime DEFAULT NULL COMMENT '失效日期',
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `notes` varchar(300) DEFAULT NULL COMMENT '备注',
  `creation_time` datetime DEFAULT NULL COMMENT '创建日期',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `weight` decimal(65,3) DEFAULT NULL COMMENT '重量',
  `cubage` decimal(65,3) DEFAULT NULL COMMENT '体积',
  `total_box` int(100) DEFAULT NULL COMMENT '合计标准箱',
  `goods_category` varchar(20) DEFAULT NULL COMMENT '货品二级类别',
  `pack` varchar(30) DEFAULT NULL COMMENT '包装',
  `quantity_unit_price` decimal(18,2) DEFAULT NULL COMMENT '数量单价 ',
  `weight_unit_price` decimal(18,2) DEFAULT NULL COMMENT '重量单价 ',
  `volume_unit_price` decimal(18,2) DEFAULT NULL COMMENT '体积单价 ',
  `billing_weight` decimal(65,3) DEFAULT NULL COMMENT '记账重量',
  `goods_type` varchar(20) DEFAULT NULL COMMENT '货品种类',
  `charging_ways` varchar(20) DEFAULT NULL COMMENT '计费方式',
  `charging_quantity` decimal(65,3) DEFAULT NULL COMMENT '计费数量',
  `charging_unit_price` decimal(18,2) DEFAULT NULL COMMENT '计费单价 ',
  PRIMARY KEY (`id`),
  KEY `index_order_code` (`order_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='货品明细信息';
CREATE TABLE `ofc_merchandiser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchandiser` varchar(30) DEFAULT NULL COMMENT '开单员名称',
  PRIMARY KEY (`id`)
  UNIQUE KEY `index:merchandiser` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='开单员信息表';


CREATE TABLE `ofc_mobile_order` (
  `mobile_order_code` varchar(30) NOT NULL COMMENT '流水号',
  `upload_date` datetime DEFAULT NULL COMMENT '上传日期',
  `dingding_account_no` varchar(30) DEFAULT NULL COMMENT '钉钉账号',
  `operator` varchar(50) DEFAULT NULL COMMENT '开单员',
  `order_type` varchar(30) NOT NULL COMMENT '订单类型（默认值 60 运输订单）',
  `business_type` varchar(30) DEFAULT NULL COMMENT '业务类型 【602】-卡班    ,【601】－干线，【600】－城配',
  `tran_code` varchar(30) NOT NULL COMMENT '运输单号',
  `mobile_order_status` varchar(30) NOT NULL COMMENT '订单状态 0:未受理 1:已受理 默认值未受理',
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `accepter` varchar(30) DEFAULT NULL COMMENT '订单受理人',
  `appcet_date` datetime DEFAULT NULL COMMENT '受理时间',
  `serial_no` varchar(300) DEFAULT NULL COMMENT '附件流水号，多个以逗号隔开',
  PRIMARY KEY (`mobile_order_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='拍照录单表';


CREATE TABLE `ofc_order_newstatus` (
  `order_code` varchar(30) NOT NULL DEFAULT '' COMMENT '订单编号',
  `order_latest_status` varchar(30) DEFAULT NULL COMMENT '订单最新状态',
  `status_update_time` datetime(3) DEFAULT NULL COMMENT '订单状态更新时间',
  `status_create_time` datetime(3) DEFAULT NULL COMMENT '订单状态创建时间',
  PRIMARY KEY (`order_code`),
  KEY `index_order_code` (`order_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运输计划单最新状态表';


CREATE TABLE `ofc_order_status` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `order_status` varchar(20) DEFAULT NULL COMMENT '订单状态',
  `status_desc` varchar(255) DEFAULT NULL COMMENT '状态描述',
  `lasted_oper_time` datetime(3) DEFAULT NULL COMMENT '最近操作时间',
  `notes` varchar(255) DEFAULT NULL COMMENT '备注',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `creation_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `index:order_code` (`order_code`) USING HASH,
  KEY `index_order_code` (`order_code`),
  KEY `index_order_status` (`order_status`),
  KEY `index_lasted_oper_time` (`lasted_oper_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单中心订单状态';




CREATE TABLE `ofc_warehouse_information` (
  `support_name` varchar(30) DEFAULT '' COMMENT '供应商名称',
  `support_code` varchar(30) DEFAULT NULL COMMENT '供应商编码',
  `provide_transport` int(1) DEFAULT NULL COMMENT '是否需要提供运输',
  `shipment_time` datetime DEFAULT NULL COMMENT '出库发货时间',
  `arrive_time` datetime DEFAULT NULL COMMENT '入库预计到达时间',
  `warehouse_name` varchar(30) DEFAULT '' COMMENT '仓库名称',
  `plate_number` varchar(20) DEFAULT NULL COMMENT '车牌号',
  `driver_name` varchar(20) DEFAULT NULL COMMENT '司机姓名',
  `contact_number` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `creation_time` datetime DEFAULT NULL COMMENT '创建日期',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `warehouse_code` varchar(30) DEFAULT NULL COMMENT '仓库编码',
  KEY `index:order_code` (`order_code`) USING HASH,
  KEY `index_order_code` (`order_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓配信息表';

CREATE TABLE `ofc_storage_template` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `template_code` varchar(50) NOT NULL COMMENT '模板编码',
  `template_type` varchar(20) NOT NULL COMMENT '模板类型',
  `template_name` varchar(100) NOT NULL COMMENT '模板名称',
  `cust_code` varchar(50) NOT NULL COMMENT '客户编码',
  `cust_name` varchar(50) NOT NULL COMMENT '客户名称',
  `standard_col_code` varchar(30) DEFAULT NULL COMMENT '平台标准列编码',
  `standard_col_name` varchar(30) DEFAULT NULL COMMENT '平台标准列名',
  `reflect_col_name` varchar(30) DEFAULT NULL COMMENT '映射模板名称',
  `col_default_val` varchar(50) DEFAULT NULL COMMENT '列默认值',
  `creat_time` datetime NOT NULL COMMENT '创建时间',
  `creator` varchar(50) NOT NULL COMMENT '创建人编码',
  `creator_name` varchar(50) NOT NULL COMMENT '创建人名称',
  `oper_time` datetime NOT NULL COMMENT '操作时间',
  `operator` varchar(50) NOT NULL COMMENT '操作人编码',
  `operator_name` varchar(50) NOT NULL COMMENT '操作人名称',
  `index_num` int(50) NOT NULL COMMENT '序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓储开单批量导入模板配置';

SET FOREIGN_KEY_CHECKS = 1;
