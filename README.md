# Hospital Management System

This repository contains a Java implementation of several data structures and a simple hospital management system.

## Project Structure

The project includes the following Java source files:

- `DSAGraph.java` - Graph data structure implementation.
- `DSAGraphNode.java` - Node used in the graph implementation.
- `DSAHashTable.java` - Hash table data structure implementation.
- `DSAHeap.java` - Heap data structure implementation.
- `DSAHeapEntry.java` - Entries stored in the heap.
- `DSALinkedList.java` - Singly linked list implementation.
- `DSAQueue.java` - Queue data structure implementation.
- `DSASorts.java` - Sorting algorithms (e.g., insertion sort, selection sort).
- `DSAStack.java` - Stack data structure implementation.
- `HospitalManagementSystem.java` - Simple command-line hospital management system using the data structures.
- `Patient.java` - Class representing a patient in the hospital system.

Test files (JUnit tests):

- `ModuleOneTest.java`
- `ModuleTwoTest.java`
- `ModuleThreeTest.java`
- `ModuleFourTest.java`

## Requirements

- Java JDK 8 or later
- Optional: JUnit 4 for running tests (if not included in the project classpath already)

## Compilation

To compile all Java files, run:

```sh
javac *.java
```

This will produce `.class` files for each source file.

## Running the Application

To run the hospital management system, execute:

```sh
java HospitalManagementSystem
```

Follow the on-screen prompts to add, remove, or display patient information.

## Running Tests

If you have JUnit installed, compile the test files with the classpath pointing to the JUnit jar:

```sh
javac -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar *.java
```

Then run tests using:

```sh
java -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore ModuleOneTest
```

Replace `ModuleOneTest` with the desired test class name. Repeat for other modules.

## Notes

- The data structure implementations are standard exercises and may be used or extended for other applications.
- The hospital management system is a basic command-line program demonstrating usage of the data structures.

## Author

Bhagwat Ajaykumar Patel
