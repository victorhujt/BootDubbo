![image](http://ued.xianyiscm.com:8083/nby/logo_nby.png)
![image](http://ued.xianyiscm.com:8083/nby/xian.png)
<#list dailyAccountInfo as da>
        <#if da_index==0>
            <#if da.baseName!="">
            > **${da.baseName}**
                <#if  da.additionalOrderPercent?exists && da.additionalOrderPercent!="">
                    - 补录订单  **${da.additionalOrderPercent}**
                </#if>
                <#if da.receivablePercent?exists &&  da.receivablePercent!="">
                    - 应收确认日清  **${da.receivablePercent}**
                </#if>
                <#if da.payablePercent?exists && da.payablePercent!="">
                    - 应付确认日清  **${da.payablePercent}**
                </#if>
            <#--<#elseif da.areaName!="">-->
            <#--> **${da.areaName}**-->
                <#--<#if  da.additionalOrderPercent?exists && da.additionalOrderPercent!="">-->
                    <#--- 补录订单  **${da.additionalOrderPercent}**-->
                <#--</#if>-->
                <#--<#if da.receivablePercent?exists && da.receivablePercent!="">-->
                    <#--- 应收确认日清  **${da.receivablePercent}**-->
                <#--</#if>-->
                <#--<#if da.payablePercent?exists && da.payablePercent!="">-->
                    <#--- 应付确认日清  **${da.payablePercent}**-->
                <#--</#if>-->
            <#--<#elseif da.baseName=="" && da.areaName=="">-->
            <#--> **鲜易供应链**-->
                <#--<#if  da.additionalOrderPercent?exists && da.additionalOrderPercent!="">-->
                    <#--- 补录订单  **${da.additionalOrderPercent}**-->
                <#--</#if>-->
                <#--<#if da.receivablePercent?exists &&  da.receivablePercent!="">-->
                    <#--- 应收确认日清  **${da.receivablePercent}**-->
                <#--</#if>-->
                <#--<#if da.payablePercent?exists && da.payablePercent!="">-->
                    <#--- 应付确认日清  **${da.payablePercent}**-->
                <#--</#if>-->
            </#if>
        <#else>
            <#if da_index==dailyAccountInfo?size-1>
                <#if da.baseName!="">
                > **${da.baseName}**
                    <#if  da.additionalOrderPercent?exists && da.additionalOrderPercent!="">
                    - 补录订单  **${da.additionalOrderPercent}**
                    </#if>
                    <#if da.receivablePercent?exists &&  da.receivablePercent!="">
                    - 应收确认日清  **${da.receivablePercent}**
                    </#if>
                    <#if da.payablePercent?exists && da.payablePercent!="">
                    - 应付确认日清  **${da.payablePercent}**
                    </#if>
                <#--<#elseif da.areaName!="">-->
                <#--> **${da.areaName}**-->
                    <#--<#if  da.additionalOrderPercent?exists && da.additionalOrderPercent!="">-->
                    <#--- 补录订单  **${da.additionalOrderPercent}**-->
                    <#--</#if>-->
                    <#--<#if da.receivablePercent?exists &&  da.receivablePercent!="">-->
                    <#--- 应收确认日清  **${da.receivablePercent}**-->
                    <#--</#if>-->
                    <#--<#if da.payablePercent?exists && da.payablePercent!="">-->
                    <#--- 应付确认日清  **${da.payablePercent}**-->
                    <#--</#if>-->
                <#--<#elseif da.baseName=="" && da.areaName=="">-->
                <#--> **鲜易供应链**-->
                    <#--<#if  da.additionalOrderPercent?exists && da.additionalOrderPercent!="">-->
                    <#--- 补录订单  **${da.additionalOrderPercent}**-->
                    <#--</#if>-->
                    <#--<#if da.receivablePercent?exists &&  da.receivablePercent!="">-->
                    <#--- 应收确认日清  **${da.receivablePercent}**-->
                    <#--</#if>-->
                    <#--<#if da.payablePercent?exists && da.payablePercent!="">-->
                    <#--- 应付确认日清  **${da.payablePercent}**-->
                    <#--</#if>-->
                </#if>
            </#if>
        </#if>
</#list>
![image](http://ued.xianyiscm.com:8083/nby/xian.png)

[了解规则](${(ofcUrl)!}/ofc/rule "Title")

[显示全部](${ofcUrl!}/ofc/platformDaily "Title")





