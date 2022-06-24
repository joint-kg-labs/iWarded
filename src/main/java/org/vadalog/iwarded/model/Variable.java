package org.vadalog.iwarded.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



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
public class Variable extends Term implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private String name;
	
	public Variable(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getOperationName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String strip() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Variable) {
			return name.equals(((Variable) obj).name);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public Set<Variable> getAllVariables() {
		Set<Variable> vars = new HashSet<>();
		vars.add(this);
		return vars;
	}

}