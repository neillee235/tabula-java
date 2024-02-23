package technology.tabula.bes2;

import technology.tabula.Page;
import technology.tabula.TextElement;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class DrawRectangle extends JFrame {


    public final static  int width = 100000;
    public final static  int heigh = 100000;

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

    public static void drawPicture(List<Page> allPage,List<TextElement> allList){
        JFrame jFrame;
        JPanel jPanel=new MyPanel(allPage,allList);
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

    private List<TextElement> textList = new ArrayList<>();

    private List<Page> pageList = new ArrayList<>();

    public MyPanel(){

    }
    public MyPanel(List<Page> pageList,List<TextElement> textList){
        this.pageList = pageList;
        this.textList = textList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 在MyPanel上绘制一个红色的矩形
        Graphics2D g2d = (Graphics2D) g;

        drawBlackColor(g2d);
        drawTable(g2d);

        drawPage(g2d);
        drawTextElement(g2d);

    }

    private void drawPage(Graphics2D g2d){
        g2d.setColor(Color.RED);
        for(Page page: pageList){

            Rectangle2D bounds2D = page.getBounds2D();
//            g2d.draw(bounds2D);
            int multiple = 1;
            int x  = new Double(bounds2D.getX()*multiple).intValue();
            int y  = new Double(bounds2D.getY()*multiple).intValue();
            int with = new Double(bounds2D.getWidth()*multiple).intValue();
            int height = new Double(bounds2D.getHeight()*multiple).intValue();
//            g2d.drawRect(-9000,y-9000,with,height);
            g2d.drawRect(x,y,with,height);
        }
    }

    private void drawTextElement(Graphics2D g2d){
        g2d.setColor(Color.blue);
        for(TextElement textElement: textList){

            Rectangle2D bounds2D = textElement.getBounds2D();
//            g2d.draw(bounds2D);
            int multiple = 1;
            int x  = new Double(bounds2D.getX()*multiple).intValue();
            int y  = new Double(bounds2D.getY()*multiple).intValue();
            int with = new Double(bounds2D.getWidth()*multiple).intValue();
            int height = new Double(bounds2D.getHeight()*multiple).intValue();
//            g2d.drawRect(-9000,y-9000,with,height);
            g2d.fillRect(x,y,with,height);
        }
    }


    private void drawBlackColor(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawTable(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        int width = getWidth();
        int height = getHeight();

        // 计算每条线段的长度
        int lineLength = Math.min(width / 10000, height / 10000);

        // 开始绘制网格
        for (int i = 0; i <= width; i += lineLength) {
            g2d.drawLine(i, 0, i, height);
        }

        for (int j = 0; j <= height; j += lineLength) {
            g2d.drawLine(0, j, width, j);
        }
    }

}
