package dataStructuresProject;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ProgramUI extends JFrame 
{
	private JTextField startCityField;
    private JTextField csvFileField;
    private JTextField endCityField;
    private JTextField unitDist;
    private JTextArea resultArea;
    private JButton findPathButton;
    private static City[] bfsCities;
    private static String[] dfsCities;
    private static int[][] adjacencyMatrix;

    public ProgramUI() throws FileNotFoundException 
    {
        setTitle("Shortest Path Finder");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initialize components
        csvFileField = new JTextField(30);
        startCityField = new JTextField(20);
        endCityField = new JTextField(20);
        unitDist = new JTextField(10);
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        findPathButton = new JButton("Find Shortest Path");

        //Setup the panel for the form
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("CSV File*:"));
        inputPanel.add(csvFileField);
        inputPanel.add(new JLabel("Starting City*:"));
        inputPanel.add(startCityField);
        inputPanel.add(new JLabel("Destination City*:"));
        inputPanel.add(endCityField);
        inputPanel.add(new JLabel("Unit Distance:"));
        inputPanel.add(unitDist);
        inputPanel.add(findPathButton);

        //Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        //Action listener for the button
        findPathButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
					handlePathCalculation();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        
    }

    private void initializeData() throws IOException 
    {
    	String csvFileCont = csvFileField.getText().trim();
    	CSVReader readerC = new CSVReader(csvFileCont);
    	dfsCities = readerC.getCityNames();
    	bfsCities = readerC.getCityArr();
    	adjacencyMatrix = readerC.getDistances();
    }
    private void handlePathCalculation() throws IOException 
    {
    	{
    	    try 
    	    {
    	        initializeData();
    	    } 
    	    catch (FileNotFoundException e) 
    	    {
    	        resultArea.setText("CSV file cannot be found.");
    	        return;
    	    }
        String startCityName = startCityField.getText().trim();
        String endCityName = endCityField.getText().trim();
        String unitD = unitDist.getText().trim();
        
        if (startCityName.isEmpty() || endCityName.isEmpty()) 
        {
            resultArea.setText("Please enter both the starting and destination cities.");
            return;
        }

        try 
        {
            // Create instances for DFS and BFS
            DFS dfsAlgorithm = new DFS(adjacencyMatrix, dfsCities);
            BFS bfsAlgorithm = new BFS(adjacencyMatrix, bfsCities);
            
            // For BFS
            City startBFS = findCityByName(startCityName, bfsCities);
            City endBFS = findCityByName(endCityName, bfsCities);
            
            // Perform DFS
            long dfsStartTime = System.nanoTime();
            String[] dfsShortestPath = dfsAlgorithm.dfs(startCityName, endCityName);
            long dfsEndTime = System.nanoTime();
            String dfsResult = "DFS Shortest Path: " + Arrays.toString(dfsShortestPath) + "\nDistance: " + dfsAlgorithm.calculatePathDistance(dfsShortestPath) + " " + unitD + "\nExecution Time: " + (dfsEndTime - dfsStartTime) / 1_000_000.0 + " milliseconds\n";
            
            // Perform BFS
            long bfsStartTime = System.nanoTime();
            Path bfsPath = bfsAlgorithm.BFSPathFinder(startBFS, endBFS);
            long bfsEndTime = System.nanoTime();
            String bfsResult = "BFS Shortest Path: " + bfsPath + "\nDistance: " + bfsPath.distance + " " + unitD + "\nExecution Time: " + (bfsEndTime - bfsStartTime) / 1_000_000.0 + " milliseconds\n";
            
            resultArea.setText(dfsResult + "\n" + bfsResult);
        } 
        catch (CityNotFoundException e) 
        {
            resultArea.setText(e.getMessage());
        }
    	}
    }

    // Find city by name for BFS
    public static City findCityByName(String cityName, City[] cities) 
    {
        cityName = normalize(cityName);
        
        for (City city : cities) 
        {
            if (normalize(city.getName()).equalsIgnoreCase(cityName)) 
                return city;
        }
        
        throw new CityNotFoundException("City '" + cityName + "' cannot be found by the system. Please make sure that you write the city's name correctly.");
    }

    //Turkish characters fix
	private static String normalize(String input) 
	{
	    return input
	            .replace("İ", "I").replace("ı", "i")
	            .replace("Ğ", "G").replace("ğ", "g")
	            .replace("Ü", "U").replace("ü", "u")
	            .replace("Ş", "S").replace("ş", "s")
	            .replace("Ö", "O").replace("ö", "o")
	            .replace("Ç", "C").replace("ç", "c");
	}

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            ProgramUI ui = null;
			try {
				ui = new ProgramUI();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ui.setVisible(true);
        });
    }
}
