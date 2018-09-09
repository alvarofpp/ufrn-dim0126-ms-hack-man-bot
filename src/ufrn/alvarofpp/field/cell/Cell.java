package ufrn.alvarofpp.field.cell;

import ufrn.alvarofpp.move.MoveType;

import java.awt.*;

public class Cell {
    /**
     * Tipo da celula
     */
    private CellType cellType;
    /**
     * Celulas vizinhas
     */
    private Cell up;
    private Cell down;
    private Cell left;
    private Cell right;
    /**
     * Se a celula serve de spawn para bugs
     */
    private boolean spawnBug;

    /**
     *
     */

    public Cell(CellType cellType) {
        this.cellType = cellType;
        this.initDefault();
    }

    public Cell(String cellType) {
        this.cellType = CellType.declare(cellType);
        this.initDefault();
    }

    private void initNeighborhood() {
        this.up = null;
        this.down = null;
        this.left = null;
        this.right = null;
    }

    private void initDefault() {
        this.spawnBug = false;
        this.initNeighborhood();
    }

    /**
     * Verifica se é um ponto válido para andar, dependendo da direção pedida
     *
     * @return True caso seja um ponto válido, False caso contrário
     */
    public boolean isMovePointValid(MoveType moveType) {
        if (moveType == MoveType.UP && this.getUp() != null) {
            return !this.getUp().isBlocked();
        } else if (moveType == MoveType.DOWN && this.getDown() != null) {
            return !this.getDown().isBlocked();
        } else if (moveType == MoveType.LEFT && this.getLeft() != null) {
            return !this.getLeft().isBlocked();
        } else if (moveType == MoveType.RIGHT && this.getRight() != null) {
            return !this.getRight().isBlocked();
        }

        return false;
    }

    /**
     * Verifica se é uma celula bloqueada ou não ("X")
     *
     * @return True se for do tipo BLOCKED, False caso contrário
     */
    public boolean isBlocked() {
        return this.cellType == CellType.BLOCKED;
    }

    /**
     * Verifica se é uma celula vazio ou não (".", "C", "P", "S")
     *
     * @return True se for do tipo EMPTY, False caso contrário
     */
    public boolean isEmpty() {
        return this.cellType == CellType.EMTPY;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public boolean isSpawnBug() {
        return spawnBug;
    }

    public void setSpawnBug(boolean spawnBug) {
        this.spawnBug = spawnBug;
    }

    public Cell getUp() {
        return up;
    }

    public void setUp(Cell up) {
        this.up = up;
    }

    public Cell getDown() {
        return down;
    }

    public void setDown(Cell down) {
        this.down = down;
    }

    public Cell getLeft() {
        return left;
    }

    public void setLeft(Cell left) {
        this.left = left;
    }

    public Cell getRight() {
        return right;
    }

    public void setRight(Cell right) {
        this.right = right;
    }
}
