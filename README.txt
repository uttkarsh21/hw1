==For thisIsServer==
run this script via command line and make sure to add the port number to run the server on. eg. - "java .\thisIsServer.java 1234"

==For thisIsClient==
run this script via command line and make sure to add the port number && hostname to run the server on. eg. - "java .\thisIsClient.java 1234 localhost"

Make sure the port number is the same as that of the server. Once connection is made, enter the string you want the server to reverse.

==SingleThread==
This is basically the same program from HW1

==MultiThread==
Made a clientHandler thread so that multiple clients can run on the server at the same time. 
The server as well as the multiple clients run endlessly. Give each client a unique name when prompted after you run an instance of it.