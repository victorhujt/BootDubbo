<script type="text/javascript" src="${STATIC!}/tools/js/cross_domain_storage.js"></script>
<#--<script type="text/javascript" src="${STATIC!}/tools/js/validate.js"></script>-->
<#--<script type="text/javascript" src="${STATIC!}/tools/js/addclear.js"></script>-->
<script type="text/javascript">
    $(document).ready(function() {
        //myStorage = cross_domain_storage(options);
        //setAjaxHeader();
        var spm = window.localStorage.getItem('token');
        var chgSpm = "${token!}";
//         console.log(spm);
//         console.log(chgSpm);
        if(!spm || (chgSpm && spm != chgSpm)){
            window.localStorage.setItem('token',chgSpm);
        }
    });

    $(document).ajaxSend( function(event,jqXHR,options){
        jqXHR.setRequestHeader('Authorization',"Bearer " + window.localStorage.getItem('token'));
    });

    $(document).ajaxComplete( function(event,jqXHR,options){
        var newSpm = jqXHR.getResponseHeader('newtoken');
        if(newSpm){
            window.localStorage.setItem('spm',newSpm);
        }
    });

    $(function(){
        $("input").attr("value","");
    });

</script>