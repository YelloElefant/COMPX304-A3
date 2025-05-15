public class Test {
   public static void main(String[] args) {
      int numQubits = 32;
      String message = "11010001000101010110100111101100101100101"; // 16-bit message
      int tries = Integer.parseInt(args[0].isEmpty() ? "1" : args[0]);

      System.out.println("Performing QKE with " + numQubits + " qubits...");
      // Perform QKE in a loop to simulate multiple sessions
      for (int i = 0; i < tries; i++) {
         QKE.QKESession session = QKE.performQKE(numQubits);

         // After QKE.performQKE()
         double matchingRatio = (double) session.matchingBits / numQubits;

         if (matchingRatio < 0.25) { // Threshold: 25% matching
            System.err.print("Warning: Low matching ratio (" + (matchingRatio * 100) + "%). Possible eavesdropping.");
         } else if (session.aliceKey.length() < numQubits) {
            System.err.print("Need more qubits to generate " + numQubits + "-bit key.");
         } else {
            System.out.print("Perfect key exchange: ");
         }

         System.out.print(", Matching bases: " + session.matchingBits +
               " (≈" + (session.matchingBits * 100 / numQubits) + "%)");
         System.out.print(", Alice's key: " + session.aliceKey);
         System.out.print(", Bob's key:   " + session.bobKey + "\n");
      }
   }

   public static String toBinaryString(String input) {
      StringBuilder binary = new StringBuilder();
      for (char c : input.toCharArray()) {
         String binaryString = Integer.toBinaryString(c);
         // Pad with leading zeros to make it 8 bits
         while (binaryString.length() < 8) {
            binaryString = "0" + binaryString;
         }
         binary.append(binaryString);
      }
      return binary.toString();
   }

   public static String fromBinaryString(String binary) {
      StringBuilder result = new StringBuilder();
      for (int i = 0; i < binary.length(); i += 8) {
         String byteString = binary.substring(i, Math.min(i + 8, binary.length()));
         int charCode = Integer.parseInt(byteString, 2);
         result.append((char) charCode);
      }
      return result.toString();
   }
}
