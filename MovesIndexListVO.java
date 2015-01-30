/**
 * Author 		: Renjith J Ephrem
 * Email  		: rje49@drexel.edu
 * Subject		: CS510 - Assignment 1.
 * Instructor 	: Dr. Christopher Geib
 * Class Name   : MovesIndexListVO.java
 *
 * This is a Virtual Object (VO) file that has the place holders, setters and getters for storing moves related information.
 * An ArrayList object of type MovesIndexListVO is used to store a list of such moves available.
 *
 */

public class MovesIndexListVO {

    int moveRowIndex1;
    int moveColumnIndex1;
    int moveRowIndex2;
    int moveColumnIndex2;

    boolean moveUp;
    boolean moveDown;
    boolean moveLeft;
    boolean moveRight;

    String move = "";

    public int getMoveRowIndex1() {
        return moveRowIndex1;
    }

    public void setMoveRowIndex1(int moveRowIndex1) {
        this.moveRowIndex1 = moveRowIndex1;
    }

    public int getMoveColumnIndex1() {
        return moveColumnIndex1;
    }

    public void setMoveColumnIndex1(int moveColumnIndex1) {
        this.moveColumnIndex1 = moveColumnIndex1;
    }

    public int getMoveRowIndex2() {
        return moveRowIndex2;
    }

    public void setMoveRowIndex2(int moveRowIndex2) {
        this.moveRowIndex2 = moveRowIndex2;
    }

    public int getMoveColumnIndex2() {
        return moveColumnIndex2;
    }

    public void setMoveColumnIndex2(int moveColumnIndex2) {
        this.moveColumnIndex2 = moveColumnIndex2;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }


}
