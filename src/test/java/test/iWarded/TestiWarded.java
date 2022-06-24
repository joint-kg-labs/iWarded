package test.iWarded;

import org.junit.Test;
import org.vadalog.iwarded.IWardedGenerator;



/**
 * iWarded Test Class
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
public class TestiWarded {



	/*----------------------------------- BASIC TESTS iWARDED -----------------------------------*/

	@Test
	public void testIWardedGenerator_WithoutRecursions_WithoutExistentials_WithoutJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "0", "0", "0", "100", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithoutRecursions_WithoutExistentials_WithHarmlessJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "0", "0", "0", "100", "10", "10",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	/*the program is with harmless joins because it is not possible to have harmful joins without existentials*/
	public void testIWardedGenerator_WithoutRecursions_WithoutExistentials_WithHarmfulJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "0", "0", "0", "100", "0", "0",
				"10", "10", "10", "10", "0", "0", "0", "0", "0", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	/*the program is with harmless joins because it is not possible to have harmful joins without existentials*/
	public void testIWardedGenerator_WithoutRecursions_WithoutExistentials_WithAllTypesOfJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "0", "0", "0", "100", "10", "10",
				"10", "10", "10", "10", "0", "0", "0", "0", "0", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithoutRecursions_WithExistentials_WithoutJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithoutRecursions_WithExistentials_WithHarmlessJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithoutRecursions_WithExistentials_WithHarmfulJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "0", "0",
				"10", "10", "10", "10", "0", "0", "0", "0", "0", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithoutRecursions_WithExistentials_WithAllTypesOfJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "0", "0", "0", "0", "0", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);

	}

	@Test
	public void testIWardedGenerator_WithLinearRecursions_WithoutExistentials_WithoutJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "0", "0",
				"0", "0", "10", "10", "3", "0", "0", "0", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithLinearRecursions_WithoutExistentials_WithHarmlessJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "10", "10",
				"0", "0", "10", "10", "3", "0", "0", "0", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	/*the program is with harmless joins because it is not possible to have harmful joins without existentials*/
	public void testIWardedGenerator_WithLinearRecursions_WithoutExistentials_WithHarmfulJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "0", "0",
				"10", "10", "10", "10", "3", "0", "0", "0", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithLinearRecursions_WithoutExistentials_WithAllTypesOfJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "10", "10",
				"10", "10", "10", "10", "3", "0", "0", "0", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithLinearRecursions_WithExistentials_WithoutJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "0", "0",
				"0", "0", "10", "10", "3", "0", "0", "0", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithLinearRecursions_WithExistentials_WithHarmlessJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"0", "0", "10", "10", "3", "0", "0", "0", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithLinearRecursions_WithExistentials_WithHarmfulJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "0", "0",
				"10", "10", "10", "10", "3", "0", "0", "0", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithLinearRecursions_WithExistentials_WithAllTypesOfJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "3", "0", "0", "0", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	/*the program is with harmless joins because it is not possible to have harmful joins without existentials*/
	public void testIWardedGenerator_WithJoinRecursions_WithoutExistentials_WithoutJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "0", "0",
				"0", "0", "10", "10", "0", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithJoinRecursions_WithoutExistentials_WithHarmlessJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "10", "10",
				"0", "0", "10", "10", "0", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	/*the program is with harmless joins because it is not possible to have harmful joins without existentials*/
	public void testIWardedGenerator_WithJoinRecursions_WithoutExistentials_WithHarmfulJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "0", "0",
				"10", "10", "10", "10", "0", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithJoinRecursions_WithoutExistentials_WithAllTypesOfJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "10", "10",
				"10", "10", "10", "10", "0", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithJoinRecursions_WithExistentials_WithoutJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "0", "0",
				"0", "0", "10", "10", "0", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithJoinRecursions_WithExistentials_WithHarmlessJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"0", "0", "10", "10", "0", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithJoinRecursions_WithExistentials_WithHarmfulJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "0", "0",
				"10", "10", "10", "10", "0", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithJoinRecursions_WithExistentials_WithAllTypesOfJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "0", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	/*the program is with join rules because it is not possible to have recursive joins without joins*/
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithoutExistentials_WithoutJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "0", "0",
				"0", "0", "10", "10", "3", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithoutExistentials_WithHarmlessJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "10", "10",
				"0", "0", "10", "10", "3", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	/*the program is with harmless joins because it is not possible to have harmful joins without existentials*/
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithoutExistentials_WithHarmfulJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "0", "0",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithoutExistentials_WithAllTypesOfJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "0", "1", "1.0", "0", "100", "10", "10",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	/*the program is with join rules because it is not possible to have recursive joins without joins*/
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithExistentials_WithoutJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "0", "0",
				"0", "0", "10", "10", "3", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithExistentials_WithHarmlessJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"0", "0", "10", "10", "3", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithExistentials_WithHarmfulJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "1", "1.0", "100", "100", "0", "0",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithExistentials_WithAllTypesOfJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "1", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "1", "1", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}





	/*----------------------------------- SPECIFIC TESTS iWARDED -----------------------------------*/


	@Test
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithMultipleExistentialsPerRule_WithAllTypesOfJoins_WithoutConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "3", "1.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "0", "0", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithAllTypesOfRecursions_WithMultipleExistentialsPerRule_WithAllTypesOfJoins_WithConditions() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "3", "3.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "10", "10", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithSingleInputAtom() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"1", "10", "1", "1.0", "100", "3", "3.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "10", "10", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithNotEnoughRules() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "1", "3", "3.0", "1", "1", "1", "1",
				"1", "1", "10", "10", "3", "3", "3", "3", "3", "10", "10", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_WithMoreEvarsThanVars() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "100", "10", "3.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "10", "10", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}
	
	@Test
	public void testIWardedGenerator_StressTestProgram1() {
		
		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"34", "56", "34", "23", "4", "27", "6", "3", "2", "4", "5",
				"7", "6", "10", "10", "3", "2", "2", "4", "5", "6", "7", "5", "4", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}
	
	@Test
	public void testIWardedGenerator_StressTestProgram2() {
		
		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"34", "5", "3", "554", "34", "45", "3", "45", "6", "3", "2",
				"4", "5", "10", "10", "44", "5", "55", "3", "4", "5", "10", "10", "34", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}
	
	@Test
	public void testIWardedGenerator_StressTestProgram3() {
		
		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"1", "1", "25", "55", "100", "35", "75", "100", "1", "1", "1",
				"1", "100", "10", "10", "1", "1", "1", "100", "100", "0", "0", "0", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}
	
	@Test
	public void testIWardedGenerator_GuardedProgram() {
		
		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"1", "1", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "1", "1", "10", "true", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}
	
	@Test
	public void testIWardedGenerator_ShyProgram() {
		
		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"1", "1", "1", "1.0", "100", "1", "1.0", "100", "100", "10", "10",
				"10", "10", "10", "10", "3", "3", "3", "3", "3", "1", "1", "10", "false", "true", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_Selectivity1() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "3", "1", "1.0", "3", "1", "1.0", "3", "6", "3", "0",
				"0", "0", "10", "10", "1", "1", "0", "0", "2", "3", "1", "100", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}
	
	@Test
	public void testIWardedGenerator_Selectivity2() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "10", "1", "1.0", "10", "100", "10", "10",
				"10", "10", "10", "10", "0", "3", "3", "3", "3", "0", "2", "100", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}


	/*----------------------------------- PAPER TESTS IWARDED -----------------------------------*/
	
	@Test
	public void testIWardedGenerator_SmallProgram() {
		
		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"1", "1", "1", "1.0", "0", "0", "0", "0", "5", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "0", "3", "100", "true", "false", "smallProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_TutorialProgram() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "3", "1", "1.0", "3", "1", "1.0", "3", "6", "3", "0",
				"0", "0", "10", "10", "1", "1", "0", "0", "2", "3", "3", "100", "false", "false", "tutorialProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}
	
	@Test
	public void testIWardedGenerator_ComplexProgram() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"100", "10", "1", "1.0", "10", "1", "1.0", "10", "100", "10", "10",
				"10", "10", "10", "10", "0", "3", "3", "3", "3", "3", "3", "100", "false", "false", "complexProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}
	
	@Test
	public void testIWardedGenerator_ForiWardedPaper() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "3", "1", "1.0", "3", "1", "1.0", "3", "20", "3", "3",
				"0", "0", "10", "10", "3", "3", "0", "0", "3", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_SynthScenarioA() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"10", "1", "1", "1.0", "20", "1", "1.0", "10", "90", "4", "1",
				"5", "0", "10", "10", "27", "3", "0", "0", "1", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_SynthScenarioB() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"10", "1", "1", "1.0", "20", "1", "1.0", "10", "10", "40", "5",
				"45", "0", "10", "10", "3", "27", "0", "0", "1", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_SynthScenarioC() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"10", "1", "1", "1.0", "40", "1", "1.0", "10", "30", "20", "5",
				"25", "20", "10", "10", "9", "20", "0", "0", "1", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_SynthScenarioD() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"10", "1", "1", "1.0", "22", "1", "1.0", "10", "30", "9", "1",
				"10", "50", "10", "10", "9", "20", "0", "0", "1", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_SynthScenarioE() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"10", "1", "1", "1.0", "20", "1", "1.0", "10", "30", "29", "1",
				"35", "5", "10", "10", "15", "40", "0", "0", "1", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_SynthScenarioF() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"10", "10", "1", "1.0", "50", "1", "1.0", "10", "30", "29", "1",
				"35", "5", "10", "10", "25", "20", "0", "0", "1", "3", "3", "100", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_SynthScenarioG() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"10", "1", "1", "1.0", "30", "1", "1.0", "10", "30", "10", "60",
				"0", "0", "10", "10", "9", "21", "0", "0", "1", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_SynthScenarioH() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"10", "1", "1", "1.0", "30", "1", "1.0", "10", "30", "60", "10",
				"0", "0", "10", "10", "9", "21", "0", "0", "1", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingLinearRules_10() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "3", "1", "1.0", "0", "1", "1.0", "0", "10", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingLinearRules_50() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "3", "1", "1.0", "0", "1", "1.0", "0", "50", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingLinearRules_100() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "3", "1", "1.0", "0", "1", "1.0", "0", "100", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingLinearRules_250() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "3", "1", "1.0", "0", "1", "1.0", "0", "250", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingLinearRules_500() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "3", "1", "1.0", "0", "1", "1.0", "0", "500", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingLinearRules_1000() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "3", "1", "1.0", "0", "1", "1.0", "0", "1000", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumChase_10() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "10", "1", "1.0", "0", "1", "1.0", "0", "0", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumChase_50() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "50", "1", "1.0", "0", "1", "1.0", "0", "0", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumChase_100() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "100", "1", "1.0", "0", "1", "1.0", "0", "0", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumChase_250() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "250", "1", "1.0", "0", "1", "1.0", "0", "0", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumChase_500() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "500", "1", "1.0", "0", "1", "1.0", "0", "0", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumChase_1000() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1000", "1", "1.0", "0", "1", "1.0", "0", "0", "0", "0",
				"0", "0", "10", "10", "0", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLinRec_10() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "10", "0", "0",
				"0", "0", "10", "10", "10", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLinRec_50() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "50", "0", "0",
				"0", "0", "10", "10", "50", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLinRec_100() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "100", "0", "0",
				"0", "0", "10", "10", "100", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLinRec_250() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "250", "0", "0",
				"0", "0", "10", "10", "250", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLinRec_500() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "500", "0", "0",
				"0", "0", "10", "10", "500", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLinRec_1000() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "1000", "0", "0",
				"0", "0", "10", "10", "1000", "0", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLeftRightRec_10() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "10", "0",
				"0", "0", "10", "10", "0", "10", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLeftRightRec_50() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "50", "0",
				"0", "0", "10", "10", "0", "50", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLeftRightRec_100() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "100", "0",
				"0", "0", "10", "10", "0", "100", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLeftRightRec_250() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "250", "0",
				"0", "0", "10", "10", "0", "250", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLeftRightRec_500() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "500", "0",
				"0", "0", "10", "10", "0", "500", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingNumLeftRightRec_1000() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "1000", "0",
				"0", "0", "10", "10", "0", "1000", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingRecLength_10() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "10", "0",
				"0", "0", "10", "10", "0", "1", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingRecLength_50() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "50", "0",
				"0", "0", "10", "10", "0", "1", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingRecLength_100() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "100", "0",
				"0", "0", "10", "10", "0", "1", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingRecLength_250() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "250", "0",
				"0", "0", "10", "10", "0", "1", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingRecLength_500() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "500", "0",
				"0", "0", "10", "10", "0", "1", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}

	@Test
	public void testIWardedGenerator_GrowingRecLength_1000() {

		IWardedGenerator iWarded = new IWardedGenerator();
		String[] args = {"5", "1", "1", "1.0", "0", "1", "1.0", "0", "0", "1000", "0",
				"0", "0", "10", "10", "0", "1", "0", "0", "0", "3", "3", "10", "false", "false", "testProgram"};
		iWarded.createModelFromInputArgsForTests(args);
	}


}
