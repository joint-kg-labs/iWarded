package org.vadalog.iwarded.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.vadalog.iwarded.model.Atom;
import org.vadalog.iwarded.model.Literal;
import org.vadalog.iwarded.model.Term;
import org.vadalog.iwarded.model.Variable;



/**
 * This class handles the generation of a join rule in iWarded
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
public class JoinRuleGenerator extends RuleGenerator{

	private final static String joinWithEdb = "joinWithEdb";
	private final static String joinWithIdb = "joinWithIdb";

	private final static String joinSameIdb = "sameIdb";
	private final static String joinDifferentIdb = "differentIdb";


	/**
	 * Constructor for JoinRuleGenerator
	 */
	public JoinRuleGenerator(){
		super();
	}



	/**
	 * It randomly chooses whether the current join will be between idb atoms
	 * or between an idb and an edb
	 * 
	 * @return whether the join is with edb or idb
	 */
	private String joinWithEdbOrIdb(){
		int edbOrIdb = ThreadLocalRandom.current().nextInt(0, 1 + 1);
		if(edbOrIdb == 0)
			return joinWithEdb;
		else
			return joinWithIdb;
	}



	/**
	 * It randomly chooses whether the current join will be 
	 * between instances of the same idb atom or two distinct ones	 
	 *  
	 * @return whether the join is with the same idb or a different one
	 */
	private String joinWithSameOrDifferentIdb() {
		int sameOrDifferentIdb = ThreadLocalRandom.current().nextInt(0, 1 + 1);
		if(sameOrDifferentIdb == 0)
			return joinSameIdb;
		else
			return joinDifferentIdb;
	}



	/**
	 * It builds the body of a join rule involved in the input-output sequence
	 * 
	 * @param lastAtomInHead
	 * @param typeOfJoin
	 * @param isGuarded
	 * @return the built list of literals
	 */
	public List<Literal> createJoinBodyChaseSteps(Atom lastAtomInHead, String typeOfJoin, boolean isGuarded){
		Random r = new Random();
		List<Literal> body = new ArrayList<>();

		/*create LEFT body atom - last idb in head*/
		Literal left = new Literal(lastAtomInHead, true);
		body.add(left);
		List<Term> argumentsLeft = lastAtomInHead.getArguments();

		/*decide which variable is the join variable*/
		/*the type of join influences this choice*/
		Variable joinVariable = null;	
		/*harmless joins require a harmless join variable*/
		/*by construction, the first variable is always harmless*/
		if(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
			joinVariable = (Variable) argumentsLeft.get(0);
		else
			/*harmful joins require a harmful join variable*/
			/*by construction, if the atom is affected, the last variable is always harmful*/
			if(typeOfJoin.equals(ModelGenerator.joinhH) || typeOfJoin.equals(ModelGenerator.joinHH))
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-1);	


		/*create RIGHT body atom - edb or idb*/
		Literal right;
		String inputAtomName = null;
		List<Term> argumentsRight = new ArrayList<>();

		/*decide whether the join is with edb or idb*/
		/*the type of join and the possible guardedness requirement influence this choice*/
		String edbOrIdb = null;
		/*with harmless joins the choice is random*/
		if((typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW)) && !isGuarded)
			edbOrIdb = this.joinWithEdbOrIdb();
		else
			/*with harmless-harmful joins it is required to have an edb as right atom*/
			if(typeOfJoin.equals(ModelGenerator.joinhH) || isGuarded)
				edbOrIdb = joinWithEdb;
			else
				/*with harmful-harmful joins it is required to have an idb as right atom*/
				if(typeOfJoin.equals(ModelGenerator.joinHH))
					edbOrIdb = joinWithIdb;

		/*build the atom based on the previous choice*/
		Integer varsInAtom = 0;

		if(edbOrIdb.equals(joinWithEdb)){
			int indexInput = this.run.inputAtomNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, this.run.inputAtomNames.size());
			inputAtomName = this.run.inputAtomNames.get(indexInput);

			/*check if the atom has already been defined previously*/
			boolean atomAlreadyGenerated = this.run.inputLiteralNamesNumArguments.containsKey(inputAtomName);
			if(atomAlreadyGenerated) {
				varsInAtom = this.run.inputLiteralNamesNumArguments.get(inputAtomName);
				/*if isGuarded, the atom has to be with a single argument (or new, as in the "else" case)*/
				/*to avoid variables different from the ones in the left atom and respect guardedness*/
				if(isGuarded && varsInAtom!=1) {
					inputAtomName = this.run.inputAtomNameForGuarded;
					varsInAtom = this.run.inputLiteralNamesNumArguments.get(inputAtomName);
				}
			}
			else {
				/*obtain number of arguments for the atom, based on input average and variance values*/
				if(!isGuarded) {
					varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInPredicate + this.run.averageVarsInPredicate);
					/*counter to attempt "while"*/
					int attemptsCounter = 3;
					while(varsInAtom<1 && attemptsCounter>0) {
						varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInPredicate + this.run.averageVarsInPredicate);
						attemptsCounter--;
					}
					if(attemptsCounter==0)
						varsInAtom = 2;
				}
				/*if isGuarded, here the only variable is the join one to fulfill the guardedness requirement without changing left atom*/
				else
					varsInAtom = 1;
			}

			/*define each variable as argument*/
			for(int j = 1; j <= varsInAtom-1; j++) {
				argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
				this.run.updateHarmlessInstance();

			}
			/*add join variable*/
			argumentsRight.add(joinVariable);

			/*create the new atom with these arguments*/
			right = new Literal(inputAtomName, argumentsRight);

			/*update the edb data structures, if needed*/
			if(!atomAlreadyGenerated) {
				this.run.inputLiterals.add(right);
				this.run.inputLiteralNamesNumArguments.put(inputAtomName,varsInAtom);
			}
		}

		else{	 // joinWithIdb
			/*decide whether the join is with the same idb or a distinct one*/
			/*the type of join does not influence this choice*/
			String sameOrDifferentIdb = this.joinWithSameOrDifferentIdb();

			/*if same idb, use the left atom just created*/
			if(sameOrDifferentIdb.equals(joinSameIdb)) {
				inputAtomName = left.getAtom().getName();
				varsInAtom = left.getAtom().getArguments().size();
			}
			/*if different idb, select an idb already defined*/
			else { //joinDifferentIdb
				/*select an idb as right atom among the ones already defined*/
				/*the type of join influences this choice*/
				List<String> idbNames;
				/*with harmless-harmless joins it is not required to choose an atom with affected positions*/
				if(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW)) {
					/*choose atom among the idb ones already generated*/
					idbNames = new ArrayList<>(this.run.idbLiteralNamesNumArguments.keySet());
				}
				/*with harmful-harmful joins it is required to choose an atom with affected positions*/
				else {	//joinHH, as joinhH can only be with edb by construction
					/*choose atom among the idb ones already generated which are affected*/
					idbNames = new ArrayList<>(this.run.affectedAtomNames);
				}
				int indexInput = idbNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, idbNames.size());
				inputAtomName = idbNames.get(indexInput);
				/*get number of arguments from the previous creation of the atom*/
				varsInAtom = this.run.idbLiteralNamesNumArguments.get(inputAtomName);
			}

			/*get possible number of affected positions in the atom*/
			Integer harmfulVarsInAtom = 0;
			if(this.run.affectedAtomNames.contains(inputAtomName))
				harmfulVarsInAtom = this.run.numAffectedPositionsPerAtom.get(inputAtomName);

			/*the type of join influences the order of the variables*/
			if(harmfulVarsInAtom!=0 && 
					(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))){
				/*add join variable in first position, as it is harmless by construction*/
				argumentsRight.add(joinVariable);
				/*define each harmless variable as argument*/
				for(int j = 1; j <= varsInAtom-1-harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
					this.run.updateHarmlessInstance();
				}
				/*define each harmful variable as argument*/
				for(int j = 1; j <= harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmfulVariable + this.run.harmfulInstance));
					this.run.updateHarmfulInstance();
				}
			}
			else{
				/*define each harmless variable as argument*/
				for(int j = 1; j <= varsInAtom-harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
					this.run.updateHarmlessInstance();
				}
				/*define each harmful variable as argument*/
				for(int j = 1; j <= harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmfulVariable + this.run.harmfulInstance));
					this.run.updateHarmfulInstance();
				}
				/*add join variable in last position*/
				argumentsRight.remove(argumentsRight.size()-1);
				argumentsRight.add(joinVariable);
			}
			/*create the new atom with these arguments*/
			right = new Literal(inputAtomName, argumentsRight);
		}

		body.add(right);

		return body;	
	}



	/**
	 * It builds the body of a join rule involved in a recursive cycle
	 * 
	 * @param lastAtomInHead
	 * @param typeOfJoin
	 * @param isGuarded
	 * @return the built list of literals
	 */
	public List<Literal> createJoinBodyRecursiveSteps(Atom lastAtomInHead, String typeOfJoin, boolean isGuarded){
		return this.createJoinBodyChaseSteps(lastAtomInHead, typeOfJoin, isGuarded);
	}



	/**
	 * It builds the body of a join rule involved in an indirect recursive closure
	 * 
	 * @param rLast
	 * @param typeOfJoin
	 * @param typeOfRecJoin
	 * @param recName
	 * @param isGuarded
	 * @return the built list of literals
	 */
	public List<Literal> createJoinBodyIndirectRecursiveClosure(Atom rLast, String typeOfJoin, String typeOfRecJoin, String recName, boolean isGuarded) {
		Random r = new Random();
		List<Literal> body = new ArrayList<>();

		/*create LEFT body atom - last idb in head which closes recursive sequence*/
		Literal left = new Literal(rLast,true);
		body.add(left);
		List<Term> argumentsLeft = rLast.getArguments();

		/*decide which variable is the join variable*/
		/*the type of join influences this choice*/
		Variable joinVariable = null;
		/*harmless joins require a harmless join variable*/
		/*by construction, the first variable is always harmless*/
		if(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
			joinVariable = (Variable) argumentsLeft.get(0);
		else
			/*harmful joins require a harmful join variable*/
			/*by construction, if the atom is affected, the last variable is always harmful*/
			if(typeOfJoin.equals(ModelGenerator.joinhH) || typeOfJoin.equals(ModelGenerator.joinHH))
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-1);	


		/*create RIGHT body atom - edb or idb*/
		Literal right;
		String name = null;
		List<Term> argumentsRight = new ArrayList<>();	

		/*decide whether the join is with edb or idb and build the atom*/
		/*the type of recursion influences this choice*/
		Integer varsInAtom = 0;
		/*with left or right recursive joins it is required to have an edb as right atom, to avoid additional recursive closures*/
		if(typeOfRecJoin.equals(leftRecJoin) || typeOfRecJoin.equals(rightRecJoin)){
			int indexInput = this.run.inputAtomNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, this.run.inputAtomNames.size());
			name = this.run.inputAtomNames.get(indexInput);			

			/*check if the atom has already been defined previously*/
			boolean atomAlreadyGenerated = this.run.inputLiteralNamesNumArguments.containsKey(name);			
			if(atomAlreadyGenerated) {
				varsInAtom = this.run.inputLiteralNamesNumArguments.get(name);
				/*if isGuarded, the atom has to be with a single argument or new*/
				/*to avoid variables different from the ones in the left atom and respect guardedness*/
				if(isGuarded && varsInAtom!=1) {
					name = this.run.inputAtomNameForGuarded;
					varsInAtom = this.run.inputLiteralNamesNumArguments.get(name);
				}
			}
			else {
				/*obtain number of arguments for the atom, based on input average and variance values*/
				if(!isGuarded) {
					varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInPredicate + this.run.averageVarsInPredicate);
					/*counter to attempt "while"*/
					int attemptsCounter = 3;
					while(varsInAtom<1 && attemptsCounter>0) {
						varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInPredicate + this.run.averageVarsInPredicate);
						attemptsCounter--;
					}
					if(attemptsCounter==0)
						varsInAtom = 2;
				}
				/*if isGuarded, here the only variable is the join one to fulfill the guardedness requirement without changing left atom*/
				else
					varsInAtom = 1;
			}

			/*define each variable as argument*/
			for(int j = 1; j <= varsInAtom-1; j++) {
				argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
				this.run.updateHarmlessInstance();
			}
			/*add join variable*/
			argumentsRight.add(joinVariable);

			/*create the new atom with these arguments*/
			right = new Literal(name, argumentsRight);

			/*update the edb data structures, if needed*/
			if(!atomAlreadyGenerated) {
				this.run.inputLiterals.add(right);
				this.run.inputLiteralNamesNumArguments.put(name,varsInAtom);
			}
		}

		/*with left-right recursive joins it is required to have an idb as right atom*/
		else{	// leftRightRecJoin
			/*decide whether the join is with the same idb or a distinct one*/
			/*the type of join does not influence this choice*/
			String sameOrDifferentIdb = this.joinWithSameOrDifferentIdb();
			Integer harmfulVarsInAtom = 0;

			/*if same idb, use the left atom just created*/
			if(sameOrDifferentIdb.equals(joinSameIdb)){
				name = left.getAtom().getName();
				varsInAtom = left.getAtom().getArguments().size();
				/*get possible number of affected positions in the atom*/
				if(this.run.affectedAtomNames.contains(name))
					harmfulVarsInAtom = this.run.numAffectedPositionsPerAtom.get(name);
			}
			/*if different idb, define new idb*/
			else { //joinDifferentIdb
				/*define a new idb, to avoid additional recursive closure of defined previously ones*/
				name = "idb_" + this.run.idbInstance;
				this.run.updateIdbInstance();				
				/*obtain number of arguments for the atom*/
				Atom indLeftRightRecJoinBodyAtom = this.run.indLeftRightRecJoinBodyAtoms.get(recName);
				varsInAtom = indLeftRightRecJoinBodyAtom.getArguments().size();
				/*get possible number of affected positions in the body atom which will link the new one to the program*/
				if(this.run.affectedAtomNames.contains(indLeftRightRecJoinBodyAtom.getName()))
					harmfulVarsInAtom = this.run.numAffectedPositionsPerAtom.get(indLeftRightRecJoinBodyAtom.getName());
				/*with harmful-harmful join the new atom is required to have at least one harmful variable*/
				if(typeOfJoin.equals(ModelGenerator.joinHH) && harmfulVarsInAtom==null) {
					/*counter to attempt "while"*/
					int attemptsCounter = 3;
					while((harmfulVarsInAtom<1 || harmfulVarsInAtom>varsInAtom-1) && attemptsCounter>0) {
						harmfulVarsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceEVarsInRule + this.run.averageEVarsInRule);
						attemptsCounter--;
					}
					if(attemptsCounter==0)
						harmfulVarsInAtom = 1;
				}
				/*update the affected positions data structures*/
				if(harmfulVarsInAtom!=0) {
					this.run.affectedAtomNames.add(name);
					this.run.numAffectedPositionsPerAtom.put(name, harmfulVarsInAtom);
				}
				/*update sequence data structures*/
				this.run.idbLiteralNamesNumArguments.put(name, varsInAtom);				
			}

			/*the type of join influences the order of the variables*/
			if(//harmfulVarsInAtom!=0 && 
					(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))){
				/*add join variable in first position, as it is harmless by construction*/
				argumentsRight.add(joinVariable);
				/*define each harmless variable as argument*/
				for(int j = 1; j <= varsInAtom-1-harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
					this.run.updateHarmlessInstance();
				}
				/*define each harmful variable as argument*/
				for(int j = 1; j <= harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmfulVariable + this.run.harmfulInstance));
					this.run.updateHarmfulInstance();
				}
			}
			else{ //harmfulVarsInAtom==0 || joinHH
				/*define each harmless variable as argument*/
				for(int j = 1; j <= varsInAtom-harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
					this.run.updateHarmlessInstance();
				}
				/*define each harmful variable as argument*/
				for(int j = 1; j <= harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmfulVariable + this.run.harmfulInstance));
					this.run.updateHarmfulInstance();
				}
				/*add join variable in last position*/
				argumentsRight.remove(argumentsRight.size()-1);
				argumentsRight.add(joinVariable);
			}

			/*create the new atom with these arguments*/
			right = new Literal(name, argumentsRight);

			/*update data structure to link non-linear join to main program*/
			if(sameOrDifferentIdb.equals(joinSameIdb)){
				/*remove current rec from non linear ones because there is no need to add a new rule*/
				/*to link the right atom to the main body of the program*/
				this.run.indLeftRightRecJoinBodyAtoms.remove(recName);
			}
			else { //joinDifferentIdb
				this.run.indLeftRightRecJoinHeadAtoms.put(recName,right.getAtom());
			}
		}

		body.add(right);
		/*if it is a right recursion, invert the order of the atoms in the body*/
		if(typeOfRecJoin.equals(rightRecJoin))
			return IntStream.range(0, body.size())
					.map(i -> (body.size() - 1 - i))
					.mapToObj(body::get)
					.collect(Collectors.toCollection(ArrayList::new));
		else
			return body;	
	}



	/**
	 * It builds the body of a join rule involved in a direct recursive closure
	 * 
	 * @param typeOfJoin
	 * @param typeOfRecJoin
	 * @param recName
	 * @param isGuarded
	 * @return the built list of literals
	 */
	public List<Literal> createJoinBodyDirectRecursiveClosure(String typeOfJoin, String typeOfRecJoin, String recName, boolean isGuarded) {
		Random r = new Random();
		List<Literal> body = new ArrayList<>();

		String name;
		List<Term> argumentsLeft = new ArrayList<>();

		/*create LEFT body atom - last idb in head which completes recursive cycle*/
		List<String> idbNames = new ArrayList<>(this.run.idbLiteralNamesNumArguments.keySet());
		int indexInput = idbNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, idbNames.size());
		name = idbNames.get(indexInput);

		/*the number of arguments is the one already defined for this idb*/
		Integer varsInAtom = this.run.idbLiteralNamesNumArguments.get(name);	
		Integer harmfulVarsInAtom = 0;
		/*get possible number of affected positions in the atom*/
		if(this.run.affectedAtomNames.contains(name))
			harmfulVarsInAtom = this.run.numAffectedPositionsPerAtom.get(name);
		/*define each harmless variable as argument*/
		for(int j = 1; j <= varsInAtom-harmfulVarsInAtom; j++) {
			argumentsLeft.add(new Variable(harmlessVariable + this.run.harmlessInstance));
			this.run.updateHarmlessInstance();
		}
		/*define each harmful variable as argument*/
		for(int j = 1; j <= harmfulVarsInAtom; j++) {
			argumentsLeft.add(new Variable(harmfulVariable + this.run.harmfulInstance));
			this.run.updateHarmfulInstance();
		}

		Literal left = new Literal(name, argumentsLeft);
		body.add(left);

		/*update current direct recursive literal*/
		this.run.literalDirectRec = left;

		/*decide which variable is the join variable*/
		/*the type of join influences this choice*/
		Variable joinVariable = null;
		/*harmless joins require a harmless join variable*/
		/*by construction, the first variable is always harmless*/
		if(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
			joinVariable = (Variable) argumentsLeft.get(0);
		else
			/*harmful joins require a harmful join variable*/
			/*by construction, if the atom is affected, the last variable is always harmful*/
			if(typeOfJoin.equals(ModelGenerator.joinhH) || typeOfJoin.equals(ModelGenerator.joinHH))
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-1);	

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		/*create RIGHT body atom - edb or idb*/
		Literal right;
		List<Term> argumentsRight = new ArrayList<>();

		/*decide whether the join is with edb or idb and build the atom*/
		/*the type of recursion influences this choice*/
		varsInAtom = 0;
		/*with left or right recursive joins it is required to have an edb as right atom, to avoid additional recursive closures*/
		if(typeOfRecJoin.equals(leftRecJoin) || typeOfRecJoin.equals(rightRecJoin)){			
			indexInput = this.run.inputAtomNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, this.run.inputAtomNames.size());
			name = this.run.inputAtomNames.get(indexInput);

			/*check if the atom has already been defined previously*/
			boolean atomAlreadyGenerated = this.run.inputLiteralNamesNumArguments.containsKey(name);
			if(atomAlreadyGenerated) {
				varsInAtom = this.run.inputLiteralNamesNumArguments.get(name);
				/*if isGuarded, the atom has to be with a single argument or new*/
				/*to avoid variables different from the ones in the left atom and respect guardedness*/
				if(isGuarded && varsInAtom!=1) {
					name = this.run.inputAtomNameForGuarded;
					varsInAtom = this.run.inputLiteralNamesNumArguments.get(name);
				}
			}
			else {
				/*obtain number of arguments for the atom, based on input average and variance values*/
				if(!isGuarded) {
					varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInPredicate+this.run.averageVarsInPredicate);
					/*counter to attempt "while"*/
					int attemptsCounter = 3;
					while(varsInAtom<1 && attemptsCounter>0) {
						varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInPredicate+this.run.averageVarsInPredicate);
						attemptsCounter--;
					}
					if(attemptsCounter==0)
						varsInAtom = 2;
				}
				/*if isGuarded, here the only variable is the join one to fulfill the guardedness requirement without changing left atom*/
				else
					varsInAtom = 1;
			}

			/*define each variable as argument*/
			for(int j = 1; j <= varsInAtom-1; j++) {
				argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
				this.run.updateHarmlessInstance();
			}
			/*add join variable*/
			argumentsRight.add(joinVariable);

			/*create the new atom with these arguments*/
			right = new Literal(name, argumentsRight);

			/*update the edb data structures, if needed*/
			if(!atomAlreadyGenerated) {
				this.run.inputLiterals.add(right);
				this.run.inputLiteralNamesNumArguments.put(name,varsInAtom);
			}
		}

		/*with left-right recursive joins it is required to have an idb as right atom*/
		else{	// leftRightRecJoin
			/*here the join must be with the same idb*/
			name = left.getAtom().getName();
			varsInAtom = left.getAtom().getArguments().size();
			/*get possible number of affected positions in the atom*/
			if(this.run.affectedAtomNames.contains(name))
				harmfulVarsInAtom = this.run.numAffectedPositionsPerAtom.get(name);

			/*the type of join influences the order of the variables*/
			if(//harmfulVarsInAtom!=0 && 
					(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))){
				/*add join variable in first position, as it is harmless by construction*/
				argumentsRight.add(joinVariable);
				/*define each harmless variable as argument*/
				for(int j = 1; j <= varsInAtom-1-harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
					this.run.updateHarmlessInstance();
				}
				/*define each harmful variable as argument*/
				for(int j = 1; j <= harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmfulVariable + this.run.harmfulInstance));
					this.run.updateHarmfulInstance();
				}
			}
			else{ //harmfulVarsInAtom==0 || joinHH
				/*define each harmless variable as argument*/
				for(int j = 1; j <= varsInAtom-harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
					this.run.updateHarmlessInstance();
				}
				/*define each harmful variable as argument*/
				for(int j = 1; j <= harmfulVarsInAtom; j++) {
					argumentsRight.add(new Variable(harmfulVariable + this.run.harmfulInstance));
					this.run.updateHarmfulInstance();
				}
				/*add join variable in last position*/
				argumentsRight.remove(argumentsRight.size()-1);
				argumentsRight.add(joinVariable);
			}

			/*create the new atom with these arguments*/
			right = new Literal(name, argumentsRight);
		}

		body.add(right);
		/*if it is a right recursion, invert the order of the atoms in the body*/
		if(typeOfRecJoin.equals(rightRecJoin))
			return IntStream.range(0, body.size())
					.map(i -> (body.size() - 1 - i))
					.mapToObj(body::get)
					.collect(Collectors.toCollection(ArrayList::new));
		else
			return body;	
	}



	/**
	 * It builds the body of a join rule involved in a tertiary branch
	 * 
	 * @param firstExistentialHead
	 * @param typeOfJoin
	 * @param isGuarded
	 * @return the built list of literals
	 */
	public List<Literal> createJoinBodyTertiaryRule(Atom firstHead, String typeOfJoin, boolean isGuarded) {
		return this.createJoinBodyChaseSteps(firstHead, typeOfJoin, isGuarded);
	}

}
