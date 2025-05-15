public class SecureMessageExchange {
   public static void main(String[] args) {
      int numQubits = 256;
      String message = "0100110101100101"; // Example 16-bit binary message

      System.out.println("Performing QKE with " + numQubits + " qubits...");
      QKE.QKESession session = QKE.performQKE(numQubits);

      double matchingRatio = (double) session.matchingBits / numQubits;

      System.out.println("Matching ratio: " + (matchingRatio * 100) + "%");
      System.out.println("Alice Key: " + session.aliceKey);
      System.out.println("Bob Key:   " + session.bobKey);

      int keyLength = message.length();
      if (session.aliceKey.length() < keyLength) {
         System.err.println("Key too short! Needed: " + keyLength);
         return;
      }

      String encryptionKey = session.aliceKey.substring(0, keyLength);
      String cipherText = XORCipher.encrypt(message, encryptionKey);

      String decryptionKey = session.bobKey.substring(0, keyLength);
      String plainText = XORCipher.decrypt(cipherText, decryptionKey);

      System.out.println("Original Message: " + message);
      System.out.println("Encrypted Message: " + cipherText);
      System.out.println("Decrypted Message: " + plainText);
   }
}