var merchants_account;
var password;
var conn = null;
var merchantsBean;
$(document).ready(function() {
	/*conn = new Easemob.im.Connection();
	conn.init({
		// 预留空现实
		onOpened : function() {
			startRequest(1);
			conn.setPresence();
		},
		onClosed : function() {
			conn.clear();
			conn.onClosed();
		}, onError : function(e) {
	        //异常处理
			showTip(-1,e.msg);
	    }
	});*/
	funPlaceholder("merchants_account");
	funPlaceholder("showPwd", "password");
});

function startRequest(index) {
	switch (index) {
	case 1:
		getDataByPost(index,
				homeurl + "merchantsController.api?merchantsLogin", {
					merchants_account : merchants_account,
					password : password
				});
		break;
	default:
		break;
	}
}

function doSuccess(index, data) {
	switch (index) {
	case 1:
		merchantsBean = data;
		SetCookie("merchants_id", merchantsBean.merchants_id);
		SetCookie("merchants_account", merchantsBean.merchants_account);
		SetCookie("password", merchantsBean.password);
		SetCookie("merchants_token", merchantsBean.merchants_token);
		SetCookie("merchants_name", merchantsBean.merchants_name);
		SetCookie("merchants_img", merchantsBean.merchants_img);
		SetCookie("hx_account", merchantsBean.hx_account);
		SetCookie("hx_pass", merchantsBean.hx_pass);
		SetCookie("hx_nick_name", merchantsBean.hx_nick_name);
		SetCookie("merchants_desc_img", merchantsBean.merchants_desc_img);
		window.location.href = 'main.html?merchants_id='
				+ merchantsBean.merchants_id;
		break;
	default:
		break;
	}
}
function enterClick() {
	loginClick();
}
function loginClick() {
	merchants_account = $("#merchants_account").val();
	password = $("#password").val();
	/*conn.open({
		user : merchants_account,
		pwd : password,
		appKey : hxAppKey
	});*/
	startRequest(1);
}

function showTip(i, msg) {
	$("#tipLabel").text(msg);
	$("#opacityDiv").show();
	$("#tipDiv").show();
	$("#buttonDiv").show();
	$("#tipTag").text(i);
}

function closeTipDivClick() {
	$("#opacityDiv").hide();
	$("#tipDiv").hide();
	$("#buttonDiv").hide();
}

function okTipClick() {
	var index = $("#tipTag").text();
	switch (index) {
	case "-1":
		closeTipDivClick();
		break;
	default:
		okTip(index);
		break;
	}
};

/*window.onbeforeunload=function checkLeave(e){ 
	conn.onClosed;
};*/
