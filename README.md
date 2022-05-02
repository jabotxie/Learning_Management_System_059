# CS180_Project 5_Learning_Management_System_059

## Instructions to compile and run the project:


To let this application function,
* Run the main() in SystemServer first
* Run the main() in Client
* Run again the main() in Client to launch different client
* The home login menu will appear for each client connect to the server, users can use this application based on the prompts in the windows

###If you are using two or more computers, please remember to check the hostname and port in SystemServer.java and Client.java

## Submissions

Project 5 Report - Kundana Nittala

Project 5 Presentation - 

Project 5 Code - Jia Xie

### Project Description
Our choice is option one, to implement a learning management system discussion board. The Discussion Board gives access 
to teachers to create discussion forums and allows students to post replies. This discussion board works similar to the 
discussion boards on brightspace. Teachers are allowed to add, edit and delete courses while students can access the 
discussion posts and forums within these courses. Our implementation also allows students to vote for the best 
discussion posts within the forum.

### File Storage

#### UserInfo.txt
The text file that includes all the user data
In the format showing below:
Username
Password
util.User Type (T for util.Teacher, S for util.Student)

#### CoursesInfo.txt
The text file that included all the courses, forums, posts, replies and votes.
The application is storing the data through ObjectOutputStream, and the application is importing by ObjectInputStream

### util Class Descriptions

#### Packet.java
A class that represents a packet. 
 * ************************************
* requestType: an integer that tells the server which kind of request is this.
* msg: a String array that helps to communicate
* posts: a List of String that helps the client to display the posts
* replies: a List of String that helps the client to display the replies
* isOperationSuccess: a boolean that illustrates if the action succeed
 * ************************************

#### DataManager.java
A class that helps to read and store the user information in a format of
 * ************************************
* username
* password
* User Type(T for util.Teacher, S for util.Student)
 * ************************************
#### Vote.java 
This class represents a vote.

#### User.java
An abstract class that holds a static ArrayList of util.DiscussionForum instances.
It has two subclass, Student and Teacher
#### Teacher.java extends User.java
A class that represents a teacher

#### Student.java extends User.java
A class that represents a student and takes care of the functions students can perform in the discussion board. Specifically, students are able to view the courses on their dashboard, create their own replies to discussions, and upvote other students' posts.

#### DiscussionForum.java
A class that represents a discussion forum

#### DiscussionPost.java
A class that represents posts in a discussion forum. This class includes the functionality for teachers and students through their ability to edit, create, and reply to posts.

#### Course.java
A class that represents a course. A course is identified by its title. There are forums in a course

### Client Class Descriptions

#### AccountLoginUI.java

A class that includes the GUI for the Login to account page. Here students and teachers will be able to enter their
existing username and password to access their course dashboard. They can also delete their account in this window.

#### AccountCreateUI.java

A class that includes the GUI for users to create their account. They can select the account type and create the
account using their own token
#### StudentCourseUI.java

A class that includes the GUI for the Course dashboard. 
Here students will be able to view courses and have option enter a course.

#### TeacherCourseUI.java

A class that includes the GUI for the Course dashboard.
Here teachers will be able to view courses and have option to create, edit, or delete a course.

#### StudentForumUI.java

A class that included the GUI for Forum dashboard for students. 
Student can select forums that they want to enter, in this window.

#### TeacherForumUI.java

A class that included the GUI for Forum dashboard for teachers.
Student can select forums that they want to enter and have option to create, edit, delete
forums, in this window.

#### StudentPostUI.java
A class that included the GUI for discussion board. Student can create post and reply to others' posts. 
They can also vote for posts they like

#### TeacherPostUI.java
A class that included the GUI for discussion board. Teachers can create post and reply to others' posts.
They can also choose the display method as either sorting by vote numbers or post date

#### Client.java

A class that initialized the client using sockets and helps to communicate with the server.

### Server Classes Description

#### ClientThread.java
A class that represents a thread responding to each client.

#### PacketHandle.java

A class that process the packet receiving from the client and return a response packet to the client

#### SystemServer.java
A class thar initialized the server and creating the thread to communicate with different clients.

## Project 4 Bug and Implementation Improvements
In order to improve the score we received for Project 4, we have implemented the following changes:

- Allow teachers to create and edit posts

- Denied request to create courses and forums with the same name existing


