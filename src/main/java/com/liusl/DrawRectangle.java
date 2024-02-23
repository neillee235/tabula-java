package com.liusl;

import technology.tabula.Image;
import technology.tabula.Page;
import technology.tabula.Table;
import technology.tabula.TextElement;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class DrawRectangle extends JFrame {

    public final static int multiple = 3; //缩放倍数
    public final static int grid = 10*multiple;  //网格尺寸
    public final static  int width = 600*multiple;
    public final static  int heigh = 1000*multiple;


    public static void main(String[] args) {
        drawPicture();
    }

    public static void drawPicture(){
        JFrame jFrame;
        JPanel jPanel=new MyPanel();
        JScrollPane jScrollPane=new JScrollPane(jPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        jFrame=new JFrame("测试滚动条界面");
        jFrame.setSize(800,800);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);

        //设置组件
        jFrame.add(jScrollPane);
        //滚动最大范围
        jPanel.setPreferredSize(new Dimension(width,heigh));
        jFrame.setVisible(true);
    }

    public static void drawPicture(List<Page> allPage,List<TextElement> allList,List<Table> tableList){
        JFrame jFrame;
        JPanel jPanel=new MyPanel(allPage,allList,tableList);
        JScrollPane jScrollPane=new JScrollPane(jPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        jFrame=new JFrame("测试滚动条界面");
        jFrame.setSize(800,800);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);

        //设置组件
        jFrame.add(jScrollPane);
        //滚动最大范围
        jPanel.setPreferredSize(new Dimension(width,heigh));
        jFrame.setVisible(true);
    }
    public static void drawPicture(List<Page> allPage, List<TextElement> allList, List<Table> tableList, List<Image> images){
        JFrame jFrame;
        JPanel jPanel=new MyPanel(allPage,allList,tableList, images);
        JScrollPane jScrollPane=new JScrollPane(jPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        jFrame=new JFrame("测试滚动条界面");
        jFrame.setSize(800,800);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);

        //设置组件
        jFrame.add(jScrollPane);
        //滚动最大范围
        jPanel.setPreferredSize(new Dimension(width,heigh));
        jFrame.setVisible(true);
    }
}

class MyPanel extends JPanel {

    private List<Image> images = new ArrayList<>();
    private List<TextElement> textList = new ArrayList<>();

    private List<Page> pageList = new ArrayList<>();
    private List<Table> tableList = new ArrayList<>();

    public MyPanel(){

    }
    public MyPanel(List<Page> pageList,List<TextElement> textList,List<Table> tableList){
        this.pageList = pageList;
        this.textList = textList;
        this.tableList = tableList;
    }

    public MyPanel(List<Page> pageList,List<TextElement> textList,List<Table> tableList, List<Image> images){
        this.pageList = pageList;
        this.textList = textList;
        this.tableList = tableList;
        this.images = images;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 在MyPanel上绘制一个红色的矩形
        Graphics2D g2d = (Graphics2D) g;

        drawBlackColor(g2d);
        drawCanvas(g2d);

        drawPage(g2d);
        drawTextElement(g2d);
        drawTable(g2d);
        drawImages(g2d);

        g2d.setColor(Color.magenta);

        int x  = new Double(20*DrawRectangle.multiple).intValue();
        int y  = new Double(20*DrawRectangle.multiple).intValue();
        int with = new Double(30*DrawRectangle.multiple).intValue();
        int height = new Double(40*DrawRectangle.multiple).intValue();
        g2d.fillRect(x,y,with,height);
    }

    private void drawImages(Graphics2D g2d) {
        g2d.setColor(Color.magenta);
        for(Image image: images){
            Rectangle2D bounds2D = image.getBounds2D();
            int x  = new Double(bounds2D.getX()*DrawRectangle.multiple).intValue();
            double y1 = bounds2D.getY();
            double height1 = pageList.get(0).getHeight();
            double v = height1 - (y1 + bounds2D.getHeight());

            int y  = new Double((v) *DrawRectangle.multiple).intValue();
            int with = new Double(bounds2D.getWidth()*DrawRectangle.multiple).intValue();
            int height = new Double(bounds2D.getHeight()*DrawRectangle.multiple).intValue();
            g2d.drawRect(x,y,with,height);
        }
    }

    private void drawTable(Graphics2D g2d){
        g2d.setColor(Color.magenta);
        for(Table table: tableList){
            Rectangle2D bounds2D = table.getBounds2D();

            int x  = new Double(bounds2D.getX()*DrawRectangle.multiple).intValue();
            int y  = new Double(bounds2D.getY()*DrawRectangle.multiple).intValue();
            int with = new Double(bounds2D.getWidth()*DrawRectangle.multiple).intValue();
            int height = new Double(bounds2D.getHeight()*DrawRectangle.multiple).intValue();
            g2d.drawRect(x,y,with,height);
        }
    }

    private void drawPage(Graphics2D g2d){
        g2d.setColor(Color.RED);
        for(Page page: pageList){

            Rectangle2D bounds2D = page.getBounds2D();
            int x  = new Double(bounds2D.getX()*DrawRectangle.multiple).intValue();
            int y  = new Double(bounds2D.getY()*DrawRectangle.multiple).intValue();
            int with = new Double(bounds2D.getWidth()*DrawRectangle.multiple).intValue();
            int height = new Double(bounds2D.getHeight()*DrawRectangle.multiple).intValue();
            g2d.drawRect(x,y,with,height);
        }

    }

    private void drawTextElement(Graphics2D g2d){
        g2d.setColor(Color.blue);
        for(TextElement textElement: textList){

            Rectangle2D bounds2D = textElement.getBounds2D();
//            g2d.draw(bounds2D);
            int x  = new Double(bounds2D.getX()*DrawRectangle.multiple).intValue();
            int y  = new Double(bounds2D.getY()*DrawRectangle.multiple).intValue();
            int with = new Double(bounds2D.getWidth()*DrawRectangle.multiple).intValue();
            int height = new Double(bounds2D.getHeight()*DrawRectangle.multiple).intValue();
//            g2d.drawRect(-9000,y-9000,with,height);
            g2d.fillRect(x,y,with,height);
        }
    }


    private void drawBlackColor(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawCanvas(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        int width = getWidth();
        int height = getHeight();

        // 计算每条线段的长度
        int lineLength = Math.min(width / (width/DrawRectangle.grid), height / (height/DrawRectangle.grid));

        // 开始绘制网格
        for (int i = 0; i <= width; i += lineLength) {
            g2d.drawLine(i, 0, i, height);
        }

        for (int j = 0; j <= height; j += lineLength) {
            g2d.drawLine(0, j, width, j);
        }
    }

}
