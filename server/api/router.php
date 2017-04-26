<?php

  require __DIR__ . '/../api/user-service.php';
  // require __DIR__ . '/../api/student-service.php';
  // require __DIR__ . '/../api/data-service.php';


  /**
   * route_request()
   *
   * Basic RESTful request router
   *
   */
  function route_request($request) {

    $action = $request['payload']['action'];

    switch ($action) {
      
      case 'login':
        // TODO: use namespaces here
        login($request);
        break;
      
      case 'register':
        // TODO: delegate request to appropirate service -> e.g. user-service.php
        rest_response('register');
        break;

      default:
        // TODO: delegate request to appropirate service -> e.g. user-service.php
        rest_error_response('Action not found!');
        break;
    }
  }

?>