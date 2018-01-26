# Elsevier Search API
Please read the Exercise.docx for more information.

# About
- Search API takes the words as input for a HTTP GET request and searches the files for a given path , it searches the sub folders of the given path tree.
- Search API support different cases
- current version only reads files from .txt and .log to support more update the applicaiton properties file
- Spring boot actualtor /health endpoint gives the status of the service (i.e http://localhost:8080/health)

# How to build
``mvn clean install``
will build the project 

# How to run the program
## Running the jar file
``java -jar target/gs-rest-service-0.1.0.jar``
will run the program with default path as it is part of the class path

in case to specifiy differnt path then run the program with

``java -jar target/gs-rest-service-0.1.0.jar --search-file-path=/Users/satishgummadelli/elsevier/code-test``

## Runninng from Java IDEs (Eclipse or IntelliJ)
Import the project and run the com.elsevier.services.search.ElsevierSearchApiApplication as java program.


# Sample CURL request
``curl --request GET \
  --url 'http://localhost:8080/elsevier/api/test/search?words=ishi' \
  --header 'cache-control: no-cache' \
  --header 'postman-token: 1984955c-a6d3-2896-2814-d9a7418f6a4d'``
  
  
``curl --request GET \
  --url 'http://localhost:8080/elsevier/api/test/search?words=The%2Cis' \
  --header 'cache-control: no-cache' \
  --header 'postman-token: 48fea397-00fa-8b28-4e48-70c1b7da35d5``
