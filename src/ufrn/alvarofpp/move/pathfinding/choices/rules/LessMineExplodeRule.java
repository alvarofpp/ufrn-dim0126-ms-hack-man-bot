package ufrn.alvarofpp.move.pathfinding.choices.rules;

import ufrn.alvarofpp.field.grid.cell.Cell;

/**
 * Classe responsável por verificar se a celula tem menos chance de uma mina explodir e acertar o personagem
 */
public class LessMineExplodeRule extends Rule {
    /**
     * Possui nenhum risco de laser
     */
    public static final int NO_DANGER_LASER = 6;
    /**
     * Risco de laser preocupante
     */
    public static final int WORRISOME_DANGER_LASER = 3;
    /**
     * Risco de laser alto
     */
    public static final int VERY_DANGER_LASER = 2;
    /**
     * Vai explodir!!!
     */
    public static final int IS_GOING_TO_EXPLODE = 1;

    /**
     * Verifica se a celula estar ou possui menor grau de periculosidade
     *
     * @param cell        Celula que será verificada
     * @param dangerLaser Valor de risco de explosão
     * @return True se a celula tiver menos influencia de bugs, False caso contrário
     */
    public static boolean check(Cell cell, Integer dangerLaser) {
        return cell.getDangerLaser() > dangerLaser;
    }
}
