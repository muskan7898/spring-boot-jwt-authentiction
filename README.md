# Authentication Project Tasks

## Working with cookies

### Task 1 - Create an api /increment-me

**Description** - 


`/increment-me` is a get http api and in the response it tells you how many number of times you have hit this particular api, hitting it for the first time should yield 1 ( not 0 ).

**How to implement** - 


When the user is hitting this api for the first time, set a cookie in the response that holds the count of the number of times this api is used, and return 1;

On Further request get the value of the cookie you have set, increment it, update the cookie and return the incremented value.


**Note** -

- Create a cookie service to handle this logic
- This api must be in a CookieController

### Task 2 - JSONIFY 
**Description -**

You have to create an api in a controller with the endpoint `/jsonify`, this is a post request and will accept any body, convert it in to json and return the json string as a response.

As we know not everything can be converted into json like

```
{ "name" : "snehal"
```
cannot be converted into json as we are missing a closing bracket, if this is the case then return a 400 status response.

---

# Jwt Related questions

## What is JWT

## How JWT works
