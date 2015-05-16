// Mon Sep  1 18:08:54 EDT 2014 
// HW 1 Public Tests

// UNIX Command line instructions
// 
// Compile
// lila [ckauffm2-hw1]% javac -cp .:junit-4.11.jar *.java
// 
// Run tests
// lila [ckauffm2-hw1]% java -cp .:junit-4.11.jar HW1PublicTests
// 
// WINDOWS Command line instructions: replace colon with semicolon
// 
// Compile
// lila [ckauffm2-hw1]% javac -cp .;junit-4.11.jar *.java
// 
// Run tests
// lila [ckauffm2-hw1]% java -cp .;junit-4.11.jar HW1PublicTests
// 
// For IDEs, consult documentation to see how to run junit tests


// org.junit.Assert.assertEquals - statically imported
// assertEquals(message, expected, actual);
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class HW1PublicTests {
  /*Main method runs tests in this file*/ 
  public static void main(String args[])
  {
    org.junit.runner.JUnitCore.main("HW1PublicTests");
  } 

  ////////////////////////////////////////////////////////////////////////////////
  // TwoNTile

  // Make sure there is a constructor that takes 1 int
  // and that TwoNTile is a subclass of Tile
  @Test(timeout=100)
  public void constructors(){
    Tile t1 = new TwoNTile(4);
    assertEquals(4,t1.getScore());
    Tile t2 = new TwoNTile(16);
    assertEquals(16,t2.getScore());
  }

  // Merging

  // Unlike does not merge with unlike
  @Test(timeout=100)
  public void mergesWith1(){
    Tile t1 = new TwoNTile(4);
    Tile t2 = new TwoNTile(16);
    assertFalse(t1.mergesWith(t2));
    assertFalse(t2.mergesWith(t1));
  }    
  @Test(timeout=100)
  public void mergesWith2(){
    Tile t1 = new TwoNTile(8);
    Tile t2 = new TwoNTile(2);
    assertFalse(t1.mergesWith(t2));
    assertFalse(t2.mergesWith(t1));
  }    
  // Like merges with like
  @Test(timeout=100)
  public void mergesWith3(){
    Tile t1 = new TwoNTile(4);
    Tile t2 = new TwoNTile(4);
    assertTrue(t1.mergesWith(t2));
    assertTrue(t2.mergesWith(t1));
  }    
  @Test(timeout=100)
  public void mergesWith4(){
    Tile t1 = new TwoNTile(8);
    Tile t2 = new TwoNTile(8);
    assertTrue(t1.mergesWith(t2));
    assertTrue(t2.mergesWith(t1));
  }    
  // Dummy tile for testing 
  public class DummyTile extends Tile{
    public boolean mergesWith(Tile t){ return false; }
    public Tile merge(Tile t){ return null; }
    public int getScore(){ return 0; }
    public String toString(){ return "dummy"; }
  }
  // TwoNTiles don't merge with other types of tiles
  @Test(timeout=100)
  public void doesntMerge1(){
    Tile t1 = new TwoNTile(8);
    Tile t2 = new DummyTile();
    assertFalse(t1.mergesWith(t2));
    assertFalse(t2.mergesWith(t1));
  }
  // Dummy tile for testing 
  public class DummyTile4 extends DummyTile{
    public int getScore(){ return 4; }
  }    
  // TwoNTiles don't merge with other types of tiles
  @Test(timeout=100)
  public void doesntMerge2(){
    Tile t1 = new TwoNTile(4);
    Tile t2 = new DummyTile4();
    assertFalse(t1.mergesWith(t2));
    assertFalse(t2.mergesWith(t1));
  }

  // Merging TwoNTiles should yield new tiles with double the value
  // Original values should not change for old tiles
  @Test(timeout=100)
  public void doMerge1(){
    Tile t1 = new TwoNTile(8);
    Tile t2 = new TwoNTile(8);
    Tile tm = t1.merge(t2);
    assertEquals(8,t1.getScore());
    assertEquals(8,t2.getScore());
    assertEquals(16,tm.getScore());
  }
  @Test(timeout=100)
  public void doMerge2(){
    Tile t1 = new TwoNTile(16);
    Tile t2 = new TwoNTile(16);
    Tile tm = t1.merge(t2);
    assertEquals(16,t1.getScore());
    assertEquals(16,t2.getScore());
    assertEquals(32,tm.getScore());
  }

  // Should raise exceptions when trying to merge improper tiles
  @Test(timeout=100, expected=Exception.class)
  public void mergeFail1(){
    Tile t1 = new TwoNTile(8);
    Tile t2 = new TwoNTile(4);
    Tile tm = t1.merge(t2);
  }
  @Test(timeout=100, expected=Exception.class)
  public void mergeFail2(){
    Tile t1 = new TwoNTile(4);
    Tile t2 = new TwoNTile(8);
    Tile tm = t1.merge(t2);
  }
  @Test(timeout=100, expected=Exception.class)
  public void mergeFail3(){
    Tile t1 = new TwoNTile(4);
    Tile t2 = new DummyTile();
    Tile tm = t1.merge(t2);
  }


  ////////////////////////////////////////////////////////////////////////////////
  // Board Utilities

  // Utility to convert a 2d array into a string for error printing
  public static String arraysToString(Object objs[][]){
    StringBuilder sb = new StringBuilder();
    String fmt = "%4s ";
    int rows=objs.length, cols=objs[0].length;

    for(int i=0; i<rows; i++){
      for(int j=0; j<cols; j++){
        String append = "-";
        if(objs[i][j]!=null){
          append = objs[i][j].toString();
        }
        sb.append(String.format(fmt,append));
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  // Utility to create a grid of tiles from a grid of integers
  public static Tile [][] tilesFromIntegers(Integer arr[][]){
    int rows = arr.length, cols=arr[0].length;
    Tile tiles[][] = new Tile[rows][cols];
    for(int i=0; i<rows; i++){
      for(int j=0; j<cols; j++){
        if(arr[i][j] != null){
          tiles[i][j] = new TwoNTile(arr[i][j]);
        }
      }
    }
    return tiles;
  }

  // Utility to assert that a board has a certain layout of tile
  // scores, check sizes and tile counts
  public static void checkBoard(Board board, Integer[][] scores, String more){
    int expectRows = scores.length, expectCols = scores[0].length;
    assertEquals(board.getRows(), expectRows);
    assertEquals(board.getCols(), expectCols);

    int expectTileCount = 0;
    String expectBoardString = arraysToString(scores);

    for(int row=0; row<expectRows; row++){
      for(int col=0; col<expectCols; col++){
        Tile tile = board.tileAt(row,col);
        Integer expect = scores[row][col];
        if(expect != null){ expectTileCount++; }
        if(expect==null && tile==null){ continue; }
        if((expect == null && tile!=null) ||
           (expect != null && tile==null) ||
           (!expect.equals(tile.getScore()))){
          String msg = 
            String.format("Tile at (%d, %d) is wrong score\nExpect Board:\n%sActual Board:\n%s%s",
                          row,col,expectBoardString,board,more);
          fail(msg);
        }
      }
    }
    int expectFreeSpaceCount = expectRows*expectCols - expectTileCount;
    assertEquals(expectTileCount, board.getTileCount());
    assertEquals(expectFreeSpaceCount, board.getFreeSpaceCount());
  }
  public static void checkBoard(Board board, Integer[][] scores){
    checkBoard(board, scores, "");
  }


  ////////////////////////////////////////////////////////////////////////////////
  // DenseBoard


  // Constructors, sizes and no tiles, all free space
  @Test(timeout=100)
  public void denseBoardConstructor1(){
    Board board = new DenseBoard(4,5);
    Integer scores[][] = new Integer[4][5];
    checkBoard(board, scores);
    assertFalse(board.lastShiftMovedTiles());
  }
  @Test(timeout=100)
  public void denseBoardConstructor2(){
    Board board = new DenseBoard(1,10);
    Integer scores[][] = new Integer[1][10];
    checkBoard(board, scores);
    assertFalse(board.lastShiftMovedTiles());
  }

  // Second constructor tests
  @Test(timeout=100)
  public void denseBoardConstructor3(){
    Integer scores[][] = {
      {null, null, null},
      {null,    4,    8},
      {null, null,   16},
      {   2,    4, null},
    };
    Tile tiles[][] = tilesFromIntegers(scores);    
    Board board = new DenseBoard(tiles);
    checkBoard(board, scores);
    assertFalse(board.lastShiftMovedTiles());
  }
  @Test(timeout=100)
  public void denseBoardConstructor4(){
    Integer scores[][] = {
      {8,  8,  8,  16,  32,  64,  2},  
      {8,  8,  2,  8,   32,  64,  2},  
      {4,  8,  8,  32,  64,  16,  2},  
    };
    Tile tiles[][] = tilesFromIntegers(scores);    
    Board board = new DenseBoard(tiles);
    checkBoard(board, scores);
    assertFalse(board.lastShiftMovedTiles());
  }
  @Test(timeout=100)
  public void denseBoardConstructor5(){
    Integer scores[][] = {
      {null, null, null},
      {null, null, null},
      {null, null, null},
      {null, null, null},
      {null, null, null},
      {null, null, null},
    };
    Tile tiles[][] = tilesFromIntegers(scores);    
    Board board = new DenseBoard(tiles);
    checkBoard(board, scores);
    assertFalse(board.lastShiftMovedTiles());
  }

  // Make sure that the constructor does not re-use the given 2d array of tiles
  @Test(timeout=100)
  public void denseBoardConstructorDistinct(){
    Integer scores[][] = {
      {null, null, null},
      {null,    4,    8},
      {null, null,   16},
      {   2,    4, null},
    };
    Tile tiles[][] = tilesFromIntegers(scores);    
    Board board = new DenseBoard(tiles);
    tiles[0][0] = new TwoNTile(2);
    tiles[1][0] = new TwoNTile(4);
    checkBoard(board, scores);
    assertFalse(board.lastShiftMovedTiles());
  }


  // tileAt raises exceptions, not index out of bounds
  @Test(timeout=100)
  public void tileAtGoodExceptions(){
    String msg = "Check the boundaries of access in DenseBoard.tileAt(i,j) and throw an exception with an informative error message";
    try{
      Integer scores[][] = {
        {null, null, null},
        {null,    4,    8},
        {null, null,   16},
        {   2,    4, null},
      };
      Tile tiles[][] = tilesFromIntegers(scores);    
      Board board = new DenseBoard(tiles);
      Tile tile = board.tileAt(20,30);
      fail(msg);
    }
    catch(ArrayIndexOutOfBoundsException e) { fail(msg); }
    catch(IndexOutOfBoundsException e)      { fail(msg); }
    catch(Exception e)                      {} // Other kinds okay
  }

  // Adding at free spaces
  @Test(timeout=100)
  public void addTileAtFreeSpace1(){
    Integer before[][] = {
      {null, null, null},
      {null,    4,    8},
      {null, null,   16},
      {   2,    4, null},
    };
    Integer after[][] = {
      {   2, null, null},
      {null,    4,    8},
      {null, null,   16},
      {   2,    4, null},
    };
    int freeL = 0;
    Tile tile = new TwoNTile(2);

    Board board = new DenseBoard(tilesFromIntegers(before));
    board.addTileAtFreeSpace(freeL, tile);
    String more = String.format("Adding %s at free space %s",
                                tile,freeL);
    checkBoard(board,after,more);
  }
  @Test(timeout=100)
  public void addTileAtFreeSpace2(){
    Integer before[][] = {
      {  null, null, null, null, },
      {  null,    4, null, null, },
      {    16,    2, null,    2, },
      {     8,    8,    4,    4, },
    };
    Integer after[][] = {
      {  null, null, null, null, },
      {  null,    4, null,   32, },
      {    16,    2, null,    2, },
      {     8,    8,    4,    4, },
    };
    int freeL = 6;
    Tile tile = new TwoNTile(32);

    Board board = new DenseBoard(tilesFromIntegers(before));
    board.addTileAtFreeSpace(freeL, tile);
    String more = String.format("Adding %s at free space %s",
                                tile,freeL);
    checkBoard(board,after,more);
  }
  @Test(timeout=100)
  public void addTileAtFreeSpace3(){
    Integer before[][] = {
      {  null, null, null, null, },
      {  null,    4, null, null, },
      {    16,    2, null,    2, },
      {     8,    8,    4,    4, },
    };
    Integer after[][] = {
      {  null, null, null, null, },
      {  null,    4, null, null, },
      {    16,    2,   32,    2, },
      {     8,    8,    4,    4, },
    };
    int freeL = 7;
    Tile tile = new TwoNTile(32);

    Board board = new DenseBoard(tilesFromIntegers(before));
    board.addTileAtFreeSpace(freeL, tile);
    String more = String.format("Adding %s at free space %s",
                                tile,freeL);
    checkBoard(board,after,more);
  }
  @Test(timeout=100)
  public void addTileAtFreeSpace4(){
    Integer before[][] = {
      { 2,    2,},
      { 2,    2,},
      { 2,    2,},
      { 2,    2,},
      { 2, null,},
    };
    Integer after[][] = {
      { 2,    2,},
      { 2,    2,},
      { 2,    2,},
      { 2,    2,},
      { 2,    4,},
    };
    int freeL = 0;
    Tile tile = new TwoNTile(4);

    Board board = new DenseBoard(tilesFromIntegers(before));
    board.addTileAtFreeSpace(freeL, tile);
    String more = String.format("Adding %s at free space %s",
                                tile,freeL);
    checkBoard(board,after,more);
  }
  @Test(timeout=100)
  public void addTileAtFreeSpace5(){
    Integer before[][] = {
      {   2,    2,},
      {   2,    2,},
      {   2, null,},
      {null,    2,},
      {   2, null,},
    };
    Integer after[][] = {
      {   2,    2,},
      {   2,    2,},
      {   2,    4,},
      {null,    2,},
      {   2, null,},
    };
    int freeL = 0;
    Tile tile = new TwoNTile(4);

    Board board = new DenseBoard(tilesFromIntegers(before));
    board.addTileAtFreeSpace(freeL, tile);
    String more = String.format("Adding %s at free space %s",
                                tile,freeL);
    checkBoard(board,after,more);
  }
  @Test(timeout=100)
  public void addTileAtFreeSpace6(){
    Integer before[][] = {
      {   2,    2,},
      {   2,    2,},
      {   2, null,},
      {null,    2,},
      {   2, null,},
    };
    Integer after[][] = {
      {   2,    2,},
      {   2,    2,},
      {   2, null,},
      {   4,    2,},
      {   2, null,},
    };
    int freeL = 1;
    Tile tile = new TwoNTile(4);

    Board board = new DenseBoard(tilesFromIntegers(before));
    board.addTileAtFreeSpace(freeL, tile);
    String more = String.format("Adding %s at free space %s",
                                tile,freeL);
    checkBoard(board,after,more);
  }

  // No free space, should throw an exception on a request
  @Test(timeout=100)
  public void addTileAtFreeSpaceExceptions(){
    String msg = "Throw an appropriate exception on a request to add a tile to full board";
    Integer
      before[][] = {
      { 2,    2,},
      { 2,    2,},
      { 2,    2,},
      { 2,    2,},
      { 2,    2,},
    };
    int freeL = 0;
    Tile tile = new TwoNTile(4);

    Board board = new DenseBoard(tilesFromIntegers(before));

    try{
      board.addTileAtFreeSpace(freeL, tile);
      fail(msg);
    }
    catch(ArrayIndexOutOfBoundsException e) { fail(msg); }
    catch(IndexOutOfBoundsException e)      { fail(msg); }
    catch(Exception e)                      {} // Other kinds okay
  }
    
  // Test whether merges are possible
  @Test(timeout=100)
  public void mergePossible1(){
    Integer scores[][] = {
      {  null, null, null, null, },
      {  null,    4, null, null, },
      {    16,    2, null,    2, },
      {     8,    8,    4,   32, },
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "Left/Right shift merges";
    boolean expect = true, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible2(){
    Integer scores[][] = {
      {  null, null, null, },
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "No shift merges";
    boolean expect = false, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible3(){
    Integer scores[][] = {
      {  null, null, null, null, },
      {  null,    4, null, null, },
      {    16,    2,    8,    2, },
      {     2,    8,    2,    4, },
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "No shift merges";
    boolean expect = false, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible4(){
    Integer scores[][] = {
      {  2, 4, 2, 4, 2, 8},
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "No shift merges";
    boolean expect = false, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible5(){
    Integer scores[][] = {
      {  2, 4, 2, 4, 4, 8},
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "Left/right shift merges";
    boolean expect = true, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible6(){
    Integer scores[][] = {
      {  2, null, 2, 4, 16, 8},
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "Left/right shift merges";
    boolean expect = true, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }  
  @Test(timeout=100)
  public void mergePossible7(){
    Integer scores[][] = {
      { 2, },
      { 4, },
      { 8, },
      { 2, },
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "No shift merges";
    boolean expect = false, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible8(){
    Integer scores[][] = {
      { 2, },
      { 2, },
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "Up/Down shift merges";
    boolean expect = true, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible9(){
    Integer scores[][] = {
      {  2, 4, 2, 4, 2, 8},
      {  4, 2, 4, 2, 4, 2},
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "No shift merges";
    boolean expect = false, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible10(){
    Integer scores[][] = {
      {  2, 4, 2, 4, 2, 8},
      {  2, 2, 4, 2, 4, 2},
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "Left/Right/Up/Down shift merges";
    boolean expect = true, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible11(){
    Integer scores[][] = {
      {    2, null, null},
      { null, null,    2},
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "No shift merges";
    boolean expect = false, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  @Test(timeout=100)
  public void mergePossible12(){
    Integer scores[][] = {
      {    2, }
    };
    Board board = new DenseBoard(tilesFromIntegers(scores));
    String msg = "No shift merges";
    boolean expect = false, actual = board.mergePossible();
    assertEquals(msg, expect, actual);
  }
  


  //////////////////////////////
  // SHIFT TESTS

  // Utility to do a shift on a board based a string argument for the
  // direction
  public static int doShift(Board board, String shift){
    switch(shift){ 
      case  "up":     return board.shiftUp();     
      case  "down":   return board.shiftDown();   
      case  "left":   return board.shiftLeft();   
      case  "right":  return board.shiftRight();  
      default: throw new RuntimeException("Something is terribly wrong.");
    }
  }

  // convert an example to an array
  // gawk '{printf("{"); for(i=1; i<=NF; i++){ if($i=="-"){printf(" null,");}else{printf(" %4d,",$i); }} print("},"); }'

  // convert a stringy board into a printable string, use with shell-command-on-region
  // gawk '{printf("\"%s\\n\"+\n",$0); } END{print "\"\";";}'

  // **********************************************************************
  // No merging
  @Test(timeout=100) public void shiftNoMerge1(){
    Integer before[][] = {
      { null, null, null, null, null,},
      { null, null,    4, null,    2,},
      { null, null, null,    2,    4,},
    };
    String shift = "up"; int expectScore = 0;
    Integer after[][] = {
      { null, null,    4,    2,    2,},
      { null, null, null, null,    4,},
      { null, null, null, null, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }

  @Test(timeout=100) public void shiftNoMerge2(){
    Integer before[][] = {
      { null, null, null, null, null,},
      { null,    8, null, null,    2,},
      {    8,   32,    4,    2, null,},
    };
    String shift = "left"; int expectScore = 0;
    Integer after[][] = {
      { null, null, null, null, null,},
      {    8,    2, null, null, null,},
      {    8,   32,    4,    2, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftNoMerge3(){
    Integer before[][] = {
      {    4,   16,    2, null, null,},
      {   32,    8, null,    2, null,},
      {    4,    8,    4, null,    2,},
    };
    String shift = "right"; int expectScore = 0;
    Integer after[][] = {
      { null, null,    4,   16,    2,},
      { null, null,   32,    8,    2,},
      { null,    4,    8,    4,    2,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftNoMerge4(){
    Integer before[][] = {
      { null,    2,},
      { null, null,},
      { null, null,},
      {    2,    4,},
    };
    String shift = "up"; int expectScore = 0;
    Integer after[][] = {
      {    2,    2,},
      { null,    4,},
      { null, null,},
      { null, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftNoMerge5(){
    Integer before[][] = {
      { null, null,},
      {    2, null,},
      { null,    4,},
      {    2,    8,},
    };
    String shift = "left"; int expectScore = 0;
    Integer after[][] = {
      { null, null,},
      {    2, null,},
      {    4, null,},
      {    2,    8,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  // @Test(timeout=100) public void shiftNoMerge2(){
  //   Integer before[][] = {
  //   };
  //   String shift = ""; int expectScore = 0;
  //   Integer after[][] = {
  //   };
  //   Board board = new DenseBoard(tilesFromIntegers(before));
  //   String initialString = board.toString();
  //   int actualScore = doShift(board, shift);
  //   String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
  //   checkBoard(board,after,more);
  //   assertEquals(expectScore, actualScore);
  // }


  // **********************************************************************
  // Shifts with merges but no conflicts
  @Test(timeout=100) public void shiftWithMerge1(){
    Integer before[][] = {
      {    2, null, null,    2, null,},
    };
    String shift = "left"; int expectScore = 4;
    Integer after[][] = {
      {    4, null, null, null, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMerge2(){
    Integer before[][] = {
      {    4,    2,    4,    2,    2,},
    };
    String shift = "right"; int expectScore = 4;
    Integer after[][] = {
      { null,    4,    2,    4,    4,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMerge3(){
    Integer before[][] = {
      {    2, null, null,},
      { null, null,    2,},
      { null, null, null,},
      {    2, null,    2,},
      { null, null, null,},
      { null, null, null,},
    };
    String shift = "down"; int expectScore = 8;
    Integer after[][] = {
      { null, null, null,},
      { null, null, null,},
      { null, null, null,},
      { null, null, null,},
      { null, null, null,},
      {    4, null,    4,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMerge4(){
    Integer before[][] = {
      { null, null, null,},
      { null, null, null,},
      { null, null, null,},
      { null, null, null,},
      { null, null, null,},
      {    4, null,    4,},
    };
    String shift = "left"; int expectScore = 8;
    Integer after[][] = {
      { null, null, null,},
      { null, null, null,},
      { null, null, null,},
      { null, null, null,},
      { null, null, null,},
      {    8, null, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMerge5(){
    Integer before[][] = {
      {    4,    2, null, null,},
      {    8,    2, null, null,},
      {    2,   16, null, null,},
      {   32,   16,    8,    2,},
    };
    String shift = "down"; int expectScore = 36;
    Integer after[][] = {
      {    4, null, null, null,},
      {    8, null, null, null,},
      {    2,    4, null, null,},
      {   32,   32,    8,    2,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMerge6(){
    Integer before[][] = {
      {    4, null, null, null,},
      {    8, null, null, null,},
      {    2,    4, null, null,},
      {   32,   32,    8,    2,},
    };
    String shift = "left"; int expectScore = 64;
    Integer after[][] = {
      {    4, null, null, null,},
      {    8, null, null, null,},
      {    2,    4, null, null,},
      {   64,    8,    2, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMerge7(){
    Integer before[][] = {
      {    8,    8,    4,    2,},
      {   16, null,    8,    2,},
      { null, null, null, null,},
      { null, null, null, null,},
    };
    String shift = "down"; int expectScore = 4;
    Integer after[][] = {
      { null, null, null, null,},
      { null, null, null, null,},
      {    8, null,    4, null,},
      {   16,    8,    8,    4,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  // @Test(timeout=100) public void shiftWithMerge1(){
  //   Integer before[][] = {
  //   };
  //   String shift = ""; int expectScore = 0;
  //   Integer after[][] = {
  //   };
  //   Board board = new DenseBoard(tilesFromIntegers(before));
  //   String initialString = board.toString();
  //   int actualScore = doShift(board, shift);
  //   String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
  //   checkBoard(board,after,more);
  //   assertEquals(expectScore, actualScore);
  // }

  // **********************************************************************
  // Shifts with Merges and conflicts to resolve
  @Test(timeout=100) public void shiftWithMergeConflicts1(){
    Integer before[][] = {
      { null, null, null, null,},
      {    2, null,    2,    2,},
      { null, null, null,    2,},
      { null, null, null, null,},
    };
    String shift = "left"; int expectScore = 4;
    Integer after[][] = {
      { null, null, null, null,},
      {    4,    2, null, null,},
      {    2, null, null, null,},
      { null, null, null, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMergeConflicts2(){
    Integer before[][] = {
      {    2, null, null, null,},
      {    2, null, null, null,},
      {    2,    8, null, null,},
      {   16, null, null, null,},
    };
    String shift = "down"; int expectScore = 4;
    Integer after[][] = {
      { null, null, null, null,},
      {    2, null, null, null,},
      {    4, null, null, null,},
      {   16,    8, null, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMergeConflicts3(){
    Integer before[][] = {
      { null, null, null,    4,},
      { null, null,    2,    2,},
      { null,    8,    8,    8,},
      {   64,   16,    4,    4,},
    };
    String shift = "left"; int expectScore = 28;
    Integer after[][] = {
      {    4, null, null, null,},
      {    4, null, null, null,},
      {   16,    8, null, null,},
      {   64,   16,    8, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMergeConflicts4(){
    Integer before[][] = {
      {    2,    2,    2,    2,    2,},
    };
    String shift = "left"; int expectScore = 8;
    Integer after[][] = {
      {    4,    4,    2, null, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  @Test(timeout=100) public void shiftWithMergeConflicts5(){
    Integer before[][] = {
      {    2,    2,    4,    2,    2,},
    };
    String shift = "right"; int expectScore = 8;
    Integer after[][] = {
      { null, null,    4,    4,    4,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
  }
  // @Test(timeout=100) public void shiftWithMergeConflicts1(){
  //   Integer before[][] = {
  //   };
  //   String shift = ""; int expectScore = 0;
  //   Integer after[][] = {
  //   };
  //   Board board = new DenseBoard(tilesFromIntegers(before));
  //   String initialString = board.toString();
  //   int actualScore = doShift(board, shift);
  //   String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
  //   checkBoard(board,after,more);
  //   assertEquals(expectScore, actualScore);
  // }

  // **********************************************************************
  // Test to see if tiles move during shift, call to
  // lastShiftMovedTiles() works correctly
  @Test(timeout=100) public void shiftMovedTiles1(){
    Integer before[][] = {
      { null, null, null,    2,},
      { null, null, null, null,},
      { null, null, null,    2,},
      { null,    2,    4,    8,},
    };
    String shift = "right"; int expectScore = 0;
    boolean expectMoved = false;
    Integer after[][] = {
      { null, null, null,    2,},
      { null, null, null, null,},
      { null, null, null,    2,},
      { null,    2,    4,    8,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  @Test(timeout=100) public void shiftMovedTiles2(){
    Integer before[][] = {
      { null, null, null,    8,},
      { null, null, null, null,},
      { null, null, null,    4,},
      {    4,    2,    4,    8,},
    };
    String shift = "up"; int expectScore = 0;
    boolean expectMoved = true;
    Integer after[][] = {
      {    4,    2,    4,    8,},
      { null, null, null,    4,},
      { null, null, null,    8,},
      { null, null, null, null,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  @Test(timeout=100) public void shiftMovedTiles3(){
    Integer before[][] = {
      { null, null, null, null,},
      { null, null, null, null,},
      {    2, null, null,    4,},
      {    2,    2,    4,    8,},
    };
    String shift = "down"; int expectScore = 4;
    boolean expectMoved = true;
    Integer after[][] = {
      { null, null, null, null,},
      { null, null, null, null,},
      { null, null, null,    4,},
      {    4,    2,    4,    8,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  @Test(timeout=100) public void shiftMovedTiles4(){
    Integer before[][] = {
      { null, null, null, null,},
      { null, null, null,    8,},
      { null,    2, null,    4,},
      {    4,    4,    4,    8,},
    };
    String shift = "right"; int expectScore = 8;
    boolean expectMoved = true;
    Integer after[][] = {
      { null, null, null, null,},
      { null, null, null,    8,},
      { null, null,    2,    4,},
      { null,    4,    8,    8,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  @Test(timeout=100) public void shiftMovedTiles5(){
    Integer before[][] = {
      { null, },
      { null, },
      { null, },
      { null, },
    };
    String shift = "left"; int expectScore = 0;
    boolean expectMoved = false;
    Integer after[][] = {
      { null, },
      { null, },
      { null, },
      { null, },
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  @Test(timeout=100) public void shiftMovedTiles6(){
    Integer before[][] = {
      { null, },
      { null, },
      { null, },
      { null, },
    };
    String shift = "up"; int expectScore = 0;
    boolean expectMoved = false;
    Integer after[][] = {
      { null, },
      { null, },
      { null, },
      { null, },
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  @Test(timeout=100) public void shiftMovedTiles7(){
    Integer before[][] = {
      { null, },
      { null, },
      { null, },
      {    2, },
    };
    String shift = "down"; int expectScore = 0;
    boolean expectMoved = false;
    Integer after[][] = {
      { null, },
      { null, },
      { null, },
      {    2, },
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  @Test(timeout=100) public void shiftMovedTiles8(){
    Integer before[][] = {
      { null, null, null, 2, },
    };
    String shift = "right"; int expectScore = 0;
    boolean expectMoved = false;
    Integer after[][] = {
      { null, null, null, 2, },
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  @Test(timeout=100) public void shiftMovedTiles9(){
    Integer before[][] = {
      { null, null, null, 2, },
    };
    String shift = "up"; int expectScore = 0;
    boolean expectMoved = false;
    Integer after[][] = {
      { null, null, null, 2, },
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  @Test(timeout=100) public void shiftMovedTiles10(){
    Integer before[][] = {
      { null, null, null, 2, },
    };
    String shift = "down"; int expectScore = 0;
    boolean expectMoved = false;
    Integer after[][] = {
      { null, null, null, 2, },
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
  }
  // @Test(timeout=100) public void shiftMovedTiles(){
  //   Integer before[][] = {
  //   };
  //   String shift = ""; int expectScore = 0;
  //   boolean expectMoved = false;
  //   Integer after[][] = {
  //   };
  //   Board board = new DenseBoard(tilesFromIntegers(before));
  //   String initialString = board.toString();
  //   int actualScore = doShift(board, shift);
  //   String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
  //   checkBoard(board,after,more);
  //   assertEquals(expectScore, actualScore);
  //   boolean actualMoved = board.lastShiftMovedTiles();
  //   String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
  //                              expectMoved,actualMoved,more,board);
  //   assertEquals(msg,expectMoved,actualMoved);
  // }

  
  // Copy method 
  // Copy is distinct from original
  @Test(timeout=100) public void copy1(){
    Integer before[][] = {
      { null, null, null,    2,},
      { null, null, null, null,},
      { null, null, null,    2,},
      { null,    2,    4,    8,},
    };
    String shift = "down"; int expectScore = 4;
    boolean expectMoved = true;
    Integer after[][] = {
      { null, null, null, null,},
      { null, null, null, null,},
      { null, null, null,    4,},
      { null,    2,    4,    8,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    Board boardCopy = board.copy();
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
    checkBoard(boardCopy,before,"Board copy changed state with the original, should not have");
    assertFalse(boardCopy.lastShiftMovedTiles());    
  }
  @Test(timeout=100) public void copy2(){
    Integer before[][] = {
      { null, null, null,    2,},
      { null, null, null, null,},
      { null, null, null,    2,},
      { null,    2,    4,    8,},
    };
    String shift = "down"; int expectScore = 4;
    boolean expectMoved = true;
    Integer after[][] = {
      { null, null, null, null,},
      { null, null, null, null,},
      { null, null, null,    4,},
      { null,    2,    4,    8,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    String initialString = board.toString();
    int actualScore = doShift(board, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(board,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = board.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,board);
    assertEquals(msg,expectMoved,actualMoved);
    Board boardCopy = board.copy();
    actualScore = doShift(board,"down");
    assertTrue(boardCopy.lastShiftMovedTiles());    
  }
  @Test(timeout=100) public void copy3(){
    Integer before[][] = {
      { null, null, null,    2,},
      { null, null, null, null,},
      { null, null, null,    2,},
      { null,    2,    4,    8,},
    };
    String shift = "down"; int expectScore = 4;
    boolean expectMoved = true;
    Integer after[][] = {
      { null, null, null, null,},
      { null, null, null, null,},
      { null, null, null,    4,},
      { null,    2,    4,    8,},
    };
    Board board = new DenseBoard(tilesFromIntegers(before));
    Board boardCopy = board.copy();
    String initialString = board.toString();
    int actualScore = doShift(boardCopy, shift);
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkBoard(boardCopy,after,more);
    assertEquals(expectScore, actualScore);
    boolean actualMoved = boardCopy.lastShiftMovedTiles();
    String msg = String.format("ExpectMoved: %s\nActualMoved: %s\n%s\nFinal Board:\n%s",
                               expectMoved,actualMoved,more,boardCopy);
    assertEquals(msg,expectMoved,actualMoved);
    checkBoard(board,before,"Board original changed state with the copy, should not have");
    assertFalse(board.lastShiftMovedTiles());    
  }
  

  // ************************************************************
  // Test that toString formats correctly
  @Test(timeout=100) public void testToString1(){
    Integer scores[][] = {
      { null, null, null,    2,},
      { null, null, null, null,},
      { null, null, null,    2,},
      { null,    2,    4,    8,},
    };
    String expect =
      "   -    -    -    2 \n"+
      "   -    -    -    - \n"+
      "   -    -    -    2 \n"+
      "   -    2    4    8 \n"+
      "";
    String actual = (new DenseBoard(tilesFromIntegers(scores))).toString();
    assertEquals(expect,actual);
  }
  @Test(timeout=100) public void testToString2(){
    Integer scores[][] = {
      {    2, null, null,},
      { null, null,    2,},
      { null, null, null,},
      {    2, null,    2,},
      { null, null, null,},
      { null, null, null,},
    };
    String expect =
      "   2    -    - \n"+
      "   -    -    2 \n"+
      "   -    -    - \n"+
      "   2    -    2 \n"+
      "   -    -    - \n"+
      "   -    -    - \n"+
      "";
    String actual = (new DenseBoard(tilesFromIntegers(scores))).toString();
    assertEquals(expect,actual);
  }
  @Test(timeout=100) public void testToString3(){
    Integer scores[][] = {
      {    4,    2,    4,    2,    2,},
    };
    String expect =
      "   4    2    4    2    2 \n"+
      "";
    String actual = (new DenseBoard(tilesFromIntegers(scores))).toString();
    assertEquals(expect,actual);
  }
  @Test(timeout=100) public void testToString4(){
    Integer scores[][] = {
      { null, null,},
      {    2, null,},
      { null,    4,},
      {    2,    8,},
    };
    String expect =
      "   -    - \n"+
      "   2    - \n"+
      "   -    4 \n"+
      "   2    8 \n"+
      "";
    String actual = (new DenseBoard(tilesFromIntegers(scores))).toString();
    assertEquals(expect,actual);
  }
  @Test(timeout=100) public void testToString5(){
    Integer scores[][] = {
      {    4,   16,    2, null, null,},
      {   32,    8, null,    2, null,},
      {    4,    8,    4, null,    2,},
    };
    String expect =
      "   4   16    2    -    - \n"+
      "  32    8    -    2    - \n"+
      "   4    8    4    -    2 \n"+
      "";
    String actual = (new DenseBoard(tilesFromIntegers(scores))).toString();
    assertEquals(expect,actual);
  }
  @Test(timeout=100) public void testToString6(){
    Integer scores[][] = {
      { null, null, null, null, null,},
      { null,    8, null, null,    2,},
      {    8,   32,    4,    2, null,},
    };
    String expect =
      "   -    -    -    -    - \n"+
      "   -    8    -    -    2 \n"+
      "   8   32    4    2    - \n"+
      "";
    String actual = (new DenseBoard(tilesFromIntegers(scores))).toString();
    assertEquals(expect,actual);
  }
  // @Test(timeout=100) public void testToString(){
  //   Integer scores[][] = {
  //   };
  //   String expect =
  //   String actual = (new DenseBoard(tilesFromIntegers(scores))).toString();
  //   assertEquals(expect,actual);
  // }

  // Boards should be able to hold arbitrary Tiles
  @Test(timeout=100) public void boardCanHoldTile(){  
    Board board = new DenseBoard(5,4);
    Tile dummy = new DummyTile();
    Tile dummy4 = new DummyTile4();
    board.addTileAtFreeSpace(0,dummy);
    board.addTileAtFreeSpace(0,dummy4);
    Tile result = board.tileAt(0,0);
    if(!(result instanceof DummyTile)){
      fail("Expected a DummyTile at 0 0");
    }
    Tile result4 = board.tileAt(0,1);
    if(!(result4 instanceof DummyTile)){
      fail("Expected a DummyTile4 at 0 1");
    }
  }


  ////////////////////////////////////////////////////////////////////////////////
  // Game2048

  // Utility to assert that a Game has a certain layout of tile
  // scores, check sizes and tile counts
  public static void checkGame(Game2048 game, Integer[][] scores, String more){
    int expectRows = scores.length, expectCols = scores[0].length;
    assertEquals(game.getRows(), expectRows);
    assertEquals(game.getCols(), expectCols);

    int expectTileCount = 0;
    String expectGameString = arraysToString(scores);

    for(int row=0; row<expectRows; row++){
      for(int col=0; col<expectCols; col++){
        Tile tile = game.tileAt(row,col);
        Integer expect = scores[row][col];
        if(expect != null){ expectTileCount++; }
        if(expect==null && tile==null){ continue; }
        if((expect == null && tile!=null) ||
           (expect != null && tile==null) ||
           (!expect.equals(tile.getScore()))){
          String msg = 
            String.format("Tile at (%d, %d) is wrong score\nExpect board:\n%sActual board:\n%s%s",
                          row,col,expectGameString,game.boardString(),more);
          fail(msg);
        }
      }
    }
  }
  public static void checkGame(Game2048 game, Integer[][] scores){
    checkGame(game, scores, "");
  }

  // Utility to do a shift on a game based a string argument for the
  // direction
  public static void doShift(Game2048 game, String shift){
    switch(shift){ 
      case "up":     game.shiftUp();     break;   // case "u": 
      case "down":   game.shiftDown();   break;   // case "d": 
      case "left":   game.shiftLeft();   break;   // case "l": 
      case "right":  game.shiftRight();  break;   // case "r": 
      default: throw new RuntimeException("Something is terribly wrong.");
    }
  }


  // Constructors and accessors
  @Test(timeout=100) public void game2048_empty_constructor1(){
    int rows=4, cols=5, seed=12345, score=0;
    Game2048 game = new Game2048(rows,cols,seed);
    assertEquals(rows,  game.getRows());
    assertEquals(cols,  game.getCols());
    assertEquals(score, game.getScore());
    assertFalse(game.lastShiftMovedTiles());
  }
  @Test(timeout=100) public void game2048_tile_constructor1(){
    int seed=12345, score=0;
    Integer before[][] = {
      {  null, null,    2, null, },
      {  null,    4, null, null, },
      {    16,    2, null,    2, },
      {     8,    8,    4,    4, },
    };
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles,seed);
    checkGame(game,before);
    assertEquals(score, game.getScore());
    assertFalse(game.lastShiftMovedTiles());
  }

  // Methods present: shifts, boardString 
  @Test(timeout=100) public void game_shift1(){
    int seed=12345;
    Integer before[][] = {
      { null,    4, null, null,   4,},
      {    2,    2,    2, null,   2,},
      { null,    8,    8, null,   8,},
      {   64,   16,    4, null,   4,},
    };
    String shift = "left"; int expectScore = 40;
    Integer after[][] = {
      {    8, null, null, null, null,},
      {    4,    4, null, null, null,},
      {   16,    8, null, null, null,},
      {   64,   16,    8, null, null,},
    };
    Game2048 game = new Game2048(tilesFromIntegers(before),seed);
    String initialString = game.boardString();
    doShift(game, shift);
    int actualScore = game.getScore();
    String more = String.format("Initial:\n%sShift: %s",initialString,shift.toUpperCase());
    checkGame(game,after,more);
    assertEquals(expectScore, actualScore);
    assertTrue(game.lastShiftMovedTiles());
  }

  // Check randomFreeLocation by checking sequence of free locations generated
  @Test(timeout=100) public void game_randomFreeLocation1(){
    int expect[] = new int[]{11, 0, 1, 8, 15, 4, 15, 2, 1, 9};
    int rows=4, cols=5, seed=12345, score=0;
    Game2048 game = new Game2048(rows,cols,seed);
    int actual[] = new int[expect.length];
    for(int i=0; i<expect.length; i++){
      actual[i] = game.randomFreeLocation(); 
    }
    String msg = 
      String.format("Free location sequence mismatch\nExpect: %s\nActual: %s\n",
                    Arrays.toString(expect),Arrays.toString(actual));
    assertArrayEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_randomFreeLocation2(){
    int expect[] = new int[]{42, 34, 210, 101, 221, 230, 170, 146, 20, 72};
    int rows=9, cols=30, seed=9433, score=0;
    Game2048 game = new Game2048(rows,cols,seed);
    int actual[] = new int[expect.length];
    for(int i=0; i<expect.length; i++){
      actual[i] = game.randomFreeLocation(); 
    }
    String msg = 
      String.format("Free location sequence mismatch\nExpect: %s\nActual: %s\n",
                    Arrays.toString(expect),Arrays.toString(actual));
    assertArrayEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_randomFreeLocation3(){
    int expect[] = new int[]{76, 96, 259, 178, 247, 62, 53, 209, 56, 126};
    int rows=4, cols=67, seed=13, score=0;
    Game2048 game = new Game2048(rows,cols,seed);
    int actual[] = new int[expect.length];
    for(int i=0; i<expect.length; i++){
      actual[i] = game.randomFreeLocation(); 
    }
    String msg = 
      String.format("Free location sequence mismatch\nExpect: %s\nActual: %s\n",
                    Arrays.toString(expect),Arrays.toString(actual));
    assertArrayEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_randomFreeLocation4(){
    int expect[] = new int[]{6, 6, 5, 5, 6, 7, 8, 4, 3, 2};
    int seed=49277, score=0;
    Integer before[][] = {
      { null, null, null, null, null,},
      { null,    8, null, null,    2,},
      {    8,   32,    4,    2, null,},
    };
    Game2048 game = new Game2048(tilesFromIntegers(before), seed);
    int actual[] = new int[expect.length];
    for(int i=0; i<expect.length; i++){
      actual[i] = game.randomFreeLocation(); 
    }
    String msg = 
      String.format("Free location sequence mismatch\nExpect: %s\nActual: %s\n",
                    Arrays.toString(expect),Arrays.toString(actual));
    assertArrayEquals(msg,expect,actual);
  }

  // Check getRandomTile
  // Check randomTile by checking sequence of free locations generated
  @Test(timeout=100) public void game_randomTile1(){
    int expect[] = new int[]{2, 4, 4, 2, 2, 2, 2, 2, 2, 4};
    int rows=4, cols=5, seed=12345, score=0;
    Game2048 game = new Game2048(rows,cols,seed);
    int actual[] = new int[expect.length];
    for(int i=0; i<expect.length; i++){
      actual[i] = ((TwoNTile) game.getRandomTile()).getScore(); 
    }
    String msg = 
      String.format("Free location sequence mismatch\nExpect: %s\nActual: %s\n",
                    Arrays.toString(expect),Arrays.toString(actual));
    assertArrayEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_randomTile2(){
    int expect[] = new int[]{4, 4, 2, 2, 2, 8, 4, 2, 4, 4};
    int rows=9, cols=30, seed=9433, score=0;
    Game2048 game = new Game2048(rows,cols,seed);
    int actual[] = new int[expect.length];
    for(int i=0; i<expect.length; i++){
      actual[i] = ((TwoNTile) game.getRandomTile()).getScore(); 
    }
    String msg = 
      String.format("Free location sequence mismatch\nExpect: %s\nActual: %s\n",
                    Arrays.toString(expect),Arrays.toString(actual));
    assertArrayEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_randomTile3(){
    int expect[] = new int[]{4, 2, 2, 4, 2, 2, 4, 2, 4, 4};
    int rows=4, cols=67, seed=13, score=0;
    Game2048 game = new Game2048(rows,cols,seed);
    int actual[] = new int[expect.length];
    for(int i=0; i<expect.length; i++){
      actual[i] = ((TwoNTile) game.getRandomTile()).getScore(); 
    }
    String msg = 
      String.format("Free location sequence mismatch\nExpect: %s\nActual: %s\n",
                    Arrays.toString(expect),Arrays.toString(actual));
    assertArrayEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_randomTile4(){
    int expect[] = new int[]{2, 2, 4, 4, 2, 2, 2, 2, 2, 4};
    int seed=49277, score=0;
    Integer before[][] = {
      { null, null, null, null, null,},
      { null,    8, null, null,    2,},
      {    8,   32,    4,    2, null,},
    };
    Game2048 game = new Game2048(tilesFromIntegers(before), seed);
    int actual[] = new int[expect.length];
    for(int i=0; i<expect.length; i++){
      actual[i] = ((TwoNTile) game.getRandomTile()).getScore(); 
    }
    String msg = 
      String.format("Free location sequence mismatch\nExpect: %s\nActual: %s\n",
                    Arrays.toString(expect),Arrays.toString(actual));
    assertArrayEquals(msg,expect,actual);
  }

  // Check addRandomTile adds to the correct positions
  @Test(timeout=100) public void game_addRandomTile1(){
    int seed=49277, addCount=1;
    Integer before[][] = {
      { null, null, null, null, null,},
      { null,    8, null, null,    2,},
      {    8,   32,    4,    2, null,},
    };
    String expect = 
      "   -    -    -    -    - \n"+
      "   -    8    2    -    2 \n"+
      "   8   32    4    2    - \n"+
      "";
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    String initial = game.boardString();
    for(int i=0; i<addCount; i++){
      game.addRandomTile();
    }
    String actual = game.boardString();
    String msg = 
      String.format("Adding random tiles went wrong\naddCount: %d\nInitial:\n%sExpect:\n%sActual:\n%s",
                    addCount, initial, expect, actual);
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_addRandomTile2(){
    int seed=70141, addCount=2;
    Integer before[][] = {
      { null, null, null, null, null,},
      { null,    8, null, null,    2,},
      {    8,   32,    4,    2, null,},
    };
    String expect = 
      "   -    -    -    -    - \n"+
      "   2    8    4    -    2 \n"+
      "   8   32    4    2    - \n"+
      "";
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    String initial = game.boardString();
    for(int i=0; i<addCount; i++){
      game.addRandomTile();
    }
    String actual = game.boardString();
    String msg = 
      String.format("Adding random tiles went wrong\naddCount: %d\nInitial:\n%sExpect:\n%sActual:\n%s",
                    addCount, initial, expect, actual);
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_addRandomTile3(){
    int seed=70141, addCount=4;
    Integer before[][] = {
      {null, null, null},
      {null,    4,    8},
      {null, null,   16},
      {   2,    4, null},
    };
    String expect = 
      "   -    2    4 \n"+
      "   2    4    8 \n"+
      "   -    -   16 \n"+
      "   2    4    2 \n"+
      "";
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    String initial = game.boardString();
    for(int i=0; i<addCount; i++){
      game.addRandomTile();
    }
    String actual = game.boardString();
    String msg = 
      String.format("Adding random tiles went wrong\naddCount: %d\nInitial:\n%sExpect:\n%sActual:\n%s",
                    addCount, initial, expect, actual);
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_addRandomTile4(){
    int seed=19, addCount=5;
    Integer before[][] = {
      {null, null, null},
      {null,    4,    8},
      {null, null,   16},
      {   2,    4, null},
    };
    String expect = 
      "   4    4    2 \n"+
      "   -    4    8 \n"+
      "   -    2   16 \n"+
      "   2    4    4 \n"+
      "";
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    String initial = game.boardString();
    for(int i=0; i<addCount; i++){
      game.addRandomTile();
    }
    String actual = game.boardString();
    String msg = 
      String.format("Adding random tiles went wrong\naddCount: %d\nInitial:\n%sExpect:\n%sActual:\n%s",
                    addCount, initial, expect, actual);
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_addRandomTile5(){
    int seed=103979, addCount=1;
    Integer before[][] = {
      { 2,    2,},
      { 2,    2,},
      { 2,    2,},
      { 2,    2,},
      { 2, null,},
    };
    String expect = 
      "   2    2 \n"+
      "   2    2 \n"+
      "   2    2 \n"+
      "   2    2 \n"+
      "   2    8 \n"+
      "";
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    String initial = game.boardString();
    for(int i=0; i<addCount; i++){
      game.addRandomTile();
    }
    String actual = game.boardString();
    String msg = 
      String.format("Adding random tiles went wrong\naddCount: %d\nInitial:\n%sExpect:\n%sActual:\n%s",
                    addCount, initial, expect, actual);
    assertEquals(msg,expect,actual);
  }

  // Check isGameOver
  @Test(timeout=100) public void game_gameOver1(){  
    boolean expect = true;
    Integer before[][] = {
      { 2 }
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver2(){  
    boolean expect = false;
    Integer before[][] = {
      { 2, 2 }
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver3(){  
    boolean expect = false;
    Integer before[][] = {
      { null, 2 }
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver4(){  
    boolean expect = true;
    Integer before[][] = {
      { 2, },
      { 4, },
      { 8, },
      { 2, },
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver5(){  
    boolean expect = false;
    Integer before[][] = {
      { 2, },
      { 4, },
      { 4, },
      { 2, },
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver6(){  
    boolean expect = false;
    Integer before[][] = {
      { 2, },
      { 4, },
      { 8, },
      { null, },
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver7(){  
    boolean expect = false;
    Integer before[][] = {
      {  null, null, null, null, },
      {  null,    4, null, null, },
      {    16,    2,    8,    2, },
      {     2,    8,    2,    4, },
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver8(){  
    boolean expect = true;
    Integer before[][] = {
      {     2,    8,    2,    4, },
      {    32,    4,   16,   64, },
      {    16,    2,    8,    2, },
      {     2,    8,    2,    4, },
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver9(){  
    boolean expect = true;
    Integer before[][] = {
      {  2, 4, 2, 4, 2, 8},
      {  4, 2, 4, 2, 4, 2},
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver10(){  
    boolean expect = false;
    Integer before[][] = {
      {  2, 4, 2, 4, 2, 8},
      {  4, 8, 8, 2, 4, 2},
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }
  @Test(timeout=100) public void game_gameOver11(){  
    boolean expect = false;
    Integer before[][] = {
      {  2, 4, 8, 4, 2, 8},
      {  4, 2, 8, 2, 4, 2},
    };
    int seed=103979;
    Tile tiles[][] = tilesFromIntegers(before);
    Game2048 game = new Game2048(tiles, seed);
    boolean actual = game.isGameOver();
    String msg = 
      String.format("Expect game over %s\nExpect: %s\nActual: %s\nBoard:\n%s",
                    expect,expect,actual,game.boardString());
    assertEquals(msg,expect,actual);
  }

  // Utilities to append columns of strings

  // Append strings as columns using space as the divider
  public static String appendColumns2(String all[]){
    return appendColumns2(all, " ");
  }

  // Append string as columns using the provided divider between lines
  public static String appendColumns2(String all[], String divider){
    String allCols[][] = new String[all.length][];
    int widths[] = new int[all.length];
    int rowCounts[] = new int[all.length];
    for(int col=0; col<all.length; col++){
      widths[col]=1;            // Can't have %0s formats
      allCols[col] = all[col].split("\n");
      for(int row=0; row<allCols[col].length; row++){
        int len = allCols[col][row].length();
        widths[col] = len > widths[col] ? len : widths[col];
      }
    }
    String formats[] = new String[all.length];
    int maxRow = 0;
    for(int col=0; col<all.length; col++){
      String div = col < all.length-1 ? divider : "\n";
      formats[col] = String.format("%%-%ds%s",widths[col],div);
      maxRow = maxRow < allCols[col].length ? allCols[col].length : maxRow;
    }
    StringBuilder sb = new StringBuilder();
    for(int row=0; row<maxRow; row++){
      for(int col=0; col<all.length; col++){
        String fill = "";
        if(row < allCols[col].length){
          fill = allCols[col][row];
        }
        sb.append(String.format(formats[col],fill));
      }
    }
    return sb.toString();
  }


  static final String u = "up";
  static final String d = "down";
  static final String l = "left";
  static final String r = "right";

  // Utility to run a series of moves (shifts) on a Game and check
  // that it's final state is correct
  public static 
    void stressGame(int seed, String moves[], 
                    Integer start[][], String allExpect[]){
    int nmoves = moves.length;
    Tile tiles[][] = tilesFromIntegers(start);
    Game2048 game = new Game2048(tiles, seed);
    String initial = game.boardString();
    StringBuilder errMsg = new StringBuilder();
    Exception excpetion = null;
    boolean fail = false;
    int failMove = -1;
    for(int i=0; i<nmoves && !game.isGameOver(); i++){
      String expect = "";
      if(i < allExpect.length){
        expect = allExpect[i];
      }

      doShift(game, moves[i]);
      if(game.lastShiftMovedTiles()){
        game.addRandomTile();
      }
      String actualDisplay = 
        String.format("ACTUAL move: %d \nshift: %s \nscore: %s \nmoved tiles: %s \ngame over: %s \n%s",
                      i,moves[i],game.getScore(),game.lastShiftMovedTiles(),
                      game.isGameOver(), game.boardString());
      String actualCompare = actualDisplay.replaceAll("ACTUAL","EXPECT");
      String sideBySide = appendColumns2(new String[]{actualDisplay,expect},"|");
      errMsg.append(sideBySide);
      if(!fail && !actualCompare.equals(expect)){
        fail = true;
        failMove = i;
      }
    }
    if(!fail){ return; }        // Didn't fail, done with test
    // Something went wrong, report it
    String msg = 
      String.format("Something wrong at move %d\nInitial:\n%s%s", 
                    failMove,initial,errMsg.toString());
    fail(msg);
  }

  //////////////////////////////////////////////////////////////////////////////////
  // Stress tests: feed game a random set of moves and track each
  // change
  @Test(timeout=100) public void game_stress1(){
    int seed=49277;
    String moves[] = {u};
    Integer start[][] = {
      { null, null, null, null, null,},
      { null,    8, null, null,    2,},
      {    8,   32,    4,    2, null,},
    };
    String expect[] = {
      "EXPECT move: 0 \n"+
      "shift: up \n"+
      "score: 0 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   8    8    4    2    2 \n"+
      "   -   32    -    -    - \n"+
      "   -    -    2    -    - \n"+
      "",
    };
    stressGame(seed, moves, start, expect);
  }
  @Test(timeout=100) public void game_stress2(){
    int seed=103979;
    String moves[] = {u,d,l};
    Integer start[][] = {
      { null, null, null, null, null,},
      { null,    8, null, null,    2,},
      {    8,   32,    4,    2, null,},
    };
    String expect[] = {
      "EXPECT move: 0 \n"+
      "shift: up \n"+
      "score: 0 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   8    8    4    2    2 \n"+
      "   -   32    -    -    - \n"+
      "   -    8    -    -    - \n"+
      "",
      "EXPECT move: 1 \n"+
      "shift: down \n"+
      "score: 0 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    8    -    -    - \n"+
      "   -   32    -    2    - \n"+
      "   8    8    4    2    2 \n"+
      "",
      "EXPECT move: 2 \n"+
      "shift: left \n"+
      "score: 20 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   8    -    -    -    - \n"+
      "  32    2    -    2    - \n"+
      "  16    4    4    -    - \n"+
      "",
    };
    stressGame(seed, moves, start, expect);
  }
  @Test(timeout=100) public void game_stress3(){
    int seed=103979;
    String moves[] = {u,u,d,d,l,r,l,r};
    Integer start[][] = {
      {    4,   16,    2, null, },
      {   32,    8, null,    2, },
      {    4,    8,    4, null, },
      {   32,    8, null,    2, },
    };
    String expect[] = {
      "EXPECT move: 0 \n"+
      "shift: up \n"+
      "score: 20 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4   16    2    4 \n"+
      "  32   16    4    - \n"+
      "   4    8    -    - \n"+
      "  32    -    -    8 \n"+
      "",
      "EXPECT move: 1 \n"+
      "shift: up \n"+
      "score: 52 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4   32    2    4 \n"+
      "  32    8    4    8 \n"+
      "   4    -    2    - \n"+
      "  32    -    -    - \n"+
      "",
      "EXPECT move: 2 \n"+
      "shift: down \n"+
      "score: 52 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    -    -    - \n"+
      "  32    2    2    - \n"+
      "   4   32    4    4 \n"+
      "  32    8    2    8 \n"+
      "",
      "EXPECT move: 3 \n"+
      "shift: down \n"+
      "score: 52 \n"+
      "moved tiles: false \n"+
      "game over: false \n"+
      "   4    -    -    - \n"+
      "  32    2    2    - \n"+
      "   4   32    4    4 \n"+
      "  32    8    2    8 \n"+
      "",
      "EXPECT move: 4 \n"+
      "shift: left \n"+
      "score: 64 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    -    -    - \n"+
      "  32    4    2    - \n"+
      "   4   32    8    - \n"+
      "  32    8    2    8 \n"+
      "",
      "EXPECT move: 5 \n"+
      "shift: right \n"+
      "score: 64 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    -    4 \n"+
      "   -   32    4    2 \n"+
      "   2    4   32    8 \n"+
      "  32    8    2    8 \n"+
      "",
      "EXPECT move: 6 \n"+
      "shift: left \n"+
      "score: 64 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    4    -    - \n"+
      "  32    4    2    - \n"+
      "   2    4   32    8 \n"+
      "  32    8    2    8 \n"+
      "",
      "EXPECT move: 7 \n"+
      "shift: right \n"+
      "score: 72 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    -    8 \n"+
      "   2   32    4    2 \n"+
      "   2    4   32    8 \n"+
      "  32    8    2    8 \n"+
      "",
    };
    stressGame(seed, moves, start, expect);
  }
  // Short game leading to game over
  @Test(timeout=100) public void game_stress4(){
    int seed=19;
    String moves[] = {d,d,l,r};
    Integer start[][] = {
      {    4,   16,    2,   128, },
      {   32,    8,    64,    2, },
      {    4,    8,    4,   256, },
      {   32,    8,  512,    2, },
    };
    String expect[] = {
      "EXPECT move: 0 \n"+
      "shift: down \n"+
      "score: 16 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    2    2  128 \n"+
      "  32   16   64    2 \n"+
      "   4    8    4  256 \n"+
      "  32   16  512    2 \n"+
      "",
      "EXPECT move: 1 \n"+
      "shift: down \n"+
      "score: 16 \n"+
      "moved tiles: false \n"+
      "game over: false \n"+
      "   4    2    2  128 \n"+
      "  32   16   64    2 \n"+
      "   4    8    4  256 \n"+
      "  32   16  512    2 \n"+
      "",
      "EXPECT move: 2 \n"+
      "shift: left \n"+
      "score: 20 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    4  128    4 \n"+
      "  32   16   64    2 \n"+
      "   4    8    4  256 \n"+
      "  32   16  512    2 \n"+
      "",
      "EXPECT move: 3 \n"+
      "shift: right \n"+
      "score: 28 \n"+
      "moved tiles: true \n"+
      "game over: true \n"+
      "   4    8  128    4 \n"+
      "  32   16   64    2 \n"+
      "   4    8    4  256 \n"+
      "  32   16  512    2 \n"+
      "",
    };
    stressGame(seed, moves, start, expect);
  } 
  // Long game leading to game over after 39 moves
  @Test(timeout=100) public void game_stress6(){
    int seed=19;
    String moves[] = {u,d,l,r,u,d,l,r,u,d,l,r,u,d,l,r,u,d,l,r,u,d,l,r,u,d,l,r,u,d,l,r,u,d,l,r,u,d,l};
    Integer start[][] = {
      { null, null, null, null,    2, null, },
      { null, null,    2, null, null, null, },
    };
    String expect[] = {
      "EXPECT move: 0 \n"+
      "shift: up \n"+
      "score: 0 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    2    -    2    - \n"+
      "   -    2    -    -    -    - \n"+
      "",
      "EXPECT move: 1 \n"+
      "shift: down \n"+
      "score: 0 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    -    4    -    - \n"+
      "   -    2    2    -    2    - \n"+
      "",
      "EXPECT move: 2 \n"+
      "shift: left \n"+
      "score: 4 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    -    -    -    -    - \n"+
      "   4    2    -    -    -    4 \n"+
      "",
      "EXPECT move: 3 \n"+
      "shift: right \n"+
      "score: 4 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    -    -    -    4 \n"+
      "   -    -    4    4    2    4 \n"+
      "",
      "EXPECT move: 4 \n"+
      "shift: up \n"+
      "score: 12 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    4    4    2    8 \n"+
      "   -    -    2    -    -    - \n"+
      "",
      "EXPECT move: 5 \n"+
      "shift: down \n"+
      "score: 12 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    4    -    -    - \n"+
      "   -    4    2    4    2    8 \n"+
      "",
      "EXPECT move: 6 \n"+
      "shift: left \n"+
      "score: 12 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    -    -    -    -    4 \n"+
      "   4    2    4    2    8    - \n"+
      "",
      "EXPECT move: 7 \n"+
      "shift: right \n"+
      "score: 20 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    -    -    -    -    8 \n"+
      "   -    4    2    4    2    8 \n"+
      "",
      "EXPECT move: 8 \n"+
      "shift: up \n"+
      "score: 36 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    4    2    4    2   16 \n"+
      "   -    -    -    -    2    - \n"+
      "",
      "EXPECT move: 9 \n"+
      "shift: down \n"+
      "score: 40 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    -    -    4    - \n"+
      "   2    4    2    4    4   16 \n"+
      "",
      "EXPECT move: 10 \n"+
      "shift: left \n"+
      "score: 48 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    -    -    2    -    - \n"+
      "   2    4    2    8   16    - \n"+
      "",
      "EXPECT move: 11 \n"+
      "shift: right \n"+
      "score: 48 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    -    -    4    2 \n"+
      "   2    2    4    2    8   16 \n"+
      "",
      "EXPECT move: 12 \n"+
      "shift: up \n"+
      "score: 48 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    2    4    2    4    2 \n"+
      "   -    -    -    2    8   16 \n"+
      "",
      "EXPECT move: 13 \n"+
      "shift: down \n"+
      "score: 52 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    2    -    4    2 \n"+
      "   2    2    4    4    8   16 \n"+
      "",
      "EXPECT move: 14 \n"+
      "shift: left \n"+
      "score: 64 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    4    2    -    -    2 \n"+
      "   4    8    8   16    -    - \n"+
      "",
      "EXPECT move: 15 \n"+
      "shift: right \n"+
      "score: 84 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    -    -    2    4    4 \n"+
      "   -    -    -    4   16   16 \n"+
      "",
      "EXPECT move: 16 \n"+
      "shift: up \n"+
      "score: 84 \n"+
      "moved tiles: false \n"+
      "game over: false \n"+
      "   2    -    -    2    4    4 \n"+
      "   -    -    -    4   16   16 \n"+
      "",
      "EXPECT move: 17 \n"+
      "shift: down \n"+
      "score: 84 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    2    2    4    4 \n"+
      "   2    -    -    4   16   16 \n"+
      "",
      "EXPECT move: 18 \n"+
      "shift: left \n"+
      "score: 128 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    8    -    -    2    - \n"+
      "   2    4   32    -    -    - \n"+
      "",
      "EXPECT move: 19 \n"+
      "shift: right \n"+
      "score: 128 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    -    4    8    2 \n"+
      "   -    -    2    2    4   32 \n"+
      "",
      "EXPECT move: 20 \n"+
      "shift: up \n"+
      "score: 128 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    2    4    8    2 \n"+
      "   2    -    -    2    4   32 \n"+
      "",
      "EXPECT move: 21 \n"+
      "shift: down \n"+
      "score: 128 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    -    -    4    8    2 \n"+
      "   2    -    2    2    4   32 \n"+
      "",
      "EXPECT move: 22 \n"+
      "shift: left \n"+
      "score: 132 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    4    8    2    2    - \n"+
      "   4    2    4   32    -    - \n"+
      "",
      "EXPECT move: 23 \n"+
      "shift: right \n"+
      "score: 136 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    -    2    4    8    4 \n"+
      "   -    2    4    2    4   32 \n"+
      "",
      "EXPECT move: 24 \n"+
      "shift: up \n"+
      "score: 136 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    2    2    4    8    4 \n"+
      "   -    4    4    2    4   32 \n"+
      "",
      "EXPECT move: 25 \n"+
      "shift: down \n"+
      "score: 136 \n"+
      "moved tiles: false \n"+
      "game over: false \n"+
      "   -    2    2    4    8    4 \n"+
      "   -    4    4    2    4   32 \n"+
      "",
      "EXPECT move: 26 \n"+
      "shift: left \n"+
      "score: 148 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    4    8    4    -    - \n"+
      "   8    2    4   32    2    - \n"+
      "",
      "EXPECT move: 27 \n"+
      "shift: right \n"+
      "score: 156 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    -    -    8    8    4 \n"+
      "   -    8    2    4   32    2 \n"+
      "",
      "EXPECT move: 28 \n"+
      "shift: up \n"+
      "score: 156 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    8    2    8    8    4 \n"+
      "   4    -    -    4   32    2 \n"+
      "",
      "EXPECT move: 29 \n"+
      "shift: down \n"+
      "score: 156 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   2    2    -    8    8    4 \n"+
      "   4    8    2    4   32    2 \n"+
      "",
      "EXPECT move: 30 \n"+
      "shift: left \n"+
      "score: 176 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4   16    4    2    -    - \n"+
      "   4    8    2    4   32    2 \n"+
      "",
      "EXPECT move: 31 \n"+
      "shift: right \n"+
      "score: 176 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   -    4    4   16    4    2 \n"+
      "   4    8    2    4   32    2 \n"+
      "",
      "EXPECT move: 32 \n"+
      "shift: up \n"+
      "score: 180 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    4    4   16    4    4 \n"+
      "   2    8    2    4   32    - \n"+
      "",
      "EXPECT move: 33 \n"+
      "shift: down \n"+
      "score: 180 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   4    4    4   16    4    2 \n"+
      "   2    8    2    4   32    4 \n"+
      "",
      "EXPECT move: 34 \n"+
      "shift: left \n"+
      "score: 188 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   8    4   16    4    2    4 \n"+
      "   2    8    2    4   32    4 \n"+
      "",
      "EXPECT move: 35 \n"+
      "shift: right \n"+
      "score: 188 \n"+
      "moved tiles: false \n"+
      "game over: false \n"+
      "   8    4   16    4    2    4 \n"+
      "   2    8    2    4   32    4 \n"+
      "",
      "EXPECT move: 36 \n"+
      "shift: up \n"+
      "score: 204 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   8    4   16    8    2    8 \n"+
      "   2    8    2    -   32    2 \n"+
      "",
      "EXPECT move: 37 \n"+
      "shift: down \n"+
      "score: 204 \n"+
      "moved tiles: true \n"+
      "game over: false \n"+
      "   8    4   16    2    2    8 \n"+
      "   2    8    2    8   32    2 \n"+
      "",
      "EXPECT move: 38 \n"+
      "shift: left \n"+
      "score: 208 \n"+
      "moved tiles: true \n"+
      "game over: true \n"+
      "   8    4   16    4    8    4 \n"+
      "   2    8    2    8   32    2 \n"+
      "",
    };
    stressGame(seed, moves, start, expect);
  } 
}