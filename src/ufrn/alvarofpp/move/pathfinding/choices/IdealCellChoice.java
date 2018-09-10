package ufrn.alvarofpp.move.pathfinding.choices;

import ufrn.alvarofpp.field.grid.cell.Cell;
import ufrn.alvarofpp.move.pathfinding.choices.rules.LessBugInfluenceRule;
import ufrn.alvarofpp.move.pathfinding.choices.rules.LessMineExplodeRule;
import ufrn.alvarofpp.move.pathfinding.choices.rules.MoreSnippetInfluenceRule;

/**
 * Escolhe a celula ideal:
 * 1. Maior influencia de code snippet;
 * 2. Menor influencia de bugs;
 * 3. Menor chance de disparo de laser.
 */
public class IdealCellChoice extends Choice {

    /**
     * Verifica se é uma celula ideal ou não
     *
     * @param cell             Celula em que será aplicada as regras
     * @param influenceSnippet Valor de influencia dos codes snippets
     * @return Se é uma celula válida ou não de acordo com os parametros da escolha
     */
    public static boolean choose(Cell cell, Double influenceSnippet) {
        // Analisa influencia dos code snippet
        // Analisa a influencia dos bugs
        // Analisa a periculosidade de uma mina explodir
        return MoreSnippetInfluenceRule.check(cell, influenceSnippet)
                && LessBugInfluenceRule.check(cell)
                && LessMineExplodeRule.check(cell, LessMineExplodeRule.VERY_DANGER_LASER);
    }
}
