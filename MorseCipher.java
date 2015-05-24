import java.lang.reflect.Array;
import java.util.Arrays;

class MorseCipher extends  SymmetricCipher {

  public static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  public static final String[] codes = {
    ".-",    /* A */    "-...",  /* B */    "-.-.",  /* C */    "-..",   /* D */
    ".",     /* E */    "..-.",  /* F */    "--.",   /* G */    "....",  /* H */
    "..",    /* I */    ".---",  /* J */    "-.-",   /* K */    ".-..",  /* L */
    "--",    /* M */    "-.",    /* N */    "---",   /* O */    ".--.",  /* P */
    "--.-",  /* Q */    ".-.",   /* R */    "...",   /* S */    "-",     /* T */
    "..-",   /* U */    "...-",  /* V */    ".--",   /* W */    "-..-",  /* X */
    "-.--",  /* Y */    "--..",  /* Z */    ".----", /* 1 */    "..---", /* 2 */
    "...--", /* 3 */    "....-", /* 4 */    ".....", /* 5 */    "-....", /* 6 */
    "--...", /* 7 */    "---..", /* 8 */    "----.", /* 9 */    "-----", /* 0 */
  };

  
  public MorseCipher(){
	  super(new Alphabet(letters));
  }
  
  public char encrypt1(char c){
	  throw new UnsupportedOperationException();
  }
  
  public char decrypt1(char c){
	  throw new UnsupportedOperationException();
  }
  
  public String encrypt(String plainText){
	  String text = plainText.toUpperCase(), s="";
	  for(int i = 0; i< text.length(); i++){
		  char c = text.charAt(i);
		  if(c == ' '){
			  s+="       ";
			  continue;
		  }
		  if(alphabet.getSymbols().indexOf(c) == -1)throw new NotInAlphabetException(text.charAt(i), alphabet);		 
		  if(i == text.length()-1 || text.charAt(i+1) == ' ')s+=codes[alphabet.getSymbols().indexOf(c)];
		  else  s+=codes[alphabet.getSymbols().indexOf(c)]+"   ";
	  }
	  
	  return s;
  }
  
  public String decrypt(String cryptText){
	  String s="";
	  String[] arr = cryptText.split(" ");
	  int y=1;
	  for(int i=0; i< arr.length; i++){
		  int x=0;
		  while(cryptText.charAt(y++) == ' ') {
			  x++;}
		  y--;
		  y+=arr[i].length();
		  if(x == 7 || x==6)
			  s+=" ";
		  else if(Arrays.asList(codes).indexOf(arr[i]) == -1)
			  s+="";
		  else
			 s+= alphabet.getSymbols().charAt(Arrays.asList(codes).indexOf(arr[i]));
	  }	  
	  return s;
	  
  }
}