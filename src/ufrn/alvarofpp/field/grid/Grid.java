package ufrn.alvarofpp.field.grid;

import ufrn.alvarofpp.field.grid.cell.Cell;
import ufrn.alvarofpp.move.pathfinding.choices.rules.LessMineExplodeRule;

import java.util.ArrayList;

/**
 * Malha de celulas que representa o mapa do jogo
 */
public class Grid {
    /**
     * Largura da malha
     */
    private int width;
    /**
     * Altura da malha
     */
    private int height;
    /**
     * Malha de celulas
     */
    private Cell[][] cells;

    /**
     * Constructor
     *
     * @param width  Tamanho X da malha
     * @param height Tamanho Y da malha
     */
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;

        this.cells = new Cell[width][height];
    }

    /**
     * Pega a celula desejada
     *
     * @param x Posição X
     * @param y Posição Y
     * @return A celula desejada
     */
    public Cell getCell(int x, int y) {
        return this.cells[x][y];
    }

    /**
     * Atribui uma nova celula a malha
     *
     * @param x    Posição X
     * @param y    Posição Y
     * @param cell Celula que será atribuida
     */
    private void setCell(int x, int y, Cell cell) {
        this.cells[x][y] = cell;
    }

    /**
     * Limpa as influencias em todas as celulas
     */
    public void clearInfluence() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.cells[j][i].isPassable()) {
                    this.cells[j][i].clearInfluences();
                }
            }
        }
    }

    /**
     * Limpa os valores de perigo de laser
     */
    public void clearDangerLaser() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.cells[j][i].isPassable()) {
                    this.cells[j][i].setDangerLaser(LessMineExplodeRule.NO_DANGER_LASER);
                }
            }
        }
    }

    /**
     * Seta o valor desejado na variavel "percorrida" das celulas
     *
     * @param value Valor desejado que seja setado
     */
    public void setAllPercorrida(boolean value) {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.cells[j][i].isPassable()) {
                    this.cells[j][i].setPercorrido(value);
                }
            }
        }
    }

    /**
     * Define a malha a partir da variavel de entrada
     *
     * @param cells Celulas que irão pertencer a malha
     */
    public void define(String[] cells) {
        int x = 0;
        int y = 0;

        // Atribuindo cada celula
        for (String cell : cells) {
            this.setCell(x, y, new Cell(cell, x, y));

            // Controle dos indices da matriz
            if (++x == this.width) {
                x = 0;
                y++;
            }
        }

        x = 0;
        y = 0;
        // Declarando os vizinhos
        for (int i = 0; i < cells.length; i++) {
            Cell cell = this.cells[x][y];

            // Esquerdo
            if (x != 0) {
                cell.setLeft(this.cells[x - 1][y]);
            } else if (cell.isPortal()) {
                cell.setLeft(this.cells[this.width - 1][y]);
            }
            // Direito
            if (x != (this.width - 1)) {
                cell.setRight(this.cells[x + 1][y]);
            } else if (cell.isPortal()) {
                cell.setRight(this.cells[0][y]);
            }
            // Superior
            if (y != 0) {
                cell.setUp(this.cells[x][y - 1]);
            }
            // Inferior
            if (y != (this.height - 1)) {
                cell.setDown(this.cells[x][y + 1]);
            }

            // Controle dos indices da matriz
            if (++x == this.width) {
                x = 0;
                y++;
            }
        }
    }

    /**
     * Pega as celulas que servem de spawn de bugs
     *
     * @return Uma lista de celulas que são spawn de bugs
     */
    public ArrayList<Cell> getSpawns() {
        ArrayList<Cell> spawns = new ArrayList<Cell>(4);

        // Adiciona as celulas de spawns
        spawns.add(this.getCell(0, 0));
        spawns.add(this.getCell(0, this.height - 1));
        spawns.add(this.getCell(this.width - 1, this.height - 1));
        spawns.add(this.getCell(this.width - 1, 0));

        return spawns;
    }

    /**
     * Diz se a malha já foi definida ou não
     *
     * @return True se já foi definida, False caso contrario
     */
    public boolean isDefined() {
        return this.cells[0][0] != null;
    }
}
