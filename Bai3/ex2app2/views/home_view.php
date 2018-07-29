
<!DOCTYPE html>
<html>
<head>
  <?php require_once 'head_template.php';?>
</head>
<body>


<?php
require_once 'check_login.php';
?>

<div class="container" style="border: 1px solid;">
  <div class="row">
    <div class="mx-auto col-md-4
    ">
    <h1>Hello</h1>
    <p>username: user --- password: 123</p>
    <form action="index.php?controller=home&action=storedata" method="post">
      <div class="form-group" >
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" name ="username" placeholder="Enter user nam">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="text" class="form-control" id="password" name="password" placeholder="Password" >
      </div>
      <button type="submit" name ="login" class="btn btn-primary">Submit</button>
    </form>
  </div>
</div>
</div>
</body>
</html>