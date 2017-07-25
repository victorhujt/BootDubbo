package com.xescm.ofc.model.dto.csc;

import com.xescm.csc.model.dto.CscGoodsImportDto;

import java.io.Serializable;

/**
 * Created by wangsongtao on 2016/12/26.
 */
public class OfcGoodsImportDto implements Serializable {
    private static final long serialVersionUID = 626828340488869922L;
    private String id;
    private String goodsCode;
    private String goodsName;
    private String goodsType;
    private String smallGoodsType;
    private String hiddenSecondGoodsTypeList;
    private String keeptemperate;
    private String brand;
    private String specification;
    private String unit;
    private String customerCode;
    private String serialNo;
    private String creator;
    private String creatorId;
    private String state;
    private String barCode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHiddenSecondGoodsTypeList() {
        return hiddenSecondGoodsTypeList;
    }

    public void setHiddenSecondGoodsTypeList(String hiddenSecondGoodsTypeList) {
        this.hiddenSecondGoodsTypeList = hiddenSecondGoodsTypeList;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    private String unitPrice;

    public OfcGoodsImportDto() {
    }

    public String getId() {
        return this.id;
    }

    public String getGoodsCode() {
        return this.goodsCode;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public String getGoodsType() {
        return this.goodsType;
    }

    public String getSmallGoodsType() {
        return this.smallGoodsType;
    }

    public String getKeeptemperate() {
        return this.keeptemperate;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getSpecification() {
        return this.specification;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getCustomerCode() {
        return this.customerCode;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public String getCreator() {
        return this.creator;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public String getState() {
        return this.state;
    }

    public String getBarCode() {
        return this.barCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public void setSmallGoodsType(String smallGoodsType) {
        this.smallGoodsType = smallGoodsType;
    }

    public void setKeeptemperate(String keeptemperate) {
        this.keeptemperate = keeptemperate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof OfcGoodsImportDto)) {
            return false;
        } else {
            OfcGoodsImportDto other = (OfcGoodsImportDto)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                label191: {
                    String this$id = this.getId();
                    String other$id = other.getId();
                    if(this$id == null) {
                        if(other$id == null) {
                            break label191;
                        }
                    } else if(this$id.equals(other$id)) {
                        break label191;
                    }

                    return false;
                }

                String this$goodsCode = this.getGoodsCode();
                String other$goodsCode = other.getGoodsCode();
                if(this$goodsCode == null) {
                    if(other$goodsCode != null) {
                        return false;
                    }
                } else if(!this$goodsCode.equals(other$goodsCode)) {
                    return false;
                }

                String this$goodsName = this.getGoodsName();
                String other$goodsName = other.getGoodsName();
                if(this$goodsName == null) {
                    if(other$goodsName != null) {
                        return false;
                    }
                } else if(!this$goodsName.equals(other$goodsName)) {
                    return false;
                }

                label170: {
                    String this$goodsType = this.getGoodsType();
                    String other$goodsType = other.getGoodsType();
                    if(this$goodsType == null) {
                        if(other$goodsType == null) {
                            break label170;
                        }
                    } else if(this$goodsType.equals(other$goodsType)) {
                        break label170;
                    }

                    return false;
                }

                label163: {
                    String this$smallGoodsType = this.getSmallGoodsType();
                    String other$smallGoodsType = other.getSmallGoodsType();
                    if(this$smallGoodsType == null) {
                        if(other$smallGoodsType == null) {
                            break label163;
                        }
                    } else if(this$smallGoodsType.equals(other$smallGoodsType)) {
                        break label163;
                    }

                    return false;
                }

                String this$keeptemperate = this.getKeeptemperate();
                String other$keeptemperate = other.getKeeptemperate();
                if(this$keeptemperate == null) {
                    if(other$keeptemperate != null) {
                        return false;
                    }
                } else if(!this$keeptemperate.equals(other$keeptemperate)) {
                    return false;
                }

                String this$brand = this.getBrand();
                String other$brand = other.getBrand();
                if(this$brand == null) {
                    if(other$brand != null) {
                        return false;
                    }
                } else if(!this$brand.equals(other$brand)) {
                    return false;
                }

                label142: {
                    String this$specification = this.getSpecification();
                    String other$specification = other.getSpecification();
                    if(this$specification == null) {
                        if(other$specification == null) {
                            break label142;
                        }
                    } else if(this$specification.equals(other$specification)) {
                        break label142;
                    }

                    return false;
                }

                label135: {
                    String this$unit = this.getUnit();
                    String other$unit = other.getUnit();
                    if(this$unit == null) {
                        if(other$unit == null) {
                            break label135;
                        }
                    } else if(this$unit.equals(other$unit)) {
                        break label135;
                    }

                    return false;
                }

                String this$customerCode = this.getCustomerCode();
                String other$customerCode = other.getCustomerCode();
                if(this$customerCode == null) {
                    if(other$customerCode != null) {
                        return false;
                    }
                } else if(!this$customerCode.equals(other$customerCode)) {
                    return false;
                }

                label121: {
                    String this$serialNo = this.getSerialNo();
                    String other$serialNo = other.getSerialNo();
                    if(this$serialNo == null) {
                        if(other$serialNo == null) {
                            break label121;
                        }
                    } else if(this$serialNo.equals(other$serialNo)) {
                        break label121;
                    }

                    return false;
                }

                String this$creator = this.getCreator();
                String other$creator = other.getCreator();
                if(this$creator == null) {
                    if(other$creator != null) {
                        return false;
                    }
                } else if(!this$creator.equals(other$creator)) {
                    return false;
                }

                label107: {
                    String this$creatorId = this.getCreatorId();
                    String other$creatorId = other.getCreatorId();
                    if(this$creatorId == null) {
                        if(other$creatorId == null) {
                            break label107;
                        }
                    } else if(this$creatorId.equals(other$creatorId)) {
                        break label107;
                    }

                    return false;
                }

                String this$state = this.getState();
                String other$state = other.getState();
                if(this$state == null) {
                    if(other$state != null) {
                        return false;
                    }
                } else if(!this$state.equals(other$state)) {
                    return false;
                }

                String this$barCode = this.getBarCode();
                String other$barCode = other.getBarCode();
                if(this$barCode == null) {
                    if(other$barCode != null) {
                        return false;
                    }
                } else if(!this$barCode.equals(other$barCode)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof CscGoodsImportDto;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        String $id = this.getId();
        int result1 = result * 59 + ($id == null?43:$id.hashCode());
        String $goodsCode = this.getGoodsCode();
        result1 = result1 * 59 + ($goodsCode == null?43:$goodsCode.hashCode());
        String $goodsName = this.getGoodsName();
        result1 = result1 * 59 + ($goodsName == null?43:$goodsName.hashCode());
        String $goodsType = this.getGoodsType();
        result1 = result1 * 59 + ($goodsType == null?43:$goodsType.hashCode());
        String $smallGoodsType = this.getSmallGoodsType();
        result1 = result1 * 59 + ($smallGoodsType == null?43:$smallGoodsType.hashCode());
        String $keeptemperate = this.getKeeptemperate();
        result1 = result1 * 59 + ($keeptemperate == null?43:$keeptemperate.hashCode());
        String $brand = this.getBrand();
        result1 = result1 * 59 + ($brand == null?43:$brand.hashCode());
        String $specification = this.getSpecification();
        result1 = result1 * 59 + ($specification == null?43:$specification.hashCode());
        String $unit = this.getUnit();
        result1 = result1 * 59 + ($unit == null?43:$unit.hashCode());
        String $customerCode = this.getCustomerCode();
        result1 = result1 * 59 + ($customerCode == null?43:$customerCode.hashCode());
        String $serialNo = this.getSerialNo();
        result1 = result1 * 59 + ($serialNo == null?43:$serialNo.hashCode());
        String $creator = this.getCreator();
        result1 = result1 * 59 + ($creator == null?43:$creator.hashCode());
        String $creatorId = this.getCreatorId();
        result1 = result1 * 59 + ($creatorId == null?43:$creatorId.hashCode());
        String $state = this.getState();
        result1 = result1 * 59 + ($state == null?43:$state.hashCode());
        String $barCode = this.getBarCode();
        result1 = result1 * 59 + ($barCode == null?43:$barCode.hashCode());
        return result1;
    }

    public String toString() {
        return "CscGoodsImportDto(id=" + this.getId() + ", goodsCode=" + this.getGoodsCode() + ", goodsName=" + this.getGoodsName() + ", goodsType=" + this.getGoodsType() + ", smallGoodsType=" + this.getSmallGoodsType() + ", keeptemperate=" + this.getKeeptemperate() + ", brand=" + this.getBrand() + ", specification=" + this.getSpecification() + ", unit=" + this.getUnit() + ", customerCode=" + this.getCustomerCode() + ", serialNo=" + this.getSerialNo() + ", creator=" + this.getCreator() + ", creatorId=" + this.getCreatorId() + ", state=" + this.getState() + ", barCode=" + this.getBarCode() + ")";
    }
}