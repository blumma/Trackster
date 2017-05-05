<?php 
  
require __DIR__ . '/../vendor/autoload.php';

require __DIR__ . '/../api/config.php';

session_start();

// Instantiate the app
$app = new \Slim\App();

<<<<<<< HEAD
  session_start();
  
=======
// Register routes
require __DIR__ . '/../api/routes.php';
>>>>>>> origin/develop

$app->run();
 
?>