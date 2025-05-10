package paging.simulation;

import paging.algorithms.PagingAlgorithm;

public interface Simulator {
    void simulate();
    PagingAlgorithm getAlgorithm();
}
