package technology.tabula.bes;

import technology.tabula.TextElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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

    public static void drawPicture(List<TextElement> list){
        JFrame jFrame;
        JPanel jPanel=new MyPanel(list);
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

    private List<TextElement> list = new ArrayList<>();

    public MyPanel(){

    }
    public MyPanel(List<TextElement> list){
        this.list = list;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 在MyPanel上绘制一个红色的矩形
        Graphics2D g2d = (Graphics2D) g;

        drawBlackColor(g2d);
        drawTable(g2d);

        drawTextElement(g2d);

    }

    private void drawTextElement(Graphics2D g2d){
        g2d.setColor(Color.blue);
        for(TextElement textElement: list){
            int x  = new Double(textElement.getX()).intValue();
            int y  = new Double(textElement.getY()).intValue();
            int with = new Double(textElement.getWidth()).intValue();
            g2d.fillRect(x,y,with , 10);
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
