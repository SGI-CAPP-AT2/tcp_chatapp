# Chat Application with Tcp
![Preview of Application](https://github.com/user-attachments/assets/837fa51b-b630-4ca5-819e-4a0cd87fdd5c)


This Project aims to develop a TCP based chatapp using TCP and Swing in Java

> The Project is developed during Semester 5 (Winter 2023) of Diploma in Computer Engineering but build script and git initialization done on 25/12/2024

## Architecture 
<p align="center"><img src="https://github.com/user-attachments/assets/a031323b-d9ae-4099-84d7-03c6ceddf557" /></p>

This architecture demonstrates how each thread at server will receive message from client and will send messages to client.


## Dependencies
This project requires following dependencies in order to run project on your system.

- Java 1.2

## Build and Run
To Build or Run this project you can follow these steps:

### Common Scripts for Windows and Linux

You can build project for windows by using commands:
```shell
cd windows # or `cd linux` for linux
./compile
```
this will create folder `windows/build` where all compiled classes are

After Compilation to Run Server use following command

```shell
./runServer MYSQL_HOSTNAME_OR_IP_ADDRESS MYSQL_UNAME MYSQL_PWD 61109 # or port you want
```

After running server successfully try instantiating a client

```shell
./newClient 61109 # the port where server is listening
```
Now You'll see a Window for chat app
You can create as many clients you want

### Extra Script for Linux

Instead of dealing with those multiple scripts you can use play script which is interactive script to create as many clients as you want.

```shell
cd linux
./play MYSQL_HOSTNAME_OR_IP_ADDRESS MYSQL_UNAME MYSQL_PWD 61109 # or the port you want
```
This will print output something like
```
Server running on port 61109
Enter input (type 'q' to quit or 'n' instantiate a client): n
Enter input (type 'q' to quit or 'n' instantiate a client):  
No input provided. Try again.
Enter input (type 'q' to quit or 'n' instantiate a client): q
Exiting the program.
```
It'll run server for you and you can instantiate client by using `n` as input.
