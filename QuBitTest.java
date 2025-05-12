import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for the QuBit class.
 * Verifies correct behavior of qubit initialization, measurement, and state
 * changes.
 */
public class QuBitTest {

   // Tests valid construction of a qubit
   @Test
   public void testValidConstruction() {
      QuBit q = new QuBit(1, 1);
      assertEquals(1, q.getBit()); // Verify bit value
      assertEquals(1, q.getBasis()); // Verify polarization basis
   }

   // Tests that invalid bit values throw an exception
   @Test
   public void testInvalidBitThrows() {
      assertThrows(IllegalArgumentException.class, () -> {
         new QuBit(2, 1); // 2 is invalid bit value
      });
   }

   // Tests that invalid polarization bases throw an exception
   @Test
   public void testInvalidBasisThrows() {
      assertThrows(IllegalArgumentException.class, () -> {
         new QuBit(0, 3); // 3 is invalid basis
      });
   }

   // Tests polarization symbols for linear basis (↑/↓)
   @Test
   public void testGetPolarizationSymbolLinear() {
      QuBit q1 = new QuBit(1, 1); // Linear up
      QuBit q0 = new QuBit(0, 1); // Linear down
      assertEquals("↑", q1.getPolarizationSymbol());
      assertEquals("↓", q0.getPolarizationSymbol());
   }

   // Tests polarization symbols for circular basis (↺/⟳)
   @Test
   public void testGetPolarizationSymbolCircular() {
      QuBit q1 = new QuBit(1, 0); // Circular counterclockwise
      QuBit q0 = new QuBit(0, 0); // Circular clockwise
      assertEquals("↺", q1.getPolarizationSymbol());
      assertEquals("⟳", q0.getPolarizationSymbol());
   }

   // Tests measurement when measurement basis matches qubit's polarization
   @Test
   public void testMeasureSameBasisReturnsBit() {
      QuBit q = new QuBit(1, 1); // Linear basis
      int result = q.measure(1); // Measure in same basis
      assertEquals(1, result); // Should return original bit
   }

   // Tests quantum behavior when measurement basis differs from qubit's
   // polarization
   @Test
   public void testMeasureDifferentBasisReturnsRandom() {
      boolean gotZero = false, gotOne = false;
      // Test multiple times to verify random behavior
      for (int i = 0; i < 100; i++) {
         QuBit q = new QuBit(1, 0); // Circular basis
         int result = q.measure(1); // Measure in linear basis
         if (result == 0)
            gotZero = true;
         if (result == 1)
            gotOne = true;
         // Early exit if both outcomes observed
         if (gotZero && gotOne)
            break;
      }
      // Verify both 0 and 1 were observed (quantum randomness)
      assertTrue("Should get both 0 and 1 with enough trials", gotZero && gotOne);
   }

   // Tests that measurement updates the qubit's state when bases differ
   @Test
   public void testMeasureDifferentBasisUpdatesState() {
      QuBit q = new QuBit(1, 0); // Circular basis
      q.measure(1); // Measure in linear basis
      assertEquals(1, q.getBasis()); // Polarization should update to measurement basis
      // Bit value is now random (can't assert specific value)
   }
}