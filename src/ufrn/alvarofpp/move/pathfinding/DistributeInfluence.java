package ufrn.alvarofpp.move.pathfinding;

import ufrn.alvarofpp.field.grid.Grid;
import ufrn.alvarofpp.field.grid.cell.Cell;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe responsável por realizar os algoritmos de distribuição de influência na malha
 */
public class DistributeInfluence {
    /**
     * Malha do jogo
     */
    private Grid grid;
    /**
     * Objeto que realizará o algoritmo de Map Influence
     */
    private MapInfluence mapInfluence;

    private static final int MAX_CELLS_SPAWN = 4;

    /**
     * Constructor
     *
     * @param grid   Malha
     * @param player Posição do jogador
     */
    public DistributeInfluence(Grid grid, Point player) {
        this.grid = grid;

        Cell myCell = this.grid.getCell(player.x, player.y);
        this.mapInfluence = new MapInfluence(myCell, InfluenceType.SNIPPET);
    }

    /**
     * Realiza a distribuição de influencia na malha
     *
     * @param snippetPositions     Posição dos code snippets
     * @param bugsPositions        Posição dos bugs
     * @param tickingBombPositions Posição das minas
     */
    public void distribute(ArrayList<Point> snippetPositions, ArrayList<Point> bugsPositions,
                           ArrayList<Point> tickingBombPositions) {
        // Limpa as influencias deixadas
        this.grid.clearInfluence();

        // Realiza as distribuições de influencias
        this.influenceSnippet(snippetPositions);
        this.influenceBugs(bugsPositions);
        this.influenceMines(tickingBombPositions);
        this.influenceSpawn();
    }

    /**
     * Realiza a distribuição de influência dos code snippets
     *
     * @param snippetPositions Posição dos code snippets
     */
    private void influenceSnippet(ArrayList<Point> snippetPositions) {
        this.mapInfluence.setInfluenceType(InfluenceType.SNIPPET);

        // Distribui a influencia nas celulas para cada code snippet
        for (Point point : snippetPositions) {
            // Coloca que não foram percorridos ainda e depois percorre
            this.grid.setAllPercorrida(false);
            this.mapInfluence.algorithm(this.grid.getCell(point.x, point.y), MapInfluence.INFLUENCE_INIT);
        }
    }

    /**
     * Realiza a distribuição de influência dos bugs
     *
     * @param bugsPositions Posição dos bugs
     */
    private void influenceBugs(ArrayList<Point> bugsPositions) {
        this.mapInfluence.setInfluenceType(InfluenceType.BUG);

        // Distribui a influencia nas celulas para cada bug em um raio definido
        for (Point point : bugsPositions) {
            // Coloca que não foram percorridos ainda e depois percorre
            this.grid.setAllPercorrida(false);
            this.mapInfluence.algorithm(this.grid.getCell(point.x, point.y), MapInfluence.INFLUENCE_INIT);
        }
    }

    /**
     * Realiza a distribuição de influência das minas
     *
     * @param tickingBombPositions Posição das minas
     */
    private void influenceMines(ArrayList<Point> tickingBombPositions) {
        this.mapInfluence.setInfluenceType(InfluenceType.LASE_MINER);

        // Distribui a influencia nas celulas para cada laser-mine
        for (Point point : tickingBombPositions) {
            this.mapInfluence.initAlgorithmLaserMines(this.grid.getCell(point.x, point.y));
        }
    }

    /**
     * Realiza a distribuição de influência das minas
     */
    private void influenceSpawn() {
        this.mapInfluence.setInfluenceType(InfluenceType.BUG);

        // Distribui a influencia nas celulas para cada ponto de spawn
        for (Point point : this.grid.getSpawnPoints()) {
            this.mapInfluence.initAlgorithmSpawn(this.grid.getCell(point.x, point.y));
        }
    }

    /**
     * Salva a nova posição do jogador
     *
     * @param player Posição do jogador
     */
    public void setPlayerPosition(Point player) {
        Cell playerCell = this.grid.getCell(player.x, player.y);
        this.mapInfluence.setiAm(playerCell);
    }
}
