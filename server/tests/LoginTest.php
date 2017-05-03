<?php

class LoginTest extends PHPUnit_Framework_TestCase {

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


  public function test_post_login() {

    $data = array(
      'action' => 'login',
      'data' => [
        'email' => 'max.mustermann@test.com',
        'pwd' => 'test123'
      ]
    );

    $user = array(
      'id' => 1,
      'first_name' => 'Test',
      'last_name' => 'Testtest',
      'email' => 'max.mustermann@test.com',
      'created_at' => '2017-05-01'
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
    $this->assertEquals(json_encode($user), json_encode($response_data['data']));
  }


  public function test_post_login_fail() {

    // INFO: make sure the user is NOT in the db, otherwise this test case will 
    //       fail!
    $data = array(
      'action' => 'login',
      'data' => [
        'email' => 'no_user',
        'pwd' => 'pwd123'
      ]
    );

    $response = $this->http->request('POST', '/', [
      'body' => json_encode($data),
      'headers' => [
        'Content-Type' => 'application/json'
      ] 
    ]);

    $this->assertEquals(401, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertArrayHasKey('message', $response_data);
    $this->assertEquals('Login failed.', $response_data['message']);
  }

}

?>