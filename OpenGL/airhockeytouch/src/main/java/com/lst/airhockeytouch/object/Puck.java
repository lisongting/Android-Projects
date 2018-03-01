package com.lst.airhockeytouch.object;


import com.lst.airhockeytouch.VertexArray;
import com.lst.airhockeytouch.program.ColorShaderProgram;
import com.lst.airhockeytouch.util.Geometry;

import java.util.List;

/**
 * Created by lisongting on 2018/2/28.
 */

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;
    public final float radius,height;
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck) {
        ObjectBuilder.GeneratedData generatedData =
                ObjectBuilder.createdPuck(new Geometry.Cylinder(new Geometry.Point(0f, 0f, 0f), radius, height), numPointsAroundPuck);
        this.radius = radius;
        this.height = height;
        vertexArray = new VertexArray(generatedData.vertexData);

        drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }

    public void draw(){
        for (ObjectBuilder.DrawCommand draw : drawList) {
            draw.draw();
        }
    }
}
