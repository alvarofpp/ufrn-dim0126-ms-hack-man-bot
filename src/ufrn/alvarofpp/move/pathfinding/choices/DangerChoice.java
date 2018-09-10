package ufrn.alvarofpp.move.pathfinding.choices;

import ufrn.alvarofpp.field.grid.cell.Cell;
import ufrn.alvarofpp.move.pathfinding.choices.rules.LessBugInfluenceRule;
import ufrn.alvarofpp.move.pathfinding.choices.rules.LessMineExplodeRule;

/**
 * Prioriza a celula menos perigosa:
 * 1. Menor influencia de bugs;
 * 2. Menor periculosidade de uma mina explodir.
 */
public class DangerChoice extends Choice {

    /**
     * Verifica se é uma celula menos perigosa ou não
     *
     * @param cell         Celula em que será aplicada as regras
     * @param influenceBug Valor de influencia dos bugs
     * @return Se é uma celula válida ou não
     */
    public static boolean choose(Cell cell, Double influenceBug) {
        // Analisa a influencia dos bugs
        // Analisa a periculosidade de uma mina explodir
        return LessBugInfluenceRule.check(cell, influenceBug)
                && LessMineExplodeRule.check(cell, LessMineExplodeRule.WORRISOME_DANGER_LASER);
    }
}
