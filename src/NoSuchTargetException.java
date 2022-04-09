public class NoSuchTargetException extends Exception {
    @Override
    public String toString() {
        return "No such reply under this post.";
    }

}
