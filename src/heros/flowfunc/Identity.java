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

import static java.util.Collections.singleton;
import heros.FlowFunction;

import java.util.Set;



public class Identity<D,X> implements FlowFunction<D,X> {
	
	//@SuppressWarnings("rawtypes")
	//private final static Identity instance = new Identity();
	
	//private Identity(){} //use v() instead
	private final X info;

	public Identity(X info){
		this.info = info;
	}

	public Set<D> computeTargets(D source) {
		return singleton(source);
	}

	@Override
	public X getMeta() {
		return info;
	}

	//@SuppressWarnings("unchecked")
	//public static <D,X> Identity<D,X> v() {
	//	return instance;
	//}

}
