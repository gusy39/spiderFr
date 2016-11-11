function parse(str){
    var html = $(str);
    var liList = html.find(".child-list li");
    var sql ="";

    for(var i=0;i<liList.length;i++) {
        var goods_sub_id = liList.eq(i).find("a").attr("href").substring(28);
        var num = $.trim(liList.eq(i).html().split("</a>")[1]);

        sql +=' INSERT INTO `goods_sub` (`goods_id_wlb`, `goods_sub_id`, `shop_name`, `num`) VALUES ("${goods_id_wlb}", "' + goods_sub_id + '", "${shop_name}", "' + num + '")@#!';
    }
    return sql;
}

