# ToyLanguageInterpreterGUI
**Interpreter for a toy language**

A program consists of a **statement**, which can be one of the following:
* assignment, compound, print
* open/read/close file
* read/write from heap
* fork, sleep, lock, unlock, await
* for, if, repeat, while, switch
* new
* cycle barrier, lock table

There are also **expressions**, such as:
* arithmetic expressions
* boolean expressions
* constant values
* variable names.

The interpreter uses three main structures:
**Execution Stack**: a stack of statements to execute the currrent program__
**Table of Symbols**: a table which keeps the variables values__
**Output**: that keeps all the mesages printed by the toy program__

The program is implemented using the **model-view-controller architectural pattern** and **the repository is saved into a text file** and it also supports **concurrent programming**.
**GUI** was implemented in **JavaFX**.
Toy language programs must be hard coded in the repository and the user can choose the evaluation of one of these.





