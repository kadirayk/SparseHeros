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
package heros.flowfunc;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import heros.FlowFunction;

import java.util.Set;



/**
 * Function that kills a specific value (i.e. returns an empty set for when given this
 * value as an argument), but behaves like the identity function for all other values.
 *
 * @param <D> The type of data-flow facts to be computed by the tabulation problem.
 */
public class Kill<D,X> implements FlowFunction<D,X> {
	
	private final D killValue;
	private final X info;
	
	public Kill(D killValue, X info){
		this.killValue = killValue;
		this.info = info;
	} 

	public Set<D> computeTargets(D source) {
		if(source.equals(killValue)) {
			return emptySet();
		} else {
			return singleton(source);
                }
	}

	@Override
	public X getMeta() {
		return info;
	}

}
