/**
 * Represents a quantum bit (qubit) for the BB84 quantum key distribution
 * algorithm.
 * A qubit has a bit value (0 or 1) and a polarization basis (0 for circular, 1
 * for linear).
 */
public class QuBit {
   private int bit; // Stores the qubit value (0 or 1)
   private int polarization; // Stores the polarization basis (0 = circular, 1 = linear)

   /**
    * Constructs a new qubit with the given value and polarization.
    * 
    * @param bit          The qubit value (must be 0 or 1)
    * @param polarization The polarization basis (must be 0 or 1)
    * @throws IllegalArgumentException if bit or polarization are invalid
    */
   public QuBit(int bit, int polarization) {
      set(bit, polarization);
   }

   /**
    * Sets the qubit's value and polarization basis.
    * 
    * @param bit          The qubit value (must be 0 or 1)
    * @param polarization The polarization basis (must be 0 or 1)
    * @throws IllegalArgumentException if bit or polarization are invalid
    */
   public void set(int bit, int polarization) {
      // Validate input values
      if (bit != 0 && bit != 1) {
         throw new IllegalArgumentException("Bit must be 0 or 1.");
      }
      if (polarization != 0 && polarization != 1) {
         throw new IllegalArgumentException("Polarization must be '0' or '1'.");
      }
      this.bit = bit;
      this.polarization = polarization;
   }

   /**
    * Measures the qubit in the given basis.
    * 
    * @param measurementBasis The basis to measure in (0 = circular, 1 = linear)
    * @return The measured bit value
    * @throws IllegalArgumentException if measurementBasis is invalid
    */
   public int measure(int measurementBasis) {
      // If measurement basis matches current polarization, return the bit value
      if (measurementBasis == this.polarization) {
         return this.bit;
      }
      // If bases differ, collapse to random value in new basis (quantum behavior)
      else {
         int rndValue = (int) (Math.random() * 2); // Random 0 or 1
         this.bit = rndValue;
         this.polarization = measurementBasis; // Update to measurement basis
         return rndValue;
      }
   }

   /**
    * Gets the symbol representing the qubit's polarization state.
    * 
    * @return "↑" or "↓" for linear basis, "↺" or "⟳" for circular basis
    */
   public String getPolarizationSymbol() {
      if (this.polarization == 1) {
         return bit == 1 ? "↑" : "↓"; // Linear basis symbols
      } else {
         return bit == 1 ? "↺" : "⟳"; // Circular basis symbols
      }
   }

   /**
    * String representation of the qubit.
    * 
    * @return String showing polarization symbol and basis
    */
   @Override
   public String toString() {
      return "Qubit(" + getPolarizationSymbol() + ", basis=" + polarization + ")";
   }

   /**
    * Gets the current bit value.
    * 
    * @return 0 or 1
    */
   public int getBit() {
      return bit;
   }

   /**
    * Gets the current polarization basis.
    * 
    * @return 0 (circular) or 1 (linear)
    */
   public int getBasis() {
      return polarization;
   }
}