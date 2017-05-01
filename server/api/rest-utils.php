<?php


  /**
   * parse_request()
   *
   * Basic RESTful HTTP-Request parser
   *
   */
  function parse_request() {

    $method = $_SERVER['REQUEST_METHOD'];
    $path = $_SERVER['REQUEST_URI'];
    $payload = json_decode(file_get_contents('php://input'), true);

    $request = array(
      'method' => $method,
      'path' => $path,
      'payload' => $payload
    );

    return $request;
  }



  /**
   * rest_response()
   *
   * Basic RESTful response handler
   *
   */
  function rest_response($data) {

    $res = array();

    if ($data) {
      $res['data'] = $data;
    }

    $json = json_encode($res);
    header('Content-Type: application/json');
    http_response_code(200);
    echo $json;
  }


  /**
   * rest_error_response()
   *
   * Basic RESTful error response handler
   *
   */
  function rest_error_response($message, $status = 500) {
    
    $res = array();

    if ($message) {
      $res['message'] = $message;
    }

    $json = json_encode($res);
    header('Content-Type: application/json');
    http_response_code($status);
    echo $json;
  }


  function is_logged_in($callback) {

    if (isset($_SESSION['user'])) {
      if (is_callable($callback)) {
        $callback();
      }
    }
    else {
      rest_error_response('Not logged in.', 401);
    }
  }

?>