<?php
  
  $dbh = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
  $dbh->set_charset(DB_CHARSET);

  // Check for database connection error
  if ($dbh->connect_error) {
    die("Failed to connect to Database: " . $dbh->connect_error);
  }
  
?>