var HttpService = {
};
HttpService.call = function(action, params,options){
    var option = {};
    option.validate = function(){
        return true;
    }

     option.beforeSubmit = function(){
       //console.log('BeforeSubmit >>>> do you need me show animation ?');
    };
    option.success = function(response){
       // alert("SUCCESS LONG");
      // console.log('SUCCESS >>>>'+response);
    };
    option.error = function(response){
    //  console.log('ERROR >>>>'+response);
    };
    option.redirect=function(response){
         return null;
    };
    option.complete = function(){
      // console.log('Complete >>>> do you need me close animation ?');
    };
    $.extend(option,options);
    //do validation
     if(!option.validate()){
         return false;
     }
    //before submit data handling
   option.beforeSubmit.call(this);
    // ajax call service
      $.ajax(action, {
          'type':'post',
          'dataType': 'json',
          'traditional': true,	// Server only supports traditional style params
          'data': params,
          'contentType': "application/x-www-form-urlencoded; charset=utf-8",
          'success': function(response, status, xhr){
            if(response.result.toUpperCase() == "LOGIN"){
                window.location.href="/login";
                return;
            }
            if(response.result.toUpperCase() == "REDIRECT"){
                option.redirect(response);
                return;
            }
            if(response.result.toUpperCase() == "SUCCESS"){
                option.success(response);
                return;
            }
            option.error(response);

        },
        'error': function(xhr, status, error){
            var response = {};
            // Parse json response
            try{
                response = $.parseJSON(xhr.responseText);
            }catch(e){
                error = 'parse_error';
                response = 'parse json error!';
            }
            option.error(response);
        },
        'complete':function(res){

            option.complete();
        }
    });
};
window.HttpService = HttpService;