<head>
    <title>拍照开单</title>
    <link rel="stylesheet" type="text/css" href="../css/jquery.editable-select.min.css" />
    <style type="text/css">
        #goodsListDiv,#consignorListDiv,#consigneeListDiv,#custListDiv,#goodsAndConsigneeDiv {
            position:fixed;
            left:50%;
            top:85px;
            margin-left:-400px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 1px;
        }
        .date_a{
            line-height:21px !important;
        }
        .form-group > label[class*="col-"]{
            margin-top:0;
            line-height:34px;
        }
        .form-horizontal .control-label{
            padding-top:0;
            line-height:30px;
        }
        input[type=checkbox], input[type=radio]{
            margin:10px 0 0;
        }
        .has-error .checkbox, .has-error .checkbox-inline, .has-error .control-label, .has-error .help-block, .has-error .radio, .has-error .radio-inline, .has-error.checkbox label, .has-error.checkbox-inline label, .has-error.radio label, .has-error.radio-inline label{
            color:#393939;
        }
        .has-success .checkbox, .has-success .checkbox-inline, .has-success .control-label, .has-success .help-block, .has-success .radio, .has-success .radio-inline, .has-success.checkbox label, .has-success.checkbox-inline label, .has-success.radio label, .has-success.radio-inline label{
            color:#393939;
        }
        .dataTable > thead > tr > th[class*=sort]:hover{
            color:#707070;
        }
        .dataTable > thead > tr > th[class*=sorting_]{
            color:#707070;
        }
        .has-success .form-control{
            border-color:#cacaca;
        }
        .help-block{
            color:#f00 !important;
        }
        .has-error .form-control{
            border-color:#b5b5b5 !important;
        }
        .custNameIcon:hover{color:#2868c6 !important;}
        .initBtn{
            line-height:32px;
            width:34px;
            border:1px solid #cacaca;
            background:#f7f7f7!important;
            cursor:pointer;
            position:absolute;
            top:0;
            right:0;
        }
        .initBtn:hover{
            background:#fff!important;
            border:1px solid #cacaca!important;
        }
        .col-label{
            margin-right:2px;
            margin-bottom:0;
        }
        #goodsInfoListDiv .help-block{
            line-height:20px;
        }
    </style>
</head>
<body class="no-skin">

<div class="col-xs-12">
    <div class="col-sm-6" style="float: right">
    <#--<button style="float:right;" class="btn btn-white btn-info btn-bold filters" id="goBack" value="" onclick="detailBackToHistory()">
            返回
        </button>-->
    </div>
    <form id="" method="post" class="form-horizontal" role="form">
        <div class="page-header">
            <p>
                订单详情
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">流水号</label>
                        <div class="w-width-220 col-float">
                            <input id="orderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(mobileOrder.mobileOrderCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">上传日期</label>
                        <div class="w-width-220 col-float">
                            <input id="uploadDate" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.uploadDate?string("yyyy-MM-dd"))!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">钉钉账号</label>
                        <div class="w-width-220 col-float">
                            <input id="dingdingAccountNo" name="dingdingAccountNo" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.dingdingAccountNo)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">开单员</label>
                        <div class="w-width-220 col-float">
                            <input id="operator" name="operator" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.operator)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                        <div class="w-width-220 col-float">
                        <#if mobileOrder.businessType  =="602">
                            <input id="businessType" name="businessType" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="卡班">
                        <#elseif  mobileOrder.businessType =="600">
                            <input id="businessType" name="businessType" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="城配">
                        <#elseif  mobileOrder.businessType =="601">
                            <input id="businessType" name="businessType" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="干线">
                        </#if>
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">运输单号</label>
                        <div class="w-width-220 col-float">
                            <input id="tranCode" name="tranCode" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.tranCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">订单编号</label>
                        <div class="w-width-220 col-float">
                            <input id="tranCode" name="OrderCode" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.OrderCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">受理人</label>
                        <div class="w-width-220 col-float">
                            <input id="tranCode" name="accepter" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.accepter)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">受理时间</label>
                        <div class="w-width-220 col-float">
                            <input id="uploadDate" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.appcetDate?string("yyyy-MM-dd"))!""}">
                        </div>
                        <div class="laypage_curr">
                            <a href="index#/ofc/mobileOrderManageOpera">返回</a>

                        </div>
                    </div>
                </form>
            </div>
        </div>
    </form>
</div><!-- /.col -->
</body>
