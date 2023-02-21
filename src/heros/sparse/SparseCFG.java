package heros.sparse;

import java.util.List;

/**
 *
 * @param <N> node (stmt)
 * @param <D> DFF
 *
 */
public interface SparseCFG<N, D> {

    /**
     * returns next uses after given stmt
     * can return multiple uses when brancing
     *
     * @param n
     * @return
     */
    List<N> getNextUses(N n);

    void add(N n);

    D getD();

    List<N> getCFG();

    void setCFG(List<N> cfg);

}
