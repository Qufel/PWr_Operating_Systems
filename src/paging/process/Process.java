package paging.process;

import paging.Main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Process {

    private final int id;
    private final int[] pages;
    private final int[] sequence;

    /**
     * Which frames process is allowed to use.
     */
    private int[] frameRange;

    /**
     * How many page faults occurred during execution of this process.
     */
    private int pageFaults = 0;

    /**
     * How many times thrashing occurred.
     */
    private int thrashing = 0;

    /**
     * What page in sequence process should point to.
     */
    private int sequenceIndex = 0;

    private final LinkedList<Boolean> pageFaultsStack;

    public Process(int id, int[] pages, int[] sequence) {
        this.id = id;
        this.pages = pages;
        this.sequence = sequence;

        this.pageFaultsStack = new LinkedList<>();

        this.frameRange = new int[] {0, Main.FRAMES - 1};
    }

    public int getId() {
        return id;
    }

    public int[] getSequence() {
        return sequence;
    }

    public int getLength() {
        return sequence.length;
    }

    public boolean isFinished() {
        return sequenceIndex == sequence.length;
    }

    public int getPage(int index) {
        return sequence[index];
    }

    public int getNextPage() {
        return sequence[sequenceIndex++];
    }

    public int getIndex() { return sequenceIndex; }

    //region Allowed Frames

    public int getFirstAllowedFrame() {
        return frameRange[0];
    }

    public int getLastAllowedFrame() {
        return frameRange[1];
    }

    public void setFirstAllowedFrame(int frame) {
        frameRange[0] = frame;
    }

    public void setLastAllowedFrame(int frame) {
        frameRange[1] = frame;
    }

    public void setAllowedFrameRange(int first, int last) {
        frameRange[0] = first;
        frameRange[1] = last;
    }

    //endregion

    //region Process Generation

    public static Process generateProcess(int id, int seed, int firstReference, int lastReference) {
        Random random = new Random(seed);

        boolean locality = false;

        // Create a new sequence
        int sequenceLength = random.nextInt(Main.MIN_REFERENCE_COUNT, Main.MAX_REFERENCE_COUNT + 1);
        int pagesLength = lastReference - firstReference + 1;

        int[] pages = new int[pagesLength];

        for (int i = 0; i < pagesLength; i++) {
            pages[i] = i + firstReference;
        }

        int[] sequence = new int[sequenceLength];

        int localityLength = 1;
        int localities = 0;

        int localityStart;

        int[] localityReferences = new int[0];

        for (int i = 0; i < sequenceLength; i++) {

            //region Locality Indication

            // Check for locality
            float l = random.nextFloat();
            boolean localityChange = locality;
            locality = isLocality(locality, l, localityLength, localities, i);

            localityLength = locality ? localityLength + 1 : 0;

            // Indicate if locality appeared
            if (!localityChange && locality) {
                localities++;

                localityStart = Math.max(Math.abs(random.nextInt() % (pagesLength)) - Main.LOCALITY_REFERENCES, 0);
                localityReferences = new int[Main.LOCALITY_REFERENCES];
                System.arraycopy(pages, localityStart, localityReferences, 0, Main.LOCALITY_REFERENCES);
            }

            //endregion

            // Apply locality effect
            if (locality) {
                sequence[i] = localityReferences[Math.abs(random.nextInt()) % Main.LOCALITY_REFERENCES];
            } else
                sequence[i] = random.nextInt( ((id - 1) * Main.REFERENCES_WIDTH + 1), id * Main.REFERENCES_WIDTH + 1 );

        }

        return new Process(id, pages, sequence);
    }

    public static Process generateCyclicProcess(int id, int firstReference, int lastReference) {

        int[] pages = new int[lastReference - firstReference + 1];
        int[] sequence = new int[Main.MAX_REFERENCE_COUNT];

        for (int i = 0; i < pages.length; i++) {
            pages[i] = i + firstReference;
        }

        for (int i = 0; i < Main.MAX_REFERENCE_COUNT; i++) {
            sequence[i] = pages[i % pages.length];
        }

        return new Process(id, pages, sequence);
    }

    private static boolean isLocality(boolean locality, float l, int localityLength, int localities, int i) {
        return ((l < Main.LOCALITY_START_CHANCE || locality) ^ (l < Main.LOCALITY_END_CHANCE && localityLength > Main.MIN_LOCALITY_CHAIN)) && localities < Main.MAX_LOCALITIES && i > Main.LOCALITY_DEPTH;
    }

    /**
     * Searches for most frequent references.
     * @param index an index from which to look towards the head of a references array.
     * @param references references to take a weighted count.
     * @return an array of most frequent references.
     */
    private static int[] getWeightedReferences(int index, int[] references) {

        int n = Math.min(index, Main.LOCALITY_DEPTH);

        int[] weightedReferences = new int[n];
        float[] weights = new float[n];

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

        int[] result = new int[Main.LOCALITY_REFERENCES];

        int max = 0;
        for (int i = 0; i < Main.LOCALITY_REFERENCES; i++) {
            for (int j = 0; j < n; j++) {
                if (weights[j] > weights[max] && weights[j] != 0f) {
                    max = j;
                }
            }
            result[i] = weightedReferences[max];
            weights[max] = 0f;
        }

        return result;
    }

    //endregion

    //region Page Faults

    public int getPageFaults() {
        return pageFaults;
    }

    public void updatePageFaults() {
        pageFaults++;
    }

    //endregion

    //region Thrashing

    public boolean thrashing() {
        int faults = 0;
        for (Boolean fault : pageFaultsStack) {
            if (fault) faults++;
        }
        return faults >= Main.THRASHING_THRESHOLD;
    }

    public void pushFaultState(boolean fault) {
        pageFaultsStack.addLast(fault);
        if (pageFaultsStack.size() > Main.THRASHING_CHECK_LENGTH)
            pageFaultsStack.removeFirst();
    }

    public void updateThrashing() {
        this.thrashing++;
    }

    public int getThrashing() {
        return thrashing;
    }

    //endregion

    @Override
    public String toString() {
        return "Process ID: " + id + "\n" +
                "Sequence: " + Arrays.toString(sequence) + "\n";
    }

}
