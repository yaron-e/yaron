// CHANGELOG
// 
// Sun Feb 22 11:19:54 EST 2015 : Removed assertNotEquals() which is
// not compatible with DrJava, replaced with equivalent code

/** Example of using unit tests for programming assignment 3.  This is
 * partially how your code will be graded.  Later in the class we will
 * write our own unit tests.  To run them on the command line, make
 * sure that the junit-cs211.jar is in the project directory.
 * 
 *  demo$ javac -cp .:junit-cs211.jar *.java     # compile everything
 *  demo$ java  -cp .:junit-cs211.jar P3Tests    # run tests
 * 
 * On windows replace : with ; (colon with semicolon)
 *  demo$ javac -cp .;junit-cs211.jar *.java     # compile everything
 *  demo$ java  -cp .;junit-cs211.jar P3Tests    # run tests
 */

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.Arrays.*;

public class P3Tests {
    public static void main(String args[]){
 org.junit.runner.JUnitCore.main("P3Tests");
    }
  
  
  
    /* Alphabet Tests */
  
    static Alphabet uppers     = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ ."); 
    static Alphabet lowers     = new Alphabet("abcdefghijklmnopqrstuvwxyz");
    static Alphabet lowerSpace = new Alphabet("abcdefghijklmnopqrstuvwxyz ");
  
    @Test (timeout=2000) public void test_Alpha_1(){ assertEquals(3,new Alphabet("abcde").indexOf('d')); }
    @Test (timeout=2000) public void test_Alpha_2(){ assertEquals(20,new Alphabet("ABCDEFGHIJabcdefghijk").indexOf('k')); }
    @Test (timeout=2000) public void test_Alpha_3(){ assertEquals('c',new Alphabet("abcde").get(2)); }
    @Test (timeout=2000) public void test_Alpha_4(){ assertEquals(93, Alphabet.DEFAULT.length()); }
    @Test (timeout=2000) public void test_Alpha_5(){ assertEquals(5, new Alphabet("ABCDE").length()); }
    @Test (timeout=2000) public void test_Alpha_6(){ assertEquals(18,new Alphabet("abcdefghijklmnopqr").length()); }
    @Test (timeout=2000) public void test_Alpha_7(){ assertEquals("QUICK BROWN FoX",new Alphabet("QUICK BROWN FoX").getSymbols()); }
    @Test (timeout=2000) public void test_Alpha_8(){ assertEquals("Alphabet(123XYzabC)",new Alphabet("123XYzabC").toString()); }

    @Test (timeout=2000) public void test_Alpha_9(){ assertEquals( 5, new Alphabet("abcdefg").indexOf('f')); }
    @Test (timeout=2000) public void test_Alpha_10(){ assertEquals( 0, new Alphabet("abcdefg").indexOf('a')); }
    @Test (timeout=2000) public void test_Alpha_11(){ assertEquals(26, Alphabet.DEFAULT   .indexOf('a')); }

    @Test (timeout=2000) public void test_Alpha_12(){ assertEquals('A', Alphabet.DEFAULT.get( 0)); }
    @Test (timeout=2000) public void test_Alpha_13(){ assertEquals('c', Alphabet.DEFAULT.get(28)); }
    @Test (timeout=2000) public void test_Alpha_14(){ assertEquals(' ', Alphabet.DEFAULT.get(52)); }
    @Test (timeout=2000) public void test_Alpha_15(){ assertEquals('>', Alphabet.DEFAULT.get(92)); }

    @Test (timeout=2000) public void test_Alpha_16() {
 Object a1 = new Alphabet("ABCabc");
 Object a2 = new Alphabet("ABCabc");
 Object a3 = new Alphabet("123");
 
 assertEquals   ( a1,a2); // true
 assertFalse( a1.equals(a3)); // false
    }
    
    @Test (timeout=2000) public void test_Alpha_17(){
 try{
     uppers.get(400);
 } catch (NotInAlphabetException e){ return; }
 fail ("should have realized 400 wasn't a valid index in the uppers alphabet.");
    }
    @Test (timeout=2000) public void test_Alpha_18(){
 try{
     uppers.indexOf('a');
 } catch (NotInAlphabetException e){ return; }
 fail ("should have realized 'a' wasn't in the uppers alphabet.");
    }
    
    /* NotInAlphabetExcpetion Tests */

    @Test (timeout=2000) public void test_NotInAlphabetException_1(){
 NotInAlphabetException e = new NotInAlphabetException('c',uppers);
 assertEquals('c', e.offender);
    }
    @Test (timeout=2000) public void test_NotInAlphabetException_2(){
 NotInAlphabetException e = new NotInAlphabetException('c',uppers);
 assertEquals(uppers, e.a);
    }
    @Test (timeout=2000) public void test_NotInAlphabetException_3(){
 NotInAlphabetException e = new NotInAlphabetException('C',lowers);
 assertEquals("Not in alphabet: 'C' not found in abcdefghijklmnopqrstuvwxyz.", e.msg);
    }


    @Test (timeout=2000) public void test_NotInAlphabetException_4(){
 NotInAlphabetException e = new NotInAlphabetException("special message.",'c',uppers);
 assertEquals('c', e.offender);
    }
    @Test (timeout=2000) public void test_NotInAlphabetException_5(){
 NotInAlphabetException e = new NotInAlphabetException("special message.",'c',uppers);
 assertEquals(uppers, e.a);
    }
    @Test (timeout=2000) public void test_NotInAlphabetException_6(){
 NotInAlphabetException e = new NotInAlphabetException("special message.",'c',uppers);
 assertEquals("special message.", e.msg);
    }
    

    
    @Test (timeout=2000) public void test_NotInAlphabetException_7(){
 try {
     assertEquals(5, new Alphabet("WXYZ").get(10));
     fail("shouldn't get here...");
 } catch (NotInAlphabetException e){ /* good. */ }
    }
    @Test (timeout=2000) public void test_NotInAlphabetException_8(){ 
 try{
     assertEquals(20,new Alphabet("ABCDEFGHIJabcdefghijk").indexOf('X'));
     fail("shouldn't get here...");
 } catch (NotInAlphabetException e){ /* good. */ }
    }
    @Test (timeout=2000) public void test_NotInAlphabetException_9(){ 
 NotInAlphabetException e = new NotInAlphabetException("no good",'x',new Alphabet("ABC"));
 assertEquals(new String("no good"), e.msg);
 assertEquals('x',e.offender);
 assertEquals("ABC",e.a.getSymbols());
 assertEquals(e.toString(),e.msg);
    }
    @Test (timeout=2000) public void test_NotInAlphabetException_10(){ 
 NotInAlphabetException e = new NotInAlphabetException('x',new Alphabet("ABC"));
 String msg = new String("Not in alphabet: 'x' not found in ABC.");
 assertEquals('x',e.offender);
 assertEquals("ABC",e.a.getSymbols());
 assertEquals(e.toString(),e.msg);
    }

    @Test (timeout=2000) public void test_NotInAlphabetException_11(){
 NotInAlphabetException e = new NotInAlphabetException('x',new Alphabet("ABC"));
 assertEquals(e.msg,e.toString());
    }
    
    /* Cipher - no direct tests.  */
  
  
    /* SymmetricCipher */
  
    /*
     * This inner class extends SymmetricCipher in its own way, correctly; this allows us to test your implementation
     * details of SymmetricCipher without relying upon your CaesarCipher or VigenereCipher to be absolutely correct.
     */
    class SwapRWCipher extends SymmetricCipher{
 public SwapRWCipher(Alphabet a){
     super(a);
 }
 public SwapRWCipher(){
     super(Alphabet.DEFAULT);
 }
 public char encrypt1(char c){
     switch(c){
     case 'R': return 'W';
     case 'r': return 'w';
     case 'W': return 'R';
     case 'w': return 'r';
     default:  return c;
     }
 }
 public char decrypt1(char c){
     switch(c){
     case 'R': return 'W';
     case 'r': return 'w';
     case 'W': return 'R';
     case 'w': return 'r';
     default:  return c;
     }
 }
    }
  
    SwapRWCipher swap = new SwapRWCipher(Alphabet.DEFAULT);
    @Test (timeout=2000) public void test_Symmetric_1(){ assertEquals( 5, swap.rotate( 4,  1)); }
    @Test (timeout=2000) public void test_Symmetric_2(){ assertEquals( 5, swap.rotate( 4,  1)); }
    @Test (timeout=2000) public void test_Symmetric_3(){ assertEquals( 3, swap.rotate( 4, -1)); }
    @Test (timeout=2000) public void test_Symmetric_4(){ assertEquals(10, swap.rotate(10, 93)); } // DEFAULT has 93 characters.
    @Test (timeout=2000) public void test_Symmetric_5(){ assertEquals(91, swap.rotate( 3, -5)); }
    @Test (timeout=2000) public void test_Symmetric_6(){ assertEquals( 9, swap.rotate( 2,100)); }
    
    @Test (timeout=2000) public void test_Symmetric_7(){ assertEquals( Alphabet.DEFAULT, swap.getAlphabet()); }
    @Test (timeout=2000) public void test_Symmetric_8(){ assertEquals( uppers, new SwapRWCipher(uppers).getAlphabet()); }
    
    @Test (timeout=2000) public void test_Symmetric_9() { assertEquals( 1, swap.wrapInt(   1)); }
    @Test (timeout=2000) public void test_Symmetric_10(){ assertEquals( 0, swap.wrapInt(  93)); }
    @Test (timeout=2000) public void test_Symmetric_11(){ assertEquals(92, swap.wrapInt(  -1)); }
    @Test (timeout=2000) public void test_Symmetric_12(){ assertEquals(88, swap.wrapInt(  -5)); }
    @Test (timeout=2000) public void test_Symmetric_13(){ assertEquals(70, swap.wrapInt(1000)); }
    
    @Test (timeout=2000) public void test_Symmetric_14(){ assertEquals("no fancy alphas", swap.encrypt("no fancy alphas"));}
    @Test (timeout=2000) public void test_Symmetric_15(){ assertEquals("no fancy alphas", swap.decrypt("no fancy alphas"));}
    @Test (timeout=2000) public void test_Symmetric_16(){ assertEquals("hello rowld", swap.encrypt("hello world"));}
    @Test (timeout=2000) public void test_Symmetric_17(){ assertEquals("rwRW", swap.encrypt("wrWR"));}
    @Test (timeout=2000) public void test_Symmetric_18(){ assertEquals("hello rowld", swap.decrypt("hello world"));}
    @Test (timeout=2000) public void test_Symmetric_19(){ assertEquals("rwRW", swap.decrypt("wrWR"));}
    
    /* here's another sneaky way to make a SymmetricCipher value in-place; this is called an "anonymous class".
     * In this case, the anonymous class is extending SymmetricCipher. Anonymous classes are truly expressions, 
     * and this is why it shows up on the righthand side of an assignment statement here.
     * 
     * It'll complain if we try to use encrypt1 or decrypt1, but we can test the other methods.
     *
     //  static SymmetricCipher sc = new SymmetricCipher(Alphabet.DEFAULT){
     //    public char encrypt1(char c){ throw new RuntimeException("shouldn't try encrypt1 with this anonymous class!"); }
     //    public char decrypt1(char c){ throw new RuntimeException("shouldn't try encrypt1 with this anonymous class!"); }
     //  };
     // we've already got enough SymmetricCipher tests above though, so we won't actually use this.




     /* CaesarCipher Tests */
  
        @Test (timeout=2000) public void test_Caesar_1(){ assertEquals(5,new CaesarCipher(5).shift); }
    @Test (timeout=2000) public void test_Caesar_2(){ assertEquals(50,new CaesarCipher(50).shift); }
    @Test (timeout=2000) public void test_Caesar_3(){ assertEquals(-8,new CaesarCipher(-8).shift); }
    @Test (timeout=2000) public void test_Caesar_4(){ assertEquals(Alphabet.DEFAULT,new CaesarCipher(20).getAlphabet()); }
  
    @Test (timeout=2000) public void test_Caesar_5(){
 Alphabet a =  new Alphabet("ABCDEFGHIJKLMNOP");
 CaesarCipher c = new CaesarCipher(10,a);
 assertEquals(a, c.alphabet);
    }
    
    @Test (timeout=2000) public void test_Caesar_6(){ assertEquals('X',new CaesarCipher(  0) .encrypt1('X')); }
    @Test (timeout=2000) public void test_Caesar_7(){ assertEquals('Y',new CaesarCipher(  1) .encrypt1('X')); }
    @Test (timeout=2000) public void test_Caesar_8(){ assertEquals('1',new CaesarCipher(  2) .encrypt1('z')); }
    @Test (timeout=2000) public void test_Caesar_9(){ assertEquals('e',new CaesarCipher(100) .encrypt1('X')); }
    @Test (timeout=2000) public void test_Caesar_10(){ assertEquals('S',new CaesarCipher( -5) .encrypt1('X')); }
    @Test (timeout=2000) public void test_Caesar_11(){ assertEquals(',',new CaesarCipher(-10) .encrypt1('E')); }
    
    @Test (timeout=2000) public void test_Caesar_12(){ assertEquals('X',new CaesarCipher(  0) .decrypt1('X')); }
    @Test (timeout=2000) public void test_Caesar_13(){ assertEquals('W',new CaesarCipher(  1) .decrypt1('X')); }
    @Test (timeout=2000) public void test_Caesar_14(){ assertEquals('x',new CaesarCipher(  2) .decrypt1('z')); }
    @Test (timeout=2000) public void test_Caesar_15(){ assertEquals('Q',new CaesarCipher(100) .decrypt1('X')); }
    @Test (timeout=2000) public void test_Caesar_16(){ assertEquals('c',new CaesarCipher( -5) .decrypt1('X')); }
    @Test (timeout=2000) public void test_Caesar_17(){ assertEquals('P',new CaesarCipher(-11) .decrypt1('E')); }


    @Test (timeout=2000) public void test_Caesar_18(){ assertEquals("catfood for cats",new CaesarCipher(0, lowerSpace).encrypt("catfood for cats")); }
    @Test (timeout=2000) public void test_Caesar_19(){ assertEquals("catfood for cats",new CaesarCipher(0, lowerSpace).decrypt("catfood for cats")); }
    @Test (timeout=2000) public void test_Caesar_20(){ assertEquals("trjweeu",new CaesarCipher(17, lowerSpace).encrypt("catfood")); }
    @Test (timeout=2000) public void test_Caesar_21(){ assertEquals("catfood",new CaesarCipher(17, lowerSpace).decrypt("trjweeu")); }
    @Test (timeout=2000) public void test_Caesar_22(){ assertEquals("bjvxwyfrwmzn",new CaesarCipher(-5, lowerSpace).encrypt("go backwards")); }
    @Test (timeout=2000) public void test_Caesar_23(){ assertEquals("go backwards",new CaesarCipher(-5, lowerSpace).decrypt("bjvxwyfrwmzn")); }
    @Test (timeout=2000) public void test_Caesar_24(){
 CaesarCipher c30 = new CaesarCipher(30, lowerSpace);
 CaesarCipher c3  = new CaesarCipher( 3, lowerSpace);
 String msg = "roundabout equal effects";
 assertEquals(c3.encrypt(msg), c30.encrypt(msg));
    }
    @Test (timeout=2000) public void test_Caesar_25(){
 CaesarCipher c = new CaesarCipher(300, lowerSpace);
 String clear   = "long messages are no extra hassle";
 String crypted = "orqjcphvvdjhvcduhcqrch wudckdvvoh";
 assertEquals(crypted,c.encrypt(clear));
    }
    @Test (timeout=2000) public void test_Caesar_26(){
 CaesarCipher c = new CaesarCipher(300, lowerSpace);
 String clear   = "long messages are no extra hassle";
 String crypted = "orqjcphvvdjhvcduhcqrch wudckdvvoh";
 assertEquals(clear,c.decrypt(crypted));
    }
    @Test (timeout=2000) public void test_Caesar_27(){
 CaesarCipher c = new CaesarCipher(300, new Alphabet(Alphabet.DEFAULT.getSymbols()+"\n"));
 String clear = "Four score and seven years ago our fathers brought forth on this continent, a new nation, conceived in Liberty, and dedicated to the proposition that all men are created equal.\n\nNow we are engaged in a great civil war, testing whether that nation, or any nation so conceived and so dedicated, can long endure. We are met on a great battle-field of that war. We have come to dedicate a portion of that field, as a final resting place for those who here gave their lives that that nation might live. It is altogether fitting and proper that we should do this.\n\nBut, in a larger sense, we can not dedicate -- we can not consecrate -- we can not hallow -- this ground. The brave men, living and dead, who struggled here, have consecrated it, far above our poor power to add or detract. The world will little note, nor long remember what we say here, but it can never forget what they did here. It is for us the living, rather, to be dedicated here to the unfinished work which they who fought here have thus far so nobly advanced. It is rather for us to be here dedicated to the great task remaining before us -- that from these honored dead we take increased devotion to that cause for which they gave the last full measure of devotion -- that we here highly resolve that these dead shall not have died in vain -- that this nation, under God, shall have a new birth of freedom -- and that government of the people, by the people, for the people, shall not perish from the earth.";
 String crypted = "X6@9*0u69w*s5v*0w#w5*^ws90*sy6*6@9*xs!zw90*t96@yz!*x69!z*65*!z 0*u65! 5w5!L*s*5w$*5s! 65L*u65uw #wv* 5*d tw9!^L*s5v*vwv us!wv*!6*!zw*796760 ! 65*!zs!*s33*4w5*s9w*u9ws!wv*w8@s3MRRf6$*$w*s9w*w5ysywv* 5*s*y9ws!*u # 3*$s9L*!w0! 5y*$zw!zw9*!zs!*5s! 65L*69*s5^*5s! 65*06*u65uw #wv*s5v*06*vwv us!wvL*us5*365y*w5v@9wM*ow*s9w*4w!*65*s*y9ws!*ts!!3w\nx w3v*6x*!zs!*$s9M*ow*zs#w*u64w*!6*vwv us!w*s*769! 65*6x*!zs!*x w3vL*s0*s*x 5s3*9w0! 5y*73suw*x69*!z60w*$z6*zw9w*ys#w*!zw 9*3 #w0*!zs!*!zs!*5s! 65*4 yz!*3 #wM*a!* 0*s3!6yw!zw9*x !! 5y*s5v*7967w9*!zs!*$w*0z6@3v*v6*!z 0MRRT@!L* 5*s*3s9yw9*0w50wL*$w*us5*56!*vwv us!w*\n\n*$w*us5*56!*u650wu9s!w*\n\n*$w*us5*56!*zs336$*\n\n*!z 0*y96@5vM*lzw*t9s#w*4w5L*3 # 5y*s5v*vwsvL*$z6*0!9@yy3wv*zw9wL*zs#w*u650wu9s!wv* !L*xs9*st6#w*6@9*7669*76$w9*!6*svv*69*vw!9su!M*lzw*$693v*$ 33*3 !!3w*56!wL*569*365y*9w4w4tw9*$zs!*$w*0s^*zw9wL*t@!* !*us5*5w#w9*x69yw!*$zs!*!zw^*v v*zw9wM*a!* 0*x69*@0*!zw*3 # 5yL*9s!zw9L*!6*tw*vwv us!wv*zw9w*!6*!zw*@5x 5 0zwv*$692*$z uz*!zw^*$z6*x6@yz!*zw9w*zs#w*!z@0*xs9*06*56t3^*sv#s5uwvM*a!* 0*9s!zw9*x69*@0*!6*tw*zw9w*vwv us!wv*!6*!zw*y9ws!*!s02*9w4s 5 5y*twx69w*@0*\n\n*!zs!*x964*!zw0w*z6569wv*vwsv*$w*!s2w* 5u9ws0wv*vw#6! 65*!6*!zs!*us@0w*x69*$z uz*!zw^*ys#w*!zw*3s0!*x@33*4ws0@9w*6x*vw#6! 65*\n\n*!zs!*$w*zw9w*z yz3^*9w063#w*!zs!*!zw0w*vwsv*0zs33*56!*zs#w*v wv* 5*#s 5*\n\n*!zs!*!z 0*5s! 65L*@5vw9*Y6vL*0zs33*zs#w*s*5w$*t 9!z*6x*x9wwv64*\n\n*s5v*!zs!*y6#w954w5!*6x*!zw*7w673wL*t^*!zw*7w673wL*x69*!zw*7w673wL*0zs33*56!*7w9 0z*x964*!zw*ws9!zM";
    
 // does it encrypt, decrypt, and cancel out?
 assertEquals(crypted, c.encrypt(clear));
 assertEquals(clear,c.decrypt(crypted));
 assertEquals(clear, c.decrypt(c.encrypt(clear)));
    }    
  
    @Test (timeout=2000) public void test_Caesar_28(){ assertEquals("NYLLUFZSPTLFPZFKLHKSDG",new CaesarCipher(7,uppers).encrypt("GREEN SLIME IS DEADLY.")); }
    @Test (timeout=2000) public void test_Caesar_29(){ assertEquals("SBQQZKCXUYQKUCKO YUZS",new CaesarCipher(40,uppers).encrypt("GREEN SLIME IS COMING")); }
    @Test (timeout=2000) public void test_Caesar_30(){ assertEquals("GMXXCKUZKDTQKGMI",new CaesarCipher(40,uppers).encrypt("WALLS IN THE WAY")); }
    @Test (timeout=2000) public void test_Caesar_31(){ assertEquals("WALLS IN THE WAY",new CaesarCipher(40,uppers).decrypt("GMXXCKUZKDTQKGMI")); }


    @Test (timeout=2000) public void test_Caesar_32(){
 try {
     char c = new CaesarCipher(12,lowers).encrypt1('A');
 } catch (NotInAlphabetException e){ return; }
 fail("shouldn't encrypt any characters not in the alphabet.");
    }

    @Test (timeout=2000) public void test_Caesar_33(){
 try {
     char c = new CaesarCipher(12,lowers).decrypt1('A');
 } catch (NotInAlphabetException e){ return; }
 fail("shouldn't encrypt any characters not in the alphabet.");
    }

    @Test (timeout=2000) public void test_Caesar_34(){
 try {
     String s = new CaesarCipher(12,lowers).encrypt("HELLO");
 } catch (NotInAlphabetException e){ return; }
 fail("shouldn't encrypt any characters not in the alphabet.");
    }
    @Test (timeout=2000) public void test_Caesar_35(){
 try {
     String s = new CaesarCipher(12,lowers).decrypt("HELLO");
 } catch (NotInAlphabetException e){ return; }
 fail("shouldn't encrypt any characters not in the alphabet.");
    }

    @Test (timeout=2000) public void test_Caesar_36(){
 CaesarCipher c = new CaesarCipher(12,lowers);
 assertEquals("Caesar Cipher (shift=12)",c.toString());
    }
    
    
    
    /*  VigenereCipher */
  
  
    @Test (timeout=2000) public void test_VigenereCipher_1(){ assertEquals("ABCDEFG",new VigenereCipher("ABCDEFG").getPassword()); }
    @Test (timeout=2000) public void test_VigenereCipher_2(){ assertEquals("abcdefg",new VigenereCipher("abcdefg",lowers).getPassword()); }
    
    @Test (timeout=2000) public void test_VigenereCipher_3(){ assertEquals(Alphabet.DEFAULT,new VigenereCipher("ABCDEFG").getAlphabet()); }
    @Test (timeout=2000) public void test_VigenereCipher_4(){ assertEquals(lowers,new VigenereCipher("abcdefg",lowers).getAlphabet()); }

    @Test (timeout=2000) public void test_VigenereCipher_5(){ assertEquals("ZERO",        new VigenereCipher("A")    .encrypt("ZERO"));         }
    @Test (timeout=2000) public void test_VigenereCipher_6(){ assertEquals("YcMQR",       new VigenereCipher("SLIME").encrypt("GREEN"));        }
    @Test (timeout=2000) public void test_VigenereCipher_7(){ assertEquals("SLIMESLI",    new VigenereCipher("SLIME").encrypt("AAAAAAAA"));     }
    @Test (timeout=2000) public void test_VigenereCipher_8(){ assertEquals("exiuLEFUsooo",new VigenereCipher("AAAB") .encrypt("exitLEFTsoon")); }

    @Test (timeout=2000) public void test_VigenereCipher_9() { assertEquals("ZERO",        new VigenereCipher("A")    .decrypt("ZERO"));         }
    @Test (timeout=2000) public void test_VigenereCipher_10(){ assertEquals("GREEN",       new VigenereCipher("SLIME").decrypt("YcMQR"));        }
    @Test (timeout=2000) public void test_VigenereCipher_11(){ assertEquals("AAAAAAAA",    new VigenereCipher("SLIME").decrypt("SLIMESLI"));     }
    @Test (timeout=2000) public void test_VigenereCipher_12(){ assertEquals("exitLEFTsoon",new VigenereCipher("AAAB") .decrypt("exiuLEFUsooo")); }

    @Test (timeout=2000) public void test_VigenereCipher_13(){ assertEquals('X',new VigenereCipher("A") .encrypt1('X')); }
    @Test (timeout=2000) public void test_VigenereCipher_14(){ assertEquals('E',new VigenereCipher("E") .encrypt1('A')); }
    @Test (timeout=2000) public void test_VigenereCipher_15(){ assertEquals('Y',new VigenereCipher("B") .encrypt1('X')); }
    @Test (timeout=2000) public void test_VigenereCipher_16(){ assertEquals('A',new VigenereCipher("B") .encrypt1('>')); }
    @Test (timeout=2000) public void test_VigenereCipher_17(){ assertEquals('a',new VigenereCipher("B") .encrypt1('Z')); }
    @Test (timeout=2000) public void test_VigenereCipher_18(){ assertEquals('x',new VigenereCipher("a") .encrypt1('X')); }
    @Test (timeout=2000) public void test_VigenereCipher_19(){ assertEquals('r',new VigenereCipher("b") .encrypt1('Q')); }
    
    @Test (timeout=2000) public void test_VigenereCipher_20(){ assertEquals('X',new VigenereCipher("A") .decrypt1('X')); }
    @Test (timeout=2000) public void test_VigenereCipher_21(){ assertEquals('.',new VigenereCipher("F") .decrypt1('A')); }
    @Test (timeout=2000) public void test_VigenereCipher_22(){ assertEquals('W',new VigenereCipher("B") .decrypt1('X')); }
    @Test (timeout=2000) public void test_VigenereCipher_23(){ assertEquals('<',new VigenereCipher("B") .decrypt1('>')); }
    @Test (timeout=2000) public void test_VigenereCipher_24(){ assertEquals('Y',new VigenereCipher("B") .decrypt1('Z')); }
    @Test (timeout=2000) public void test_VigenereCipher_25(){ assertEquals('m',new VigenereCipher("N") .decrypt1('z')); }


    @Test (timeout=2000) public void test_Vigenere_26(){
 try {
     char c = new VigenereCipher("password",lowers).encrypt1('A');
 } catch (NotInAlphabetException e){ return; }
 fail("shouldn't encrypt any characters not in the alphabet.");
    }

    @Test (timeout=2000) public void test_Vigenere_27(){
 try {
     char c = new VigenereCipher("password",lowers).decrypt1('A');
 } catch (NotInAlphabetException e){ return; }
 fail("shouldn't encrypt any characters not in the alphabet.");
    }

    @Test (timeout=2000) public void test_Vigenere_28(){
 try {
     String s = new VigenereCipher("password",lowers).encrypt("HELLO");
 } catch (NotInAlphabetException e){ return; }
 fail("shouldn't encrypt any characters not in the alphabet.");
    }
    @Test (timeout=2000) public void test_Vigenere_29(){
 try {
     String s = new VigenereCipher("password",lowers).decrypt("HELLO");
 } catch (NotInAlphabetException e){ return; }
 fail("shouldn't encrypt any characters not in the alphabet.");
    }

    @Test (timeout=2000) public void test_Vigenere_30(){
 VigenereCipher v = new VigenereCipher("swordfish");
 assertEquals("Vigenere Cipher (password='swordfish')",v.toString());
    }
    
    
    /* 

    // Honors Section Tests

    MorseCipher mc = new MorseCipher();
    @Test (timeout=2000) public void test_Morse_1(){ assertEquals(new Alphabet(MorseCipher.letters),new MorseCipher().getAlphabet()); }
    @Test (timeout=2000) public void test_Morse_2(){
 try{ mc.encrypt1('a');}
 catch(UnsupportedOperationException e){return;}
 fail("should not allow encrypt1(char) to be called.");
    }
    @Test (timeout=2000) public void test_Morse_3(){
 try{ mc.encrypt1('a');}
 catch(UnsupportedOperationException e){return;}
 fail("should not allow decrypt1(char) to be called.");
    }
    

    @Test (timeout=2000) public void test_Morse_4(){
 assertEquals(".-",mc.encrypt("A"));
    }
    
    @Test (timeout=2000) public void test_Morse_5() {
 assertEquals("....   .   .-..   .-..   ---", mc.encrypt("HELLO"));
    }

    @Test (timeout=2000) public void test_Morse_6(){
 assertEquals(".----   ..---   ...--", mc.encrypt("123"));
    }

    @Test (timeout=2000) public void test_Morse_7(){
 assertEquals(".-       -...   -...       -.-.   -.-.   -.-.", mc.encrypt("a bb ccc"));
    }

    @Test (timeout=2000) public void test_Morse_8(){
 assertEquals(".-.   ..-   -.       ..-.   ---   .-.       -   ....   .       .   -..-   ..   -   ...", mc.encrypt("run for the exits"));
    }

    @Test (timeout=2000) public void test_Morse_9(){
 assertEquals("A",mc.decrypt(".-"));
    }
    
    @Test (timeout=2000) public void test_Morse_10() {
 assertEquals("HELLO",mc.decrypt("....   .   .-..   .-..   ---"));
    }

    @Test (timeout=2000) public void test_Morse_11(){
 assertEquals("123", mc.decrypt(".----   ..---   ...--"));
    }

    @Test (timeout=2000) public void test_Morse_12(){
 assertEquals("A BB CCC",mc.decrypt(".-       -...   -...       -.-.   -.-.   -.-."));
    }

    @Test (timeout=2000) public void test_Morse_13(){
 assertEquals("RUN FOR THE EXITS", mc.decrypt(".-.   ..-   -.       ..-.   ---   .-.       -   ....   .       .   -..-   ..   -   ..."));
    }

    
    @Test (timeout=2000) public void test_Morse_14(){
 try {
     String s = new MorseCipher().encrypt("%^&*");
 } catch (NotInAlphabetException e){ return; }
 fail("shouldn't encrypt any characters not in the alphabet.");
    }

    */
    
}
