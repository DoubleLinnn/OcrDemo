package com.hellochartsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends Activity {
    //折线图
    private LineChartView chart;
    //折线图数据
    private LineChartData data;
    //线的数量
    private int numberOfLines = 1;
    //最大的线的数量
    private int maxNumberOfLines = 4;
    //点的个数（横坐标的数量）
    private int numberOfPoints = 12;

    //水质数据
    private float[][] water_data = new float[maxNumberOfLines][numberOfPoints];

    //是否有数据轴
    private boolean hasAxes = true;
    //是否有数据轴名字
    private boolean hasAxesNames = true;
    //是否显示线条
    private boolean hasLines = true;
    //是否显示点
    private boolean hasPoints = true;
    //点的形状（圆形，正方形）
    private ValueShape shape = ValueShape.CIRCLE;
    //填充线下面的部分
    private boolean isFilled = false;
    //每个点显示数值
    private boolean hasLabels = false;
    //曲线圆滑
    private boolean isCubic = false;
    //点击某个点后显示数值
    private boolean hasLabelForSelected = true;
    //点和线有不同的颜色
    private boolean pointsHaveDifferentColor = false;
    //
    private boolean hasGradientToTransparent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (LineChartView) findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());

        //产生水质数据
        generateValues();

        //将数据填充到图中
        generateData();

        //重计算
        chart.setViewportCalculationEnabled(false);

        resetViewport();
    }

    //初始化 water_data 数组中
    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                water_data[i][j] = 0;
            }
        }
    }

    //将水质数据存入到 water_data 数组中
    private void prepareDataAnimation() {
        for (Line line : data.getLines()) {
            for (PointValue value : line.getValues()) {
                value.setTarget(value.getX(), (float) Math.random() * 100);
            }
        }
    }

    //将数据填充到图中
    private void generateData(){
        List<Line> lines = new ArrayList<Line>();
        //每条线
        for (int i = 0; i < numberOfLines; ++i) {
            List<PointValue> values = new ArrayList<PointValue>();
            //每个点
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, water_data[i][j]));
            }
            //将点加到线中
            Line line = new Line(values);
            //设置线的颜色
            line.setColor(ChartUtils.COLORS[i]);
            //设置点的形状
            line.setShape(shape);
            //设置线是否光滑
            line.setCubic(isCubic);
            //设置线下部分是否填充
            line.setFilled(isFilled);
            //显示点的数值
            line.setHasLabels(hasLabels);
            //点的数值点击后显示
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            //线是否存在
            line.setHasLines(hasLines);
            //点是否存在
            line.setHasPoints(hasPoints);
            //设置点和线的颜色是否一样
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        //将线填充到 data 中
        data = new LineChartData(lines);

        //坐标轴
        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        //数据加入到图中
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

        //点击某个点后后显示数值
        chart.setValueSelectionEnabled(hasLabelForSelected);
        //设置平行，缩放
        chart.setInteractive(true);
        chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chart.setVisibility(View.VISIBLE);

        //动画效果
        prepareDataAnimation();
        chart.startDataAnimation(1000);
    }

    //设定坐标范围
    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = numberOfPoints - 1;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }

    //点击监听器
    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getApplicationContext(), "Selected: " + value.getX() + "点的数值为：" + value.getY(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

}