package org.vadalog.iwarded.model;

import java.util.*;
import java.util.stream.Collectors;

/**
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
public class Atom {

	// core members
	private String name;
	private List<Term> arguments;

	public Atom(final String name) {
		this.name = name;
		arguments = new ArrayList<>();
	}

	public Atom(final String name, final List<Term> arguments) {
		this.name = name;
		this.arguments = arguments;
	}

	/**
	 * It returns the name of this atom
	 * @return the name of this atom
	 */
	public String getName() {
		return name;
	}


	/**
	 * It returns the simple name for this atom, which means that it does not
	 * consider the calculated variable.
	 * @return the name of this atom
	 */
	public String getSimpleName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Term> getArguments() {
		return arguments;
	}

	public void setArguments(List<Term> arguments) {
		this.arguments = arguments;
	}
	/**
	 * It returns the set of all the variables in this Atom
	 * @return the set of the variables in this Atom.
	 */
	public Set<Variable> getVariables() {
		return new HashSet<>(this.getVariableList());
	}

	/**
	 * It returns the list of all the variables in this Atom
	 * @return the List of variables in this Atom.
	 */
	public List<Variable> getVariableList() {
		List<Variable> var;
		var = this.arguments.stream()
				.filter((x -> x instanceof Variable))
				.map(x -> (Variable)x)
				.collect(Collectors.toList());
		return var;
	}


	public Set<Constant<?>> getAllConstants() {
		return this.arguments.stream()
				.filter(x -> x instanceof Constant<?>)
				.map(x -> (Constant<?>)x)
				.collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(getName());
		if (arguments.size() > 0) {
			s.append("(");
			final Iterator<Term> i = arguments.iterator();
			while (i.hasNext()) {
				final Term argument = i.next();
				s.append(argument);
				if (i.hasNext())
					s.append(",");
			}
			s.append(")");
		} 
		return s.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(arguments);
		result = prime * result + Objects.hashCode(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Atom other = (Atom) obj;
		return Objects.equals(this.arguments, other.arguments) &&
				Objects.equals(this.name, other.name);
	}	


}