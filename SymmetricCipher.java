
public abstract class SymmetricCipher extends Cipher{
	protected Alphabet alphabet;
	
	public SymmetricCipher(Alphabet alphabet){
		this.alphabet = alphabet;
	}
	public int wrapInt(int i){
		if(i >= alphabet.length())
			while(i >= alphabet.length())
				i-=alphabet.length();
		else if( i < 0 )
			while( i < 0 )
				i+=alphabet.length();
		return i;
	}
	public int rotate(int index, int shift){
		if(index+ shift > alphabet.length() || index + shift < 0)
			return wrapInt(index + shift);
		else
			return index + shift;
				
	}

	public Alphabet getAlphabet(){
		return alphabet;
	}
	public String encrypt(String s){
		String s1="";
		
		for(int i=0; i< s.length(); i++){
			s1+=encrypt1(s.charAt(i));
		}
		return s1;
	}
	public String decrypt(String s){
		String s1="";
		
		for(int i=0; i< s.length(); i++){
			s1+=decrypt1(s.charAt(i));
		}
		return s1;
	}
	protected abstract char encrypt1(char c);
	protected abstract char decrypt1(char c);
}
