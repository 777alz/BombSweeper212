import java.util.Random;

public class BombSquare extends GameSquare {
    private boolean thisSquareHasBomb = false;
    public static final int MINE_PROBABILITY = 10;
    //boolean for if a square has already been revealed
    private boolean revealed = false;

    public BombSquare(int x, int y, GameBoard board) {
        super(x, y, "images/blank.png", board);

        Random r = new Random();
        thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
    }

    /**
     * method for when a square is clicked:
     * if the square has a bomb, it will display that.
     * else, if there are no bombs surrounding the square it will reveal the adjacent empty squares.
     * else, it displays the number of adjacent bombs.
     *
     */
    public void clicked() {
        int bombCount = adjacentBombs();
        if (thisSquareHasBomb) {
            this.setImage("images/bomb.png");
        } else if (bombCount == 0 && !revealed) {
            revealed = true;
            revealAdjacentSquares();
            this.setImage("images/0.png");
        }
        else {
            this.setImage("images/" + bombCount + ".png");
        }
    }

    /**
     * @return the number of bombs in the squares adjacent to the current square.
     */
    private int adjacentBombs() {
        int bombCount = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip the current square
                int nx = xLocation + dx;
                int ny = yLocation + dy;
                if (nx >= 0 && nx < board.getWidth() && ny >= 0 && ny < board.getHeight()) {
                    GameSquare adjacentSquare = board.getSquareAt(nx, ny);
                    if (adjacentSquare instanceof BombSquare && ((BombSquare) adjacentSquare).thisSquareHasBomb) {
                        bombCount++;
                    }
                }
            }
        }
        return bombCount;
    }

    /**
     * Reveals the adjacent empty squares.
     */
    private void revealAdjacentSquares() {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; //skip current square
                int nx = xLocation + dx;
                int ny = yLocation + dy;
                if (nx >= 0 && nx < board.getWidth() && ny >= 0 && ny < board.getHeight()) {
                    GameSquare adjacentSquare = board.getSquareAt(nx, ny);
                    if (adjacentSquare instanceof BombSquare && !((BombSquare) adjacentSquare).revealed) {
                            adjacentSquare.clicked();
                    }
                }
            }
        }
    }
}