package org.vadalog.iwarded.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.vadalog.iwarded.model.Literal;
import org.vadalog.iwarded.model.Term;
import org.vadalog.iwarded.model.Variable;



/**
 * This class handles the generation of data from parameters in iWarded 
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
public class DataGenerator {

	ModelGenerator run;
	


	/**
	 * Constructor for ConditionsGenerator
    */
	public DataGenerator(){
		this.run = ModelGenerator.getModelInstance();
	}



	/**
	 * It defines names and attributes of atoms and rules for the vada program
	*/
	public void defineAtomsAndRules() {

		System.out.println("\n=====Checking values of parameters for the vada program=====");

		Random rand = new Random();

		/*here we define a name for each input atom*/
		this.run.inputAtomNames = new ArrayList<>();		
		for(int i = 1; i <= this.run.numberOfInputAtoms; i++) {
			String inputAtomName = "edb_" + i;
			this.run.inputAtomNames.add(inputAtomName);
		}
		
		/*if Guarded, we store the name of one atom with a single argument to handle guarded joins if input atoms are over*/
		if(this.run.isGuarded) {
			String name = this.run.inputAtomNames.get(0);
			this.run.inputAtomNameForGuarded = name;
			this.run.inputLiteralNamesArguments.put(name, 1);
			List<Term> args = new ArrayList<>();
			args.add(new Variable("HARMLESS_" + this.run.harmlessInstance));
			this.run.inputLiterals.add(new Literal(name, args));
		}

		/*here we define name for each output atom and link it to a specific number of chase steps*/
		this.run.outputAtomNames = new ArrayList<>();
		for(int i = 1; i <= this.run.numberOfOutputAtoms; i++) {
			String outputAtomName = "out_" + i;
			this.run.outputAtomNames.add(outputAtomName);
			int steps = (int) Math.round(rand.nextGaussian()*+this.run.averageChaseSteps);
			
			/*minimum value has to be 1, considering the minimum vada program out_1 :- edb_1 has 2 steps*/
			while(steps<1)					
				steps = (int) Math.round(rand.nextGaussian()*+this.run.averageChaseSteps);

			this.run.cSteps.put(outputAtomName, steps);
		}

		/*here we calculate the total number of join rules*/
		this.run.numberOfJoinRules = this.run.numberOfHarmlessHarmlessJoinsWithWard 
				+ this.run.numberOfHarmlessHarmlessJoinsWithoutWard
				+ this.run.numberOfHarmlessHarmfulJoins + this.run.numberOfHarmfulHarmfulJoins;

		if(this.run.averageRecursionLength > 0) {
			/*here we define name for each linear recursion and associate it to recursion length*/
			for(int i=1; i<=this.run.numberOfLinearRecursions; i++){
				String recursionSingleName = "linRec_" + i;
				int length = (int) Math.round(rand.nextGaussian()*+this.run.averageRecursionLength);

				while(length<1)
					length = (int) Math.round(rand.nextGaussian()*+this.run.averageRecursionLength);

				if(length==1) //DIRECT
					this.run.directRecursionName.add(recursionSingleName);

				else //INDIRECT
					this.run.indirectRecursionName.add(recursionSingleName);

				this.run.rLength.put(recursionSingleName, length);
				this.run.recLinNames.add(recursionSingleName);
			}

			/*here we define name for each left join recursion and associate it to recursion length*/
			for(int i=1; i<=this.run.numberOfLeftJoinRecursions; i++){
				String recursionSingleName = "leftJRec_" + i;
				int length = (int) Math.round(rand.nextGaussian()*+this.run.averageRecursionLength);

				while(length<1)
					length = (int) Math.round(rand.nextGaussian()*+this.run.averageRecursionLength);

				if(length==1) //DIRECT
					this.run.directRecursionName.add(recursionSingleName);

				else //INDIRECT
					this.run.indirectRecursionName.add(recursionSingleName);

				this.run.rLength.put(recursionSingleName, length);
				this.run.recLeftJoinNames.add(recursionSingleName);
			}

			/*here we define name for each right join recursion and associate it to recursion length*/
			for(int i=1; i<=this.run.numberOfRightJoinRecursions; i++){
				String recursionSingleName = "rightJRec_" + i;
				int length = (int) Math.round(rand.nextGaussian()*+this.run.averageRecursionLength);

				while(length<1)
					length = (int) Math.round(rand.nextGaussian()*+this.run.averageRecursionLength);

				if(length==1) //DIRECT
					this.run.directRecursionName.add(recursionSingleName);

				else //INDIRECT
					this.run.indirectRecursionName.add(recursionSingleName);

				this.run.rLength.put(recursionSingleName, length);
				this.run.recRightJoinNames.add(recursionSingleName);
			}

			/*here we define name for each non-linear recursion and associate it to recursion length*/
			for(int i=1; i<=this.run.numberOfNonLinearJoinRecursions; i++){
				String recursionSingleName = "nonLinJRec_" + i;
				int length = (int) Math.round(rand.nextGaussian()*+this.run.averageRecursionLength);

				while(length<1)
					length = (int) Math.round(rand.nextGaussian()*+this.run.averageRecursionLength);

				if(length==1) //DIRECT
					this.run.directRecursionName.add(recursionSingleName);

				else //INDIRECT
					this.run.indirectRecursionName.add(recursionSingleName);

				this.run.rLength.put(recursionSingleName, length);
				this.run.recNonLinearJoinNames.add(recursionSingleName);
			}

		}
	}

}
