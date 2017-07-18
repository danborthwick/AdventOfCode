package y2016;

import util.MutableInt;
import util.Permutor;
import util.StringProvider;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day24Robot {
    private List<List<Integer>> distances;  // from, to
    private List<List<Boolean>> walls;  // y, x
    private List<Position> positions;


    static class Position {

        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Position other = (Position) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    public Day24Robot(StringProvider input) throws Exception {
        readMap(input);
        makeDistances();
    }

    void readMap(StringProvider input) throws Exception {
        List<String> map = input.asList();
        Map<Integer, Position> positionMap = new HashMap<>();
        walls = new ArrayList<>(map.size());

        for (int y = 0; y < map.size(); y++) {
            String mapRow = map.get(y);
            ArrayList<Boolean> wallRow = new ArrayList<>(mapRow.length());
            walls.add(wallRow);

            for (int x = 0; x < mapRow.length(); x++) {
                char cell = mapRow.charAt(x);
                wallRow.add(cell == '#');
                if ('0' <= cell && cell <= '9') {
                    positionMap.put(cell - '0', new Position(x, y));
                }
            }
        }

        positions = new ArrayList<>(positionMap.size());
        for (int i=0; i < positionMap.size(); i++) {
            positions.add(positionMap.get(i));
        }
    }

    private void makeDistances() {
        distances = new ArrayList<>(positions.size());

        for (int from = 0; from < positions.size(); from++) {
            int[][] shortest = findShortestDistances(positionOf(from));
            ArrayList<Integer> row = new ArrayList<>(positions.size());

            for (int to = 0; to < positions.size(); to++) {
                Position toPos = positionOf(to);
                row.add(shortest[toPos.y][toPos.x]);
            }

            distances.add(row);
        }
    }

    private int[][] findShortestDistances(Position from) {
        int[][] shortest = new int[walls.size()][walls.get(0).size()];
        for (int[] row : shortest) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        shortest[from.y][from.x] = 0;
        Set<Position> lastVisited = new HashSet<>(walls.size());
        lastVisited.add(from);

        for (int distance = 1; lastVisited.size() > 0; distance++) {
            Set<Position> nextVisited = new HashSet<>(walls.size());

            for (Position last : lastVisited) {
                shortestDistanceHelper(last.x - 1, last.y, shortest, distance, nextVisited);
                shortestDistanceHelper(last.x + 1, last.y, shortest, distance, nextVisited);
                shortestDistanceHelper(last.x, last.y - 1, shortest, distance, nextVisited);
                shortestDistanceHelper(last.x, last.y + 1, shortest, distance, nextVisited);
            }

            lastVisited = nextVisited;
        }

        return shortest;
    }

    private void shortestDistanceHelper(int x, int y, int[][] shortest, int distance, Set<Position> visited) {
        if (!isWall(x, y) && (distance < shortest[y][x])) {
            shortest[y][x] = distance;
            visited.add(new Position(x, y));
        }
    }

    boolean isWall(int x, int y) {
        return walls.get(y).get(x);
    }

    public Position positionOf(int i) {
        return positions.get(i);
    }

    int shortestDistance() {
        List<Integer> otherPositions = IntStream.range(1, positions.size()).mapToObj(Integer::new).collect(Collectors.toList());
        final MutableInt shortest = new MutableInt(Integer.MAX_VALUE);

        new Permutor<>(otherPositions).permute(visitOrder -> {
            int from = 0;
            int distance = 0;
            for (Integer to : visitOrder) {
                distance += distanceBetween(from, to);
                from = to;
            }
            if (distance < shortest.value) {
                shortest.value = distance;
            }
            return null;
        });

        return shortest.value;
    }

    int shortestDistanceReturningHome() {
        List<Integer> otherPositions = IntStream.range(1, positions.size()).mapToObj(Integer::new).collect(Collectors.toList());
        final MutableInt shortest = new MutableInt(Integer.MAX_VALUE);

        new Permutor<>(otherPositions).permute(visitOrder -> {
            int from = 0;
            int distance = 0;
            for (Integer to : visitOrder) {
                distance += distanceBetween(from, to);
                from = to;
            }
            distance += distanceBetween(from, 0);

            if (distance < shortest.value) {
                shortest.value = distance;
            }
            return null;
        });

        return shortest.value;
    }

    int distanceBetween(int from, int to) {
        return distances.get(from).get(to);
    }

    public static void main(String[] args) throws Exception {
        StringProvider input = StringProvider.forFile("2016Day24Input.txt");
        Day24Robot robot = new Day24Robot(input);
        System.out.println("Part 1: " + robot.shortestDistance());
        System.out.println("Part 2: " + robot.shortestDistanceReturningHome());
    }
}
