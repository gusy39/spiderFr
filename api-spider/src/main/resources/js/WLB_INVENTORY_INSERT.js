function parse(str) {
    var html = $(str);
    var trList = html.find(".list-table tbody").children("tr");
    var insertsql = "";

    for (var i = 0; i < trList.length; i++) {
        var tdArr = trList.eq(i).find("td");

        var dateArr = tdArr.eq(0).text().split("-");
        var year = $.trim(dateArr[0]);
        var month = $.trim(dateArr[1]);
        var day = 0;
        if (dateArr.length > 2) {
            day = $.trim(dateArr[2]);
        }

        var goods_name = $.trim(tdArr.eq(1).html().split("<br/>")[0]).replace("\\","\\\\").replace("'", "\\'");
        var goods_no_platform = $.trim(tdArr.eq(1).html().split("<br/>")[1]);
        var goods_no_wlb = $.trim(tdArr.eq(1).html().split("<br/>")[2]);

        var warehouse = $.trim(tdArr.eq(2).html());

        var beginning_total = $.trim(tdArr.eq(3).html().split("<br/>")[0]);
        var beginning_good = $.trim(tdArr.eq(3).html().split("<br/>")[1]);
        var beginning_bad = $.trim(tdArr.eq(3).html().split("<br/>")[2]);

        var storage_total = $.trim(tdArr.eq(4).html().split("<br/>")[0]);
        var storage_good = $.trim(tdArr.eq(4).html().split("<br/>")[1]);
        var storage_bad = $.trim(tdArr.eq(4).html().split("<br/>")[2]);

        var total_delivery = $.trim(tdArr.eq(5).html().split("<br/>")[0]);
        var good_sales_delivery = $.trim(tdArr.eq(5).html().split("<br/>")[1]);
        var good_other_delivery = $.trim(tdArr.eq(5).html().split("<br/>")[2]);
        var bad_delivery = $.trim(tdArr.eq(5).html().split("<br/>")[3]);

        var end_total = $.trim(tdArr.eq(6).html().split("<br/>")[0]);
        var end_good = $.trim(tdArr.eq(6).html().split("<br/>")[1]);
        var end_bad = $.trim(tdArr.eq(6).html().split("<br/>")[2]);

        var purchase_transit = $.trim(tdArr.eq(7).html().split("<br/>")[0]);
        var allotted_transit = $.trim(tdArr.eq(7).html().split("<br/>")[1]);
        var return_transit = $.trim(tdArr.eq(7).html().split("<br/>")[2]);

        insertsql += ' INSERT INTO `goods_stock_statistics` ( `type`, `year`, `month`, `day`, `shop_name`, `warehouse`, `goods_name`, `goods_no_platform`, `goods_no_wlb`, `beginning_total`, `beginning_good`, `beginning_bad`, `storage_total`, `storage_good`, `storage_bad`, `total_delivery`, `good_sales_delivery`, `good_other_delivery`, `bad_delivery`, `end_total`, `end_good`, `end_bad`, `purchase_transit`, `allotted_transit`, `return_transit`) VALUES ("${wlb.type}", "' + year + '", "' + month + '", "' + day + '", "${wlb.shopName}", "' + warehouse + '", "' + goods_name + '","' + goods_no_platform + '", "' + goods_no_wlb + '", "' + beginning_total + '", "' + beginning_good + '", "' + beginning_bad + '", "' + storage_total + '", "' + storage_good + '", "' + storage_bad + '", "' + total_delivery + '", "' + good_sales_delivery + '", "' + good_other_delivery + '",  "' + bad_delivery + '","' + end_total + '", "' + end_good + '", "' + end_bad + '", "' + purchase_transit + '", "' + allotted_transit + '", "' + return_transit + '")@#!';
    }
    return insertsql;
}

