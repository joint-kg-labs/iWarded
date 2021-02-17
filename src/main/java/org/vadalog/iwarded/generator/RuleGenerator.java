package org.vadalog.iwarded.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.vadalog.iwarded.model.Atom;
import org.vadalog.iwarded.model.Literal;
import org.vadalog.iwarded.model.Term;
import org.vadalog.iwarded.model.Variable;



/**
 * This class defines methods to handle the generation of a generic rule in iWarded
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
public abstract class RuleGenerator {

	protected final static String harmlessVariable = "HARMLESS_";
	protected final static String harmfulVariable = "HARMFUL_";

	protected final static String leftRecJoin = "leftRecJoin";
	protected final static String rightRecJoin = "rightRecJoin";


	ModelGenerator run;


	/**
	 * Constructor for RuleGenerator
	*/
	public RuleGenerator(){
		this.run = ModelGenerator.getModelInstance();
	}



	/**
	 * It builds the head for a rule involved in the input-output sequence
	 * 
	 * @param body
	 * @param isBodyAffected
	 * @param isDangerous
	 * @param isExistential
	 * @return the built literal
	 */
	public Atom createHeadChaseSteps(List<Literal> body,boolean isBodyAffected,boolean isDangerous,boolean isExistential){// boolean isWard, ){
		Atom head;
		List<Term> arguments = new ArrayList<>();
		Atom atomInBody = body.get(0).getAtom();

		String name = "idb_" + this.run.idbInstance;
		this.run.updateIdbInstance();

		/*add the arguments of the first atom in the body as arguments of the current one*/ 
		List<Term> previousArguments = atomInBody.getArguments();
		for(int i=0; i<previousArguments.size(); i++){
			Term t = previousArguments.get(i);
			arguments.add(t);
		}

		/*check whether there are and the number of affected positions in the first atom of the body*/
		Integer numAffectedPositionsAtomInBody = this.run.numAffectedPositionsPerAtom.get(atomInBody.getName());

		/*if it is an existential rule*/
		if(isExistential){
			Random r = new Random();
			Integer eVarsInAtom;

			/*if the atom in the body has affected positions*/
			/*remove the affected arguments from the atom in the head*/
			if(isBodyAffected) {
				for(int i=1; i<=numAffectedPositionsAtomInBody; i++)
					arguments.remove(arguments.size()-1);
			}

			/*add existential quantifications*/
			if(arguments.size()>=2) {
				eVarsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceEVarsInRule+this.run.averageEVarsInRule);
				while(eVarsInAtom<1 || eVarsInAtom>arguments.size()-1) {
					eVarsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceEVarsInRule+this.run.averageEVarsInRule);		
				}
				for(int i=1; i<=eVarsInAtom; i++)
					arguments.remove(arguments.size()-1);
				for(int i=1; i<=eVarsInAtom; i++) {
					arguments.add(new Variable(harmfulVariable + this.run.harmfulInstance));
					this.run.updateHarmfulInstance();			
				}
			}
			else {	//arguments.size()==1
				eVarsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceEVarsInRule+this.run.averageEVarsInRule);
				while(eVarsInAtom<1) {
					eVarsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceEVarsInRule+this.run.averageEVarsInRule);
				}
				for(int i=1; i<=eVarsInAtom; i++) {
					arguments.add(new Variable(harmfulVariable + this.run.harmfulInstance));
					this.run.updateHarmfulInstance();			
				}
			}
			/*update the affected atoms data structures*/
			this.run.affectedAtomNames.add(name);
			this.run.numAffectedPositionsPerAtom.put(name, eVarsInAtom);
		}

		else{	// !isExistential
			/*if it is dangerous, update the corresponding data structures*/
			if(isDangerous) {
				this.run.affectedAtomNames.add(name);
				this.run.numAffectedPositionsPerAtom.put(name, numAffectedPositionsAtomInBody);
			}
			else	// !isDangerous && !Existential
				/*if the atom in the body has affected positions*/
				/*remove the affected arguments from the atom in the head*/
				if(isBodyAffected) {
					for(int i=1; i<=numAffectedPositionsAtomInBody; i++)
						arguments.remove(arguments.size()-1);
				}
		}

		/*create the new atom with these arguments*/
		head = new Atom(name, arguments);

		return head;	
	}



	/**
	 * It builds the head for a rule involved in a recursive cycle
	 * 
	 * @param body
	 * @param isBodyAffected
	 * @param isDangerous
	 * @param isExistential
	 * @return the built literal
	 */
	public Atom createHeadRecursiveSteps(List<Literal> body, boolean isBodyAffected, boolean isDangerous, boolean isExistential) {
		return this.createHeadChaseSteps(body, isBodyAffected, isDangerous, isExistential);
	}



	/**
	 * It builds the head for a rule involved in an indirect recursive closure
	 * 
	 * @param body
	 * @param rHead
	 * @param isBodyAffected
	 * @param isRightRecJoin
	 * @return the built literal
	 */
	public Atom createHeadIndirectRecursiveClosure(List<Literal> body, Atom rHead, boolean isBodyAffected, boolean isRightRecJoin){
		Atom head;
		List<Term> arguments = new ArrayList<>();

		/*get the number of arguments for the current head*/
		/*from the previous creation of the atom*/
		Integer varsInAtom = rHead.getArguments().size();
		 
		/*select which atom is the first one in the body of the rule*/
		/*based on whether is is a righ recursive case or not*/
		Atom previousAtom;
		if(isRightRecJoin) {
			previousAtom = body.get(1).getAtom();
		}
		else {
			previousAtom = body.get(0).getAtom();
		}
		/*add the arguments of the first atom in the body as arguments of the current one*/ 
		List<Term> previousArguments = previousAtom.getArguments();
		for(int i=0; i<previousArguments.size() && i<varsInAtom; i++){
			Term t = previousArguments.get(i);
			arguments.add(t);
		}
		
		/*check whether there are and the number of affected positions in the first atom of the body*/
		Integer numAffectedPositionsMainAtomInBody = this.run.numAffectedPositionsPerAtom.get(previousAtom.getName());

		/*define remaining arguments of the current atom by adding the ones of the other atom in the join body*/
		varsInAtom = varsInAtom - arguments.size();
		while(varsInAtom>0) {
			Integer varsInAtomTemp = varsInAtom;
			
			if(body.size()>1) {		// join rule
				if(isRightRecJoin)
					previousAtom = body.get(0).getAtom();
				else
					previousAtom = body.get(1).getAtom();				
				previousArguments = previousAtom.getArguments();

				/*do not add arguments in affected positions, if present*/
				if(this.run.affectedAtomNames.contains(previousAtom.getName())) {
					Integer numAffectedPositionsOtherAtomInBody = this.run.numAffectedPositionsPerAtom.get(previousAtom.getName()); 
					for(int i=0; i<previousArguments.size()-numAffectedPositionsOtherAtomInBody && i<varsInAtomTemp; i++){
						Term t = previousArguments.get(i);
						arguments.add(0,t);
						varsInAtom--;
					}
				}
				else {
					for(int i=0; i<previousArguments.size() && i<varsInAtomTemp; i++){
						Term t = previousArguments.get(i);
						arguments.add(0,t);
						varsInAtom--;
					}
				}
			}
			
			else {	// linear rule
				if(!run.affectedAtomNames.contains(previousAtom.getName())) {
					Term t = arguments.get(arguments.size()-1);
					arguments.add(0,t);
					varsInAtom--;
				}
				else
					/*do not add arguments in affected positions, if present*/
					if(arguments.size()>1) {
						Integer numAffectedPositionsOtherAtomInBody = this.run.numAffectedPositionsPerAtom.get(previousAtom.getName()); 
						Term t = arguments.get(arguments.size()-numAffectedPositionsOtherAtomInBody-1);
						arguments.add(0,t);
						varsInAtom--;
					}		
			}
		}
		
		/*if the atom in the body has affected positions*/
		/*remove the affected arguments from the atom in the head*/
		if(isBodyAffected) {
			varsInAtom = arguments.size();
			for(int i=1; i<=numAffectedPositionsMainAtomInBody && i<varsInAtom; i++)
				arguments.remove(arguments.size()-1);
			for(int i=1; i<=numAffectedPositionsMainAtomInBody && i<varsInAtom; i++)
				arguments.add(arguments.get(arguments.size()-1));
		}

		/*create the new atom with these arguments*/
		head = new Atom(rHead.getName(), arguments);

		return head;
	}



	/**
	 * It builds the head for a rule involved in a direct recursive closure
	 * 
	 * @param rHead
	 * @param isBodyAffected
	 * @return the built literal
	 */
	public Atom createHeadDirectRecursiveClosure(Atom rHead, boolean isBodyAffected){
		Atom head;
		List<Term> arguments = new ArrayList<>();

		/*get the number of arguments for the current head*/
		/*from the atom in the body*/
		List<Term> previousArguments = rHead.getArguments();
		for(int i=0; i<previousArguments.size(); i++){
			Term t = previousArguments.get(i);
			arguments.add(t);
		}
		
		/*check whether there are and the number of affected positions in the atom in the body*/
		Integer numAffectedPositionsMainAtomInBody = this.run.numAffectedPositionsPerAtom.get(rHead.getName());
		
		/*if the atom in the body has affected positions*/
		/*remove the affected arguments from the atom in the head*/
		if(isBodyAffected) {
			for(int i=1; i<=numAffectedPositionsMainAtomInBody; i++)
				arguments.remove(arguments.size()-1);
			for(int i=1; i<=numAffectedPositionsMainAtomInBody; i++)
				arguments.add(arguments.get(arguments.size()-1));
		}
		
		/*create the new atom with these arguments*/
		head = new Atom(rHead.getName(), arguments);

		return head;
	}



	/**
	 * It builds the head for a rule involved in the secondary branch
	 * 
	 * @param body
	 * @return the built literal
	 */
	public Atom createHeadSecondaryRule(List<Literal> body) {
		return this.createHeadChaseSteps(body, false, false, false);
	}	
	
	
	
	
	/**
	 * It builds the head for a rule involved in a tertiary branch
	 * 
	 * @param body
	 * @param isBodyAffected
	 * @param isDangerous
	 * @param isExistential
	 * @return the built literal
	 */
	public Atom createHeadTertiaryRule(List<Literal> body,boolean isBodyAffected,boolean isDangerous,boolean isExistential) {
		return this.createHeadChaseSteps(body, isBodyAffected, isDangerous, isExistential);
	}



	/**
	 * It builds the head for a rule involved in an output closure
	 * 
	 * @param body
	 * @param outputAtomName
	 * @param isBodyAffected
	 * @param isDangerous
	 * @param isExistential
	 * @return the built literal
	 */
	public Atom createHeadOutputRule(List<Literal> body,String outputAtomName, boolean isBodyAffected, boolean isDangerous, boolean isExistential){
		Atom head;
		List<Term> arguments = new ArrayList<>();

		/*get the number of arguments for the current head*/
		/*from the atom in the body*/
		List<Term> previousArguments = body.get(0).getAtom().getArguments();
		for(int i=0; i<previousArguments.size(); i++){
			Term t = previousArguments.get(i);
			arguments.add(t);
		}
		if(isExistential){
			/*if the atom in the body has affected positions*/
			/*remove the affected arguments from the atom in the head*/
			if(arguments.size()>=2)
				arguments.remove(arguments.size()-1);
			/*add existential quantifications*/
			arguments.add(new Variable(harmfulVariable + this.run.harmfulInstance));
			this.run.updateHarmfulInstance();			
		}
		else{	// !isExistential
			/*if the atom in the body has affected positions*/
			/*remove the affected arguments from the atom in the head*/
			if(!isDangerous && isBodyAffected)
				arguments.remove(arguments.size()-1);
		}

		/*create the new atom with these arguments*/
		head = new Atom(outputAtomName, arguments);

		return head;
	}

}
