/*package dataStructuresProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	private String file;
	private String row;
	private String[] cityNames;
	private City[] CityArr;
	private int rowLen = 0;
	private int columnLen = 0;
	private int[][] distances;
	
	public CSVReader(String file) throws FileNotFoundException {
		this.file = file;
		readAndInitialize();
		setCityArr();
		

	}
	public void readAndInitialize() throws FileNotFoundException {
		try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            row = br.readLine();
            rowLen++;
            
            String[] cityNamesTemp = row.split(",");
            columnLen = cityNamesTemp.length;
            cityNames = new String[columnLen - 1];
            for(int i = 1; i < columnLen; i++) {
            	cityNames[i-1] = cityNamesTemp[i];
            }
            
            while((row = br.readLine()) != null) {
            	rowLen++;
            }
            br.close();
            distances = new int [rowLen][columnLen];
            
            BufferedReader br2 = new BufferedReader(new FileReader(file));
            
            row = br2.readLine();
            for(int k = 0; k < rowLen; k++) {

                String[] data = row.split(",");
                for(int i = 1; i < data.length; i++) {
                	distances[k][i - 1] = Integer.parseInt(data[i]);
                }
            }

            br2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public int getRowLen() {
		return rowLen;
	}
	public int[][] getDistances(){
		return distances;
	}
	public String[] getCityNames() {
		return cityNames;
	}
	public void setCityArr() {
		
		for(int j = 0; j < rowLen; j++) {
			CityArr[0] = new City(cityNames[j], j);
		}
	}
	public City[] getCityArr() {
		return CityArr;
	}
}
*/
package dataStructuresProject;

import java.io.*;
import java.util.*;

public class CSVReader {
    private String file;
    private String[] cityNames;
    private City[] CityArr;
    private int[][] distances;

    public CSVReader(String file) throws IOException {
        this.file = file;
        readAndInitialize();
        setCityArr();
    }

    private void readAndInitialize() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        // İlk satır = şehir isimleri
        line = br.readLine();
        if (line == null) throw new IOException("CSV is empty");

        String[] names = line.split(",");
        int numCities = names.length - 1; // ilk hücre boş, onu atlıyoruz

        cityNames = new String[numCities];
        for (int i = 1; i < names.length; i++) {
            cityNames[i - 1] = names[i].trim();
        }

        // Satır sayısı = şehir sayısı
        distances = new int[numCities][numCities];
        int row = 0;

        while ((line = br.readLine()) != null && row < numCities) {
            String[] data = line.split(",");
            if (data.length - 1 != numCities) {
                br.close();
                throw new IOException("CSV format error at row " + (row + 2));
            }
            for (int col = 1; col < data.length; col++) {
                distances[row][col - 1] = Integer.parseInt(data[col].trim());
            }
            row++;
        }

        br.close();
    }

    private void setCityArr() {
        int numCities = cityNames.length;
        CityArr = new City[numCities];
        for (int i = 0; i < numCities; i++) {
            CityArr[i] = new City(cityNames[i], i);
        }
    }

    public String[] getCityNames() {
        return cityNames;
    }

    public City[] getCityArr() {
        return CityArr;
    }

    public int[][] getDistances() {
        return distances;
    }
}




