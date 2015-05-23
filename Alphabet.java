public class Alphabet {
	
	private String symbols;
 
  public static final Alphabet DEFAULT = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz 1234567890!@#$%^&*()_+-=[]{}\\|;:'\",./?<>");
    // finish this class definition.
  
  public Alphabet(String symbols){
	  this.symbols = symbols;
  }
  
  public int indexOf(char c){
	  
	  for(int i=0; i< symbols.length() ; i++)
		  if(symbols.charAt(i) == c)
			  return i;
	  throw new NotInAlphabetException(c, DEFAULT);
  }
  
  public char get(int i){
	  if(i>=0 && i< symbols.length())
		  return symbols.charAt(i);
	  else
		  throw new NotInAlphabetException(	'a', DEFAULT);	  
  }
  
  public int length(){
	  return symbols.length();
  }
  
  public String getSymbols(){
	  return this.symbols;
  }
  
  public String toString(){
	  return "Alphabet("+ this.symbols+")";
  }

  public boolean equals(Object other){
	  if(other instanceof Alphabet && ((Alphabet) other).getSymbols().equals(symbols))
		  return true;
	  else 
		  return false;
  }
}