package edu.stanford.cs108.tetris;

public class TetrisBrainLogic extends TetrisLogic {

    private boolean brain_check;
    private DefaultBrain defaultBrain;

    public TetrisBrainLogic(TetrisUIInterface uiInterface) {
        super(uiInterface);
        defaultBrain = new DefaultBrain();

    }

    //based on the brain check status from MainActivity update current object
    public void setBrainMode(boolean brain_check) {
        if (brain_check) this.brain_check = true;
        else this.brain_check = false;
    }

    // overriding the tick() function in case brain_check is selected
    //should I only do move once for each piece?
    @Override()
    public void tick(int verb) {
        if (!gameOn) return;
        if (!brain_check) super.tick(verb);
        else {
            if (verb == DOWN) {
                board.undo();    // remove the piece from its old position
                int i = board.getMaxHeight();
                //uncomment for debugging
                /*if(i > 4){
                    System.out.print("nada"+board.getMaxHeight());//do nothing
                }*/
                //find the best move
                Brain.Move move;
                move = defaultBrain.bestMove(board, currentPiece, board.getHeight()- TOP_SPACE, null);
                if(move == null) return;
                //if we're at the best position, drop
                if (move.x == currentX && move.piece.equals(currentPiece)) {
                    super.tick(DROP);
                    super.tick(verb);
                } else {
                    //adjust the piece
                    if (!move.piece.equals(currentPiece)) {
                        super.tick(ROTATE);

                    }
                    if (move.x < currentX) {
                        super.tick(LEFT);
                    }
                    else if (move.x > currentX) {
                        super.tick(RIGHT);
                    }
                    super.tick(verb);
                }
            }
            else{
                super.tick(verb);
            }

        }
    }
}

