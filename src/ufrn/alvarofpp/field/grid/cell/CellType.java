package ufrn.alvarofpp.field.grid.cell;

/**
 * Tipo de celula que pode existir na malha
 */
public enum CellType {
    EMTPY,
    BLOCKED,
    PORTAL;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * Declara o tipo de celula pela entrada
     *
     * @param cell Tipo de celula recebido pela API
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
