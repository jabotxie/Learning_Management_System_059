package data;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;


import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version April 11, 2022
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Spring 2022</p>
     *
     * @author Purdue CS
     * @version April 11, 2022
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }


        @Test(timeout = 1000)
        public void testExpectedOutputOne() {

            // Set the input
            String input = commandGenerator("2", "1", "teacher1", "teacher1", "5");

            // Pair the input with the expected result
            String expected =
                    "Welcome to the Learning Management System\n" +
                            "_____________________________________\n" +
                            "Selection an option\n" +
                            "1. Log in\n" +
                            "2. Create an account\n" +
                            "3. Delete an account\n" +
                            "4. Quit the system" +
                            System.lineSeparator() +
                            "_____________________________________\n" +
                            "What type of account would you like to create?\n" +
                            "1. Teacher\n" +
                            "2. Student" +
                            System.lineSeparator() +
                            "_____________________________________\n" +
                            "Please enter the username: " +
                            System.lineSeparator() +
                            "Please enter the password: " +
                            System.lineSeparator() +
                            "Successfully logged in!\n" +
                            "_____________________________________\n" +
                            "Please choose an operation\n" +
                            "    1. Create a course\n" +
                            "    2. Delete a course\n" +
                            "    3. Edit a course\n" +
                            "    4. Enter a course\n" +
                            "    5. Log out" +
                            System.lineSeparator() +
                            "Thanks for using Learning Management System!" +
                            System.lineSeparator();

            // Runs the program with the input values
            receiveInput(input);
            UserClient.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

//            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected format",
                    expected, output);
        }

        @Test(timeout = 1000)
        public void testExpectedOutputTwo() {

            // Set the input
            String input = commandGenerator("1", "student\n", "student\n", "5");

            // Pair the input with the expected result
            String expected = "Welcome to the Learning Management System\n" +
                    "_____________________________________\n" +
                    "Selection an option\n" +
                    "1. Log in\n" +
                    "2. Create an account\n" +
                    "3. Delete an account\n" +
                    "4. Quit the system" +
                    System.lineSeparator() +
                    "_____________________________________\n" +
                    "Please enter the username: " + System.lineSeparator() +
                    "Please enter the password: " + System.lineSeparator() +
                    "Successfully logged in!" + System.lineSeparator() +
                    "_____________________________________\n" +
                    "Please choose an operation\n" +
                    "    1. Create a course\n" +
                    "    2. Delete a course\n" +
                    "    3. Edit a course\n" +
                    "    4. Enter a course\n" +
                    "    5. Log out\n" + System.lineSeparator() +
                    "Thanks for using Learning Management System!";
        }

        @Test(timeout = 1000)
        public void testExpectedOutputThree() {

            // Set the input
            String input = commandGenerator("3", "student\n", "student\n", "1", "4");

            // Pair the input with the expected result
            String expected = "Welcome to the Learning Management System\n" +
                    "_____________________________________\n" +
                    "Selection an option\n" +
                    "1. Log in\n" +
                    "2. Create an account\n" +
                    "3. Delete an account\n" +
                    "4. Quit the system" +
                    System.lineSeparator() +
                    "_____________________________________\n" +
                    "Please enter the username you want to delete: \n" +
                    System.lineSeparator() +
                    "Please enter the password you want to delete: \n" +
                    "!!!WARNING!!!\n" + System.lineSeparator() +
                    " Deleting an account will void all the posts and votes created by this user!" +
                    System.lineSeparator() +
                    "1. I understand, and I am responsible for any data lost" +
                    System.lineSeparator() +
                    "2. Cancel the deleting process\n" +
                    System.lineSeparator() +
                    "The account has been successfully deleted" + System.lineSeparator() +
                    "_____________________________________\n" +
                    "Selection an option\n" +
                    "1. Log in\n" +
                    "2. Create an account\n" +
                    "3. Delete an account\n" +
                    "4. Quit the system" +
                    System.lineSeparator() +
                    "Thanks for using Learning Management System!";


            // Runs the program with the input values
            receiveInput(input);
            UserClient.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

//            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected format",
                    expected, output);
        }

        private String commandGenerator(String... strings) {
            StringBuilder command = new StringBuilder();
            for (String string : strings) {
                command.append(string);
                command.append(System.lineSeparator());
            }
            return command.toString();
        }


    }

}
