package dataStructuresProject;
//Path class is created in order to have the partial and completed paths. The class is created with the linked list strategy but a reversed version.
public class Path {
    private City lastCity;   //A data field of type City named LastCity. This data keeps track of the last city of the path in order to find its neighbors, we use it.
    private Path initialPath;   //a private data field of type Path named initialPath. This variable is used for keeping track of the initial path (excluding currentCity) in order to use less Java heap space.
    private boolean[] visitedCities;   //A boolean array named visitedCities for each path in order to mark a city if it is visited in a path. The array does not affect the other paths since it belongs to the path itself.
    public int distance = 0;   //The total distance of type int from the beginning point to the currentCity. It will be used to find the shortest distance and hence the shortest path.

    Path(City city, int numberOfTotalCities) {   //A constructor to create a path with only the specified city and number of total cities.
        this.lastCity = city;   //Set LastCity as the specified city. There is only 1 city in the beginning.
        this.visitedCities = new boolean[numberOfTotalCities];   //Create a boolean array of length numberOfTotalCities. If 17 cities are provided, We can mark at most 17 cities hence a bigger array is not needed.
        this.visitedCities[city.getIndex()] = true;   //The boolean array mark the city's index. Let's say that Istanbul is of type City. İts index is 0. The array's value at index 0 will be true.
        //If the second city is let's say İzmir with index 3, The array's value at index 3 will be true / marked as visited. This is faster than traversing the array to check if a city is visited.
        }

    Path(Path initialPath, City newCity, int distanceToNewCity) {   //Create a second constructor with the given initialPath, newCity and distanceToNewCity
        this.initialPath = initialPath;   //This is allowing us to create a parent-child relation in the path class. The initial array will be also be used for several new path in order to prevent the java heap space error
        this.lastCity = newCity;   //Since we are creating a new path with the initial path, we must set the new LastCity as the newCity.
        this.visitedCities = initialPath.visitedCities.clone();   //We are cloning the visitedCities array since the mark cities are not changed except the newCity.
        this.visitedCities[newCity.getIndex()] = true;   //Mark the newCity as visited because it was not visited.
        this.distance = initialPath.distance + distanceToNewCity;   //Increase the distance since a city is added. Increase it by the distance between the newCity and old lastCity
    }//The clone operation is O(n) since we have n cities.

    public boolean visited(City wanted) {   //Check whether a wanted city is visited or not.
        return visitedCities[wanted.getIndex()];   //Return the value of the visitedCities at where the index is the same as the index of the wanted city.  -2 operations. 1 for getting the index of the city, 1 for finding the value in the array.-
    }   //Time complexity is O(1).

    public City getLast() {   //Getter method for the lastCity
        return lastCity;   //Return the lastCity.  -1 operation-
    }   //Time complexity is O(1)
    @Override
    public String toString() {   //An overridden toString method to represent the path as a readable string.
        int size = 0;   //A variable to count the number of cities in the path. -1 operation-
        Path current = this;   //Start with the current path. -1 operation-

        while (current != null) {   //Traverse backward through the path until reaching the initial path. -size operations-
            size++;   //Increment the size for each city in the path. -size operations-
            current = current.initialPath;   //Move to the previous path in the chain. -size operations-
        }
        //Time complexity of this loop: O(n), where n is the number of cities in the path.

        String[] pathInString = new String[size];   //Create an array to store the city names in the correct order. -1 operation-
        current = this;   //Reset current to this path to start filling the array. -1 operation-

        for (int i = size - 1; i >= 0; i--) {   //Iterate through the array from the last index to the first. -n operations-
            pathInString[i] = current.lastCity.getName();   //Store the name of the last city in the current path. -n operations-
            current = current.initialPath;   //Move to the previous path in the chain to continue backward. -n operations-
        }
        //Time complexity of this loop: O(n), where n is the number of cities in the path.

        StringBuilder str = new StringBuilder();   //Create a StringBuilder to efficiently construct the final string. -1 operation-

        for (int i = 0; i < pathInString.length; i++) {   //Iterate through the array of city names. -n operations-
            str.append(pathInString[i]);   //Append the city name to the StringBuilder. -size operations-
            if (i < pathInString.length - 1) {   //If it is not the last city, add an arrow separator. -(n - 1) operation-
                str.append(" -> ");   //Add the arrow between cities to indicate the path. -size - 1 operations-
            }
        }
        //Time complexity of this loop: O(n), where n is the number of cities in the path.

        str.append(" Distance: ").append(distance).append(" km");   //Append the total distance at the end of the string. -3 operations-

        return str.toString();   //Return the final string representation of the path. -1 operation-
    }
    //Total time complexity of this method: O(n), where n is the number of cities in the path.
    public boolean[] clones() {
    	boolean cloned[] = new boolean[visitedCities.length];
    	for(int i = 0; i < visitedCities.length; i++) {
    		cloned[i] = visitedCities[i];
    	}
    	return cloned;
    }
}
