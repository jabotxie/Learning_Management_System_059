public class TargetNotFoundException extends Exception {
    @Override
    public String toString() {
        return "The course, forum, or post you are dealing with might have been deleted or edited by administrator. " +
                "You will be redirect to course selection page.";
    }
}
