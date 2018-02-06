# REST-API-Online-Food-Ordering

This is a RESTful API simulating online food ordering service, which is built on Java/Maven/Spring framework. In this application, I designed three models, Restaurant, Menu and Item. Each model has been implemented with four HTTP methods, which are GET/POST/PUT/DELETE. 

MySQL is used for data storage, together with Hibernate/JPA framework for constructing mappings between models and database. Moreover, add an additional cache layer in order to facilitate the running speed, especially considering the situation when the same object has been accessed multiple times under a certain time period.

There are some other techniques that integrated into this project, such as Actuator, which provides some built-in services to make this application more production-ready; and JUnit for unit tests.

## Prerequisites

To run this application, you need at least:

  Java JDK8
  
  Maven 3.5.2 (You can download and install Maven by referring to https://maven.apache.org/download.cgi)
  
  MySQL 5.7

## Running Instructions

- Clone this repository;
- Make sure the configure file application.properties has the same configuration as that of your computer (Especially MySQL configuration).
- Create a new database called "onlineordering" in MySQL.
- At the root directory of this application, open the command prompt and enter "mvn clean package". Wait until this applicaton has been successfully built.
- Enter "mvn spring-boot:run" to let this application run.
- Launch POSTMAN in Chrome as the client. (You can add the POSTMAN extension here: https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en). 

Now it is time to have fun with this REST API.

A list of requests (method+URL):

GET method is used for searching operations:
- GET http://localhost:8080/restaurant/all           // Return all restaurants in the database
- GET http://localhost:8080/restaurant/search/{id}   // Return the restaurant with restaurantId={id}
- GET http://localhost:8080/menu/all                 // Return all menus in the database
- GET http://localhost:8080/menu/search/{id}         // Return the specific menu with menuId={id}
- GET http://localhost:8080/item/all                 // Return all dishes in the database
- GET http://localhost:8080/item/search/{id}         // Return the specific dish with itemId={id}

POST method is used for inserting operations:
- POST http://localhost:8080/restaurant/register, requestbody: {"name":"McDonald", "address":"55th Street"}    // Only providing name and address, add this resaurant's information into the database.
- POST http://localhost:8080/menu/add/{id}, requestbody: {"name": "Drink"}    // Add a new menu to the restaurant whose restaurantId equals to {id}, and provide the name of this menu in the request body.
- POST http://localhost:8080/item/add/{id}, requestbody: {"name":"Coffee", "price":3.79}    // Add a new dish to the menu which has the menuId set to be {id}, and provide the name and price of this new dish.

PUT method is used for undating operations:
- PUT http://localhost:8080/restaurant/update/{id}, requestbody: {"name": "KFC", "address": "7th Avenue"}    // Re-set the name and address information of the restaurant whose restaurantId={id}.
- PUT http://localhost:8080/menu/update/{id}, requestbody: {"name": "Hot Drink"}    // Only renew the name of the menu whose menuId={id}, while not change other information.   
- PUT http://localhost:8080/item/update/{id}, requestbody: {"name": "Hot Chocolate", "price":"5.50"}  {}  // Update the dish whose itemId={id}, applying the new information of name and price offered in the request body.
Notice: For both POST and PUT methods, please make sure that the content-type of your request body is set to be raw -> "JSON(applicaton/json)".

DELETE method is used for deleting operations:
- DELETE http://localhost:8080/restaurant/delete/{id}    // Remove the restaurant from database whose restaurantId={id}
- DELETE http://localhost:8080/menu/delete/{id}          // Remove the menu from database whose menuId={id}
- DELETE http://localhost:8080/restaurant/delete/{id}    // Remove the dish from database whose itemId={id}

## Unit Testing

To run the unit tests with JUnit, simply import this project into your favorite IDE and run JUnit Suite file AllTests.java (\onlineOrdering\src\test\java\com\webService\onlineOrdering\tests).
