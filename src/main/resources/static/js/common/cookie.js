/**
 * Created by 孟柯楠 on 2017/1/16.
 */

// 上次选择的开单员
function setLastUser() {
    // 设置当前用户上次选择的开单员
    var loginUser = $('#login_user').html();
    var lastSelMer = getCookie(loginUser);

    if (lastSelMer != '') {
        $("#merchandiser").val(lastSelMer);
    }

    // 获取指定名称的cookie值
    function getCookie(name){
        var strCookie=document.cookie;
        var arrCookie=strCookie.split("; ");
        for(var i=0;i<arrCookie.length;i++){
            var arr=arrCookie[i].split("=");
            if(arr[0]==name){
                return unescape(arr[1]);
            } else{
                continue;
            }
        }
        return "";
    }
}

// 更新开单员

// 检查cookie是否存在旧值，如果不存在则创建，
// 如果存在则判断新旧值是否相同，不同的更新
function checkAndSetCookie(keyName, value) {
    var oldVal = getCookie(keyName);
    if(oldVal != '') {
        if (oldVal != value) {
            editCookie(keyName, value, -1);
        }
    } else {
        addCookie(keyName, value, -1)
    }
}

// 获取指定名称的cookie值
function getCookie(name){
    var strCookie=document.cookie;
    var arrCookie=strCookie.split("; ");
    for(var i=0;i<arrCookie.length;i++){
        var arr=arrCookie[i].split("=");
        if(arr[0]==name){
            return unescape(arr[1]);
        } else{
            continue;
        }
    }
    return "";
}


function updateLastUser() {


    // 设置上次开单员
    var merchandiser = $("#merchandiser").val();
    checkAndSetCookie(loginUser, merchandiser);
}
// 根据指定名称的cookie修改cookie的值
function editCookie(name,value,expiresHours){
    var cookieString=name+"="+escape(value);
    //判断是否设置过期时间,0代表关闭浏览器时失效
    if(expiresHours>0){
        var date=new Date();
        date.setTime(date.getTime+expiresHours*3600*1000); //单位是多少小时后失效
        cookieString=cookieString+"; expires="+date.toGMTString();
    }
    document.cookie=cookieString;
}

// 添加cookie
function addCookie(name,value,expiresHours){
    var cookieString=name+"="+escape(value);
    //判断是否设置过期时间,0代表关闭浏览器时失效
    if(expiresHours>0){
        var date=new Date();
        date.setTime(date.getTime+expiresHours*3600*1000);
        cookieString=cookieString+"; expires="+date.toGMTString();
    }
    document.cookie=cookieString;
}
