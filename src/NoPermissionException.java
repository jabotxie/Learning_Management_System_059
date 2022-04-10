public class NoPermissionException extends Exception{
    @Override
    public String toString() {
        return "You don't have permission to proceed the action.";
    }
}
