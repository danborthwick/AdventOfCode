package y2016;

import util.StringProvider;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

public class Day1Taxi {

    static int distance(String input) throws Exception {
        String[] commands = input.split(", ");
        Position position = new Position();

        for (String command : commands) {
            position.turn(command.charAt(0));

            int forward = parseInt(command.substring(1));

            switch (position.direction) {
                case 0: position.y += forward; break;
                case 1: position.x += forward; break;
                case 2: position.y -= forward; break;
                case 3: position.x -= forward; break;
            }
        }

        return position.manhattanDistance();
    }

    static int firstRevisitDistance(String input) throws Exception {
        String[] commands = input.split(", ");
        Position position = new Position();
        Set<String> visited = new HashSet<>();
        visited.add(position.toString());

        for (String command : commands) {
            position.turn(command.charAt(0));

            int forward = parseInt(command.substring(1));
            for (int i=0; i < forward; i++) {
                switch (position.direction) {
                    case 0:
                        position.y++;
                        break;
                    case 1:
                        position.x++;
                        break;
                    case 2:
                        position.y--;
                        break;
                    case 3:
                        position.x--;
                        break;
                }
                if (visited.contains(position.toString())) {
                    return position.manhattanDistance();
                }
                visited.add(position.toString());
            }
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        String input = StringProvider.forFile("2016Day1Input.txt").next();
        System.out.println("Distance is " + Day1Taxi.distance(input));
        System.out.println("Revisit distance is " + Day1Taxi.firstRevisitDistance(input));
    }

    private static class Position {
        public int x = 0;
        public int y = 0;
        public int direction = 0;

        void turn(char turnDirection) throws Exception {
            switch (turnDirection) {
                case 'R': direction = (direction + 1) % 4; break;
                case 'L': direction = (direction + 3) % 4; break;
                default: throw new Exception("Unexpected turn direction " + turnDirection);
            }
        }

        int manhattanDistance() {
            return abs(x) + abs(y);
        }

        @Override
        public String toString() {
            return x + " " + y;
        }
    }
}
