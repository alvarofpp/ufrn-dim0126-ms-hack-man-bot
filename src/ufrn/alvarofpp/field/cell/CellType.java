package ufrn.alvarofpp.field.cell;

public enum CellType {
    EMTPY,
    BLOCKED,
    PORTAL;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * Define o tipo de celular pela entrada
     *
     * @return O tipo correspondente da celula
     */
    public static CellType declare(String cell) {
        if (cell.charAt(0) == 'x') {
            return CellType.BLOCKED;
        } else if (cell.charAt(0) == 'G') {
            return CellType.PORTAL;
        }

        return CellType.EMTPY;
    }
}
