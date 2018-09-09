package ufrn.alvarofpp.move.path;

import ufrn.alvarofpp.field.cell.Cell;

public class Pathfinding {
    private Cell iAm;

    public Pathfinding(Cell iAm) {
        this.iAm = iAm;
    }

    public void algorithm(Cell here, int value) {
        // Quando chegamos na celula que o jogador estar
        if (here.getX() == this.iAm.getX() && here.getY() == this.iAm.getY()) {
            here.setValuePath(value);
            return;
        }

        // Quando a celula analisada ainda não possui valor
        // Quando a celula possui valor, porém não é o melhor caminho
        if (here.getValuePath() == -1 || here.getValuePath() > value) {
            here.setValuePath(value);

            for (Cell cell : here.getValidMoveCells()) {
                this.algorithm(cell, value+1);
            }
        }

        return;
    }
}
