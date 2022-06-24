package org.vadalog.iwarded.model.annotations;

import java.util.HashSet;
import java.util.Set;



/**
 * The possible types for the columns in the mapping annotation
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
public enum TypeEnum {
	STRING("string", "str"),
	INT("int", "integer"),
	DOUBLE("double"),
	BOOLEAN("boolean", "bool"),
	SET("set"),
	LIST("list"),
	DATE("date"),
	UNKNOWN("unknown"),
	;
	
	private Set<String> names;
	
	TypeEnum(String... names) {
		this.names = new HashSet<>();
		this.names.add(this.name().toLowerCase());
		for (String name : names) this.names.add(name.toLowerCase());
	}

	@Override
	public String toString() {
		if(this.equals(TypeEnum.STRING) )
			return "STRING";
		else if(this.equals(TypeEnum.INT))
			return "INT";
		else if(this.equals(TypeEnum.DOUBLE))
			return "DOUBLE";
		else if(this.equals(TypeEnum.BOOLEAN))
			return "BOOLEAN";
		else if(this.equals(TypeEnum.DATE))
			return "DATE";
		else if(this.equals(TypeEnum.SET))
			return "{}";
		else if(this.equals(TypeEnum.LIST))
			return "[]";
		
		else return "none";
	}

	public Set<String> getNames() {
		return names;
	}

	public String getName() {
		return this.names.iterator().next();
	}

}
