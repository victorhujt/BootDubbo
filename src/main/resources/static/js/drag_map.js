drag = 0
move = 0

// ��ק����
// �μ���http://blog.sina.com.cn/u/4702ecbe010007pe
var ie=document.all;
var nn6=document.getElementById&&!document.all;
var isdrag=false;
var y,x;
var oDragObj;

function moveMouse(e) {
 if (isdrag) {
 oDragObj.style.top  =  (nn6 ? nTY + e.clientY - y : nTY + event.clientY - y)+"px";
 oDragObj.style.left  =  (nn6 ? nTX + e.clientX - x : nTX + event.clientX - x)+"px";
 return false;
 }
}

function initDrag(e) {
 var oDragHandle = nn6 ? e.target : event.srcElement;
 var topElement = "HTML";
 while (oDragHandle.tagName != topElement && oDragHandle.className != "dragAble") {
 oDragHandle = nn6 ? oDragHandle.parentNode : oDragHandle.parentElement;
 }
 if (oDragHandle.className=="dragAble") {
 isdrag = true;
 oDragObj = oDragHandle;
 nTY = parseInt(oDragObj.style.top+0);
 y = nn6 ? e.clientY : event.clientY;
 nTX = parseInt(oDragObj.style.left+0);
 x = nn6 ? e.clientX : event.clientX;
 document.onmousemove=moveMouse;
 return false;
 }
}
document.onmousedown=initDrag;
document.onmouseup=new Function("isdrag=false");

function clickMove(s){
 if(s=="up"){
  dragObj.style.top = parseInt(dragObj.style.top) + 100;
 }else if(s=="down"){
  dragObj.style.top = parseInt(dragObj.style.top) - 100;
 }else if(s=="left"){
  dragObj.style.left = parseInt(dragObj.style.left) + 100;
 }else if(s=="right"){
  dragObj.style.left = parseInt(dragObj.style.left) - 100;
 }

}

function smallit(){            
  var height1=images1.height;            
  var width1=images1.width;            
  images1.height=height1/1.2;            
  images1.width=width1/1.2;           
}             
    
function bigit(){            
 var height1=images1.height;            
 var width1=images1.width;            
 images1.height=height1*1.2;          
 images1.width=width1*1.2;           
}             
function realsize()
{
 images1.height=images2.height;     
 images1.width=images2.width;
 block1.style.left = 0;
 block1.style.top = 0;
 
}
function featsize()
{
 var width1=images2.width;            
 var height1=images2.height;            
 var width2=701;            
 var height2=576;            
 var h=height1/height2;
 var w=width1/width2;
 if(height1<height2&&width1<width2)
 {
  images1.height=height1;            
  images1.width=width1;           
 }
 else
 {
  if(h>w)
  {
   images1.height=height2;          
   images1.width=width1*height2/height1;           
  }
  else
  {
   images1.width=width2;           
   images1.height=height1*width2/width1;          
  }
 }
 block1.style.left = 0;
 block1.style.top = 0;
}
/*if(navigator.userAgent.indexOf('Firefox') >= 0){  
    alert(0)}else{
      alert(1)
    }*/ 
if(navigator.userAgent.indexOf('Firefox') >= 0){  
    function onWheelZoom(obj){  //��������  
        zoom = parseFloat(obj.style.zoom);
        tZoom = zoom + (event.detail<0 ? 0.05 : -0.05);
        if(tZoom<0.1 ) return true;
        obj.style.zoom=tZoom;
        return false;
  
    
    }
 }else{
    function onWheelZoom(obj){  //��������  
      zoom = parseFloat(obj.style.zoom);
      tZoom = zoom + (event.wheelDelta>0 ? 0.05 : -0.05);
      if(tZoom<0.1 ) return true;
      obj.style.zoom=tZoom;
      return false;
    }
}


