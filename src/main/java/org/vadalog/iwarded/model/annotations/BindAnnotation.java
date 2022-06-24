package org.vadalog.iwarded.model.annotations;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;



/**
 * It binds a predicate to a dataSource, a schema and a table
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
 */
public class BindAnnotation extends DatalogAnnotation implements Serializable {

    private static final String BIND_ANNOTATION_NAME = "bind";

	private static final long serialVersionUID = 1L;
	private final String predicateName;
	private final String dataSource;
	private final String schema;
	private final String tableOrQuery;
	
	public BindAnnotation(String predicateName, String dataSource, String schema, String table, boolean isQuery) {
		super(BIND_ANNOTATION_NAME, Arrays.asList(predicateName, dataSource, schema, table));
		this.predicateName = predicateName;
		this.dataSource = dataSource;
		this.schema = schema;
		this.tableOrQuery = table;
	}
	
	public BindAnnotation(String predicateName) {
		this(predicateName, "", "", "", false);
	}

	public String getPredicateName() {
		return predicateName;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getSchema() {
		return schema;
	}

	public String getTableOrQuery() {
		return tableOrQuery;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(dataSource);
		result = prime * result + Objects.hashCode(predicateName);
		result = prime * result + Objects.hashCode(schema);
		result = prime * result + Objects.hashCode(tableOrQuery);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		BindAnnotation other = (BindAnnotation) obj;
		return Objects.equals(this.dataSource, other.dataSource) &&
                Objects.equals(this.predicateName, other.predicateName) &&
                Objects.equals(this.schema, other.schema) &&
                Objects.equals(this.tableOrQuery, other.tableOrQuery);
	}
}
