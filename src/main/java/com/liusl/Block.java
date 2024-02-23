package com.liusl;

import technology.tabula.Table;
import technology.tabula.TextElement;

public class Block {

    public  Dot leftTop;  //左上角 xy

    public  Dot leftBottom;  //左下xy

    public  Dot rightTop;  //右上xy

    public  Dot rightBottom;  //右下xy

    public  String text;

    public  TextElement textElement;

    public Table table;

    public Block(double[] leftTop, double[] leftBottom, double[] rightTop, double[] rightBottom, TextElement textElement) {
        this.leftTop  = new Dot(leftTop[0],leftTop[1]);
        this.leftBottom  = new Dot(leftBottom[0],leftBottom[1]);
        this.rightTop  = new Dot(rightTop[0],rightTop[1]);
        this.rightBottom  = new Dot(rightBottom[0],rightBottom[1]);
        this.textElement  = textElement;
    }

    public Block(double[] leftTop, double[] leftBottom, double[] rightTop, double[] rightBottom, Table table) {
        this.leftTop  = new Dot(leftTop[0],leftTop[1]);
        this.leftBottom  = new Dot(leftBottom[0],leftBottom[1]);
        this.rightTop  = new Dot(rightTop[0],rightTop[1]);
        this.rightBottom  = new Dot(rightBottom[0],rightBottom[1]);
        this.table  = table;
    }

    public Dot getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(Dot leftTop) {
        this.leftTop = leftTop;
    }

    public Dot getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(Dot leftBottom) {
        this.leftBottom = leftBottom;
    }

    public Dot getRightTop() {
        return rightTop;
    }

    public void setRightTop(Dot rightTop) {
        this.rightTop = rightTop;
    }

    public Dot getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(Dot rightBottom) {
        this.rightBottom = rightBottom;
    }

    public String getText() {
        return textElement==null?"":textElement.getText();
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextElement getTextElement() {
        return textElement;
    }

    public void setTextElement(TextElement textElement) {
        this.textElement = textElement;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }


    public double getHeight(){
        return textElement==null?table.getHeight():textElement.getHeight();
    }

    public double getWidth(){
        return textElement==null?table.getWidth():textElement.getWidth();
    }


    class Dot{
        public double x;
        public double y;

        public Dot(double x,double y){
            this.x = x;
            this.y = y;
        }

    }

}
