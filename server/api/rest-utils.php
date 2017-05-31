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

?>