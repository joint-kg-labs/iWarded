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
public class Rule {
	private List<Atom> head;
	private List<Literal> body;
	private List<Condition> conditions;


	public Rule(List<Atom> head, List<Literal> body, List<Condition> conditions) {
		this.head = head;
		this.body = body;
		if(conditions==null)
			this.conditions = new ArrayList<>();
		else 
			this.conditions = conditions;
	}

	public Rule(Atom head, List<Literal> body, List<Condition> conditions) {
		this.head = new LinkedList<>();
		this.head.add(head);
		this.body = body;
		if(conditions==null)
			this.conditions = new ArrayList<>();
		else 
			this.conditions = conditions;	
	}

	public Rule(Atom head, Literal body, List<Condition> conditions) {
		this.head = new LinkedList<>();
		this.head.add(head);
		this.body = new LinkedList<>();
		this.body.add(body);
		if(conditions==null)
			this.conditions = new ArrayList<>();
		else 
			this.conditions = conditions;
	}

	public List<Literal> getLiterals() {
		return this.body;
	}

	public List<Condition> getConditions() {
		return this.conditions;
	}

	public List<Atom> getHead() {
		return this.head;
	}

	public List<Literal> getBody() {
		return this.body;
	}

	public void setBody(List<Literal> body) {
		this.body = body;
	}

	/**
	 * It returns the set of all the variables in the body
	 * @return all the variables in the body
	 */
	public Set<Variable> getBodyVariables() {
		Set<Variable> bodyVariables = new HashSet<>();
		for (Literal l : this.getLiterals()) {
			bodyVariables.addAll(l.getAtom().getVariables());
		}
		return bodyVariables;
	}
	
	/**
	 * It returns the list of all body atoms.
	 * @return all the atoms in the body
	 */
	public List<Atom> getBodyAtomsList() {
		return this.getBody().stream()
				.map(Literal::getAtom)
				.collect(Collectors.toList());
	}
	
	/**
	 * It returns the set of all the variables that appear in the conditions
	 * of this rule.
	 * @return the set of all the variables that appear in the conditions of this rule.
	 */
	public Set<Variable> getConditionsVariables() {
		
		Set<Variable> variablesInConditions = new HashSet<>();
		
		for (Condition c : this.conditions) {
			variablesInConditions.addAll(c.getAllVariables());
		}
		
		return variablesInConditions;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();

		Iterator<Atom> i = head.iterator();
		while (i.hasNext()) {
			s.append(i.next().toString());
			if (i.hasNext()) s.append(", ");
		}
		s.append(" :- ");
		Iterator<Literal> j = body.iterator();
		while (j.hasNext()) {
			s.append(j.next().toString());
			if (j.hasNext()) s.append(", ");
		}

		Iterator<Condition> k = conditions.iterator();
		if(k.hasNext())
			s.append(", ");

		while(k.hasNext()) {
			s.append(k.next().toString());
			if (k.hasNext()) s.append(", ");
		}

		s.append(".");
		return s.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(body);
		result = prime * result + Objects.hashCode(conditions);
		result = prime * result + Objects.hashCode(head);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		return Objects.equals(this.body, other.body) &&
				Objects.equals(this.conditions, other.conditions) &&
				Objects.equals(this.head, other.head);
	}


}