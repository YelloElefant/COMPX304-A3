import static org.junit.Assert.*;
import org.junit.Test;

public class QKETest {

   @Test
   public void testKeyLengthsMatch() {
      QKE.QKESession session = QKE.performQKE(100);
      assertEquals(session.aliceKey.length(), session.bobKey.length());
   }

   @Test
   public void testSomeMatchingBits() {
      QKE.QKESession session = QKE.performQKE(100);
      assertTrue(session.matchingBits > 0);
   }

   @Test
   public void testSufficientKeyLengthForMessage() {
      QKE.QKESession session = QKE.performQKE(128);
      assertTrue(session.aliceKey.length() >= 16);
   }

   @Test
   public void testPerfectKeyUseForEncryption() {
      String message = "1010101010101010";
      QKE.QKESession session = QKE.performQKE(128);
      if (session.aliceKey.length() >= message.length()) {
         String k = session.aliceKey.substring(0, message.length());
         String cipher = XORCipher.encrypt(message, k);
         String plain = XORCipher.decrypt(cipher, k);
         assertEquals(message, plain);
      }
   }
}
