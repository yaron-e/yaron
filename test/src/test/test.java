package test;



public class test {
	static Integer board[][] = {
		      {  null, null, null, null, },
		      {  null,    4, null, null, },
		      {    16,    2, null,    2, },
		      {     8,    8,    4,   32, },
		    };
	public static void main(String [] args){
		
		System.out.println(print());
		System.out.println();//addTileAtFreeSpace(3, 5);
		System.out.println(print());

		/*System.out.println("Sum: "+shiftUp());
		System.out.println(print());
		System.out.println("Sum: "+shiftUp());
		System.out.println(print());*/
		
		
	}
	public static void addTileAtFreeSpace(int freeL, Integer tile) {
		  int at=0;
		  for(int i=0; i< board.length; i++)
			  for(int j=0; j< board[0].length; j++){
				  if(board[i][j] instanceof Integer)
					  continue;
				  else{
					  if(at == freeL){
						  board[i][j] = tile;
					  }
					  at++;				  
				  }
			  }
	  }
	
	/*public static int shiftUp(){
		int sum=0;
		for(int i=1; i< board.length ; i++)
			for(int j=0; j < board[0].length; j++){
				//if(board[i][j] == null)continue;
				if(board[i-1][j] =="."){
					board[i-1][j] = board[i][j];
					board[i][j] = ".";
				}else if(board[i-1][j].equals(board[i][j])){
					board[i][j] = ".";
					board[i-1][j] = Integer.parseInt(board[i-1][j])*2 +"";
					sum+= Integer.parseInt(board[i-1][j]);
				}
			}
		
		
		return sum;
	}
	public static int shiftLeft(){
		  int sum=0;
		  for(int i= 0 ; i< board.length; i++){
			  for(int j=1; j< board[0].length; j++){
				  if(board[i][j] == null)continue;
				  if(board[i][j-1] == "."){
					  board[i][j-1]=board[i][j];
					  board[i][j] = null;
				  }
				  else if (board[i][j].equals(board[i][j-1])){
					  board[i][j]=".";
					  board[i][j-1] = Integer.parseInt(board[i][j-1])*2 +"";
					  //sum+=board[i][j-1].getScore();
					  sum+= Integer.parseInt(board[i][j-1]);
				  }
					  
			  }
		  }
		  return sum;
	  }
	public static int shiftRight(){
		int sum=0;
		  for(int i= 0 ; i< board.length; i++){
			  for(int j=0; j< board[0].length-1; j++){
				  if(board[i][j] == ".")continue;
				  if(board[i][j+1] == "."){
					  board[i][j+1]=board[i][j];
					  board[i][j] = ".";
				  }
				  else if (board[i][j].equals(board[i][j+1])){
					  board[i][j]=".";
					  board[i][j+1] = Integer.parseInt(board[i][j+1])*2 +"";
					  sum+= Integer.parseInt(board[i][j+1]);

					  //sum+=board[i][j+1].getScore();
				  }
					  
			  }
		  }
		  return sum;
	}*/
	public static String print(){
		String s="";
		
		for(int i=0; i<board.length; i++){
			for(int j=0; j < board[0].length; j++){
				s+=board[i][j]+" ";
			}s+="\n";
		}
		return s;
	}

}
