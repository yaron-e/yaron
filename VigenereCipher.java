
public class VigenereCipher extends SymmetricCipher{
	protected String password;
	protected int passwordPos;
	
	public VigenereCipher(String password,Alphabet alphabet){
		super(alphabet);
		this.password = password;	
		this.passwordPos =0;
	}
	
	public VigenereCipher(String password){
		super(Alphabet.DEFAULT);
		this.password = password;
		this.passwordPos =0;
	}
	
	public String getPassword(){
		return this.password;
	}
	@Override
	protected char encrypt1(char c){
		if(passwordPos == password.length()) 
			passwordPos= 0;
		
		int i = alphabet.indexOf(password.charAt(passwordPos++));
		int i2 = alphabet.indexOf(c);
		if(i == -1 || i2 == -1)
			throw new NotInAlphabetException(c, alphabet);
		
		return alphabet.getSymbols().charAt(wrapInt(i+i2)); 
	}
	@Override
	protected char decrypt1(char c){
		if(passwordPos == password.length())
			passwordPos= 0;
		
		int i = alphabet.getSymbols().indexOf(password.charAt(passwordPos++));
		int i2 = alphabet.indexOf(c);
		if(i == -1 || i2 == -1)
			throw new NotInAlphabetException(c, alphabet);
		
		return alphabet.getSymbols().charAt(wrapInt(i*-1+i2)); 
	}
	
	public String encrypt(String s){
		this.passwordPos =0;
		return super.encrypt(s);
	}
	
	public String decrypt(String s){
		this.passwordPos = 0;
		return super.decrypt(s);
	}
	
	public String toString(){
		return String.format("Vigenere Cipher (password=\'%s\')", password);
	}

}
