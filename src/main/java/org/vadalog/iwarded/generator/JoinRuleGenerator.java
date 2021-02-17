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
public class JoinRuleGenerator extends RuleGenerator{

	private final static String joinWithEdb = "joinWithEdb";
	private final static String joinWithIdb = "joinWithIdb";

	private final static String nonLinRecJoinSameIdb = "sameIdb";
	private final static String nonLinRecJoinDifferentIdb = "differentIdb";


	/**
	 * Constructor for JoinRule
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
	 * It randomly chooses whether the current non-linear join will be 
	 * between the same idb atoms or two distinct ones	 
	 *  
	 * @return whether the non-linear join is with the same idb or a different one
	 */
	private String nonLinearJoinWithSameOrDifferentIdb() {
		int sameOrDifferentIdb = ThreadLocalRandom.current().nextInt(0, 1 + 1);
		if(sameOrDifferentIdb == 0)
			return nonLinRecJoinSameIdb;
		else
			return nonLinRecJoinDifferentIdb;
	}



	/**
	 * It builds the body for a join rule involved in the input-output sequence
	 * 
	 * @param lastAtomInHead
	 * @param typeOfJoin
	 * @param isGuarded
	 * @return the built list of literals
	 */
	public List<Literal> createJoinBodyChaseSteps(Atom lastAtomInHead, String typeOfJoin, boolean isGuarded){
		Random r = new Random();
		List<Literal> body = new ArrayList<>();

		/*create LEFT body atom - last IDB in head*/
		Literal left = new Literal(lastAtomInHead,true);
		body.add(left);
		List<Term> argumentsLeft = lastAtomInHead.getArguments();

		Integer harmfulVarsInAtom = 0;
		if(this.run.affectedAtomNames.contains(lastAtomInHead.getName()))
			harmfulVarsInAtom = this.run.numAffectedPositionsPerAtom.get(lastAtomInHead.getName());

		/*define which variable is the join variable*/
		Variable joinVariable = null;	
		/*the type of join influences this choice*/
		/*harmless joins require harmless join variables*/
		if(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
			if(run.affectedAtomNames.contains(lastAtomInHead.getName()))
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-harmfulVarsInAtom);
			else
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-1);
		else
			/*harmful joins require harmful join variables*/
			if(typeOfJoin.equals(ModelGenerator.joinhH) || typeOfJoin.equals(ModelGenerator.joinHH))
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-1);


		/*create RIGHT body atom - EDB or IDB*/
		String edbOrIdb = null;
		/*the type of join  and the possible Guardedness requirement influence this choice*/
		/*with harmless joins the choice is random*/
		if((typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW)) && !isGuarded)
			edbOrIdb = this.joinWithEdbOrIdb();
		else
			/*with harmless-harmful joins it is required to have an edb as right atom*/
			if(typeOfJoin.equals(ModelGenerator.joinhH) || isGuarded)
				edbOrIdb = JoinRuleGenerator.joinWithEdb;
			else
				/*with harmful-harmful joins it is required to have an idb as right atom*/
				if(typeOfJoin.equals(ModelGenerator.joinHH))
					edbOrIdb = JoinRuleGenerator.joinWithIdb;

		String name;
		Integer varsInAtom = 0;
		List<Term> argumentsRight = new ArrayList<>();
		Literal right;


		/*build the atom based on the previous choice*/
		if(edbOrIdb.equals(JoinRuleGenerator.joinWithEdb)){
			Integer indexInput = this.run.inputAtomNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, this.run.inputAtomNames.size());
			name = this.run.inputAtomNames.get(indexInput);

			boolean alreadyUsedAtomName = this.run.inputLiteralNamesArguments.containsKey(name);

			/*obtain number of arguments for the atom*/
			if(alreadyUsedAtomName) {
				varsInAtom = this.run.inputLiteralNamesArguments.get(name);
				/*if isGuarded, the atom has to be with a single argument or new*/
				/*to avoid variables different from the ones in the left atom and respect guardedness*/
				if(isGuarded && varsInAtom!=1) {
					name = this.run.inputAtomNameForGuarded;
					varsInAtom = this.run.inputLiteralNamesArguments.get(name);
				}
			}
			else { //!alreadyUsedAtomName
				/*obtain number of arguments for the atom, based on input average and variance values*/
				if(!isGuarded) {
					varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInAtom+this.run.averageVarsInAtom);
					while(varsInAtom<1)
						varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInAtom+this.run.averageVarsInAtom);
				}
				/*if isGuarded, here the only variable is the join one to fulfill the guardedness requirement without changing left atom*/
				else
					varsInAtom = 1;
			}

			/*create each variable*/
			for(int j = 1; j <= varsInAtom-1; j++) {
				Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
				this.run.harmlessInstance ++;
				argumentsRight.add(v);
			}
			/*add join variable*/
			argumentsRight.add(joinVariable);

			/*create the new atom with these arguments*/
			right = new Literal(name, argumentsRight);

			/*update the edb data structures, if needed*/
			if(!alreadyUsedAtomName) {
				this.run.inputLiterals.add(right);
				this.run.inputLiteralNamesArguments.put(name,varsInAtom);
			}
		}


		else{	 // joinWithIdb
			List<String> idbNames = new ArrayList<>(this.run.idbNumVariables.keySet());
			Integer indexInput = idbNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, idbNames.size());
			name = idbNames.get(indexInput);
			/*get number of arguments from the previous creation of the atom*/
			varsInAtom = this.run.idbNumVariables.get(name);
			harmfulVarsInAtom = 0;
			if(this.run.affectedAtomNames.contains(name))
				harmfulVarsInAtom = this.run.numAffectedPositionsPerAtom.get(name);

			/*the type of join influences the creation of arguments*/
			if((typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
					&& this.run.affectedAtomNames.contains(name)){
				/*create each harmless variable*/
				for(int j = 1; j <= varsInAtom-(harmfulVarsInAtom+1); j++) {
					Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
					this.run.harmlessInstance ++;
					argumentsRight.add(v);
				}
				/*add join variable*/
				argumentsRight.add(joinVariable);
				/*create each harmful variable*/
				for(int j = 1; j <= harmfulVarsInAtom; j++) {
					Variable v = new Variable(harmfulVariable + this.run.harmfulInstance);
					this.run.harmfulInstance ++;
					argumentsRight.add(v);
				}
			}

			else{
				/*create each variable*/
				for(int j = 1; j <= varsInAtom-1; j++) {
					Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
					this.run.harmlessInstance ++;
					argumentsRight.add(v);
				}
				/*add join variable*/
				argumentsRight.add(joinVariable);
			}

			/*create the new atom with these arguments*/
			right = new Literal(name, argumentsRight);
		}

		body.add(right);

		return body;	
	}



	/**
	 * It builds the body for a join rule involved in a recursive cycle
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
	 * It builds the body for a join rule involved in an indirect recursive closure
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

		/*create LEFT body atom - last IDB in head which completes recursive cycle*/
		Literal left = new Literal(rLast,true);
		body.add(left);
		List<Term> argumentsLeft = rLast.getArguments();

		/*define which variable is the join variable*/
		Variable joinVariable = null;	
		/*the type of join influences this choice*/
		/*harmless joins require harmless join variables*/
		if(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
			if(run.affectedAtomNames.contains(rLast.getName()))
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-2);
			else
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-1);
		else
			/*harmful joins require harmful join variables*/
			if(typeOfJoin.equals(ModelGenerator.joinhH) || typeOfJoin.equals(ModelGenerator.joinHH))
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-1);


		/*create RIGHT body atom - EDB or IDB*/
		Literal right;
		String name;
		Integer varsInAtom = 0;
		List<Term> argumentsRight = new ArrayList<>();

		/*the type of join influences this choice*/
		if(typeOfRecJoin.equals(leftRecJoin) || typeOfRecJoin.equals(rightRecJoin)){
			Integer indexInput = this.run.inputAtomNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, this.run.inputAtomNames.size());
			name = this.run.inputAtomNames.get(indexInput);

			boolean alreadyUsedAtomName = this.run.inputLiteralNamesArguments.containsKey(name);

			/*obtain number of arguments for the atom*/
			if(alreadyUsedAtomName) {
				varsInAtom = this.run.inputLiteralNamesArguments.get(name);
				/*if isGuarded, the atom has to be with a single argument or new*/
				/*to avoid variables different from the ones in the left atom and respect guardedness*/
				if(isGuarded && varsInAtom!=1) {
					name = this.run.inputAtomNameForGuarded;
					varsInAtom = this.run.inputLiteralNamesArguments.get(name);
				}
			}
			else { //!alreadyUsedAtomName
				/*obtain number of arguments for the atom, based on input average and variance values*/
				if(!isGuarded) {
					varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInAtom+this.run.averageVarsInAtom);
					while(varsInAtom<1)
						varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInAtom+this.run.averageVarsInAtom);
				}
				/*if isGuarded, here the only variable is the join one to fulfill the guardedness requirement without changing left atom*/
				else
					varsInAtom = 1;
			}

			/*create each variable*/
			for(int j = 1; j <= varsInAtom-1; j++) {
				Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
				this.run.harmlessInstance ++;
				argumentsRight.add(v);
			}
			/*add join variable*/
			argumentsRight.add(joinVariable);

			/*create the new atom with these arguments*/
			right = new Literal(name, argumentsRight);

			/*update the edb data structures, if needed*/
			if(!alreadyUsedAtomName) {
				this.run.inputLiterals.add(right);
				this.run.inputLiteralNamesArguments.put(name,varsInAtom);
			}

			body.add(right);
		}
		else{	// nonLinearRecJoin
			String sameOrDifferentIdb = this.nonLinearJoinWithSameOrDifferentIdb();
			if(sameOrDifferentIdb.equals(nonLinRecJoinSameIdb)){
				name = left.getAtom().getName();
				varsInAtom = left.getAtom().getArguments().size();

				/*harmless joins require harmless join variables*/
				if((typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
						&& this.run.affectedAtomNames.contains(name)){
					/*create each harmless variable*/
					for(int j = 1; j <= varsInAtom-2; j++) {
						Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
						this.run.harmlessInstance ++;
						argumentsRight.add(v);
					}
					/*add join variable*/
					argumentsRight.add(joinVariable);
					/*add remaining harmless variables*/
					argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
					this.run.harmlessInstance ++;
				}

				else{
					/*harmful joins require harmful join variables*/
					for(int j = 1; j <= varsInAtom-1; j++) {
						Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
						this.run.harmlessInstance ++;
						argumentsRight.add(v);
					}
					/*add join variable*/
					argumentsRight.add(joinVariable);
				}

				/*create the new atom with these arguments*/
				right = new Literal(name, argumentsRight);

				/*update data structure to link non-linear join to main program*/
				this.run.bodyRuleForIndNonLinRecJoinRightAtom.remove(recName);
			}

			else{	// nonLinRecJoinDifferentIdb
				name = "idb_" + this.run.idbInstance;
				this.run.updateIdbInstance();;

				/*obtain number of arguments for the atom*/
				varsInAtom = this.run.bodyRuleForIndNonLinRecJoinRightAtom.get(recName).getArguments().size();			

				/*harmless joins require harmless join variables*/
				if((typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
						&& this.run.affectedAtomNames.contains(name)){
					/*create each variable*/
					for(int j = 1; j <= varsInAtom-2; j++) {
						Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
						this.run.harmlessInstance ++;
						argumentsRight.add(v);
					}
					/*add join variable*/
					argumentsRight.add(joinVariable);
					/*add remaining harmless variables*/
					argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
					this.run.harmlessInstance ++;
				}
				else{
					/*harmful joins require harmful join variables*/
					for(int j = 1; j <= varsInAtom-1; j++) {
						Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
						this.run.harmlessInstance ++;
						argumentsRight.add(v);
					}
					/*add join variable*/
					argumentsRight.add(joinVariable);
				}

				/*create the new atom with these arguments*/
				right = new Literal(name, argumentsRight);

				/*update data structure to link non-linear join to main program*/
				this.run.recNonLinIndJoinRightAtom.put(recName,right.getAtom());

			}
			body.add(right);
		}

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
	 * It builds the body for a join rule involved in a direct recursive closure
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
		Integer varsInAtom = 0;

		/*create LEFT body atom - last IDB in head which completes recursive cycle*/
		List<String> idbNames = new ArrayList<>(this.run.idbNumVariables.keySet());
		Integer indexInput = idbNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, idbNames.size());
		name = idbNames.get(indexInput);

		/*get arguments from previous creation of the atom*/
		List<Term> argumentsLeft = new ArrayList<>();
		varsInAtom = this.run.idbNumVariables.get(name);
		/*create each variable*/
		for(int j = 1; j <= varsInAtom; j++) {
			Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
			this.run.harmlessInstance ++;
			argumentsLeft.add(v);
		}

		Literal left = new Literal(name, argumentsLeft);
		body.add(left);

		/*update current direct recursive literal*/
		this.run.literalDirectRec = left;

		/*define which variable is the join variable*/
		Variable joinVariable = null;	
		/*the type of join influences this choice*/
		/*harmless joins require harmless join variables*/
		if(typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
			if(run.affectedAtomNames.contains(name))
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-2);
			else
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-1);
		else
			/*harmful joins require harmful join variables*/
			if(typeOfJoin.equals(ModelGenerator.joinhH) || typeOfJoin.equals(ModelGenerator.joinHH))
				joinVariable = (Variable) argumentsLeft.get(argumentsLeft.size()-1);


		/*create RIGHT body atom - EDB or IDB*/
		Literal right;
		List<Term> argumentsRight = new ArrayList<>();

		/*the type of join influences this choice*/
		if(typeOfRecJoin.equals(leftRecJoin) || typeOfRecJoin.equals(rightRecJoin)){			
			indexInput = this.run.inputAtomNames.size() == 1 ? 0 : ThreadLocalRandom.current().nextInt(0, this.run.inputAtomNames.size());
			name = this.run.inputAtomNames.get(indexInput);

			boolean alreadyUsedAtomName = this.run.inputLiteralNamesArguments.containsKey(name);

			/*obtain number of arguments for the atom*/
			if(alreadyUsedAtomName) {
				varsInAtom = this.run.inputLiteralNamesArguments.get(name);
				/*if isGuarded, the atom has to be with a single argument or new*/
				/*to avoid variables different from the ones in the left atom and respect guardedness*/
				if(isGuarded && varsInAtom!=1) {
					name = this.run.inputAtomNameForGuarded;
					varsInAtom = this.run.inputLiteralNamesArguments.get(name);
				}
			}
			else { //!alreadyUsedAtomName
				/*obtain number of arguments for the atom, based on input average and variance values*/
				if(!isGuarded) {
					varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInAtom+this.run.averageVarsInAtom);
					while(varsInAtom<1)
						varsInAtom = (int) Math.round(r.nextGaussian()*this.run.varianceVarsInAtom+this.run.averageVarsInAtom);
				}
				/*if isGuarded, here the only variable is the join one to fulfill the guardedness requirement without changing left atom*/
				else
					varsInAtom = 1;
			}

			/*create each variable*/
			for(int j = 1; j <= varsInAtom-1; j++) {
				Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
				this.run.harmlessInstance ++;
				argumentsRight.add(v);
			}
			/*add join variable*/
			argumentsRight.add(joinVariable);

			/*create the new atom with these arguments*/
			right = new Literal(name, argumentsRight);

			/*update the edb data structures, if needed*/
			if(!alreadyUsedAtomName) {
				this.run.inputLiterals.add(right);
				this.run.inputLiteralNamesArguments.put(name,varsInAtom);
			}
		}

		else{	// nonLinearRecJoin
			name = left.getAtom().getName();
			varsInAtom = left.getAtom().getArguments().size();

			/*the type of join influences this choice*/
			/*harmless joins require harmless join variables*/
			if((typeOfJoin.equals(ModelGenerator.joinhhW) || typeOfJoin.equals(ModelGenerator.joinhhNW))
					&& this.run.affectedAtomNames.contains(name)){
				/*create each harmful variable*/
				for(int j = 1; j <= varsInAtom-2; j++) {
					Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
					this.run.harmlessInstance ++;
					argumentsRight.add(v);
				}
				/*add join variable*/
				argumentsRight.add(joinVariable);
				/*add remaining harmless variable*/
				argumentsRight.add(new Variable(harmlessVariable + this.run.harmlessInstance));
				this.run.harmlessInstance ++;
			}
			else{
				/*create each variable*/
				for(int j = 1; j <= varsInAtom-1; j++) {
					Variable v = new Variable(harmlessVariable + this.run.harmlessInstance);
					this.run.harmlessInstance ++;
					argumentsRight.add(v);
				}
				/*add join variable*/
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
	 * It builds the body for a join rule involved in a tertiary branch
	 * 
	 * @param firstExistentialHead
	 * @param typeOfJoin
	 * @param isGuarded
	 * @return the built list of literals
	 */
	public List<Literal> createJoinBodyTertiaryRule(Atom firstExistentialHead, String typeOfJoin, boolean isGuarded) {
		return this.createJoinBodyChaseSteps(firstExistentialHead, typeOfJoin, isGuarded);
	}


}
