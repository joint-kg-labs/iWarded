# User Manual to iWarded

# Overview

iWarded is a benchmarking system for Warded Datalog+/- reasoning.

In particular it allows:
* building warded Datalog+/- benchmark settings
* building guarded Datalog+/- benchmark settings (which we sometimes call "iGuarded")
* building various subsets of warded and/or guarded Datalog+/- benchmark settings via a multitude of parameters

The benchmark settings it creates consist of synthetic programs and data sources from realistic scenarios with distinct characteristics,
and are aimed at testing and benchmarking systems that implement warded or guarded Datalog+/- reasoning.

## Contact Information
- Teodoro Baldazzi - teodoro.baldazzi@gmail.com

# Table of Contents

- [Installation and Tutorial](#installation-and-tutorial)
	* [Installation](#installation)
	* [Commandline Options](#commandline-options)
	* [Tutorial](#tutorial)
- [The Generator](#the-generator)
	* [Rules and Features](#rules-and-features)
	* [Configuration Scenario and Program](#configuration-scenario-and-program)


# Installation and Tutorial

## Installation
iWarded is written in Java and it uses ```Maven``` for building.  

### System requirements
- JDK 1.8.0 or higher;
- Maven 3.

### Installation steps
To build iWarded, run the following script from the source folder ```iWardedGenerator```.  
```
./iWarded_build.sh
```
This will download ```jar``` dependencies and generate a jar file in the ```target``` folder.  
To check that iWarded is working, run the following script from the source folder ```iWardedGenerator```:
```
./iWarded_run.sh h
```

## Commandline Options
There are different ways to use iWarded:
* for *multiple* uses, with a simple interactive shell built with jLine3, by running ```./iWarded_run.sh``` without any arguments;
* for a *single* use, by running ```./iWarded_run.sh``` with one of the arguments below.

The following are the arguments currently supported by iWarded (both the interactive shell and the generator):
```
usage: CommandLineParameters
 -a,--args                                                           Build Program from Input Parameters
 -c,--config ([path-to-configScenarioFile.txt] only in single use)   Build Program From from Configuration Scenario
 -d,--docs                                                           Describe Input Parameters from Documentation
 -e,--exit                                                           Close iWarded
```
As can be observed, iWarded allows to generate programs from input args as well as from pre-defined [configuration scenarios](#configuration-scenario-and-program).  
The following is an example of a single use of the generator, based on the configuration scenario ```smallProgram.txt```:
```
./iWarded_run.sh -c exampleScenarios/smallScenario.txt
```

## Tutorial
This tutorial assumes that iWarded is already [installed](#installation-and-tutorial).  
It makes use of the pre-defined configuration file ```exampleScenarios/tutorialScenario```, which describes a small program with some of the main [components](#rules-and-features) supported by the generator:
~~~sh
# iWarded Scenario Configuration File

# Number of Input Atoms
NumberOfInputAtoms = 5

# Number of Output Atoms
NumberOfOutputAtoms = 3

# Average number of Variables per Atom
AverageVarsInAtom = 1

# Variance of Variables per Atom
VarianceVarsInAtom = 1.0

# Number of Existential Rules
NumberOfExistentialRules = 3

# Average number of Existentially Quantified Variables per Rule
AverageEVarsInRule = 1

# Variance of Existentially Quantified Variables per Rule
VarianceEVarsInRule = 1.0

# Number of Dangerous Rules
NumberOfDangerousRules = 3

# Number of Linear Rules
NumberOfLinearRules = 6

# Number of Harmless-Harmless Joins with Ward
NumberOfHarmlessHarmlessJoinsWithWard = 3

# Number of Harmless-Harmless Joins without Ward
NumberOfHarmlessHarmlessJoinsWithoutWard = 0

# Number of Harmless-Harmful Joins
NumberOfHarmlessHarmfulJoins = 0

# Number of Harmful-Harmful Joins
NumberOfHarmfulHarmfulJoins = 0

# Average Number of Chase Steps from Input to Output
AverageChaseSteps = 2

# Number of Linear Recursions
NumberOfLinearRecursions = 1

# Number of Left-Join Recursions
NumberOfLeftJoinRecursions = 1

# Number of Right-Join Recursions
NumberOfRightJoinRecursions = 0

# Number of Non-Linear Join Recursions
NumberOfNonLinearJoinRecursions = 0

# Average Recursion length
AverageRecursionLength = 2

# Number of Selection Conditions
NumberOfSelectionConditions = 3

# Average Selectivity
AverageSelectivity = 3

# Number of records in CSV files
NumberOfRecordsCSV = 10

# Guardedness of the Program
IsGuarded = false

# Name of the Program
ProgramName = tutorialProgram
~~~
To generate the corresponding program and data sources, you may run the following (*single* use):
```
./iWarded_run.sh -c exampleScenarios/tutorialScenario.txt
```
Each execution generates a warded program, written in a ```.vada``` file, and a corresponding set of ```.csv``` files as data sources.  
It also provides the time (in seconds) required for the generation process.  
The generated program (```.vada```) and data (```.csv```) are added to the folder ```generatedPrograms/tutorialProgram```.  
This is an example of a program created with the scenario above:
```
% =====ORIGINAL VADA PROGRAM PARAMETERS=====
% Number of Input Atoms: 5
% Number of Output Atoms: 3
% Average number of Variables per Atom: 1
% Variance number of Variables per Atom: 1.0
% Number of Existential Rules: 3
% Average number of Existentially Quantified Variables per Rule: 1
% Variance of Existentially Quantified Variables per Rule: 1.0
% Number of Dangerous Rules: 3
% Number of Linear Rules: 6
% Number of Join Rules: 3
% Number of Harmless-Harmless Join Rules with Ward: 3
% Number of Harmless-Harmless Join Rules without Ward: 0
% Number of Harmless-Harmful Joins: 0
% Number of Harmful-Harmful Joins: 0
% Average number of Chase Steps: 2
% Number of Chase Steps for each Output Atom: 
% out_1->4; out_2->3; out_3->2; 
% Number of Linear Recursions: 1
% Number of Left Join Recursions: 1
% Number of Right Join Recursions: 0
% Number of Non-Linear Join Recursions: 0
% Average Recursion Length: 2
% Length for each Recursion: 
% leftJRec_1->1; linRec_1->2; 
% Number of Selection Conditions: 3
% Average Selectivity: 3
% Number of records in CSV files: 10
% Guardedness of the Program: false
% Name of the Program: tutorialProgram
% ==========================================

% =====ADAPTED VADA PROGRAM PARAMETERS=====
% Number of Input Atoms: 3
% Number of Dangerous Rules: 1
% Number of Harmless-Harmless Join Rules with Ward: 2
% Number of Harmless-Harmless Join Rules without Ward: 1
% ==========================================

@output("out_1").
@output("out_2").
@output("out_3").
@input("edb_4").
@bind("edb_4","csv","generatedPrograms/tutorialProgram","edb_4_csv.csv").
@mapping("edb_4",0,"arg_0","int").
@mapping("edb_4",1,"arg_1","int").
@input("edb_2").
@bind("edb_2","csv","generatedPrograms/tutorialProgram","edb_2_csv.csv").
@mapping("edb_2",0,"arg_0","int").
@input("edb_3").
@bind("edb_3","csv","generatedPrograms/tutorialProgram","edb_3_csv.csv").
@mapping("edb_3",0,"arg_0","int").
@mapping("edb_3",1,"arg_1","int").
idb_1(HARMLESS_1,HARMLESS_2) :- edb_4(HARMLESS_1,HARMLESS_2).
idb_2(HARMLESS_1,HARMLESS_2) :- idb_1(HARMLESS_1,HARMLESS_2), edb_4(HARMLESS_3,HARMLESS_2), HARMLESS_1>=40.
idb_3(HARMLESS_1,HARMFUL_1) :- idb_2(HARMLESS_1,HARMLESS_2).
idb_4(HARMLESS_1) :- idb_3(HARMLESS_1,HARMFUL_1), edb_2(HARMFUL_1).
idb_1(HARMLESS_1,HARMLESS_2) :- idb_2(HARMLESS_1,HARMLESS_2).
idb_2(HARMLESS_4,HARMLESS_5) :- idb_2(HARMLESS_4,HARMLESS_5), edb_3(HARMLESS_6,HARMLESS_5).
out_1(HARMLESS_1,HARMFUL_1) :- idb_3(HARMLESS_1,HARMFUL_1), HARMLESS_1=32.
out_2(HARMLESS_1,HARMFUL_2) :- idb_2(HARMLESS_1,HARMLESS_2), HARMLESS_1>=13.
out_3(HARMLESS_1,HARMFUL_3) :- idb_1(HARMLESS_1,HARMLESS_2).
```
Please refer to [Configuration Scenario and Program](#configuration-scenario-and-program) for a more in-depth explanation and to ```exampleScenarios``` and the [test suite](https://github.com/joint-kg-labs/iWarded/blob/main/src/test/java/test/iWarded/TestiWarded.java) for more example scenarios.


# The Generator

## Rules and Features
iWarded generates sets of Warded Datalog+/- rules with diverse characteristics. The current version of iWarded follows the language model and notation adopted in the [Vadalog System](https://www.vldb.org/pvldb/vol11/p975-bellomarini.pdf).

We here provide insight into the types of rules and recursions it can generate, as well as a set of additional features, part of the Vadalog language.  
In terms of rules, iWarded is able to create:
* *existential*, *harmless*, *harmful* and *dangerous* rules;
* *linear* rules (single body atom) and *join* rules (multiple body atoms, with facts joined on common variable or Cartesian product) with at most two atoms, as any n-ary join can be rewritten in this form;
* *harmless-harmless*, *harmless-harmful* and *harmful-harmful* joins rules, depending on the nature of the variables involved in the join;
* harmless-harmless join *with ward*, when one of the involved atoms contains the dangerous variables, and *without ward* otherwise (no dangerous variables, by wardedness).

In terms of recursions, iWarded is able to create:
* *direct*, when in a single rule a body atom also appears in the head, and *indirect* recursions, for longer cycles in the predicate graph;
* *linear* recursion on linear rules, as well as *left*, *right* and *non-linear* recursion on join rules, whether the left body atom, the right one or both are recursive, respectively.

In terms of additional features, iWarded is able to create:
* *expressions*, boolean expressions which combine body variables with standard comparison operators (=,>,<,>=,<=,<>);
* *annotations*, special facts that allow injecting specific behaviors into the set of rules: *input* annotations specify that the facts for an atom are imported from an external data source; *output* annotations specify that the facts for an atom are exported to an external destination; *bind* annotations, which bind input or output atoms to a source; *mapping* annotations, which map specific columns of the input/output source to a position of an atom;
* *guarded* programs, sets of rules where (at least) a single atom in the body, called *guard*, contains all universally quantified variables.

Please refer to [Configuration Scenario and Program](#configuration-scenario-and-program) for a detailed description of the structure of a generic generated program.

## Configuration Scenario and Program
The generation of programs and data sources with iWarded makes use of *configurations* which describe the scenario in terms of key-value pairs: due to some random choices in the generation, distinct programs may be created from a single configuration.

It is possible to create a program from a pre-defined configuration file (using the ```-c [path-to-configScenarioFile.txt]``` option) or by adding the corresponding values to the primitives at runtime (using the ```-a``` option).  
Please refer to the [template](https://github.com/joint-kg-labs/iWarded/blob/main/exampleScenarios/templateScenario.txt) to build a configuration file and to ```exampleScenarios``` and the [test suite](https://github.com/joint-kg-labs/iWarded/blob/main/src/test/java/test/iWarded/TestiWarded.java) for multiple example scenarios.

A generic scenario is based on the primitives currently supported by iWarded:
* *Number of Input Atoms*:
	the total number of edb atoms with the ```@input``` annotation and the ```.csv``` dataset
    [int>=1]

* *Number of Output Atoms*:
	the total number of idb atoms with the ```@output``` annotation
    [int>=0]

* *Average number of Variables per Atom*:
	the average number of variables as arguments of each atom
    [int>=1]

* *Variance of Variables per Atom*:
	the variance for the number of variables as arguments of each atom
    [float>=1.0]

* *Number of Existential Rules*:
	the total number of rules with an existential quantifier in the head
    [int>=0]

* *Average number of Existentially Quantified Variables per Rule*:
	the average number of existential quantifiers in each head of existential rules
    [int>=0]

* *Variance of Existentially Quantified Variables per Rule*:
	the variance for the number existential quantifiers in each head of existential rules
    [float>=0]

* *Number of Dangerous Rules*:
	the total number of warded rules with dangerous variables
    [int>=0]

* *Number of Linear Rules*:
	the total number rules with a linear body
    [int>=0]

* *Number of Harmless-Harmless Joins with Ward*:
	the total number of rules with join between harmless variables and with dangerous variables in the ward
    [int>=0]

* *Number of Harmless-Harmless Joins without Ward*:
	the total number of rules with join between harmless variables and without dangerous variables
    [int>=0]

* *Number of Harmless-Harmful Joins*:
	the total number of rules with join between a harmless variable and a harmful one
    [int>=0]

* *Number of Harmful-Harmful Joins*:
	the total number of rules with join between harmful variables
    [int>=0]

* *Average Number of Chase Steps from Input to Output*:
	the average number of steps for each input-output sequence
    [int>=0]

* *Number of Linear Recursions*:
	the total number of linear rules with recursions
    [int>=0]

* *Number of Left-Join Recursions*:
	the total number of join rules with recursion on the left-join atom
    [int>=0]

* *Number of Right-Join Recursions*:
	the total number of join rules with recursion on the right-join atom
    [int>=0]

* *Number of Non-Linear Join Recursions*:
	the total number of join rules with recursion on both the left-join and the right-join atom
    [int>=0]

* *Average Recursion length*:
	the average number of steps for each recursive cycle
    [int>=0]

* *Number of Selection Conditions*:
	the total number of selection conditions for the arguments in the bodies of the rules
    [int>=0]

* *Average Selectivity*:
	the average number of facts filtered in
    [int>=0]

* *Number of records in CSV files*:
	the total number of records in each input atoms'```.csv``` file
    [int>=0]

* *Guardedness of the Program*:
	whether for each rule all the universally quantified variables are comprised within a single guard atom in the body
    [boolean true/false]

* *Name of the Program*:
	name of the folder containing the generated program and the data files

~~~sh
# iWarded Scenario Configuration File

# Number of Input Atoms
NumberOfInputAtoms = 5

# Number of Output Atoms
NumberOfOutputAtoms = 3

# Average number of Variables per Atom
AverageVarsInAtom = 1

# Variance of Variables per Atom
VarianceVarsInAtom = 1.0

# Number of Existential Rules
NumberOfExistentialRules = 3

# Average number of Existentially Quantified Variables per Rule
AverageEVarsInRule = 1

# Variance of Existentially Quantified Variables per Rule
VarianceEVarsInRule = 1.0

# Number of Dangerous Rules
NumberOfDangerousRules = 3

# Number of Linear Rules
NumberOfLinearRules = 6

# Number of Harmless-Harmless Joins with Ward
NumberOfHarmlessHarmlessJoinsWithWard = 3

# Number of Harmless-Harmless Joins without Ward
NumberOfHarmlessHarmlessJoinsWithoutWard = 0

# Number of Harmless-Harmful Joins
NumberOfHarmlessHarmfulJoins = 0

# Number of Harmful-Harmful Joins
NumberOfHarmfulHarmfulJoins = 0

# Average Number of Chase Steps from Input to Output
AverageChaseSteps = 2

# Number of Linear Recursions
NumberOfLinearRecursions = 1

# Number of Left-Join Recursions
NumberOfLeftJoinRecursions = 1

# Number of Right-Join Recursions
NumberOfRightJoinRecursions = 0

# Number of Non-Linear Join Recursions
NumberOfNonLinearJoinRecursions = 0

# Average Recursion length
AverageRecursionLength = 2

# Number of Selection Conditions
NumberOfSelectionConditions = 3

# Average Selectivity
AverageSelectivity = 3

# Number of records in CSV files
NumberOfRecordsCSV = 10

# Guardedness of the Program
IsGuarded = false

# Name of the Program
ProgramName = tutorialProgram
~~~
The generated program consists of a network of rule sequences, designed to control the propagation of null and avoid violations of wardedness. The following is an example of a program generated from the ```exampleScenarios/tutorialScenario.txt``` above, which we here make use of to show the generic program template:
```
% =====ORIGINAL VADA PROGRAM PARAMETERS=====
% Number of Input Atoms: 5
% Number of Output Atoms: 3
% Average number of Variables per Atom: 1
% Variance number of Variables per Atom: 1.0
% Number of Existential Rules: 3
% Average number of Existentially Quantified Variables per Rule: 1
% Variance of Existentially Quantified Variables per Rule: 1.0
% Number of Dangerous Rules: 3
% Number of Linear Rules: 6
% Number of Join Rules: 3
% Number of Harmless-Harmless Join Rules with Ward: 3
% Number of Harmless-Harmless Join Rules without Ward: 0
% Number of Harmless-Harmful Joins: 0
% Number of Harmful-Harmful Joins: 0
% Average number of Chase Steps: 2
% Number of Chase Steps for each Output Atom: 
% out_1->2; out_2->1; out_3->5; 
% Number of Linear Recursions: 1
% Number of Left Join Recursions: 1
% Number of Right Join Recursions: 0
% Number of Non-Linear Join Recursions: 0
% Average Recursion Length: 2
% Length for each Recursion: 
% leftJRec_1->1; linRec_1->3; 
% Number of Selection Conditions: 3
% Average Selectivity: 3
% Number of records in CSV files: 10
% Guardedness of the Program: false
% Name of the Program: tutorialProgram
% ==========================================
% =====ADAPTED VADA PROGRAM PARAMETERS=====
% Number of Input Atoms: 4
% Number of Linear Rules: 9
% Number of Dangerous Rules: 1
% Number of Harmless-Harmless Join Rules without Ward: 1
% ==========================================
@output("out_1").
@output("out_2").
@output("out_3").
@input("edb_1").
@bind("edb_1","csv","generatedPrograms/tutorialProgram","edb_1_csv.csv").
@mapping("edb_1",0,"arg_0","int").
@mapping("edb_1",1,"arg_1","int").
@input("edb_3").
@bind("edb_3","csv","generatedPrograms/tutorialProgram","edb_3_csv.csv").
@mapping("edb_3",0,"arg_0","int").
@input("edb_4").
@bind("edb_4","csv","generatedPrograms/tutorialProgram","edb_4_csv.csv").
@mapping("edb_4",0,"arg_0","int").
@input("edb_2").
@bind("edb_2","csv","generatedPrograms/tutorialProgram","edb_2_csv.csv").
@mapping("edb_2",0,"arg_0","int").

idb_1(HARMLESS_1,HARMLESS_2) :- edb_1(HARMLESS_1,HARMLESS_2), HARMLESS_1>=29.

idb_2(HARMLESS_1,HARMLESS_2) :- idb_1(HARMLESS_1,HARMLESS_2).
idb_3(HARMLESS_1,HARMFUL_1) :- idb_2(HARMLESS_1,HARMLESS_2), edb_3(HARMLESS_2).
idb_4(HARMLESS_1) :- idb_3(HARMLESS_1,HARMFUL_1), HARMLESS_1<=6.

idb_5(HARMLESS_1) :- idb_4(HARMLESS_1), idb_1(HARMLESS_3,HARMLESS_1).
idb_6(HARMLESS_1) :- idb_5(HARMLESS_1), edb_4(HARMLESS_1).

idb_1(HARMLESS_1,HARMLESS_1) :- idb_3(HARMLESS_1,HARMFUL_1).
idb_6(HARMLESS_1) :- idb_6(HARMLESS_1), edb_2(HARMLESS_1).

idb_7(HARMLESS_1,HARMFUL_2) :- idb_1(HARMLESS_1,HARMLESS_2), HARMLESS_1<=36.
idb_8(HARMLESS_1,HARMFUL_2) :- idb_7(HARMLESS_1,HARMFUL_2).

out_1(HARMLESS_1,HARMFUL_3) :- idb_1(HARMLESS_1,HARMLESS_2).
out_2(HARMLESS_1,HARMLESS_2) :- edb_1(HARMLESS_1,HARMLESS_2).
out_3(HARMLESS_1) :- idb_4(HARMLESS_1).
```
The generic program consists of:
* *Comment section - Original parameters*:
	documents the value of the input parameters as well as internal parameters generated by iWarded (e.g., the average number of chase steps and the number of steps for each output atom, respectively).

* *Comment section - Adapted parameters*:
	documents the adjustments applied by iWarded to the input parameters to generate a consistent set of rules.

* *Annotation section*:
	the set of ```@output```, ```@input```, ```@bind``` and ```@mapping``` annotations, with reference to the output atoms, the input atoms, 
	the data sources' location and the arguments mapping, respectively.

* *Root rule*:
	the first rule of the set, by convention it is a linear harmless rule.

* *Input-Output sequence*:
	a set of linear and join rules, with various characteristics based on the input parameters.

* *Recursive sequences*:
	a set of rules part of a recursion.

* *Recursive closure sequence*:
	a set of linear and join rules, each introducing a recursion and thus a cycle in the predicate graph.

* *Program completion sequence*:
	a set of extra rules needed to globally satisfy the parameters, e.g., the number of selection conditions, in the form of expressions, in our example.

* *Output completion sequence*:
	a set of linear rules, one for each input-output sequence; the rule head is the output atom and the body atom is connected to the head of the rule closing the sequence.

Therefore, the generic program has three types of branches (i.e., sets of rules):

* *Main branch*: 
from the root rule, the input-output sequence, the recursive sequences and the corresponding closures;

* *Secondary branch*: 
from the root rule, a sequence of non existential, non dangerous linear rules to satisfy the corresponding input parameter, if required;

* *Tertiary branches*: 
from the root rule, individual existential, dangerous, linear and join rules to satisfy  the corresponding input parameters, if required.