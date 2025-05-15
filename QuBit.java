import java.util.Random;

public class QuBit {
   private int value;
   private char basis;

   public QuBit(int value, char basis) {
      if (value != 0 && value != 1) {
         throw new IllegalArgumentException("Value must be 0 or 1.");
      }
      if (basis != '+' && basis != 'x') {
         throw new IllegalArgumentException("Basis must be '+' or 'x'.");
      }
      this.value = value;
      this.basis = basis;
   }

   public int measure(char measurementBasis) {
      if (measurementBasis != '+' && measurementBasis != 'x') {
         throw new IllegalArgumentException("Measurement basis must be '+' or 'x'.");
      }
      if (this.basis == measurementBasis) {
         return this.value;
      } else {
         return new Random().nextBoolean() ? 1 : 0;
      }
   }

   public int getValue() {
      return value;
   }

   public char getBasis() {
      return basis;
   }
}