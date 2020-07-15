
# Spring Security JWT project


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

You will find Bearer token in header, 


---------------------------------------------------
2) localhost:8080/app-ws/users/putyouruserid

replace your user id (putyouruserid) in the url 

Put Authorization header with Bearer token in header value like

Authorization Bearer adfasdhfsadifnsidoafwbeoui321u4u08fsf2h3iu4902309hn

Call the webservice