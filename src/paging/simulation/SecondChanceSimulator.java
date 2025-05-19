package paging.simulation;

import paging.Main;
import paging.algorithms.PagingAlgorithm;
import paging.algorithms.SecondChance;
import paging.frame_allocators.*;
import paging.process.Process;

import java.util.Arrays;
import java.util.Random;

public class SecondChanceSimulator implements Simulator {

    SecondChance algorithm;

    AllocationAlgorithm allocator;

    private int seed;

    private int generatedProcesses = 0;
    private int lastReference = 1;
    private int processId = 1;

    public SecondChanceSimulator(int seed, AllocationType allocationType) {
        this.seed = seed;

        algorithm = new SecondChance();

        switch (allocationType) {
            case EQUAL:
                allocator = new EqualAllocator(algorithm);
                break;
            case PROPORTIONAL:
                allocator = new ProportionalAllocator(algorithm);
                break;
            case PFF:
                allocator = new PFFAllocator(algorithm);
                break;
            case WSS:
                break;
            default:
                throw new IllegalArgumentException("Unknown allocation type: " + allocationType);
        }

        algorithm.setAllocator(allocator);
    }

    @Override
    public void simulate() {

        Random rand;

        // Main simulation loop
        while (!algorithm.isFinished()) {

            if (generatedProcesses < Main.PROCESSES) {
                // Processes can be generated
                rand = new Random(seed);
                float r = rand.nextFloat();

                if (r <= Main.PROCESS_GENERATION_THRESHOLD) {

                    // Generate Process

                    int length = rand.nextInt(Main.MIN_PROGRAM_SIZE, Main.MAX_PROGRAM_SIZE + 1);

                    Process process = Process.generateProcess(processId, seed, lastReference, lastReference + length);

                    algorithm.addProcess(process);

                    System.out.println("Process " + processId + " generated\n");

                    lastReference += length + 1;
                    generatedProcesses++;
                    processId++;
                }

                seed = seed + 1 % Integer.MAX_VALUE;

            }

            algorithm.step();

            algorithm.showFrames();
        }

        algorithm.showFrames();

        System.out.println(algorithm.getTotalPageFaults());

    }

    @Override
    public PagingAlgorithm getAlgorithm() {
        return algorithm;
    }
}
