function parse(str){
    var html = $(str);
    var trList = html.find(".list-table tbody").children("tr");
    var insertsql ="";
    var jsonStr ="[";

    for(var i=0;i<trList.length;i++)
    {
        var tdArr = trList.eq(i).find("td");
        var wld_order_sn = $.trim(tdArr.eq(0).text());

        var goods_name = $.trim(tdArr.eq(1).html().split("<br/>")[0]);
        var goods_no_platform = $.trim(tdArr.eq(1).html().split("<br/>")[1]);
        var goods_no_wlb = $.trim(tdArr.eq(1).html().split("<br/>")[2]);

        var warehouse = $.trim(tdArr.eq(2).text());
        var operate_time = $.trim(tdArr.eq(3).text());
        var operate_type = $.trim(tdArr.eq(4).text());
        var stock_type = $.trim(tdArr.eq(5).text());
        var operate_number = $.trim(tdArr.eq(6).text());
        var balance_number = $.trim(tdArr.eq(7).text());
        var erp_order_sn = $.trim(tdArr.eq(8).text());
        var out_order_sn =$.trim(tdArr.eq(9).text());

      //`shop_name`, `wld_order_sn`, `goods_name`, `goods_no_platform`, `goods_no_wlb`, `warehouse`, `operate_time`,
        // `operate_type`, `stock_type`, `operate_number`, `balance_number`, `erp_order_sn`, `out_order_sn`)
     // VALUES ("${shopName}","'+wld_order_sn+'", "'+goods_name+'", "'+goods_no_platform+'", "'+goods_no_wlb+'", "'+warehouse+'", "'+operate_time+'", "'+operate_type+'", "'+stock_type+'", "'+operate_number+'", "'+balance_number+'", "'+erp_order_sn+'", "'+out_order_sn+'")@#!';
        jsonStr += "{";
        jsonStr += '"wld_order_sn":' + '"'+ wld_order_sn +'",';
        jsonStr += '"goods_name":' + '"'+ goods_name +'",';
        jsonStr += '"goods_no_platform":' + '"'+ goods_no_platform +'",';
        jsonStr += '"goods_no_wlb":' + '"'+ goods_no_wlb +'",';
        jsonStr += '"warehouse":' + '"'+ warehouse +'",';
        jsonStr += '"operate_time":' + '"'+ operate_time +'",';
        jsonStr += '"operate_type":' + '"'+ operate_type +'",';
        jsonStr += '"stock_type":' + '"'+ stock_type +'",';
        jsonStr += '"operate_number":' + '"'+ operate_number +'",';
        jsonStr += '"balance_number":' + '"'+ balance_number +'",';
        jsonStr += '"erp_order_sn":' + '"'+ erp_order_sn +'",';
        jsonStr += '"out_order_sn":' + '"'+ out_order_sn +'"';
        jsonStr += "},";
    }
    if(trList.length > 0){
        jsonStr = jsonStr.substr(0, jsonStr.length - 1);
    }
    jsonStr += "]";
    return jsonStr;
}