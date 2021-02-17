package org.vadalog.iwarded.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * It represents a condition in the body of a Datalog rule.
 * A condition is a formula where the LHS is a variable and the RHS
 * is an expression. The two parts are connected by an assignment operator
 * or by a comparison operator (>,<,>=,<=,=,!=,in,!in). There is ambiguity
 * on the assignment, which is also a comparison. In case the variable
 * in the LHS appears in the body, the operator has to be interpreted as
 * comparison, assignment otherwise.
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
public class Condition {

	private final Variable lhs;
	private final ComparisonOperatorsEnum compOp;
	private final Expression rhs;

	public Condition(Variable lhs, ComparisonOperatorsEnum compOp, Expression rhs) {
		super();
		this.lhs = lhs;
		this.compOp = compOp;
		this.rhs = rhs;
	}

	public Variable getLhs() {
		return lhs;
	}

	/**
	 * Returns an immutable set containing the variables in this condition
	 * @return the set of variables
	 */
	public Set<Variable> getAllVariables() {
		return Sets.union(Collections.singleton(lhs), getAllExpressionVariables());
	}

	/**
	 * It returns all the variables in the expression in this condition.
	 * @return the set of variables
	 */
	public Set<Variable> getAllExpressionVariables() {
		return this.rhs.getAllVariables();
	}

	public Expression getRhs() {
		return rhs;
	}

	public ComparisonOperatorsEnum getCompOp() {
		return compOp;
	}

	public String toString() {
		String space = "";
		if(this.compOp==ComparisonOperatorsEnum.IN||this.compOp==ComparisonOperatorsEnum.NOTIN)
			space = " ";
		return this.lhs.toString() + space + this.compOp.toString() + space + this.rhs.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compOp == null) ? 0 : compOp.hashCode());
		result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
		result = prime * result + ((rhs == null) ? 0 : rhs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Condition that = (Condition) obj;
		return Objects.equals(this.compOp, that.compOp) &&
				Objects.equals(this.lhs, that.lhs) &&
				Objects.equals(this.rhs, that.rhs);
	}


}
