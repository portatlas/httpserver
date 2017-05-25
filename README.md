# HTTP Server
This is a project to build an HTTP server in Java

## Dependencies
1. Java
2. Maven
3. JUnit

## Compile and Build Package
At the root dir run the command:
```$ mvn package```

## Run the Server
At the target dir run the command:
```java -jar httpserver-1.0.jar -p 5000 -d /path/to/somewhere```

Where -p is the port
and -d is the resource directory

Currently, you should only see the port and directory displayed to the console.
