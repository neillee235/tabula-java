package technology.tabula.bes;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import technology.tabula.*;
import technology.tabula.bes.DrawRectangle;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TabulaTest2 {

    public static void main(String[] args) throws Exception {
        //String filePath = "D:\\javacode\\kkFileView\\server\\src\\main\\file\\灵雀云全栈云原生开放平台 v3.8.3 部署文档docx.pdf";
        String filePath = "E:\\code\\tabula-java\\tabula-java\\src\\test\\resources\\technology\\tabula\\bes\\测试tabula.pdf";
//        String filePath = "D:\\javacode\\kkFileView\\server\\src\\main\\file\\demo\\宝兰德智能学习平台V2.3.0用户手册.pdf";
//        String filePath = "C:\\Users\\houyibing\\Desktop\\神奇文档\\这是一个神奇的文档原始文档2.pdf";
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = new ObjectExtractor(document).extract();
            List<TextElement> allText = new ArrayList<>();
            while (pi.hasNext()) {
                // iterate over the pages of the document
                Page page = pi.next();

                PDPage pdPage = page.getPDPage();

                List<Table> table = sea.extract(page);
                // iterate over the tables of the page
                for(Table tables: table) {
                    List<List<RectangularTextContainer>> rows = tables.getRows();
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

                List<TextElement> text = page.getText();
                
                for(int i = 0 ; i < text.size();  i++){
                    TextElement textElement = text.get(i);
                    System.out.print(textElement.getText());
                }
                if(page.getPageNumber()==1) {
                    allText.addAll(text);
                }
                DrawRectangle.drawPicture(allText);
            }

        }
    }

}
