package org.vadalog.iwarded.model;

import java.util.*;

import org.vadalog.iwarded.model.annotations.DatalogAnnotation;

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
public class Model {

	// core members coming from parsing
	protected List<Atom> facts = new ArrayList<>();
	protected List<DatalogAnnotation> annotations = new ArrayList<>();
	protected List<Rule> rules = new ArrayList<>();

	// computable/adorning members
	protected List<String> comments = new ArrayList<>();

	public Model() {
		
	}

	public void readRule(Rule rule) {
		this.rules.add(rule);	
	}
	
	public void readAnnotation(DatalogAnnotation annotation) {
		this.annotations.add(annotation);
	}

	public List<String> getComments(){
		return this.comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public List<Atom> getFacts() {
		return facts;
	}
	
	public List<DatalogAnnotation> getAnnotations() {
		return this.annotations;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for(String s : comments)
			sb.append(s).append("\n");
		for(DatalogAnnotation a : annotations)
			sb.append(a.toString()).append(".\n");
		for(Atom f: facts) 
			sb.append(f.toString()).append(".\n");
		for(Rule r : this.rules)
			sb.append(r.toString()).append("\n");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((facts == null) ? 0 : facts.hashCode());
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Model other = (Model) obj;
		return Objects.equals(this.facts, other.facts) &&
				Objects.equals(this.rules, other.rules);
	}

}