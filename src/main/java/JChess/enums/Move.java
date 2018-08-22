package JChess.enums;

public enum Move {
    DiagonallyLeftDown, DiagonallyLeftUp, DiagonallyRightUp, DiagonallyRightDown,
    StraightLeft, StraightUp, StraightRight, StraightDown,;

    public static Move getReverseMove (Move move) {
        if (move == DiagonallyLeftDown)
            return DiagonallyRightUp;
        else if (move == DiagonallyLeftUp)
            return DiagonallyRightDown;
        else if (move == DiagonallyRightDown)
            return DiagonallyLeftUp;
        else if (move == DiagonallyRightUp)
            return DiagonallyLeftDown;
        else if (move == StraightDown)
            return StraightUp;
        else if (move == StraightLeft)
            return StraightRight;
        else if (move == StraightRight)
            return StraightLeft;
        else if (move == StraightUp)
            return StraightDown;
        return null;
    }
}
