package dataStructuresProject;
//The city class is implemented in order to create cities
public class City {
    private String name;   //All cities have a name of type string
    private int index;     //All cities have indexes to be used in the matrices

    City(String name, int index) {   //A constructor to construct a city with the specified name and index
        this.name = name;            //Set the name to the specified name
        this.index = index;          //Set the index to the specified index
    }

    public String getName() {   //A getter method to access the name of return type String
        return name;            //Return the name   -1 operation-
    } //Time complexity = O(1)

    public int getIndex() {   //A getter method to access the index of return type int
        return index;         //Return the index   -1 operation-
    } //Time complexity = O(1)
}