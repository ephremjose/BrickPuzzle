import java.util.ArrayList;

/**
 * Author 		: Renjith J Ephrem
 * Email  		: rje49@drexel.edu
 * Subject		: CS510 - Assignment 1.
 * Instructor 	: Dr. Christopher Geib
 * Class Name   : MovesVO.java
 *
 * This is a Virtual Object (VO) file that has the place holders, setters and getters for storing about a brick and a list of its available moves.
 * An ArrayList object of type MovesIndexListVO is used to store this information for each brickNumber.
 *
 */

public class MovesVO {

    int brickNumber = 0;

    ArrayList<MovesIndexListVO> movesIndexList = null;

    public ArrayList<MovesIndexListVO> getMovesIndexList() {
        return movesIndexList;
    }

    public void setMovesIndexList(ArrayList<MovesIndexListVO> movesIndexList) {
        this.movesIndexList = movesIndexList;
    }

    public int getBrickNumber() {
        return brickNumber;
    }

    public void setBrickNumber(int brickNumber) {
        this.brickNumber = brickNumber;
    }


}
