package ufrn.alvarofpp.move.pathfinding.choices.rules;

import ufrn.alvarofpp.field.grid.cell.Cell;

/**
 * Classe responsável por verificar se a celula tem mais influencia de code snippet
 */
public class MoreSnippetInfluenceRule extends Rule {

    /**
     * Verifica se a celula possui maior valor de influência de code snippet do que o valor enviado
     *
     * @param cell             Celula que será verificada
     * @param influenceSnippet Valor de influencia de code snippet
     * @return True se a celula tiver mais influencia de code snippet, False caso contrário
     */
    public static boolean check(Cell cell, Double influenceSnippet) {
        return cell.getInfluenceSnippet() > influenceSnippet;
    }
}
