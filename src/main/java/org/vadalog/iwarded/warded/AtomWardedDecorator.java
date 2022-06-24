package org.vadalog.iwarded.warded;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vadalog.iwarded.model.Atom;
import org.vadalog.iwarded.model.Position;
import org.vadalog.iwarded.model.Term;
import org.vadalog.iwarded.model.Variable;



/**
 * It decorates an Atom, adding many useful functionalities
 * to deal with the warded fragment.
 *  
 * Copyright (C) 2021  authors: Teodoro Baldazzi, Luigi Bellomarini, Emanuel Sallinger
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/gpl-3.0.html
 */
public class AtomWardedDecorator {
	
	private Atom atom;

	public AtomWardedDecorator(Atom atom) {
		this.atom = atom;
	}

	public Atom getAtom() {
		return atom;
	}

	public void setAtom(Atom atom) {
		this.atom = atom;
	}
	
	/**
	 * It returns all the positions in which x occurs
	 * @param x the variable to look for
	 * @return the set of the positions x occurs in
	 */
	public Set<Position> getPositionsByVariable(Variable x) {
		List<Term> terms = this.atom.getArguments();
		Set<Position> positions = new HashSet<Position>();
		int pos = 0;
		
		for(Term t : terms) {
			if(t.equals(x))
				positions.add(new Position(this.atom.getName(), pos));
			pos++;
		}
			
		return positions;
	}
	
}