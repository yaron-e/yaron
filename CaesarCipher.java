
public class CaesarCipher extends SymmetricCipher{
	protected int shift;
	
	public CaesarCipher(int shift, Alphabet alphabet){
		super(alphabet);
		this.shift= shift;
	}
	
	public CaesarCipher(int shift){
		super(Alphabet.DEFAULT);
		this.shift = shift;
	}

	@Override
	public char encrypt1(char c){
		int i = alphabet.indexOf(c);
		i+= shift;
		return alphabet.getSymbols().charAt(wrapInt(i));
		
	}
	@Override
	public char decrypt1(char c){
		int i = alphabet.indexOf(c);
		return alphabet.getSymbols().charAt(wrapInt(i-shift));
	}
	
	public String toString(){
		return String.format("Caesar Cipher (shift=%d)", shift);
	}
}
