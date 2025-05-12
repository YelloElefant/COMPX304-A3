import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QKE {
   private static final Random rand = new Random();

   public static QKESession performQKE(int numQubits) {
      // Alice's preparation
      List<QuBit> qubits = new ArrayList<>();
      List<Integer> aliceBits = new ArrayList<>();
      List<Integer> aliceBases = new ArrayList<>();

      // Prepare qubits
      for (int i = 0; i < numQubits; i++) {
         int bit = rand.nextInt(2);
         int basis = rand.nextInt(2);
         qubits.add(new QuBit(bit, basis));
         aliceBits.add(bit);
         aliceBases.add(basis);
      }

      // Bob's measurement
      List<Integer> bobBases = new ArrayList<>();
      List<Integer> bobBits = new ArrayList<>();

      for (QuBit qubit : qubits) {
         int basis = rand.nextInt(2); // Bob chooses random basis
         bobBases.add(basis);
         bobBits.add(qubit.measure(basis));
      }

      // Basis reconciliation
      List<Integer> matchingIndices = new ArrayList<>();
      for (int i = 0; i < numQubits; i++) {
         if (aliceBases.get(i).equals(bobBases.get(i))) {
            matchingIndices.add(i);
         }
      }

      // Build final key from matching indices
      String aliceKey = matchingIndices.stream()
            .map(aliceBits::get)
            .map(String::valueOf)
            .collect(Collectors.joining());

      String bobKey = matchingIndices.stream()
            .map(bobBits::get)
            .map(String::valueOf)
            .collect(Collectors.joining());

      return new QKESession(aliceKey, bobKey, matchingIndices.size());
   }

   public static class QKESession {
      public final String aliceKey;
      public final String bobKey;
      public final int matchingBits;

      public QKESession(String aliceKey, String bobKey, int matchingBits) {
         this.aliceKey = aliceKey;
         this.bobKey = bobKey;
         this.matchingBits = matchingBits;
      }
   }
}