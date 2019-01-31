package com.ks1c.gdxdemo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Light {

    private static final float MAX_RADIUS = 500;
    private final Vector2 position;
    private float radius;
    //private final Color color;
    private List<Circle> circles = new ArrayList<Circle>(LightRenderer.MAX_INTERACTIONS);

    public Light(Vector2 position, float radius) {

        this.position = position;
        //this.color = color;

        float grantedRadius = 50;

        if (radius - grantedRadius > 0) {
            if (radius <= MAX_RADIUS) {
                this.radius = radius - grantedRadius;
            } else this.radius = MAX_RADIUS - grantedRadius;
        }

        float radiusRatio = this.radius / LightRenderer.MAX_INTERACTIONS;

        float i = 0;
        while (i < LightRenderer.MAX_INTERACTIONS) {
            circles.add(new Circle(position.x, position.y, grantedRadius + i * radiusRatio));
            i++;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }

   /* public Color getColor() {
        return color;
    }*/

    public Circle getCircle(int index) {
        return circles.get(index);
    }
}
