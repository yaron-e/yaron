// Tracks the positions of an arbitrary 2D grid of Tiles.  DenseBoard
// uses an internal, multi-dimensional array to store the tiles and
// thus has a O(R * C) memory footprint (rows by columns).
public class DenseBoard extends Board {

	private int rows, cols;
	private Tile board[][];
	private int numberOfTiles;
	private boolean shifted;
  // Build a Board of the specified size that is empty of any tiles
  public DenseBoard(int rows, int cols){
	  this.rows = rows;
	  this.cols = cols;
	  this.numberOfTiles = 0;
	  this.shifted = false;
	  this.board= new Tile [rows][cols];
  }

  // Build a board that copies the 2D array of tiles provided Tiles
  // are immutable so can be referenced without copying but the a
  // fresh copy of the 2D array must be created for internal use by
  // the Board.
  public DenseBoard(Tile t[][]){
	  this.board = new Tile [t.length][t[0].length];
	  this.shifted = false;

	  this.rows = t.length;
	  this.cols = t[0].length;
	  this.numberOfTiles = 0;
	  
	  for(int i=0; i< t.length; i++)
		  for(int j=0; j< t[0].length; j++){
			  board[i][j] = t[i][j];
			  if(t[i][j] != null)this.numberOfTiles++;
		  }
  }

  // Create a distinct copy of the board including its internal tile
  // positions and any other state
  public Board copy(){
	  return new DenseBoard(board);
  }

  // Return the number of rows in the Board
  public int getRows(){
	  return this.rows;
  }

  // Return the number of columns in the Board
  public int getCols(){
	  return this.cols;
  }

  // Return how many tiles are present in the board (non-empty spaces)
  // TARGET COMPLEXITY: O(1)
  public int getTileCount(){
	  return this.numberOfTiles;
  }

  // Return how many free spaces are in the board
  // TARGET COMPLEXITY: O(1)
  public int getFreeSpaceCount(){
	  return (getCols() * getRows()) - getTileCount();
  }

  // Get the tile at a particular location.  If no tile exists at the
  // given location (free space) then null is returned. Throw a
  // runtime exception with a useful error message if an out of bounds
  // index is requested.
  public Tile tileAt(int i, int j) {
	  if(i < 0 || j< 0 || i> getRows() || j > getCols())
		  throw new RuntimeException("Index out of bound");
	  else if(board[i][j] == null)
		  return null;
	  else
		  return board[i][j];
  }

  // true if the last shift operation moved any tile; false otherwise
  // TARGET COMPLEXITY: O(1)
  public boolean lastShiftMovedTiles(){
	  return this.shifted;
  }

  // Return true if a shift left, right, up, or down would merge any
  // tiles. If no shift would cause any tiles to merge, return false.
  // The inability to merge anything is part of determining if the
  // game is over.
  // 
  // TARGET COMPLEXITY: O(R * C)
  // R: number of rows
  // C: number of columns
  public boolean mergePossible(){
	DenseBoard temp = new DenseBoard(board);
	if (temp.shiftDown() != 0 || temp.shiftUp() != 0 ||
			temp.shiftRight() != 0 || temp.shiftLeft() != 0)
		return true;
	else 
		return false;
  }

  // Add a the given tile to the board at the "freeL"th free space.
  // Free spaces are numbered 0,1,... from left to right accross the
  // columns of the zeroth row, then the first row, then the second
  // and so forth. For example the board with following configuration
  // 
  //    -    -    -    - 
  //    -    4    -    - 
  //   16    2    -    2 
  //    8    8    4    4 
  // 
  // has its 9 free spaces numbered as follows
  // 
  //    0    1    2    3 
  //    4    .    5    6 
  //    .    .    7    . 
  //    .    .    .    . 
  // 
  // where the dots (.) represent filled tiles on the board.
  // 
  // Calling addTileAtFreeSpace(6, new Tile(32) would leave the board in
  // the following state.
  // 
  //    -    -    -    - 
  //    -    4    -   32 
  //   16    2    -    2 
  //    8    8    4    4 
  // 
  // Throw a runtime exception with an informative error message if a
  // location that does not exist is requested.
  // 
  // TARGET COMPLEXITY: O(T + I)
  // T: the number of non-empty tiles in the board
  // I: the value of parameter freeL
  public void addTileAtFreeSpace(int freeL, Tile tile) {
	  int at=0;
	  for(int i=0; i< getRows(); i++)
		  for(int j=0; j< getCols(); j++){
			  if(tileAt(i, j) == null)
				  continue;
			  else{
				  if(at == freeL){
					  board[i][j] = tile;
					  this.numberOfTiles++;
				  }
				  at++;				  
			  }
		  }
  }

  // Pretty-printed version of the board. Use the format "%4s " to
  // print the String version of each tile in a grid.
  // 
  // TARGET COMPLEXITY: O(R * C)
  // R: number of rows
  // C: number of columns
  public String toString(){
	  String s = "";
	  
	  for(int i=0; i< getRows(); i++){
		  for(int j=0; j< getCols(); j++){
			  if(board[i][j] == null)
				  s+= "- ";
			  else
				  s+=board[i][j].getScore()+" ";
		  }s+="\n";
	  }
	  return s;
	  
  }

  // Shift the tiles of Board in various directions.  Any tiles that
  // collide and should be merged should be changed internally in the
  // board.  Shifts only remove tiles, never add anything.  The shift
  // methods also set the state of the board internally so that a
  // subsequent call to lastShiftMovedTiles() will return true if any
  // Tile moved and false otherwise.  The methods return the score
  // that is generated from the shift which is the sum of the scores
  // all tiles merged during the shift. If no tiles are merged, the
  // return score is 0.
  // 
  // TARGET COMPLEXITY: O(R * C)
  // R: the number of rows of the board
  // C: the number of columns of the board
  public int shiftLeft(){
	  shifted=false;
	  int sum=0;
	  for(int i= 0 ; i< board.length; i++){
		  for(int j=1; j< board[0].length; j++){
			  if(board[i][j] == null)continue;
			  if(board[i][j-1] == null){
				  board[i][j-1]=board[i][j];
				  board[i][j] = null;
				  shifted = true;
			  }
			  else if (board[i][j].getScore() == board[i][j-1].getScore()){
				  board[i][j]=null;
				  board[i][j-1] = board[i][j-1].merge(board[i][j-1]);
				  sum+=board[i][j-1].getScore();
				  shifted = true;
			  }
				  
		  }
	  }
	  return sum;
  }

  public int shiftRight(){
	  shifted=false;
	  int sum=0;
	  for(int i= 0 ; i< board.length; i++){
		  for(int j=0; j< board[0].length-1; j++){
			  if(board[i][j] == null)continue;
			  if(board[i][j+1] == null){
				  board[i][j+1]=board[i][j];
				  board[i][j] = null;
				  shifted = true;
			  }
			  else if (board[i][j].getScore() == board[i][j+1].getScore()){
				  board[i][j]=null;
				  board[i][j+1] = board[i][j+1].merge(board[i][j+1]);
				  sum+=board[i][j+1].getScore();
				  shifted = true;
			  }
				  
		  }
	  }
	  return sum;
  }
  public int shiftUp(){
	  shifted=false;
	  int sum=0;
		for(int i=1; i< board.length ; i++)
			for(int j=0; j < board[0].length; j++){
					shifted = true;
					if(board[i][j] == null)continue;
					if(board[i-1][j] ==null){
					board[i-1][j] = board[i][j];
					board[i][j] = null;
				}else if(board[i-1][j].getScore() == (board[i][j]).getScore()){
					board[i][j] = null;
					board[i-1][j] = board[i-1][j].merge(board[i-1][j]);//board[i-1][j].getScore() * 2;
					shifted = true;
					sum+= (board[i-1][j]).getScore();
				}
			}		
		return sum;
  }

  public int shiftDown(){
	  shifted=false;
	  int sum=0;
		for(int i=0; i < board.length -1; i++)
			for(int j =0; j< board[0].length; j++){
				if(board[i][j] == null)continue;
				if(board[i+1][j] == null){
					board[i +1][j] = board[i][j];
					shifted = true;
					board[i][j] = null;
				}else if(board[i+1][j].getScore() == board[i][j].getScore()){
					board[i][j] = null;
					shifted = true;
					board[i+1][j] = board[i+1][j].merge(board[i+1][j]);
					sum+= (board[i+1][j]).getScore();
					this.numberOfTiles--;
				}
			}
		return sum;
  }

}