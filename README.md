# HTTP Server
This project is an implementation of a basic HTTP Server built with Java. 

The HTTP Server supports the following:
1. HTTP Methods: simple GET, POST, PUT, PATCH, DELETE, HEAD, and OPTIONS.
2. Decoding Parameters
3. Basic Authentication
4. Basic Cookie Implementation (Setting a cookie on the client side)
5. The server can server txt, jpgeg, png, and gif files.
6. Partial Content Request


## Getting Started
Clone this repository to your local machine
<br>
```$ git clone https://github.com/portatlas/httpserver.git```
<br>
```$ cd httpserver```

## Dependencies
1. Java 1.8
2. JUnit 4.12
3. Maven

## Run Tests
Run the following command to run the unit tests: <br>
```$ mvn test```

## Run Acceptance Test
The acceptance test and instructions to run them can be found [here](https://github.com/8thlight/cob_spec]) 

## Compile and Build Package
At the root dir run the command:<br>
```$ mvn package```

## Run the Server
At the root directory run the command:<br>
```java -jar target/httpserver-1.0.jar -p 5000```

Where:<br>
-p is the port<br>
-d is the resource directory (if a directory argument is not supplied the server will default to the public directory in the repo)

You should see the port and directory displayed to the console.

Go to [http://localhost:5000](http://localhost:5000) and you should see the contents of the public directory.
