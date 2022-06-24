package org.vadalog.iwarded.model.annotations;

import java.io.Serializable;
import java.util.Arrays;



/**
 * It maps a term of a bound predicate (used in a bind annotation) to 
 * a specific column of the respective table in the data source.
 * 
 * \@mapping("pred", pos, "colName", "colType")
 * e.g. \@mapping("p", 3, "aColumn", "colType").
 * 
 * It is used together with InputAnnotation to read a predicate from the input.
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
public class MappingAnnotation extends DatalogAnnotation implements Serializable {


	private static final long serialVersionUID = 1L;
	/* the name of the predicate */
	private final String predicateName;
	/* the position of the argument in the predicate it is mapping */
	/* argPos starts from 0 */
	private final int argPos;
	/* the name of the column in the source */
	private final String colName;
	/* the type of the column in the source */
	private final TypeEnum colType;
	/* whether this annotation has been inferred (and hence is not reliable in terms of name and type). */
	private boolean isInferred = false;

	public MappingAnnotation(String predicateName, int argPos, String colName, TypeEnum colType) {
		super("mapping", Arrays.asList(predicateName, argPos, colName, colType.toString()));
		this.predicateName = predicateName;
		this.argPos = argPos;
		this.colName = colName;
		this.colType = colType;
	}

	
	public MappingAnnotation(String predicateName, int argPos, String colName, String colType) {
		super("mapping", Arrays.asList(predicateName, argPos, colName, colType));
		this.predicateName = predicateName;
		this.argPos = argPos;
		this.colName = colName;

		if(colType.equalsIgnoreCase("string"))
			this.colType = TypeEnum.STRING;
		else if(colType.equalsIgnoreCase("int"))
			this.colType = TypeEnum.INT;
		else if(colType.equalsIgnoreCase("double"))
			this.colType = TypeEnum.DOUBLE;
		else if(colType.equalsIgnoreCase("boolean"))
			this.colType = TypeEnum.BOOLEAN;
		else if(colType.equalsIgnoreCase("date"))
			this.colType = TypeEnum.DATE;
		else if(colType.equalsIgnoreCase("set"))
			this.colType = TypeEnum.SET;
		else
		    this.colType = TypeEnum.UNKNOWN;
	}

	
	public String getPredicateName() {
		return predicateName;
	}
	public int getArgPos() {
		return argPos;
	}
	public String getColName() {
		return colName;
	}
	public TypeEnum getColType() {
		return colType;
	}

	public boolean isInferred() {
		return isInferred;
	}

	public void setInferred(boolean isInferred) {
		this.isInferred = isInferred;
	}

}
