<?php
$controller_name = filter_input(INPUT_GET, 'controller');
if (empty($controller_name)) {
	$controller_name = 'home';
}
require_once 'controllers/' . $controller_name . '_controller.php';
$controller_name = strtolower($controller_name);
$controller_class = $controller_name . "_controller()";
$cmd = '$controller = new ' . $controller_class . ';';
eval($cmd);
$controller->run();
?>