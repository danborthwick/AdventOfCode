import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Permutor {

    private final List<Integer> list;

    public Permutor(List<Integer> list) {
        this.list = list;
    }

    public void apply(Function<List<Integer>, Void> f) {
        permute(0, f);
    }

    private void permute(int k, Function<List<Integer>, Void> f) {
        for (int i = k; i < list.size(); i++)
        {
            Collections.swap(list, k, i);
            permute(k + 1, f);
            Collections.swap(list, i, k);
        }

        if (k == list.size() - 1) {
            f.apply(list);
        }
    }
}
