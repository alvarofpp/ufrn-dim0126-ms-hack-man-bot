package ufrn.alvarofpp.move.pathfinding.choices.rules;

/**
 * Define as regras que devem serem seguidas para a escolha de uma nova celula no pathfinding
 */
abstract class Rule {
    /**
     * Valor máximo de influencia aceitavel de Bug na celula
     */
    static final double MAX_INFLUENCE_BUG_ACCEPT = 0.6;
}
