package ufrn.alvarofpp.move.pathfinding.choices.rules;

import ufrn.alvarofpp.field.grid.cell.Cell;

/**
 * Classe responsável por verificar se a celula tem menos influência de bugs
 */
public class LessBugInfluenceRule extends Rule {

    /**
     * Verifica se a celula possui maior valor de influência de bugs do que o valor máximo aceito
     *
     * @param cell Celula que será verificada
     * @return True se a celula tiver menos influencia de bugs, False caso contrário
     */
    public static boolean check(Cell cell) {
        return cell.getInfluenceBug() < Rule.MAX_INFLUENCE_BUG_ACCEPT;
    }

    /**
     * Verifica se a celula possui maior valor de influência de bugs do que o valor enviado
     *
     * @param cell         Celula que será verificada
     * @param influenceBug Valor de influência de bugs
     * @return True se a celula tiver menos influencia de bugs, False caso contrário
     */
    public static boolean check(Cell cell, Double influenceBug) {
        return cell.getInfluenceBug() < influenceBug;
    }
}
