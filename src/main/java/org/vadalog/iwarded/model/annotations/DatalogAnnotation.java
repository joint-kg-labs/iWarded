package org.vadalog.iwarded.model.annotations;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.vadalog.iwarded.model.Constant;

/**
 * It is a fact @f(c1, c2, ... cn) used in a program to define specific configurations.
 * Parameters c1, ..., cn are specific to f and positional.
 * For example, is f is "input", then c1,..., cn hold the
 * connection, schema, table name and so on.
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
public abstract class DatalogAnnotation {

    private final String name;
    List<Object> arguments;

    /**
     * Constructs a DatalogAnnoatation from it's name and arguments
     * @param name the annotation name
     * @param arguments the annotation's arguments
     */
    DatalogAnnotation(String name, List<Object> arguments)  {
        this.name = name;
        this.arguments = arguments;
    }

    /**
     * A getter for the annotation's name
     * @return the annotation name
     */
    public String getName() {
        return name;
    }

    /**
     * A getter for the annotation's arguments
     * @return the annotation's arguments
     */
    public List<Object> getArguments() {
        return arguments;
    }

    /**
     * Returns the annotation arguments as a list of constants
     * @return the list of constants
     */
    private List<Constant<?>> getArgumentsAsConstants() {
        return getArguments().stream().map(s-> new Constant<>(s instanceof String ? "\"" + escape((String)s) + "\"" : s)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        List<Constant<?>> argumentsAsConstants = getArgumentsAsConstants();
        String argumentList = "";
        if (argumentsAsConstants.size() > 0) {
            StringJoiner sj = new StringJoiner(",", "(", ")");
            argumentsAsConstants.forEach(x -> sj.add(String.valueOf(x)));
            argumentList = sj.toString();
        }
        return "@" + getName() + argumentList;
    }

    private String escape(String s) {
        return s.replaceAll("\"", "\"\"");
    }
}
