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
