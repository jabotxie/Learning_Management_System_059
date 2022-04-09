public class UsernameAlreadyTakenException extends Exception {
    @Override
    public String toString() {
        return "The username is already taken. Please try another one.";
    }
}
