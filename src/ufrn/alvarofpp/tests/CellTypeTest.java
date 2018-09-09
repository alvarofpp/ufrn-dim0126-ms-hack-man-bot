package ufrn.alvarofpp.tests;

import org.junit.jupiter.api.Test;
import ufrn.alvarofpp.field.cell.CellType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CellTypeTest {
    @Test
    void type() {
        CellType cellEmpty = CellType.EMTPY;
        CellType cellBlocked = CellType.BLOCKED;
        CellType cellPortal = CellType.PORTAL;

        // Equals
        assertEquals(cellEmpty, CellType.EMTPY);
        assertEquals(cellBlocked, CellType.BLOCKED);
        assertEquals(cellPortal, CellType.PORTAL);

        // NotEquals
        assertNotEquals(cellEmpty, cellBlocked);
        assertNotEquals(cellBlocked, cellPortal);
        assertNotEquals(cellPortal, cellEmpty);
    }

    @Test
    public String toString() {
        CellType cellEmpty = CellType.EMTPY;
        CellType cellBlocked = CellType.BLOCKED;
        CellType cellPortal = CellType.PORTAL;

        // Equals
        assertEquals(cellEmpty.toString(), CellType.EMTPY.toString());
        assertEquals(cellBlocked.toString(), CellType.BLOCKED.toString());
        assertEquals(cellPortal.toString(), CellType.PORTAL.toString());

        // NotEquals
        assertNotEquals(cellEmpty.toString(), cellBlocked.toString());
        assertNotEquals(cellBlocked.toString(), cellPortal.toString());
        assertNotEquals(cellPortal.toString(), cellEmpty.toString());

        return null;
    }
}
