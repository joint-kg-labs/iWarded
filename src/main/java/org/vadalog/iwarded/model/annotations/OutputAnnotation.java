package org.vadalog.iwarded.model.annotations;

import java.util.Collections;

/**
 * @author luigibellomarini
 * It declares that a predicate is an output
 * e.g. @output("p")
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
public class OutputAnnotation extends DatalogAnnotation implements InputOutputAnnotation {
	
	private final String predicateName;

	public OutputAnnotation(String predicateName) {
		super("output", Collections.singletonList(predicateName));
		this.predicateName = predicateName;
	}

	@Override
	public String getPredicateName() {
		return predicateName;
	}

}