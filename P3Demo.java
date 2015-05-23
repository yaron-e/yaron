// Demonstrate the use of interchangeable ciphers. Most of the classes
// in the project will need to be complete for this code to work
// properly.
public class P3Demo {
  public static void main(String[] args){
    Alphabet alphabet = Alphabet.DEFAULT;
    String encrypted, decrypted;
    
    Object a1 = new Alphabet("ABCabc");
    Object a2 = new Alphabet("ABCabc");
    
    System.out.println(new CaesarCipher(1, (Alphabet) a1).encrypt("w"));
    System.out.println(a2.toString());
    /* Cipher c;
    c= new CaesarCipher(1);
    encrypted = c.encrypt("a");
   // System.out.println(encrypted);
    
    
    
    String message = "All your base are belong to us.";

    c = new CaesarCipher(2,alphabet);
    encrypted = c.encrypt(message);
    decrypted = c.decrypt(encrypted);
    System.out.printf("Cipher:    %s\n",c.toString());
    System.out.printf("Message:   %s\n",message);
    System.out.printf("Encrypted: %s\n",encrypted);
    System.out.printf("Decrypted: %s\n",decrypted);
    System.out.println();
    // Cipher:    Caesar Cipher (shift=2)
    // Message:   All your base are belong to us.
    // Encrypted: Cnn2 qwt2dcug2ctg2dgnqpi2vq2wu?
    // Decrypted: All your base are belong to us.

    c = new CaesarCipher(17,alphabet);
    encrypted = c.encrypt(message);
    decrypted = c.decrypt(encrypted);
    System.out.printf("Cipher:    %s\n",c.toString());
    System.out.printf("Message:   %s\n",message);
    System.out.printf("Encrypted: %s\n",encrypted);
    System.out.printf("Decrypted: %s\n",decrypted);
    System.out.println();
    // Cipher:    Caesar Cipher (shift=17)
    // Message:   All your base are belong to us.
    // Encrypted: R22&%5!8&sr9v&r8v&sv254x&05&!9M
    // Decrypted: All your base are belong to us.

    //String message = "ABCDE";
    c= new VigenereCipher("B", alphabet);
    encrypted = c.encrypt(message);
    				System.err.println("Encrypted: "+encrypted);
    decrypted = c.decrypt(encrypted);
    				System.err.println("Decrypted: "+decrypted);
    
    
    c = new VigenereCipher("Cats",alphabet);
    encrypted = c.encrypt(message);
    decrypted = c.decrypt(encrypted);
    System.out.printf("Cipher:    %s\n",c.toString());
    System.out.printf("Message:   %s\n",message);
    System.out.printf("Encrypted: %s\n",encrypted);
    System.out.printf("Decrypted: %s\n",decrypted);
    System.out.println();*/
    // Cipher:    Vigenere Cipher (password='Cats')
    // Message:   All your base are belong to us.
    // Encrypted: C!|D $<,21(.g](,g])+n$:=2('Dw*o
    // Decrypted: All your base are belong to us.

    // Comment out this section if not implementing the MorseCipher
   /* message = "ALLYOURBASE";
    c = new MorseCipher();
    encrypted = c.encrypt(message);
    decrypted = c.decrypt(encrypted);
    System.out.printf("Cipher:    %s\n",c.toString());
    System.out.printf("Message:   %s\n",message);
    System.out.printf("Encrypted: %s\n",encrypted);
    System.out.printf("Decrypted: %s\n",decrypted);
    System.out.println();*/
    // Cipher:    Morse Cipher
    // Message:   ALLYOURBASEAREBELONGTOUS
    // Encrypted: .-   .-..   .-..   -.--   ---   ..-   .-.   -...   .-   ...   .
    // Decrypted: ALLYOURBASEAREBELONGTOUS
  }
}
