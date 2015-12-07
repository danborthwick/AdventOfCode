import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public interface StringProvider {
    String next() throws Exception;
    boolean hasMore();

    static StringProvider forFile(String filename) throws Exception {
        return new StringProvider() {

            FileInputStream fstream = new FileInputStream("Day2Input.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String nextLine = br.readLine();

            @Override
            public String next() throws Exception {
                String result = nextLine;
                nextLine = br.readLine();
                return result;
            }

            @Override
            public boolean hasMore() {
                return nextLine != null;
            }
        };
    }
}
