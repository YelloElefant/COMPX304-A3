
public class SecureMessageExchange {
   public static void main(String[] args) {
      int numQubits = 16;
      String message = "0100110101100101"; // Example 16-bit binary message

      // Enable verbose logging
      // check for -v or --verbose flag
      for (String arg : args) {
         if (arg.equals("-v") || arg.equals("--verbose")) {
            System.out.println("Verbose mode enabled.");
            QuBit.setVerbose(true);
            QKE.setVerbose(true);
            XORCipher.setVerbose(true);
            break;
         }
      }

      System.out.println("Performing QKE with " + numQubits + " qubits...");
      QKE.QKESession session = QKE.performQKE(numQubits);

      double matchingRatio = (double) session.matchingBits / numQubits;

      System.out.println("Matching ratio: " + (matchingRatio * 100) + "%");

      System.out.println("-- Session keys --");
      System.out.println("Alice's key: " + session.aliceKey);
      System.out.println("Bob's key:   " + session.bobKey);

      int keyLength = session.aliceKey.length();
      if (matchingRatio < 0.25) {
         System.out.println("Not enough matching bits for secure communication.");
         return;
      }

      String encryptionKey = session.aliceKey.substring(0, keyLength);
      String cipherText = XORCipher.encrypt(message, encryptionKey);

      String decryptionKey = session.bobKey.substring(0, keyLength);
      String plainText = XORCipher.decrypt(cipherText, decryptionKey);

      System.out.println("Original Message:  " + message);
      System.out.println("Encrypted Message: " + cipherText);
      System.out.println("Decrypted Message: " + plainText);
   }
}