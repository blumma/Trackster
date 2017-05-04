<?php

  class DbHandler {

    private static $dbh = NULL;

    // Singleton -> make constructor private!
    private function __construct() {
    
    }

    static function getDbh() {
    
      if (self::$dbh == NULL) {
        
        self::$dbh = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
        // self::$dbh->set_charset(DB_CHARSET);
        
        if (self::$dbh->connect_error) {
          die("Failed to connect to Database: " . self::$dbh->connect_error);
        }
      }
      
      return self::$dbh;
    }
  }
  
?>