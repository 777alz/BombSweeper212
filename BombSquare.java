import java.util.Random;

public class BombSquare extends GameSquare {
    private boolean thisSquareHasBomb = false;
    public static final int MINE_PROBABILITY = 10;
    private boolean revealed = false;

    public BombSquare(int x, int y, GameBoard board) {
        super(x, y, "images/blank.png", board);

        Random r = new Random();
        thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
    }

    public void clicked() {
        if (thisSquareHasBomb) {
            this.setImage("images/bomb.png");
        } else {
            int bombCount = adjacentBombs();
            this.setImage("images/" + bombCount + ".png");
            if (bombCount == 0 && !revealed) {
                revealed = true;
                revealSurroundingEmptySquares();
            }
        }
    }

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

    private void revealSurroundingEmptySquares() {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip the current square
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