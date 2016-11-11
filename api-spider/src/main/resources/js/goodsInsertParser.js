function parse(str){
    var html = $(str);
    var trList = html.find(".list-table tbody").children("tr").filter(function(){
        return $(this).find("table").size();
    });
    var jsonStr ="[";

    for(var i=0;i<trList.length;i++)
    {

        var tdArr = trList.eq(i).children("td");
        jsonStr += "{";
        jsonStr += '"goods_no_wlb":' + '"'+ $.trim(tdArr.eq(0).find("a").text()) +'",';

        if(tdArr.eq(1).find("a").find("b").length > 0){
            jsonStr += '"type":"zuhe",';
            jsonStr += '"goods_name_wlb":' + '"'+ $.trim(tdArr.eq(1).find("a").html().split("</b>")[1]) +'",';
        }else{
            jsonStr += '"type":"putong",';
            jsonStr += '"goods_name_wlb":' + '"'+ $.trim(tdArr.eq(1).find("a").text()) +'",';
        }

        jsonStr += '"goods_id_wlb":' + '"'+ $.trim(tdArr.eq(2).text()) +'",';

        var subTableTdArr = tdArr.eq(3).find("table td:even");
        jsonStr += '"goodsWlbTbRelationalVoList":[';
        for(var j=0; j<subTableTdArr.length; j++){

            jsonStr += "{";
            jsonStr += '"goods_id_wlb":' + '"'+  $.trim(tdArr.eq(2).text()) +'",';
            jsonStr += '"goods_id_tb":' + '"'+  $.trim(subTableTdArr.eq(j).find("a").attr("href")).substring(30) +'",';
            jsonStr += '"goods_name_tb":' + '"'+  $.trim(subTableTdArr.eq(j).find("a").text()) +'"';
            jsonStr += "},";
        }
        if(subTableTdArr.length > 0){
            jsonStr = jsonStr.substr(0, jsonStr.length - 1);
        }
        jsonStr += '],';
        jsonStr += '"seller":"",';
        jsonStr += '"audit":' + '"'+  $.trim(tdArr.eq(4).text()) +'",';
        jsonStr += '"audit_changes":' + '"'+  $.trim(tdArr.eq(5).text()) +'",';
        jsonStr += '"weight_seller":' + '"'+  $.trim(tdArr.eq(6).text()) +'",';
        jsonStr += '"weight_charging":' + '"'+  $.trim(tdArr.eq(7).text()) +'"';
        jsonStr += "},";

    }
    if(trList.length > 0){
        jsonStr = jsonStr.substr(0, jsonStr.length - 1);
    }
    jsonStr += "]";
    return jsonStr;
}

