	// Concrete implementation of a Tile. TwoNTiles merge with each other
// but only if they have the same value.
public class TwoNTile extends Tile {
	
	private int n;

  // Create a tile with the given value of n; should be a power of 2
  // though no error checking is done
  public TwoNTile(int n){
	  this.n = n;
  }

  // Returns true if this tile merges with the given tile. "this"
  // (calling tile) is assumed to be the stationary tile while moving
  // is presumed to be the moving tile. TwoNTiles only merge with
  // other TwoNTiles with the same internal value.
  @Override
  public boolean mergesWith(Tile moving){
	  if(moving.getScore() == n && moving instanceof TwoNTile)
		  return true;
	  else 
		  return false;
  }

  // Produce a new tile which is the result of merging this tile with
  // the other. For TwoNTiles, the new Tile will be another TwoNTile
  // and will have the sum of the two merged tiles for its value.
  // Throw a runtime exception with a useful error message if this
  // tile and other cannot be merged.
  @Override
  public Tile merge(Tile moving){
	  if(!mergesWith(moving))
		  throw new RuntimeException("Tiles values dont match");
	  else
		  return new TwoNTile(n*2);
  }

  // Get the score for this tile. The score for TwoNTiles are its face
  // value.
  @Override
  public int getScore(){
	  return this.n;
  }

  // Return a string representation of the tile
  @Override
  public String toString(){
	  return ""+n;
  }

}