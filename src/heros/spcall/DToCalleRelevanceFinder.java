package heros.spcall;

import heros.sparse.SparseCFGQueryStat;

public interface DToCalleRelevanceFinder<M, D> {

    Boolean findRelevance(M m, D d, SparseCFGQueryStat queryStat);

}
