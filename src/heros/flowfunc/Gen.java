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

import static heros.TwoElementSet.twoElementSet;
import static java.util.Collections.singleton;
import heros.FlowFunction;

import java.util.Set;



/**
 * Function that creates a new value (e.g. returns a set containing a fixed value when given
 * a specific parameter), but acts like the identity function for all other parameters.
 *
 * @param <D> The type of data-flow facts to be computed by the tabulation problem.
 */
public class Gen<D,X> implements FlowFunction<D,X> {
	
	private final D genValue;
	private final D zeroValue;
	private final X info;
	
	public Gen(D genValue, D zeroValue, X info){
		this.genValue = genValue;
		this.zeroValue = zeroValue;
		this.info = info;
	} 

	public Set<D> computeTargets(D source) {
		if(source.equals(zeroValue)) {
			return twoElementSet(source, genValue);
		} else {
			return singleton(source);
                }
	}

	@Override
	public X getMeta() {
		return info;
	}

}
