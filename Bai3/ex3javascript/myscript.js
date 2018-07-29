const KEY_IS_LOGIN = 'is_login';
const KEY_USERNAME ='KEY_USERNAME';

var username = checkCookie(KEY_IS_LOGIN);
console.log("is_login cookie: "+username);
$(document).ready(function(){
	if(username!=null){
		$('#divnotlogin').hide();
		$('#divloggedin').show();
		$('#user_info_loggedin').html("Hello, "+username);
	}else{
		$('#divloggedin').hide();
		$('#loginform').show();
	}

	$("#btnLogin").click(function(){
		var username = $('#username').val();
		var password = $('#password').val();
		console.log('username: '+username);
		console.log('password: '+password);

		var value_cookie =JSON.stringify({KEY_USERNAME:username});

		if(username=='user' && password==123){
			setCookie(KEY_IS_LOGIN,value_cookie,1);
			// alert(username +" , "+password);
			$('#divnotlogin').hide();
			$('#divloggedin').show();
			$('#user_info_loggedin').html("Hello, "+username);

		}else if (username=='' || password==''){
			alert("you have to enter username and password!!");
		}else{

			alert("username or password is incorrect!!");
		}
		
	});

	$("#btn_logout").click(function(){
		$('#divloggedin').hide();
		$('#divnotlogin').show();
		removeCookie(KEY_IS_LOGIN);

	});

});

function setCookie(cname,cvalue,exhours){
	console.log("1234");
	var d = new Date();
	d.setTime(d.getTime() + (exhours*60*60*1000));
	var expires = "expires="+ d.toUTCString();
	document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
function removeCookie(cname){
	setCookie(cname,null,-1);
}
function checkCookie(cname){
	var name = cname + "=";
	console.log(cname);
	console.log("cookie ido nothing: "+document.cookie);
	var decodedCookie = decodeURIComponent(document.cookie);
	console.log("cookie using decodeURIcompenent: "+decodedCookie);
	console.log("type of cookie: "+typeof decodedCookie);
	var ca = decodedCookie.split(';');
	console.log(ca);
	console.log(typeof ca);
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1); 
		}
		if (c.indexOf(name) == 0) {
			var value_cookie= c.substring(name.length, c.length);
			// value_cookie = "'"+value_cookie+"'";
			var username = JSON.parse(value_cookie).KEY_USERNAME;
			
			return username;
		}
	}
	return null;
}