<?php

class AuthServiceTest extends PHPUnit_Framework_TestCase {

  private $http;


  public function setUp() {
    $this->http = new GuzzleHttp\Client([
      'base_uri' => 'http://mobile-apps.dev',
      'http_errors' => false
    ]);
  }
  

  public function tearDown() {
    $this->http = null;
  }

  public function test_isLoggedIn() {
    $response = $this->http->request('GET', '/api/isLoggedIn');

    $this->assertEquals(401, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertArrayHasKey('message', $response_data);
    $this->assertEquals("Not Authenticated.", $response_data['message']);
  }


  public function test_restriced_endpoint() {

  }

  public function test_login() {

  }

  public function test_login_failed() {

  }

  public function test_logout() {

  }

}

?>