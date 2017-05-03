<?php

require __dir__ . '/../api/rest-utils.php';
require __dir__ . '/../api/router.php';

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
    // @mblum TODO: untestable due to php://input is read-only, maybe we can find
    //              a workaround?!
  }


  /**
   * @runInSeparateProcess
   */
  public function test_rest_response() {
    
    $data = array(
      'param1' => 'param1',
      'param2' => 'param2',
      'array1' => [
        'array_param1' => 'array_param1'
      ]
    );

    $respose_data = array();
    $respose_data['data'] = $data;

    $this->expectOutputString(json_encode($respose_data));
    rest_response($data);
    $this->assertEquals(200, http_response_code());
    
  }


  /**
   * @runInSeparateProcess
   */
  public function test_rest_error_response() {

    $message = 'An error occurred!';

    $respose_data = array();
    $respose_data['message'] = $message;

    $this->expectOutputString(json_encode($respose_data));
    rest_error_response($message);
    $this->assertEquals(500, http_response_code());
  }


  /**
   * @runInSeparateProcess
   */
  public function test_restricted_request() {

    $request = array();
    $request['payload'] = array(
      'action' => 'restricted',
      'data' => ''
    );

    $respose_data = array(
      'message' => 'Not logged in.'
    );    

    $this->expectOutputString(json_encode($respose_data));
    route_request($request);
    $this->assertEquals(401, http_response_code());
  }


  /**
   * @runInSeparateProcess
   */
  public function test_is_logged_in() {

    $respose_data = array(
      'message' => 'Not logged in.'
    );    

    $this->expectOutputString(json_encode($respose_data));

    is_logged_in(function() {
      $this->assertFalse(true);
    });

    $this->assertEquals(401, http_response_code());
  }

}

?>