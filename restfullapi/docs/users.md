# User API Spec

## Register User

Endpoint : POST /api/users

Request Body :

```json
{
    "username":"ahmad",
    "password":"rahasia",
    "name":"Ahmad Zulfadli"
}
```
Response Body (Success) :

```json
{
    "data":"Success"
}
```
Response Body (Failed) :

```json
    "errors":"Username must not blank, ??"
```

## Login User

Endpoint : POST /api/auth/login

Request Body (Success) :

```json
{
    "username":"ahmad",
    "password":"rahasia"
}
```

Response Body (Succes) :

```json
{
    "data":{
        "token":"TOKEN",
        "expiredAt":21212132121 //milliseconds
    }
}
```
Response (Failed,401) :

```json
{
    "errors":"username and password wrong"
}
```

## Get User

Endpoint : GET /api/users/current

Request Header:

X-APITOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
    "data":{
        "username":"ahmad",
        "name":"Ahmad Zulfadli"
    }
}
```
Response Body (Failed) :

```json
{
    "errors":"Unauthorized"
}
```

## Update User 

Endpoint : PATCH /api/users/current

Request Header:

X-APITOKEN : Token (Mandatory)

Request Body :

```json
{
    "data":{
        "name":"Ahmad Zulfadli",
        "password":"rahasia"
    }
}
```

Response Body (Success) :

```json
{
    "data":{
        "username":"ahmad",
        "name":"Ahmad Zulfadli",
    }
}
```

Response Body (Failed, 401) :

```json
{
    "errors":"Unauthorized"
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header:

X-APITOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
    "data":"Succes"
}
```