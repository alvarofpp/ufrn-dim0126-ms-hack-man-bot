package ufrn.alvarofpp.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ufrn.alvarofpp.move.MoveType;

public class MoveTypeTest {
    @Test
    void type() {
        MoveType mtUp = MoveType.UP;
        MoveType mtDown = MoveType.DOWN;
        MoveType mtLeft = MoveType.LEFT;
        MoveType mtRight = MoveType.RIGHT;
        MoveType mtPass = MoveType.PASS;

        // Equals
        assertEquals(mtUp, MoveType.UP);
        assertEquals(mtDown, MoveType.DOWN);
        assertEquals(mtLeft, MoveType.LEFT);
        assertEquals(mtRight, MoveType.RIGHT);
        assertEquals(mtPass, MoveType.PASS);

        // NotEquals
        assertNotEquals(mtUp, mtDown);
        assertNotEquals(mtDown, mtLeft);
        assertNotEquals(mtLeft, mtRight);
        assertNotEquals(mtRight, mtPass);
        assertNotEquals(mtPass, mtUp);
    }

    @Test
    public String toString() {
        MoveType mtUp = MoveType.UP;
        MoveType mtDown = MoveType.DOWN;
        MoveType mtLeft = MoveType.LEFT;
        MoveType mtRight = MoveType.RIGHT;
        MoveType mtPass = MoveType.PASS;

        // Equals
        assertEquals(mtUp.toString(), MoveType.UP.toString());
        assertEquals(mtDown.toString(), MoveType.DOWN.toString());
        assertEquals(mtLeft.toString(), MoveType.LEFT.toString());
        assertEquals(mtRight.toString(), MoveType.RIGHT.toString());
        assertEquals(mtPass.toString(), MoveType.PASS.toString());

        // NotEquals
        assertNotEquals(mtUp.toString(), mtDown.toString());
        assertNotEquals(mtDown.toString(), mtLeft.toString());
        assertNotEquals(mtLeft.toString(), mtRight.toString());
        assertNotEquals(mtRight.toString(), mtPass.toString());
        assertNotEquals(mtPass.toString(), mtUp.toString());

        return null;
    }
}
