import java.awt.*;
import javax.swing.*;

public class MainApplication extends JFrame {
    private Graph transportationGraph;
    private GraphicalRepresentation graphPanel;

    public MainApplication() {
        setTitle("Public Transportation Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        initializeGraph(); // Setup the transportation graph
        setupGraphicalPanel(); // Setup the panel that displays the graph
        setupControlPanel(); // Setup additional control elements if needed
    }

    private void initializeGraph() {
        transportationGraph = new Graph();
        transportationGraph.addEdge("Station 1", "Station 2", 10, "Bus");
        transportationGraph.addEdge("Station 2", "Station 3", 20, "Train");
        transportationGraph.addEdge("Station 4", "Station 5", 5, "Bus");
        transportationGraph.addEdge("Station 1", "Station 5", 20, "Bus");
        transportationGraph.addEdge("Station 2", "Station 4", 10, "Bus");
        transportationGraph.addEdge("Station 3", "Station 5", 10, "Bus");
        transportationGraph.addEdge("Station 5", "Station 6", 5, "Bus");
        transportationGraph.addEdge("Station 6", "Station 7", 15, "Bus");
        transportationGraph.addEdge("Station 3", "Station 7", 5, "Bus");
        // Add more stations and edges as needed
    }

    private void setupGraphicalPanel() {
        graphPanel = new GraphicalRepresentation(transportationGraph);
        add(graphPanel, BorderLayout.CENTER);
    }

    private void setupControlPanel() {
        JPanel controlPanel = new JPanel();
        JButton resetButton = new JButton("Reset");
        controlPanel.add(resetButton);
        add(controlPanel, BorderLayout.SOUTH);

        resetButton.addActionListener(e -> {
            graphPanel.resetSelections();
            graphPanel.repaint();
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new MainApplication().setVisible(true);
        });
    }
}
