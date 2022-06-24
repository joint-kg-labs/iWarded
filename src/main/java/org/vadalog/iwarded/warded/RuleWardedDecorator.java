package org.vadalog.iwarded.warded;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.vadalog.iwarded.model.Atom;
import org.vadalog.iwarded.model.Literal;
import org.vadalog.iwarded.model.Position;
import org.vadalog.iwarded.model.Rule;
import org.vadalog.iwarded.model.Variable;



/** 
 * It decorates a Rule, adding many useful functionalities
 * to deal with the warded fragment.
 * 
 * Important assumption: a rule is with a single head.
 * 
 * Definitions are in the paper, Georg Gottlob, Andreas Pieris "Beyond SPARQL under OWL 2 QL Entailment Regime: Rules to the Rescue".
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
 *
 */
public class RuleWardedDecorator {
	
	private Rule rule;

	public RuleWardedDecorator(Rule rule) {
		this.rule = rule;
	}
	
	/**
	 * It returns all the Positions in the body where a variable x appears
	 * @param x the variable to be looked up
	 * @return the set of positions
	 */
	public Set<Position> getBodyPositionsByVariable(Variable x) {
		List<Atom> atoms = this.rule.getLiterals()
				.stream()
				.map(Literal::getAtom)
				.collect(Collectors.toList());
		
		Set<Position> positions = new HashSet<>();
		
		for(Atom a : atoms)
			positions.addAll((new AtomWardedDecorator(a)).getPositionsByVariable(x));
		
		return positions;
	}

}