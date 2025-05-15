import java.util.Random;

public class QuBit {
   private int value;
   private char basis;
   private static boolean verbose = false;

   public static void setVerbose(boolean v) {
      verbose = v;
   }

   public QuBit(int value, char basis) {
      if (value != 0 && value != 1) {
         throw new IllegalArgumentException("Value must be 0 or 1.");
      }
      if (basis != '+' && basis != 'x') {
         throw new IllegalArgumentException("Basis must be '+' or 'x'.");
      }
      this.value = value;
      this.basis = basis;
      if (verbose)
         System.out.println("[QuBit] Created qubit with value=" + value + " and basis=" + basis);
   }

   public int measure(char measurementBasis) {
      if (measurementBasis != '+' && measurementBasis != 'x') {
         throw new IllegalArgumentException("Measurement basis must be '+' or 'x'.");
      }
      int result;
      if (this.basis == measurementBasis) {
         result = this.value;
      } else {
         result = new Random().nextBoolean() ? 1 : 0;
      }
      if (verbose)
         System.out.println("[QuBit] Measured with basis=" + measurementBasis + " => " + result);
      return result;
   }

   public int getValue() {
      return value;
   }

   public char getBasis() {
      return basis;
   }
}