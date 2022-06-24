package org.vadalog.iwarded.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.vadalog.iwarded.model.Atom;
import org.vadalog.iwarded.model.ComparisonOperatorsEnum;
import org.vadalog.iwarded.model.Condition;
import org.vadalog.iwarded.model.Constant;
import org.vadalog.iwarded.model.Rule;
import org.vadalog.iwarded.model.Variable;



/**
 * This class handles the generation of selection conditions in iWarded 
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
public class ConditionGenerator {

	ModelGenerator run;



	/**
	 * Constructor for ConditionsGenerator
	 */
	public ConditionGenerator(){
		this.run = ModelGenerator.getModelInstance();
	}



	/**
	 * It defines randomly the type of comparison operator
	 * for the current selection condition
	 * 
	 * @return selected comparison operator
	 */
	private ComparisonOperatorsEnum defineComparisonOperator() {
		int comparisonOperator = ThreadLocalRandom.current().nextInt(0, 4 + 1);
		switch (comparisonOperator) {
		case 1:
			return ComparisonOperatorsEnum.GT;
		case 2:
			return ComparisonOperatorsEnum.EQ;
		case 3:
			return ComparisonOperatorsEnum.LE;
		case 4:
			return ComparisonOperatorsEnum.GE;
		}
		return ComparisonOperatorsEnum.LT;

	}



	/**
	 * It adds condition to a Rule, specifically to first variable of first atom of body
	 * 
	 * @param Rule r
	 * @return the rule with the added conditions
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Rule addConditions(Rule rule){
		Random r = new Random();
		List<Condition> conditions = new ArrayList<>();

		Atom atom = rule.getBody().get(0).getAtom();
		Variable variable = (Variable) atom.getArguments().get(0);

		Constant compar = new Constant(r.nextInt(99));	//condition has to be lower than 99 (by design choice)
		Integer value = Integer.valueOf((int) compar.getValue());
		while(value<=0){
			compar = new Constant(r.nextInt(99));
			value = Integer.valueOf((int) compar.getValue());
		}

		ComparisonOperatorsEnum compOperator = this.defineComparisonOperator();
		Condition condition = new Condition(variable, compOperator, compar);
		conditions.add(condition);

		rule = new Rule(rule.getHead(),rule.getBody(),conditions);
		return rule;
	}




}
