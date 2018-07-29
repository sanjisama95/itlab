<!DOCTYPE html>
<html>
<head>
	<?php require_once 'head_template.php';?>
</head>
<body>
	<?php
require_once 'value_default.php';
if (isset($_SESSION[KEY_USERNAME])) {

	?>
		<h1>This is another tab </h1>
		<h1> Hello <?php print($_SESSION[KEY_USERNAME]);?>!</h1>
		<?php
} else {
	?>
		<h1>This is another tab </h1>
		<h1>There is no user was logged-in</h1>

		<?php
}
?>
</body>
</html>