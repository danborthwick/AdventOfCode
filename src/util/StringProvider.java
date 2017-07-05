package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public interface StringProvider {
    String next() throws Exception;
    boolean hasMore();

    static StringProvider forFile(String filename) throws Exception {
        return new StringProvider() {

            FileInputStream fstream = new FileInputStream(filename);
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

    static StringProvider forArray(String[] strings) {
        return new StringProvider() {

            int position = 0;

            @Override
            public String next() throws Exception {
                return strings[position++];
            }

            @Override
            public boolean hasMore() {
                return position < strings.length;
            }
        };
    }

    default List<String> asList() throws Exception {
        List<String> list = new ArrayList<>();
        while (hasMore()) {
            list.add(next());
        }
        return list;
    }
}
