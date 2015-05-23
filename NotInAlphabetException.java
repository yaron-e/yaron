
public class NotInAlphabetException extends RuntimeException{
	public final String msg;
	public final char offender;
	public final Alphabet a;

	
	public NotInAlphabetException(String msg, char offender, Alphabet a){
		this.msg = msg;
		this.offender = offender;
		this.a = a;
	}
	public NotInAlphabetException(char offender, Alphabet a){
		this.msg = String.format("Not in alphabet: \'%c\' not found in %s", offender, a.toString());
		this.offender = offender;
		this.a = a;
	}
	public String toString(){
		return msg;
	}
}
