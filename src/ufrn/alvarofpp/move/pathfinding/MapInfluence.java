package ufrn.alvarofpp.move.pathfinding;

import ufrn.alvarofpp.field.grid.cell.Cell;

/**
 * Classe que executará o algoritmo de dispersão de influência do mapa de influência
 */
public class MapInfluence {
    /**
     * Valor inicial da influencia
     */
    public static final double INFLUENCE_INIT = 1.0;
    /**
     * Tipo de influência que se esta mapeando
     */
    private InfluenceType influenceType;
    /**
     * Celula que o jogador estar
     */
    private Cell iAm;

    /**
     * Constructor
     *
     * @param iAm           Celula que o jogador estar naquele momento
     * @param influenceType Tipo de influencia que se deseja dispersar pela malha
     */
    public MapInfluence(Cell iAm, InfluenceType influenceType) {
        this.iAm = iAm;
        this.influenceType = influenceType;
    }

    /**
     * Realiza o algoritmo de espalhar a influencia nas celulas
     *
     * @param here      Celula em questão
     * @param influence Valor de influencia que será atribuido
     */
    public void algorithm(Cell here, double influence) {
        // Quando chegamos na celula que o jogador estar
        if (here.getX() == this.iAm.getX() && here.getY() == this.iAm.getY()) {
            this.iAm.setPercorrida(true);
            return;
        }

        // Verifica se a celula já foi percorrida
        if (!here.isPercorrida()) {
            this.sumInfluence(here, influence);
            here.setPercorrida(true);

            // Novo valor de influencia que será atribuido
            double newValueInfluence = influence * this.influenceType.getGrauDecrescimo();

            // Atribui o novo valor as celulas ao redor validas
            for (Cell cell : here.getValidMoveCells()) {
                this.algorithm(cell, newValueInfluence);
            }
        }

        return;
    }

    /**
     * Soma a influencia atribuida a celula em questão
     *
     * @param here      Celula em questão
     * @param influence Influencia atribuida a celula
     */
    private void sumInfluence(Cell here, double influence) {
        if (this.influenceType == InfluenceType.BUG) {
            here.sumInfluenceBug(influence);
        } else if (this.influenceType == InfluenceType.ENEMY) {
            here.sumInfluenceEnemy(influence);
        } else {
            here.sumInfluenceSnippet(influence);
        }
    }
}
