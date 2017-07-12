package y2016;

import util.Chooser;
import util.MutableInt;
import util.StringProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class Day22GridComputer {

    public static final Pattern PATTERN = Pattern.compile("/dev/grid/node-x(?<x>\\d+)-y(?<y>\\d+)\\s+(?<size>\\d+)T\\s+(?<used>\\d+)T\\s+(?<avail>\\d+)T");
    final List<Node> allNodes;
    final List<List<Node>> grid;
    final int gridSize;

    class Node {

        int used;
        int avail;
        int size;
    }
    public Day22GridComputer(StringProvider input) throws Exception {
        allNodes = new ArrayList<>();
        input.next();

        while (input.hasMore()) {
            Matcher matcher = PATTERN.matcher(input.next());
            if (!matcher.find()) {
                throw new Exception("Bad match");
            }

            Node node = new Node();
            node.size = parseInt(matcher.group("size"));
            node.used = parseInt(matcher.group("used"));
            node.avail = parseInt(matcher.group("avail"));

            allNodes.add(node);
        }

        gridSize = (int) Math.sqrt(allNodes.size());
        grid = IntStream.range(0, gridSize).mapToObj(y -> allNodes.subList(y * gridSize, (y + 1) * gridSize)).collect(Collectors.toList());
    }

    public Node node(int x, int y) {
        return grid.get(x).get(y);
    }

    int minimumMoves() {
        return -1;
    }

    private int viablePairs() {
        Chooser<Node> chooser = new Chooser<>(allNodes);
        final MutableInt total = new MutableInt(0);

        chooser.choose(2, choice -> {
            if (isViable(choice.get(0), choice.get(1))) {
                total.increment();
            }
            if (isViable(choice.get(1), choice.get(0))) {
                total.increment();
            }
            return null;
        });

        return total.value;
    }

    private boolean isViable(Node a, Node b) {
        return (a.used > 0) && (a != b) && (b.avail > a.used);
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day22Input.txt");
        Day22GridComputer computer = new Day22GridComputer(input);
        System.out.println("Pairs: " + computer.viablePairs());
    }
}
