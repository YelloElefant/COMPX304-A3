import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for the QuBit class.
 * Verifies correct behavior of qubit initialization, measurement, and state
 * changes.
 */
public class QuBitTest {

   @Test
   public void testValidConstruction() {
      QuBit qubit = new QuBit(1, '+');
      assertEquals(1, qubit.getValue());
      assertEquals('+', qubit.getBasis());
   }

   @Test
   public void testInvalidValue() {
      assertThrows(IllegalArgumentException.class, () -> new QuBit(2, '+'));
   }

   @Test
   public void testInvalidBasis() {
      assertThrows(IllegalArgumentException.class, () -> new QuBit(1, '*'));
   }

   @Test
   public void testCorrectMeasurement() {
      QuBit qubit = new QuBit(1, '+');
      assertEquals(1, qubit.measure('+'));
   }

   @Test
   public void testIncorrectMeasurementRandomResult() {
      QuBit qubit = new QuBit(1, '+');
      int result = qubit.measure('x');
      assertTrue(result == 0 || result == 1);
   }

   @Test
   public void testInvalidMeasurementBasis() {
      QuBit qubit = new QuBit(1, '+');
      assertThrows(IllegalArgumentException.class, () -> qubit.measure('!'));
   }
}