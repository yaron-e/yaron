import static org.junit.Assert.*;

import org.junit.Test;


public class TwoNTileTest1 {


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
	    //checkBoard(board,after,more);
	  }
	
	@Test
	 public  void checkBoard(/*Board board, Integer[][] scores, String more*/){
		 Integer before[][] = {
			      {   2,    2,},
			      {   2,    2,},
			      {   2, null,},
			      {null,    2,},
			      {   2, null,},
			    };
			    Integer scores[][] = {
			      {   2,    2,},
			      {   2,    2,},
			      {   2, null,},
			      {   4,    2,},
			      {   2, null,},
			    };
			    int freeL = 1;
			    Tile tile1 = new TwoNTile(4);

			    Board board = new DenseBoard(tilesFromIntegers(before));
			    board.addTileAtFreeSpace(freeL, tile1);
			    String more = String.format("Adding %s at free space %s",
			                                tile1,freeL);
		 
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
		        	if(!expect.equals(tile.getScore()))fail("Failed");
		        	
		          /*String msg = 
		            String.format("Tile at (%d, %d) is wrong score\nExpect Board:\n%sActual Board:\n%s%s",
		                          row,col,expectBoardString,board,more);
		          fail(msg);*/
		        }
		      }
		    }
		    int expectFreeSpaceCount = expectRows*expectCols - expectTileCount;
		    assertEquals(expectTileCount, board.getTileCount());
		    assertEquals(expectFreeSpaceCount, board.getFreeSpaceCount());
		  }

	
	
	
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
}
