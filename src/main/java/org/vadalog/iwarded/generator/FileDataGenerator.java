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
import org.vadalog.iwarded.model.annotations.OutputAnnotation;
import org.vadalog.iwarded.utils.Utils;



/**
 * This class handles the generation of .vada file with the program and .csv files with data in iWarded 
 * 
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
public class FileDataGenerator {

	ModelGenerator run;



	/**
	 * Constructor for FileGenerator 
	 */
	public FileDataGenerator(){
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
	 * It creates a csv file for a predicate
	 * 
	 * @param numberOfRecords: number of records for csv file
	 * @param path: where to save the csv file
	 * @param l: current literal
	 */
	public void createCsvFile(Integer numberOfRecordsCSV, String path, Literal l) {
		try {
			List<Term> arguments = l.getAtom().getArguments();
			List<Integer> columnCardinality = new ArrayList<>();
			Map<Integer,Integer> columnLastValue = new HashMap<>();
			FileWriter writer = new FileWriter(path);
			Map<Integer,String> rowToWrite = new HashMap<>();
			Random r;

			/*obtain cardinality for each column and define range of values*/
			for(int j = 0; j < arguments.size(); j++) {
				r = new Random();
				Float selectivity = (float) r.nextGaussian() + this.run.averageSelectivity;
				while(selectivity<=0)
					selectivity = (float) r.nextGaussian() + this.run.averageSelectivity;
				Integer cardinality = (int) Math.round(selectivity*numberOfRecordsCSV/100);
				columnCardinality.add(cardinality);
				columnLastValue.put(j, cardinality);
			}

			/*generate values for each record*/
			while(numberOfRecordsCSV != 0) {
				/*generate values for each column*/
				for(int col = 0; col < arguments.size(); col++) {
					/*choose value based on cardinality*/
					Integer currentValue;
					Integer cardinality = columnCardinality.get(col);
					Integer lastValue = columnLastValue.get(col);
					if(lastValue != cardinality)
						currentValue = lastValue + 1;
					else
						currentValue = 1;
					columnLastValue.put(col, currentValue);
					rowToWrite.put(col+1, String.valueOf(currentValue));
				}
				this.writeLine(writer, rowToWrite, arguments.size());
				rowToWrite = new HashMap<>();
				numberOfRecordsCSV--;
			}
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * It creates input, output, bind and mapping annotations
	 * 
	 * @param path: path
	 */
	public void createAnnotationsAndCsvFiles(String path) {
		Model result = this.run.resultModel;

		InputAnnotation inputAnnot;
		BindAnnotation bindAnnot;
		MappingAnnotation mappingAnnot;

		/*create annotations for output predicates*/
		for(String outputPredName : this.run.outputAtomNames) {
			OutputAnnotation outputAnnot = new OutputAnnotation(outputPredName);
			result.readAnnotation(outputAnnot);
			String nameCsv = outputPredName + "_csv.csv";
			bindAnnot = new BindAnnotation(outputPredName, "csv", path + "/outputCsv/", nameCsv, false);
			result.readAnnotation(bindAnnot);
		}

		/*create annotations and csv files for input predicates*/
		for(Literal l : this.run.inputLiterals) {
			String inputPredName = l.getAtom().getName();
			inputAnnot = new InputAnnotation(inputPredName);
			result.readAnnotation(inputAnnot);
			String nameCsv = inputPredName + "_csv.csv";
			bindAnnot = new BindAnnotation(inputPredName, "csv", path + "/inputCsv/", nameCsv, false);
			result.readAnnotation(bindAnnot);
			for(int argPos=0; argPos<l.getAtom().getArguments().size(); argPos++) {
				mappingAnnot = new MappingAnnotation(inputPredName, argPos, "arg_" + argPos, "int");
				result.readAnnotation(mappingAnnot);
			}			
			this.createCsvFile(this.run.numberOfRecordsCSV, path + "/inputCsv/" + nameCsv, l);			
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
		parameters.add("% Number of Input Predicates: " + this.run.numberOfInputPredicates);
		parameters.add("% Number of Output Predicates: " + this.run.numberOfOutputPredicates);
		parameters.add("% Average number of Variables per Predicate: " + this.run.averageVarsInPredicate);
		parameters.add("% Variance number of Variables per Predicate: " + this.run.varianceVarsInPredicate);
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
		parameters.add("% Number of Input-Output Sequences: " + this.run.numberOfInputOutputSequences);
		parameters.add("% Average number of Max Chase Steps: " + this.run.averageInputOutputSequenceLength);
		parameters.add("% Number of Max Chase Steps for each Output Predicate: ");
		parameters.add(Utils.printSortedMapFromHashMap(this.run.maxCSteps));
		parameters.add("% Number of Linear Recursions: " + this.run.numberOfLinearRecursions);
		parameters.add("% Number of Left Join Recursions: " + this.run.numberOfLeftJoinRecursions);
		parameters.add("% Number of Right Join Recursions: " + this.run.numberOfRightJoinRecursions);
		parameters.add("% Number of Left-Right Join Recursions: " + this.run.numberOfLeftRightJoinRecursions);
		parameters.add("% Average Recursion Length: " + this.run.averageRecursionLength);
		parameters.add("% Length for each Recursion: ");
		parameters.add(Utils.printSortedMapFromHashMap(this.run.rLength));
		parameters.add("% Number of Selection Conditions: " + this.run.numberOfSelectionConditions );
		parameters.add("% Average Selectivity: " + this.run.averageSelectivity);
		parameters.add("% Number of records in CSV files: " + this.run.numberOfRecordsCSV);
		parameters.add("% Guardedness of the Program: " + this.run.isGuarded);
		parameters.add("% Shyness of the Program: " + this.run.isShy);
		parameters.add("% Name of the Program: " + this.run.programName);
		parameters.add("% ==========================================\n");		

		return parameters;
	}


}
