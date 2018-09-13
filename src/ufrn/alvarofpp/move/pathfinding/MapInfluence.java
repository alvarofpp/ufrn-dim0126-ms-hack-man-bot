package ufrn.alvarofpp.move.pathfinding;

import ufrn.alvarofpp.field.grid.cell.Cell;
import ufrn.alvarofpp.move.MoveType;

/**
 * Classe que executará o algoritmo de dispersão de influência do mapa de influência
 */
public class MapInfluence {
    /**
     * Valor inicial da influencia
     */
    public static final double INFLUENCE_INIT = 1.0;
    /**
     * Tipo de influência que se esta mapeando
     */
    private InfluenceType influenceType;
    /**
     * Celula que o jogador estar
     */
    private Cell iAm;

    /**
     * Constructor
     *
     * @param iAm           Celula que o jogador estar naquele momento
     * @param influenceType Tipo de influencia que se deseja dispersar pela malha
     */
    MapInfluence(Cell iAm, InfluenceType influenceType) {
        this.iAm = iAm;
        this.influenceType = influenceType;
    }

    /**
     * Realiza o algoritmo de espalhar a influencia nas celulas (code spinnet e bug)
     *
     * @param here      Celula em questão
     * @param influence Valor de influencia que será atribuido
     */
    void algorithm(Cell here, double influence) {
        // Quando chegamos na celula que o jogador estar
        if (here.getX() == this.iAm.getX() && here.getY() == this.iAm.getY()) {
            this.iAm.setPercorrido(true);
            this.setInfluence(this.iAm, influence);
            return;
        }

        // Caso a influencia seja pequena o suficiente para ignorar
        if (this.influenceType.getMinValueInfluence() >= influence) {
            return;
        }

        // Verifica se a celula já foi percorrida
        if (!here.isPercorrido() || this.getInfluenceValue(here) < influence) {
            // Prevalece a influencia do caminho mais perto
            if (this.getInfluenceValue(here) < influence) {
                this.setInfluence(here, influence);
            }
            here.setPercorrido(true);

            // Novo valor de influencia que será atribuido
            double newValueInfluence = influence * this.influenceType.getGrauDecrescimo();

            // Atribui o novo valor as celulas ao redor validas
            for (Cell cell : here.getValidMoveCells()) {
                this.algorithm(cell, newValueInfluence);
            }
        }
    }

    void initAlgorithmSpawn(Cell here) {
        // Caso não tenha spawn de bug nos proximos rounds
        if (here.getRoundSpawn() < 1 || here.getRoundSpawn() > 2) {
            return;
        }
        // Quantidade de celulas que irã percorrer de distância (1-4)
        int distance = 5 - here.getRoundSpawn();
        // Influencia inicial
        double influence = INFLUENCE_INIT / distance;

        // Começa o algoritmo para spawn
        this.algorithmSpawn(here, influence, distance);
    }

    private void algorithmSpawn(Cell here, double influence, int distance) {
        // Caso já tenha percorrido a quantidade máxima de celulas
        if (distance == 0) {
            return;
        }

        // Insere influencia
        this.setInfluence(here, influence);

        // Novo valor de influencia
        double newInfluence = 1.0 - (0.2 * (5 - distance));

        // Atribui o novo valor as celulas ao redor validas
        for (Cell cell : here.getValidMoveCells()) {
            this.algorithmSpawn(cell, newInfluence, distance - 1);
        }
    }

    /**
     * Começo do algoritmo recursivo de inserir os valores de periculosidade de laser-mines
     *
     * @param here Celula inicial que possui uma bomba prestes a explodir
     */
    void initAlgorithmLaserMines(Cell here) {
        int dangerLaser = here.getDangerLaser();

        // Realiza a distribuição de influência nas celulas que estão nas direções válidas
        for (MoveType mt : here.getValidDirections()) {
            this.algorithmLaserMines(here, dangerLaser, mt);
        }
    }

    /**
     * Atribui o valor de periculosidade do laser da mina na celula
     *
     * @param here        Celula que se esta analisando
     * @param dangerLaser Grau de periculosidade do laser
     * @param direction   Direção do algoritmo
     */
    private void algorithmLaserMines(Cell here, int dangerLaser, MoveType direction) {
        // Verifica se chegou ao fim de uma rota
        if (here == null) {
            return;
        }
        // Quando a celula é bloqueada
        if (here.isBlocked()) {
            return;
        }

        // Caso a celula possa ter maior periculosidade
        if (here.getDangerLaser() > dangerLaser) {
            here.setDangerLaser(dangerLaser);
        }

        // Segue o algoritmo na direção informada
        if (direction.equals(MoveType.UP)) {
            this.algorithmLaserMines(here.getUp(), dangerLaser, direction);
        } else if (direction.equals(MoveType.DOWN)) {
            this.algorithmLaserMines(here.getDown(), dangerLaser, direction);
        } else if (direction.equals(MoveType.LEFT)) {
            this.algorithmLaserMines(here.getLeft(), dangerLaser, direction);
        } else if (direction.equals(MoveType.RIGHT)) {
            this.algorithmLaserMines(here.getRight(), dangerLaser, direction);
        }
    }

    /**
     * Seta a influencia atribuida a celula em questão
     *
     * @param here      Celula em questão
     * @param influence Influencia atribuida a celula
     */
    private void setInfluence(Cell here, double influence) {
        if (this.influenceType == InfluenceType.BUG) {
            here.setInfluenceBug(influence);
        } else if (this.influenceType == InfluenceType.OPPONENT) {
            here.setInfluenceOpponent(influence);
        } else {
            here.setInfluenceSnippet(influence);
        }
    }

    /**
     * Retorna o valor da influência desejada
     *
     * @param cell Celula que se deseja pegar o valor de influência
     * @return Valor da influência solicitada
     */
    private double getInfluenceValue(Cell cell) {
        if (this.influenceType == InfluenceType.BUG) {
            return cell.getInfluenceBug();
        } else if (this.influenceType == InfluenceType.OPPONENT) {
            return cell.getInfluenceOpponent();
        } else {
            return cell.getInfluenceSnippet();
        }
    }

    /**
     * Seta um novo tipo de influência no algoritmo
     *
     * @param influenceType Tipo de influência que se deseja
     */
    void setInfluenceType(InfluenceType influenceType) {
        this.influenceType = influenceType;
    }

    void setiAm(Cell iAm) {
        this.iAm = iAm;
    }
}
