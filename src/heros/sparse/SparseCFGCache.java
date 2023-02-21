package heros.sparse;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SparseCFGCache<M, D> {

    Map<M, Map<D, SparseCFG>> cache;
    SparseCFGBuilder sparseCFGBuilder;

    public SparseCFGCache(SparseCFGBuilder sparseCFGBuilder){
        this.cache = new HashMap<>();
        this.sparseCFGBuilder = sparseCFGBuilder;
    }

    /**
     *
     * @param m method
     * @param d dff
     * @return
     */
    public synchronized SparseCFG getSparseCFG(M m, D d){
        if(cache.containsKey(m)){
            Map<D, SparseCFG> dToCFG = cache.get(m);
            if(dToCFG.containsKey(d)){
                return dToCFG.get(d);
            }else{
                SparseCFG cfg = buildSparseCFGAndPutToDMap(m, d, dToCFG);
                return cfg;
            }
        } else{
            Map<D, SparseCFG> dToCFG = new HashMap<>();
            SparseCFG cfg = buildSparseCFGAndPutToDMap(m, d, dToCFG);
            cache.put(m, dToCFG);
            return cfg;
        }
    }

    private SparseCFG buildSparseCFGAndPutToDMap(M m, D d, Map<D, SparseCFG> dToCFG) {
        SparseCFG cfg = sparseCFGBuilder.buildSparseCFG(m, d);
        dToCFG.put(d, cfg);
        return cfg;
    }

}
