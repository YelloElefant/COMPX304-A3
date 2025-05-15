public class XORCipher {
   public static String encrypt(String message, String key) {
      validateInput(message, key);
      String repeatedKey = repeatKey(key, message.length());
      StringBuilder result = new StringBuilder();

      for (int i = 0; i < message.length(); i++) {
         int bit = message.charAt(i) - '0';
         int keyBit = repeatedKey.charAt(i) - '0';
         result.append(bit ^ keyBit);
      }

      return result.toString();
   }

   public static String decrypt(String cipher, String key) {
      return encrypt(cipher, key); // XOR again reverses it
   }

   private static String repeatKey(String key, int length) {
      StringBuilder sb = new StringBuilder();
      while (sb.length() < length) {
         sb.append(key);
      }
      return sb.substring(0, length);
   }

   private static void validateInput(String str, String key) {
      if (str == null || key == null || str.isEmpty() || key.isEmpty()) {
         throw new IllegalArgumentException("Message and key must not be null or empty.");
      }
      if (!str.matches("[01]+") || !key.matches("[01]+")) {
         throw new IllegalArgumentException("Both message and key must be binary strings.");
      }
   }
}
