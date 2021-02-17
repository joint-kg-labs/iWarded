package org.vadalog.iwarded.model;

import java.util.*;

import org.vadalog.iwarded.model.annotations.TypeEnum;

/**
 * A Constant in a Datalog program. It is a typed object.
 * The literals are like in Java. The quotes "" are part of the String
 * for String constants, and directly returned by the parser.
 * 
 * A constant can be also of a composite type, namely Set. In this
 * case the TypeEnum is Set. Notice that also in this case it is
 * immutable.
 * 
 * @author luigibellomarini
 *
 * @param <T> the type of the constant
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
public class Constant<T> extends Term {
	
	private static final long serialVersionUID = 1L;
	private T value;
	private TypeEnum type;

	public Constant() {
		this.type = TypeEnum.UNKNOWN;
	}
	
	public Constant(Constant<T> aConstant) {
		super();
		this.value = aConstant.value;
		inferType(value);
	}
	
	public Constant(T value) {
		super();
		this.value = value;
		inferType(value);
	}

	private void inferType(T value) {
		/* we store the data type */
		if(value instanceof String)
			type = TypeEnum.STRING;
		else if(value instanceof Integer)
			type = TypeEnum.INT;
		else if(value instanceof Double)
			type = TypeEnum.DOUBLE;
		else if(value instanceof Boolean)
			type = TypeEnum.BOOLEAN;
		else if(value instanceof GregorianCalendar)
			type = TypeEnum.DATE;
		else if(value instanceof Set)
			type = TypeEnum.SET;
		else if (value instanceof List)
			type = TypeEnum.LIST;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public String toString() {
        if (value instanceof Collection) {
            StringJoiner sj = value instanceof List ? new StringJoiner(",", "[", "]") : new StringJoiner(",", "{", "}");
            for (Object v : (Collection) this.value)
                sj.add(v.toString());
            return sj.toString();
        }
        else if (value instanceof Boolean)
        	return value.equals(true) ? "#T" : "#F";
        else
            return Objects.toString(value);
    }
	
	/**
	 * It returns the data type of this constant
	 * @return the type of the held value
	 */
	public TypeEnum getType() {
		return this.type;
	}
	
	public T getValue() {	
		return this.value;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {	
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Constant<?>)) 
			return false;
		else
			return this.value.equals(((Constant<T>)obj).getValue());
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Variable> getAllVariables() {
		HashSet<Variable> variables = new HashSet<>();
		if (type == TypeEnum.LIST)
			for (Expression element : (List<Expression>)value)
				variables.addAll(element.getAllVariables());
		return variables;
	}

	@Override
	public String getOperationName() {
		return this.toString();
	}
}