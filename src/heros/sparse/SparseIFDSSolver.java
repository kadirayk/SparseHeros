package heros.sparse;

import heros.*;
import heros.edgefunc.AllBottom;
import heros.edgefunc.AllTop;
import heros.edgefunc.EdgeIdentity;
import heros.solver.IDESolver;

import java.util.Map;
import java.util.Set;

/**
 * A solver for an {@link IFDSTabulationProblem}. This solver in effect uses the {@link IDESolver}
 * to solve the problem, as any IFDS problem can be intepreted as a special case of an IDE problem.
 * See Section 5.4.1 of the SRH96 paper. In effect, the IFDS problem is solved by solving an IDE
 * problem in which the environments (D to N mappings) represent the set's characteristic function.
 *
 * @param <N> The type of nodes in the interprocedural control-flow graph. Typically {@link Unit}.
 * @param <D> The type of data-flow facts to be computed by the tabulation problem.
 * @param <M> The type of objects used to represent methods. Typically {@link SootMethod}.
 * @param <I> The type of inter-procedural control-flow graph being used.
 * @see IFDSTabulationProblem
 */
public class SparseIFDSSolver<N,D,M,I extends InterproceduralCFG<N, M>> extends SparseIDESolver<N,D,M, SparseIFDSSolver.BinaryDomain,I> {

    protected static enum BinaryDomain { TOP,BOTTOM }

    private final static EdgeFunction<BinaryDomain> ALL_BOTTOM = new AllBottom<BinaryDomain>(BinaryDomain.BOTTOM);

    /**
     * Creates a solver for the given problem. The solver must then be started by calling
     * {@link #solve()}.
     */
    public SparseIFDSSolver(final IFDSTabulationProblem<N,D,M,I> ifdsProblem, final SparseCFGBuilder sparseCFGBuilder) {
        super(createIDETabulationProblem(ifdsProblem), sparseCFGBuilder);
    }

    static <N, D, M, I extends InterproceduralCFG<N, M>> IDETabulationProblem<N, D, M, BinaryDomain, I> createIDETabulationProblem(
            final IFDSTabulationProblem<N, D, M, I> ifdsProblem) {
        return new IDETabulationProblem<N,D,M, BinaryDomain,I>() {

            public FlowFunctions<N,D,M> flowFunctions() {
                return ifdsProblem.flowFunctions();
            }

            public I interproceduralCFG() {
                return ifdsProblem.interproceduralCFG();
            }

            public Map<N, Set<D>> initialSeeds() {
                return ifdsProblem.initialSeeds();
            }

            public D zeroValue() {
                return ifdsProblem.zeroValue();
            }

            public EdgeFunctions<N,D,M, BinaryDomain> edgeFunctions() {
                return new IFDSEdgeFunctions();
            }

            public MeetLattice<BinaryDomain> meetLattice() {
                return new MeetLattice<BinaryDomain>() {

                    public BinaryDomain topElement() {
                        return BinaryDomain.TOP;
                    }

                    public BinaryDomain bottomElement() {
                        return BinaryDomain.BOTTOM;
                    }

                    public BinaryDomain meet(BinaryDomain left, BinaryDomain right) {
                        if(left== BinaryDomain.TOP && right== BinaryDomain.TOP) {
                            return BinaryDomain.TOP;
                        } else {
                            return BinaryDomain.BOTTOM;
                        }
                    }
                };
            }

            @Override
            public EdgeFunction<BinaryDomain> allTopFunction() {
                return new AllTop<BinaryDomain>(BinaryDomain.TOP);
            }

            @Override
            public boolean followReturnsPastSeeds() {
                return ifdsProblem.followReturnsPastSeeds();
            }

            @Override
            public boolean autoAddZero() {
                return ifdsProblem.autoAddZero();
            }

            @Override
            public int numThreads() {
                return ifdsProblem.numThreads();
            }

            @Override
            public boolean computeValues() {
                return ifdsProblem.computeValues();
            }

            class IFDSEdgeFunctions implements EdgeFunctions<N,D,M, BinaryDomain> {

                public EdgeFunction<BinaryDomain> getNormalEdgeFunction(N src, D srcNode, N tgt, D tgtNode) {
                    if(srcNode==ifdsProblem.zeroValue()) return ALL_BOTTOM;
                    return EdgeIdentity.v();
                }

                public EdgeFunction<BinaryDomain> getCallEdgeFunction(N callStmt, D srcNode, M destinationMethod, D destNode) {
                    if(srcNode==ifdsProblem.zeroValue()) return ALL_BOTTOM;
                    return EdgeIdentity.v();
                }

                public EdgeFunction<BinaryDomain> getReturnEdgeFunction(N callSite, M calleeMethod, N exitStmt, D exitNode, N returnSite, D retNode) {
                    if(exitNode==ifdsProblem.zeroValue()) return ALL_BOTTOM;
                    return EdgeIdentity.v();
                }

                public EdgeFunction<BinaryDomain> getCallToReturnEdgeFunction(N callStmt, D callNode, N returnSite, D returnSideNode) {
                    if(callNode==ifdsProblem.zeroValue()) return ALL_BOTTOM;
                    return EdgeIdentity.v();
                }
            }

            @Override
            public boolean recordEdges() {
                return ifdsProblem.recordEdges();
            }

        };
    }

    /**
     * Returns the set of facts that hold at the given statement.
     */
    public Set<D> ifdsResultsAt(N statement) {
        return resultsAt(statement).keySet();
    }

}