package org.vadalog.iwarded.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vadalog.iwarded.utils.Utils;



/**
 * This class handles the compatibility of parameters in iWarded
 * If iWarded required different parameters to build the program,
 * it adds the changes as comments in the .vada file
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
public class CompatibilityAdapter {

	private ModelGenerator run;

	/*copy of input variables which may be changed by iWarded*/
	/*to succeed in the generation of the program*/	
	private Integer numberOfInputAtoms;
	private Integer averageEVarsInRule;
	private Float varianceEVarsInRule;
	
	private Integer numberOfLinearRules;
	private Integer numberOfJoinRules;
	private Integer numberOfExistentialRules;
	private Integer numberOfDangerousRules;
	
	private Integer numberOfHarmlessHarmlessJoinsWithWard;
	private Integer numberOfHarmlessHarmlessJoinsWithoutWard;
	private Integer numberOfHarmlessHarmfulJoins;
	private Integer numberOfHarmfulHarmfulJoins;
	private Integer numberOfNonLinearJoinRecursions;
	
	private boolean isGuarded;

	private boolean noHHJoinsDueToGuardedness;
	private boolean noNonLinJoinRecsDueToGuardedness;



	/**
	 * Constructor for CompatibilityAdapter
	*/
	public CompatibilityAdapter() {
		this.run = ModelGenerator.getModelInstance();
	}



	/**
	 * It initializes the parameters as the original ones from input
	 */
	public void initializeParametersCompatibility() {
		this.numberOfInputAtoms = this.run.numberOfInputAtoms;
		this.averageEVarsInRule = this.run.averageEVarsInRule;
		this.varianceEVarsInRule = this.run.varianceEVarsInRule;

		this.numberOfLinearRules = this.run.numberOfLinearRules;
		this.numberOfExistentialRules = this.run.numberOfExistentialRules;
		this.numberOfDangerousRules = this.run.numberOfDangerousRules;

		this.numberOfHarmlessHarmlessJoinsWithWard = this.run.numberOfHarmlessHarmlessJoinsWithWard;
		this.numberOfHarmlessHarmlessJoinsWithoutWard = this.run.numberOfHarmlessHarmlessJoinsWithoutWard;
		this.numberOfHarmlessHarmfulJoins = this.run.numberOfHarmlessHarmfulJoins;
		this.numberOfHarmfulHarmfulJoins = this.run.numberOfHarmfulHarmfulJoins;
		this.numberOfNonLinearJoinRecursions = this.run.numberOfNonLinearJoinRecursions;
		
		this.isGuarded = this.run.isGuarded;
	}
	
	
	
	/**
	 * It updates the parameters for compatibility 
	 */
	public void updateParametersCompatibility() {
		
		/*not possible to have more evars than vars in head of rule*/
		if(this.averageEVarsInRule-this.varianceEVarsInRule>this.run.averageVarsInAtom-this.run.varianceVarsInAtom) {
			this.run.averageEVarsInRule = this.run.averageVarsInAtom;
			this.run.varianceEVarsInRule = this.run.varianceVarsInAtom;
		}
		
		this.numberOfJoinRules = this.run.numberOfJoinRules;
		/*due to join generation procedure, iWarded (right now) cannot build HH joins or non linear rec joins to satisfy guardedness*/
		/*because it would require to modify the atoms to make one a guard, which would affect the whole program*/
		// TODO: allow these two types of structures even in Guarded scenarios
		if(this.isGuarded) {
			if(this.numberOfHarmfulHarmfulJoins!=0) {
				this.numberOfJoinRules -= this.numberOfHarmfulHarmfulJoins;
				this.run.numberOfJoinRules -= this.numberOfHarmfulHarmfulJoins;
				this.numberOfHarmfulHarmfulJoins = 0;
				this.run.numberOfHarmfulHarmfulJoins = 0;
				this.noHHJoinsDueToGuardedness = true;
			}
			if(this.numberOfNonLinearJoinRecursions!=0) {
				this.numberOfNonLinearJoinRecursions = 0;
				this.run.numberOfNonLinearJoinRecursions = 0;
				this.noNonLinJoinRecsDueToGuardedness = true;
				Map<String,Integer> rLengthTemp = new HashMap<>(this.run.rLength);
				for(String recName : rLengthTemp.keySet()) {
					if(recName.contains("nonLinJRec_")) {
						this.run.rLength.remove(recName);
						this.run.directRecursionName.remove(recName);
						this.run.indirectRecursionName.remove(recName);
					}
				}
			}
		}
	}


	/**
	 * It checks original parameters after program generation and, if needed,
	 * add adapted program parameters to list of vada comments
	 * 
	 * @return string containing the description of adapted parameters
	 */
	public List<String> addAdaptedProgramParametersToVadaFile() {
		System.out.println("\n=====Generating comments for the vada program=====");

		List<String> parameters = new ArrayList<>();

		/*if, after the generation of the program, some parameters differ*/
		/*from the expected value, inform the User by adding the changed*/
		/*parameters to the list of comments for the vada file*/
		parameters.add("% =====ADAPTED VADA PROGRAM PARAMETERS=====");

		if(this.numberOfInputAtoms!=this.run.inputLiterals.size())
			parameters.add("% Number of Input Atoms: " + this.run.inputLiterals.size());
		if(this.run.numberOfLinearRules!=0) {
			this.numberOfLinearRules = this.numberOfLinearRules - this.run.numberOfLinearRules;
			parameters.add("% Number of Linear Rules: " + this.numberOfLinearRules);
		}
		
		if(this.averageEVarsInRule!=this.run.averageEVarsInRule) {
			parameters.add("% Average number of Existentially Quantified Variables per Rule: " + this.run.averageEVarsInRule);
			parameters.add("% Variance of Existentially Quantified Variables per Rule: " + this.run.varianceEVarsInRule);

		}
		
		if(this.run.numberOfExistentialRules!=0) {
			this.numberOfExistentialRules = this.numberOfExistentialRules - this.run.numberOfExistentialRules;
			parameters.add("% Number of Existential Rules: " + this.numberOfExistentialRules);
		}
		if(this.run.numberOfDangerousRules!=0) {
			this.numberOfDangerousRules = this.numberOfDangerousRules - this.run.numberOfDangerousRules;
			parameters.add("% Number of Dangerous Rules: " + this.numberOfDangerousRules);
		}
		if(this.run.numberOfJoinRules!=0) {
			this.numberOfJoinRules = this.numberOfJoinRules - this.run.numberOfJoinRules;
			parameters.add("% Number of Join Rules: " + this.numberOfJoinRules);
		}
		if(this.run.numberOfHarmlessHarmlessJoinsWithWard!=0) {
			this.numberOfHarmlessHarmlessJoinsWithWard = this.numberOfHarmlessHarmlessJoinsWithWard - this.run.numberOfHarmlessHarmlessJoinsWithWard;
			parameters.add("% Number of Harmless-Harmless Join Rules with Ward: " + this.numberOfHarmlessHarmlessJoinsWithWard);
		}
		if(this.run.numberOfHarmlessHarmlessJoinsWithoutWard!=0) {
			this.numberOfHarmlessHarmlessJoinsWithoutWard = this.numberOfHarmlessHarmlessJoinsWithoutWard - this.run.numberOfHarmlessHarmlessJoinsWithoutWard;
			parameters.add("% Number of Harmless-Harmless Join Rules without Ward: " + this.numberOfHarmlessHarmlessJoinsWithoutWard);
		}
		if(this.run.numberOfHarmlessHarmfulJoins!=0) {
			this.numberOfHarmlessHarmfulJoins = this.numberOfHarmlessHarmfulJoins - this.run.numberOfHarmlessHarmfulJoins;
			parameters.add("% Number of Harmless-Harmful Joins: " + this.numberOfHarmlessHarmfulJoins);
		}
		if(this.run.numberOfHarmfulHarmfulJoins!=0 || this.noHHJoinsDueToGuardedness) {
			this.numberOfHarmfulHarmfulJoins = this.numberOfHarmfulHarmfulJoins - this.run.numberOfHarmfulHarmfulJoins;
			parameters.add("% Number of Harmful-Harmful Joins: " + this.numberOfHarmfulHarmfulJoins);
		}
		if(this.noNonLinJoinRecsDueToGuardedness) {
			parameters.add("% Number of Non-Linear Join Recursions: " + this.numberOfNonLinearJoinRecursions);
			parameters.add("% Length for each Recursion: ");
			parameters.add(Utils.printSortedMapFromHashMap(this.run.rLength));
		}

		parameters.add("% ==========================================\n");

		return parameters;
	}

}
