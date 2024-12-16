import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
public class WeatherModeling extends JPanel {
    private double a, b, c;
    public WeatherModeling(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int originX = width / 2;  
        int originY = height / 2; 
        int scale = 10;
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, originY, width, originY);  
        g2d.drawLine(originX, 0, originX, height);
        g2d.setColor(Color.GREEN);
        for (int x = -originX / scale; x < originX / scale; x++) {
            double y1 = a * Math.pow(x, 2) + b * x + c;
            double y2 = a * Math.pow(x + 1, 2) + b * (x + 1) + c;
            int scaledX1 = originX + x * scale;
            int scaledY1 = originY - (int) (y1 * scale);
            int scaledX2 = originX + (x + 1) * scale;
            int scaledY2 = originY - (int) (y2 * scale);
            g2d.drawLine(scaledX1, scaledY1, scaledX2, scaledY2);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JFrame frame = new JFrame("Weather Modeling - Quadratic Graph");
        System.out.println("Stage 1: Hard-coded example");
        double a = -1, b = 3, c = 5; // Hardcoded coefficients
        frame.add(new WeatherModeling(a, b, c));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame, "Close this graph to proceed to the next stage.");
        System.out.println("Stage 2: Keyboard Input");
        System.out.print("Enter coefficients (a, b, c): ");
        a = scanner.nextDouble();
        b = scanner.nextDouble();
        c = scanner.nextDouble();
        frame.getContentPane().removeAll();
        frame.add(new WeatherModeling(a, b, c));
        frame.revalidate();
        frame.repaint();
        JOptionPane.showMessageDialog(frame, "Close this graph to proceed to the next stage.");
        System.out.println("Stage 3: File Input");
        System.out.print("Enter the filename containing coefficients: ");
        String filename = scanner.next();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coefficients = line.split(",");
                a = Double.parseDouble(coefficients[0].trim());
                b = Double.parseDouble(coefficients[1].trim());
                c = Double.parseDouble(coefficients[2].trim());
                frame.getContentPane().removeAll(); 
                frame.add(new WeatherModeling(a, b, c));
                frame.revalidate();
                frame.repaint();
                JOptionPane.showMessageDialog(frame, "Close this graph to proceed to the next set of inputs.");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid file format. Ensure coefficients are in the format: a,b,c");
        }

        scanner.close();
    }
}