# CS180_Project 5_Learning_Management_System_059

## Instructions to compile and run the project:


To let this application function,
* run the main() in SystemServer first
* run the main() in Client
* run again the main() in Client to launch different client
* The home login menu will appear for each client connect to the server, users can use this application based on the prompts in the windows

######&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if you are using two or more computers, please remember to check the hostname and port in SystemServer.java and Client.java

## Submissions

Project 4 Report - Kundana Nittala

Project 4 Code - Jia Xie

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

#### Packet
A class that represents a packet. 
 * ************************************
* requestType: an integer that tells the server which kind of request is this.
* msg: a String array that helps to communicate
* posts: a List of String that helps the client to display the posts
* replies: a List of String that helps the client to display the replies
* isOperationSuccess: a boolean that illustrates if the action succeed
 * ************************************

#### DataManager
A class that helps to read and store the user information in a format of
 * ************************************
* username
* password
* User Type(T for util.Teacher, S for util.Student)
 * ************************************
#### Vote  
This class represents a vote.

#### User
An abstract class that holds a static ArrayList of util.DiscussionForum instances.
It has two subclass, Student and Teacher
#### Teacher extends User
A class that represents a teacher

#### Student extends User
A class that represents a student and takes care of the functions students can perform in the discussion board. Specifically, students are able to view the courses on their dashboard, create their own replies to discussions, and upvote other students' posts.

#### DiscussionForum
A class that represents a discussion forum

#### DiscussionPost
A class that represents posts in a discussion forum. This class includes the functionality for teachers and students through their ability to edit, create, and reply to posts.

#### Course
A class that represents a course. A course is identified by its title. There are forums in a course

### client Class Descriptions

#### AccountLoginUI

A class that includes the GUI for the Login to account page. Here students and teachers will be able to enter their
existing username and password to access their course dashboard. They can also delete their account in this window.

#### AccountCreateUI

A class that includes the GUI for users to create their account. They can select the account type and create the
account using their own token
#### StudentCourseUI

A class that includes the GUI for the Course dashboard. 
Here students will be able to view courses and have option enter a course.

#### TeacherCourseUI

A class that includes the GUI for the Course dashboard.
Here teachers will be able to view courses and have option to create, edit, or delete a course.

#### StudentForumUI

A class that included the GUI for Forum dashboard for students. 
Student can select forums that they want to enter, in this window.

#### TeacherForumUI

A class that included the GUI for Forum dashboard for teachers.
Student can select forums that they want to enter and have option to create, edit, delete
forums, in this window.

#### StudentPostUI
A class that included the GUI for discussion board. Student can create post and reply to others' posts. 
They can also vote for posts they like

#### TeacherPostUI
A class that included the GUI for discussion board. Teachers can create post and reply to others' posts.
They can also choose the display method as either sorting by vote numbers or post date


#### Client.java

A class that initialized the client using sockets and helps to communicate with the server.

### Server Classes Description

#### ClientThread
A class that represents a thread responding to each client.

#### PacketHandle

A class that process the packet receiving from the client and return a response packet to the client

#### SystemServer
A class thar initialized the server and creating the thread to communicate with different clients.

## Project 4 Bug and Implementation Improvements
In order to improve the score we received for Project 4, we have implemented the following changes:

- Allow teachers to create and edit posts.

- Denied request to create courses and forums with the same name existing


