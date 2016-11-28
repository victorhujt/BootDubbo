<head>
    <style type="text/css">
        #goodsListDiv {

            position:absolute;

            left:138px;

            top:91px;

            width:946px;

            height:auto;

            z-index:3;

            overflow: auto;

            border:solid #7A7A7A 4px;

        }

        #consignorListDiv {
            position:absolute;

            left:138px;

            top:91px;

            width:946px;

            height:auto;

            z-index:3;

            overflow: auto;

            border:solid #7A7A7A 4px;
        }

        #consigneeListDiv {
            position:absolute;

            left:138px;

            top:91px;

            width:946px;

            height:auto;

            z-index:3;

            overflow: auto;

            border:solid #7A7A7A 4px;
        }


        #supportListDiv {
            position:absolute;

            left:138px;

            top:91px;

            width:946px;

            height:500px;

            z-index:3;

            overflow: auto;

            border:solid #7A7A7A 4px;
        }
    </style>
</head>
<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<!--goodsListDiv-->
<div class="modal-content" id="consignorListDiv" style="display: none;">
    <div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">发货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <div class="widget-main">
                <form id="consignorSelConditionForm"  class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="consignorName2" name="contactCompanyName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="consignorPerson2" name="contactName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="consignorPhoneNumber2" name="phone" type="text">
                            <#--<input id="purpose2" name="purpose" type="hidden" value="2">-->
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <span id="consignorSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        <#--   </div>
       </div>-->
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            选择
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">传真</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">Email</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">邮编</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>

                    </thead>
                    <tbody id="contactSelectListTbody2"></tbody>
                </table>
            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="consignorListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="contactinEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>

<!-- /section:basics/navbar.layout -->



<!-- basic scripts -->

<!--[if !IE]> -->
<!-- <![endif]-->

</body>