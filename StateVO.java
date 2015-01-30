import java.util.HashMap;

/**
 * Author 		: Renjith J Ephrem
 * Email  		: rje49@drexel.edu
 * Subject		: CS510 - Assignment 1.
 * Instructor 	: Dr. Christopher Geib
 * Class Name   : StateVO.java
 *
 * This is the main Virtual Object (VO) file that has the place holders, setters and getters for storing cumulative information about the current game state.
 *
 * It holds the following information:
 *
 *      -- currentState     : A 2D integer matrix that holds the game state information.
 *      -- height           : The number of rows of the matrix.
 *      -- width            : The number of columns of the matrix.
 *      -- isComplete       : The boolean flag that holds the information, whether the game is in solved state or not.
 *      -- bricksHashMap    : A hash map that holds the complete information about the positions of each brick, and the brick type.
 *      -- movesHashMap     : A hash map that holds the cumulative information related to all the moves available for the bricks.
 *      -- totalMovesMap    : This hash map holds the list of all possible moves, but just the names of the moves.
 *
 */

public class StateVO {


    int[][] currentState = null;
    int height;
    int width;
    boolean isComplete = false;
    HashMap<Integer, BrickVO> bricksHashMap = new HashMap<Integer, BrickVO>();
    HashMap<Integer, MovesVO> movesHashMap      = new HashMap<Integer, MovesVO>();
    HashMap<Integer, String> totalMovesMap = new HashMap<Integer, String>();

    public int[][] getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int[][] currentState) {
        this.currentState = currentState;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public HashMap<Integer, BrickVO> getBricksHashMap() {
        return bricksHashMap;
    }

    public void setBricksHashMap(HashMap<Integer, BrickVO> bricksHashMap) {
        this.bricksHashMap = bricksHashMap;
    }

    public HashMap<Integer, MovesVO> getMovesHashMap() {
        return movesHashMap;
    }

    public void setMovesHashMap(HashMap<Integer, MovesVO> movesHashMap) {
        this.movesHashMap = movesHashMap;
    }

    public HashMap<Integer, String> getTotalMovesMap() {
        return totalMovesMap;
    }

    public void setTotalMovesMap(HashMap<Integer, String> totalMovesMap) {
        this.totalMovesMap = totalMovesMap;
    }

}
