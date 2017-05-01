<?php

class RouterTest extends PHPUnit_Framework_TestCase {

  private $http;


  public function setUp() {
    $this->http = new GuzzleHttp\Client([
      'base_uri' => 'http://mobile-apps.dev'
    ]);
  }
  

  public function tearDown() {
    $this->http = null;
  }


  public function test_post_login() {

    $data = array(
      'action' => 'login',
      'data' => [
        'username' => 'user',
        'password' => 'pwd123'
      ]
    );

    $response = $this->http->request('POST', '/', [
      'body' => json_encode($data),
      'headers' => [
        'Content-Type' => 'application/json'
      ] 
    ]);

    $this->assertEquals(200, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertArrayHasKey('data', $response_data);
    // $this->assertArrayHasKey('uid', $response_data['data']);
  }


}

?>