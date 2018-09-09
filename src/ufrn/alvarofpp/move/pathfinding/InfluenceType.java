package ufrn.alvarofpp.move.pathfinding;

/**
 * Tipo de influencia que pode ser atribuida a celula
 */
public enum InfluenceType {
    SNIPPET,
    BUG,
    ENEMY;

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
            return 0.99;
        } else if (this.equals(InfluenceType.BUG)) {
            return 0.8;
        } else {
            return 0.6;
        }
    }
}
