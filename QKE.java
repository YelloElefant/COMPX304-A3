
import java.util.*;

public class QKE {
   private static boolean verbose = false;

   public static void setVerbose(boolean v) {
      verbose = v;
   }

   public static class QKESession {
      public String aliceKey;
      public String bobKey;
      public int matchingBits;
   }

   public static QKESession performQKE(int numQubits) {
      List<QuBit> qubits = new ArrayList<>();
      List<Character> aliceBases = new ArrayList<>();
      List<Character> bobBases = new ArrayList<>();
      List<Integer> bobMeasurements = new ArrayList<>();

      Random rand = new Random();

      // Alice prepares qubits
      if (verbose) {
         System.out.println("[QKE] Alice is preparing qubits...");
      }
      for (int i = 0; i < numQubits; i++) {
         int value = rand.nextBoolean() ? 1 : 0;
         char basis = rand.nextBoolean() ? '+' : 'x';
         qubits.add(new QuBit(value, basis));
         aliceBases.add(basis);
      }

      // Bob measures qubits
      if (verbose) {
         System.out.println("[QKE] Bob is measuring qubits...");
      }
      for (int i = 0; i < numQubits; i++) {
         char basis = rand.nextBoolean() ? '+' : 'x';
         bobBases.add(basis);
         bobMeasurements.add(qubits.get(i).measure(basis));
      }

      // Key extraction
      StringBuilder aliceKey = new StringBuilder();
      StringBuilder bobKey = new StringBuilder();
      int matches = 0;

      for (int i = 0; i < numQubits; i++) {
         if (aliceBases.get(i) == bobBases.get(i)) {
            int aVal = qubits.get(i).getValue();
            int bVal = bobMeasurements.get(i);
            aliceKey.append(aVal);
            bobKey.append(bVal);
            matches++;
            if (verbose) {
               System.out.println("[QKE] Match at " + i + ": basis='" + aliceBases.get(i) + "', value=" + aVal);
            }
         }
      }

      QKESession session = new QKESession();
      session.aliceKey = aliceKey.toString();
      session.bobKey = bobKey.toString();
      session.matchingBits = matches;
      if (verbose) {
         System.out.println("[QKE] Total matching bits: " + matches);
         System.out.println("[QKE] Alice Key: " + session.aliceKey);
         System.out.println("[QKE] Bob Key:   " + session.bobKey);
      }
      return session;
   }
}
