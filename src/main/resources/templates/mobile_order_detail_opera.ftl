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
        .imgClass{
            float: left;
            width:188px;
            height:198px;
            border:1px solid #cacaca;
            margin-top: 20px;
            overflow: hidden;
        }
        .imgClass img{
            width: 100%;
            height:100%;
        }
     /*   .row{
            max-width: 1250px;
            min-width: 1250px;

        }*/
        .laypage_curr{
            margin-top: 24px;
            margin-left:12px;
            float: left;
        }
        .laypage_curr a{
            margin-right: 204px;
        }
      /*  .laypage_curr a{
            float: right;
        }*/
     /*   .col-label{
            margin-left: 71px;
        }*/
        .imgone{
           margin-right: 10px;
        }
        .Apend{
            width: 100%;
            height: 250px;
            display: none;
            overflow: hidden;
            position: relative;
        }
        .scales,.closes{
            position: relative;
            z-index: 2;
            width: 50px;height: 50px;
            float: right;
            background: #dbdbdb;
            line-height: 50px;
        }
        .scales img,.closes img{
            background-size: 100% 100%;
            margin-left: 14px;
            width: 24px;
            height: 22px;
            display: block;
            margin-top: 16px;

         }
    </style>
</head>
<body class="no-skin">

<div class="col-xs-12" id="BApen">
    <div class="Apend">
        <img id="viewBiggerImg" src="" alt=""  class="dragAble"  style="position: absolute">
        <div class="closes"><img src="${OFC_WEB_URL!}/docs/images/clons.png" alt=""></div>
        <div class="scales"><img src="${OFC_WEB_URL!}/docs/images/scales.png" alt=""></div>
    </div>
    <div class="col-sm-6" style="float: right">

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
                    <div class="width-100 y-float">
                    <div class="form-group y-float">
                        <label class="control-label col-label no-padding-right" for="name" >流水号</label>
                        <div class="w-width-220 col-float">
                            <input class="width-100" id="orderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(mobileOrder.mobileOrderCode)!""}">
                        </div>
                    </div>
                    <div class="form-group y-float">
                        <label class="control-label col-label no-padding-right" for="name" >上传日期</label>
                        <div class="w-width-220 col-float">
                            <input class="width-100" id="uploadDate" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.uploadDate?string("yyyy-MM-dd"))!""}">
                        </div>
                    </div>
                       <div class="form-group y-float">
                           <label class="control-label col-label no-padding-right" for="name" >钉钉账号</label>
                           <div class="w-width-220 col-float">
                               <input class="width-100" id="dingdingAccountNo" name="dingdingAccountNo" type="search" placeholder=""
                                      aria-controls="dynamic-table"
                                      value="${(mobileOrder.dingdingAccountNo)!""}">
                           </div>
                       </div>
                       <div class="form-group y-float" style="clear:left">
                           <label class="control-label col-label no-padding-right" for="name" >开单员</label>
                           <div class="w-width-220 col-float">
                               <input class="width-100" id="operator" name="operator" type="search" placeholder=""
                                      aria-controls="dynamic-table"
                                      value="${(mobileOrder.operator)!""}">
                           </div>
                       </div>
                       <div class="form-group y-float">
                           <label class="control-label col-label no-padding-right" for="name" >业务类型</label>
                           <div class="w-width-220 col-float">
                           <#if mobileOrder.businessType  =="602">
                               <input class="width-100" id="businessType" name="businessType" type="search" placeholder=""
                                      aria-controls="dynamic-table" value="卡班">
                           <#elseif  mobileOrder.businessType =="600">
                               <input class="width-100" id="businessType" name="businessType" type="search" placeholder=""
                                      aria-controls="dynamic-table" value="城配">
                           <#elseif  mobileOrder.businessType =="601">
                               <input class="width-100" id="businessType" name="businessType" type="search" placeholder=""
                                      aria-controls="dynamic-table" value="干线">
                           </#if>
                           </div>
                       </div>
                        <div class="form-group y-float">
                            <label class="control-label col-label no-padding-right" for="name" >运输单号</label>
                            <div class="w-width-220 col-float">
                                <input class="width-100" id="tranCode" name="tranCode" type="search" placeholder=""
                                       aria-controls="dynamic-table"
                                       value="${(mobileOrder.tranCode)!""}">
                            </div>
                        </div>
                       <div class="form-group y-float" style="clear:left">
                           <label class="control-label col-label no-padding-right" for="name" >订单编号</label>
                           <div class="w-width-220 col-float">
                               <input class="width-100" id="tranCode" name="OrderCode" type="search" placeholder=""
                                      aria-controls="dynamic-table"
                                      value="${(mobileOrder.OrderCode)!""}">
                           </div>
                       </div>
                       <div class="form-group y-float">
                           <label class="control-label col-label no-padding-right" for="name" >受理人</label>
                           <div class="w-width-220 col-float">
                               <input class="width-100" id="tranCode" name="accepter" type="search" placeholder=""
                                      aria-controls="dynamic-table"
                                      value="${(mobileOrder.accepter)!""}">
                           </div>
                       </div>
                       <div class="form-group y-float">
                        <label class="control-label col-label no-padding-right" for="name" >受理时间</label>
                        <div class="w-width-220 col-float">
                            <input class="width-100" id="uploadDate" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.appcetDate?string("yyyy-MM-dd"))!""}">
                        </div>
                       </div>
                    </div>
                    <div class="y-float">
                        <label class="control-label col-label no-padding-right" for="name"  style="margin-right:8px;"></label>
                        <#if mobileOrder.urls?? && (mobileOrder.urls?size > 0)>
                            <#list mobileOrder.urls as url>
                                <div class="imgClass  imgone" style="position: relative;">
                                    <img src="${url!""}" class="dragAble" onmousewheel="return onWheelZoom(this)" style="position: absolute"/>
                                </div>
                            </#list>
                        </#if>
                    </div>

                    <div class="form-group y-float" style="width:100%;">
                        <label class="control-label col-label no-padding-right" for="name" ></label>
                        <div class="laypage_curr">
                            <a href="index#/ofc/mobileOrderManageOpera" class="btn btn-white btn-info btn-bold y-rt-btn">
                                <i class="ace-icon fa fa-arrow-left"></i>
                                返回
                            </a>
                        </div>

                    </div>
                </form>
            </div>
        </div>
    </form>
</div><!-- /.col -->
<script type="text/javascript" src="../js/drag_map.js"></script>
<script>
    function Maxmin() {
        var imgs = document.getElementsByClassName("dragAble");
            for (var i=0;i<imgs.length;i++){
                (function(i){
                    imgs[i].onclick=function () {
                        var _this=this;
                        $(".Apend").css({"display":"block"});
                        $("#viewBiggerImg").attr('src',_this.src);
                        $(".scales").on("click",function () {
                            $(".dragAble").animate({
                                "transform":"rotate(90deg)"
                            })
                        })


                        return
                    }
                })(i)
            }
            $(".closes").on("click",function () {
                $(".Apend").css({"display":"block"});
            })
    }
    Maxmin();
</script>
</body>
