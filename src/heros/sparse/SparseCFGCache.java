package heros.sparse;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SparseCFGCache<M, D> {

    private static SparseCFGCache INSTANCE;

    List<SparseCFGQueryStat> queryStats;

    Map<M, Map<D, SparseCFG>> cache;
    SparseCFGBuilder sparseCFGBuilder;

    public static SparseCFGCache getInstance(SparseCFGBuilder sparseCFGBuilder){
        if(INSTANCE==null){
           INSTANCE = new SparseCFGCache(sparseCFGBuilder);
        }
        return INSTANCE;
    }

    private SparseCFGCache(SparseCFGBuilder sparseCFGBuilder){
        this.cache = new HashMap<>();
        this.sparseCFGBuilder = sparseCFGBuilder;
        this.queryStats = new ArrayList<>();
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
                queryStats.add(new SparseCFGQueryStat(true));
                return dToCFG.get(d);
            }else{
                SparseCFGQueryStat queryStat = new SparseCFGQueryStat(false);
                SparseCFG cfg = buildSparseCFGAndPutToDMap(m, d, dToCFG, queryStat);
                queryStats.add(queryStat);
                return cfg;
            }
        } else{
            Map<D, SparseCFG> dToCFG = new HashMap<>();
            SparseCFGQueryStat queryStat = new SparseCFGQueryStat(false);
            SparseCFG cfg = buildSparseCFGAndPutToDMap(m, d, dToCFG, queryStat);
            queryStats.add(queryStat);
            cache.put(m, dToCFG);
            return cfg;
        }
    }

    private SparseCFG buildSparseCFGAndPutToDMap(M m, D d, Map<D, SparseCFG> dToCFG, SparseCFGQueryStat queryStat) {
        queryStat.logStart();
        SparseCFG cfg = sparseCFGBuilder.buildSparseCFG(m, d, queryStat);
        queryStat.logEnd();
        dToCFG.put(d, cfg);
        return cfg;
    }

    public static List<SparseCFGQueryStat> getQueryStats(){
        if(INSTANCE==null){
            throw new RuntimeException("Invalid state");
        }
        return INSTANCE.queryStats;
    }


}
