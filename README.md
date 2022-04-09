# CS180_Project 4_Learning_Management_System_059

## Instructions to compile and run the project:

## Submissions

### Class Descriptions

#### User.java:
An abstract class that holds a static ArrayList of DiscussionForum instances.
It has two subclass, Student.java and Teacher.java
A static ArrayList of forum is stored in User class, which can be access be multiple users.

#### Teacher.java extends User.class
A class that represents a teacher
#### Student.java extends User.class
A class that represents a student
#### DiscussionForum.java
A class that represents a discussion forum
#### DiscussionPost.java:
A class that represents posts in a discussion forum

### Program Decription
In this thread, the user is asked to do the following things in linear order
1. Read from files to update users information, forum information (including the posts in forums)
2. Ask if they to create an account or log in 
   1. If they want to create the account, then 
      1. let them enter a username 
         1. if the username is taken, handle exception and repeat [a.]
         2. if the username is available, proceed program 
      2. let the user enter the passcode
      3. assign the user to UserActivities.currentUser
   2. If the user want to log in 
      1. let them enter username and password
      2. check if match with Users info
         1. match, assign the user to current user, proceed the program
         2. not match, handle exception, return to beginning of 2.
   3. Proceed to 3. 
3. select from menu
   1. Create forum
      1. Add a DiscussionForum instance in User.forums, then repeat 3 
      ###### P.S. Handle the exception if the user does not have permission
   2. Edit forum 
      Edit the given forum's topic with a user input, which is the topic of the forum, then repeat 3
      ######P.S. Handle the exception if the user does not have permission
   3. Delete forum 
      1. Delete the given forum, then repeat 3
      ######P.S. Handle the exception if the user does not have permission
      ######P.S. Handle the exception if the given forum does not exist
   4. Choose a forum 
      1. proceed to 4
   5. Log out 
      1. proceed to 8
4. select forum
   a. e.g. forum one (topic)
   proceed to 5
   b. e.g. forum two
   proceed to 5
   c. e.g. forum three
   proceed to 5
   d. back to last menu
   back to 3
5. select action (Display the topic of the forum selected)
   a. add post
   add the post, and repeat 5
   b. reply post
   proceed to 6
   c. back to last menu
   back to 4
   d. log out
6. select action
   a. e.g. post one (the first 30 character...)
   proceed to 7
   b. e.g. post two
   same with a.
   c. e.g. post three
   same with a.
   d. back to last menu
   back to 3
7. select action
   a. import from file
   let the user enter a file name, add the reply and back to 5
   b. enter reply
   let the user enter a String type reply, and add the reply, then back to 5
8. Log out
   a. save the user info again
   b. save the forum (and the post in it)
   c. End the program



The student class takes care of the functions students can perform in the discussion board.

#### UserInfo.txt
The text file that includes all the user data
In the format showing below:
Username
Password
User Type (T for Teachaer, S for Student)
