import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

public class QKETest {
   private static final int TEST_ITERATIONS = 100;

   // ===== Core Functionality Tests =====
   @Test
   public void testKeyAgreement() {
      QKE.QKESession session = QKE.performQKE(128);
      assertEquals("Alice and Bob's keys should match exactly",
            session.aliceKey, session.bobKey);
   }

   @Test
   public void testMatchingBitsWithinExpectedRange() {
      int numQubits = 256;
      QKE.QKESession session = QKE.performQKE(numQubits);

      double ratio = (double) session.matchingBits / numQubits;
      assertTrue("Matching ratio should be >25% (was " + (ratio * 100) + "%)",
            ratio > 0.25);
      assertTrue("Matching ratio should be <75% (was " + (ratio * 100) + "%)",
            ratio < 0.75);
   }

   // ===== Stream Length Experiments =====
   @Test
   public void testStreamLength16() {
      testStreamLength(16);
   }

   @Test
   public void testStreamLength256() {
      testStreamLength(256);
   }

   @Test
   public void testStreamLength1024() {
      testStreamLength(1024);
   }

   private void testStreamLength(int numQubits) {
      QKE.QKESession session = QKE.performQKE(numQubits);

      // Verify keys match
      assertEquals("Keys should match for stream length " + numQubits,
            session.aliceKey, session.bobKey);

      // Verify matching ratio
      double ratio = (double) session.matchingBits / numQubits;
      assertTrue("Ratio for " + numQubits + " qubits should be >25% (was " + (ratio * 100) + "%)",
            ratio > 0.25);
      assertTrue("Ratio for " + numQubits + " qubits should be <75% (was " + (ratio * 100) + "%)",
            ratio < 0.75);

      // Test encryption if we have enough bits
      if (session.aliceKey.length() >= 8) {
         String testMessage = "11010001"; // 8-bit test message
         String key = session.aliceKey.substring(0, 8);
         String encrypted = XORCipher.apply(testMessage, key);
         String decrypted = XORCipher.apply(encrypted, key);
         assertEquals("Roundtrip encryption should work for stream length " + numQubits,
               testMessage, decrypted);
      }
   }

   // ===== Statistical Validation =====
   @Test
   public void testStatisticalProperties() {
      int numQubits = 1024;
      int totalMatches = 0;
      int totalKeys = 0;

      for (int i = 0; i < TEST_ITERATIONS; i++) {
         QKE.QKESession session = QKE.performQKE(numQubits);
         totalMatches += session.matchingBits;
         totalKeys += session.aliceKey.length();

         // Verify keys match in every iteration
         assertEquals("Keys should match in all iterations",
               session.aliceKey, session.bobKey);
      }

      double avgMatchRatio = (double) totalMatches / (TEST_ITERATIONS * numQubits);
      assertTrue("Average match ratio should be ~50% (was " + (avgMatchRatio * 100) + "%)",
            avgMatchRatio > 0.45 && avgMatchRatio < 0.55);
   }

   // ===== Edge Cases =====
   @Test
   public void testSingleQubit() {
      QKE.QKESession session = QKE.performQKE(1);
      assertTrue("Key should be 0 or 1 for single qubit",
            session.aliceKey.equals("0") || session.aliceKey.equals("1"));
      assertEquals("Keys should match for single qubit",
            session.aliceKey, session.bobKey);
   }

   @Test
   public void testNoMatchingBases() {
      // Forced test case where bases never match
      List<Integer> aliceBases = List.of(0, 0, 0);
      List<Integer> bobBases = List.of(1, 1, 1);
      List<Integer> bobBits = List.of(1, 0, 1);

      String key = deriveKey(aliceBases, bobBases, bobBits);
      assertTrue("Key should be empty when no bases match",
            key.isEmpty());
   }

   @Test
   public void testAllMatchingBases() {
      // Forced test case where all bases match
      List<Integer> aliceBases = List.of(0, 1, 0);
      List<Integer> bobBases = List.of(0, 1, 0);
      List<Integer> bobBits = List.of(1, 0, 1);

      String key = deriveKey(aliceBases, bobBases, bobBits);
      assertEquals("Key should include all bits when all bases match",
            "101", key);
   }

   // ===== Error Handling =====
   @Test(expected = IllegalArgumentException.class)
   public void testZeroQubits() {
      QKE.performQKE(0);
   }

   // ===== Helper Methods =====
   /**
    * Exposed for testing basis reconciliation
    */
   static String deriveKey(List<Integer> aliceBases,
         List<Integer> bobBases,
         List<Integer> bobValues) {
      StringBuilder key = new StringBuilder();
      for (int i = 0; i < aliceBases.size(); i++) {
         if (aliceBases.get(i).equals(bobBases.get(i))) {
            key.append(bobValues.get(i));
         }
      }
      return key.toString();
   }
}
