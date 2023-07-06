/*******************************************************************************
 * Copyright (c) 2012 Eric Bodden.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Eric Bodden - initial API and implementation
 ******************************************************************************/
package heros;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ZeroedFlowFunctions<N, D, M,X> implements FlowFunctions<N, D, M,X> {

	protected final FlowFunctions<N, D, M,X> delegate;
	protected  final D zeroValue;

	public ZeroedFlowFunctions(FlowFunctions<N, D, M,X> delegate, D zeroValue) {
		this.delegate = delegate;
		this.zeroValue = zeroValue;
	}

	public FlowFunction<D,X> getNormalFlowFunction(N curr, N succ) {
		return new ZeroedFlowFunction(delegate.getNormalFlowFunction(curr, succ));
	}

	public FlowFunction<D,X> getCallFlowFunction(N callStmt, M destinationMethod) {
		return new ZeroedFlowFunction(delegate.getCallFlowFunction(callStmt, destinationMethod));
	}

	public FlowFunction<D,X> getReturnFlowFunction(N callSite, M calleeMethod, N exitStmt, N returnSite) {
		return new ZeroedFlowFunction(delegate.getReturnFlowFunction(callSite, calleeMethod, exitStmt, returnSite));
	}

	public FlowFunction<D,X> getCallToReturnFlowFunction(N callSite, N returnSite) {
		return new ZeroedFlowFunction(delegate.getCallToReturnFlowFunction(callSite, returnSite));
	}
	
	protected class ZeroedFlowFunction implements FlowFunction<D,X> {

		protected FlowFunction<D,X> del;

		private ZeroedFlowFunction(FlowFunction<D,X> del) {
			this.del = del;
		}		
		
		@Override
		public Set<D> computeTargets(D source) {
			if(source==zeroValue) {
				HashSet<D> res = new LinkedHashSet<D>(del.computeTargets(source));
				res.add(zeroValue);
				return res;
			} else {
				return del.computeTargets(source);
			}
		}

		@Override
		public X getMeta() {
			return null;
		}

	}
	

}
