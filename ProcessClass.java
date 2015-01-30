import java.io.*;
import java.util.*;

/**
 * Author 		: Renjith J Ephrem
 * Email  		: rje49@drexel.edu
 * Subject		: CS510 - Assignment 1.
 * Instructor 	: Dr. Christopher Geib
 * Class Name   : ProcessClass.java
 *
 * This class holds the entire code logic to perform various operations on the game state.
 *
 */

public class ProcessClass {


    /**
     *
     * This method is used to output all the logs to the output.log file, thereby keeping the console output clean.
     *
     * @param value
     */
    public void log(String value)  throws Exception
    {

        FileOutputStream fileOutputStream = null;
        File logFile = new File("output.log");
        try{

            FileWriter fileWriter = new FileWriter(logFile.getName(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(value);
            bufferedWriter.close();

        }
        catch (Exception e)
        {
            //e.printStackTrace();
            throw e;
        }

    }

    /**
     *
     * This method reads the input text file and gets the initial state of the game.
     *
     *      -- int[][] stateArray   holds the game state matrix.
     *      -- StateVO state        holds the information regarding the state.
     *
     * @param fileName
     * @return
     */
    public StateVO getInitialGameState( String fileName ) throws Exception
    {

        log("\n" + "In ProcessClass.getInitialGameState() ");

        int width               = 0;
        int height              = 0;
        int lineIndex           = 0;
        int[][] stateArray      = null;
        String lineFromFile     = "";
        Scanner scanner         = null;
        String[][] startArray   = null;
        String[] dimensionsArr  = new String [3];
        String[] tempArr        = null;
        StateVO state           = new StateVO();

        try{

            scanner = new Scanner( new FileInputStream("./" + fileName));

            while( scanner.hasNextLine())
            {
                lineFromFile = scanner.nextLine();
                if(lineIndex == 0) {
                    dimensionsArr = lineFromFile.split(",");

                    width = Integer.parseInt(dimensionsArr[0]);
                    height = Integer.parseInt(dimensionsArr[1]);

                    log("Height of Array : " + height);
                    log("Width  of Array : " + width );

                    startArray  = new String[height][width];
                    stateArray  = new int[height][width];
                }

                else
                {
                    tempArr = lineFromFile.split(",");
                    startArray[lineIndex-1] = tempArr;
                }
                lineIndex++;
            }

            System.out.println("\n" +"Initial Game State : " );
            log("\n" +"Initial Game State : \n" );

            for (int i = 0; i < height ; i++) {
                for (int j = 0; j < width; j++) {

                    stateArray[i][j] = Integer.parseInt(startArray[i][j]);

                    System.out.print(stateArray[i][j] + " ");
                    log(stateArray[i][j] + " ");
                }
                System.out.println();
                log("\n");
            }

            state.setCurrentState(stateArray);
            state.setHeight(height);
            state.setWidth(width);
        }

        catch (Exception e)
        {
            log("\nException in ProcessClass.getInitialGameState() : File not in the right format. Please check the file. " );
            log("\n" + e.getStackTrace().toString());
            throw e;

        }
        finally {
            if (scanner != null )
            scanner.close();
        }

        log("\n" +"Exiting ProcessClass.getInitialGameState() \n");
        return state;
    }


    /**
     *
     * This method clones any state that is sent in the argument and returns a new state object.
     *
     * @param state
     * @return
     */
    public StateVO cloneState( StateVO state ) throws Exception
    {
        log( "\n" + "In ProcessClass.getInitialGameState() ");
        StateVO clonedState = new StateVO();
        int[][] stateArray  = null;

        try {

            clonedState.setCurrentState(state.getCurrentState());
            clonedState.setHeight(state.getHeight());
            clonedState.setWidth(state.getWidth());
            clonedState.setMovesHashMap(state.getMovesHashMap());
            clonedState.setTotalMovesMap(state.getTotalMovesMap());
            clonedState.setBricksHashMap(state.getBricksHashMap());
            clonedState.setComplete(state.isComplete());

        }
        catch (Exception ex)
        {
            log("\nException in ProcessClass.cloneState() while cloning.");
            log("\n" + ex.getStackTrace().toString());
            throw ex;
        }

        log("\n" + "Exiting ProcessClass.cloneState() \n ");
        return clonedState;
    }


    /**
     *
     * analyzeState() method analyzes the game state. It runs through the game state matrix and does the following:
     *      -- Finds the brick number at each position.
     *      -- bricksHashMap holds information related to each brick Number.
     *      -- For the first occurrence of each brickNumber, a new entry is added into the hash map.
     *      -- If a brickNumber already exists in the hash map, then the existing information is retrieved from the map, and updated with new indexes.
     *      -- Each brickNumber is then classified into a Single / Double / Quad / Wall / Goal of Free brick.
     *      -- It also checks if the game is in completed state or not.
     *
     * @param state
     * @return
     */
    public StateVO analyzeState ( StateVO state )  throws Exception
    {
        log("\n" + "In ProcessClass.analyzeState() ");

        int[][] stateArray                  = null;
        int currentBrick                    = 0;

        ArrayList<BrickIndexListVO> brickIndexList  = null;
        HashMap<Integer, BrickVO> bricksHashMap = new HashMap<Integer, BrickVO>();
        BrickIndexListVO brickIndex                  = null;
        BrickVO      brick                          = null;

        try {


            stateArray = state.getCurrentState();

            for (int i = 0; i < state.getHeight(); i++) {
                for (int j = 0; j < state.getWidth(); j++) {

                    currentBrick = stateArray[i][j];


                    //First occurrence of a brick number.
                    if(!bricksHashMap.containsKey(currentBrick)) {
                        //brickIndexList.add(currentBrick);

                        brickIndex = new BrickIndexListVO();
                        brickIndexList = new ArrayList<BrickIndexListVO>();
                        brick = new BrickVO();

                        brickIndex.setBrickRowIndex(i);
                        brickIndex.setBrickColumnIndex(j);

                        brickIndexList.add(brickIndex);
                        brick.setBrickIndexList(brickIndexList);

                        bricksHashMap.put(currentBrick, brick);

                    }
                    else
                    {
                        //If brick already present in the has map.
                        brickIndex = new BrickIndexListVO();

                        brick = bricksHashMap.get(currentBrick);
                        brickIndexList = brick.getBrickIndexList();
                        
                        brickIndex.setBrickRowIndex(i);
                        brickIndex.setBrickColumnIndex(j);

                        brickIndexList.add(brickIndex);

                    }
                }
            }

            for( Integer key : bricksHashMap.keySet())
            {
                brick = bricksHashMap.get(key);
                brickIndexList = brick.getBrickIndexList();
                String brickType = "";

                if(key != 1)
                {
                    if (key == -1 ) {
                        brick.setGoal(true);
                        brickType = "the Goal";
                    }
                    else if(key == 0)
                    {
                        brick.setFree(true);
                        brickType = "a Free Space";
                    }

                    else {

                        if (brickIndexList.size() == 1) {
                            brick.setSingleBrick(true);
                            brickType = "a Single Brick";
                        }

                        if (brickIndexList.size() == 2) {
                            brick.setDoubleBrick(true);
                            brickType = "a Double Brick";
                        }

                        if (brickIndexList.size() == 4) {
                            brick.setQuadBrick(true);
                            brickType = "a Quadruple Brick";
                        }
                    }
                } else
                {
                    brick.setWall(true);
                    brickType = "a Wall";
                }

              log("\nBrick " + key + " is " + brickType + ", whose indexes are : ");


                for (int i = 0; i < brickIndexList.size() ; i++) {

                    brickIndex = brickIndexList.get(i);
                    log("\n" + (brickIndex.getBrickRowIndex()) + ", " + (brickIndex.getBrickColumnIndex()));
                }

            }

        }
        catch (Exception ex)
        {
            log("\nException in ProcessClass.analyzeState().");
            log("\n"+ex.getStackTrace().toString());
            throw ex;
        }

        state.setBricksHashMap(bricksHashMap);

        log("\nExiting ProcessClass.analyzeState() \n ");
        return state;
    }


    public StateVO removeBarrier ( StateVO state) throws Exception
    {
        int[][] stateArray                  = null;
        int currentBrick                    = 0;
        HashMap<Integer, BrickVO> bricksHashMap;
        ArrayList<BrickIndexListVO> brickIndexList;
        BrickVO      brick                          = null;
        int row1,row2;
        int column1,column2;

        log("\nIn ProcessClass.removeBarrier().");
        try {

            stateArray = state.getCurrentState();
            bricksHashMap = state.getBricksHashMap();


            for (Integer key : bricksHashMap.keySet()) {
                //currentBrick = stateArray[i][j];

                if (key == -2) {

                    brick = bricksHashMap.get(key);
                    brickIndexList = brick.getBrickIndexList();

                    if (brick.isSingleBrick()) {

                        row1 = brickIndexList.get(0).getBrickRowIndex();
                        column1 = brickIndexList.get(0).getBrickColumnIndex();


                        if (stateArray[row1 - 1][column1] == 2 || stateArray[row1 + 1][column1] == 2 || stateArray[row1][column1 - 1] == 2 || stateArray[row1][column1 + 1] == 2)
                            stateArray[row1][column1] = 0;

                    }

                    if (brick.isDoubleBrick()) {

                        row1 = brickIndexList.get(0).getBrickRowIndex();
                        column1 = brickIndexList.get(0).getBrickColumnIndex();

                        row2 = brickIndexList.get(1).getBrickRowIndex();
                        column2 = brickIndexList.get(1).getBrickColumnIndex();

                        System.out.println(row1 + " " + column1);

                        if (row1 == row2) {

                            if ((stateArray[row1 - 1][column1] == 2 && stateArray[row2 - 1][column2] == 2 )|| (stateArray[row1+1][column1] == 2 && stateArray[row2+1][column2] == 2)) {
                                stateArray[row1][column1] = 0;
                                stateArray[row2][column2] = 0;
                                }
                            }

                        if(column1 == column2){
                            System.out.println("here");

                            if((stateArray[row1][column1-1] == 2 && stateArray[row2][column2-1] == 2 )|| (stateArray[row1][column1+1] == 2 && stateArray[row2][column2+1] == 2)) {
                                stateArray[row1][column1] = 0;
                                stateArray[row2][column2] = 0;
                            }
                        }

                    }


                }

            }


        }

        catch (Exception e)
        {
            log("\nException in ProcessClass.removeBarrier(). Illegal barriers present in the game state.");
            log("\n"+e.getStackTrace().toString());
            throw e;
        }


        return state;

    }

    /**
     *
     * Checks if the given state is in completed position or not. This method is not called from anywhere in the code as of now.
     * AnalyzeState() method also checks for game state completion.
     *
     * @param state1
     * @return
     * @throws Exception
     */
    public boolean checkStateForCompletion( StateVO state1) throws Exception
    {

        log("\n" + "In checkStateForCompletion().");

        boolean incompleteFlag      = false;
        int currentBrick            = 0;
        int[][] stateArray1         = state1.getCurrentState();

        try{
            for (int i = 0; i < state1.getHeight() ; i++) {
                for (int j = 0; j < state1.getWidth(); j++) {

                    currentBrick = stateArray1[i][j];
                    if (currentBrick == -1)
                        incompleteFlag = true;
                }
            }

            if (incompleteFlag != true){
                state1.setComplete(true);
                System.out.println("The game is in solved state!!");
                log("\nThe game is in solved state!!");
            }
            else {
                log("\nThe game is not solved yet.");
            }

        } catch ( Exception e )
        {
            log("\nException in ProcessClass.checkStateForCompletion().");
            log("\n"+e.getStackTrace().toString());
            throw e;
        }

        return incompleteFlag;
    }


    /**
     *
     * listMovableBricks() lists all the bricks in the game state that are allowed to perform a move.
     * Basically, it takes out all the bricks in the state which are not a wall, the goal or a free brick.
     *
     * @param state
     * @return
     */
    public ArrayList<Integer> listMovableBricks ( StateVO state )  throws Exception
    {
        log("\n" + "In ProcessClass.listMovableBricks()");

        ArrayList<Integer> listOfMovableBricks = new ArrayList<Integer>();

        HashMap<Integer, BrickVO> bricksHashMap     = null;

        try {
            bricksHashMap = state.getBricksHashMap();

            for (Integer key : bricksHashMap.keySet()) {

                if (key != -1 && key != 0 && key != 1 && key != -2) {
                    //System.out.println(" Keys being added : " + key);
                    listOfMovableBricks.add(key);
                }
            }

        } catch ( Exception e)
        {
            log("\nException in ProcessClass.listMovableBricks().");
            log("\n"+e.getStackTrace().toString());
            throw e;
        }

        log("\n" + "Exiting ProcessClass.listMovableBricks() \n");
        return listOfMovableBricks;
    }

    /**
     *
     * compareState() compares two states and returns true if they are identical.
     *
     * @param state1
     * @param state2
     * @return
     */
    public boolean compareState( StateVO state1, StateVO state2) throws Exception
    {

        log("\n" + "Comparing states.");
        int[][] stateArray1                  = state1.getCurrentState();
        int[][] stateArray2                  = state2.getCurrentState();

        try{
            for (int i = 0; i < state1.height ; i++) {
                for (int j = 0; j < state1.width; j++) {

                   if(stateArray1[i][j] != stateArray2[i][j])
                       return false;
                }
            }

        } catch ( Exception e)
        {
            log("\nException in ProcessClass.compareState().");
            log("\n"+e.getStackTrace().toString());
            throw e;
        }

        return true;
    }

    /**
     *
     * This method is used to normalize any given game state.
     * Code was provided in the assignment, but had to be modified as the indexes were mixed up.
     *
     * @param state
     * @return
     */
    public StateVO normalizeState (StateVO state) throws Exception
    {

        log("\n" + "Normalizing states.");

        int[][] stateArray                  = state.getCurrentState();

        int temp;
        int nextIdx = 3;

        try {
            for (int i = 0; i < state.getHeight(); i++) {
                for (int j = 0; j < state.getWidth(); j++) {
                    if (stateArray[i][j] == nextIdx) {
                        nextIdx++;
                    } else if (stateArray[i][j] > nextIdx) {


                        temp = stateArray[i][j];
                        for (int k = 0; k < state.getHeight(); k++) {
                            for (int l = 0; l < state.getWidth(); l++) {
                                if (stateArray[k][l] == nextIdx) {
                                    stateArray[k][l] = temp;
                                } else if (stateArray[k][l] == temp) {
                                    stateArray[k][l] = nextIdx;
                                }
                            }
                        }


                        nextIdx++;
                    }
                }
            }

            System.out.println("\nNormalized state.\n");
            log("\nAfter normalizing. \n");

            for (int i = 0; i < state.getHeight(); i++) {
                for (int j = 0; j < state.getWidth(); j++) {

                    //System.out.print(stateArray[i][j] + " ");
                    log(stateArray[i][j] + " ");
                }
                //System.out.println();
                log("\n");
            }

        } catch (Exception ex)
        {
            log("\nException in ProcessClass.compareState().");
            log("\n"+ex.getStackTrace().toString());
            throw ex;
        }

        return state;

    }

    /**
     *
     * Given a state and moveNumber, this method applies the move and returns the updated StateVO object.
     *
     * Logic:
     *      -- It runs through all the bricks in the bricksHashMap of the state.
     *      -- From the totalMovesMap ( hash map ), it fetches the move to be performed corresponding to the input moveNumber.
     *      -- It will be in the format (brickNumber,move). For eg. (2,UP) or (5,LEFT) etc.
     *      -- The move string is extracted and split, to get the brick number and the move to be applied.
     *      -- The move is then applied depending on the brick type. There is an if-else-if ladder for the three types of bricks ( Single / Double / Quad ).
     *      -- For a Double Brick, it checks if it is a vertical double brick or a horizontal double brick and then applies the move.
     *
     * @param state
     * @param moveNumber
     * @return
     */
    public StateVO applyMove ( StateVO state, int moveNumber) throws Exception
    {
        log("\nPerforming move number : " + moveNumber);

        int[][] stateArray                          = state.getCurrentState();
        ArrayList<BrickIndexListVO> brickIndexList  = null;
        BrickVO brick                               = null;
        HashMap<Integer, String> totalMovesMap      = state.getTotalMovesMap();
        HashMap<Integer, BrickVO> bricksHashMap     = state.getBricksHashMap();
        String move                                 = totalMovesMap.get(moveNumber);



        move                = move.substring(1);
        move                = move.substring(0,move.length()-1);
        String[] moveArr    = move.split(",");
        int brickNumber     = Integer.parseInt(moveArr[0]);
        String operation    = moveArr[1];

        int row1,row2;
        int column1,column2;



        System.out.println("\n" + "After applying move: " + move);
        log("\n\n" + "After applying move: " + move + "\n");
        Thread.sleep(1000);
        try {

            for (Integer key : bricksHashMap.keySet()) {

                if (brickNumber == key) {

                    brick = bricksHashMap.get(key);

                    brickIndexList = brick.getBrickIndexList();

                    if (brick.isSingleBrick()) {
                        row1 = brickIndexList.get(0).getBrickRowIndex();
                        column1 = brickIndexList.get(0).getBrickColumnIndex();

                        if (operation != null && "UP".equals(operation)) {
                            stateArray[row1 - 1][column1] = brickNumber;
                            stateArray[row1][column1] = 0;
                        }

                        if (operation != null && "DOWN".equals(operation)) {
                            stateArray[row1 + 1][column1] = brickNumber;
                            stateArray[row1][column1] = 0;
                        }

                        if (operation != null && "LEFT".equals(operation)) {
                            stateArray[row1][column1 - 1] = brickNumber;
                            stateArray[row1][column1] = 0;
                        }

                        if (operation != null && "RIGHT".equals(operation)) {
                            stateArray[row1][column1 + 1] = brickNumber;
                            stateArray[row1][column1] = 0;
                        }
                    } else if (brick.isDoubleBrick()) {
                        row1 = brickIndexList.get(0).getBrickRowIndex();
                        column1 = brickIndexList.get(0).getBrickColumnIndex();

                        row2 = brickIndexList.get(1).getBrickRowIndex();
                        column2 = brickIndexList.get(1).getBrickColumnIndex();

                        if (row1 == row2) {
                            if (operation != null && "UP".equals(operation)) {
                                stateArray[row1 - 1][column1] = brickNumber;
                                stateArray[row1 - 1][column2] = brickNumber;
                                stateArray[row1][column1] = 0;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "DOWN".equals(operation)) {
                                stateArray[row1 + 1][column1] = brickNumber;
                                stateArray[row1 + 1][column2] = brickNumber;
                                stateArray[row1][column1] = 0;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "LEFT".equals(operation)) {
                                stateArray[row1][column1 - 1] = brickNumber;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "RIGHT".equals(operation)) {
                                stateArray[row1][column2 + 1] = brickNumber;
                                stateArray[row1][column1] = 0;
                            }
                        }

                        if (column1 == column2) {
                            if (operation != null && "UP".equals(operation)) {
                                stateArray[row1 - 1][column1] = brickNumber;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "DOWN".equals(operation)) {
                                stateArray[row2 + 1][column1] = brickNumber;
                                stateArray[row1][column1] = 0;
                            }

                            if (operation != null && "LEFT".equals(operation)) {
                                stateArray[row1][column1 - 1] = brickNumber;
                                stateArray[row2][column2 - 1] = brickNumber;
                                stateArray[row1][column1] = 0;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "RIGHT".equals(operation)) {
                                stateArray[row1][column1 + 1] = brickNumber;
                                stateArray[row2][column2 + 1] = brickNumber;
                                stateArray[row2][column2] = 0;
                                stateArray[row1][column1] = 0;
                            }
                        }
                    } else if (brick.isQuadBrick()) {

                        row1 = brickIndexList.get(0).getBrickRowIndex();
                        column1 = brickIndexList.get(0).getBrickColumnIndex();

                        row2 = brickIndexList.get(2).getBrickRowIndex();
                        column2 = brickIndexList.get(1).getBrickColumnIndex();

                        if (operation != null && "UP".equals(operation)) {
                            stateArray[row1 - 1][column1] = brickNumber;
                            stateArray[row1 - 1][column2] = brickNumber;
                            stateArray[row2][column1] = 0;
                            stateArray[row2][column2] = 0;
                        }

                        if (operation != null && "DOWN".equals(operation)) {
                            stateArray[row2 + 1][column1] = brickNumber;
                            stateArray[row2 + 1][column2] = brickNumber;
                            stateArray[row1][column1] = 0;
                            stateArray[row1][column2] = 0;
                        }

                        if (operation != null && "LEFT".equals(operation)) {
                            stateArray[row1][column1 - 1] = brickNumber;
                            stateArray[row2][column1 - 1] = brickNumber;
                            stateArray[row1][column2] = 0;
                            stateArray[row2][column2] = 0;
                        }

                        if (operation != null && "RIGHT".equals(operation)) {
                            stateArray[row1][column2 + 1] = brickNumber;
                            stateArray[row2][column2 + 1] = brickNumber;
                            stateArray[row2][column1] = 0;
                            stateArray[row1][column1] = 0;
                        }
                    }


                }
            }


            for (int i = 0; i < state.getHeight(); i++) {
                for (int j = 0; j < state.getWidth(); j++) {
                    System.out.print(stateArray[i][j] + " ");
                    log(stateArray[i][j] + " ");
                }
                System.out.println();
                log("\n");
            }

            Thread.sleep(1000);

        } catch (Exception ex)
        {
            log("\nException in ProcessClass.compareState().");
            log("\n"+ex.getStackTrace().toString());
            throw ex;
        }

        return state;
    }

    /**
     *
     * applyMoveCloning() method performs the exact same operation as applyMove, but here it will create a new state object and the move is performed on that.
     * The state object passed in the argument remains unaffected.
     *
     * This method is currently not called from anywhere in the code.
     *
     * @param state
     * @param moveNumber
     * @return
     */
    public StateVO applyMoveCloning ( StateVO state, int moveNumber) throws Exception
    {
        log("\nPerforming move number : " + moveNumber);

        StateVO cloneState                          = cloneState(state);
        int[][] stateArray                          = cloneState.getCurrentState();
        HashMap<Integer, String> totalMovesMap      = cloneState.getTotalMovesMap();
        HashMap<Integer, BrickVO> bricksHashMap     = cloneState.getBricksHashMap();
        ArrayList<BrickIndexListVO> brickIndexList  = null;
        BrickVO brick                               = null;
        String move                                 = totalMovesMap.get(moveNumber);

        log("\n" + "After applying move: " + move);

        move                = move.substring(1);
        move                = move.substring(0,move.length()-1);
        String[] moveArr    = move.split(",");
        int brickNumber     = Integer.parseInt(moveArr[0]);
        String operation    = moveArr[1];

        int row1,row2;
        int column1,column2;

        try {


            for (Integer key : bricksHashMap.keySet()) {

                if (brickNumber == key) {

                    brick = bricksHashMap.get(key);

                    brickIndexList = brick.getBrickIndexList();

                    if (brick.isSingleBrick()) {
                        row1 = brickIndexList.get(0).getBrickRowIndex();
                        column1 = brickIndexList.get(0).getBrickColumnIndex();

                        if (operation != null && "UP".equals(operation)) {
                            stateArray[row1 - 1][column1] = brickNumber;
                            stateArray[row1][column1] = 0;
                        }

                        if (operation != null && "DOWN".equals(operation)) {
                            stateArray[row1 + 1][column1] = brickNumber;
                            stateArray[row1][column1] = 0;
                        }

                        if (operation != null && "LEFT".equals(operation)) {
                            stateArray[row1][column1 - 1] = brickNumber;
                            stateArray[row1][column1] = 0;
                        }

                        if (operation != null && "RIGHT".equals(operation)) {
                            stateArray[row1][column1 + 1] = brickNumber;
                            stateArray[row1][column1] = 0;
                        }
                    } else if (brick.isDoubleBrick()) {
                        row1 = brickIndexList.get(0).getBrickRowIndex();
                        column1 = brickIndexList.get(0).getBrickColumnIndex();

                        row2 = brickIndexList.get(1).getBrickRowIndex();
                        column2 = brickIndexList.get(1).getBrickColumnIndex();

                        if (row1 == row2) {
                            if (operation != null && "UP".equals(operation)) {
                                stateArray[row1 - 1][column1] = brickNumber;
                                stateArray[row1 - 1][column2] = brickNumber;
                                stateArray[row1][column1] = 0;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "DOWN".equals(operation)) {
                                stateArray[row1 + 1][column1] = brickNumber;
                                stateArray[row1 + 1][column2] = brickNumber;
                                stateArray[row1][column1] = 0;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "LEFT".equals(operation)) {
                                stateArray[row1][column1 - 1] = brickNumber;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "RIGHT".equals(operation)) {
                                stateArray[row1][column1 + 1] = brickNumber;
                                stateArray[row1][column1] = 0;
                            }
                        }

                        if (column1 == column2) {
                            if (operation != null && "UP".equals(operation)) {
                                stateArray[row1 - 1][column1] = brickNumber;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "DOWN".equals(operation)) {
                                stateArray[row2 + 1][column1] = brickNumber;
                                stateArray[row1][column1] = 0;
                            }

                            if (operation != null && "LEFT".equals(operation)) {
                                stateArray[row1][column1 - 1] = brickNumber;
                                stateArray[row2][column2 - 1] = brickNumber;
                                stateArray[row1][column1] = 0;
                                stateArray[row2][column2] = 0;
                            }

                            if (operation != null && "RIGHT".equals(operation)) {
                                stateArray[row1][column1 + 1] = brickNumber;
                                stateArray[row2][column2 + 1] = brickNumber;
                                stateArray[row2][column2] = 0;
                                stateArray[row1][column1] = 0;
                            }
                        }
                    } else if (brick.isQuadBrick()) {

                        row1 = brickIndexList.get(0).getBrickRowIndex();
                        column1 = brickIndexList.get(0).getBrickColumnIndex();

                        row2 = brickIndexList.get(2).getBrickRowIndex();
                        column2 = brickIndexList.get(1).getBrickColumnIndex();

                        if (operation != null && "UP".equals(operation)) {
                            stateArray[row1 - 1][column1] = brickNumber;
                            stateArray[row1 - 1][column2] = brickNumber;
                            stateArray[row2][column1] = 0;
                            stateArray[row2][column2] = 0;
                        }

                        if (operation != null && "DOWN".equals(operation)) {
                            stateArray[row2 + 1][column1] = brickNumber;
                            stateArray[row2 + 1][column2] = brickNumber;
                            stateArray[row1][column1] = 0;
                            stateArray[row1][column2] = 0;
                        }

                        if (operation != null && "LEFT".equals(operation)) {
                            stateArray[row1][column1 - 1] = brickNumber;
                            stateArray[row2][column1 - 1] = brickNumber;
                            stateArray[row1][column2] = 0;
                            stateArray[row2][column2] = 0;
                        }

                        if (operation != null && "RIGHT".equals(operation)) {
                            stateArray[row1][column2 + 1] = brickNumber;
                            stateArray[row2][column2 + 1] = brickNumber;
                            stateArray[row2][column1] = 0;
                            stateArray[row1][column1] = 0;
                        }
                    }


                }
            }


            for (int i = 0; i < cloneState.getHeight(); i++) {
                for (int j = 0; j < cloneState.getWidth(); j++) {
                    System.out.print(stateArray[i][j] + " ");
                    log(stateArray[i][j] + " ");
                }
                System.out.println();
                log("\n");
            }

            cloneState = analyzeState(cloneState);
        } catch (Exception ex)
        {
            log("\nException in ProcessClass.compareState().");
            log("\n"+ex.getStackTrace().toString());
            throw ex;
        }

        return cloneState;
    }


    /**
     *
     * listMovesForBrick() takes a State and brickNumber as its input and returns an ArrayList that contains all the possible moves for that brick.
     *
     * Logic:
     *      -- Contains an if-else-if ladder to determine the available moves for each type of brick.
     *      -- For Double bricks, there are two different cases based on if its a Vertical or Horizontal brick.
     *
     * @param state
     * @param brickNumber
     * @return
     */
    public ArrayList<MovesIndexListVO> listMovesForBrick ( StateVO state , int brickNumber ) throws Exception
    {
        log("\n" + "In ProcessClass.listMovesForBrick() for brickNumber : " + brickNumber);


        int[][] stateArray                          = null;
        HashMap<Integer, BrickVO> bricksHashMap     = null;
        BrickVO brick                               = null;
        ArrayList<BrickIndexListVO> brickIndexList  = null;
        HashMap<Integer, MovesVO> movesHashMap      = null;
        MovesVO movesVO                             = new MovesVO();
        ArrayList<MovesIndexListVO> movesIndexList  = new ArrayList<MovesIndexListVO>();
        MovesIndexListVO movesIndexListVO           = null;

        int row1;
        int row2;
        int column1;
        int column2;

        movesVO.setBrickNumber(brickNumber);

        stateArray      = state.getCurrentState();
        bricksHashMap   = state.getBricksHashMap();
        movesHashMap    = state.getMovesHashMap();
        brick           = bricksHashMap.get(brickNumber);

        if( bricksHashMap!= null && brick != null && !movesHashMap.containsKey(brickNumber)) {

            try {


                brickIndexList = brick.getBrickIndexList();

                if (brick.isSingleBrick()) {
                    row1 = brickIndexList.get(0).getBrickRowIndex();
                    column1 = brickIndexList.get(0).getBrickColumnIndex();

                    if ((stateArray[row1 - 1][column1] == 0) || (brickNumber == 2 && (stateArray[row1 - 1][column1] == -1))) {
                        movesIndexListVO = new MovesIndexListVO();
                        movesIndexListVO.setMoveRowIndex1(row1 - 1);
                        movesIndexListVO.setMoveColumnIndex1(column1);
                        movesIndexListVO.setMoveUp(true);
                        movesIndexListVO.setMove("UP");

                        movesIndexList.add(movesIndexListVO);
                    }
                    if ((stateArray[row1 + 1][column1] == 0) || (brickNumber == 2 && (stateArray[row1 + 1][column1] == -1))) {
                        movesIndexListVO = new MovesIndexListVO();
                        movesIndexListVO.setMoveRowIndex1(row1 + 1);
                        movesIndexListVO.setMoveColumnIndex1(column1);
                        movesIndexListVO.setMoveDown(true);
                        movesIndexListVO.setMove("DOWN");

                        movesIndexList.add(movesIndexListVO);
                    }
                    if ((stateArray[row1][column1 - 1] == 0) || (brickNumber == 2 && (stateArray[row1][column1-1] == -1))) {
                        movesIndexListVO = new MovesIndexListVO();
                        movesIndexListVO.setMoveRowIndex1(row1);
                        movesIndexListVO.setMoveColumnIndex1(column1 - 1);
                        movesIndexListVO.setMoveLeft(true);
                        movesIndexListVO.setMove("LEFT");

                        movesIndexList.add(movesIndexListVO);
                    }
                    if ((stateArray[row1][column1 + 1] == 0) || (brickNumber == 2 && (stateArray[row1][column1+1] == -1))) {
                        movesIndexListVO = new MovesIndexListVO();
                        movesIndexListVO.setMoveRowIndex1(row1);
                        movesIndexListVO.setMoveColumnIndex1(column1 + 1);
                        movesIndexListVO.setMoveRight(true);
                        movesIndexListVO.setMove("RIGHT");

                        movesIndexList.add(movesIndexListVO);
                    }
                }

                else if (brick.isDoubleBrick()) {
                    row1 = brickIndexList.get(0).getBrickRowIndex();
                    column1 = brickIndexList.get(0).getBrickColumnIndex();

                    row2 = brickIndexList.get(1).getBrickRowIndex();
                    column2 = brickIndexList.get(1).getBrickColumnIndex();

                    if (row1 == row2) {

                        if (((stateArray[row1 - 1][column1] == 0) && (stateArray[row1 - 1][column2] == 0)) ||
                                (brickNumber == 2 && (stateArray[row1 - 1][column1] == -1) && (stateArray[row1 - 1][column2] == -1))) {
                            movesIndexListVO = new MovesIndexListVO();
                            movesIndexListVO.setMoveRowIndex1(row1 - 1);
                            movesIndexListVO.setMoveColumnIndex1(column1);
                            movesIndexListVO.setMoveRowIndex2(row1 - 1);
                            movesIndexListVO.setMoveColumnIndex2(column2);
                            movesIndexListVO.setMoveUp(true);
                            movesIndexListVO.setMove("UP");

                            movesIndexList.add(movesIndexListVO);
                        }
                        if (((stateArray[row1 + 1][column1] == 0) && (stateArray[row1 + 1][column2] == 0)) ||
                                ((brickNumber == 2)&&(stateArray[row1 + 1][column1] == 0) && (stateArray[row1 + 1][column2] == 0))) {
                            movesIndexListVO = new MovesIndexListVO();
                            movesIndexListVO.setMoveRowIndex1(row1 + 1);
                            movesIndexListVO.setMoveColumnIndex1(column1);
                            movesIndexListVO.setMoveRowIndex2(row1 + 1);
                            movesIndexListVO.setMoveColumnIndex2(column2);
                            movesIndexListVO.setMoveDown(true);
                            movesIndexListVO.setMove("DOWN");

                            movesIndexList.add(movesIndexListVO);
                        }
                        if ((stateArray[row1][column1 - 1] == 0) || (brickNumber==2 && stateArray[row1][column1 - 1] == -1)) {
                            movesIndexListVO = new MovesIndexListVO();
                            movesIndexListVO.setMoveRowIndex1(row1);
                            movesIndexListVO.setMoveColumnIndex1(column1 - 1);
                            movesIndexListVO.setMoveLeft(true);
                            movesIndexListVO.setMove("LEFT");

                            movesIndexList.add(movesIndexListVO);
                        }
                        if ((stateArray[row1][column2 + 1] == 0) || (brickNumber == 2 && (stateArray[row1][column2 + 1] == -1))) {
                            movesIndexListVO = new MovesIndexListVO();
                            movesIndexListVO.setMoveRowIndex1(row1);
                            movesIndexListVO.setMoveColumnIndex1(column2 + 1);
                            movesIndexListVO.setMoveRight(true);
                            movesIndexListVO.setMove("RIGHT");

                            movesIndexList.add(movesIndexListVO);
                        }
                    }

                    if (column1 == column2) {

                        if ((stateArray[row1 - 1][column1] == 0) || (brickNumber==2 && (stateArray[row1 - 1][column1] == -1))) {
                            movesIndexListVO = new MovesIndexListVO();
                            movesIndexListVO.setMoveRowIndex1(row1 - 1);
                            movesIndexListVO.setMoveColumnIndex1(column1);
                            movesIndexListVO.setMoveUp(true);
                            movesIndexListVO.setMove("UP");

                            movesIndexList.add(movesIndexListVO);
                        }
                        if ((stateArray[row2 + 1][column1] == 0) || (brickNumber==2 && (stateArray[row2 + 1][column1] == -1))) {
                            movesIndexListVO = new MovesIndexListVO();
                            movesIndexListVO.setMoveRowIndex1(row2 + 1);
                            movesIndexListVO.setMoveColumnIndex1(column1);
                            movesIndexListVO.setMoveDown(true);
                            movesIndexListVO.setMove("DOWN");

                            movesIndexList.add(movesIndexListVO);
                        }
                        if (((stateArray[row1][column1 - 1] == 0) && (stateArray[row2][column1 - 1] == 0)) || (brickNumber==2 && ((stateArray[row1][column1 - 1] == -1) && (stateArray[row2][column1 - 1] == -1)))) {
                            movesIndexListVO = new MovesIndexListVO();
                            movesIndexListVO.setMoveRowIndex1(row1);
                            movesIndexListVO.setMoveColumnIndex1(column1 - 1);
                            movesIndexListVO.setMoveRowIndex2(row2);
                            movesIndexListVO.setMoveColumnIndex2(column1 - 1);

                            movesIndexListVO.setMoveLeft(true);
                            movesIndexListVO.setMove("LEFT");

                            movesIndexList.add(movesIndexListVO);
                        }
                        if (((stateArray[row1][column2 + 1] == 0) && (stateArray[row2][column2 + 1] == 0)) || (brickNumber==2 && ((stateArray[row1][column2 + 1] == -1) && (stateArray[row2][column2 + 1] == -1)))) {
                            movesIndexListVO = new MovesIndexListVO();
                            movesIndexListVO.setMoveRowIndex1(row1);
                            movesIndexListVO.setMoveColumnIndex1(column2 + 1);
                            movesIndexListVO.setMoveRowIndex2(row2);
                            movesIndexListVO.setMoveColumnIndex2(column2 + 1);
                            movesIndexListVO.setMoveRight(true);
                            movesIndexListVO.setMove("RIGHT");

                            movesIndexList.add(movesIndexListVO);
                        }
                    }

                }

                else if( brick.isQuadBrick())
                {
                    row1 = brickIndexList.get(0).getBrickRowIndex();
                    column1 = brickIndexList.get(0).getBrickColumnIndex();

                    row2 = brickIndexList.get(2).getBrickRowIndex();
                    column2 = brickIndexList.get(1).getBrickColumnIndex();

                    if (((stateArray[row1 - 1][column1] == 0) && (stateArray[row1 - 1][column2] == 0)) || (brickNumber==2&&(stateArray[row1 - 1][column1] == -1) && (stateArray[row1 - 1][column2] == -1))) {
                        movesIndexListVO = new MovesIndexListVO();
                        movesIndexListVO.setMoveRowIndex1(row1 - 1);
                        movesIndexListVO.setMoveColumnIndex1(column1);
                        movesIndexListVO.setMoveRowIndex2(row1 - 1);
                        movesIndexListVO.setMoveColumnIndex2(column2);
                        movesIndexListVO.setMoveUp(true);
                        movesIndexListVO.setMove("UP");

                        movesIndexList.add(movesIndexListVO);
                    }
                    if (((stateArray[row2 + 1][column1] == 0) && (stateArray[row2 + 1][column2] == 0)) || (brickNumber==2 && (stateArray[row2 + 1][column1] == -1) && (stateArray[row2 + 1][column2] == -1))) {
                        movesIndexListVO = new MovesIndexListVO();
                        movesIndexListVO.setMoveRowIndex1(row2 + 1);
                        movesIndexListVO.setMoveColumnIndex1(column1);
                        movesIndexListVO.setMoveRowIndex2(row2 + 1);
                        movesIndexListVO.setMoveColumnIndex2(column2);
                        movesIndexListVO.setMoveDown(true);
                        movesIndexListVO.setMove("DOWN");

                        movesIndexList.add(movesIndexListVO);
                    }
                    if (((stateArray[row1][column1 - 1] == 0) && (stateArray[row2][column1 - 1] == 0)) || (brickNumber==2 && (stateArray[row1][column1 - 1] == 0) && (stateArray[row2][column1 - 1] == 0))) {
                        movesIndexListVO = new MovesIndexListVO();
                        movesIndexListVO.setMoveRowIndex1(row1);
                        movesIndexListVO.setMoveColumnIndex1(column1 - 1);
                        movesIndexListVO.setMoveRowIndex2(row2);
                        movesIndexListVO.setMoveColumnIndex2(column1 - 1);

                        movesIndexListVO.setMoveLeft(true);
                        movesIndexListVO.setMove("LEFT");

                        movesIndexList.add(movesIndexListVO);
                    }
                    if (((stateArray[row1][column2 + 1] == 0) && (stateArray[row2][column2 + 1] == 0)) || (brickNumber==2 && (stateArray[row1][column2 + 1] == 0) && (stateArray[row2][column2 + 1] == 0))) {
                        movesIndexListVO = new MovesIndexListVO();
                        movesIndexListVO.setMoveRowIndex1(row1);
                        movesIndexListVO.setMoveColumnIndex1(column2 + 1);
                        movesIndexListVO.setMoveRowIndex2(row2);
                        movesIndexListVO.setMoveColumnIndex2(column2 + 1);
                        movesIndexListVO.setMoveRight(true);
                        movesIndexListVO.setMove("RIGHT");

                        movesIndexList.add(movesIndexListVO);
                    }
                }


                movesVO.setMovesIndexList(movesIndexList);
                movesHashMap.put(brickNumber, movesVO);

                if (movesIndexList.size() > 0) {
                    log("\nPossible moves for brick# " + brickNumber + " are : ");

                    for (int i = 0; i < movesIndexList.size(); i++) {

                        String secondIndex = "";

                        if (brick.isDoubleBrick() || brick.isQuadBrick())
                            secondIndex += " && " + movesIndexList.get(i).getMoveRowIndex2() + ", " + movesIndexList.get(i).getMoveColumnIndex2();

                        log("\n(" + brickNumber + "," + movesIndexList.get(i).getMove() + ") to (" + movesIndexList.get(i).getMoveRowIndex1() + "," + movesIndexList.get(i).getMoveColumnIndex1() + secondIndex + ")");

                    }
                } else {
                    log("\nNo valid moves for brick# : " + brickNumber);
                }

            } catch (Exception ex) {
                log("\nException in ProcessClass.listMovesForBrick().");
                //log(ex.getStackTrace().toString());
                ex.printStackTrace();

                throw ex;
            }

        }
        else
            log("\nBrick not found or already processed. ");

        state.setMovesHashMap(movesHashMap);
        state.setBricksHashMap(bricksHashMap);

        //log("\nExiting ProcessClass.listMovesForBrick() ");
        return movesIndexList;
    }


    /**
     *
     * This method prints on to the console, the total possible moves for all the bricks of the input state.
     * It also pushes the moves into a String ArrayList, which is later used by the applyMove() method.
     *
     * @param state
     * @return
     */
    public HashMap<Integer, String> printMoves ( StateVO state ) throws Exception
    {

        HashMap<Integer, MovesVO> movesHashMap      = state.getMovesHashMap();
        HashMap<Integer, BrickVO> bricksHashMap     = state.getBricksHashMap();
        MovesVO movesVO = null;
        BrickVO brick = null;
        HashMap<Integer, String> totalMovesMap      = new HashMap<Integer, String>();
        int index   = 0;

        System.out.println("\nTotal possible moves for the current state are : ");
        log("\n\nTotal possible moves for the current state are : " );

        try {
            for (Integer key : movesHashMap.keySet()) {

                movesVO = movesHashMap.get(key);
                ArrayList<MovesIndexListVO> movesIndexList = movesVO.getMovesIndexList();

                brick = bricksHashMap.get(key);
                if (movesIndexList.size() > 0) {

                    for (int i = 0; i < movesIndexList.size(); i++) {

                        String secondIndex = "";

                        if (brick.isDoubleBrick() || brick.isQuadBrick())
                            secondIndex += " && " + movesIndexList.get(i).getMoveRowIndex2() + ", " + movesIndexList.get(i).getMoveColumnIndex2();

                        System.out.println("(" + movesVO.getBrickNumber() + "," + movesIndexList.get(i).getMove() + ") to (" + movesIndexList.get(i).getMoveRowIndex1() + "," + movesIndexList.get(i).getMoveColumnIndex1() + secondIndex + ")");
                        log("\n(" + movesVO.getBrickNumber() + "," + movesIndexList.get(i).getMove() + ") to (" + movesIndexList.get(i).getMoveRowIndex1() + "," + movesIndexList.get(i).getMoveColumnIndex1() + secondIndex + ")");

                        totalMovesMap.put(index, "(" + movesVO.getBrickNumber() + "," + movesIndexList.get(i).getMove() + ")");
                        index++;
                    }
                }


                state.setTotalMovesMap(totalMovesMap);
            }

        } catch (Exception ex)
        {
            log("\nException in ProcessClass.listMovesForBrick().");
            log(ex.getStackTrace().toString());

            throw ex;

        }
        return totalMovesMap;


    }



}
