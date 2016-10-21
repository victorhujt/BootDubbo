<script type="text/javascript" src="${STATIC!}/tools/js/cross_domain_storage.js"></script>
<#--<script type="text/javascript" src="${STATIC!}/tools/js/validate.js"></script>-->
<#--<script type="text/javascript" src="${STATIC!}/tools/js/addclear.js"></script>-->
<script type="text/javascript">
    var options = {
        origin: "${crossDomainUrl!}",
        path: "/tools/crossd_iframe.html"
    };
    var myStorage = null;
    $(document).ready(function() {
        myStorage = cross_domain_storage(options);
        setAjaxHeader();
    });

    function setAjaxHeader(){
        myStorage.getItem('token', function (key,spm) {
            $(document).ajaxSend( function(event,jqXHR,options){
                jqXHR.setRequestHeader('Authorization',"Bearer " + spm);
            });
        });
    }


    $(document).ajaxComplete( function(event,jqXHR,options){
        var newSpm = jqXHR.getResponseHeader('newtoken');
        if(newSpm){
            myStorage.setItem('token',newSpm);
            setAjaxHeader();
        }
    });

    $(function(){
        $("input").attr("value","");
    });

</script>