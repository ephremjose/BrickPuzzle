import java.util.ArrayList;

/**
 * Author 		: Renjith J Ephrem
 * Email  		: rje49@drexel.edu
 * Subject		: CS510 - Assignment 1.
 * Instructor 	: Dr. Christopher Geib
 * Class Name   : BrickVO.java
 *
 * This is a Virtual Object (VO) file that has the place holders, setters and getters for storing information about each brick.
 * This VO object has an ArrayList object of type BrickIndexListVO.
 * Each brick is classified into one of the following:
 *          -- Single Brick ( A brick that spans only on position of the game state matrix )
 *          -- Double Brick ( A brick that spans two positions of the game state matrix )
 *          -- Quadruple Brick (A brick that spans four positions of the game state matrix )
 *          -- Wall ( A wall brick )
 *          -- Goal ( The Goal position )
 *          -- Free ( A free position available on the game state to which a legal move can be made)
 *
 */


public class BrickVO {


    ArrayList<BrickIndexListVO> brickIndexList = null;
    boolean isSingleBrick   = false;
    boolean isDoubleBrick   = false;
    boolean isQuadBrick     = false;
    boolean isWall          = false;
    boolean isGoal          = false;
    boolean isFree          = false;
    boolean isBarrier       = false;

    public ArrayList<BrickIndexListVO> getBrickIndexList() {
        return brickIndexList;
    }

    public void setBrickIndexList(ArrayList<BrickIndexListVO> brickIndex) {
        this.brickIndexList = brickIndex;
    }

    public boolean isSingleBrick() {
        return isSingleBrick;
    }

    public void setSingleBrick(boolean isSingleBrick) {
        this.isSingleBrick = isSingleBrick;
    }

    public boolean isDoubleBrick() {
        return isDoubleBrick;
    }

    public void setDoubleBrick(boolean isDoubleBrick) {
        this.isDoubleBrick = isDoubleBrick;
    }

    public boolean isQuadBrick() {
        return isQuadBrick;
    }

    public void setQuadBrick(boolean isQuadBrick) {
        this.isQuadBrick = isQuadBrick;
    }


    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean isWall) {
        this.isWall = isWall;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public void setGoal(boolean isGoal) {
        this.isGoal = isGoal;
    }



    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public boolean isBarrier() {
        return isBarrier;
    }

    public void setBarrier(boolean isBarrier) {
        this.isBarrier = isBarrier;
    }



}
