package tw.tension.effective.java.learning;

/**
 * Created by Rickey Huang on 2016/6/24.
 */
public class Circle extends Shape {

    private float radius;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;

        Circle circle = (Circle) o;

        return Float.compare(circle.radius, radius) == 0;

    }

    @Override
    public int hashCode() {
        return (radius != +0.0f ? Float.floatToIntBits(radius) : 0);
    }
}
