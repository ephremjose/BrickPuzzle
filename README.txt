
*******************************************************************************
* Please compile the files in the following order:                             *
*                                                                              *
* javac BrickIndexListVO.java                                                  *
* javac MovesIndexListVO.java                                                  *
* javac BrickVO.java                                                           *
* javac MovesVO.java                                                           *
* javac StateVO.java                                                           *
* javac ProcessClass.java                                                      *
* javac BrickGame.java                                                         *
*                                                                              *
* Start the game with the following command:                                   *
*                                                                              *
* java BrickGame <textfile name with extension> <Number of iterations of game> *
* Eg: java BrickGame SBP-level3.txt 5                                          *
*                                                                              *
******************************************************************************

IMPORTANT NOTE:
This code supports the extra credit question one - removal of barriers.
The current design supports barriers only that are present within the walls. That is, none of the outermost positions of the game state can be a barrier

Use the following files for barriers:
SBP-level1B.txt
SBP-level2B.txt
SBP-level3B.txt

There is a BrickGame_Code_Design document attached, that explains how the code works.