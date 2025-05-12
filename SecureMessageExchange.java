public class SecureMessageExchange {
   public static void main(String[] args) {
      int numQubits = 16;
      String message = "11010001000101010110100111101100101100101"; // 16-bit message

      System.out.println("Performing QKE with " + numQubits + " qubits...");
      QKE.QKESession session = QKE.performQKE(numQubits);

      // After QKE.performQKE()
      double matchingRatio = (double) session.matchingBits / numQubits;

      if (matchingRatio < 0.25) { // Threshold: 25% matching
         System.err.println("Warning: Low matching ratio (" + (matchingRatio * 100) + "%). Possible eavesdropping.");
      } else if (session.aliceKey.length() < numQubits) {
         System.err.println("Need more qubits to generate " + numQubits + "-bit key.");
      }

      System.out.println("Matching bases: " + session.matchingBits +
            " (≈" + (session.matchingBits * 100 / numQubits) + "%)");
      System.out.println("Alice's key: " + session.aliceKey);
      System.out.println("Bob's key:   " + session.bobKey);

      String key = session.aliceKey;

      System.out.println("\nEncrypting with key: " + key);
      String encrypted = XORCipher.apply(message, key);
      String decrypted = XORCipher.apply(encrypted, key);

      System.out.println("Original:  " + message);
      System.out.println("Encrypted: " + encrypted);
      System.out.println("Decrypted: " + decrypted);
      System.out.println("Success: " + message.equals(decrypted));
   }
}