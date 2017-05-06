<?php

require_once __dir__ . '/db-handler.php';
require_once __dir__ .'/rest-utils.php';
require_once __dir__ .'/filters.php';

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;


/**
 * GET /api
 *
 * Testroute
 *
 */
$app->get('/api', function ($request, $response, $args) {
  $response->getBody()->write(' Hello Trackster ');
  return $response;
});


/**
 * GET /api
 *
 * Check isLoggedIn middleware. Use $app->get()->add($isLoggedIn) to restrict a
 * specific endpoint to authenticated users only.
 *
 */
$app->get('/api/restricted', function ($request, $response, $args) {  
  return sendRestResponse($response, "This request can only be reached by authenticated users");
})->add($isLoggedIn);


/**
 * POST /api/logout
 *
 * Logout the current user. Delete all session data.
 *
 */
$app->post('/api/logout', function ($request, $response, $args) {
  session_destroy();
  return sendRestResponse($response);
});


/**
 * GET /api/isLoggedIn
 *
 * Check if the current user has a valid session.
 *
 */
$app->get('/api/isLoggedIn', function ($request, $response, $args) {

  if (isset($_SESSION['user'])) {
    return sendRestResponse($response, $_SESSION['user']);
  }
  else {
    return sendErrorReponse($response, "Not Authenticated.", 401);
  }
});


/**
 * POST /api/login
 *
 * Login the current user. Set session to current user.
 *
 */
$app->post('/api/login', function($request, $response, $args) {

  $parsedBody = $request->getParsedBody();

  $pwd = '';
  $email = '';

  if (isset($parsedBody['pwd'])) $pwd = $parsedBody['pwd'];
  if (isset($parsedBody['email'])) $email = $parsedBody['email'];

  $dbh = DbHandler::getDbh();

  $user_stmt = $dbh->prepare("SELECT id, firstName, lastName, email, "
    . "createdAt FROM users WHERE email=? AND pwd=? LIMIT 1");

  $user_stmt->bind_param("ss", $email, $pwd);
  
  if (!$user_stmt->execute()) {
    unset($_SESSION['user']);
    return sendErrorReponse($response, $user_stmt->error);
  } 
  
  $users_result = $user_stmt->get_result();

  if ($users_result->num_rows <= 0) {
    unset($_SESSION['user']);
    return sendErrorReponse($response, "Login Failed.", 401);
  }

  $user = $users_result->fetch_assoc();

  $_SESSION['user'] = $user;

  return sendRestResponse($response, $user);
});



/**
 * GET /api/users
 *
 * Get all Users
 *
 */
$app->get('/api/users', function ($request, $response, $args) {

  $users = array();

  array_push($users, array(
      'id' => 1,
      'firstName' => 'mathias',
      'lastName' => 'blum',
      'email' => 'mathias.blum@student.tugraz.at',
      'createdAt' => '2017-05-01'
    )
  );

  array_push($users, array(
      'id' => 2,
      'firstName' => 'max',
      'lastName' => 'mustermann',
      'email' => 'max.mustermann@student.tugraz.at',
      'createdAt' => '2017-04-01'
    )
  );

  array_push($users, array(
      'id' => 3,
      'firstName' => 'bat',
      'lastName' => 'man',
      'email' => 'batman@student.tugraz.at',
      'createdAt' => '2017-04-10'
    )
  );

  return sendRestResponse($response, $users);
});


/**
 * GET /api/users
 *
 * Get all Users
 *
 */
$app->get('/api/usersFromDb', function ($request, $response, $args) {

  $dbh = DbHandler::getDbh();
  $users_stmt = $dbh->prepare("SELECT id, firstName, lastName, email, "
    . "createdAt FROM users;");
  
  if (!$users_stmt->execute()) {
    return sendErrorReponse($response, $users_stmt->error);
  } 
  
  $users_result = $users_stmt->get_result();
  
  $users = array();

  while($row = $users_result->fetch_assoc()) {
    array_push($users, $row);
  }

  return sendRestResponse($response, $users);
});


/**
 * GET /api/schueler
 *
 * Get all Schueler
 *
 */
$app->get('/api/schueler', function ($request, $response, $args) {

  $dbh = DbHandler::getDbh();
  $stmt = $dbh->prepare("SELECT id, kennzahl, klasse, nachname, vorname, geschlecht, geburtsdatum, 60m_run, 1000m_run, shot_put, long_throw, long_jump, sum_points FROM schueler");
  
  if (!$stmt->execute()) {
    return sendErrorReponse($response, $stmt->error);
  } 
  
  $result = $stmt->get_result();
  
  $schueler = array();

  while($row = $result->fetch_assoc()) {
    array_push($schueler, $row);
  }

  return sendRestResponse($response, $schueler);
});

?>
