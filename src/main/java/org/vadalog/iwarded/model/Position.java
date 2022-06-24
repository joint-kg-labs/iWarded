package org.vadalog.iwarded.model;

import java.util.HashSet;
import java.util.Set;



/**
 * A position is a pair (Atom, Position) in the Atom.
 * 
 * @author luigibellomarini
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
public class Position {
	
	private String atom;
	private int position;
	/* the cause for this affected position, which the Set */
	/* of rules causing this affectedness */
	private Set<Rule> causes = new HashSet<Rule>();
	
	public Position(String atom, int position) {
		this.atom = atom;
		this.position = position;
	}
	
	public Position(Atom atom, int position, Rule cause) {
		this.atom = atom.getName();
		this.position = position;
		this.causes.add(cause);
	}
	
	public Position(Atom atom, int position) {
		super();
		this.atom = atom.getName();
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getAtom() {
		return atom;
	}

	public void setAtom(Atom atom) {
		this.atom = atom.getName();
	}
	
	public void addCause(Rule r) {
		this.causes.add(r);
	}
	
	public void addCauses(Set<Rule> rSet) {
		this.causes.addAll(rSet);
	}
	
	public Set<Rule> getCauses() {
		return this.causes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atom == null) ? 0 : atom.hashCode());
		result = prime * result + position;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (atom == null) {
			if (other.atom != null)
				return false;
		} else if (!atom.equals(other.atom))
			return false;
		if (position != other.position)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.atom + "[" + this.position + "]";
	}
	
}