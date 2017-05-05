<?php

require_once __dir__ .'/rest-utils.php';

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

/**
 * isLoggedIn()
 *
 * Middleware for protecting unauthorized access to specific routes
 *
 */
$isLoggedIn = function($request, $response, $next) {

 if (isset($_SESSION['user'])) {
    return $next($request, $response);
  }

  return sendErrorReponse($response, "Not authenticated.", 401);
};

?>
