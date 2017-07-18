package y2016;

import util.StringProvider;

import java.util.*;
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
    final Set<Node> mostUsed;
    final Set<Node> mostAvail;

    class Node {
        int used;
        int avail;
        int size;
        final int x, y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void moveTo(Node destination) {
            destination.used += this.used;
            destination.avail -= this.used;
            this.used = 0;
            this.avail = this.size;

            nodeChanged(this);
            nodeChanged(destination);
        }
    }

    static class UsedComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return (n1.used < n2.used) ? -1 : 1;
        }
    }

    static class AvailableComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return (n1.avail < n2.avail) ? -1 : 1;
        }
    }

    public Day22GridComputer(StringProvider input) throws Exception {
        allNodes = new ArrayList<>();
        input.next();

        while (input.hasMore()) {
            Matcher matcher = PATTERN.matcher(input.next());
            if (!matcher.find()) {
                throw new Exception("Bad match");
            }

            Node node = new Node(parseInt(matcher.group("x")), parseInt(matcher.group("y")));
            node.size = parseInt(matcher.group("size"));
            node.used = parseInt(matcher.group("used"));
            node.avail = parseInt(matcher.group("avail"));

            allNodes.add(node);
        }

        mostUsed = new TreeSet<>(new UsedComparator().reversed());
        mostUsed.addAll(allNodes);

        mostAvail = new TreeSet<>(new AvailableComparator().reversed());
        mostAvail.addAll(allNodes);

        gridSize = (int) Math.sqrt(allNodes.size());
        grid = IntStream.range(0, gridSize).mapToObj(y -> allNodes.subList(y * gridSize, (y + 1) * gridSize)).collect(Collectors.toList());
    }

    public Node node(int x, int y) {
        return grid.get(x).get(y);
    }

    int minimumMoves() {
        return -1;
    }

    int viablePairs() {
        Iterator<Node> usedIt = mostUsed.iterator();
        Iterator<Node> availIt = mostAvail.iterator();

        int used = 0;
        int avail = Integer.MIN_VALUE;
        int availPos = -1;
        int pairCount = 0;
        final int nodeCount = allNodes.size();

        while (usedIt.hasNext() && availIt.hasNext()) {

            while ((used > avail) && availIt.hasNext()) {
                avail = availIt.next().avail;
                availPos++;
            }

            while ((used == 0) || (used < avail) && usedIt.hasNext()) {
                used = usedIt.next().used;
            }

            pairCount += nodeCount - availPos;
        }

        return pairCount;
    }

    private void nodeChanged(Node node) {
        mostUsed.remove(node);
        mostUsed.add(node);
        mostAvail.remove(node);
        mostAvail.add(node);
    }

    private boolean isViable(Node a, Node b) {
        return (a.used > 0) && (a != b) && (b.avail > a.used);
    }

    private String statistics() {
        StringBuilder builder = new StringBuilder();

        builder.append("Min used: ");
        builder.append(mostUsed.stream().limit(5).map(n -> Integer.toString(n.used)).collect(Collectors.joining(", ")));
        builder.append("\nMax used: ");
        builder.append(allNodes.stream().sorted(Comparator.comparingInt(n -> ((Node)n).used).reversed()).limit(5).map(n -> Integer.toString(n.used)).collect(Collectors.joining(", ")));

        builder.append("\nMin avail: ");
        builder.append(mostAvail.stream().limit(5).map(n -> Integer.toString(n.avail)).collect(Collectors.joining(", ")));
        builder.append("\nMax avail: ");
        builder.append(allNodes.stream().sorted(Comparator.comparingInt(n -> ((Node) n).avail).reversed()).limit(5).map(n -> Integer.toString(n.avail)).collect(Collectors.joining(", ")));

        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day22Input.txt");
        Day22GridComputer computer = new Day22GridComputer(input);
        System.out.println("Pairs: " + computer.viablePairs());

        System.out.println(computer.statistics());
    }
}
