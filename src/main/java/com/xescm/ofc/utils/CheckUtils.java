package com.xescm.ofc.utils;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.warehouse.CscWarehouseDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.model.vo.CscStorevo;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.model.dto.coo.CreateOrderEntity;
import com.xescm.ofc.model.dto.coo.CreateOrderGoodsInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.xescm.ofc.constant.ResultModel.ResultEnum.CODE_0011;

/**
 * 校验
 * Created by hiyond on 2016/11/15.
 */
public class CheckUtils {

    static Logger logger = LoggerFactory.getLogger(CheckUtils.class);

    /**
     * 货主编码
     * 货主编码为对接客户前应为其分配的货主编码。
     * 判断货主编码是否为客户档案信息中的客户编码字段，
     * 如果不存在，接口应返回错误信息，返回对应客户订单编号，错误信息 “货主编码不正确!“
     * fixme
     *
     * @return
     */
    public static ResultModel checkCustCode(String s) {
        return StringUtils.isBlank(s) ? new ResultModel(ResultModel.ResultEnum.CODE_0009) : new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }

    /**
     * 判断传入的订单类型是否为【60】、【61】，
     * 如果非此两项则，则返回错误信息，
     * 对应的客户订单编号，错误信息“传递的订单类型有误！”
     *
     * @param orderType 订单类型
     * @return
     */
    public static ResultModel checkOrderType(String orderType) {
        orderType = StringUtils.trim(orderType);
        if ("60".equals(orderType) ||  "61".equals(orderType)) {
            return new ResultModel(ResultModel.ResultEnum.CODE_0000);
        }
        return new ResultModel(ResultModel.ResultEnum.CODE_0001);
    }

    /**
     * 【业务类型】
     * 判断传入的业务类型是否为【600】、【601】、【602】、【610】、【611】、【612】、【613】、【620】、【621】、【622】、【623】,若不为上述值，则返回错误信息，对应的客户订单编号，错误信息“传递的业务类型有误！”
     * 判断业务类型与否与订单类型相对应，即
     * 订单类型【60】对应【600】、【601】、【602】
     * 订单类型【61】对应【610】、【611】、【612】、【613】、【620】、【621】、【622】、【623】
     * 若不一致，则返回错误信息，对应的客户订单编号，错误信息“传递的业务类型与订单类型不匹配！”
     *
     * @param orderType
     * @param businessType
     * @return
     */
    public static ResultModel checkBusinessType(String orderType, String businessType) {
        //Set<String> checkSet = new HashSet<String>();
        Set<String> checkSet = new HashSet<>();
        if ("60".equals(orderType)) {
            String[] checkArray = {"600", "601", "602"};
            if (ArrayUtils.contains(checkArray, businessType)) {
                return new ResultModel(ResultModel.ResultEnum.CODE_0000);
            }
        }

        if ("61".equals(orderType)) {
            String[] checkArray = {"610", "611", "612", "613", "620", "621", "622", "623"};
            if (ArrayUtils.contains(checkArray, businessType)) {
                return new ResultModel(ResultModel.ResultEnum.CODE_0000);
            }
        }
        return new ResultModel(ResultModel.ResultEnum.CODE_0002);
    }

    /**
     * 对接前应在协同平台为客户设置店铺档案信息，获取对应的店铺编码，固定店铺编码。
     * 判断传入的店铺编码与货主编码，判断该货主的店铺档案信息中是否存在该店铺编码，
     * 若不返回，返回错误信息，对应的客户订单编号，错误信息“传递的店铺编码有误！”
     * fixme
     *
     * @param storeCode
     * @return
     */
    public static ResultModel checkStoreCode(Wrapper<List<CscStorevo>> listWrapper, String storeCode) {
        if (listWrapper.getCode() == Wrapper.ERROR_CODE) {
            return new ResultModel(ResultModel.ResultEnum.CODE_0003);
        }
        List<CscStorevo> result = listWrapper.getResult();
        for (CscStorevo c : result) {
            if (StringUtils.equals(c.getStoreCode(), storeCode)) {
                return new ResultModel(ResultModel.ResultEnum.CODE_0000);
            }
        }

        return new ResultModel(ResultModel.ResultEnum.CODE_0003);
    }

    /**
     * 【发货方】与【收货方】 发货方必填信息的校验。
     * 若订单类型为【60】
     * 发货方名称、发货方联系人、发货方联系电话、发货方的地址为必填项。
     * 收货方名称、收货方联系人、收货方联系电话、收货方的地址为必填项。
     * 若其中一项未填写，则返回错误信息，“ＸＸＸＸ信息不能为空！“
     * 若订单类型为【61】，同时【是否需要运输】为【是】
     * 发货方名称、发货方联系人、发货方联系电话、发货方的地址为必填项。
     * 收货方名称、收货方联系人、收货方联系电话、收货方的地址为必填项。
     * 若其中一项未填写，则返回错误信息，“ＸＸＸＸ信息不能为空！“
     *
     *
     * @return
     */
    public static ResultModel checkWaresDist(CreateOrderEntity createOrderEntity) {
        String orderType = createOrderEntity.getOrderType();
        String consignor_name = createOrderEntity.getConsignorName();
        String consignor_contact = createOrderEntity.getConsignorContact();
        String consignor_phone = createOrderEntity.getConsignorPhone();
        String consignor_address = createOrderEntity.getConsignorAddress();
//        String consignor_province_code = createOrderEntity.getConsignorProvinceCode();
//        String consignor_city_code = createOrderEntity.getConsignorCityCode();
        String consignor_country_code = createOrderEntity.getConsignorCountyCode();
        String consignor_town_code = createOrderEntity.getConsignorTownCode();
        String consignee_name = createOrderEntity.getConsigneeName();
        String consignee_contact = createOrderEntity.getConsigneeContact();
        String consignee_phone = createOrderEntity.getConsigneePhone();
        String consignee_address = createOrderEntity.getConsigneeAddress();
        String consignee_province_code = createOrderEntity.getConsigneeProvinceCode();
        String consignee_city_code = createOrderEntity.getConsigneeCityCode();
        String consignee_country_code = createOrderEntity.getConsigneeCountyCode();
        String consignee_town_code = createOrderEntity.getConsigneeTownCode();
        String provide_transport = createOrderEntity.getProvideTransport();
        if ("60".equals(orderType) || "61".equals(orderType)) {
            if ("61".equals(orderType)) {
                if (!StringUtils.equals(provide_transport, "1")) {
                    return new ResultModel("1000", "是否需要运输信息不能为空");
                }
            }
            if (StringUtils.isBlank(consignor_name)) {
                return new ResultModel("1000", "发货方名称信息不能为空");
            } else if (StringUtils.isBlank(consignor_contact)) {
                return new ResultModel("1000", "发货方联系人信息不能为空");
            } else if (StringUtils.isBlank(consignor_phone)) {
                return new ResultModel("1000", "发货方联系电话信息不能为空");
            } else if (StringUtils.isBlank(consignor_address)) {
                return new ResultModel("1000", "发货方地址信息不能为空");
            }/* else if (StringUtils.isBlank(consignor_province_code)) {
                return new ResultModel("1000", "发货方地址信息省编码不能为空");
            } else if (StringUtils.isBlank(consignor_city_code)) {
                return new ResultModel("1000", "发货方地址信息市编码不能为空");
            }*/ else if (StringUtils.isBlank(consignee_name)) {
                return new ResultModel("1000", "收货方名称信息不能为空");
            } else if (StringUtils.isBlank(consignee_contact)) {
                return new ResultModel("1000", "收货方联系人信息不能为空");
            } else if (StringUtils.isBlank(consignee_phone)) {
                return new ResultModel("1000", "收货方联系电话信息不能为空");
            } else if (StringUtils.isBlank(consignee_address)) {
                return new ResultModel("1000", "收货方地址信息不能为空");
            }
        }
        return new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }

    /**
     * 店铺编码
     * 对接前应在协同平台为客户设置店铺档案信息，获取对应的店铺编码，固定店铺编码。
     * 判断传入的店铺编码与货主编码，判断该货主的店铺档案信息中是否存在该店铺编码，
     * 若不返回，返回错误信息，对应的客户订单编号，错误信息“传递的店铺编码有误！”
     * fixme
     *
     * @return
     */
    public static ResultModel checkStoreCode() {
        return new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }

    /**
     * 【仓库编码】
     * 　　仓库编码，在统一对接平台调用时，需要将外部的仓库编码与平台的仓库编码做映射。
     * 订单类型为【61】时，判断仓库编码是否为空，为空则返回错误信息，“仓库编码不能为空！”
     * 判断仓库编码是否在仓库档案信息中存在，若不存在，则返回错误信息，“仓库不存在！”
     * 若不存在，则返回错误信息，“仓库不存在！”
     */
    public static ResultModel checkWarehouseCode(Wrapper<List<CscWarehouseDto>> listWrapper, String warehouse, String orderType) {
        warehouse = StringUtils.trim(warehouse);
        orderType = StringUtils.trim(orderType);
        if (StringUtils.equals("60", orderType)) {
            return new ResultModel(ResultModel.ResultEnum.CODE_0000);
        }
        if(StringUtils.equals("61",orderType)){
            if (listWrapper.getCode() == Wrapper.ERROR_CODE) {
                return new ResultModel(ResultModel.ResultEnum.CODE_0004);
            }
            List<CscWarehouseDto> cscWarehouseList = listWrapper.getResult();
            for (CscWarehouseDto c : cscWarehouseList) {
                if (StringUtils.equals(c.getWarehouseCode(), warehouse)) {
                    return new ResultModel(ResultModel.ResultEnum.CODE_0000);
                }
            }
        }
        return new ResultModel(ResultModel.ResultEnum.CODE_0005);
    }

    /**
     * 【供应商】
     * 使用供应商名称查询该货主供应商档案下是否存在对应【供应商名称】的档案信息，
     * 若存在，则获取该名称对应的【供应商编码】；不存在，则创建该供应商名称。
     * 使用供应商联系人查询该【供应商名称】下是否存在该联系人姓名，
     * 若不存在，则创建该联系人信息，并与订单关联保存；若存在，则获取该联系人编码与订单关联保存。
     */
    public static String checkSupport(Wrapper<List<CscSupplierInfoDto>> listWrapper, String supportName) {
        List<CscSupplierInfoDto> list = listWrapper.getResult();
        for (CscSupplierInfoDto c : list) {
            if (StringUtils.equals(c.getSupplierName(), supportName)) {
                return c.getSupplierCode();
            }
        }
        return null;
    }

    /**
     * 【货品档案信息】
     * 货品档案信息，在统一对接平台调用时，需要将外部的货品信息编码与平台的货品编码做对应。
     * 若传递的货品代码不存在，则返回错误信息，给予提示”XXXX货品档案不存在！
     */
    public static ResultModel checkGoodsInfo(Wrapper<List<CscGoodsApiVo>> listWrapper, CreateOrderGoodsInfo createOrderGoodsInfo) {
        if (listWrapper.getCode() == Wrapper.ERROR_CODE) {
            return new ResultModel(ResultModel.ResultEnum.CODE_2001);
        }
        for (CscGoodsApiVo c : listWrapper.getResult()) {
            if (StringUtils.equals(c.getGoodsCode(), createOrderGoodsInfo.getGoodsCode())) {
                return new ResultModel(ResultModel.ResultEnum.CODE_0000);
            }
        }
        return new ResultModel("1000", createOrderGoodsInfo.getGoodsName() + "货品档案不存在");
    }

    /**
     * 数量、重量、体积 三选一不能为空
     * @param quantity 数量
     * @param weight 重量
     * @param cubage 体积
     * @return
     */
    public static ResultModel checkQuantityAndWeightAndCubage(String quantity, String weight, String cubage) {
        return (StringUtils.isBlank(quantity) && StringUtils.isBlank(weight) && StringUtils.isBlank(cubage)) ? new ResultModel(ResultModel.ResultEnum.CODE_0007) : new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }

    public static ResultModel checkOrderTime(String orderTime) {
        if(PubUtils.isSEmptyOrNull(orderTime)) {
            return new ResultModel(CODE_0011.getCode(), CODE_0011.getDesc());
        }
        return new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }


    public static void validateParam(Boolean condition, String errorMsg) {
        if (!condition) {
            logger.error("参数校验失败, 失败原因:{}", errorMsg);
            throw new BusinessException(errorMsg);
        }
    }

    public static void checkArgument(boolean result, ResultCodeEnum message){
        if(result){
            throw new BusinessException(message.code(), message.getMsg());
        }
    }

}
