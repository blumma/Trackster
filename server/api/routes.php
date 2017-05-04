<?php

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
      'created_at' => '2017-05-01'
    )
  );

  array_push($users, array(
      'id' => 2,
      'firstName' => 'max',
      'lastName' => 'mustermann',
      'email' => 'max.mustermann@student.tugraz.at',
      'created_at' => '2017-04-01'
    )
  );

  array_push($users, array(
      'id' => 3,
      'firstName' => 'bat',
      'lastName' => 'man',
      'email' => 'batman@student.tugraz.at',
      'created_at' => '2017-04-10'
    )
  );

  return sendRestResponse($response, $users);
});


?>