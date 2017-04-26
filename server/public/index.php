<?php 
  
  require_once __DIR__ . '/../api/rest-utils.php';
  require_once __DIR__ . '/../api/router.php';

  /* **** Main entry point ************************************************** */
  try {
    $req = parse_request();
    route_request($req);
  }
  catch(Exception $e) {
    rest_error_response('Oh, something went wrong.');
  }
 
?>