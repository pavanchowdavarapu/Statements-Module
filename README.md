# Statements-Module

<table>
<tr>
<td>
This module mainly used for fetching customer transactions details  based on Account number,FromDate,ToDate,FromAmount,ToAmount.
For this module only two users are configured. ADMIN can fetch transaction using all filters and USER login can fetch only last three months statements
</td>
</tr>
</table>


## Demo
Here is a working demo using postman

First we have generate JWT token using below url for user ADMIN

Everey jwt token session is for five minutes.

## Payload

{
    "username":"admin",
    "password":"admin"
}

http://localhost:8080/authenticate

![Screenshot (10)](https://user-images.githubusercontent.com/30865872/185793167-c0b0ef4e-ee5a-4b66-a652-6a3144708974.png)

Now generated jwt token need to set authorization key starting with Bearer space jwt token

## payload
{
    "accNumber":"0012250016005",
    "fromDate":"2020-10-01",
    "toDate":"2021-01-09",
    "fromAmount":10,
    "toAmount":100000
}
http://localhost:8080/Statement

In the response account Number is hashed to end user

![Screenshot (11)](https://user-images.githubusercontent.com/30865872/185793403-0ae4195d-44a8-4548-b5e8-2a3a48edfdf6.png)


![Screenshot (12)](https://user-images.githubusercontent.com/30865872/185793545-65a983bb-787e-43c7-aa19-876b13335af1.png)


### How to Install and Run the Project

This application is developed using spring boot and java 8

Just clone the branch and java 8 to be installed 

And run the main method. service urls and payloads specified in above section
