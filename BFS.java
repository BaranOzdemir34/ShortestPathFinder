package dataStructuresProject;
//This is the class for the BFS algorithm.
public class BFS {
    int[][] distances;   //A multidimensional array that stores all the distances.
    Path shortestPath;   //A path that refers to the shortestPath. This will be set as new shortPaths are found.
    City[] cities;   //All the cities in the problem.
    int shortestDist = 99999;   //Shortest distance is initially 99999 so that all other paths can be smaller than it.
    final int MAX = 99999;

    BFS(int[][] distances, City[] cities) {   //A constructor to create a BFS object with specified distances array and cities.
        this.distances = distances;
        this.cities = cities;
    }

    public Path BFSPathFinder(City start, City end) {   //A method that will return the shortestPath of type path.
        CustomQueue<Path> open = new CustomQueue<>(cities.length);   //Create a customQueue called open to store the incomplete partial paths. 
        //Length is cities.length because a path cannot be larger than the number of total cities. we can never visit a city more than once for each path.
        CustomQueue<Path> closed = new CustomQueue<>(cities.length);   //Create a customQueue called closed in order to store the complete paths.
        //the length is the same with open.
        
        
        Path initialPath = new Path(start, cities.length);   //Since we do not have any initialPath in the beginning, we have to create one. 
        //Create an inital path with the given start and an int value for the boolean visitedCities array (See Path.java).
        open.enqueue(initialPath);   //Insert the initialPath into the Open Queue so that you can perform operations on the Open Queue.
            while (!open.isEmpty()) {   //Since the open Queue holds the incomplete paths, if all paths are complete, we are done.   -n operations. With for loop, n^3-
            	//Hence we have an expression that will keep us on a loop until all paths are completed
                Path currentPath = open.dequeue();   //Delete the front path from the Open Queue and set it as current.
                City currentCity = currentPath.getLast();  //Get the lastCity of the currentPath and make currentCity equal to it.
                //We now have the last city in the path and we can find the neighbors doing the followings:
                if (currentCity.equals(end)) {   //If the currentCity is the, that path is already complete so we can move on to the next Path.
                    closed.enqueue(currentPath);   //But before moving on, We must insert the path into the complete paths queue named closed.
                } else {   //If the paths is not complete, we have to find the neighbors:
                	for (int i = 0; i < distances.length; i++) {   //Traverse the distances array to find the neighbors. -n operations in the worst case. With the clonning n^2 operations.-
                    	int distanceToNextCity = distances[currentCity.getIndex()][i];   //Find the distance between the currentCity and the neighbor. -2 operations. 1 for finding the distance and 1 for assigning the value-
                    	if (distanceToNextCity != 0 && distanceToNextCity != MAX && !currentPath.visited(cities[i])) {
                        	Path newPath = new Path(currentPath, cities[i], distanceToNextCity);   //Create a new path that consists of the currentPath as initialPath, newCity and the distance. -n operations since the constructor has O(n) time complexity.
                        	open.enqueue(newPath);   //Put the new Path into the open queue so that you can continue your operations.
                    	}
                	}
                }
            }
        while(!closed.isEmpty()) {   //Repeat until the closed queue is empty which means that all completed paths are compared.
        	Path completed = closed.dequeue();   //Dequeue the first path in the queue and set the temporary value as it. -2 operations. 1 for dequeue, 1 for setting the value.-
        	if(completed.distance < shortestDist) {   //Compare it to the shortest distance to check if it is shorter.
        		shortestDist = completed.distance;   //If so, set the new Shortest distance -2 operations. 1 for getting the distance, 1 for setting the value.
        		shortestPath = completed;   //Also set the new Shortest Path.   -1 operations. Setting the value.-
        	}//Time complexity is O(n).
        }
        return shortestPath;   //Return the shortest Path.
    }   //Total complexity is n^3 + n. Time complexity is O(n^3) in total. 2 loops and a cloning each worth n. All done n times. Total will be O(n^3). 
}