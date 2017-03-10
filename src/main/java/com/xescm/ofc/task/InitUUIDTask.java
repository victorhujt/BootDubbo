//package com.xescm.ofc.task;
//
//import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
//import com.xescm.ofc.domain.OfcOrderStatus;
//import com.xescm.ofc.mapper.OfcGoodsDetailsInfoMapper;
//import com.xescm.ofc.mapper.OfcOrderStatusMapper;
//import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
//import com.xescm.ofc.service.OfcOrderStatusService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicBoolean;
//
///**
// * <p>Title:      InitUUIDTask. </p>
// * <p>Description 为没有主键的表初始化UUID主键 </p>
// * <p>Company:    http://www.hnxianyi.com </p>
// *
// * @Author	      nothing
// * @CreateDate    2017/2/8 14:10
// */
//@Component
//public class InitUUIDTask implements CommandLineRunner {
//
//    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private OfcGoodsDetailsInfoService goodsDetailsInfoService;
//    @Autowired
//    private OfcGoodsDetailsInfoMapper goodsDetailsInfoMapper;
//    @Autowired
//    private OfcOrderStatusService orderStatusService;
//    @Autowired
//    private OfcOrderStatusMapper ofcOrderStatusMapper;
//
//    private static AtomicBoolean isStarted = new AtomicBoolean(false);
//
//    /**
//     * <p>Title:      initUUID. </p>
//     * <p>Description 表 ofc_goods_details_info 初始化UUID主键</p>
//     *
//     * @param
//     * @Author	      nothing
//     * @CreateDate    2017/2/8 14:11
//     * @return
//     */
//    /*public void initTableGoodsDetailInfoUUID() {
//        long start = System.currentTimeMillis();
//        LOGGER.info(">>>>>>>>>>>>>>>>>>> Begin initializing table [ofc_goods_details_info] ID...");
//        // 初始化订单商品明细表ID
//        List<OfcGoodsDetailsInfo> goodsDetails = goodsDetailsInfoService.selectAll();
////        List<OfcGoodsDetailsInfo> result = new ArrayList<>();
//        int i = 1;
//        for (OfcGoodsDetailsInfo detail: goodsDetails) {
//            String id = UUID.randomUUID().toString().replace("-", "");
//            detail.setId(id);
////            result.add(detail);
//            goodsDetailsInfoMapper.batchInsertGoodsDetail(detail);
//            LOGGER.info(i+"");
//            i++;
//        }
//        long stop = System.currentTimeMillis();
//        LOGGER.info(">>>>>>>>>>>>>>>>>>> Initializing table [ofc_goods_details_info] ID finished. Elapsed time ("+(stop - start)+")");
//
//    }
//
//    *//**
//     * <p>Title:      initTableOrderStatusUUID. </p>
//     * <p>Description 批量插入订单状态主键id</p>
//     *
//     * @param
//     * @Author	      nothing
//     * @CreateDate    2017/2/10 12:09
//     * @return
//     *//*
//    private void initTableOrderStatusUUID() {
//        LOGGER.info(">>>>>>>>>>>>>>>>>>> Begin initializing table [ofc_order_status] ID...");
//        long start = System.currentTimeMillis();
//        List<OfcOrderStatus> orderStatus = orderStatusService.selectAll();
////        List<OfcOrderStatus> result = new ArrayList<>();
//        int i = 1;
//        for (OfcOrderStatus status : orderStatus) {
//            String id = UUID.randomUUID().toString().replace("-", "");
//            status.setId(id);
////            result.add(status);
//            status.setCreationTime(status.getLastedOperTime());
//            ofcOrderStatusMapper.batchInsertOrderStatusId(status);
//            LOGGER.info(i+"");
//            i++;
//        }
//        long stop = System.currentTimeMillis();
//        LOGGER.info(">>>>>>>>>>>>>>>>>>> Initializing table [ofc_order_status] ID finished. Elapsed time ("+(stop - start)+")");
//    }
//
//    @Override
//    public void run(String... strings) throws Exception {
////        if (!isStarted.get()) {
////            LOGGER.info("==========================================> Initializing starting ...");
////            isStarted.getAndSet(true);
////            initTableGoodsDetailInfoUUID();
//
////            initTableOrderStatusUUID();
////            LOGGER.info("==========================================> Initializing finished.");
////        }
//    }*/
//}