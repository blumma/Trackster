<?php

class SchuelerServiceTest extends PHPUnit_Framework_TestCase {

  private $http;


  public function setUp() {
    $this->http = new GuzzleHttp\Client([
      'base_uri' => 'http://mobile-apps.dev'
    ]);
  }
  

  public function tearDown() {
    $this->http = null;
  }

  public function test_get_schueler() {

    $response = $this->http->request('GET', '/api/schueler', [
        'headers' => [
          'Content-Type' => 'application/json'
        ]
      ]);

    $this->assertEquals(200, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertTrue(count($response_data) > 0);

    $schueler = $response_data[0];

    $this->assertArrayHasKey('id', $schueler);


    $this->assertFalse(isset($user['pwd']));

  }

}

?>
