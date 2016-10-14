package com.xescm.ofc.controller;


import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Created by lyh on 2016/10/13.
 */
public class test {

    public static void main(String[] args) throws Exception {

        Customer customer = new Customer();
        customer.setName("lyh");
        Address address = new Address();
        address.setStreet("xxx");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Customer.class, OrderDTO.class);
        modelMapper.createTypeMap(Address.class, OrderDTO.class);
        OrderDTO orderDTO1 = modelMapper.map(address,OrderDTO.class);
        OrderDTO orderDTO2 = modelMapper.map(customer,OrderDTO.class);
        CopyUtils.Copy(orderDTO1,orderDTO2);
        System.out.println("xxxx========="+orderDTO2);
    }
}

class OrderDTO {
    private String name;
    private String street;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "name='" + name + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}


class Customer {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

class Address {
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    private String street;


}

class CopyUtils {

    public static void Copy(Object source, Object dest) throws Exception {
        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),
                java.lang.Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean
                .getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(),
                java.lang.Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

        try {
            for (int i = 0; i < sourceProperty.length; i++) {

                for (int j = 0; j < destProperty.length; j++) {

                    if (sourceProperty[i].getName().equals(
                            destProperty[j].getName())) {
                        // 调用source的getter方法和dest的setter方法
                        destProperty[j].getWriteMethod().invoke(
                                dest,
                                sourceProperty[i].getReadMethod()
                                        .invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }

}

