public class LoginUnsuccessfulException extends Exception {
    @Override
    public String toString() {
        return "Username entered doesn't exist or the password is incorrect. Please try again.";
    }
}
