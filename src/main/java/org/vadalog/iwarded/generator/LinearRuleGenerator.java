package org.vadalog.iwarded.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.vadalog.iwarded.model.Atom;
import org.vadalog.iwarded.model.Literal;
import org.vadalog.iwarded.model.Term;
import org.vadalog.iwarded.model.Variable;



/**
 * This class handles the generation of a linear rule in iWarded
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
public class LinearRuleGenerator extends RuleGenerator{



	/**
	 * Constructor for Linear Rule
	 * 
	*/
	public LinearRuleGenerator(){
		super();
	}



	/**
	 * It builds the body for a linear rule involved in the input-output sequence
	 * 
	 * @param lastAtomInHeadLinearSequence
	 * @param isRoot
	 * @return the built literal
	 */
	public Literal createLinearBodyChaseSteps(Atom lastAtomInHeadLinearSequence, boolean isRoot){
		Literal body;
		Random r = new Random();
		boolean alreadyUsedAtomName = false;

		/*create body atom*/		
		/*if this is the root of the program, the body is an edb*/
		if(isRoot){
			String name;
			Integer varsInAtom = 0;
			List<Term> arguments = new ArrayList<>();

			Integer indexInput = this.run.inputAtomNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, this.run.inputAtomNames.size());
			name = this.run.inputAtomNames.get(indexInput);

			/*obtain number of arguments for the atom, based on input average and variance values*/
			alreadyUsedAtomName = this.run.inputLiteralNamesArguments.containsKey(name);

			/*obtain number of arguments for the atom*/
			if(alreadyUsedAtomName)
				varsInAtom = this.run.inputLiteralNamesArguments.get(name);
			else {
				varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInAtom+this.run.averageVarsInAtom);
				while(varsInAtom<2)		// because the root requires it for the program to work
					varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInAtom+this.run.averageVarsInAtom);
			}
			/*define each variable as argument*/
			for(int j = 1; j <= varsInAtom; j++) {
				Variable v;
				v = new Variable(harmlessVariable + this.run.harmlessInstance);
				this.run.updateHarmlessInstance();
				arguments.add(v);
			}			
			body = new Literal(name, arguments);
		}
		/*otherwise, it is the idb */
		else{
			body =  new Literal(lastAtomInHeadLinearSequence,true);

		}

		/*if it is the root, update the edb data structures*/
		if(isRoot && !alreadyUsedAtomName){
			this.run.inputLiterals.add(body);
			this.run.inputLiteralNamesArguments.put(body.getAtom().getName(),body.getAtom().getArguments().size());
		}	

		return body;		
	}



	/**
	 * It builds the body for a linear rule involved in a recursive cycle
	 * 
	 * @param lastAtomInHead
	 * @return the built literal
	 */
	public Literal createLinearBodyRecursiveSteps(Atom lastAtomInHead){
		return this.createLinearBodyChaseSteps(lastAtomInHead, false);
	}



	/**
	 * It builds the body for a linear rule involved in an indirect recursive closure
	 * 
	 * @param rLast
	 * @return the built literal
	 */
	public Literal createLinearBodyIndirectRecursiveClosure(Atom rLast){
		return this.createLinearBodyChaseSteps(rLast, false);

	}



	/**
	 * It builds the body for a linear rule involved in a direct recursive closure
	 * 
	 * @return the built literal
	 */
	public Literal createLinearBodyDirectRecursiveClosure() {

		Literal body;
		List<Term> arguments = new ArrayList<>();

		List<String> idbNames = new ArrayList<>(this.run.idbNumVariables.keySet());
		Integer indexInput = idbNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, idbNames.size());
		String name = idbNames.get(indexInput);

		/*the number of arguments is the one already defined for this idb*/
		Integer varsInAtom = this.run.idbNumVariables.get(name);
		/*create each variable*/
		for(int j = 1; j <= varsInAtom; j++) {
			Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
			this.run.harmlessInstance ++;
			arguments.add(v);
		}
		body = new Literal(name, arguments);

		/*save the literal to build the head*/
		this.run.literalDirectRec = body;

		return body;

	}
	
	
	
	/**
	 * It builds the body for a linear rule involved in the secondary branch
	 * 
	 * @param firstExistentialHead
	 * @return the built literal	 
	 */
	public Literal createLinearBodySecondaryRule(Atom firstExistentialHead) {
		return this.createLinearBodyChaseSteps(firstExistentialHead, false);
	}
	
	
	
	/**
	 * It builds the body for a linear rule involved in a tertiary branch
	 * 
	 * @param firstExistentialHead
	 * @return the built literal	 
	 */
	public Literal createLinearBodyTertiaryRule(Atom firstExistentialHead) {
		return this.createLinearBodyChaseSteps(firstExistentialHead, false);
	}


	
	/**
	 * It builds the body for a linear rule involved in an output closure
	 * 
	 * @param lastAtomBeforeOutput
	 * @return the built literal
	 */
	public Literal createLinearBodyOutputRule(Atom lastAtomBeforeOutput){
		return this.createLinearBodyChaseSteps(lastAtomBeforeOutput, false);		
	}

}
