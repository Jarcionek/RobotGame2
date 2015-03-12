package robotgame.game;

public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    static {
        NORTH.right = EAST;
        EAST.right = SOUTH;
        SOUTH.right = WEST;
        WEST.right = NORTH;

        NORTH.left = WEST;
        WEST.left = SOUTH;
        SOUTH.left = EAST;
        EAST.left = NORTH;
    }

    private Direction left;
    private Direction right;

    public Direction left() {
        return left;
    }

    public Direction right() {
        return right;
    }

}
