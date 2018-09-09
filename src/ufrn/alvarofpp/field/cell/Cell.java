package ufrn.alvarofpp.field.cell;

import ufrn.alvarofpp.move.MoveType;

import java.awt.*;
import java.util.ArrayList;

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
     * Ponto X,Y que essa célula representa no mapa
     */
    private Point position;
    /**
     * O valor inserido na celula pelo algoritmo de pathfinding
     */
    private int valuePath;
    /**
     * Quantidade de vezes que a celula foi encontrada como uma boa direção
     */
    private int countPath;

    /**
     *
     */
    public Cell(String cellType, int x, int y) {
        this.cellType = CellType.declare(cellType);
        this.position = new Point(x, y);
        this.initDefault();
    }

    private void initDefault() {
        this.spawnBug = false;
        this.valuePath = -1;
        this.countPath = 0;
        this.initNeighborhood();
    }

    private void initNeighborhood() {
        this.up = null;
        this.down = null;
        this.left = null;
        this.right = null;
    }

    /**
     * Pega as direções válidas
     *
     * @return Lista de direções válidas para seguir
     */
    public ArrayList<Point> getValidMove() {
        ArrayList<Point> validMoveTypes = new ArrayList<>();
        int myX = this.position.x;
        int myY = this.position.y;

        if (this.isMovePointValid(MoveType.UP)) {
            validMoveTypes.add(new Point(myX, myY - 1));
        }
        if (this.isMovePointValid(MoveType.DOWN)) {
            validMoveTypes.add(new Point(myX, myY + 1));
        }
        if (this.isMovePointValid(MoveType.LEFT)) {
            validMoveTypes.add(new Point(myX - 1, myY));
        }
        if (this.isMovePointValid(MoveType.RIGHT)) {
            validMoveTypes.add(new Point(myX + 1, myY));
        }

        return validMoveTypes;
    }

    /**
     * Pega as celulas válidas
     *
     * @return Lista de celulas válidas para seguir
     */
    public ArrayList<Cell> getValidMoveCells() {
        ArrayList<Cell> validMoveTypes = new ArrayList<>();

        if (this.isMovePointValid(MoveType.UP)) {
            validMoveTypes.add(this.getUp());
        }
        if (this.isMovePointValid(MoveType.DOWN)) {
            validMoveTypes.add(this.getDown());
        }
        if (this.isMovePointValid(MoveType.LEFT)) {
            validMoveTypes.add(this.getLeft());
        }
        if (this.isMovePointValid(MoveType.RIGHT)) {
            validMoveTypes.add(this.getRight());
        }

        return validMoveTypes;
    }

    /**
     *
     * @return
     */
    public MoveType getBestValidMove() {
        int value = this.valuePath;
        Point point = this.position;

        for (Cell cell: this.getValidMoveCells()) {
            if (cell.getValuePath() < value) {
                value = cell.getValuePath();
                point = cell.getPosition();
            }
        }

        return this.whichMoveType(point);
    }

    /**
     *
     * @param point
     * @return
     */
    public MoveType whichMoveType(Point point) {
        int myX = this.position.x;
        int myY = this.position.y;

        if (point.x == myX && point.y == myY-1) {
            return MoveType.UP;
        } else if (point.x == myX && point.y == myY+1) {
            return MoveType.DOWN;
        } else if (point.x == myX-1 && point.y == myY) {
            return MoveType.LEFT;
        } else if (point.x == myX+1 && point.y == myY) {
            return MoveType.RIGHT;
        }

        return MoveType.PASS;
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

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getX() {
        return this.position.x;
    }

    public int getY() {
        return this.position.y;
    }

    public int getValuePath() {
        return valuePath;
    }

    public void setValuePath(int valuePath) {
        this.valuePath = valuePath;
    }

    public int getCountPath() {
        return countPath;
    }

    public void setCountPath(int countPath) {
        this.countPath = countPath;
    }
}
