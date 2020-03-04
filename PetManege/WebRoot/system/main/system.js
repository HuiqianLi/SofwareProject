function getPosition() { 
var top = document.documentElement.scrollTop; 
var left = document.documentElement.scrollLeft; 
var height = document.documentElement.clientHeight; 
var width = document.documentElement.clientWidth; 
return {top:top,left:left,height:height,width:width}; 
};

function pop(){ 
	var obj = document.getElementById("pop"); 
	showPop(obj);
};

function showPop(obj){ 
var width = 320; //弹出框的宽度 
var height = 300; //弹出框的高度 
//var obj = document.getElementById("pop"); 

obj.style.display = "block"; 
obj.style.position = "absolute"; 
obj.style.zIndex = "999"; 
obj.style.width = width + "px"; 
obj.style.height = height + "px"; 

var Position = getPosition(); 
leftadd = (Position.width-width)/2; 
topadd = (Position.height-height)/2; 
obj.style.top = (Position.top + topadd) + "px"; 
obj.style.left = (Position.left + leftadd) + "px"; 

window.onscroll = function (){ 
var Position = getPosition(); 
obj.style.top = (Position.top + topadd) +"px"; 
obj.style.left = (Position.left + leftadd) +"px"; 
};
};

function hidePop(){ 
document.getElementById("pop").style.display = "none"; 
};



