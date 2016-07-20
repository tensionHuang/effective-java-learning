package tw.tension.effective.java.learning;

/**
 * Created by Rickey Huang on 2016/6/24.
 */
public class Rectangle extends Shape {
    
    private float length;
    
    private float width;

    public Rectangle(float length, float width) {
        this.length = length;
        this.width = width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        // add 0 == null condition to follow the non-nullity contract to prevent NullPointerException
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;

        if (Float.compare(rectangle.length, length) != 0) return false;
        
        return Float.compare(rectangle.width, width) == 0;

    }

    @Override
    public int hashCode() {
        
        int result = (length != +0.0f ? Float.floatToIntBits(length) : 0);
        
        result = 31 * result + (width != +0.0f ? Float.floatToIntBits(width) : 0);
        
        return result;
    }
}
