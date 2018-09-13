package ufrn.alvarofpp.field;

/**
 * Tipo de bug que pode surgir no jogo
 */
public enum BugType {
    RED,
    GREEN,
    YELLOW,
    PURPLE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * Declara o tipo de celular pela entrada
     *
     * @param type Inteiro que identifica o tipo de AI do bug
     * @return O tipo correspondente da celula
     */
    public static BugType declare(int type) {
        if (type == 0) {
            return BugType.RED;
        } else if (type == 1) {
            return BugType.GREEN;
        } else if (type == 2) {
            return BugType.YELLOW;
        }

        return BugType.PURPLE;
    }

    /**
     * Retorna um valor que será multiplicado pela influencia de bug que a celula sofre
     *
     * @return Multiplicador de influencia de bug de acordo com o tipo de AI
     */
    public double getValueMultiplyType() {
        if (this.equals(BugType.RED)) {
            return 1.20;
        } else if (this.equals(BugType.GREEN)) {
            return 1.175;
        } else if (this.equals(BugType.YELLOW)) {
            return 1.15;
        }

        return 1.125;
    }
}
