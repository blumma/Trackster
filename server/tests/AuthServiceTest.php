<?php


class AuthServiceTest extends PHPUnit_Framework_TestCase {

  private $http;


  public function setUp() {
    $this->http = new GuzzleHttp\Client([
      'base_uri' => 'http://mobile-apps.dev',
      'cookies' => true,
      'http_errors' => false
    ]);
  }
  

  public function tearDown() {
    $this->http = null;
  }


  public function test_isLoggedIn_fail() {
    $response = $this->http->request('GET', '/api/isLoggedIn');

    $this->assertEquals(401, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertArrayHasKey('message', $response_data);
    $this->assertEquals("Not Authenticated.", $response_data['message']);
  }


  public function test_restriced_endpoint_fail() {
    $response = $this->http->request('GET', '/api/restricted');

    $this->assertEquals(401, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertArrayHasKey('message', $response_data);
    $this->assertEquals("Not Authenticated.", $response_data['message']);
  }


  public function test_login_logout() {

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

    $user = json_decode($response->getBody(), true);

    $this->assertArrayHasKey('id', $user);
    $this->assertArrayHasKey('firstName', $user);
    $this->assertArrayHasKey('lastName', $user);
    $this->assertArrayHasKey('email', $user);
    $this->assertArrayHasKey('createdAt', $user);

    $this->assertFalse(isset($user['pwd']));

    $response = $this->http->request('GET', '/api/isLoggedIn');
    $this->assertEquals(200, $response->getStatusCode());

    $response = $this->http->request('GET', '/api/restricted');
    $this->assertEquals(200, $response->getStatusCode());

    $this->http->request('POST', '/api/logout');

    $response = $this->http->request('GET', '/api/isLoggedIn');
    $this->assertEquals(401, $response->getStatusCode());

    $response = $this->http->request('GET', '/api/restricted');
    $this->assertEquals(401, $response->getStatusCode());
  }


  public function test_login_failed() {

    $response = $this->http->request('POST', '/api/login', [
        'headers' => [
          'Content-Type' => 'application/json'
        ],
        'json' => [
          'email' => '',
          'pwd' => ''
        ]
      ]);

    $this->assertEquals(401, $response->getStatusCode());

    $user = json_decode($response->getBody(), true);

    $response_data = json_decode($response->getBody(), true);

    $this->assertArrayHasKey('message', $response_data);
    $this->assertEquals("Login Failed.", $response_data['message']);

    $response = $this->http->request('GET', '/api/isLoggedIn');
    $this->assertEquals(401, $response->getStatusCode());

    $response = $this->http->request('GET', '/api/restricted');
    $this->assertEquals(401, $response->getStatusCode());
  }


}

?>