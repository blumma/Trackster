<?php

class StudentServiceTest extends PHPUnit_Framework_TestCase {

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

  public function test_get_student_by_id() {

    $response = $this->http->request('GET', '/api/student/1', [
        'headers' => [
          'Content-Type' => 'application/json'
        ]
      ]);

    $this->assertEquals(200, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $student = json_decode($response->getBody(), true);

    $this->assertArrayHasKey('id', $student);
    $this->assertArrayHasKey('kennzahl', $student);
    $this->assertArrayHasKey('klasse', $student);
    $this->assertArrayHasKey('nachname', $student);
    $this->assertArrayHasKey('vorname', $student);
    $this->assertArrayHasKey('geschlecht', $student);
    $this->assertArrayHasKey('geburtsdatum', $student);
    $this->assertArrayHasKey('performance60mRun', $student);
    $this->assertArrayHasKey('performance1000mRun', $student);
    $this->assertArrayHasKey('performanceShotPut', $student);
    $this->assertArrayHasKey('performanceLongThrow', $student);
    $this->assertArrayHasKey('performanceLongJump', $student);
    $this->assertArrayHasKey('sumPoints', $student);
  }

  public function test_get_student_by_id_fail() {

    $response = $this->http->request('GET', '/api/student/asdf', [
        'headers' => [
          'Content-Type' => 'application/json'
        ]
      ]);

    $this->assertEquals(404, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);

    $this->assertArrayHasKey('message', $response_data);
    $this->assertEquals('Student not found.', $response_data['message']);
  }

  public function test_update_student_by_id() {

    $student = array(
      "id" => 1,
      "kennzahl" => 10047099955719,
      "klasse" => "1e",
      "nachname" => "Ashborne",
      "vorname" => "Andra",
      "geschlecht" => "m",
      "geburtsdatum" => format('d.m.Y'), // H:i:s
      "performance60mRun" => 99,
      "performance1000mRun" => 3599,
      "performanceShotPut" => 0,
      "performanceLongThrow" => 0,
      "performanceLongJump" => 0,
      "sumPoints" => 0
    );

    $response = $this->http->request('POST', '/api/student/1', [
        'headers' => [
          'Content-Type' => 'application/json'
        ],
        'json' => $student
      ]);

    $this->assertEquals(200, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response = $this->http->request('GET', '/api/student/1', [
        'headers' => [
          'Content-Type' => 'application/json'
        ]
      ]);

    $this->assertEquals(200, $response->getStatusCode());

    $contentType = $response->getHeaders()["Content-Type"][0];
    $this->assertEquals("application/json", $contentType);

    $response_data = json_decode($response->getBody(), true);
    $this->assertEquals(json_encode($student), json_encode($response_data));
  }

}

?>
