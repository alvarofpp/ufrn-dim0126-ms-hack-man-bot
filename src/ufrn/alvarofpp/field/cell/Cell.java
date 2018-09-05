package ufrn.alvarofpp.field.cell;

public class Cell {
    private CellType cellType;

    public Cell() {
        this.cellType = CellType.BLOCKED;
    }

    public Cell(CellType cellType) {
        this.cellType = cellType;
    }
}
