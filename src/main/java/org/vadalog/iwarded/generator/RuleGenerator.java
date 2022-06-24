package org.vadalog.iwarded.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.vadalog.iwarded.model.Atom;
import org.vadalog.iwarded.model.Literal;
import org.vadalog.iwarded.model.Position;
import org.vadalog.iwarded.model.Term;
import org.vadalog.iwarded.model.Variable;



/**
 * This class defines methods to handle the generation of a generic rule in iWarded
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
	 * It builds the head of a rule involved in the input-output sequence
	 * 
	 * @param body
	 * @param isBodyAffected
	 * @param isDangerous
	 * @param isExistential
	 * @return the built atom
	 */
	public Atom createHeadChaseSteps(List<Literal> body, boolean isBodyAffected, boolean isDangerous, boolean isExistential){
		Atom head;
		List<Term> arguments = new ArrayList<>();
		Atom firstAtomInBody = body.get(0).getAtom();

		String name = "idb_" + this.run.idbInstance;
		this.run.updateIdbInstance();

		/*add the arguments of the first atom in the body as arguments of the current one*/ 
		List<Term> previousArguments = firstAtomInBody.getArguments();
		for(int i=0; i<previousArguments.size(); i++){
			arguments.add(previousArguments.get(i));
		}
		/*check whether there are and the number of affected positions in the first atom of the body*/
		Integer numAffectedPositionsAtomInBody = this.run.numAffectedPositionsPerAtom.get(firstAtomInBody.getName());

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
				/*counter to attempt "while"*/
				int attemptsCounter = 3;
				while((eVarsInAtom<1 || eVarsInAtom>arguments.size()-1) && attemptsCounter>0) {
					eVarsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceEVarsInRule+this.run.averageEVarsInRule);		
					attemptsCounter--;
				}
				if(attemptsCounter==0)
					eVarsInAtom = 1;
				for(int i=1; i<=eVarsInAtom; i++)
					arguments.remove(arguments.size()-1);
			}
			else {	//arguments.size()==1
				eVarsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceEVarsInRule+this.run.averageEVarsInRule);
				/*counter to attempt "while"*/
				int attemptsCounter = 3;
				while(eVarsInAtom<1 && attemptsCounter>0) {
					eVarsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceEVarsInRule+this.run.averageEVarsInRule);
					attemptsCounter--;
				}
				if(attemptsCounter==0)
					eVarsInAtom = 1;
			}
			for(int i=1; i<=eVarsInAtom; i++) {
				arguments.add(new Variable(harmfulVariable + this.run.harmfulInstance));
				this.run.updateHarmfulInstance();

			}
			/*update the other affected positions data structures*/
			this.run.affectedAtomNames.add(name);
			this.run.numAffectedPositionsPerAtom.put(name, eVarsInAtom);
		}

		else{	// !isExistential
			/*if it is dangerous, update the corresponding data structures*/
			if(isDangerous) {		
				/*affected arguments propagated from body to head 
				/*are in the same positions in the two atoms*/
				for(int i=1; i<=numAffectedPositionsAtomInBody; i++) {
					Position newAffectedPos = new Position(name, arguments.size()-1-numAffectedPositionsAtomInBody+i);
					Position parentAffectedPos = new Position(firstAtomInBody.getName(), arguments.size()-1-numAffectedPositionsAtomInBody+i);
				}
				/*update the other affected positions data structures*/
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
		this.run.idbLiteralNamesNumArguments.put(head.getName(), head.getArguments().size());

		return head;	
	}



	/**
	 * It builds the head of a rule involved in a recursive sequence
	 * 
	 * @param body
	 * @param isBodyAffected
	 * @param isDangerous
	 * @param isExistential
	 * @return the built atom
	 */
	public Atom createHeadRecursiveSteps(List<Literal> body, boolean isBodyAffected, boolean isDangerous, boolean isExistential) {
		return this.createHeadChaseSteps(body, isBodyAffected, isDangerous, isExistential);
	}



	/**
	 * It builds the head of a rule involved in an indirect recursive closure
	 * 
	 * @param body
	 * @param rHead
	 * @param isBodyAffected
	 * @param isRightRecJoin
	 * @return the built atom
	 */
	public Atom createHeadIndirectRecursiveClosure(List<Literal> body, Atom rHead, boolean isBodyAffected, boolean isRightRecJoin){
		Atom head;
		List<Term> arguments = new ArrayList<>();

		/*select which atom is the first one in the body of the rule*/
		/*based on whether it is a right recursive case or not*/
		Atom firstAtomInBody;
		if(!isRightRecJoin)
			firstAtomInBody = body.get(0).getAtom();
		else
			firstAtomInBody = body.get(1).getAtom();

		/*get the number of arguments for the current head*/
		/*from the previous creation of the atom in the main branch*/
		Integer varsInAtom = rHead.getArguments().size();
		/*check whether there are and the number of affected positions in the first atom of the body*/
		Integer harmfulVarsInBody = 0;
		if(isBodyAffected)
			harmfulVarsInBody = this.run.numAffectedPositionsPerAtom.get(firstAtomInBody.getName());
		
		/*add harmless arguments of first atom in body as arguments of current one*/ 
		List<Term> bodyArguments = firstAtomInBody.getArguments();
		for(int i=0; i<varsInAtom && i<bodyArguments.size()-harmfulVarsInBody; i++){
			arguments.add(bodyArguments.get(i));
		}

		/*add remaining arguments of current atom*/
		varsInAtom = varsInAtom - arguments.size();
		/*check whether there are and the number of affected positions in the head*/
		Integer harmfulVarsInHead = 0;
		if(this.run.affectedAtomNames.contains(rHead.getName()))
			harmfulVarsInHead = this.run.numAffectedPositionsPerAtom.get(rHead.getName());
		/*define each harmless variable as argument*/
		for(int j = 1; j <= varsInAtom-harmfulVarsInHead; j++) {
			arguments.add(arguments.get(0));
		}
		/*define each harmful variable as argument*/
		for(int j = 1; j <= harmfulVarsInHead; j++) {
			arguments.add(new Variable(harmfulVariable + this.run.harmfulInstance));
			this.run.updateHarmfulInstance();
		}
		if(harmfulVarsInHead!=0)
			this.run.numberOfExistentialRules--;

		/*create the new atom with these arguments*/
		head = new Atom(rHead.getName(), arguments);

		return head;
	}



	/**
	 * It builds the head of a rule involved in a direct recursive closure
	 * 
	 * @param body
	 * @param rHead
	 * @param isBodyAffected
	 * @return the built atom
	 */
	public Atom createHeadDirectRecursiveClosure(List<Literal> body, Atom rHead, boolean isBodyAffected, boolean isRightRecJoin){
		return this.createHeadIndirectRecursiveClosure(body, rHead, isBodyAffected, isRightRecJoin);
	}



	/**
	 * It builds the head of a rule involved in a tertiary branch
	 * 
	 * @param body
	 * @param isBodyAffected
	 * @param isDangerous
	 * @param isExistential
	 * @param isForOutput
	 * @return the built atom
	 */
	public Atom createHeadTertiaryRule(List<Literal> body, boolean isBodyAffected, boolean isDangerous, boolean isExistential, boolean isForOutput) {
		if(isForOutput) {
			Random r = new Random();
			String outputAtomName = this.run.outputAtomNames.get(r.nextInt(this.run.outputAtomNames.size()));
			return this.createHeadOutputRule(body, outputAtomName, isBodyAffected, isDangerous, isExistential);
		}
		return this.createHeadChaseSteps(body, isBodyAffected, isDangerous, isExistential);
	}



	/**
	 * It builds the head of a rule involved in an output closure
	 * 
	 * @param body
	 * @param outputAtomName
	 * @param isBodyAffected
	 * @param isDangerous
	 * @param isExistential
	 * @return the built atom
	 */
	public Atom createHeadOutputRule(List<Literal> body, String outputAtomName, boolean isBodyAffected, boolean isDangerous, boolean isExistential){
		Atom head;
		List<Term> arguments = new ArrayList<>();
		Integer varsInAtom = 0;

		Atom firstAtomInBody = body.get(0).getAtom();
		/*check if the atom has already been defined previously*/
		boolean atomAlreadyGenerated = this.run.outputLiteralNamesNumArguments.containsKey(outputAtomName);
		/*get the number of arguments for the current head*/
		if(atomAlreadyGenerated)
			/*from the previous creation of the atom*/
			varsInAtom = this.run.outputLiteralNamesNumArguments.get(outputAtomName);
		else
			/*from the first atom in the body*/
			varsInAtom = firstAtomInBody.getArguments().size();

		/*check whether there are and the number of affected positions in the first atom of the body*/
		Integer harmfulVarsInBody = 0;
		if(isBodyAffected)
			harmfulVarsInBody = this.run.numAffectedPositionsPerAtom.get(firstAtomInBody.getName());
		/*add harmless arguments of first atom in body as arguments of current one*/ 
		List<Term> bodyArguments = firstAtomInBody.getArguments();
		for(int i=0; i<varsInAtom && i<bodyArguments.size()-harmfulVarsInBody; i++){
			arguments.add(bodyArguments.get(i));
		}
		/*add remaining arguments of current atom*/
		varsInAtom = varsInAtom - arguments.size();
		for(int i=0; i<varsInAtom; i++){
			arguments.add(bodyArguments.get(bodyArguments.size()-harmfulVarsInBody-1));
		}

		/*create the new atom with these arguments*/
		head = new Atom(outputAtomName, arguments);
		if(!atomAlreadyGenerated)
			this.run.outputLiteralNamesNumArguments.put(head.getName(), head.getArguments().size());

		return head;
	}


}
