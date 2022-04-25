# CS180_Project 4_Learning_Management_System_059

## Instructions to compile and run the project:

In order to compile the project

Our application start at the main() function in UserClient class, so first of all run main()
If it’s the first time running this application, namely, there is no data stored in a local file, the application will initialize the instances required for the application. If there are local files storing the data, the program will extract data from the local text file and import it into static instances and store them in util.DataManager class (A class we created to store and deal with all the data under to earth)
The menu application begins from now on. By any time during the application, users can choose to quit (or log out if they already logged in). We accomplished this by implementing a 4-level loop in the program.


## Submissions

Project 4 Report - Kundana Nittala

Project 4 Code - Jia Xie

### Project Description
Our choice is option one, to implement a learning management system discussion board. The Discussion Board gives access to teachers to create discussion forums and allows students to post replies. This discussion board works similar to the discussion boards on brightspace. Teachers are allowed to add, edit and delete courses while students can access the discussion posts and forums within these courses. Our implementation also allows students to vote for the best discussion posts within the forum.

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


### Class Descriptions

#### util.User.java:
An abstract class that holds a static ArrayList of util.DiscussionForum instances.
It has two subclass, util.Student.java and util.Teacher.java
A static ArrayList of forum is stored in util.User class, which can be access be multiple users.
#### util.Teacher.java extends util.User.class
A class that represents a teacher

#### util.Student.java extends util.User.class
A class that represents a student and takes care of the functions students can perform in the discussion board. Specifically, students are able to view the courses on their dashboard, create their own replies to discussions, and upvote other students' posts.

#### util.DiscussionForum.java
A class that represents a discussion forum

#### util.DiscussionPost.java:
A class that represents posts in a discussion forum. This class includes the functionality for teachers and students through their ability to edit, create, and reply to posts.

#### userActivites.java
This is the major class that holds all the operations and menus

#### util.Course.java
A class that represents a course. A course is identified by its title. There are forums in a course

#### CourseUI.java

A class that includes the GUI for the Course dashboard GUI. Here students and teachers will be able to view courses and forums they are in and have an option to create, edit, or delete a course or forum (depending on their status as either a Student or a Teacher).

#### CreateUI.java

A class that includes the GUI for the Create account page. Here students and teachers will be able create a new username and password to create a new accout and access their course dashboard.

#### LoginUI.java

A class that includes the GUI for the Login to account page. Here students and teachers will be able to enter their existing username and password to access their course dashboard.

#### Courselist.java
This class helps to store the data to local file

#### Client.java

#### DataServer.java
A class that helps to read and store the user information in a format of
 * ************************************
 * username
 * password
 * util.User Type(T for util.Teacher, S for util.Student)
 * ************************************

#### util.Vote.java
This class represents a vote.

### Exceptions Descriptions

#### AcountInfoNotMatchException.java
Exception is thrown when the username entered doesn't exist or the password is incorrect.

#### util.AlreadyVotedException.java
Exception is thrown when a student has already voted and is attempting to vote again

#### util.NoPermissionException.java
Exception is thrown when a user of a type does not have permission to do the intended operation. For example, a user of type teacher cannot vote and a user of type student cannot create or edit courses. This exception is thrown when an attempt is made.

#### NoSuchTargetException.java
Ecxception is thrown when a specified course, forum, post or reply does not exist.

#### util.TeacherCannotVoteException.java
Exception is thrown when a teacher user attemps to vote.

#### UsernameAlreadyTakenException.java
Exception is thrown when a user enters a username that is already on file while creating an account.

## Project 4 Bug and Implementation Improvements
In order to improve the score we recieved for Project 4, we have implemented the following changes:

- Allow teachers to create and edit posts.

- Changes to implementation and creation of courses and forums.


