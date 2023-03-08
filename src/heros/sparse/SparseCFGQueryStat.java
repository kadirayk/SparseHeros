package heros.sparse;

import com.google.common.base.Stopwatch;

import java.time.Duration;

public class SparseCFGQueryStat {

    private boolean retrievedFromCache;


    private final Stopwatch watch;

    private int initialStmtCount = 0;
    private int finalStmtCount = 0;

    private int initialEdgeCount = 0;
    private int finalEdgeCount = 0;

    public SparseCFGQueryStat(boolean retrievedFromCache) {
        this.retrievedFromCache = retrievedFromCache;
        if (!retrievedFromCache) {
            this.watch = Stopwatch.createUnstarted();
        } else {
            this.watch = null;
        }
    }

    public void logStart() {
        if (!retrievedFromCache) {
            this.watch.start();
        }
    }

    public void logEnd() {
        if (!retrievedFromCache) {
            this.watch.stop();
        }
    }

    /**
     * SparseCFG built time in microseconds, 0 if it was retrieved from the cache
     *
     * @return
     */
    public Duration getDuration() {
        if (!retrievedFromCache) {
            return this.watch.elapsed();
        } else {
            return Duration.ZERO;
        }
    }

    public boolean isRetrievedFromCache() {
        return retrievedFromCache;
    }


    public int getInitialStmtCount() {
        return initialStmtCount;
    }

    public void setInitialStmtCount(int initialStmtCount) {
        this.initialStmtCount = initialStmtCount;
    }

    public int getFinalStmtCount() {
        return finalStmtCount;
    }

    public void setFinalStmtCount(int finalStmtCount) {
        this.finalStmtCount = finalStmtCount;
    }

    public int getInitialEdgeCount() {
        return initialEdgeCount;
    }

    public void setInitialEdgeCount(int initialEdgeCount) {
        this.initialEdgeCount = initialEdgeCount;
    }

    public int getFinalEdgeCount() {
        return finalEdgeCount;
    }

    public void setFinalEdgeCount(int finalEdgeCount) {
        this.finalEdgeCount = finalEdgeCount;
    }
}
