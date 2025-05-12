/**
 * Performs XOR encryption/decryption on binary strings.
 * Note: Since XOR is symmetric, the same method handles both operations.
 */
public class XORCipher {

   /**
    * Applies XOR operation between a binary message and key.
    * 
    * @param message The binary string to process (must contain only '0's and '1's)
    * @param key     The binary key (must contain only '0's and '1's and not be
    *                empty)
    * @return The result of XOR operation as a binary string
    * @throws IllegalArgumentException if inputs are invalid
    */
   public static String apply(String message, String key) {
      validateInput(message, key);

      StringBuilder result = new StringBuilder();
      for (int i = 0; i < message.length(); i++) {
         int messageBit = message.charAt(i) - '0'; // Convert char '0'/'1' to int 0/1
         int keyBit = key.charAt(i % key.length()) - '0';
         result.append(messageBit ^ keyBit); // XOR operation
      }
      return result.toString();
   }

   /**
    * Validates the input binary strings.
    * 
    * @param input The binary string to validate
    * @param key   The binary key to validate
    * @throws IllegalArgumentException if inputs are invalid
    */
   private static void validateInput(String input, String key) {
      if (input == null || key == null) {
         throw new IllegalArgumentException("Input and key cannot be null");
      }
      if (key.isEmpty()) {
         throw new IllegalArgumentException("Key cannot be empty");
      }
      if (!input.matches("[01]+")) {
         throw new IllegalArgumentException("Message must be binary (0s and 1s)");
      }
      if (!key.matches("[01]+")) {
         throw new IllegalArgumentException("Key must be binary (0s and 1s)");
      }
   }
}