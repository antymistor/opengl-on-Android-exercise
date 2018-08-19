package com.example.lianxi2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer {
    private Context context;
    private ArrayList<Model> model=new ArrayList<>();
    private Point mCenterPoint;
    private Point eye = new Point(0, 0, -3);
    private Point up = new Point(0, 1, 0);
    private Point center = new Point(0, 0, 0);
    private float mScalef = 1;
    private float mDegreex = 0;
    private float mDegreey = 0;
    private float [] rotate=new float[3];

    public GLRenderer(Context con)
    {
        context=con;
    }

    public void setrot(int j,float r){
        rotate[j]=r;
    }
    public void setstlsourse(ArrayList<String> stlsourse)
    {
        try {
           int p= 0;
           for(p=0;p<stlsourse.size();p++)
           {model.add(new STLReader().parserBinStlInAssets(context, stlsourse.get(p)));}
            mCenterPoint = model.get(0).getCentrePoint();
            mScalef=0.5f/model.get(0).getR();
            model.get(0).movemodel(-mCenterPoint.x,-model.get(0).minY,-mCenterPoint.z);
            model.get(2).resetcolor(0.2f,0.8f,0);
            mCenterPoint = model.get(1).getCentrePoint();
            model.get(1).resetcolor(0.5f,0.7f,1);
            model.get(1).movemodel(-mCenterPoint.x,-model.get(1).minY,-mCenterPoint.z);
            model.get(2).movemodel(-model.get(2).maxX*3/31,-model.get(2).maxY/2,-model.get(2).maxZ/2);
            model.get(3).movemodel(-model.get(3).maxX*6/60.35f,-model.get(3).maxY/2,-model.get(3).maxZ/2);
           } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getscale(){return mScalef;}
    public void rotateX(float degree) {
        mDegreex = degree/200;
    }
    public void rotateY(float degree) {
        mDegreey = degree/200;
    }
    public void setscale(float scale){mScalef=scale;}
    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(1, 1, 1, 1);
        gl.glLoadIdentity();
       eye.x=-3*(float)Math.cos(-mDegreey)*(float)Math.sin(-mDegreex);
       eye.y=3*(float)Math.sin(-mDegreey);
       eye.z=-3*(float)Math.cos(-mDegreey)*(float)Math.cos(-mDegreex);
       up.y=3*(float)Math.cos(-mDegreey);
       up.x=0;
       up.z=0;
        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z);
        gl.glPushMatrix();
        gl.glPushMatrix();
        gl.glPushMatrix();
        gl.glScalef(mScalef, mScalef, mScalef);
        gl.glTranslatef(0, (model.get(0).maxY-model.get(0).minY)*0.2f,0);
        gl.glRotatef(rotate[0],0,1,0);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.get(0).getVertBuffer());
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, model.get(0).getColorsBuffer());
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.get(0).getFacetCount() * 3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);


        gl.glPopMatrix();
        gl.glScalef(mScalef, mScalef, mScalef);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.get(1).getVertBuffer());
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, model.get(1).getColorsBuffer());
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.get(1).getFacetCount() * 3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);


        gl.glPopMatrix();
        gl.glScalef(mScalef, mScalef, mScalef);
        gl.glRotatef(rotate[0],0,1,0);
        gl.glTranslatef((model.get(2).maxX-model.get(2).minX)*15/31,(model.get(2).maxY-model.get(2).minY)*5/3,0);
        gl.glRotatef(rotate[1],0,0,1);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.get(2).getVertBuffer());
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, model.get(2).getColorsBuffer());
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.get(2).getFacetCount() * 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        gl.glPopMatrix();
        gl.glScalef(mScalef, mScalef, mScalef);
        gl.glRotatef(rotate[0],0,1,0);
        gl.glTranslatef((model.get(2).maxX-model.get(2).minX)*15/31,(model.get(2).maxY-model.get(2).minY)*5/3,0);
        gl.glRotatef(rotate[1],0,0,1);
        gl.glTranslatef((model.get(3).maxX-model.get(3).minX)*50/66.35f,0,0);
        gl.glRotatef(rotate[2],0,0,1);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.get(3).getVertBuffer());
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, model.get(3).getColorsBuffer());
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.get(3).getFacetCount() * 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        gl.glFinish();

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION); // 设置投影矩阵
        gl.glLoadIdentity(); // 设置矩阵为单位矩阵，相当于重置矩阵
        GLU.gluPerspective(gl, 45.0f, ((float) width) / height, 1f, 100f);// 设置透视范围
        gl.glClearColor(0.00f,0.0f,0.0f,1.0f);
        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(1, 1, 1, 1);
        gl.glEnable(GL10.GL_DEPTH_TEST); // 启用深度缓存
        gl.glClearDepthf(1.0f); // 设置深度缓存值
        gl.glDepthFunc(GL10.GL_LEQUAL); // 设置深度缓存比较函数
        gl.glShadeModel(GL10.GL_SMOOTH);// 设置阴影模式GL_SMOOTH
       // float r = model.getR();
        //mScalef = 0.5f / r;
        mCenterPoint = model.get(0).getCentrePoint();
        enableMaterial(gl);
    }

   /* float[] ambient = {1.0f, 1.0f, 1.0f, 1.0f};
    float[] diffuse = {1.0f, 1.0f, 0.0f, 1.0f};
    float[] specular = {0.8f, 0.8f, 0.0f, 1.0f};
    float[] lightPosition = {0.0f,1.0f, 1.0f, 0.0f};*/
   float[] ambient = {0.9f, 0.9f, 0.9f, 1.0f,};
    float[] diffuse = {0.5f, 0.5f, 0.5f, 1.0f,};
    float[] specular = {1.0f, 1.0f, 1.0f, 1.0f,};
    float[] lightPosition = {0.5f, 0.5f, 0.5f, 0.0f,};

    public void openLight(GL10 gl) {

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, Util.floatToBuffer(ambient));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, Util.floatToBuffer(diffuse));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, Util.floatToBuffer(specular));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, Util.floatToBuffer(lightPosition));


    }

    float[] materialAmb = {0.4f, 0.4f, 1.0f, 1.0f,};
    float[] materialDiff = {1.0f, 1.0f, 1.0f, 1.0f,};
    float[] materialSpec = {1.0f, 0.0f, 0.0f, 1.0f,};

    public void enableMaterial(GL10 gl) {

        //材料对环境光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, Util.floatToBuffer(materialAmb));
        //散射光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, Util.floatToBuffer(materialDiff));
        //镜面光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, Util.floatToBuffer(materialSpec));

    }
}