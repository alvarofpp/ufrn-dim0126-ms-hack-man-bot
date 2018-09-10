package ufrn.alvarofpp.field.grid.cell;

import ufrn.alvarofpp.move.MoveType;
import ufrn.alvarofpp.move.pathfinding.MapInfluence;
import ufrn.alvarofpp.move.pathfinding.choices.DangerChoice;
import ufrn.alvarofpp.move.pathfinding.choices.IdealCellChoice;
import ufrn.alvarofpp.move.pathfinding.choices.MoreSnippetInfluenceChoice;
import ufrn.alvarofpp.move.pathfinding.choices.rules.LessMineExplodeRule;

import java.awt.*;
import java.util.ArrayList;

/**
 * Celula da malha
 */
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
     * Valor de influencia por code snippet
     */
    private double influenceSnippet;
    /**
     * Valor de influencia por bugs
     */
    private double influenceBug;
    /**
     * Valor de influencia por inimigo
     */
    private double influenceOpponent;
    /**
     * Se aquela celula tem risco de ter laser das minas
     */
    private int dangerLaser;
    /**
     * Se a celula já foi percorrida pelo algoritmo de map influence
     */
    private boolean percorrido;

    /**
     * Construct
     *
     * @param cellType Tipo de celula
     * @param x        Posição X na malha
     * @param y        Posição Y na malha
     */
    public Cell(String cellType, int x, int y) {
        // Variaveis declaradas
        this.cellType = CellType.declare(cellType);
        this.position = new Point(x, y);
        // Variaveis de valores padrão
        this.spawnBug = false;
        // Vizinhos
        this.up = null;
        this.down = null;
        this.left = null;
        this.right = null;
        // Influencias
        this.influenceSnippet = 0.0;
        this.influenceBug = 0.0;
        this.influenceOpponent = 0.0;
        this.percorrido = false;
        this.dangerLaser = LessMineExplodeRule.NO_DANGER_LASER;
    }

    /**
     * Pega as direções válidas que a celula permite para a realização de algum trajeto
     *
     * @return Lista de movimentos válidos
     */
    public ArrayList<MoveType> getValidDirections() {
        ArrayList<MoveType> validDirections = new ArrayList<>();

        if (this.isMovePointValid(MoveType.UP)) {
            validDirections.add(MoveType.UP);
        }
        if (this.isMovePointValid(MoveType.DOWN)) {
            validDirections.add(MoveType.DOWN);
        }
        if (this.isMovePointValid(MoveType.LEFT)) {
            validDirections.add(MoveType.LEFT);
        }
        if (this.isMovePointValid(MoveType.RIGHT)) {
            validDirections.add(MoveType.RIGHT);
        }

        return validDirections;
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
     * Pega o melhor movimento válido para o personagem
     *
     * @return Tipo de movimento, representa a direção que o personagem deve seguir
     */
    public MoveType getBestValidMove() {
        Point point = this.position;
        double influenceSnippet = this.influenceSnippet;

        // Verifica qual é a melhor celula para se movimentar
        for (Cell cell : this.getValidMoveCells()) {
            // Verificar se aquela celula representa uma celula ideal
            if (IdealCellChoice.choose(cell, influenceSnippet)) {
                influenceSnippet = cell.getInfluenceSnippet();
                point = cell.getPosition();
            }
        }

        // Direção escolhida
        MoveType escolhido = this.whichMoveType(point);

        // Caso o MoveType escolhido seja PASS, analisar com base em outros critérios
        if (escolhido.equals(MoveType.PASS)) {
            double influenceBug = MapInfluence.INFLUENCE_INIT;
            // Verifica novamente qual é a melhor celula para se movimentar
            for (Cell cell : this.getValidMoveCells()) {
                // Ver a celula menos perigosa
                if (DangerChoice.choose(cell, influenceBug)) {
                    influenceBug = cell.getInfluenceBug();
                    point = cell.getPosition();
                }
            }

            // Atualiza a escolha
            escolhido = this.whichMoveType(point);
        }

        // Caso continue como PASS, pega o com maior influencia de code snippet
        if (escolhido.equals(MoveType.PASS)) {
            influenceSnippet = 0.0;
            // Verifica novamente qual é a melhor celula para se movimentar
            for (Cell cell : this.getValidMoveCells()) {
                if (MoreSnippetInfluenceChoice.choose(cell, influenceSnippet)) {
                    influenceSnippet = cell.getInfluenceSnippet();
                    point = cell.getPosition();
                }
            }

            // Atualiza a escolha
            escolhido = this.whichMoveType(point);
        }

        return escolhido;
    }

    /**
     * Diz se o ponto desejado é o acima, abaixo, a esquerda ou a direita do ponto do celula em questão
     *
     * @param point Ponto que se dejeseda analisar
     * @return Se o ponto é acima, abaixo, a esquerda ou a direita da celula em questão
     */
    private MoveType whichMoveType(Point point) {
        int myX = this.position.x;
        int myY = this.position.y;

        if (point.x == myX && point.y == myY - 1) {
            return MoveType.UP;
        } else if (point.x == myX && point.y == myY + 1) {
            return MoveType.DOWN;
        } else if (point.x == myX - 1 && point.y == myY) {
            return MoveType.LEFT;
        } else if (point.x == myX + 1 && point.y == myY) {
            return MoveType.RIGHT;
        }

        // Quando for portal
        if (this.isPortal() && point.y == myY) {
            if (myX > point.x) {
                return MoveType.RIGHT;
            } else if (myX < point.x) {
                return MoveType.LEFT;
            }
        }

        return MoveType.PASS;
    }

    /**
     * Verifica se é um ponto válido para andar, dependendo da direção solicitada
     *
     * @return True caso seja um ponto válido, False caso contrário
     */
    private boolean isMovePointValid(MoveType moveType) {
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
     * Verifica se é uma celula bloqueada ("x") ou não
     *
     * @return True se for do tipo BLOCKED, False caso contrário
     */
    public boolean isBlocked() {
        return this.cellType.equals(CellType.BLOCKED);
    }

    /**
     * Verifica se é um portal ("GL", "GR")
     *
     * @return True se for do tipo PORTAL, False caso contrário
     */
    public boolean isPortal() {
        return this.cellType.equals(CellType.PORTAL);
    }

    /**
     * Retorna se a celula é passavel (pode-se percorrer algum caminho a partir dela) ou não
     *
     * @return True caso seja passavel, False caso contrário
     */
    public boolean itsPassable() {
        return !this.isBlocked();
    }

    /**
     * Limpa as influencias que a celula possui
     */
    public void clearInfluences() {
        this.influenceBug = 0.0;
        this.influenceOpponent = 0.0;
        this.influenceSnippet = 0.0;
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

    public int getX() {
        return this.position.x;
    }

    public int getY() {
        return this.position.y;
    }

    public void setInfluenceSnippet(double influenceSnippet) {
        this.influenceSnippet = influenceSnippet;
    }

    public double getInfluenceSnippet() {
        return influenceSnippet;
    }

    public void setInfluenceBug(double influenceBug) {
        this.influenceBug = influenceBug;
    }

    public double getInfluenceBug() {
        return influenceBug;
    }

    public void setInfluenceOpponent(double influenceOpponent) {
        this.influenceOpponent = influenceOpponent;
    }

    public double getInfluenceOpponent() {
        return influenceOpponent;
    }

    public boolean isPercorrido() {
        return percorrido;
    }

    public void setPercorrido(boolean percorrido) {
        this.percorrido = percorrido;
    }

    public int getDangerLaser() {
        return dangerLaser;
    }

    public void setDangerLaser(int dangerLaser) {
        this.dangerLaser = dangerLaser;
    }


}
