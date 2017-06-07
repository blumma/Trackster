<?php

class UserServiceTest extends PHPUnit_Framework_TestCase {

  private $http;


  public function setUp() {
    $this->http = new GuzzleHttp\Client([
      'base_uri' => 'http://mobile-apps.dev',
      'cookies' => true,
      'http_errors' => false
    ]);

    $response = $this->http->request('POST', '/api/login', [
        'headers' => [
          'Content-Type' => 'application/json'
        ],
        'json' => [
          'email' => 'test@test.com',
          'pwd' => 'test123'
        ]
      ]);

    $this->assertEquals(200, $response->getStatusCode());
  }
  

  public function tearDown() {
    $this->http->request('POST', '/api/logout');
    $this->http = null;
  }


  public function test_get_users() {

    $response = $this->http->request('GET', '/api/users', [
        'headers' => [
          'Content-Type' => 'application/json'
        ]
      ]);

    $this->assertEquals(200, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertTrue(count($response_data) > 0);

    $user = $response_data[0];

    $this->assertArrayHasKey('id', $user);
    $this->assertArrayHasKey('firstName', $user);
    $this->assertArrayHasKey('lastName', $user);
    $this->assertArrayHasKey('email', $user);
    $this->assertArrayHasKey('createdAt', $user);

    $this->assertFalse(isset($user['pwd']));

  }

  public function test_get_users_from_db() {

    $response = $this->http->request('GET', '/api/usersFromDb', [
        'headers' => [
          'Content-Type' => 'application/json'
        ]
      ]);

    $this->assertEquals(200, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertTrue(count($response_data) > 0);

    $user = $response_data[0];

    $this->assertArrayHasKey('id', $user);
    $this->assertArrayHasKey('firstName', $user);
    $this->assertArrayHasKey('lastName', $user);
    $this->assertArrayHasKey('email', $user);
    $this->assertArrayHasKey('createdAt', $user);

    $this->assertFalse(isset($user['pwd']));

  }

}

?>