/*
 * Copyright 2017 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package ufrn.alvarofpp.field;

import java.awt.*;
import java.util.ArrayList;

import ufrn.alvarofpp.field.cell.Cell;
import ufrn.alvarofpp.field.cell.CellType;
import ufrn.alvarofpp.move.MoveType;
import ufrn.alvarofpp.move.path.Pathfinding;

/**
 * field.Field
 * <p>
 * Stores all information about the playing field and
 * contains methods to perform calculations about the field
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class Field {

    protected final String EMTPY_FIELD = ".";
    protected final String BLOCKED_FIELD = "x";

    private String myId;
    private String opponentId;
    private int width;
    private int height;

    private Cell[][] grid;
    private Point myPosition;
    private Point opponentPosition;
    private ArrayList<Point> enemyPositions;
    private ArrayList<Point> snippetPositions;
    private ArrayList<Point> bombPositions;
    private ArrayList<Point> tickingBombPositions;

    public Field() {
        this.enemyPositions = new ArrayList<>();
        this.snippetPositions = new ArrayList<>();
        this.bombPositions = new ArrayList<>();
        this.tickingBombPositions = new ArrayList<>();
    }

    /**
     * Initializes field
     *
     * @throws Exception: exception
     */
    public void initField() throws Exception {
        try {
            this.grid = new Cell[this.width][this.height];
        } catch (Exception e) {
            throw new Exception("Error: trying to initialize field while field "
                    + "settings have not been parsed yet.");
        }
        clearField();
    }

    /**
     * Clears the field
     */
    public void clearField() {
        this.myPosition = null;
        this.opponentPosition = null;
        this.enemyPositions.clear();
        this.snippetPositions.clear();
        this.bombPositions.clear();
        this.tickingBombPositions.clear();
    }

    /**
     * Cria a grid
     * @param cells
     */
    private void createGrid(String[] cells) {
        int x = 0;
        int y = 0;
        int length = cells.length;

        // Primeiras atribuições
        for (String cell : cells) {
            this.grid[x][y] = new Cell(cell, x, y);

            // Controle dos indices da matriz
            if (++x == this.width) {
                x = 0;
                y++;
            }
        }

        x = 0;
        y = 0;
        // Declarando os vizinhos
        for (int i = 0; i < length; i++) {
            Cell cell = this.grid[x][y];

            // Esquerdo
            if (x != 0) {
                cell.setLeft(this.grid[x-1][y]);
            }
            // Direito
            if (x != (this.width-1)) {
                cell.setRight(this.grid[x+1][y]);
            }
            // Superior
            if (y != 0) {
                cell.setUp(this.grid[x][y-1]);
            }
            // Inferior
            if (y != (this.height-1)) {
                cell.setRight(this.grid[x][y+1]);
            }

            // Controle dos indices da matriz
            if (++x == this.width) {
                x = 0;
                y++;
            }
        }
    }

    /**
     * Parses input string from the engine and stores it in
     * this.field. Also stores several interesting points.
     *
     * @param input String input from the engine
     */
    public void parseFromString(String input) {
        clearField();

        String[] cells = input.split(",");

        // Criar o grid
        this.createGrid(cells);

        int x = 0;
        int y = 0;

        for (String cell : cells) {
            // Quando é uma celula que pode ser atualizada os valores
            if (this.grid[x][y].isEmpty()) {
                for (String cellPart : cell.split(";")) {
                    switch (cellPart.charAt(0)) {
                        case 'P':
                            parsePlayerCell(cellPart.charAt(1), x, y);
                            break;
                        case 'e':
                            // TODO: store spawn points
                            break;
                        case 'E':
                            parseEnemyCell(cellPart.charAt(1), x, y);
                            break;
                        case 'B':
                            parseBombCell(cellPart, x, y);
                            break;
                        case 'C':
                            parseSnippetCell(x, y);
                            break;
                    }
                }
            }

            // Controle dos indices da matriz
            if (++x == this.width) {
                x = 0;
                y++;
            }
        }
    }

    /**
     * Stores the position of one of the players, given by the id
     *
     * @param id Player ID
     * @param x  X-position
     * @param y  Y-position
     */
    private void parsePlayerCell(char id, int x, int y) {
        if (id == this.myId.charAt(0)) {
            this.myPosition = new Point(x, y);
        } else if (id == this.opponentId.charAt(0)) {
            this.opponentPosition = new Point(x, y);
        }
    }

    /**
     * Stores the position of an enemy. The type of enemy AI
     * is also given, but not stored in the starterbot.
     *
     * @param type Type of enemy AI
     * @param x    X-position
     * @param y    Y-position
     */
    private void parseEnemyCell(char type, int x, int y) {
        this.enemyPositions.add(new Point(x, y));
    }

    /**
     * Stores the position of a bomb that can be collected or is
     * about to explode. The amount of ticks is not stored
     * in this starterbot.
     *
     * @param cell The string that represents a bomb, if only 1 letter it
     *             can be collected, otherwise it will contain a number
     *             2 - 5, that means it's ticking to explode in that amount
     *             of rounds.
     * @param x    X-position
     * @param y    Y-position
     */
    private void parseBombCell(String cell, int x, int y) {
        if (cell.length() <= 1) {
            this.bombPositions.add(new Point(x, y));
        } else {
            this.tickingBombPositions.add(new Point(x, y));
        }
    }

    /**
     * Stores the position of a snippet
     *
     * @param x X-position
     * @param y Y-position
     */
    private void parseSnippetCell(int x, int y) {
        this.snippetPositions.add(new Point(x, y));
    }

    //Pick the best move type out of getValidMoveTypes
    public MoveType getBestMoveTypes() {
        Cell myCell = this.grid[this.myPosition.x][this.myPosition.y];
        Pathfinding pf = new Pathfinding(myCell);

        for (Point pointSnippet : getSnippetPositions()) {
            pf.algorithm(this.grid[pointSnippet.x][pointSnippet.y], 0);
        }

        return myCell.getBestValidMove();

        /*
        ArrayList<Point> validMoveTypes = this.grid[this.myPosition.x][this.myPosition.y].getValidMove();

        int leastDistance = 0;
        int distance = 0;
        int pointIndex = 0;

        // Pega a menor distancia entre os code snippets e o personagem
        for (Point pointSnippet : getSnippetPositions()) {
            for (int i = 0; i < validMoveTypes.size(); i++) {
                distance = distance(pointSnippet, validMoveTypes.get(i));
                // Verifica qual é a menor distancia
                if (leastDistance == 0 || distance < leastDistance) {
                    leastDistance = distance;
                    pointIndex = i;
                }
            }

        }

        Point bestPoint = validMoveTypes.get(pointIndex);

        //System.out.println("Lily in bestmovetype: " + bestMoveTypes);
        return myCell.whichMoveType(bestPoint);
        */
    }

    /*
     * find relative distance between potential next move and destination
     */
    public int distance(Point destination, Point potential) {
        int distance = 0;
        int deltaX = 0;
        int deltaY = 0;

        deltaX = destination.x - potential.x;
        deltaY = destination.y - potential.y;

        distance = (int) Math.sqrt((int) Math.pow(deltaX, 2) + (int) Math.pow(deltaY, 2));

        return distance;
    }

    public void setMyId(int id) {
        this.myId = id + "";
    }

    public void setOpponentId(int id) {
        this.opponentId = id + "";
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Point getMyPosition() {
        return this.myPosition;
    }

    public Point getOpponentPosition() {
        return this.opponentPosition;
    }

    public ArrayList<Point> getEnemyPositions() {
        return this.enemyPositions;
    }

    public ArrayList<Point> getSnippetPositions() {
        return this.snippetPositions;
    }

    public ArrayList<Point> getBombPositions() {
        return this.bombPositions;
    }

    public ArrayList<Point> getTickingBombPositions() {
        return this.tickingBombPositions;
    }
}
