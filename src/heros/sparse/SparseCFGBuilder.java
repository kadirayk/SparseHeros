package heros.sparse;

/**
 *
 * @param <N> Node stmt
 * @param <M> Method
 * @param <D> DFF
 */
public interface SparseCFGBuilder<N,M,D> {

    SparseCFG<N, D> buildSparseCFG(M m, D d, SparseCFGQueryStat queryStat);
}
