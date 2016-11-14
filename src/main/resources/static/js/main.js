$(function () {
    'use strict';
    // var $citypicker1 = $('#city-picker1');
    // $citypicker1.citypicker();
    // var $citypicker2 = $('#city-picker2');
    // $citypicker3.citypicker({
    //     province: '河北省',
    //     city: '邯郸市',
    //     district: '磁县'
    // });

    var $citypicker1 = $('#city-picker1');
  $citypicker1.attr('value','');
  $citypicker1.on("click",function(){
        $citypicker1.citypicker('reset');

    });
    $('#reset').click(function () {
      $citypicker1.citypicker('reset');
        // console.log($(".title span:eq(1)").html());
        // console.log($(".title span:eq(1)").attr("data-code"));
        // console.log($(".title span:eq(2)").html());
        // console.log($(".title span:eq(2)").attr("data-code"));
        // console.log($(".title span:eq(3)").html());
        // console.log($(".title span:eq(3)").attr("data-code"));
        // console.log($(".title span:nth-child(4)").html());
        // console.log($(".title span:nth-child(4)").attr("data-code"));
    });
    $('#destroy').click(function () {
      $citypicker1.citypicker('close');
      console.log($citypicker1.val());
    });
    //
    //$('#distpicker1').distpicker();
    //
    //$('#distpicker2').distpicker({
    //  province: '---- 所在省 ----',
    //  city: '---- 所在市 ----',
    //  district: '---- 所在区 ----'
    //});
    //
    //$('#distpicker3').distpicker({
    //  province: '浙江省',
    //  city: '杭州市',
    //  district: '西湖区'
    //});
    //
    //$('#distpicker4').distpicker({
    //  placeholder: false
    //});
    //
    //$('#distpicker5').distpicker({
    //  autoSelect: false
    //});
});