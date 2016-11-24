

-- ----------------------------
-- Table structure for ofc_create_order_error_log
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_create_order_error_log`;
CREATE TABLE `ofc_create_order_error_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_order_code` varchar(255) DEFAULT '' COMMENT '客户订单编号',
  `order_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `cust_code` varchar(255) DEFAULT '' COMMENT '货主编码',
  `error_log` varchar(1000) DEFAULT '' COMMENT '错误信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='对接鲜易网创建订单错误日志记录';

-- ----------------------------
-- Table structure for ofc_distribution_basic_info
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_distribution_basic_info`;
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
  `consignor_name` varchar(50) DEFAULT NULL COMMENT '发货方名称',
  `consignee_code` varchar(50) DEFAULT NULL COMMENT '收货方编码',
  `consignee_name` varchar(50) DEFAULT NULL COMMENT '收货方名称',
  `carrier_code` varchar(30) DEFAULT NULL COMMENT '承运商编码',
  `carrier_name` varchar(50) DEFAULT NULL COMMENT '承运商名称',
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `plate_number` varchar(30) DEFAULT NULL COMMENT '车牌号',
  `driver_name` varchar(30) DEFAULT NULL COMMENT '司机姓名',
  `contact_number` varchar(30) DEFAULT NULL COMMENT '联系电话',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建人员',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作人员',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `consignor_contact_name` varchar(250) DEFAULT NULL COMMENT '收货方联系人名称',
  `consignor_contact_code` varchar(250) DEFAULT NULL COMMENT '发货方联系人编码',
  `consignee_contact_name` varchar(250) DEFAULT NULL COMMENT '收货方联系人名称',
  `consignee_contact_code` varchar(250) DEFAULT NULL COMMENT '收货方联系人编码',
  `consignor_type` varchar(5) DEFAULT NULL COMMENT '发货人类型1、企业公司；2、个人'',',
  `consignee_type` varchar(5) DEFAULT NULL COMMENT '收货人类型1、企业公司；2、个人'',',
  `base_id` varchar(50) DEFAULT NULL COMMENT '基地ID(电商)',
  `consignor_contact_phone` varchar(250) DEFAULT NULL COMMENT '发货方联系人电话',
  `consignee_contact_phone` varchar(250) DEFAULT NULL COMMENT '收货方联系人电话'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单中心配送基本信息';

-- ----------------------------
-- Table structure for ofc_finance_information
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_finance_information`;
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
  `creator` varchar(20) DEFAULT NULL COMMENT '创建人员',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作人员',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `print_invoice` varchar(10) DEFAULT NULL COMMENT '是否打印发票(电商)',
  `buyer_payment_method` varchar(100) DEFAULT NULL COMMENT '买家支付方式(电商)',
  `insure` varchar(10) DEFAULT NULL COMMENT '是否保价(电商)',
  `insure_value` decimal(18,2) DEFAULT NULL COMMENT '保价金额(电商)',
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
  `luggage` decimal(18,2) DEFAULT NULL COMMENT '运费'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='财务信息表';

-- ----------------------------
-- Table structure for ofc_fundamental_information
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_fundamental_information`;
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
  `abolisher` varchar(20) DEFAULT NULL COMMENT '作废人员',
  `creation_time` datetime DEFAULT NULL COMMENT '创建日期',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建人员',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作人员',
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
  PRIMARY KEY (`order_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单中心基本信息';

-- ----------------------------
-- Table structure for ofc_goods_details_info
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_goods_details_info`;
CREATE TABLE `ofc_goods_details_info` (
  `goods_code` varchar(30) DEFAULT NULL COMMENT '货品代码',
  `goods_name` varchar(30) DEFAULT NULL COMMENT '货品名称',
  `goods_spec` varchar(20) DEFAULT NULL COMMENT '货品规格',
  `unit` varchar(6) DEFAULT NULL COMMENT '单位',
  `quantity` decimal(18,2) DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(18,2) DEFAULT NULL COMMENT '单价',
  `production_batch` varchar(100) DEFAULT NULL COMMENT '生产批次',
  `production_time` datetime DEFAULT NULL COMMENT '生产日期',
  `invalid_time` datetime DEFAULT NULL COMMENT '失效日期',
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `notes` varchar(300) DEFAULT NULL COMMENT '备注',
  `creation_time` datetime DEFAULT NULL COMMENT '创建日期',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建人员',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作人员',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `weight` decimal(65,3) DEFAULT NULL COMMENT '重量',
  `cubage` decimal(65,3) DEFAULT NULL COMMENT '体积',
  `total_box` int(100) DEFAULT NULL COMMENT '合计标准箱',
  `goods_category` varchar(20) DEFAULT NULL COMMENT '货品类别',
  `pack` varchar(30) DEFAULT NULL COMMENT '包装',
  `quantity_unit_price` decimal(18,2) DEFAULT NULL COMMENT '数量单价 ',
  `weight_unit_price` decimal(18,2) DEFAULT NULL COMMENT '重量单价 ',
  `volume_unit_price` decimal(18,2) DEFAULT NULL COMMENT '体积单价 '
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='货品明细信息';

-- ----------------------------
-- Table structure for ofc_merchandiser
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_merchandiser`;
CREATE TABLE `ofc_merchandiser` (
  `merchandiser` varchar(20) NOT NULL COMMENT '开单员',
  PRIMARY KEY (`merchandiser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ofc_order_status
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_order_status`;
CREATE TABLE `ofc_order_status` (
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `order_status` varchar(20) DEFAULT NULL COMMENT '订单状态',
  `status_desc` varchar(255) DEFAULT NULL COMMENT '状态描述',
  `lasted_oper_time` datetime DEFAULT NULL COMMENT '最近操作时间',
  `notes` varchar(255) DEFAULT NULL COMMENT '备注',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作人员'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单中心订单状态';

-- ----------------------------
-- Table structure for ofc_planned_detail
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_planned_detail`;
CREATE TABLE `ofc_planned_detail` (
  `plan_code` varchar(30) DEFAULT '' COMMENT '计划单编号',
  `goods_code` varchar(30) DEFAULT NULL COMMENT '货品代码',
  `goods_name` varchar(30) DEFAULT NULL COMMENT '货品名称',
  `goods_spec` varchar(30) DEFAULT NULL COMMENT '货品规格',
  `unit` varchar(30) DEFAULT NULL COMMENT '单位',
  `quantity` decimal(18,2) DEFAULT NULL COMMENT '数量',
  `real_quantity` decimal(18,2) DEFAULT NULL COMMENT '实发数量',
  `unit_price` decimal(18,2) DEFAULT NULL COMMENT '单价',
  `production_batch` varchar(30) DEFAULT NULL COMMENT '生产批次',
  `production_time` datetime DEFAULT NULL COMMENT '生产日期',
  `invalid_time` datetime DEFAULT NULL COMMENT '失效日期',
  `weight` decimal(65,3) DEFAULT NULL COMMENT '重量',
  `cubage` decimal(65,3) DEFAULT NULL COMMENT '体积',
  `total_box` int(100) DEFAULT NULL COMMENT '合计标准箱'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计划单明细表';

-- ----------------------------
-- Table structure for ofc_silopro_newstatus
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_silopro_newstatus`;
CREATE TABLE `ofc_silopro_newstatus` (
  `plan_code` varchar(30) DEFAULT '' COMMENT '计划单编号',
  `order_code` varchar(30) DEFAULT '' COMMENT '订单编号',
  `job_new_status` varchar(30) DEFAULT NULL COMMENT '作业最新状态',
  `job_status_update_time` datetime DEFAULT NULL COMMENT '作业状态更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓储计划单最新状态表';

-- ----------------------------
-- Table structure for ofc_silopro_source_status
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_silopro_source_status`;
CREATE TABLE `ofc_silopro_source_status` (
  `plan_code` varchar(30) DEFAULT '' COMMENT '计划单编号',
  `order_code` varchar(30) DEFAULT '' COMMENT '订单编号',
  `resource_allocation_status` varchar(30) DEFAULT NULL COMMENT '资源分配状态',
  `service_provider_code` varchar(30) DEFAULT NULL COMMENT '服务商编码',
  `service_provider_name` varchar(30) DEFAULT NULL COMMENT '服务商名称',
  `service_provider_contact` varchar(30) DEFAULT NULL COMMENT '服务商联系人',
  `service_provider_contact_phone` varchar(30) DEFAULT NULL COMMENT '服务商联系电话',
  `resource_confirmation` varchar(30) DEFAULT NULL COMMENT '资源确认人员',
  `resource_confirmation_time` datetime DEFAULT NULL COMMENT '资源确认时间',
  `plate_number` varchar(30) DEFAULT NULL COMMENT '车牌号',
  `driver_name` varchar(30) DEFAULT NULL COMMENT '司机姓名',
  `contact_number` varchar(30) DEFAULT NULL COMMENT '司机联系电话',
  `trans_code` varchar(30) DEFAULT NULL COMMENT '运输单号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓储计划资源状态表';

-- ----------------------------
-- Table structure for ofc_silopro_status
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_silopro_status`;
CREATE TABLE `ofc_silopro_status` (
  `plan_code` varchar(30) DEFAULT '' COMMENT '计划单编号',
  `order_code` varchar(30) DEFAULT '' COMMENT '订单编号',
  `planned_single_state` varchar(30) DEFAULT NULL COMMENT '计划单状态',
  `planned_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
  `planned_completion_time` datetime DEFAULT NULL COMMENT '计划完成时间',
  `task_start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `task_completion_time` datetime DEFAULT NULL COMMENT '任务完成时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓储计划单状态表';

-- ----------------------------
-- Table structure for ofc_siloprogram_info
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_siloprogram_info`;
CREATE TABLE `ofc_siloprogram_info` (
  `plan_code` varchar(30) NOT NULL DEFAULT '' COMMENT '计划单编号',
  `order_code` varchar(30) NOT NULL DEFAULT '' COMMENT '订单编号',
  `order_batch_number` varchar(30) DEFAULT NULL COMMENT '订单批次号',
  `program_serial_number` varchar(30) DEFAULT NULL COMMENT '计划序号',
  `business_type` varchar(20) DEFAULT NULL COMMENT '类型',
  `document_type` varchar(30) DEFAULT NULL COMMENT '单据类型',
  `order_time` datetime DEFAULT NULL COMMENT '日期',
  `cust_code` varchar(50) DEFAULT NULL COMMENT '货主编码',
  `warehouse_code` varchar(30) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(30) DEFAULT NULL COMMENT '仓库名称',
  `estimated_time_of_delivery` datetime DEFAULT NULL COMMENT '出库发货时间',
  `arrive_time` datetime DEFAULT NULL COMMENT '预计到达时间',
  `loading_place` varchar(30) DEFAULT NULL COMMENT '装货地',
  `unloading_place` varchar(30) DEFAULT NULL COMMENT '卸货地',
  `delivery_place` varchar(30) DEFAULT NULL COMMENT '交货地',
  `eceiving_platform` varchar(30) DEFAULT NULL COMMENT '收货月台',
  `the_total_value_of` varchar(30) DEFAULT NULL COMMENT '总货值',
  `support_code` varchar(30) DEFAULT NULL COMMENT '供应商编码',
  `support_name` varchar(30) DEFAULT NULL COMMENT '供应商名称',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `consignee_code` varchar(30) DEFAULT NULL COMMENT '收货方编码',
  `consignee_name` varchar(30) DEFAULT NULL COMMENT '收货方名称',
  `consignee_contact` varchar(30) DEFAULT NULL COMMENT '收货方联系人',
  `consignee_contact_phone` varchar(30) DEFAULT NULL COMMENT '收货方联系电话',
  `consignee_fax` varchar(30) DEFAULT NULL COMMENT '收货方传真',
  `consignee_email` varchar(30) DEFAULT NULL COMMENT '收货方Email',
  `consignee_post_code` varchar(30) DEFAULT NULL COMMENT '收货方邮编',
  `consignee_province` varchar(30) DEFAULT NULL COMMENT '收货方省',
  `consignee_city` varchar(30) DEFAULT NULL COMMENT '收货方市',
  `consignee_district_and_county` varchar(30) DEFAULT NULL COMMENT '收货方区县',
  `consignee_township_streets` varchar(30) DEFAULT NULL COMMENT '收货方乡镇街道',
  `consignee_address` varchar(30) DEFAULT NULL COMMENT '收货方地址',
  `print_invoice` varchar(30) DEFAULT NULL COMMENT '是否打印发票',
  `buyer_payment_method` varchar(30) DEFAULT NULL COMMENT '支付方式',
  `order_amount` varchar(30) DEFAULT NULL COMMENT '订单金额',
  `collect_flag` varchar(30) DEFAULT NULL COMMENT '是否货到付款',
  `insure` varchar(30) DEFAULT NULL COMMENT '是否保价',
  `insure_value` varchar(30) DEFAULT NULL COMMENT '保价金额',
  `warehouse_number` varchar(30) DEFAULT NULL COMMENT '仓储作业单号',
  `service_charge` varchar(30) DEFAULT NULL COMMENT '服务费用',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_personnel` varchar(30) DEFAULT NULL COMMENT '创建人员',
  `merchandiser` varchar(20) DEFAULT NULL COMMENT '开单员',
  `void_personnel` varchar(30) DEFAULT NULL COMMENT '作废人员',
  `void_time` datetime DEFAULT NULL COMMENT '作废时间',
  PRIMARY KEY (`plan_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓储计划单信息表';

-- ----------------------------
-- Table structure for ofc_transplan_info
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_transplan_info`;
CREATE TABLE `ofc_transplan_info` (
  `plan_code` varchar(30) NOT NULL DEFAULT '' COMMENT '计划单编号',
  `order_code` varchar(30) NOT NULL DEFAULT '' COMMENT '订单编号',
  `order_batch_number` varchar(30) DEFAULT NULL COMMENT '订单批次号',
  `program_serial_number` varchar(30) DEFAULT NULL COMMENT '计划序号',
  `business_type` varchar(20) DEFAULT NULL COMMENT '类型',
  `order_time` datetime DEFAULT NULL COMMENT '日期',
  `cust_code` varchar(50) DEFAULT NULL COMMENT '货主编码',
  `pickup_time` datetime DEFAULT NULL COMMENT '预计发货时间',
  `expected_arrived_time` datetime DEFAULT NULL COMMENT '预计到达时间',
  `weight` decimal(18,3) DEFAULT NULL COMMENT '重量',
  `quantity` decimal(18,3) DEFAULT NULL COMMENT '数量',
  `volume` decimal(18,3) DEFAULT NULL COMMENT '体积',
  `money` decimal(18,3) DEFAULT NULL COMMENT '金额',
  `shippin_customer_code` varchar(50) DEFAULT NULL COMMENT '发货客户代码',
  `shipping_address` varchar(30) DEFAULT NULL COMMENT '发货客户地址',
  `shipping_customer_contact` varchar(30) DEFAULT NULL COMMENT '发货客户联系人',
  `customer_contact_phone` varchar(30) DEFAULT NULL COMMENT '发货客户联系电话',
  `departure_province` varchar(30) DEFAULT NULL COMMENT '出发省份',
  `departure_city` varchar(30) DEFAULT NULL COMMENT '出发城市',
  `departure_district` varchar(30) DEFAULT NULL COMMENT '出发区县',
  `departure_towns` varchar(30) DEFAULT NULL COMMENT '出发乡镇',
  `departure_place_code` varchar(255) DEFAULT NULL COMMENT '出发地区域编码',
  `receiving_customer_code` varchar(50) DEFAULT NULL COMMENT '收货客户代码',
  `receiving_customer_address` varchar(30) DEFAULT NULL COMMENT '收货客户地址',
  `receiving_customer_contact` varchar(30) DEFAULT NULL COMMENT '收货客户联系人',
  `receiving_customer_contact_phone` varchar(30) DEFAULT NULL COMMENT '收货客户联系电话',
  `destination_province` varchar(30) DEFAULT NULL COMMENT '目的省份',
  `destination_city` varchar(30) DEFAULT NULL COMMENT '目的城市',
  `destination_district` varchar(30) DEFAULT NULL COMMENT '目的区县',
  `destination_town` varchar(30) DEFAULT NULL COMMENT '目的乡镇',
  `destination_code` varchar(255) DEFAULT NULL COMMENT '目的地区域编码',
  `receiving_address_longitude` varchar(30) DEFAULT NULL COMMENT '收货地址经度',
  `receiving_address_latitude` varchar(30) DEFAULT NULL COMMENT '收货地址纬度',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `sale_organization` varchar(30) DEFAULT NULL COMMENT '销售组织',
  `product_group` varchar(30) DEFAULT NULL COMMENT '产品组',
  `sale_department` varchar(30) DEFAULT NULL COMMENT '销售部门',
  `sale_group` varchar(30) DEFAULT NULL COMMENT '销售组',
  `sale_department_desc` varchar(200) DEFAULT NULL COMMENT '销售部门描述',
  `sale_group_desc` varchar(200) DEFAULT NULL COMMENT '销售组描述',
  `single_source_of_transport` varchar(30) DEFAULT NULL COMMENT '运输单来源',
  `service_charge` decimal(18,2) DEFAULT NULL COMMENT '服务费用',
  `base_id` varchar(50) DEFAULT NULL COMMENT '基地ID(电商)',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_personnel` varchar(30) DEFAULT NULL COMMENT '创建人员',
  `void_personnel` varchar(30) DEFAULT NULL COMMENT '作废人员',
  `void_time` datetime DEFAULT NULL COMMENT '作废时间',
  `shippin_customer_name` varchar(50) DEFAULT NULL COMMENT '发货客户名称',
  `receiving_customer_name` varchar(50) DEFAULT NULL COMMENT '收货客户名称',
  `merchandiser` varchar(20) DEFAULT NULL COMMENT '开单员',
  `transport_type` varchar(20) DEFAULT NULL COMMENT '运输类型',
  PRIMARY KEY (`plan_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运输计划单信息表';

-- ----------------------------
-- Table structure for ofc_transplan_newstatus
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_transplan_newstatus`;
CREATE TABLE `ofc_transplan_newstatus` (
  `plan_code` varchar(30) DEFAULT '' COMMENT '计划单编号',
  `order_code` varchar(30) DEFAULT '' COMMENT '订单编号',
  `transport_single_latest_status` varchar(30) DEFAULT NULL COMMENT '运输单最新状态',
  `transport_single_update_time` datetime DEFAULT NULL COMMENT '运输单更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运输计划单最新状态表';

-- ----------------------------
-- Table structure for ofc_transplan_status
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_transplan_status`;
CREATE TABLE `ofc_transplan_status` (
  `plan_code` varchar(30) DEFAULT '' COMMENT '计划单编号',
  `order_code` varchar(30) DEFAULT '' COMMENT '订单编号',
  `planned_single_state` varchar(30) DEFAULT NULL COMMENT '计划单状态',
  `planned_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
  `planned_completion_time` datetime DEFAULT NULL COMMENT '计划完成时间',
  `task_start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `task_completion_time` datetime DEFAULT NULL COMMENT '任务完成时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运输计划单状态表';

-- ----------------------------
-- Table structure for ofc_traplan_source_status
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_traplan_source_status`;
CREATE TABLE `ofc_traplan_source_status` (
  `plan_code` varchar(30) DEFAULT '' COMMENT '计划单编号',
  `order_code` varchar(30) DEFAULT '' COMMENT '订单编号',
  `resource_allocation_status` varchar(30) DEFAULT NULL COMMENT '资源分配状态',
  `service_provider_code` varchar(30) DEFAULT NULL COMMENT '服务商编码',
  `service_provider_name` varchar(30) DEFAULT NULL COMMENT '服务商名称',
  `service_provider_contact` varchar(30) DEFAULT NULL COMMENT '服务商联系人',
  `service_provider_contact_phone` varchar(30) DEFAULT NULL COMMENT '服务商联系电话',
  `resource_confirmation` varchar(30) DEFAULT NULL COMMENT '资源确认人员',
  `resource_confirmation_time` datetime DEFAULT NULL COMMENT '资源确认时间',
  `plate_number` varchar(30) DEFAULT NULL COMMENT '车牌号',
  `driver_name` varchar(30) DEFAULT NULL COMMENT '司机姓名',
  `contact_number` varchar(30) DEFAULT NULL COMMENT '司机联系电话',
  `trans_code` varchar(50) DEFAULT NULL COMMENT '运输单号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运输计划资源状态表';

-- ----------------------------
-- Table structure for ofc_warehouse_information
-- ----------------------------
-- DROP TABLE IF EXISTS `ofc_warehouse_information`;
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
  `creator` varchar(20) DEFAULT NULL COMMENT '创建人',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作人',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `warehouse_code` varchar(30) DEFAULT NULL COMMENT '仓库编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓配信息表';
