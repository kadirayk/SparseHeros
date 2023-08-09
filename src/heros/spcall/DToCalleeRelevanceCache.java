package heros.spcall;

import heros.sparse.SparseCFGQueryStat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DToCalleeRelevanceCache<M, D> {

    private static DToCalleeRelevanceCache INSTANCE;

    List<SparseCFGQueryStat> queryStats;

    Map<M, Map<D, Boolean>> cache;

    DToCalleRelevanceFinder relevanceFinder;

    public static DToCalleeRelevanceCache getInstance(DToCalleRelevanceFinder builder){
        if(INSTANCE == null){
            INSTANCE = new DToCalleeRelevanceCache(builder);
        }
        return INSTANCE;
    }

    private DToCalleeRelevanceCache(DToCalleRelevanceFinder builder){
        this.cache = new HashMap<>();
        this.relevanceFinder = builder;
        this.queryStats = new ArrayList<>();
    }

    public synchronized boolean isRelevant(M m, D d){
        if(cache.containsKey(m)){
            Map<D, Boolean> dToRel = cache.get(m);
            if(dToRel.containsKey(d)){
                queryStats.add(new SparseCFGQueryStat(true));
                return dToRel.get(d);
            }else{
                SparseCFGQueryStat queryStat = new SparseCFGQueryStat(false);
                boolean relevant = findRelevanceAndPutToDMap(m, d, dToRel, queryStat);
                queryStats.add(queryStat);
                return relevant;
            }
        } else{
            Map<D, Boolean> dToRel = new HashMap<>();
            SparseCFGQueryStat queryStat = new SparseCFGQueryStat(false);
            boolean relevant = findRelevanceAndPutToDMap(m, d, dToRel, queryStat);
            queryStats.add(queryStat);
            cache.put(m, dToRel);
            return relevant;
        }
    }

    private boolean findRelevanceAndPutToDMap(M m, D d, Map<D, Boolean> dToRel, SparseCFGQueryStat queryStat) {
        queryStat.logStart();
        boolean rel = relevanceFinder.findRelevance(m, d, queryStat);
        queryStat.logEnd();
        dToRel.put(d, rel);
        return rel;
    }


}
