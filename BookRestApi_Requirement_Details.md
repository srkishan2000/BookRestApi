##Book Rest Api service exercise

###Description
Implement a Java web application with RESTful services that will allow the user to execute 
CRUD operations on the books in a repository.

###Requirements
* When run locally, the application's services are available at http://localhost:8080/api/v1/books.
* The user can run the test scripts from folder src/test/scripts and get expected results.
* The executed actions will update the database.
* Add security with HTTP basic authentication 
* Add a few unit tests (maybe utilizing mocking?)

###Details
The project is based on Maven, Java 11 and Spring Boot.
The Spring Boot app can be run with this command:

* mvn spring-boot:run


* HelloController - can be tested with script **_BookRestApi/src/test/
scripts/hello.sh_**
* 
* The test scripts can be found from folder **_BookRestApi/src/test/scripts_**:
* 
* **_listBooks.sh_** - Fetches all the books from the service
* **_getBookById.sh_** - Fetch a book (by id) 
* **_getBookByAuthor.sh_** - Fetches all the books (by Author name)
* **_addBook.sh_** - adds a book to the service (data in JSON format)
* **_updateBook.sh_** - update a book to the service (data in JSON format)
* **_deleteBook.sh_** - deletes a book from the service (by id)