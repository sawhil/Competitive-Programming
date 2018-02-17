package geometry;

public class Circle {   //equation: (x-c.x)^2 + (y-c.y)^2 = r^2
// Construct a circle from 3 points X, Y, Z by getting the intersection of the bisectors of lines X-Y, Y-Z

static final double EPS = 1e-9;

Point c;
double r;

Circle(Point p, double k) {
        c = p; r = k;
}
Circle (Point cc, Point a, Point b) { // lineSegment1 (cc=>a), lineSegment2 (cc=>b)
        Point midCA = cc.translate(new Vector(cc, a).normalize().scale(cc.dist(a) / 2));
        Point midCB = cc.translate(new Vector(cc, b).normalize().scale(cc.dist(b) / 2));
        Line perpendicularCA = new Line(midCA, cc.rotate(degToRad(90), midCA));
        Line perpendicularCB = new Line(midCB, cc.rotate(degToRad(90), midCB));
        c = perpendicularCA.intersect(perpendicularCB);
        if (c == null) {     // handle this
                c = new Point(0, 0);
                r = 1e9;
        }
        else r = c.dist(cc);
}

int inside(Point p)   //1 for inside, 0 for border, -1 for outside
{
        double d = p.dist(c);

        return d + EPS < r ? 1 : Math.abs(d - r) < EPS ? 0 : -1;
}

double circum() {
        return 2 * Math.PI * r;
}

double area() {
        return Math.PI * r * r;
}

double arcLength(double deg) {
        return deg / 360.0 * circum();
}                                                                   //major and minor chords exist

double chordLength(double deg)
{
        return 2 * r * Math.sin(Geometry.degToRad(deg) / 2.0);
}

double chordLength (int deg)
{
        return 2 * r * r * (1 - Math.cos(deg));
}

double sectorArea(double deg) {
        return deg / 360.0 * area();
}

double segmentArea(double deg)
{
        return sectorArea(deg) - r * r * Math.sin(Geometry.degToRad(deg)) / 2.0;
}

boolean intersect(Circle cir)
{
        return c.dist(cir.c) <= r + cir.r + EPS && c.dist(cir.c) + EPS >= Math.abs(r - cir.r);
}
//returns true if the circle intersects with the line segment defined by p and q at one or two points
boolean intersect(Point p, Point q)
{
        Line l = new Line(p, q);
        if(Math.abs(l.b) < EPS)
        {
                if(l.c * l.c > r * r + EPS)
                        return false;

                double y1 = Math.sqrt(Math.abs(r * r - l.c * l.c)), y2 = -y1;
                return new Point(-l.c, y1).between(p, q) && new Point(-l.c, y2).between(p, q);
        }
        double a = l.a * l.a + 1, b = 2 * l.a * l.c, c = l.c * l.c - r * r;
        if(b * b - 4 * a * c + EPS < 0)
                return false;

        double dis = b * b - 4 * a * c;

        double x1 = (-b + Math.sqrt(dis)) / (2.0 * a), x2 = (-b - Math.sqrt(dis)) / (2.0 * a);

        return new Point(x1, -l.a * x1 - l.c).between(p, q) || new Point(x2, -l.a * x2 - l.c).between(p, q);
}

static Point findCenter(Point p, Point q, double r)     //for the other center, swap p and q
{
        double d2 = (p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y);
        double det = r * r / d2 - 0.25;
        if(Math.abs(det) < EPS)
                det = 0.0;
        if(det < 0.0)
                return null;
        double h = Math.sqrt(det);
        return new Point((p.x + q.x) / 2.0 + (p.y - q.y) * h, (p.y + q.y) / 2.0 + (q.x - p.x) * h);
}

}

// p = center of circle, q = point on the circumference, returns the angle between ( (Xc+Radius),Yc ) and q
static double angle(Point p, Point q)
{
        double x = q.x - p.x;
        double y = q.y - p.y;
        double res = Math.atan2(y, x);
        if (res < 0) res += 180;
        return res;
}
