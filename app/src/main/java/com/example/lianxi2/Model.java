package com.example.lianxi2;

import java.nio.FloatBuffer;

/**
 * Package com.hc.opengl
 * Created by HuaChao on 2016/7/28.
 */
public class Model {
    //三角面个数
    private int facetCount;
    //顶点坐标数组
    private float[] verts;
    //每个顶点对应的法向量数组
    private float[] vnorms;
    //每个三角面的属性信息
    private short[] remarks;

    private float[] colors;
    //顶点数组转换而来的Buffer
    private FloatBuffer vertBuffer;

    //每个顶点对应的法向量转换而来的Buffer
    private FloatBuffer vnormBuffer;

    private FloatBuffer colorsBuffer;
    //以下分别保存所有点在x,y,z方向上的最大值、最小值
    float maxX;
    float minX;
    float maxY;
    float minY;
    float maxZ;
    float minZ;

    //返回模型的中心点
    public Point getCentrePoint() {
        float cx = (maxX + minX) / 2;
        float cy = (maxY + minY) / 2;
        float cz = (maxZ + minZ) / 2;
        return new Point(cx, cy, cz);
    }

    //包裹模型的最大半径
    public float getR() {
        float dx = (maxX - minX)/2;
        float dy = (maxY - minY)/2;
        float dz = (maxZ - minZ)/2;
        float max = dx;
        if (dy > max)
            max = dy;
        if (dz > max)
            max = dz;
        return max;
    }

    public void movemodel(float px,float py ,float pz){
        int i=0;
        for(i=0;i<=facetCount*9-3;i=i+3){verts[i]=verts[i]+px;}
        for(i=1;i<=facetCount*9-2;i=i+3){verts[i]=verts[i]+py;}
        for(i=2;i<=facetCount*9-1;i=i+3){verts[i]=verts[i]+pz;}
        maxX+=px;minX+=px;maxY+=py;minY+=py;maxZ+=pz;minZ=+pz;
        vertBuffer = null;
        vertBuffer=Util.floatToBuffer(verts);
    }

    public void resetcolor(float r,float g,float b){
        int i=0;
        for(i=0;i<=facetCount*12-4;i=i+4){colors[i]=r;}
        for(i=1;i<=facetCount*12-3;i=i+4){colors[i]=g;}
        for(i=2;i<=facetCount*12-2;i=i+4){colors[i]=b;}
        for(i=2;i<=facetCount*12-1;i=i+4){colors[i]=1;}
        colorsBuffer = Util.floatToBuffer(colors);
    }
    public int getFacetCount() {
        return facetCount;
    }

    public void setFacetCount(int facetCount) {
        this.facetCount = facetCount;
    }

    public float[] getVerts() {
        return verts;
    }

    public void setVerts(float[] verts) {
        this.verts = verts;
        vertBuffer = Util.floatToBuffer(verts);
    }


    protected void setColors(float[] colors){
        this.colors=colors;
        colorsBuffer = Util.floatToBuffer(colors);
    }
    public FloatBuffer getVertBuffer() {

        return vertBuffer;
    }

    public FloatBuffer getVnormBuffer() {
        return vnormBuffer;
    }

    public FloatBuffer getColorsBuffer(){
        return colorsBuffer;
    }
    public float[] getVnorms() {
        return vnorms;
    }

    public float[] getcolors(){
        return colors;
    }
    public void setVnorms(float[] vnorms) {
        this.vnorms = vnorms;
        vnormBuffer = Util.floatToBuffer(vnorms);
    }

    public short[] getRemarks() {
        return remarks;
    }

    public void setRemarks(short[] remarks) {
        this.remarks = remarks;
    }


}
