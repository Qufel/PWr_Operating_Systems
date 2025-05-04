package paging.process;

import paging.Main;

import java.util.Arrays;
import java.util.Random;

public class Process {

    private final int id;
    private final int[] sequence;

    public Process(int id, int[] sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    public int getId() {
        return id;
    }

    public int[] getSequence() {
        return sequence;
    }

    public static Process generateProcess(int id, int seed) {
        Random random = new Random(seed);

        boolean locality = false;

        // Create a new sequence
        int length = random.nextInt(Main.MIN_REFERENCE_COUNT, Main.MAX_REFERENCE_COUNT + 1);
        int[] sequence = new int[length];

        int localityLength = 1;
        int localities = 0;

        int[] localityReferences;
        float[] referenceWeights = new float[Main.LOCALITY_REFERENCES];

        for (int i = 0; i < length; i++) {

            //region Locality Indication

            // Check for locality
            float l = random.nextFloat();
            boolean localityChange = locality;
            locality = ((l < Main.LOCALITY_START_CHANCE || locality) ^ (l < Main.LOCALITY_END_CHANCE && localityLength > Main.MIN_LOCALITY_CHAIN)) && localities < Main.MAX_LOCALITIES && i > Main.LOCALITY_DEPTH;

            // Indicate if locality appeared
            if (!localityChange && locality)
                localities++;

            //endregion

            // Apply locality effect
            if (locality) {
                System.out.println("Ref: " + i + " Locality: " + localityLength);
                int[] refs = getWeightedReferences(i, sequence, referenceWeights);
            } else
                sequence[i] = random.nextInt(1, Main.MAX_REFERENCE_ID + 1);

            localityLength = locality ? localityLength + 1 : 1;
        }

        return new Process(id, sequence);
    }

    /**
     * Searches for most frequent references.
     * @param index an index from which to look towards the head of a references array.
     * @param references references to take a weighted count.
     * @param weights passed as reference, return a weighted count of reference in the whole sequence.
     * @return an array of most frequent references.
     */
    private static int[] getWeightedReferences(int index, int[] references, float[] weights) {

        int n = Math.min(index, Main.LOCALITY_DEPTH);

        int[] weightedReferences = new int[n];
        weights = new float[n];

        for (int i = 0; i < n; i++) {
            weights[i] = 0;
        }

        for (int i = 1; i < n; i++) {
            int reference = references[index - i];
            int ind = i - 1;
            for (int j = 0; j < i; j++) {
                if (weightedReferences[j] == reference) {
                    weights[j] += 1f;
                    ind = j;
                }
            }
            weightedReferences[ind] = reference;
            if (ind == i - 1)
                weights[ind] += 1f;
        }

        return weightedReferences;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Process ID: ").append(id).append("\n");
        sb.append("Sequence: ").append(Arrays.toString(sequence)).append("\n");
        return sb.toString();
    }
}
