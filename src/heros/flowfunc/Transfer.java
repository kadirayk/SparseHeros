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
import heros.FlowFunction;

import java.util.Collections;
import java.util.Set;



public class Transfer<D,X> implements FlowFunction<D,X> {
	
	private final D toValue;
	private final D fromValue;
	private final X info;
	
	public Transfer(D toValue, D fromValue, X info){
		this.toValue = toValue;
		this.fromValue = fromValue;
		this.info = info;
	} 

	public Set<D> computeTargets(D source) {
		if(source.equals(fromValue)) {
			return twoElementSet(source, toValue);
		} else if(source.equals(toValue)) {
			return Collections.emptySet();
		} else {
			return Collections.singleton(source);
		}
	}

	@Override
	public X getMeta() {
		return info;
	}

}
