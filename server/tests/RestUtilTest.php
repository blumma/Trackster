<?php

class RestUtilTest extends PHPUnit_Framework_TestCase {

  private $http;

  
  public function setUp() {
    $this->http = new GuzzleHttp\Client([
      'base_uri' => 'http://mobile-apps.dev'
    ]);
  }


  public function tearDown() {
    $this->http = null;
  }


  public function test_parse_request() {
    // @mblum TODO
  }


  public function test_rest_response() {
    // @mblum TODO
  }


  public function test_rest_error_response() {
    // @mblum TODO
  }

}

?>