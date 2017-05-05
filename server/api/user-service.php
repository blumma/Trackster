<?php

  require_once __dir__ . '/db-handler.php';

  // User service stubs

  function login($req) {

    $dbh = DbHandler::getDbh();

    $user_stmt = $dbh->prepare("SELECT id, first_name, last_name, email, "
      . "created_at FROM users WHERE email=? and pwd=?");
    
    $user_stmt->bind_param("ss", $req['email'], $req['pwd']);

    if (!$user_stmt->execute()) {
      return rest_error_response($user_stmt->error);
    } 

    $user_result = $user_stmt->get_result();   

    if ($user_result->num_rows > 0) {
      $user = $user_result->fetch_assoc();
      $_SESSION['user'] = $user;
      rest_response($user);
    }
    else {
      rest_error_response('Login failed.', 401);
    }    
  }

?>