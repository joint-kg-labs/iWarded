package org.vadalog.iwarded.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.vadalog.iwarded.model.Literal;
import org.vadalog.iwarded.model.Model;
import org.vadalog.iwarded.model.Term;
import org.vadalog.iwarded.model.annotations.BindAnnotation;
import org.vadalog.iwarded.model.annotations.InputAnnotation;
import org.vadalog.iwarded.model.annotations.MappingAnnotation;
import org.vadalog.iwarded.utils.Utils;



/**
 * This class handles the generation of .vada file with the program in iWarded 
 * 
 * @author tbaldazzi
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
public class FileGenerator {

	ModelGenerator run;

	
	
	/**
	 * Constructor for FileGenerator 
	*/
	public FileGenerator(){
		this.run = ModelGenerator.getModelInstance();
	}



	/**
	 * It writes a single line into the csv
	 * 
	 * @param writer
	 * @param values
	 * @param columnNumber
	 * @throws IOException
	*/
	private void writeLine(Writer writer, Map<Integer, String> values, int columnNumber) throws IOException {

		boolean first = true;
		String delimiter = ",";

		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= columnNumber; i++) {
			if(!first) {
				sb.append(delimiter);
			}
			sb.append(values.get(i));
			first = false;
		}
		sb.append("\n");
		writer.append(sb.toString());
		writer.flush();
	}



	/**
	 * It creates a csv file
	 * 
	 * @param numbeOfRecords: number of records for CSV files
	 * @param path: where to save the csv file
	 * @param l: current literal
	 * @param condition
	 * @param selectivity
	*/
	public void createCsvFile(Integer numberOfRecords, String path, Literal l, Integer condition, Integer selectivity) {
		try {
			FileWriter writer = new FileWriter(path);
			Map<Integer, String> rowToWrite = new HashMap<>();
			Random r;
			while(numberOfRecords != 0) {
				int pos = 1;
				List<Integer> tmp = new ArrayList<Integer>();
				for(@SuppressWarnings("unused") Term t : l.getAtom().getArguments()) {
					r = new Random();
					Integer v;
					
					/*if selectivity not over and is the first position of the record - therefore the first variable*/
					if(selectivity!=null && selectivity!=0 && pos==1){
						v = r.nextInt(99-condition) + condition;	//condition has to be lower than 99 (by choice)
						selectivity--;
					}

					/*if no condition or selectivity over or is not the first position of the record*/
					else
						v = r.nextInt(99-1) + 1;
					
					rowToWrite.put(pos, String.valueOf(v));
					pos++;
					tmp.add(v);
				}
				this.writeLine(writer, rowToWrite, l.getAtom().getArguments().size());
				rowToWrite = new HashMap<>();
				numberOfRecords --;
			}
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * It creates the input, the bind and the mapping annotation rule
	 * 
	 * @param numberOfRecordsCSV: number of records for each CSV file
	 * @param result: The model
	 * @param path: path
	*/
	public void createInputBindMappingAnnotations(Integer numberOfRecordsCSV, Model result, String path) {
		InputAnnotation inputAnnot;
		BindAnnotation bindAnnot;
		MappingAnnotation mappingAnnot;
		/*create annotations for input atoms*/
		for(Literal l : this.run.inputLiterals) {
			String literalName = l.getAtom().getName();
			inputAnnot = new InputAnnotation(literalName);
			result.readAnnotation(inputAnnot);
			String nameCsv = literalName + "_csv.csv";
			bindAnnot = new BindAnnotation(literalName, "csv", path, nameCsv, false);
			result.readAnnotation(bindAnnot);
			for(int argPos=0; argPos<l.getAtom().getArguments().size(); argPos++) {
				String colName = "arg_" + argPos;
				mappingAnnot = new MappingAnnotation(literalName, argPos, colName, "int");
				result.readAnnotation(mappingAnnot);
			}			
			String atomName = l.getAtom().getName();
			Integer condition = this.run.conditionForRule.remove(atomName);
			Integer selectivity = this.run.selectivityForRule.remove(atomName);
			this.createCsvFile(numberOfRecordsCSV, path + "/" + nameCsv, l, condition, selectivity);
		}
	}



	/**
	 * It adds adapted program parameters to list of vada comments
	 * 
	 * @return string containing the description of input parameters
	 */
	public List<String> addOriginalProgramParametersToVadaFile() {
		List<String> parameters = new ArrayList<>();

		parameters.add("% =====ORIGINAL VADA PROGRAM PARAMETERS=====");
		parameters.add("% Number of Input Atoms: " + this.run.numberOfInputAtoms);
		parameters.add("% Number of Output Atoms: " + this.run.numberOfOutputAtoms);
		parameters.add("% Average number of Variables per Atom: " + this.run.averageVarsInAtom);
		parameters.add("% Variance number of Variables per Atom: " + this.run.varianceVarsInAtom);
		parameters.add("% Number of Existential Rules: " + this.run.numberOfExistentialRules);
		parameters.add("% Average number of Existentially Quantified Variables per Rule: " + this.run.averageEVarsInRule);
		parameters.add("% Variance of Existentially Quantified Variables per Rule: " + this.run.varianceEVarsInRule);
		parameters.add("% Number of Dangerous Rules: " + this.run.numberOfDangerousRules);
		parameters.add("% Number of Linear Rules: " + this.run.numberOfLinearRules);
		parameters.add("% Number of Join Rules: " + this.run.numberOfJoinRules);
		parameters.add("% Number of Harmless-Harmless Join Rules with Ward: " + this.run.numberOfHarmlessHarmlessJoinsWithWard);
		parameters.add("% Number of Harmless-Harmless Join Rules without Ward: " + this.run.numberOfHarmlessHarmlessJoinsWithoutWard);
		parameters.add("% Number of Harmless-Harmful Joins: " + this.run.numberOfHarmlessHarmfulJoins);
		parameters.add("% Number of Harmful-Harmful Joins: " + this.run.numberOfHarmfulHarmfulJoins);
		parameters.add("% Average number of Chase Steps: " + this.run.averageChaseSteps);
		parameters.add("% Number of Chase Steps for each Output Atom: ");
		parameters.add(Utils.printSortedMapFromHashMap(this.run.cSteps));
		parameters.add("% Number of Linear Recursions: " + this.run.numberOfLinearRecursions);
		parameters.add("% Number of Left Join Recursions: " + this.run.numberOfLeftJoinRecursions);
		parameters.add("% Number of Right Join Recursions: " + this.run.numberOfRightJoinRecursions);
		parameters.add("% Number of Non-Linear Join Recursions: " + this.run.numberOfNonLinearJoinRecursions);
		parameters.add("% Average Recursion Length: " + this.run.averageRecursionLength);
		parameters.add("% Length for each Recursion: ");
		parameters.add(Utils.printSortedMapFromHashMap(this.run.rLength));
		parameters.add("% Number of Selection Conditions: " + this.run.numberOfSelectionConditions );
		parameters.add("% Average Selectivity: " + this.run.averageSelectivity);
		parameters.add("% Number of records in CSV files: " + this.run.numberOfRecordsCSV);
		parameters.add("% Guardedness of the Program: " + this.run.isGuarded);
		parameters.add("% Name of the Program: " + this.run.programName);
		parameters.add("% ==========================================\n");		

		return parameters;
	}


}
