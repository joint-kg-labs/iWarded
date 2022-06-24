package org.vadalog.iwarded.model;



/**
 * It is a comparison operator that can be used in expressions.
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
public enum ComparisonOperatorsEnum {
	GT, GE, LT, LE, EQ, NEQ, IN, NOTIN;
	
	public String toString() {
		switch (this) {
			case LT:
				return "<";
			case GT:
				return ">";
			case EQ:
				return "=";
			case LE:
				return "<=";
			case GE:
				return ">=";
			case NEQ:
				return "<>";
			case IN:
				return "in";
			case NOTIN:
				return "!in";
		}
		return "none";
	}
}