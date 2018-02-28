package com.lst.airhockeyimprovemallet.object;


import com.lst.airhockeyimprovemallet.VertexArray;
import com.lst.airhockeyimprovemallet.program.ColorShaderProgram;
import com.lst.airhockeyimprovemallet.util.Geometry;

import java.util.List;

/**
 * Created by lisongting on 2018/2/27.
 */

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private final VertexArray vertexArray;

    public final float radius,height;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Mallet(float radius, float height, int numPointsAroundMallet) {
        ObjectBuilder.GeneratedData generatedData
        = ObjectBuilder.createMallet(new Geometry.Point(0f, 0f, 0f), radius, height, numPointsAroundMallet);
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
