package JChess.element;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PositionTest {

    @Test
    void testPositionConstructorIllegalArgumentThreeLetter () {
        assertThrows(IllegalArgumentException.class,
                () -> new Position("AA1"));
    }

    @Test
    void testPositionConstructorIllegalArgumentOutOfRange () {
        assertThrows(IllegalArgumentException.class,
                () -> new Position("I9"));
    }

    @Test
    void testPositionSecondConstructorIllegalArgumentOutOfRange () {
        assertThrows(IllegalArgumentException.class,
                () -> new Position(9,9));
    }

    @Test
    void testGetDistanceXCorrectValues () {
        Position position1 = new Position(1,1);
        Position position2 = new Position(8,8);
        assertEquals(Integer.valueOf(-7), position1.getDistanceX(position2));
        assertEquals(Integer.valueOf(7), position2.getDistanceX(position1));
    }

    @Test
    void testGetDistanceXNullPosition () {
        Position position1 = new Position(1,1);
        assertNull(position1.getDistanceX(null));
    }

    @Test
    void testGetDistanceYCorrectValues () {
        Position position1 = new Position(1,1);
        Position position2 = new Position(8,8);
        assertEquals(Integer.valueOf(-7), position1.getDistanceY(position2));
        assertEquals(Integer.valueOf(7), position2.getDistanceY(position1));
    }

    @Test
    void testGetDistanceYNullPosition () {
        Position position1 = new Position(1,1);
        assertNull(position1.getDistanceY(null));
    }

    @Test
    void testCompareToPositionsEqual () {
        Position position1 = new Position(5,5);
        Position position2 = new Position(5,5);
        assertEquals(0, position1.compareTo(position2));
    }

    @Test
    void testCompareToPositionsNotEqual () {
        Position position1 = new Position(2,5);
        Position position2 = new Position(1,3);
        assertTrue(position1.compareTo(position2) > 0);
        assertTrue(position2.compareTo(position1) < 0);
    }

    @Test
    void testCompareToPositionNull () {
        Position position1 = new Position(2,5);
        assertTrue(position1.compareTo(null) > 0);
    }
}