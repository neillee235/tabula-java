package com.liusl;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.ImageExtractionAlgorithm;
import technology.tabula.extractors.SimpleImageExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TabulaTest2 {

    public static void main(String[] args) throws Exception {
        //String filePath = "D:\\javacode\\kkFileView\\server\\src\\main\\file\\灵雀云全栈云原生开放平台 v3.8.3 部署文档docx.pdf";
        String filePath = "E:\\code\\tabula-java\\tabula-java\\src\\main\\java\\com\\liusl\\这是一个神奇的文档原始文档.pdf";
//        String filePath = "D:\\javacode\\kkFileView\\server\\src\\main\\file\\demo\\宝兰德智能学习平台V2.3.0用户手册.pdf";
//        String filePath = "C:\\Users\\houyibing\\Desktop\\神奇文档\\这是一个神奇的文档原始文档2.pdf";
        InputStream in = new FileInputStream(new File(filePath));

        List<TextElement> allText = new ArrayList<>();
        List<Page> allPage = new ArrayList<>();
        List<Table> allTable = new ArrayList<>();
        List<Image> allImages = new ArrayList<>();

        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            ImageExtractionAlgorithm imageExtractionAlgorithm = new SimpleImageExtractionAlgorithm();
            PageIterator pi = new ObjectExtractor(document).extract();
            List<Image> images = null;
            while (pi.hasNext()) {
                // iterate over the pages of the document
                Page page = pi.next();



                List<Table> tables = sea.extract(page);
                images = imageExtractionAlgorithm.extract(page);
                // iterate over the tables of the page
                //printTable(tables);

                List<TextElement> text = page.getText();

                for (int i = 0; i < text.size(); i++) {
                    TextElement textElement = text.get(i);
                    //System.out.print(textElement.getText());
                }
                if (page.getPageNumber() == 1) {
                    allText.addAll(text);
                    allTable.addAll(tables);
                    allPage.add(page);
                    allImages.addAll(images);
                }
            }

            DrawRectangle.drawPicture(allPage, allText, allTable, allImages);
        }


        computerLayout(allPage, allText, allTable);
    }


    private static void computerLayout(List<Page> pageList, List<TextElement> textList, List<Table> tableList) {

        List<PageContainer> pageContainerList = new ArrayList<>();

        for (Page page : pageList) {
            PageContainer pageContainer = new PageContainer();
            pageContainer.setPage(page);

            //组装文本区块
            List<Block> textBlockList = new ArrayList<>();
            for (TextElement textElement : page.getText()) {
                Rectangle2D bounds2D = textElement.getBounds2D();
                double x = bounds2D.getX();  //左上x
                double y = bounds2D.getY(); //左上y
                double height = bounds2D.getHeight(); //高度
                double width = bounds2D.getWidth(); //宽度

                double[] leftTop = {x, y};
                double[] leftBottom = {x, y + height};
                double[] rightTop = {x + width, y};
                double[] rightBottom = {x + width, y + height};

                Block block = new Block(leftTop, leftBottom, rightTop, rightBottom, textElement);
                textBlockList.add(block);
            }

            pageContainer.setTextBlockList(textBlockList);
            pageContainerList.add(pageContainer);

            //组装表格
            List<Block> tableBlockList = new ArrayList<>();
            for (Table table : tableList) {
                if (table.getPageNumber() == page.getPageNumber()) {
                    Rectangle2D bounds2D = table.getBounds2D();
                    double x = bounds2D.getX();  //左上x
                    double y = bounds2D.getY(); //左上y
                    double height = bounds2D.getHeight(); //高度
                    double width = bounds2D.getWidth(); //宽度

                    double[] leftTop = {x, y};
                    double[] leftBottom = {x, y + height};
                    double[] rightTop = {x + width, y};
                    double[] rightBottom = {x + width, y + height};

                    Block block = new Block(leftTop, leftBottom, rightTop, rightBottom, table);
                    tableBlockList.add(block);
                }
            }

            pageContainer.setTableBlockList(tableBlockList);

        }


        for (PageContainer pageContainer : pageContainerList) {

            List<Block> textBlockList = pageContainer.getTextBlockList();
            List<Block> tableBlockList = pageContainer.getTableBlockList();

            //遍历文本区块
            for (int i = 0; i < textBlockList.size(); i++) {
                Block previousBlock = null;
                if (i != 0) {
                    previousBlock = textBlockList.get(i - 1);
                }
                Block thisBlock = textBlockList.get(i);

                //判断是否换行
                checkNewLine(previousBlock, thisBlock);

                //过滤表格内的数据
                if (checkInTheTable(thisBlock, tableBlockList)) {

                } else {
                    //输出文本
                    System.out.print(thisBlock.getText());
                }

            }
        }
    }

    private static void printTable(List<Table> tables) {
        for (Table table : tables) {
            List<List<RectangularTextContainer>> rows = table.getRows();
            // iterate over the rows of the table
            for (List<RectangularTextContainer> cells : rows) {
                // print all column-cells of the row plus linefeed
                for (RectangularTextContainer content : cells) {
                    // Note: Cell.getText() uses \r to concat text chunks
                    String text = content.getText().replace("\r", " ");
                    System.out.print(text + "|");
                }
                System.out.println();
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
        }
    }


    /**
     * 检查是否换行
     *
     * @param previousBlock
     * @param thisBlock
     */
    private static void checkNewLine(Block previousBlock, Block thisBlock) {
        if (previousBlock != null) {

            double height = thisBlock.getHeight(); //当前区块高度
            double differenceY = thisBlock.getLeftBottom().y - previousBlock.getLeftBottom().y; //当前y轴与前一个y轴差值
            boolean largePreivousY = thisBlock.getLeftBottom().y > previousBlock.getLeftBottom().y; //y轴大于前一个y轴
            boolean largeHeight = differenceY > height; //

            if (largePreivousY && largeHeight) {
                System.out.print("\n");
            }
        }
    }

    /**
     * 检查是否在表格内
     *
     * @param thisBlock
     * @param tableBlockList
     * @return
     */
    private static boolean checkInTheTable(Block thisBlock, List<Block> tableBlockList) {

        Block.Dot leftTop = thisBlock.getLeftTop();
        Block.Dot leftBottom = thisBlock.getLeftBottom();
        Block.Dot rightTop = thisBlock.getRightTop();
        Block.Dot rightBottom = thisBlock.getRightBottom();


        for (Block tableBlock : tableBlockList) {
            Block.Dot tleftTop = tableBlock.getLeftTop();
            Block.Dot tleftBottom = tableBlock.getLeftBottom();
            Block.Dot trightTop = tableBlock.getRightTop();
            Block.Dot trightBottom = tableBlock.getRightBottom();


            boolean b1 = leftTop.x > tleftTop.x;
            boolean b2 = leftTop.y > tleftTop.y;
            boolean b3 = leftBottom.x > tleftBottom.x;
            boolean b4 = leftBottom.y < tleftBottom.y;
            boolean b5 = rightTop.x < trightTop.x;
            boolean b6 = rightTop.y > trightTop.y;
            boolean b7 = rightBottom.x < trightBottom.x;
            boolean b8 = rightBottom.y < trightBottom.y;

            if (b1
                    && b2
                    && b3
                    && b4
                    && b5
                    && b6
                    && b7
                    && b8
            ) {
                return true;
            }

        }


        return false;
    }

}
