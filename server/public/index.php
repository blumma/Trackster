<?php 
  
require __DIR__ . '/../vendor/autoload.php';

require __DIR__ . '/../api/config.php';

session_start();

// Instantiate the app
$app = new \Slim\App();


// Register routes
require __DIR__ . '/../api/routes.php';

$app->run();
 
?>