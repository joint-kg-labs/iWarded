# iWarded

iWarded is a benchmarking system for Warded Datalog+/- reasoning.

In particular it allows:
* building warded Datalog+/- benchmark settings
* building guarded Datalog+/- benchmark settings (which we sometimes call "iGuarded")
* building various subsets of warded and/or guarded Datalog+/- benchmark settings via a multitude of parameters


The benchmark settings it creates consist of synthetic programs and data sources from realistic scenarios with distinct characteristics,
and are aimed at testing and benchmarking systems that implement warded or guarded Datalog+/- reasoning.

Please refer to the [manual](https://github.com/joint-kg-labs/iWarded/blob/main/doc/iWarded_manual.md) for detailed documentation.

## Installing iWarded
iWarded is written in Java and uses ```Maven``` for building.  
### System requirements
- JDK 15 or higher;
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
## Using iWarded
There are different ways to use iWarded:
* for *multiple* uses, with a simple interactive shell built with jLine3, by running ```./iWarded_run.sh``` without any arguments;
* for a *single* use, by running ```./iWarded_run.sh``` with one of the arguments below.

The following are the arguments currently supported by iWarded (both the interactive shell and the generator):
```
usage: CommandLineParameters
 -a,--args                                                           Build Program from Input Parameters
 -c,--config ([path-to-configScenarioFile.txt] only in single use)   Build Program from Configuration Scenario
 -d,--docs                                                           Describe Input Parameters from Documentation
 -e,--exit                                                           Close iWarded
```
As can be observed, iWarded allows to generate programs from input args as well as from pre-defined [configuration scenarios](https://github.com/joint-kg-labs/iWarded/tree/main/exampleScenarios).  
The following is an example of a single use of the generator, based on the configuration scenario ```smallProgram.txt```:
```
./iWarded_run.sh -c exampleScenarios/smallScenario.txt
```
Each execution generates a warded program, written in a ```.vada``` file, and a corresponding set of ```.csv``` files as data sources.  
It also provides the time (in seconds) required for the generation process.

## Contact Information
- Teodoro Baldazzi  - https://www.linkedin.com/in/teodoro-baldazzi/
- Luigi Bellomarini - https://www.linkedin.com/in/bellomarini/
- Emanuel Sallinger - https://dbai.tuwien.ac.at/staff/sallinger/

## Acknowledgements

This project is supported by the Vienna Science and Technology fund (WWTF) grant VRG18-013. See https://kg.dbai.tuwien.ac.at/ for more details.
