<?php

class StudentServiceTest extends PHPUnit_Framework_TestCase {

  private $http;


  public function setUp() {
    $this->http = new GuzzleHttp\Client([
      'base_uri' => 'http://mobile-apps.dev'
    ]);
  }
  

  public function tearDown() {
    $this->http = null;
  }

  public function test_get_students() {

    $response = $this->http->request('GET', '/api/students', [
        'headers' => [
          'Content-Type' => 'application/json'
        ]
      ]);

    $this->assertEquals(200, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertTrue(count($response_data) > 0);

    $students = $response_data[0];

    $this->assertArrayHasKey('id', $students);
    $this->assertArrayHasKey('kennzahl', $students);
    $this->assertArrayHasKey('klasse', $students);
    $this->assertArrayHasKey('nachname', $students);
    $this->assertArrayHasKey('vorname', $students);
    $this->assertArrayHasKey('geschlecht', $students);
    $this->assertArrayHasKey('geburtsdatum', $students);
    $this->assertArrayHasKey('performance60mRun', $students);
    $this->assertArrayHasKey('performance1000mRun', $students);
    $this->assertArrayHasKey('performanceShotPut', $students);
    $this->assertArrayHasKey('performanceLongThrow', $students);
    $this->assertArrayHasKey('performanceLongJump', $students);
    $this->assertArrayHasKey('sumPoints', $students);
  }

}

?>
