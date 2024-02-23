package technology.tabula.bes2;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class PdfBoxTest {

    public static void main(String[] args) throws IOException {
        String filePath = "E:\\code\\tabula-java\\tabula-java\\src\\test\\resources\\technology\\tabula\\bes\\测试tabula.pdf";
        File file = new File(filePath);

        String s = readPDF(filePath);

        System.out.println(s);

    }


    public static String readPDF(String file) throws IOException {
        StringBuilder result = new StringBuilder();
        FileInputStream is = null;
        PDDocument document = null;
        is = new FileInputStream(file);
        PDDocument doc = Loader.loadPDF(new File(file));
        PDFTextStripper textStripper = new PDFTextStripper();
        for (int i = 1; i <= doc.getNumberOfPages(); i++) {
//            System.out.println("----------------第"+(i)+"页-------------------");
            textStripper.setStartPage(i);
            textStripper.setEndPage(i);
            textStripper.setSortByPosition(true);//按顺序行读
            String s = textStripper.getText(doc);
            result.append(s);

            PDPage page = doc.getPage(i - 1);
        }


        //文本为空，读图片提取图片里的文字，
        if (result.toString().trim().length() == 0) {
            for (int i = 1; i < doc.getNumberOfPages(); i++) {
                PDPage page = doc.getPage(i - 1);
                PDResources resources = page.getResources();
                Iterable<COSName> xobjects = resources.getXObjectNames();
                if (xobjects != null) {
                    Iterator<COSName> imageIter = xobjects.iterator();
                    while (imageIter.hasNext()) {
                        COSName cosName = imageIter.next();
                        boolean isImageXObject = resources.isImageXObject(cosName);
                        if (isImageXObject) {
                            //获取每页资源的图片
                            PDImageXObject ixt = (PDImageXObject) resources.getXObject(cosName);
                            System.out.println(ixt.getHeight());
//                            File outputfile = new File("D:\\tmp\\" + cosName.getName() + ".jpg");
//                            ImageIO.write(ixt.getImage(), "jpg", outputfile);//可保存图片到本地
                            //调用图片识别文字接口
//							//...
                        }
                    }
                }
            }
        }
        doc.close();
        return result.toString();
    }


}
