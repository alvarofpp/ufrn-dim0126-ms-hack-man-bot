package ufrn.alvarofpp.field.cell;

public enum CellType {
    EMTPY,
    BLOCKED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
