
# Spring Security JWT project
	- JWT
	- Refresh token  


#### Main package
- com\sm\app\config\securtiy
- InitialUsersSetup.java : this class will insert the initial data 


## DB Tables
- users ---> below columns are important 	
	
	- email 
	- encrypted_password
	- user_id
	
- roles ---> these roles are being used 

	- ROLE_USER
	- ROLE_ADMIN

- authorities ---> these authorities are being used 

	- READ_AUTHORITY
	- WRITE_AUTHORITY
	- DELETE_AUTHORITY

- users_roles ---> below columns are important
 	
	- users_id 
	- roles_id

- Postman URL

Http METHOD : GET

1)localhost:8080/app-ws/users/login

body : 

{
"email":"abc@hotmail.com",
"password":"12345678"
}

Check Response Header:
- Bearer token - refresh-token

---------------------------------------------------

2) localhost:8080/app-ws/users/put-your-userid

replace your user id (put-your-userid) in the url

Put Authorization header with Bearer token in header value like

Authorization Bearer adfasdhfsadifnsidoafwbeoui321u4u08fsf2h3iu4902309hn

Call the webservice

---------------------------------------------------

3) localhost:8080/app-ws/users/refresh-token 
   
Note : you will get the refresh token from then login api, check the header


   response body 
   
{
   "refreshToken":"464563c0-628a-4517-bb5f-9c2c50cabfaa"
}

Call the webservice


