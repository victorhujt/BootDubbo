![image](http://ued.xianyiscm.com:8083/nby/logo_nby.png)
![image](http://ued.xianyiscm.com:8083/nby/xian.png)
<#list dailyAccountInfo as da>
        <#if da_index==0>
            <#if da.baseName!="" || da.areaName !="">
                <#if da.baseName!="">
                > **${da.baseName}** 「小伙伴，你们太棒了」
                <#elseif da.baseName=="" || da.areaName !="">
                > **${da.areaName}** 「小伙伴，你们太棒了」
                </#if>
                <#if  da.additionalOrderPercent?exists && da.additionalOrderPercent!="">
                    - 补录订单  **${da.additionalOrderPercent}**
                </#if>
                <#if da.receivablePercent?exists &&  da.receivablePercent!="">
                    - 应收确认日清  **${da.receivablePercent}**
                </#if>
                <#if da.payablePercent?exists && da.payablePercent!="">
                    - 应付确认日清  **${da.payablePercent}**
                </#if>
            </#if>
        <#else>
            <#if da_index==dailyAccountInfo?size-1>
                <#if da.baseName!="" || da.areaName !="">
                    <#if da.baseName!="">
                    > **${da.baseName}** 「小伙伴，你们要加油了」
                    <#elseif da.baseName=="" || da.areaName !="">
                    > **${da.areaName}** 「小伙伴，你们要加油了」
                    </#if>
                    <#if  da.additionalOrderPercent?exists && da.additionalOrderPercent!="">
                    - 补录订单  **${da.additionalOrderPercent}**
                    </#if>
                    <#if da.receivablePercent?exists &&  da.receivablePercent!="">
                    - 应收确认日清  **${da.receivablePercent}**
                    </#if>
                    <#if da.payablePercent?exists && da.payablePercent!="">
                    - 应付确认日清  **${da.payablePercent}**
                    </#if>
                </#if>
            </#if>
        </#if>
</#list>
[了解规则](${(ofcUrl)!}/ofc/rule "Title")
[显示全部](${ofcUrl!}/ofc/platformDaily "Title")





