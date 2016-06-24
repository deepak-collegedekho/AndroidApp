package com.collegedekho.app.display.androidcharts.diagram;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;


public class RoundChart extends AbstractChart {
    
    public static final String DEFAULT_TITLE = "Round Chart";

    public static final boolean DEFAULT_DISPLAY_LONGITUDE = true;

    public static final int DEFAULT_LONGITUDE_LENGTH = 80;

    public static final int DEFAULT_LONGITUDE_COLOR = Color.WHITE;

    public static final int DEFAULT_CIRCLE_BORDER_COLOR = Color.WHITE;
    
    public static final int DEFAULT_CIRCLE_BORDER_WIDTH = 4;

    public static final Point DEFAULT_POSITION = new Point(0, 0);
   
    protected String title = DEFAULT_TITLE;

    protected Point position = DEFAULT_POSITION;

    protected float longitudeLength = DEFAULT_LONGITUDE_LENGTH;

    protected int longitudeColor = DEFAULT_LONGITUDE_COLOR;

    protected int circleBorderColor = DEFAULT_CIRCLE_BORDER_COLOR;
    
    protected int circleBorderWidth = DEFAULT_CIRCLE_BORDER_WIDTH;

    protected boolean displayLongitude = DEFAULT_DISPLAY_LONGITUDE;
    
    public RoundChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public RoundChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     */
    public RoundChart(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * @return the longitudeLength
     */
    public float getLongitudeLength() {
        return longitudeLength;
    }

    public void setLongitudeLength(float radiusLength) {
        this.longitudeLength = radiusLength;
    }

    /**
     * @return the longitudeColor
     */
    public int getLongitudeColor() {
        return longitudeColor;
    }

    public void setLongitudeColor(int radiusColor) {
        this.longitudeColor = radiusColor;
    }

    /**
     * @return the circleBorderColor
     */
    public int getCircleBorderColor() {
        return circleBorderColor;
    }

    /**
     * @param circleBorderColor
     *            the circleBorderColor to set
     */
    public void setCircleBorderColor(int circleBorderColor) {
        this.circleBorderColor = circleBorderColor;
    }

    /**
     * @return the displayLongitude
     */
    public boolean isDisplayLongitude() {
        return displayLongitude;
    }

    public void setDisplayLongitude(boolean displayRadius) {
        this.displayLongitude = displayRadius;
    }

    /**
     * @return the circleBorderWidth
     */
    public int getCircleBorderWidth() {
        return circleBorderWidth;
    }

    /**
     * @param circleBorderWidth the circleBorderWidth to set
     */
    public void setCircleBorderWidth(int circleBorderWidth) {
        this.circleBorderWidth = circleBorderWidth;
    }

}
