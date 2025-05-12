import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for the XORCipher class.
 * Verifies correct XOR encryption/decryption functionality and error handling.
 */
public class XORCipherTest {

   /**
    * Tests XOR operation when key length equals message length.
    * Verifies basic XOR functionality:
    * 0 XOR 1 = 1, 1 XOR 0 = 1, 0 XOR 0 = 0, 1 XOR 1 = 0
    */
   @Test
   public void sameLengthKey() {
      // 000111 XOR 111000 = 111111
      assertEquals("111111", XORCipher.apply("000111", "111000"));
   }

   /**
    * Tests XOR operation with a key shorter than the message.
    * Verifies key repetition works correctly.
    */
   @Test
   public void shortKeyRepeats() {
      // Key "101" repeats to become "101101" for message "000111"
      // 000111 XOR 101101 = 101010
      assertEquals("101010", XORCipher.apply("000111", "101"));
   }

   /**
    * Tests XOR operation with a key longer than the message.
    * Verifies only the needed portion of the key is used.
    */
   @Test
   public void longKeyTruncates() {
      // Only first 2 bits of key "111000" are used for message "01"
      // 01 XOR 11 = 10
      assertEquals("10", XORCipher.apply("01", "111000"));
   }

   /**
    * Tests that decrypting an encrypted message restores the original.
    * Demonstrates XOR's symmetric property for encryption/decryption.
    */
   @Test
   public void decryptRestoresOriginal() {
      String original = "010101";
      String key = "110";
      String encrypted = XORCipher.apply(original, key);
      // Applying XOR twice should return original message
      assertEquals(original, XORCipher.apply(encrypted, key));
   }

   /**
    * Tests XOR with all-zero message.
    * Verifies that 0 XOR keyBit = keyBit.
    */
   @Test
   public void allZerosReturnsKey() {
      // 0000 XOR 1010 = 1010
      assertEquals("1010", XORCipher.apply("0000", "1010"));
   }

   /**
    * Tests XOR with all-ones message.
    * Verifies that 1 XOR keyBit = inverted keyBit.
    */
   @Test
   public void allOnesInvertsKey() {
      // 1111 XOR 1010 = 0101 (bitwise inversion)
      assertEquals("0101", XORCipher.apply("1111", "1010"));
   }

   /**
    * Tests that null message throws IllegalArgumentException.
    */
   @Test(expected = IllegalArgumentException.class)
   public void nullMessageThrows() {
      XORCipher.apply(null, "101");
   }

   /**
    * Tests that empty key throws IllegalArgumentException.
    */
   @Test(expected = IllegalArgumentException.class)
   public void emptyKeyThrows() {
      XORCipher.apply("0101", "");
   }

   /**
    * Tests that non-binary message throws IllegalArgumentException.
    */
   @Test(expected = IllegalArgumentException.class)
   public void nonBinaryMessageThrows() {
      XORCipher.apply("0102", "101"); // '2' is invalid
   }

   /**
    * Tests multiple message/key pairs to verify general correctness.
    * Each test case is {message, key}.
    * Verifies encryption then decryption returns original message.
    */
   @Test
   public void multipleRoundtrips() {
      String[][] testCases = {
            { "0", "1" }, // Single bit
            { "0101", "1010" }, // Equal length
            { "111000111", "010101" }, // Repeating key
            { "10101010101010", "11001100" } // Long message
      };

      for (String[] testCase : testCases) {
         String message = testCase[0];
         String key = testCase[1];
         String encrypted = XORCipher.apply(message, key);
         String decrypted = XORCipher.apply(encrypted, key);
         assertEquals("Roundtrip failed for message: " + message + ", key: " + key,
               message, decrypted);
      }
   }
}