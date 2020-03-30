public class MyFirstProgram {
Point p2;
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Point p1 = new Point(10,10);
        Point p2 = new Point(15,15);
        System.out.println("Distance between points = " + p1.distance(p2));
    }
}
/* Point p1 = new Point();
   Point p2 = new Point();
   p1.x1 = 10;
   p1.y1 = 10;
   p2.y2 = 15;
   p2.x2 = 15;
   public static double distance (Point p1, Point p2) {
        double dist = Math.sqrt((p2.x2 - p1.x1) * (p2.x2 - p1.x1) + (p2.y2 - p1.y1) * (p2.y2 - p1.x1));
        return dist;
 */