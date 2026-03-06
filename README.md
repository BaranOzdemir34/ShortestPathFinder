# ShortestPathFinder
This project has the goal to find the shortest between 2 cities: start city and the target city. The project uses some extended versions of BFS and DFS agorithms for a given weighted graph.
The program stores the names of the cities and their distance in a multi-dimensional array.
The programming language used is %100 Java.

HOW TO USE:
The program has 3 inputs:
1) a csv file path
2) Name of the starting city
3) End of the starting city
After the inputs are given to the program, the program reads the csv file, turns it into a multidimensional array and runs the algorithms with the values in the array.

THE CSV FILE:
The csv file's first column and first row must consist of the name of the cities and the veri first value (the value first row and first column) can be null.
The rest of the values are integer distances of the corresponding 2 cities.
If the cities are the same distance = 0, and if there is not path between them the distance is 99999.
There is a sample csv file called distances.csv consisting of 17 cities of Turkey and their distances, you can check it!
