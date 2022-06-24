package org.vadalog.iwarded.utils;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;



/**
 * This class contains additional useful methods for iWarded
 * @author teodorobaldazzi
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
public class Utils {

	
	/**
	 * It builds string with contents of a sorted map from hashMap
	 * 
	 * @param hashMap
	 * @return the string version of the map in input
	 */
	public static String printSortedMapFromHashMap(Map<String, Integer> hashMap){
		/*build the sorted string to print*/
		StringBuilder out = new StringBuilder("% ");
		SortedSet<String> sortedKeys = new TreeSet<>(hashMap.keySet());
		for(String key : sortedKeys){
			String value = hashMap.get(key).toString();
			out.append(key + "->" + value + "; ");
		}
		return out.toString();
	}

}
