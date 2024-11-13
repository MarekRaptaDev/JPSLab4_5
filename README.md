# JPSLab4_5
My homework from collage 
# Tasks (Client)
Write a program that finds the IP address of a given host address.

Write a client that connects to any website and displays its content.

Write a client that connects to the API (https://jsonplaceholder.typicode.com/) and displays the available posts (GET https://jsonplaceholder.typicode.com/posts).

Write a client that adds a new POST { "title": "foo", "body": "bar" } and displays the server's response.

Write a client that displays current weather information for your city (or air pollution levels).

Write a client for downloading files. The user should be able to input a URL to the file to download. Save the files in the default location.

🏠 Home Project: Write a client that, based on address information, creates a map. Save the address and map in an HTML file. Try using the available OpenStreetMap API (alternatively, you can use coordinates instead of the address).

# Tasks (Server)
Write a simple server that sends the current date to the connected client.

Write an echo server – a message sent from the client should return to the client in reverse order. For example: "hello" -> "olleh".

Write a server that allows multiple clients to connect. The client should be greeted 10 times (with 1-second intervals), and then the connection should be terminated.

Write a chat server. A message sent by one user should be delivered to the other connected users.

🏠 Home Project: Write a micro web server.

It should accept browser connections on port 8080.

It should handle the GET method.

The files should be served from the www/ directory.

# Explanation for files
- All of client tasks are in ClientConnect.java file,
- Main file contains function calls to test functions for tasks (without last server task),
- ClientNThreads are threads for clients in servers asign with 'N' number,
- Servers 1-4 contains code of servers from tasks 1-4,
- serverWWW contains code for last server task.
