package Exception;

public class NotComparableException extends Exception {
    @Override
    public String toString() {
        return "Only java.util.Data is feasible using this class.";
    }
}
