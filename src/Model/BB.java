package Model;

import java.util.ArrayList;

/**
 * Created by Narmatha on 13.04.2017.
 */
public class BB {

    public ArrayList<ArrayList<Byte>> board;

    public BB(ArrayList<ArrayList<Byte>> board){
        this.board = board;
    }

    public ArrayList<ArrayList<Byte>> setBoardSize(){
        for (int i = 0; i < 140; i++) {
            // list.add( new ArrayList<String>(5) ); // doesn't work
            this.board.add( new ArrayList<Byte>(100) );
        }

        return this.board;
    }

}
