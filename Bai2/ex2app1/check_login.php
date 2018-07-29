<?php

require_once 'MyRedis.php';
require_once 'value_default.php';

// login by check redis
$myRedis = new MyRedis();
$client = $myRedis->getClient();
if ($client->exists(KEY_USERNAME)) {
	header("location:index.php?controller=home&action=loggedin");
	exit();
}

// login by check cookie
if (isset($_COOKIE[KEY_IS_LOGIN]) && !empty($_COOKIE[KEY_IS_LOGIN])) {
	$valueStoreCookie = json_decode($_COOKIE[KEY_IS_LOGIN]);
	// print("cookie page 2<br>");
	// print_r($valueStoreCookie);
	header("location:index.php?controller=home&action=loggedin");
	exit();
} else {
	print("login cookie not exist: ");
}

// print("Test<br>");

// $arrayName = array('a' => true, 'b' => null);
// print('<pre>');
// print_r($arrayName);
// print('<pre>');
// if ($arrayName['b'] == null) {

// 	print("nulll");
// } else {
// 	print("abc");
// }
// if ($arrayName['a']) {
// 	print("true");
// } else {
// 	print("false");

// }

?>