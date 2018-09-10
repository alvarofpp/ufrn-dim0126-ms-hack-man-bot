package ufrn.alvarofpp.move.pathfinding;

/**
 * Tipo de influencia que pode ser atribuida a celula
 */
public enum InfluenceType {
    SNIPPET,
    BUG,
    OPPONENT;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * Dependendo do tipo de influencia, o grau de decrescimo é diferente
     *
     * @return Grau de decrescimo do tipo desejado
     */
    public double getGrauDecrescimo() {
        if (this.equals(InfluenceType.SNIPPET)) {
            return 0.95;
        } else if (this.equals(InfluenceType.BUG)) {
            return 0.8;
        } else {
            return 0.6;
        }
    }

    /**
     * Pega o valor de parada da propagação da influencia, dependendo do seu tipo
     *
     * @return Retorna o valor minimo que a influencia pode atingir
     */
    public double getMinValueInfluence() {
        if (this.equals(InfluenceType.SNIPPET)) {
            return 0.0;
        } else if (this.equals(InfluenceType.BUG)) {
            return 0.25;
        } else {
            return 0.20;
        }
    }
}
