#  Test Cases

## Test 1: Create Account
- Launch the application
- Select create button
- Select the radio button of the right user type you want to create
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select create

Expected result: Application verifies the user's username and password and loads their homepage automatically.

Test Status: Passed.

## Test 2: Log in
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select Login
  
Expected result: Application verifies the user's username and password and logs in if account already exists.

Test Status: Passed.

## Test 3: Log Out
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select Login
- Select Log Out
- Select 'Yes' to Confirm

Expected result: User is logged out and returned to main log in and create account window

Test Status: Passed.

## Test 4: Delete Account
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select 'Delete Account'
- Select 'Yes' to confirm

Expected result: User is logged out and returned to log in and create account window

Test Status: Passed.

##Test 5: Refresh Window
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select Login
- Select 'Refresh'

Expected result: Any changes made by other users is reflected in current users' page

Test Status: Passed

## Test 6: Create Course
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select create button
- Choose input method (from a file on the computer or input through window)
- Enter course title
- Select "OK" button to confirm

Expected result: Application refreshes the window and new course is visible

Test Status: Passed

## Test 7: Rename Course
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select "Rename" next to course title
- Choose input method (from a file on the computer or input through window)
- Enter new course title
- Select 'OK' button to confirm

Expected result: Application refreshes the window and the course title reflects the changes made

Test Status: Passes

## Test 8: Delete Course
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select "Delete" next to course title
- Select 'Yes' button to confirm

Expected result: Application refreshes the window and the course is deleted

Test Status: Passed

##Test 9: Create Forum
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select 'Create' in course page
- Choose input method (from a file on the computer or input through window)
- Enter forum topic and select 'ok' to confirm

Expected result: Application refreshes the window and new forum is created

Test Status: Passed

##Test 10: Rename Forum
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select "Rename" next to forum title
- Choose input method (from a file on the computer or input through window)
- Enter new forum title
- Select 'OK' button to confirm

Expected result: Application refreshes the window and the forum title reflects the changes made

Test Status: Passed

##Test 11: Delete Forum
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select "Delete" next to forum title that needs to be deleted
- Select 'Yes' to confirm
Expected result: Application refreshes the window and the forum's removed

Test Status: Passed

##Test 12: Create Post
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select the forum
- Select Create
- Choose input method (from a file on the computer or input through window)
- Enter Post title
- Select 'Ok' to confirm 

Expected result: Application refreshes the window and new post is visible 

Test Status: Passed


##Test 13: Edit Post
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select the forum
- Select 'Edit' under required post
- Choose input method (from a file on the computer or input through window)
- Select 'Ok' to confirm

Expected result: Application refreshes the window and post reflects the changes made

Test Status: Passed

##Test 14: Delete a Post
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select the forum
- Select 'Delete' under required post

Expected result: Application refreshes the window and post is removed

Test Status: Passed


##Test 15: Reply to a Post
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select the forum
- Select 'Add a reply'
- Choose input method (from a file on the computer or input through window)
- Enter Reply
- Select 'OK' to confirm

Expected result: Application refreshes the window and reply is visible

Test Status: Passed

##Test 16: Vote for a Post
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select the forum
- Select 'Vote for this post' under relevant post

Expected result: The number of votes for the post is incremented by 1

Test Status: Passed

##Test 17: Sort Posts by time
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select the forum
- Select the 'time' radio button next to 'Display sort by'

Expected result: The order of posts is changed with the latest post appearing on top

Test Status: Passed

##Test 18: Sort Posts by votes
- Launch the application
- Select username text box
- Enter username
- Select password text box
- Enter password
- Select log in
- Select "OK" button on the login successful confirmation window
- Select the course
- Select the forum
- Select the 'votes' radio button next to 'Display sort by'

Expected result: The order of posts is changed with most voted post appearing on top

Test Status: Passed
