import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.geom.Point2D;

class ZBuffer {
    private int width;
    private int height;
    private double[][] buffer;

    public ZBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.buffer = new double[width][height];
        clear();
    }

    public void clear() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buffer[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public boolean testAndSet(int x, int y, double z) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        if (z < buffer[x][y]) {
            buffer[x][y] = z;
            return true;
        }
        return false;
    }
}

class Object3D {
    List<Polygon3D> polygons;

    public Object3D() {
        polygons = new ArrayList<>();
        Point3D[] points = {
            new Point3D(50, 50, 50),
            new Point3D(50, 50, -50),
            new Point3D(50, -50, 50),
            new Point3D(50, -50, -50),
            new Point3D(-50, 50, 50),
            new Point3D(-50, 50, -50),
            new Point3D(-50, -50, 50),
            new Point3D(-50, -50, -50)
        };
for (Point3D point : points) {
            // Increase the size of the cube
            point.x *= 2.5;
            point.y *= 2.5;
            point.z *= 2.5;
        }
       polygons.add(new Polygon3D(Arrays.asList(points[0], points[1], points[3], points[2]), new Color(255, 223, 186))); // Pastel Orange
        polygons.add(new Polygon3D(Arrays.asList(points[4], points[5], points[7], points[6]), new Color(173, 216, 230))); // Pastel Blue
        polygons.add(new Polygon3D(Arrays.asList(points[0], points[1], points[5], points[4]), new Color(255, 182, 193))); // Pastel Pink
        polygons.add(new Polygon3D(Arrays.asList(points[2], points[3], points[7], points[6]), new Color(152, 251, 152))); // Pastel Green
        polygons.add(new Polygon3D(Arrays.asList(points[0], points[2], points[6], points[4]), new Color(221, 160, 221))); // Pastel Peach
        polygons.add(new Polygon3D(Arrays.asList(points[1], points[3], points[7], points[5]), new Color(221, 160, 221))); // Pastel Purple
    }
}

class Polygon3D {
    List<Point3D> points;
    Color color;

    public Polygon3D(List<Point3D> points, Color color) {
        this.points = points;
        this.color = color;
    }
}

class Point3D {
    int x;
    int y;
    int z;

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

class Cube3DAnimation extends JPanel {
    private int prevX, prevY;
    private double alpha = 0.0;
    private double beta = 0.0;
    private Object3D cube;
    private ZBuffer zBuffer;

    private boolean isWireframe = false;

    public Cube3DAnimation() {
        cube = new Object3D();
        zBuffer = new ZBuffer(400, 400); 

        cube = new Object3D();
        class Object3D {
            List<Polygon3D> polygons;
        
            public Object3D() {
                polygons = new ArrayList<>();
                Point3D[] points = {
                        new Point3D(50, 50, 50),
            new Point3D(50, 50, -50),
            new Point3D(50, -50, 50),
            new Point3D(50, -50, -50),
            new Point3D(-50, 50, 50),
            new Point3D(-50, 50, -50),
            new Point3D(-50, -50, 50),
            new Point3D(-50, -50, -50)
                };
                for (Point3D point : points) {
                    // Increase the size of the cube
                    point.x *= 2.5;
                    point.y *= 2.5;
                    point.z *= 2.5;
                }
                polygons.add(new Polygon3D(Arrays.asList(points[0], points[1], points[3], points[2]), new Color(255, 223, 186))); // Pastel Orange
        polygons.add(new Polygon3D(Arrays.asList(points[4], points[5], points[7], points[6]), new Color(173, 216, 230))); // Pastel Blue
        polygons.add(new Polygon3D(Arrays.asList(points[0], points[1], points[5], points[4]), new Color(255, 182, 193))); // Pastel Pink
        polygons.add(new Polygon3D(Arrays.asList(points[2], points[3], points[7], points[6]), new Color(152, 251, 152))); // Pastel Green
        polygons.add(new Polygon3D(Arrays.asList(points[0], points[2], points[6], points[4]), new Color(221, 160, 221))); // Pastel Peach
        polygons.add(new Polygon3D(Arrays.asList(points[1], points[3], points[7], points[5]), new Color(221, 160, 221))); // Pastel Purple
            }
        }
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.getButton() == MouseEvent.BUTTON3) {
                    toggleRenderingMode();
                    repaint();
                } 
                else {
                    prevX = me.getX();
                    prevY = me.getY();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent me) {
                int x = me.getX();
                int y = me.getY();
                alpha -= (y - prevY) * 0.01;  // Reverse the sign
                beta -= (x - prevX) * 0.01;   // Reverse the sign
                repaint();
                prevX = x;
                prevY = y;
            }
        });
    }

    private double[][] rotationX(double theta) {
        double[][] rotationMatrixX = {
                {1, 0, 0, 0},
                {0, Math.cos(theta), -Math.sin(theta), 0},
                {0, Math.sin(theta), Math.cos(theta), 0},
                {0, 0, 0, 1}
        };
        return rotationMatrixX;
    }

    private double[][] rotationY(double theta) {
        double[][] rotationMatrixY = {
                {Math.cos(theta), 0, Math.sin(theta), 0},
                {0, 1, 0, 0},
                {-Math.sin(theta), 0, Math.cos(theta), 0},
                {0, 0, 0, 1}
        };
        return rotationMatrixY;
    }

    private double[][] translate(double tx, double ty, double tz) {
        double[][] translationMatrix = {
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        };
        return translationMatrix;
    }

    private double[][] projection(double d) {
        double[][] projectionMatrix = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 1 / d, 0}
        };
        return projectionMatrix;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Image buffer = createImage(getWidth(), getHeight());
        Graphics2D bufG = (Graphics2D) buffer.getGraphics();

        zBuffer.clear(); 

        List<Polygon3D> sortedPolygons = new ArrayList<>(cube.polygons);
        sortedPolygons.sort((p1, p2) -> {
            int z1 = (p1.points.get(0).z + p1.points.get(1).z + p1.points.get(2).z + p1.points.get(3).z) / 4;
            int z2 = (p2.points.get(0).z + p2.points.get(1).z + p2.points.get(2).z + p2.points.get(3).z) / 4;
            return Integer.compare(z2, z1);
        });

        for (Polygon3D polygon : sortedPolygons) {
            int[] x = new int[4];
            int[] y = new int[4];
            int i = 0;
            for (Point3D point : polygon.points) {
                
                double[][] pointMatrix = {{point.x}, {point.y}, {point.z}, {1}};
                double[][] rotationXMatrix = rotationX(alpha);
                double[][] rotationYMatrix = rotationY(beta);
                double[][] translationMatrix = translate(0, 0, 0);
                double[][] projectionMatrix = projection(1);

                double[][] tempMatrix = Matrix.multiply(rotationXMatrix, pointMatrix);
                tempMatrix = Matrix.multiply(rotationYMatrix, tempMatrix);
                tempMatrix = Matrix.multiply(translationMatrix, tempMatrix);
                tempMatrix = Matrix.multiply(projectionMatrix, tempMatrix);

                int x1 = (int) tempMatrix[0][0];
                int y1 = (int) tempMatrix[1][0];
                x[i] = 200 + x1;
                y[i] = 200 - y1;
                i++;
            }
            
            // Z-buffering algorithm implementation
            for (int px = 0; px < getWidth(); px++) {
                for (int py = 0; py < getHeight(); py++) {
                    if (isInsidePolygonProjection(px, py, x, y)) {
                        double z = calculateDepth(px, py, x, y, polygon);
                        if (zBuffer.testAndSet(px, py, z)) {
                            if (!isWireframe) {
                                bufG.setColor(polygon.color);
                                bufG.fillRect(px, py, 1, 1);
                            }
                        }
                    }
                }
            }

            if (isWireframe) {
                bufG.setColor(polygon.color);
                bufG.drawPolygon(x, y, 4);
            }
        }

        g2d.drawImage(buffer, 0, 0, this);
    }

    private boolean isInsidePolygonProjection(int px, int py, int[] x, int[] y) {
        boolean inside = false;
        for (int i = 0, j = x.length - 1; i < x.length; j = i++) {
            if (((y[i] > py) != (y[j] > py)) &&
                    (px < (x[j] - x[i]) * (py - y[i]) / (y[j] - y[i]) + x[i])) {
                inside = !inside;
            }
        }
        return inside;
    }

    private double calculateDepth(int px, int py, int[] x, int[] y, Polygon3D polygon) {
        Point2D.Double p = new Point2D.Double(px, py);
        Point2D.Double a = new Point2D.Double(x[0], y[0]);
        Point2D.Double b = new Point2D.Double(x[1], y[1]);
        Point2D.Double c = new Point2D.Double(x[2], y[2]);

        double area = 0.5 * (-b.getY() * c.getX() + a.getY() * (-b.getX() + c.getX()) + a.getX() * (b.getY() - c.getY()) + b.getX() * c.getY());
        double sign = area < 0 ? -1 : 1;
        double s = (a.getY() * c.getX() - a.getX() * c.getY() + (c.getY() - a.getY()) * p.getX() + (a.getX() - c.getX()) * p.getY()) * sign;
        double t = (a.getX() * b.getY() - a.getY() * b.getX() + (a.getY() - b.getY()) * p.getX() + (b.getX() - a.getX()) * p.getY()) * sign;

        double d1 = polygon.points.get(0).z;
        double d2 = polygon.points.get(1).z;
        double d3 = polygon.points.get(2).z;
        return (1 - s - t) * d1 + s * d2 + t * d3;
    }

    public void toggleRenderingMode() {
        isWireframe = !isWireframe;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cube 3D Rotation");
        Cube3DAnimation cube3DAnimation = new Cube3DAnimation();
        frame.add(cube3DAnimation);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Matrix {
    public static double[][] multiply(double[][] a, double[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;
        double[][] result = new double[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
}