package org.vadalog.iwarded.model;

import java.io.Serializable;
import java.util.Set;



/**
 * It represents a tuple-wise arithmetic expression to be used
 * in the body in the RHS of a condition. It is a composite of other
 * expressions. The base case is that of a Term.
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
public abstract class Expression implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Returns the arity of the Expression
	 * @return Returns the arity of the Expression
	 */
	public abstract int getArity();


	/**
	 * Returns the name of the expression's operation
	 * @return the name of the expression's operation
	 */
	public abstract String getOperationName();


	/**
	 * It returns all the variables in the expression.
	 * @return the set of variables
	 */
	public abstract Set<Variable> getAllVariables();

	
}