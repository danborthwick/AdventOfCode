import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Chooser<T> {
    private final List<T> from;

    public Chooser(List<T> from) {
        this.from = from;
    }

    public void choose(final int n, Function<List<T>, Void> f) {

        List<T> output = new ArrayList<>(n);

        for (int i=0; i < n; i++) {
            output.add(from.get(i));
        }

        helper(n, 0, output, f);
    }

    private void helper(int len, int startPosition, List<T> output, Function<List<T>, Void> f) {
        if (len == 0) {
            f.apply(output);
        }
        else {
            for (int i = startPosition; i <= from.size() - len; i++) {
                output.set(output.size() - len, from.get(i));
                helper(len - 1, i + 1, output, f);
            }
        }
    }

    public long numberOfChoices(final int n) {
        return factorial(from.size()) / (factorial(n) * factorial(from.size() - n));
    }

    private static long factorial(int n) {
        long result = 1;
        for (int i=2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
