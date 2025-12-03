import java.util.*;
public class Joshroo {
    private int flowers;
    private int x;
    private int y;
    private String direction;
    private String name;
    private String icon;
    private static String[][] map;
    private static int count;
    private static final ArrayList<Joshroo> Joshroos = new ArrayList<>();

    // Enums \\
    public enum DirectionOffsets {
        NORTH (0, -1),
        SOUTH (0, 1),
        WEST  (-1, 0),
        EAST  (1, 0);

        public final int offset_x;
        public final int offset_y;

        DirectionOffsets(int offset_x, int offset_y) {
            this.offset_x = offset_x;
            this.offset_y = offset_y;
        }
    }

    // Constructors \\
    public Joshroo(String n) {
        flowers = 0;
        x = 0;
        y = 0;
        name = n;
        direction = "NORTH";
        init();
    }

    public Joshroo(String n, int f) {
        flowers = f;
        x = 0;
        y = 0;
        name = n;
        direction = "NORTH";
        init();
    }

    public Joshroo(String n, int px, int py) {
        flowers = 0;
        x = px;
        y = py;
        name = n;
        direction = "NORTH";
        init();
    }

    public Joshroo(String n, int px, int py, int f) {
        flowers = f;
        x = px;
        y = py;
        name = n;
        direction = "NORTH";
        init();
    }

    public Joshroo(String n, int px, int py, String dir, int f) {
        flowers = f;
        x = px;
        y = py;
        name = n;
        direction = CheckDirection(dir);
        init();
    }
    // Action Methods \\
    public void hop() {}
    public void hop(int n) {}

    public void pick() {
        flowers++;
    }

    public void plant() {
        if (flowers != 0) flowers--;
    }

    public void toss() {}
    public void give() {}
    public void give(String relative_dir) {}
    public void turn(String relative_dir) {}

    // Boolean Methods \\
    public boolean hasFlower() {
        return flowers != 0;
    }
    public boolean isFacing(String compass_direction) {
        return compass_direction.equals(direction);
    }
    public boolean isFlower(String relative_direction) {
        if (isRelative(relative_direction)) {

        }
        return false;
    }
    public boolean isJoshroo(String relative_direction) {
        if (isRelative(relative_direction)) {

        }
        return false;
    }
    public boolean isNet(String relative_direction) {
        if (isRelative(relative_direction)) {

        }
        return false;
    }
    public boolean isWater(String relative_direction) {
        if (isRelative(relative_direction)) {

        }
        return false;
    }
    public boolean isClear(String relative_direction) {
        if (isRelative(relative_direction)) {

        }
        return false;
    }

    // Static Methods
    public static void History() {}
    public static void Debug() {}

    // Helpers \\
    private String CheckDirection(String dir) {
        String[] valid = {
                "NORTH",
                "SOUTH",
                "EAST",
                "WEST"
        };

        for (String s : valid) {
            if (dir.toUpperCase().equals(s)) return dir.toUpperCase();
        }

        return "NORTH";
    }

    private boolean isRelative(String relative) {
        String[] directions = {
                "LEFT",
                "RIGHT",
                "AHEAD",
                "HERE"
        };

        for (String s : directions) {
            if (s.equalsIgnoreCase(relative)) return true;
        }
        return false;
    }

    private boolean BoundsCheck(String dir) {
        DirectionOffsets offsets = DirectionOffsets.valueOf(dir);

        int test_x = x + offsets.offset_x;
        int test_y = y + offsets.offset_y;

        return test_x >= 0 && test_x <  map.length && test_y >= 0 && test_y < map[0].length;
    }

    private boolean Overlaps(int x, int y) {
        for (Joshroo J : Joshroos) {
            if (J.x == x && J.y == y) return true;
        }
        return false;
    }

    private boolean IdentifierAlreadyExists(String n) {
        for (Joshroo J : Joshroos) {
            if (J.name.equals(n)) return true;
        }
        return false;
    }

    private String Abbreviate(String to_abbr) {
        String out = "";
        boolean all_lowercase = false;
        int lowercase_count = 0;
        for (char c : to_abbr.toCharArray()) {
            if (c > 64 && c < 91 && out.length() < 6) out += c;
        }
        for (char c : to_abbr.toCharArray()) {
            if (out.length() < 6) out += c;
        }

        return out;
    }

    private String AddSpaces(String adding) {
        if (adding.length() == 6) return adding;
        if (adding.length() % 2 != 0) return AddSpaces(" " + adding);
        return AddSpaces(adding + " ");
    }

    private String CreateIcon(String str) {
        return str.trim().substring(0, 1);
    }

    private void PlaceJoshroo(Joshroo j) {
        int[] coords = j.GetCoords();
        String i = j.GetIcon();

        map[coords[1]][coords[0]] = i;
    }

    // Getters
    private int[] GetCoords() {
        return new int[] {y, x};
    }

    private String GetIcon() {
        return icon;
    }

    private void init() {
        if (count == 0) map = fillMap();
        count++;
        // Name Related Checks
        if (name.length() > 6) name = Abbreviate(name);
        if (name.isEmpty()) name = "NAME";
        if (name.length() != 6) name = AddSpaces(name);
        if (IdentifierAlreadyExists(name)) ErrorHandler(3);
        // Does the Jeroo overlap another
        if (Overlaps(x, y)) ErrorHandler(2);

        icon = CreateIcon(name);
        PlaceJoshroo(this);
        Joshroos.add(this);
        if (count > 4) ErrorHandler(1);
    }

    private String[][] fillMap() {
        String[][] out = new String[24][24];
        for (int r = 0; r < out.length; r++) {
            for (int c = 0; c < out[0].length; c++) {
                out[r][c] = ".";
            }
        }
        return out;
    }


    public static void PrintMap() {
        String out = "";
        int index = 0;
        // Top Border
        for (int i = 0; i < 30; i++) {
            out += "#";
        }
        out += "\n";

        for (int i = 0; i < 2; i++) {
            out += "#";
            for (int j = 0; j < 28; j++) {
                out += "W";
            }
            out += "#\n";
        }

        for (int r = 0; r < map.length; r++) {
            out += "#WW";
            for (int c = 0; c < map[0].length; c++) {
                out += map[r][c];
            }
            out += "WW#\n";
        }

        for (int i = 0; i < 2; i++) {
            out += "#";
            for (int j = 0; j < 28; j++) {
                out += "W";
            }
            out += "#\n";
        }

        for (int i = 0; i < 30; i++) {
            out += "#";
        }
        out += "\n";

        int quo = 20/2;

        for (int i = 0; i < 30; i++) {
            if (i == quo) out += "JOSHROOS";
            if (i == 0 || i == 29) out += "#";
            if (i < quo || i > quo + 8) out += " ";
        }

        out += "\n";

        // Joshroo status
        // Credit where credit due: Thank you Jair
        for (int i = 0; i < 2; i++) {
            out += "#";
            for (int j = 0; j < 2; j++) {
                if (index % 2 == 0) {
                    out += " " + Joshroos.get(index).name.substring(1) + "-- " + String.format("%02d", Joshroos.get(index).flowers);
                } else {
                    out += "  " + Joshroos.get(index).name + "-- " + String.format("%02d", Joshroos.get(index).flowers) + "";
                }

                index++;
            }

            out += "  #\n";
        }

        for (int i = 0; i < 30; i++) {
            out += "#";
        }

        System.out.println(out);
    }

    private void update(Joshroo J) {}


    private void ErrorHandler(int code) {
        switch (code) {
            case 1:
                System.out.println("There are more than 4 Joshroos on the map, remove some.");
                System.exit(code);
            case 2:
                System.out.printf("Collision for Joshroo (%s) at (%d,%d).", name, x, y);
                System.exit(code);
            case 3:
                System.out.printf("Joshroo with name (%s) already exists.", name);
                System.exit(code);
        }
    }

    public String toString() {
        return "Joshroo: " + name + "\n" +
                "Flowers: " + flowers + "\n" +
                "X Position: " + x + "\n" +
                "Y Position: " + y + "\n" +
                "Facing: " + direction + "\n";
    }
}

