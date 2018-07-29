<?php
require_once 'vendor/autoload.php';
Predis\Autoloader::register();
use Predis\Client;

class MyRedis {
	private $client;
	public function __construct() {
		$this->client = new Client(array(
			'scheme' => 'tcp',
			'host' => '127.0.0.1',
			'port' => 6379,
		));
	}

	public function getClient() {
		return $this->client;
	}
}
?>