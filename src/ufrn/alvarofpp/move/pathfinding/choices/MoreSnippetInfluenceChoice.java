package ufrn.alvarofpp.move.pathfinding.choices;

import ufrn.alvarofpp.field.grid.cell.Cell;
import ufrn.alvarofpp.move.pathfinding.choices.rules.LessMineExplodeRule;
import ufrn.alvarofpp.move.pathfinding.choices.rules.MoreSnippetInfluenceRule;

/**
 * Prioriza a celula menos perigosa:
 * 1. Maior influencia de code snippet;
 * 2. Menor periculosidade de uma mina explodir.
 */
public class MoreSnippetInfluenceChoice extends Choice {

    /**
     * Verifica se é uma celula com mais influencia de code snippet e perigosa (laser)
     *
     * @param cell             Celula em que será aplicada as regras
     * @param influenceSnippet Valor de influencia dos codes snippets
     * @return Se é uma celula válida ou não
     */
    public static boolean choose(Cell cell, Double influenceSnippet) {
        // Analisa influencia dos code snippet
        // Analisa a periculosidade de uma mina explodir
        return MoreSnippetInfluenceRule.check(cell, influenceSnippet)
                && LessMineExplodeRule.check(cell, LessMineExplodeRule.IS_GOING_TO_EXPLODE);
    }
}