package dataStructuresProject;

public class DFS 
{
    private int[][] adjacencyMatrix;
    private String[] cities;
    private int numberOfCities;

    //Constructor
    public DFS(int[][] adjacencyMatrix, String[] cities) 
    {
        this.adjacencyMatrix = adjacencyMatrix;
        this.cities = cities;
        this.numberOfCities = cities.length; 
    }
    
    //Main DFS method
    public String[] dfs(String from, String to) //-1 as a index, is invalid, [n = numberOfCities]
    {
        CustomStack<Integer> stack = new CustomStack<>(); //Custom stack by Ege ÖZTÜRK
        
        String[] shortestPath = new String[numberOfCities]; //The shortest path
        int shortestPathLength = 0; //The shortest path array's length
        int bestKnownDistance = Integer.MAX_VALUE; //Just comparing with finding path because it provides correct compare
        
        int[] parent = new int[numberOfCities]; //Parents of the visiting nodes
        int[] distances = new int[numberOfCities]; //Distance we will go with adding each other
        		
        for (int i = 0; i < numberOfCities; i++)
        {
            parent[i] = -1; //Roots dont have parents so every parent is -1, numberOfCities in worst case so O(n)
        	distances[i] = Integer.MAX_VALUE; //There isn't called distance rn so every distance is infinite, numberOfCities in worst case so O(n)
        }
        
        int firstCity = findCityIndex(from);
        int lastCity = findCityIndex(to);
        
        //From now on, I record first destination (first node) and push it into the stack
        distances[firstCity] = 0;
        stack.push(firstCity);

        while (!stack.isEmpty()) //DFS is searching, time complexity is O(n^2) in the worst case. Please look at below for more details
        {
            int currentCity = stack.pop(); //Pop the city from the stack, DFS
            
            //If I arrived my destination, continue to find paths but track the shortest path
            if (currentCity == lastCity) 
            {
                String[] currentPath = new String[numberOfCities]; //Allocate memory as much as max number of cities. In worst case, the path will travel all of cities.
                int elevator = currentCity; //Elevator will go backward in same path
                int currentPathLength = 0; //I will use for currentPath

                while (elevator != -1) //Im trying to go backward with elevator, if the elevator is -1, Im on the root. Elevator travels all of cities in worst case so O(n) in worst case.
                {
                	currentPath[currentPathLength] = cities[elevator]; //First draw the current path
                    currentPathLength++;
                    elevator = parent[elevator]; //And elevator takes the parent of the current city so we can go backward
                }
                
                //When the while loop ends currentPathLength = current path's real length but we did above currentPath.length = numberOfCities so we use currentPathLength instead of currentPath.length
                
                if (currentPath.length > 1) //If its length 1, never mind
                	reverseArray(currentPath, currentPathLength);  //Reverse the array because we went backward using elevator (to -> from), now currentPath is correct (from -> to), O(n) in worst case
                
                int currentPathDistance = calculatePathDistance(currentPath, currentPathLength);
                if (shortestPath[0] == null || currentPathDistance < bestKnownDistance) //If the path its first or shortest path 
                {
                    for (int i = 0; i < currentPathLength; i++) //Current path is shortest path. currentPathLength = numberOfCities when its max. so O(n) in worst case.
                        shortestPath[i] = currentPath[i];

                    shortestPathLength = currentPathLength; //Assign the length of array
                    bestKnownDistance = currentPathDistance; //Assign the distance
                }
        
                continue;
            }

            //Push the neighbor of the current city into the stack
            for (int neighbor = 0; neighbor < numberOfCities; neighbor++) //It makes time comp O(n^2) in worst case 
            {
                int distanceBetween = adjacencyMatrix[currentCity][neighbor]; //Distance between the current city and its neighbor
                
                if (distanceBetween != 99999 && distanceBetween > 0) //If there is a path between current city and its neighbor
                {			
                    int newDistance = distances[currentCity] + distanceBetween; //New distance
                    
                    if (newDistance < distances[neighbor]) //If its the shortest go from there
                    {
                        distances[neighbor] = newDistance; 
                        parent[neighbor] = currentCity; //New path
                        stack.push(neighbor); //Work on the neighbor
                    }
                }
            }
        }

        //In the end, {for (int i...) 2 * O(n)} +  {while(!stack...) O(n)} * [{while (elevator...) O(n)} + {for (neighbor...) O(n)} + {for (shortestPath...) O(n)}] + {trimArray O(n)} = O(3n^2 + 3n) hence O(n^2) in worst case
        
        return trimArray(shortestPath, shortestPathLength);	/*Unfortunately we are not able to use List<>, + you can not any changes in arrays so delete nulls 
        													  (because shortestPath.length = numberOfCities) from the array and return it*/
    }

	//Reversing the elevated array
    private void reverseArray(String[] array, int length) //O(n/2) hence O(n) in worst case

    {
        for (int i = 0; i < length / 2; i++) 
        {
            String temp = array[i];
            array[i] = array[length - 1 - i];
            array[length - 1 - i] = temp;
        }
    }
    
    //Finalize the shortest path array
    private String[] trimArray(String[] array, int length)  //O(n) in worst case by System.arraycopy
    {
        String[] trimmed = new String[length];
        System.arraycopy(array, 0, trimmed, 0, length);
        return trimmed;
    }
    
    //Converting string to int
    public int findCityIndex(String city) //O(n) in worst case
    {
        city = normalize(city);
        
        for (int i = 0; i < numberOfCities; i++) 
        {
            if (normalize(cities[i]).equalsIgnoreCase(city)) 
                return i;
        }
        
        throw new CityNotFoundException("City '" + city + "' cannot be found by the system. Please make sure that you write the city's name correctly.\n"); //City not found
    }
    
    //Turkish characters fix
    private String normalize(String input) 
    {
        return input
                .replace("İ", "I").replace("ı", "i")
                .replace("Ğ", "G").replace("ğ", "g")
                .replace("Ü", "U").replace("ü", "u")
                .replace("Ş", "S").replace("ş", "s")
                .replace("Ö", "O").replace("ö", "o")
                .replace("Ç", "C").replace("ç", "c");
    }
    
    
    //Calculating path distances
    public int calculatePathDistance(String[] path) //O(n-1) hence O(n) in worst case
    {
        int distance = 0;
        
        for (int i = 0; i < path.length - 1; i++) 
        {
            int from = findCityIndex(path[i]);
            int to = findCityIndex(path[i + 1]);
            
            if (from == -1 || to == -1) //Invalid, -1 means the city invalid
                return Integer.MAX_VALUE;
            
            distance += adjacencyMatrix[from][to];
        }
        
        return distance;
    }
    
    private int calculatePathDistance(String[] path, int length) //O(n-1) hence O(n) in worst case
    {
        int distance = 0;
        
        for (int i = 0; i < length - 1; i++) 
        {
            int from = findCityIndex(path[i]);
            int to = findCityIndex(path[i + 1]);
            
            if (from == -1 || to == -1) //Invalid, -1 means the city is invalid
                return Integer.MAX_VALUE;
            
            distance += adjacencyMatrix[from][to];
        }
        
        return distance;
    }
}
