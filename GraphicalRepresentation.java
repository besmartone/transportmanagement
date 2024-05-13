import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class GraphicalRepresentation extends JPanel {
    private Graph graph;
    private Map<String, Point> locations; // Holds the coordinates for each station
    private String startStation, endStation;

    private List<Point> pathPoints;
    private int currentStep = 0;
    private Timer animationTimer;

    private boolean isAnimating = false; // Animation state flag
    private Point movingDotPosition; // Variable to track the moving dot's position

    public GraphicalRepresentation(Graph graph) {
        this.graph = graph;
        this.locations = new HashMap<>();
        initializeLocations(); // Manually set locations based on your specific layout
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selectedStation = getStationNearClick(e.getPoint());
                if (selectedStation != null) {
                    if (startStation == null) {
                        startStation = selectedStation;
                        JOptionPane.showMessageDialog(null, "Start station selected: " + startStation);
                    } else {
                        endStation = selectedStation;
                        if (!startStation.equals(endStation)) {
                            List<String> path = DijkstraAlgorithm.findShortestPath(graph, startStation, endStation);
                            if (path.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "No Route Available");
                            } else {
                                preparePathAnimation(path);
                                
                                JOptionPane.showMessageDialog(null, "Best Path: " + String.join(" -> ", path));
                                
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Select a different end station than the start station.");
                        }
                        resetSelections();
                        repaint();  // Update the panel to remove any visual indicators if used
                    }
                }
            }            

        });
    }



    public void preparePathAnimation(List<String> path) {
        pathPoints = new ArrayList<>();
        for (String station : path) {
            Point loc = locations.get(station);
            if (loc != null) {
                pathPoints.add(loc);
            } else {
                System.err.println("Error: Station not found in locations - " + station);
                return; // Exit if any station in the path is not found
            }
        }
        if (!pathPoints.isEmpty()) {
            currentStep = 0;
            isAnimating = true; // Set flag to true to start animation
            if (animationTimer != null) {
                animationTimer.stop(); // Ensure no previous timer is running
            }
            startAnimation();
        }
    }
    
    private void startAnimation() {
        if (animationTimer != null) {
            animationTimer.stop(); // Stop any existing timer
        }
        movingDotPosition = new Point(pathPoints.get(0).x, pathPoints.get(0).y); // Initialize moving dot at the start position
        animationTimer = new Timer(50, e -> { // Faster timer for smoother animation
            if (currentStep < pathPoints.size() - 1) {
                Point start = pathPoints.get(currentStep);
                Point end = pathPoints.get(currentStep + 1);
                interpolateMovement(start, end);
            } else {
                ((Timer) e.getSource()).stop(); // Stop the timer at the end of the path
                isAnimating = false; // Reset the flag after animation stops
                resetSelections();
            }
        });
        animationTimer.start();
    }
    
    private void interpolateMovement(Point start, Point end) {
        final int steps = 10; // Number of intermediate steps between points
        int dx = (end.x - start.x) / steps;
        int dy = (end.y - start.y) / steps;
    
        if (Math.abs(movingDotPosition.x - end.x) > Math.abs(dx) || Math.abs(movingDotPosition.y - end.y) > Math.abs(dy)) {
            movingDotPosition.x += dx;
            movingDotPosition.y += dy;
            repaint();
        } else {
            movingDotPosition.x = end.x; // Correct any rounding errors by setting to exact end point
            movingDotPosition.y = end.y;
            currentStep++; // Move to the next segment of the path
        }
    }
    
    
    private void initializeLocations() {
        // Example positions based on your layout
        locations.put("Station 1", new Point(100, 120));
        locations.put("Station 2", new Point(300, 70));
        locations.put("Station 3", new Point(500, 100));
        locations.put("Station 4", new Point(100, 300));
        locations.put("Station 5", new Point(400, 300));
        locations.put("Station 6", new Point(280, 480));
        locations.put("Station 7", new Point(580, 300));
        // Add other stations similarly
    }

    private String getStationNearClick(Point click) {
        final int CLICK_RADIUS = 10;
        for (Map.Entry<String, Point> entry : locations.entrySet()) {
            Point p = entry.getValue();
            if (click.distance(p) <= CLICK_RADIUS) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void resetSelections() {
        if (isAnimating) { // Only reset if animation was running
            startStation = null;
            endStation = null;
            pathPoints = null;
            currentStep = 0;
            isAnimating = false; // Reset the flag
            if (animationTimer != null) {
                animationTimer.stop();
                animationTimer = null;
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw all lines first
        g.setColor(Color.BLACK);
        for (Map.Entry<String, List<Edge>> entry : graph.getAllNodes().entrySet()) {
            Point from = locations.get(entry.getKey());
            for (Edge edge : entry.getValue()) {
                Point to = locations.get(edge.getDestination());
                g.drawLine(from.x, from.y, to.x, to.y);
                drawEdgeWeight(g, edge.getDistance(), from, to);
            }
        }


        // Draw all nodes
        g.setColor(Color.RED);
        for (Map.Entry<String, Point> entry : locations.entrySet()) {
            Point p = entry.getValue();
            g.fillOval(p.x - 10, p.y - 10, 20, 20);
            g.drawString(entry.getKey(), p.x - 5, p.y - 15);
        }
        
        if (isAnimating && movingDotPosition != null) {
            g.setColor(Color.BLUE);
            g.fillOval(movingDotPosition.x - 10, movingDotPosition.y - 10, 20, 20);
        }
    

    }

    private void drawEdgeWeight(Graphics g, int weight, Point from, Point to) {
        int midX = (from.x + to.x) / 2;
        int midY = (from.y + to.y) / 2;
        String weightStr = String.valueOf(weight);
        g.drawString(weightStr, midX, midY);
    }


}
