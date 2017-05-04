<?php

require __dir__ . '/db-handler.php';

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;


/**
 * restResponse()
 *
 * Generel JSON response handler
 * Slim's withJson() function has problems with unicode characters -> use
 * this function instead of $response->withJson();
 *
 */
function sendRestResponse($response, $data = "", $status = 200) {

  $json = json_encode($data, JSON_UNESCAPED_UNICODE | JSON_NUMERIC_CHECK);
  
  return $response
    ->withHeader('Content-Type', 'application/json')
    ->withStatus($status)
    ->write($json);
}


/**
 * sendErrorReponse()
 *
 * Send error response, with json content type and default 500 status
 * response data is wrapped in message field
 *
 */
function sendErrorReponse($response, $message = "", $status = 500) {
  $data = array(
      'message' => $message
    );

  return sendRestResponse($response, $data, $status);
}


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


?>