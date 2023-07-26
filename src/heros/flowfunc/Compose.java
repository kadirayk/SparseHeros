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

import heros.FlowFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;


/**
 * Represents the ordered composition of a set of flow functions.
 */
public class Compose<D,X> implements FlowFunction<D,X> {
	
	private final FlowFunction<D,X>[] funcs;

	private Compose(FlowFunction<D,X>... funcs){
		this.funcs = funcs;
	} 

	public Set<D> computeTargets(D source) {
		Set<D> curr = Sets.newHashSet();
		curr.add(source);
		for (FlowFunction<D,X> func : funcs) {
			Set<D> next = Sets.newHashSet();
			for(D d: curr)
				next.addAll(func.computeTargets(d));
			curr = next;
		}
		return curr;
	}

	@Override
	public X getMeta() {
		return funcs[0].getMeta(); // TODO: find a better way
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <D,X> FlowFunction<D,X> compose(FlowFunction<D,X>... funcs) {
		List<FlowFunction<D,X>> list = new ArrayList<FlowFunction<D,X>>();
		for (FlowFunction<D,X> f : funcs) {
			if(!(f instanceof Identity)) {
				list.add(f);
			}
		}
		if(list.size()==1) return list.get(0);
		else if(list.isEmpty()) return new Identity<>(list.get(0).getMeta()); // TODO: check
		return new Compose(list.toArray(new FlowFunction[list.size()]));
	}
	
}
