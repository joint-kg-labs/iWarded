package org.vadalog.iwarded.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.vadalog.iwarded.model.Model;



/**
 * Main class of iWarded, Java generator of Warded Datalog+/- programs. 
 * It allows building programs based on scenarios with various architecture, dimensions and characteristics. 
 * Each program is written in a .vada file and it is accompanied by a set of generated .csv files as data sources.
 * 
 * For more details about iWarded, please consult the manual: https://github.com/joint-kg-labs/iWarded/doc/iWarded_manual.md
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
public final class IWardedGenerator {


	private String programPath = "generatedPrograms/";		//change program path here if required



	/**
	 * It illustrates the definitions of the input parameters
	 */
	private void presentParameters() {
		System.out.println("\n=====Parameters of Generated Program=====\n");
		System.out.println("- Number of Input Atoms:");
		System.out.println("the total number of edb atoms with the @input annotation and the .CSV dataset");
		System.out.println("[integer >= 1]");
		System.out.println();

		System.out.println("- Number of Output Atoms:");
		System.out.println("the total number of idb atoms with the @output annotation");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Average number of Variables per Atom: ");
		System.out.println("the average number of variables as arguments of each atom");
		System.out.println("[integer >= 1]");
		System.out.println();

		System.out.println("- Variance of Variables per Atom: ");
		System.out.println("the variance for the number of variables as arguments of each atom");
		System.out.println("[float >= 1.0]");
		System.out.println();

		System.out.println("- Number of Existential Rules: ");
		System.out.println("the total number of rules with an existential quantifier in the head");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Average number of Existentially Quantified Variables per Rule: ");
		System.out.println("the average number of existential quantifiers in each head of existential rules");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Variance of Existentially Quantified Variables per Rule: ");
		System.out.println("the variance for the number existential quantifiers in each head of existential rules");
		System.out.println("[float >= 0]");
		System.out.println();

		System.out.println("- Number of Dangerous Rules: ");
		System.out.println("the total number of warded rules with dangerous variables");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Linear Rules: ");
		System.out.println("the total number rules with a linear body");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Harmless-Harmless Joins with Ward: ");
		System.out.println("the total number of rules with join between harmless variables and with dangerous variables in the ward");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Harmless-Harmless Joins without Ward: ");
		System.out.println("the total number of rules with join between harmless variables and without dangerous variables");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Harmless-Harmful Joins: ");
		System.out.println("the total number of rules with join between a harmless variable and a harmful one");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Harmful-Harmful Joins: ");
		System.out.println("the total number of rules with join between harmful variables");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Average Number of Chase Steps from Input to Output: ");
		System.out.println("the average number of steps for each input-output sequence");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Linear Recursions: ");
		System.out.println("the total number of linear rules with recursions");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Left-Join Recursions: ");
		System.out.println("the total number of join rules with recursion on the left-join atom");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Right-Join Recursions: ");
		System.out.println("the total number of join rules with recursion on the right-join atom");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Non-Linear Join Recursions: ");
		System.out.println("the total number of join rules with recursion on both the left-join and the right-join atom");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Average Recursion length: ");
		System.out.println("the average number of steps for each recursive cycle");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of Selection Conditions: ");
		System.out.println("the total number of selection conditions for the arguments in the bodies of the rules");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Average Selectivity: ");
		System.out.println("the average number of facts filtered in");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Number of records in CSV files: ");
		System.out.println("the total number of records in each input atoms'.csv file");
		System.out.println("[integer >= 0]");
		System.out.println();

		System.out.println("- Guardedness of the Program: ");
		System.out.println("whether for each rule all the universally quantified variables are comprised within a single guard atom in the body");
		System.out.println("[boolean true/false]");
		System.out.println();

		System.out.println("- Name of the Program: ");
		System.out.println("name of the folder containing the generated program and the data files");
		System.out.println();
		
		System.out.println("\nRefer to https://github.com/joint-kg-labs/iWarded/doc/iWarded_manual.md for full documentation\n");
	}



	/**
	 * It creates a Vadalog program from the parameters provided as input by the User
	 *  
	 * TODO: in generation from command line, add dynamic check of input values and ask them again if not valid
	 */
	private void createModelFromInputArgs(LineReader reader){

		List<String> parameters = new ArrayList<>();

		System.out.println("\n=====Building Vadalog Program=====\n");
		System.out.print("Number of Input Atoms: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Output Atoms: ");
		parameters.add(reader.readLine());
		System.out.print("Average number of Variables per Atom: ");
		parameters.add(reader.readLine());
		System.out.print("Variance of Variables per Atom: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Existential Rules: ");
		parameters.add(reader.readLine());
		System.out.print("Average number of Existentially Quantified Variables per Rule: ");
		parameters.add(reader.readLine());
		System.out.print("Variance of Existentially Quantified Variables per Rule: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Dangerous Rules: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Linear Rules: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Harmless-Harmless Joins with Ward: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Harmless-Harmless Joins without Ward: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Harmless-Harmful Joins: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Harmful-Harmful Joins: ");
		parameters.add(reader.readLine());
		System.out.print("Average Number of Chase Steps from Input to Output: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Linear Recursions: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Left-Join Recursions: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Right-Join Recursions: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Non-Linear Join Recursions: ");
		parameters.add(reader.readLine());
		System.out.print("Average Recursion length: ");
		parameters.add(reader.readLine());
		System.out.print("Number of Selection Conditions: ");
		parameters.add(reader.readLine());
		System.out.print("Average Selectivity: ");
		parameters.add(reader.readLine());
		System.out.print("Number of records in CSV files: ");
		parameters.add(reader.readLine());
		System.out.print("Guardedness of the Program: ");
		parameters.add(reader.readLine());
		System.out.print("Name of the Program: ");
		parameters.add(reader.readLine());

		FileWriter fstream;
		try{
			long startTime = System.currentTimeMillis();

			String programName = parameters.get(parameters.size()-1);
			createFolder(programName);
			String path = this.programPath + programName;

			fstream = new FileWriter(path + "/iWarded_program.vada");
			BufferedWriter out = new BufferedWriter(fstream);
			String[] args = new String[parameters.size()];
			args = parameters.toArray(args);

			ModelGenerator modelGen = ModelGenerator.getModelInstance();
			modelGen.setParameters(args);
			Model model = modelGen.createModel(path);

			out.write(model.toString());
			out.close();
			fstream.close();

			double elapsedTime = 1. * (System.currentTimeMillis() - startTime) / 1000;
			System.out.println("Program Generation Time: " + elapsedTime + " seconds\n");
		} catch (Exception e) {
			System.out.println("Something went wrong. The program could not be generated.");
			System.out.println("Here the full stacktrace:");
			e.printStackTrace();
		}
	}



	/**
	 * It creates a Vadalog program from the input args
	 * Only used for testing purposes in corresponding test suite
	 */
	public void createModelFromInputArgsForTests(String[] args) {

		System.out.println("\n=====Building Vadalog Program=====");

		FileWriter fstream;
		try{
			long startTime = System.currentTimeMillis();

			String programName = args[args.length-1];
			createFolder(programName);
			String path = this.programPath + programName;			

			fstream = new FileWriter(path + "/iWarded_program.vada");
			BufferedWriter out = new BufferedWriter(fstream);

			ModelGenerator modelGen = ModelGenerator.getModelInstance();
			modelGen.setParameters(args);
			Model model = modelGen.createModel(path);

			out.write(model.toString());
			out.close();
			fstream.close();

			double elapsedTime = 1. * (System.currentTimeMillis() - startTime) / 1000;
			System.out.println("Program Generation Time: " + elapsedTime + " seconds\n");
		} catch (Exception e) {
			System.out.println("Something went wrong. The program could not be generated.");
			System.out.println("Here the full stacktrace:");
			e.printStackTrace();
		}
	}



	/**
	 * 
	 * It creates a Vadalog program from the configuration scenario provided as input by the User
	 * 
	 */
	public void createModelFromConfigScenario(String configPath) throws Exception {

		Properties p = new Properties();
		try {
			p.load(new FileInputStream(configPath));
		}
		catch (Exception e) {
			System.out.println("Unable to load " + configPath + " file");
			return;
		}

		System.out.println("\n=====Building Vadalog Program=====");
		List<String> parameters = new ArrayList<>();
		parameters.add(p.getProperty("NumberOfInputAtoms"));
		parameters.add(p.getProperty("NumberOfOutputAtoms"));
		parameters.add(p.getProperty("AverageVarsInAtom"));
		parameters.add(p.getProperty("VarianceVarsInAtom"));
		parameters.add(p.getProperty("NumberOfExistentialRules"));
		parameters.add(p.getProperty("AverageEVarsInRule"));
		parameters.add(p.getProperty("VarianceEVarsInRule"));
		parameters.add(p.getProperty("NumberOfDangerousRules"));
		parameters.add(p.getProperty("NumberOfLinearRules"));
		parameters.add(p.getProperty("NumberOfHarmlessHarmlessJoinsWithWard"));
		parameters.add(p.getProperty("NumberOfHarmlessHarmlessJoinsWithoutWard"));
		parameters.add(p.getProperty("NumberOfHarmlessHarmfulJoins"));
		parameters.add(p.getProperty("NumberOfHarmfulHarmfulJoins"));
		parameters.add(p.getProperty("AverageChaseSteps"));
		parameters.add(p.getProperty("NumberOfLinearRecursions"));
		parameters.add(p.getProperty("NumberOfLeftJoinRecursions"));
		parameters.add(p.getProperty("NumberOfRightJoinRecursions"));
		parameters.add(p.getProperty("NumberOfNonLinearJoinRecursions"));
		parameters.add(p.getProperty("AverageRecursionLength"));
		parameters.add(p.getProperty("NumberOfSelectionConditions"));
		parameters.add(p.getProperty("AverageSelectivity"));
		parameters.add(p.getProperty("NumberOfRecordsCSV"));
		parameters.add(p.getProperty("IsGuarded"));
		parameters.add(p.getProperty("ProgramName"));

		FileWriter fstream;
		try{
			long startTime = System.currentTimeMillis();

			String programName = parameters.get(parameters.size()-1);
			createFolder(programName);
			String path = this.programPath + programName;

			fstream = new FileWriter(path + "/iWarded_program.vada");
			BufferedWriter out = new BufferedWriter(fstream);
			String[] args = new String[parameters.size()];
			args = parameters.toArray(args);

			ModelGenerator modelGen = ModelGenerator.getModelInstance();
			modelGen.setParameters(args);
			Model model = modelGen.createModel(path);

			out.write(model.toString());
			out.close();
			fstream.close();

			double elapsedTime = 1. * (System.currentTimeMillis() - startTime) / 1000;
			System.out.println("Program Generation Time: " + elapsedTime + " seconds\n");
		} catch (Exception e) {
			System.out.println("Something went wrong. The program could not be generated.");
			System.out.println("Here the full stacktrace:");
			e.printStackTrace();
		}

	}



	/**
	 * It creates new directory for next program to generate
	 */
	private void createFolder(String programName) {
		File file = new File(this.programPath + programName);
		if (file.exists()) {
			try {
				FileUtils.cleanDirectory(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			Files.createDirectories(Paths.get(file.getPath()));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Main method, it launches the interactive shell
	 * 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		System.out.println("=====iWarded: Warded Datalog+/- Programs Generator=====");
		System.out.println("Copyright (C) 2021 https://www.gnu.org/licenses/gpl-3.0.html"); 
		System.out.println("authors: Teodoro Baldazzi, Luigi Bellomarini, Emanuel Sallinger");

		Options options = new Options();

		/*if no arguments, open iWarded command line for multiple uses*/
		if(args.length == 0) {			
			options.addOption("a", "args", false, "Build Program from Input Parameters");
			options.addOption("c", "config", false, "Build Program from Configuration Scenario");
			options.addOption("d", "docs", false, "Describe Input Parameters from Documentation");
			options.addOption("e", "exit", false, "Close iWarded");

			HelpFormatter formatter = new HelpFormatter();

			try {
				Terminal terminal = TerminalBuilder.builder().dumb(true).build();
				LineReader reader = LineReaderBuilder.builder()
						.terminal(terminal)
						.build();
				String prompt = "iwarded> ";

				while (true) {
					String line = null;
					try {
						line = reader.readLine(prompt);

						switch (line) {
						case "-a":
						case "--args":{
							IWardedGenerator run = new IWardedGenerator();
							run.createModelFromInputArgs(reader);
							System.out.println();
							break;
						}
						case "-c":
						case "--config":{
							System.out.print("Config file path: ");
							String path = reader.readLine();
							IWardedGenerator run = new IWardedGenerator();
							run.createModelFromConfigScenario(path);
							System.out.println();
							break;
						}
						case "-d":
						case "--docs":{
							IWardedGenerator run = new IWardedGenerator();
							run.presentParameters();
							System.out.println();
							break;
						}
						case "-e":
						case "--exit":{
							return;
						}
						default:
							formatter.printHelp("CommandLineParameters", options);
							System.out.println();
							break;
						}
					} catch (UserInterruptException e) {
						// Ignore
					} catch (EndOfFileException e) {
						return;
					}
				}
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}

		/*otherwise, generate single program based on input configuration scenario*/
		else {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd;

			options.addOption("a", "args", false, "Build Program from Input Parameters");
			options.addOption("c", "config", true, "Build Program from Configuration Scenario");
			options.addOption("d", "docs", false, "Describe Input Parameters from Documentation");
			options.addOption("e", "exit", false, "Close iWarded");

			HelpFormatter formatter = new HelpFormatter();

			try {
				cmd = parser.parse(options, args);
				if(cmd.hasOption("-a") || cmd.hasOption("--args")) {
					IWardedGenerator run = new IWardedGenerator();
					Terminal terminal = TerminalBuilder.builder().dumb(true).build();
					LineReader reader = LineReaderBuilder.builder()
							.terminal(terminal)
							.build();
					run.createModelFromInputArgs(reader);
				}
				else
					if(cmd.hasOption("-c") || cmd.hasOption("--config")) {
						IWardedGenerator run = new IWardedGenerator();
						run.createModelFromConfigScenario(args[1]);
					}
					else
						if(cmd.hasOption("-d") || cmd.hasOption("--docs")) {
							IWardedGenerator run = new IWardedGenerator();
							run.presentParameters();						}
						else
							if(cmd.hasOption("-e") || cmd.hasOption("--exit"))
								return;
							else
								formatter.printHelp("CommandLineParameters", options);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				formatter.printHelp("CommandLineParameters", options);
				System.exit(1);
			}
		}
	}
}
