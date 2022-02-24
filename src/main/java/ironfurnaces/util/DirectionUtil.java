package ironfurnaces.util;

import net.minecraft.core.Direction;


public class DirectionUtil {

    public static int getId(Direction dir) {
        if (dir == Direction.DOWN) {
            return 0;
        } else if (dir == Direction.UP) {
            return 1;
        } else if (dir == Direction.NORTH) {
            return 2;
        } else if (dir == Direction.SOUTH) {
            return 3;
        } else if (dir == Direction.WEST) {
            return 4;
        } else if (dir == Direction.EAST) {
            return 5;
        }

        return 0;
    }

    public static Direction fromId(int id) {
        switch (id) {
            case 0:
                return Direction.DOWN;
            case 1:
                return Direction.UP;
            case 2:
                return Direction.NORTH;
            case 3:
                return Direction.SOUTH;
            case 4:
                return Direction.WEST;
            case 5:
                return Direction.EAST;
            default:
                return Direction.DOWN;
        }
    }

}
