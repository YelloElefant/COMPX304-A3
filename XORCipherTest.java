import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for the XORCipher class.
 * Verifies correct XOR encryption/decryption functionality and error handling.
 */
public class XORCipherTest {

   @Test
   public void testEncryptDecryptBasic() {
      String message = "1010";
      String key = "1100";
      String encrypted = XORCipher.encrypt(message, key);
      String decrypted = XORCipher.decrypt(encrypted, key);
      assertEquals(message, decrypted);
   }

   @Test
   public void testEncryptWithShortKey() {
      String message = "101010";
      String key = "01";
      String encrypted = XORCipher.encrypt(message, key);
      String decrypted = XORCipher.decrypt(encrypted, key);
      assertEquals(message, decrypted);
   }

   @Test
   public void testInvalidBinaryCharacters() {
      assertThrows(IllegalArgumentException.class, () -> XORCipher.encrypt("abc", "0101"));
      assertThrows(IllegalArgumentException.class, () -> XORCipher.encrypt("0101", "abc"));
   }

   @Test
   public void testEmptyInputs() {
      assertThrows(IllegalArgumentException.class, () -> XORCipher.encrypt("", "1"));
      assertThrows(IllegalArgumentException.class, () -> XORCipher.encrypt("1010", ""));
   }
}