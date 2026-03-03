package dataStructuresProject;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JTextField endCityField;
    private JTextArea resultArea;
    private JButton findPathButton;
    private static City[] bfsCities;
    private static String[] dfsCities;
    private static int[][] adjacencyMatrix;

    public ProgramUI() 
    {
        setTitle("Shortest Path Finder");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initialize components
        startCityField = new JTextField(20);
        endCityField = new JTextField(20);
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        findPathButton = new JButton("Find Shortest Path");

        //Setup the panel for the form
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Starting City:"));
        inputPanel.add(startCityField);
        inputPanel.add(new JLabel("Destination City:"));
        inputPanel.add(endCityField);
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
                handlePathCalculation();
            }
        });

        initializeData();
    }

    private void initializeData() 
    {
        City istanbul = new City("İstanbul", 0);
        City ankara = new City("Ankara", 1);
        City izmir = new City("İzmir", 2);
        City bursa = new City("Bursa", 3);
        City adana = new City("Adana", 4);
        City gaziantep = new City("Gaziantep", 5);
        City konya = new City("Konya", 6);
        City diyarbakir = new City("Diyarbakır", 7);
        City antalya = new City("Antalya", 8);
        City mersin = new City("Mersin", 9);
        City kayseri = new City("Kayseri", 10);
        City sanliurfa = new City("Şanlıurfa", 11);
        City malatya = new City("Malatya", 12);
        City samsun = new City("Samsun", 13);
        City denizli = new City("Denizli", 14);
        City batman = new City("Batman", 15);
        City trabzon = new City("Trabzon", 16);

        bfsCities = new City[]{istanbul, ankara, izmir, bursa, adana, gaziantep, konya, diyarbakir, antalya, mersin, kayseri, sanliurfa, malatya, samsun, denizli, batman, trabzon};
        
        dfsCities = new String[]{"İstanbul", "Ankara", "İzmir", "Bursa", "Adana", "Gaziantep", "Konya", "Diyarbakır", "Antalya", "Mersin", "Kayseri", "Şanlıurfa", "Malatya", "Samsun", "Denizli", "Batman", "Trabzon"};
        
        adjacencyMatrix = new int[][]
        {
            {0, 449, 99999, 153, 99999, 99999, 645, 99999, 690, 956, 776, 99999, 99999, 737, 649, 99999, 99999},
            {449, 0, 591, 389, 484, 705, 266, 1003, 483, 501, 317, 848, 682, 402, 483, 99999, 732},
            {99999, 591, 0, 333, 898, 1118, 560, 99999, 451, 911, 874, 99999, 99999, 1003, 238, 99999, 99999},
            {153, 389, 333, 0, 856, 1075, 507, 99999, 546, 869, 715, 1212, 1049, 750, 480, 99999, 1091},
            {99999, 484, 898, 856, 0, 225, 346, 525, 649, 95, 307, 369, 389, 719, 758, 618, 851},
            {99999, 705, 1118, 1075, 225, 0, 568, 315, 785, 311, 357, 151, 251, 803, 974, 409, 838},
            {645, 266, 560, 507, 346, 568, 0, 866, 303, 360, 306, 702, 729, 663, 386, 964, 896},
            {99999, 1003, 99999, 99999, 525, 315, 866, 0, 99999, 610, 571, 182, 235, 803, 1276, 97, 586},
            {690, 483, 451, 546, 649, 785, 303, 99999, 0, 631, 610, 924, 99999, 99999, 217, 99999, 99999},
            {956, 501, 911, 869, 95, 311, 360, 610, 631, 0, 99999, 99999, 99999, 99999, 99999, 99999, 99999},
            {776, 317, 874, 715, 307, 357, 306, 571, 610, 99999, 0, 99999, 99999, 99999, 99999, 99999, 99999},
            {99999, 848, 99999, 1212, 369, 151, 702, 182, 924, 99999, 99999, 0, 99999, 99999, 99999, 99999, 99999},
            {99999, 682, 99999, 1049, 389, 251, 729, 235, 99999, 99999, 99999, 99999, 0, 99999, 99999, 99999, 99999},
            {737, 402, 1003, 750, 719, 803, 663, 803, 99999, 99999, 99999, 99999, 99999, 0, 99999, 858, 99999},
            {649, 483, 238, 480, 758, 974, 386, 1276, 217, 99999, 99999, 99999, 99999, 99999, 0, 99999, 99999},
            {99999, 99999, 99999, 99999, 618, 409, 964, 97, 99999, 99999, 99999, 99999, 99999, 858, 99999, 0, 99999},
            {99999, 732, 99999, 1091, 851, 838, 896, 586, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 0}
        };
    }

    // Handle path calculation and display results
    private void handlePathCalculation() 
    {
        String startCityName = startCityField.getText().trim();
        String endCityName = endCityField.getText().trim();
        
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
            String dfsResult = "DFS Shortest Path: " + Arrays.toString(dfsShortestPath) + "\nDistance: " + dfsAlgorithm.calculatePathDistance(dfsShortestPath) + "km\nExecution Time: " + (dfsEndTime - dfsStartTime) / 1_000_000.0 + " milliseconds\n";
            
            // Perform BFS
            long bfsStartTime = System.nanoTime();
            Path bfsPath = bfsAlgorithm.BFSPathFinder(startBFS, endBFS);
            long bfsEndTime = System.nanoTime();
            String bfsResult = "BFS Shortest Path: " + bfsPath + "\nExecution Time: " + (bfsEndTime - bfsStartTime) / 1_000_000.0 + " milliseconds\n";
            
            resultArea.setText(dfsResult + "\n" + bfsResult);
        } 
        catch (CityNotFoundException e) 
        {
            resultArea.setText(e.getMessage());
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
            ProgramUI ui = new ProgramUI();
            ui.setVisible(true);
        });
    }
}
