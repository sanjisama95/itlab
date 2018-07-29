<?php
session_start();
if (isset($_POST['login'])) {
	print("post exist");
	print($_POST['password']);
} else {
	print("post not exist");
}
print("session");
echo "<pre>";
print_r($_SESSION);
echo "</pre>";

print("my site<br>");
$abcd = file_get_contents("http://ex2.web2:88/cookie.php");
print($abcd);

// $curl_handle = curl_init();
// curl_setopt($curl_handle, CURLOPT_URL, 'http://ex2.web2:88/share.php');
// curl_setopt($curl_handle, CURLOPT_RETURNTRANSFER, true);
// $buf = curl_exec($curl_handle);
// curl_close($curl_handle);
// echo strlen('hihi: ' . $buf) . "\n";

print('<br>weather site');
$abc = file_get_contents("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");

$obj = json_decode($abc);

print($abc);

print("redis<br>");
require_once 'vendor/autoload.php';

Predis\Autoloader::register();
$client = new Predis\Client(array(
	'scheme' => 'tcp',
	'host' => '127.0.0.1',
	'port' => 6379,
));

print("abbbbbbbb");
$value1 = $client->get('email');
$value2 = $client->get('password');

print('email: ' . $value1);
print('password: ' . $value2);

?>
