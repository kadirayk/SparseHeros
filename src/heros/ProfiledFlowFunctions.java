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

/**
 * A wrapper that can be used to profile flow functions.
 */
public class ProfiledFlowFunctions<N, D, M,X> implements FlowFunctions<N, D, M,X> {

	protected final FlowFunctions<N, D, M,X> delegate;
	
	public long durationNormal, durationCall, durationReturn, durationCallReturn;

	public ProfiledFlowFunctions(FlowFunctions<N, D, M,X> delegate) {
		this.delegate = delegate;
	}

	public FlowFunction<D,X> getNormalFlowFunction(N curr, N succ) {
		long before = System.currentTimeMillis();
		FlowFunction<D,X> ret = delegate.getNormalFlowFunction(curr, succ);
		long duration = System.currentTimeMillis() - before;
		durationNormal += duration;
		return ret;
	}

	public FlowFunction<D,X> getCallFlowFunction(N callStmt, M destinationMethod) {
		long before = System.currentTimeMillis();
		FlowFunction<D,X> res = delegate.getCallFlowFunction(callStmt, destinationMethod);
		long duration = System.currentTimeMillis() - before;
		durationCall += duration;
		return res;
	}

	public FlowFunction<D,X> getReturnFlowFunction(N callSite, M calleeMethod, N exitStmt, N returnSite) {
		long before = System.currentTimeMillis();
		FlowFunction<D,X> res = delegate.getReturnFlowFunction(callSite, calleeMethod, exitStmt, returnSite);
		long duration = System.currentTimeMillis() - before;
		durationReturn += duration;
		return res;
	}

	public FlowFunction<D,X> getCallToReturnFlowFunction(N callSite, N returnSite) {
		long before = System.currentTimeMillis();
		FlowFunction<D,X> res = delegate.getCallToReturnFlowFunction(callSite, returnSite);
		long duration = System.currentTimeMillis() - before;
		durationCallReturn += duration;
		return res;
	}
	
}
