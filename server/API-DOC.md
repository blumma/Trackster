# School Grading System REST API



## General Request

### HEADER:

POST /
Content-Type: application/json

### PAYLOAD:

```javascript
{
  "action": "function_name",
  "data": {
    "param1": "request_param1",
    "array1": [
      "request_array_elem1",
      "request_array_elem2",
      "request_array_elem3"
    ],
    "object1": {
      "request_object_elem1",
      "request_object_elem2",
      "request_object_elem3"
    }
  }
}
```

Supported functions:

'login',
'register',
'get_student_data',
...



## General Resonse

### HEADER

200 OK
Content-Type: application/json

### BODY

```javascript
{
  "data": {
    "param1": "response_param1",
    "array1": [
      "response_array_elem1",
      "response_array_elem2",
      "response_array_elem3"
    ],
    "object1": {
      "response_object_elem1",
      "response_object_elem2",
      "response_object_elem3"
    }
  }
}
```


## Error Resonse

### HEADER:

500 Internal Server Error
Content-Type: application/json

### BODY

```javascript
{
  "message": "An error Occurred"
}
```


## Not Authorized Resonse

### HEADER:

401 Unauthorized
Content-Type: application/json

### BODY

```javascript
{
  "message": "Not Authorized"
}
```


## File / Function not found

### HEADER:

404 Not Found
Content-Type: application/json

### BODY

```javascript
{
  "message": "No method found"
}
```