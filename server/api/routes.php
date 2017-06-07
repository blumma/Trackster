<?php

require_once __dir__ . '/db-handler.php';
require_once __dir__ .'/rest-utils.php';
require_once __dir__ .'/filters.php';

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;


/**
 * GET /api
 *
 * Testroute
 *
 */
$app->get('/api', function ($request, $response, $args) {
  $response->getBody()->write(' Hello Trackster ');
  return $response;
});


/**
 * GET /api
 *
 * Check isLoggedIn middleware. Use $app->get()->add($isLoggedIn) to restrict a
 * specific endpoint to authenticated users only.
 *
 */
$app->get('/api/restricted', function ($request, $response, $args) {  
  return sendRestResponse($response, "This request can only be reached by authenticated users");
})->add($isLoggedIn);


/**
 * POST /api/logout
 *
 * Logout the current user. Delete all session data.
 *
 */
$app->post('/api/logout', function ($request, $response, $args) {
  session_destroy();
  return sendRestResponse($response);
});


/**
 * GET /api/isLoggedIn
 *
 * Check if the current user has a valid session.
 *
 */
$app->get('/api/isLoggedIn', function ($request, $response, $args) {

  if (isset($_SESSION['user'])) {
    return sendRestResponse($response, $_SESSION['user']);
  }
  else {
    return sendErrorReponse($response, "Not Authenticated.", 401);
  }
});


/**
 * POST /api/login
 *
 * Login the current user. Set session to current user.
 *
 */
$app->post('/api/login', function($request, $response, $args) {

  $parsedBody = $request->getParsedBody();

  $pwd = '';
  $email = '';

  if (isset($parsedBody['pwd'])) $pwd = $parsedBody['pwd'];
  if (isset($parsedBody['email'])) $email = $parsedBody['email'];

  $dbh = DbHandler::getDbh();

  $user_stmt = $dbh->prepare("SELECT id, firstName, lastName, email, "
    . "createdAt FROM users WHERE email=? AND pwd=? LIMIT 1");

  $user_stmt->bind_param("ss", $email, $pwd);
  
  if (!$user_stmt->execute()) {
    unset($_SESSION['user']);
    return sendErrorReponse($response, $user_stmt->error);
  } 
  
  $users_result = $user_stmt->get_result();

  if ($users_result->num_rows <= 0) {
    unset($_SESSION['user']);
    return sendErrorReponse($response, "Login Failed.", 401);
  }

  $user = $users_result->fetch_assoc();

  $_SESSION['user'] = $user;

  return sendRestResponse($response, $user);
});



/**
 * GET /api/users
 *
 * Get all Users
 *
 */
$app->get('/api/users', function ($request, $response, $args) {

  $users = array();

  array_push($users, array(
      'id' => 1,
      'firstName' => 'mathias',
      'lastName' => 'blum',
      'email' => 'mathias.blum@student.tugraz.at',
      'createdAt' => '2017-05-01'
    )
  );

  array_push($users, array(
      'id' => 2,
      'firstName' => 'max',
      'lastName' => 'mustermann',
      'email' => 'max.mustermann@student.tugraz.at',
      'createdAt' => '2017-04-01'
    )
  );

  array_push($users, array(
      'id' => 3,
      'firstName' => 'bat',
      'lastName' => 'man',
      'email' => 'batman@student.tugraz.at',
      'createdAt' => '2017-04-10'
    )
  );

  return sendRestResponse($response, $users);
})->add($isLoggedIn);


/**
 * GET /api/users
 *
 * Get all Users
 *
 */
$app->get('/api/usersFromDb', function ($request, $response, $args) {

  $dbh = DbHandler::getDbh();
  $users_stmt = $dbh->prepare("SELECT id, firstName, lastName, email, "
    . "createdAt FROM users;");
  
  if (!$users_stmt->execute()) {
    return sendErrorReponse($response, $users_stmt->error);
  } 
  
  $users_result = $users_stmt->get_result();
  
  $users = array();

  while($row = $users_result->fetch_assoc()) {
    array_push($users, $row);
  }

  return sendRestResponse($response, $users);
})->add($isLoggedIn);


/**
 * GET /api/students
 *
 * Get all students
 *
 */
$app->get('/api/students', function ($request, $response, $args) {

  $dbh = DbHandler::getDbh();
  $stmt = $dbh->prepare("SELECT id, kennzahl, klasse, nachname, vorname, "
    . "geschlecht, geburtsdatum, performance60mRun, performance1000mRun, "
    . "performanceShotPut, performanceLongThrow, performanceLongJump, "
    . "sumPoints FROM students");
  
  if (!$stmt->execute()) {
    return sendErrorReponse($response, $stmt->error);
  } 
  
  $result = $stmt->get_result();
  
  $students = array();

  while($row = $result->fetch_assoc()) {
    array_push($students, $row);
  }

  return sendRestResponse($response, $students);
})->add($isLoggedIn);

/**
 * GET /api/studentsClasses
 *
 * Get all classes
 *
 */
$app->get('/api/studentsClasses', function ($request, $response, $args) {

  $dbh = DbHandler::getDbh();
  $stmt = $dbh->prepare("SELECT klasse "
    . "FROM students "
	. "WHERE 1 GROUP By klasse");
  
  if (!$stmt->execute()) {
    return sendErrorReponse($response, $stmt->error);
  } 
  
  $result = $stmt->get_result();
  
  $students = array();

  while($row = $result->fetch_assoc()) {
    array_push($students, $row);
  }

  return sendRestResponse($response, $students);
})->add($isLoggedIn);


/**
 * GET /api/student/{id}
 *
 * Get specific student by id
 *
 */
$app->get('/api/student/{id}', function ($request, $response, $args) {

  $dbh = DbHandler::getDbh();
  $stmt = $dbh->prepare("SELECT id, kennzahl, klasse, nachname, vorname, "
    . "geschlecht, geburtsdatum, performance60mRun, performance1000mRun, "
    . "performanceShotPut, performanceLongThrow, performanceLongJump, "
    . "sumPoints FROM students WHERE id=?");

  $stmt->bind_param("i", $args['id']);
  
  if (!$stmt->execute()) {
    return sendErrorReponse($response, $stmt->error);
  } 
  
  $result = $stmt->get_result();

  if ($result->num_rows <= 0) {
    return sendErrorReponse($response, "Student not found.", 404);
  }

  $student = $result->fetch_assoc();
  return sendRestResponse($response, $student);
})->add($isLoggedIn);

/**
 * GET /api/students/{class}
 *
 * Get specific students by class
 *
 */
$app->get('/api/students/{class}', function ($request, $response, $args) {

  $dbh = DbHandler::getDbh();
  $stmt = $dbh->prepare("SELECT id, kennzahl, klasse, nachname, vorname, "
    . "geschlecht, geburtsdatum, performance60mRun, performance1000mRun, "
    . "performanceShotPut, performanceLongThrow, performanceLongJump, "
    . "sumPoints FROM students WHERE klasse=?");

  $stmt->bind_param("s", $args['class']);
  
  if (!$stmt->execute()) {
    return sendErrorReponse($response, $stmt->error);
  } 
  
  $result = $stmt->get_result();
  
  $students = array();

  while($row = $result->fetch_assoc()) {
    array_push($students, $row);
  }

  return sendRestResponse($response, $students);
})->add($isLoggedIn);



/**
 * POST /api/student/{id}
 *
 * Save changed of specific student to DB.
 *
 */
$app->post('/api/student/{id}', function ($request, $response, $args) {

  $student = $request->getParsedBody();
 
  $dbh = DbHandler::getDbh();

  $stmt = $dbh->prepare("UPDATE students SET klasse = ?, nachname = ?, "
    . "vorname = ?, geschlecht = ?, geburtsdatum = ?, performance60mRun = ?, "
    . "performance1000mRun = ?, performanceShotPut = ?, "
    . "performanceLongThrow = ?, performanceLongJump = ?, sumPoints = ? "
    . "WHERE id = ?");
  
  $stmt->bind_param("sssssddddddi", 
      $student['klasse'], 
      $student['nachname'], 
      $student['vorname'],
      $student['geschlecht'],
      $student['geburtsdatum'],
      $student['performance60mRun'],
      $student['performance1000mRun'],
      $student['performanceShotPut'],
      $student['performanceLongThrow'],
      $student['performanceLongJump'],
      $student['sumPoints'],
      $args['id']
    );
  
  if (!$stmt->execute()) {
    return sendErrorReponse($response, $stmt->error);
  }

  return sendRestResponse($response);
})->add($isLoggedIn);

/**
 * POST /api/addStudent
 *
 * Save Student to db.
 *
 */
$app->post('/api/addStudent', function ($request, $response, $args) {

  $student = $request->getParsedBody();

  $dbh = DbHandler::getDbh();

  $stmt = $dbh->prepare("Insert Into students(klasse,nachname,vorname,"
    ."geschlecht,geburtsdatum,performance60mRun,performance1000mRun,"
	."performanceShotPut,performanceLongThrow,performanceLongJump,sumPoints)"
    . "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
  
  $stmt->bind_param("sssssdddddd", 
      $student['klasse'], 
      $student['nachname'], 
      $student['vorname'],
      $student['geschlecht'],
      $student['geburtsdatum'],
	    $student['performance60mRun'],
      $student['performance1000mRun'],
      $student['performanceShotPut'],
      $student['performanceLongThrow'],
      $student['performanceLongJump'],
      $student['sumPoints']
    );
  
  if (!$stmt->execute()) {
    return sendErrorReponse($response, $stmt->error);
  }

  $data = array();

  if ($dbh->affected_rows > 0) {
    $data['id'] = $dbh->insert_id;
  }

  return sendRestResponse($response, $data);
})->add($isLoggedIn);

?>
