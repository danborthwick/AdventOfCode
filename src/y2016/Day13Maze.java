package y2016;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day13Maze {

    private final int seed;
    private final int[][] maze;

    private static final int UNKNOWN = 0;
    private static final int SPACE = -1;
    private static final int WALL = -2;

    public Day13Maze(int seed, int size) {
        this.seed = seed;
        maze = new int[size + 2][size + 2];
        Arrays.fill(maze[0], WALL);
        Arrays.fill(maze[size + 1], WALL);
        for (int[] row : maze) {
            row[0] = row[size + 1] = WALL;
        }
    }

    public String rowString(int row) {
        StringBuilder builder = new StringBuilder(maze[0].length);
        for (int x = 0; x < maze[0].length - 2; x++) {
            switch (get(x, row)) {
                case WALL:
                    builder.append('#');
                    break;

                case SPACE:
                    builder.append('.');
                    break;
            }

        }
        return builder.toString();
    }

    private int get(int x, int y) {
        int cell = maze[y + 1][x + 1];
        if (cell == UNKNOWN) {
            cell = calculate(x, y);
            maze[y + 1][x + 1] = cell;
        }
        return cell;
    }

    private int calculate(int x, int y) {
        int i = x*x + 3*x + 2*x*y + y + y*y + seed;
        i = i - ((i >> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
        int count = (((i + (i >> 4)) & 0x0F0F0F0F) * 0x01010101) >> 24;
        return (count % 2) == 0 ? SPACE : WALL;
    }


    int pathLengthTo(int targetX, int targetY) {
        int targetPosition = makePosition(targetX, targetY);
        return helper(targetPosition, Integer.MAX_VALUE);
    }

    private int helper(int targetPosition, int maxMoves) {
        Integer startPosition = makePosition(1, 1);

        Set<Integer> allVisited = new HashSet<>();
        Set<Integer> visitedLastMove = new HashSet<>();
        allVisited.add(startPosition);
        visitedLastMove.add(startPosition);

        int move = 0;
        while (!allVisited.contains(targetPosition) && move < maxMoves) {
            Set<Integer> visitedThisMove = new HashSet<>();

            for (int fromPosition : visitedLastMove) {
                int x = fromPosition & 0xffff;
                int y = fromPosition >> 16;

                visit(x + 1, y, visitedThisMove, allVisited);
                visit(x - 1, y, visitedThisMove, allVisited);
                visit(x, y + 1, visitedThisMove, allVisited);
                visit(x, y - 1, visitedThisMove, allVisited);
            }

            visitedLastMove = visitedThisMove;
            move++;
        }

        return maxMoves == Integer.MAX_VALUE ? move : allVisited.size();
    }

    int accessible(int steps) {
        return helper(-1, steps);
    }

    private void visit(int x, int y, Set<Integer> visitedThisMove, Set<Integer> allVisited) {
        int position = makePosition(x, y);
        if (!allVisited.contains(position) && get(x, y) != WALL) {
            visitedThisMove.add(position);
            allVisited.add(position);
        }
    }

    private int makePosition(int x, int y) {
        return (y << 16) | x;
    }

    public static void main(String[] args) {
        Day13Maze maze = new Day13Maze(1352, 50);
        System.out.println("Steps to 31,39: " + maze.pathLengthTo(31, 39));
        System.out.println("Accessible in 50: " + maze.accessible(50));
    }
}
