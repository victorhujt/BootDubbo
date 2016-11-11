<script type="text/javascript">

    $(document).ajaxSend( function(event,jqXHR){
        jqXHR.setRequestHeader('Authorization',"Bearer " + window.localStorage.getItem('token'));
    });

    $(document).ajaxComplete( function(event,jqXHR){
        var newSpm = jqXHR.getResponseHeader('newtoken');
        if(newSpm){
            window.localStorage.setItem('spm',newSpm);
        }
    });

    $(function(){
        $("input").attr("value","");
    });

</script>