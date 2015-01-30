import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Author 		: Renjith J Ephrem
 * Email  		: rje49@drexel.edu
 * Subject		: CS510 - Assignment 1.
 * Instructor 	: Dr. Christopher Geib
 * Class Name   : BrickGame.java
 *
 * This is the entry point for the game and the main() function resides here. It calls the ProcessClass file to perform the various operations.
 *
 */
public class BrickGame {

    public static ProcessClass process    = new ProcessClass();

    /**
     *
     * runGame() method starts the game. It does the following:
     *
     *      -- Gets the initial game state after reading the file.
     *      -- Calls the function to normalize the game state, which in turn calls the function to analyze the game state.
     *      -- Calls the function to clone the game state and return a new game state object.
     *      -- Calls the function to compare two states.
     *      -- Calls the function to list the bricks that can perform a legal move action.
     *      -- For all the bricks that can perform a legal move, it calls the function that lists all the possible moves for that game state.
     *      -- Calls the function that outputs the list of moves onto the console.
     *      -- Calls the function that applies a move.
     *
     *
     * @param fileName
     * @param count
     */
    public static void runGame(String fileName, int count)
    throws Exception{


        process.log("\n" + "In BrickGame.runGame() method. ");
        StateVO initialState    = new StateVO();
        StateVO cloneState      = new StateVO();

        initialState = process.getInitialGameState(fileName);

        try {

            for (int k = 0; k < count; k++) {

                if(!process.checkStateForCompletion(initialState))
                    break;


                initialState = process.normalizeState(initialState);//Normalizing state.
                initialState = process.analyzeState(initialState);  //Analyzing the current state.
                initialState = process.removeBarrier(initialState); //Removing any barriers.
                cloneState = process.cloneState(initialState);      //Cloning state.

                process.log("\nCompared : " + process.compareState(initialState, cloneState));

                ArrayList<Integer> movableBricks = process.listMovableBricks(initialState);

                if (k > 0)
                    initialState.getMovesHashMap().clear();

                if (movableBricks.size() > 0) {
                    for (int i = 0; i < movableBricks.size(); i++) {
                        process.listMovesForBrick(initialState, movableBricks.get(i));
                    }
                } else
                    process.log("\nNone of the bricks can be moved. We have reached an impasse. ");


                HashMap<Integer, String> totalMovesMap = new HashMap<Integer, String>();
                totalMovesMap = process.printMoves(initialState);

                if(totalMovesMap.size()==0)
                {
                    System.out.println("None of the bricks can be moved. Exiting game.");
                    break;
                }

                Random rand = new Random();
                int randomNumber = 0;

                //This gets a random number within the size of the total available moves.
                if (totalMovesMap.size() > 0) {
                    randomNumber = rand.nextInt(totalMovesMap.size());
                }

                initialState = process.applyMove(initialState, randomNumber); //Applying the random move.
                //initialState = process.analyzeState(initialState); //Analyzing the state after applying move.



            }



        }    catch( Exception e )
        {
            throw e;
        }

        process.log("\n" + "Exiting BrickGame.runGame() method. ");

    }


    /**
     * Main method - Expects two arguments :
     *
     *      -- The File Name
     *      -- The number of game iterations to be performed.
     *
     * @param args
     */
    public static void main( String args[]) {


        //Setting default values.
        String fileName = "SBP-level0.txt";
        String countString = "3";

        try {
            process.log("\n\n\nIn BrickGame.Main() method. ");
            //Retrieving the arguments.
            if (args != null && args.length > 1) {
                fileName = args[0];
                countString = args[1];

            }

            int count = Integer.parseInt(countString);

            process.log("\nFile being processed : " + fileName + ", Number of iterations : " + count);
            System.out.println("\nFile being processed : " + fileName + ", Number of iterations : " + count);

            runGame(fileName, count);

            process.log("\n\nThank You for Playing!");
            System.out.println("Thank You for Playing!");
        } catch (Exception e) {

            try {
                process.log("\n\n Exception occurred. Please see log for details. Thank You for Playing!");
                System.out.println("Exception occurred. Please see log for details. Thank You for Playing!");
            }
            catch (Exception ex)
            {

                System.out.println("Exception occurred. Please see log for details. Thank You for Playing!");
            }

        }


    }
}
