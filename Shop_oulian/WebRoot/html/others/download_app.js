function ismobile(test){
    var u = navigator.userAgent, app = navigator.appVersion;
    if(/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))){
     if(window.location.href.indexOf("?mobile")<0){
      try{
       if(/iPhone|mac|iPod|iPad/i.test(navigator.userAgent)){
        return '0';
       }else{
        return '1';
       }
      }catch(e){}
     }
    }else if( u.indexOf('iPad') > -1){
        return '0';
    }else{
        return '1';
    }
};


function androidClick() {
	if(ismobile("1")=='0'){
		alert("此设备不支持下载!");
	}else{
		window.location.href="https://www.baidu.com/";
	}
}


function iosClick() {
	if(ismobile("1")=='0'){
		window.location.href="https://www.baidu.com/";
	}else{
		alert("此设备不支持下载!");
	}
}