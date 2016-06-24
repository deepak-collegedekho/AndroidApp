package com.collegedekho.app.display.androidcharts.diagram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.collegedekho.app.display.androidcharts.series.TitleValueEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SpiderWebChart extends RoundChart {


    public static final int DEFAULT_LONGITUDE_NUM = 5;

    public static final boolean DEFAULT_DISPLAY_LATITUDE = true;

    public static final int DEFAULT_LATITUDE_NUM = 5;

    public static final int DEFAULT_LATITUDE_COLOR = Color.BLACK;

    public static final int[] COLORS = {0xffff6f00, Color.BLUE, Color.YELLOW};

    protected List<List<TitleValueEntity>> data;

    protected int longitudeNum = DEFAULT_LONGITUDE_NUM;


    protected boolean displayLatitude = DEFAULT_DISPLAY_LATITUDE;
    protected int latitudeNum = DEFAULT_LATITUDE_NUM;

    protected int latitudeColor = DEFAULT_LATITUDE_COLOR;

    protected int backgroundColor = DEFAULT_BACKGROUND_COLOR;
    float fontSize=26f;

    public SpiderWebChart(Context context) {
        super(context);
    }

    public SpiderWebChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SpiderWebChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // get safe rect
        int rect = super.getHeight();

        // calculate longitude length
        longitudeLength = (int) ((rect / 2f) * 0.8);

        // calculate position
        position = new Point((int) (super.getWidth() / 2f),
                (int) (super.getHeight() / 2f + 0.2 * longitudeLength));

        // draw this chart
        drawWeb(canvas);

        // draw mData on chart
        drawData(canvas);
    }

    protected List<PointF> getWebAxisPoints(float pos) {
        List<PointF> points = new ArrayList<PointF>();
        for (int i = 0; i < longitudeNum; i++) {
            PointF pt = new PointF();
            float offsetX = (float) (position.x - longitudeLength * pos
                    * Math.sin(i * 2 * Math.PI / longitudeNum));
            float offsetY = (float) (position.y - longitudeLength * pos
                    * Math.cos(i * 2 * Math.PI / longitudeNum));
            pt.set(offsetX, offsetY);

            points.add(pt);
        }
        return points;
    }

    protected List<PointF> getDataPoints(List<TitleValueEntity> data) {
        List<PointF> points = new ArrayList<PointF>();
        for (int i = 0; i < longitudeNum; i++) {
            PointF pt = new PointF();
            float offsetX = (float) (position.x - data.get(i).getValue() / 10f
                    * longitudeLength/2
                    * Math.sin(i * 2 * Math.PI / longitudeNum));
            float offsetY = (float) (position.y - data.get(i).getValue() / 10f
                    * longitudeLength/2
                    * Math.cos(i * 2 * Math.PI / longitudeNum));
            pt.set(offsetX, offsetY);

            points.add(pt);
        }
        return points;
    }

    protected void drawWeb(Canvas canvas) {
        Paint mPaintWebFill = new Paint();
        mPaintWebFill.setColor(0xff1f2560);
        mPaintWebFill.setAntiAlias(true);

//        Paint mPaintWebBorder = new Paint();
//        mPaintWebBorder.setColor(Color.WHITE);
//        mPaintWebBorder.setStyle(Style.STROKE);
//        mPaintWebBorder.setStrokeWidth(2);
//        mPaintWebBorder.setAntiAlias(true);

        Paint mPaintWebInnerBorder = new Paint();
        mPaintWebInnerBorder.setColor(Color.LTGRAY);
        mPaintWebInnerBorder.setStyle(Style.STROKE);
        mPaintWebInnerBorder.setAntiAlias(true);

        Paint mPaintLine = new Paint();
        mPaintLine.setColor(Color.LTGRAY);

        Paint mPaintFont = new Paint();
        mPaintFont.setColor(Color.BLACK);
        mPaintFont.setTextSize(fontSize);
        mPaintFont.setFakeBoldText(true);
        mPaintFont.setAntiAlias(true);
        Path mPath = new Path();
        List<PointF> pointList = getWebAxisPoints(1);

        // draw border
        if (null != data) {
            for (int i = 0; i < pointList.size(); i++) {
                PointF pt = pointList.get(i);
                if (i == 0) {
                    mPath.moveTo(pt.x, pt.y);
                } else {
                    mPath.lineTo(pt.x, pt.y);
                }
                Rect bounds = new Rect();
                mPaintFont.getTextBounds(title, 0, 1, bounds);
                // draw title
                String title = data.get(0).get(i).getTitle();
                float realx = 0;
                float realy = 0;

                // TODO title position  
                if (pt.x < position.x) {
                    realx = pt.x - mPaintFont.measureText(title) - 5;
                } else if (pt.x > position.x) {
                    realx = pt.x + 5;
                } else {
                    realx = pt.x - mPaintFont.measureText(title) / 2;
                }

                if (pt.y > position.y) {
                    realy = pt.y + bounds.height();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  ;
                } else if (pt.y < position.y) {
                    realy = pt.y - 4;
                } else {
                    realy = pt.y - 12;
                }

                canvas.drawText(title, realx, realy, mPaintFont);
            }
        }
        mPath.close();
        canvas.drawPath(mPath, mPaintWebFill);
//        canvas.drawPath(mPath, mPaintWebBorder);

        // draw spider web
        for (int j = 1; j < latitudeNum; j++) {

            Path mPathInner = new Path();
            List<PointF> pointListInner = getWebAxisPoints(j * 1f / latitudeNum);

            for (int i = 0; i < pointListInner.size(); i++) {
                PointF pt = pointListInner.get(i);
                if (i == 0) {
                    mPathInner.moveTo(pt.x, pt.y);
                } else {
                    mPathInner.lineTo(pt.x, pt.y);
                }
            }
            mPathInner.close();
            canvas.drawPath(mPathInner, mPaintWebInnerBorder);
        }

        // draw longitude lines
        for (int i = 0; i < pointList.size(); i++) {
            PointF pt = pointList.get(i);
            canvas.drawLine(position.x, position.y, pt.x, pt.y, mPaintLine);
        }
    }

    protected void drawData(Canvas canvas) {
        if (null != data) {
            for (int j = 0; j < data.size(); j++) {
                List<TitleValueEntity> list = data.get(j);

                Paint mPaintFill = new Paint();
                mPaintFill.setColor(COLORS[j]);
                mPaintFill.setStyle(Style.FILL);
                mPaintFill.setAntiAlias(true);
                mPaintFill.setAlpha(99);

                Paint mPaintBorder = new Paint();
                mPaintBorder.setColor(COLORS[j]);
                mPaintBorder.setStyle(Style.STROKE);
                mPaintBorder.setStrokeWidth(2);
                mPaintBorder.setAntiAlias(true);

                // paint to draw fonts
                Paint mPaintFont = new Paint();
                mPaintFont.setColor(Color.WHITE);

                // paint to draw points
                Paint mPaintPoint = new Paint();
                mPaintPoint.setColor(COLORS[j]);

                Path mPath = new Path();

                // get points to draw
                List<PointF> pointList = getDataPoints(list);
                // initialize path
                for (int i = 0; i < pointList.size(); i++) {
                    PointF pt = pointList.get(i);
                    if (i == 0) {
                        mPath.moveTo(pt.x, pt.y);
                    } else {
                        mPath.lineTo(pt.x, pt.y);
                    }
                    canvas.drawCircle(pt.x, pt.y, 5, mPaintPoint);
                }
                mPath.close();
                canvas.drawPath(mPath, mPaintFill);
                canvas.drawPath(mPath, mPaintBorder);
            }
        }
    }

    public List<List<TitleValueEntity>> getData() {
        return data;
    }

    public void setData(List<List<TitleValueEntity>> data) {
        this.data = data;
        if(data!=null && data.get(0)!=null) {
            long seed = System.nanoTime();
            Collections.shuffle(data.get(0), new Random(seed));
        }
    }

    public int getLongitudeNum() {
        return longitudeNum;
    }

    public void setLongitudeNum(int longitudeNum) {
        this.longitudeNum = longitudeNum;
    }

    public boolean isDisplayLatitude() {
        return displayLatitude;
    }

    public void setDisplayLatitude(boolean displayLatitude) {
        this.displayLatitude = displayLatitude;
    }

    public int getLatitudeNum() {
        return latitudeNum;
    }

    public void setLatitudeNum(int latitudeNum) {
        this.latitudeNum = latitudeNum;
    }

    public int getLatitudeColor() {
        return latitudeColor;
    }

    public void setLatitudeColor(int latitudeColor) {
        this.latitudeColor = latitudeColor;
    }

}
