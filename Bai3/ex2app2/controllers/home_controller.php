<?php
/**
 *
 */
require_once 'vendor/autoload.php';
require_once 'MyRedis.php';
require_once 'value_default.php';

class home_controller {

	private $redisClient;

	public function __construct() {
		$client = new MyRedis;
		$this->redisClient = $client->getClient();
	}
	public function run() {
		session_start();
		$action = filter_input(INPUT_GET, 'action');

		switch ($action) {
		case "loggedin":

			if (isset($_COOKIE[KEY_IS_LOGIN]) && !empty($_COOKIE[KEY_IS_LOGIN])) {
				$valueStoreCookie = json_decode($_COOKIE[KEY_IS_LOGIN]);
				$username = $valueStoreCookie->{KEY_USERNAME};
				$_SESSION[KEY_USERNAME] = $username;

				print("<h1>Hello $username,</h1><br><p>using cookie</p>");
				print("<br><a href='http://localhost/ex2app1/index.php'>Redirect to app1</a>");

				print("<br><a href='index.php?controller=home&action=newtab'>Redirect to new tab to check session</a>");
				print("<br><a href='index.php?controller=home&action=logout'>Logout</a>");

			} else {
				print("<br>Cookie is not using!!!<br>");
			}

			if (!$this->redisClient->exists(KEY_USERNAME)) {
				print("<br><br>Using redis: User is not exists!");
				print("<br><a href='index.php'>back to homepage</a>");
			} else {

				$username = $this->redisClient->get(KEY_USERNAME);
				// $password = $this->redisClient->get("password");

				print("<h1>Hello $username,</h1><br><p>using redis</p>");

				print("<br><a href='http://localhost/ex2app1/index.php'>Redirect to app1</a>");
				print("<br><a href='index.php?controller=home&action=newtab'>Redirect to new tab to check session</a>");
				print("<br><a href='index.php?controller=home&action=logout'>Logout</a>");
				$_SESSION[KEY_USERNAME] = $username;
			}
			break;

		case 'newtab':
			require_once 'views/tab_views.php';
			break;
		case "logout":
			if (isset($_COOKIE[KEY_IS_LOGIN]) && !empty($_COOKIE[KEY_IS_LOGIN])) {
				unset($_COOKIE[KEY_IS_LOGIN]);
				setcookie(KEY_IS_LOGIN, null, -1, '/');
			}

			if (isset($_SESSION[KEY_USERNAME])) {
				unset($_SESSION[KEY_USERNAME]);
			}

			$this->redisClient->flushAll();
			header("location:index.php");
			exit();
			break;
		case "storedata":
			if (isset($_POST["login"]) && !empty($_POST['password']) && !empty($_POST['username'])) {
				if ($_POST['username'] == USERNAME && $_POST['password'] == PASSWORD) {
					$username = $_POST['username'];
					$password = $_POST[KEY_PASSWORD];

					print("user: " . $username . "; pass: " . $password);

					$this->redisClient->set(KEY_USERNAME, $_POST[KEY_USERNAME]);
					// $this->redisClient->set(KEY_PASSWORD, $_POST[KEY_PASSWORD]);

					// using cookie
					$valueStoreCookie = array(KEY_USERNAME => $username);
					$valueStoreCookie = json_encode($valueStoreCookie);

					$name = KEY_IS_LOGIN;
					$value = $valueStoreCookie;
					$expire = time() + 3600;
					$path = '/';
					setcookie($name, $value, $expire, $path);
					// //end using cookie

					// redirect to page login success
					header("location:index.php?controller=home&action=loggedin");
					exit;
					// //test, you need to comment header("location:index.php?controller=home&action=loggedin"); to test

					// print("<br>bat dau test");
					// print("<br>store cookie");
					// print_r($valueStoreCookie);

					// $username1 = $this->redisClient->get(KEY_USERNAME);
					// $password1 = $this->redisClient->get("password");

					// print("<h1>Hello $username1,</h1>");

					// print("<a href='http://localhost/ex2app1/index.php'>Redirect to app1</a>");
					// print("<br><a href='index.php?controller=home&action=logout'>Logout</a>");
					// //end test

				} else {
					echo "<script>alert('The password or username that you have entered is incorred!');";
					echo "history.back(-1);</script>";
					exit;
				}

			} else {
				print('ab');
				echo "<script>alert('you need to enter username and password!!');";
				echo "history.back(-1);</script>";
				exit;
			}
			break;

		default:
			require_once "views/home_view.php";
			break;
		}
	}
}

?>