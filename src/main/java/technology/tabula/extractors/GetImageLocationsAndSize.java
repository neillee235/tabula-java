package technology.tabula.extractors;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.PDFStreamEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.contentstream.operator.state.Concatenate;
import org.apache.pdfbox.contentstream.operator.state.Restore;
import org.apache.pdfbox.contentstream.operator.state.Save;
import org.apache.pdfbox.contentstream.operator.state.SetGraphicsStateParameters;
import org.apache.pdfbox.contentstream.operator.state.SetMatrix;
import technology.tabula.Image;

/**
 * This is an example on how to get the x/y coordinates of image location and size of image.
 */
public class GetImageLocationsAndSize extends PDFStreamEngine {

    private List<Image> images = new ArrayList<>();

    /**
     * @throws IOException If there is an error loading text stripper properties.
     */
    public GetImageLocationsAndSize() throws IOException {
        // preparing PDFStreamEngine
        addOperator(new Concatenate(this));
        addOperator(new DrawObject(this));
        addOperator(new SetGraphicsStateParameters(this));
        addOperator(new Save(this));
        addOperator(new Restore(this));
        addOperator(new SetMatrix(this));
    }


    /**
     * @throws IOException If there is an error parsing the document.
     */
    public static void main(String[] args) throws IOException {
        PDDocument document = null;
        String fileName = "E:\\code\\tabula-java\\tabula-java\\src\\test\\resources\\technology\\tabula\\bes\\测试tabula.pdf";
        try {
            document = Loader.loadPDF(new File(fileName));
            GetImageLocationsAndSize printer = new GetImageLocationsAndSize();
            int pageNum = 0;
            for (PDPage page : document.getPages()) {
                pageNum++;
                System.out.println("\n\nProcessing page: " + pageNum + "\n---------------------------------");
                printer.processPage(page);
                List<Image> images = printer.getImages();
            }
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    /**
     * @param operator The operation to perform.
     * @param operands The list of arguments.
     * @throws IOException If there is an error processing the operation.
     */
    @Override
    protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
        String operation = operator.getName();
        if ("Do".equals(operation)) {
            COSName objectName = (COSName) operands.get(0);
            // get the PDF object
            PDXObject xobject = getResources().getXObject(objectName);
            // check if the object is an image object
            if (xobject instanceof PDImageXObject) {
                PDImageXObject image = (PDImageXObject) xobject;
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();


                System.out.println("\nImage [" + objectName.getName() + "]");

                Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
                float imageXScale = ctmNew.getScalingFactorX();
                float imageYScale = ctmNew.getScalingFactorY();

                Image image1 = new Image();
                image1.setRect(ctmNew.getTranslateX(), ctmNew.getTranslateY(), imageXScale, imageYScale);
                image1.setMatrix(ctmNew);
                image1.setPDImageXObject(image);

                this.images.add(image1);

//                // position of image in the pdf in terms of user space units
//                System.out.println("position in PDF = " + ctmNew.getTranslateX() + ", " + ctmNew.getTranslateY() + " in user space units");
//                // raw size in pixels
//                System.out.println("raw image size  = " + imageWidth + ", " + imageHeight + " in pixels");
//                // displayed size in user space units
//                System.out.println("displayed size  = " + imageXScale + ", " + imageYScale + " in user space units");
            } else if (xobject instanceof PDFormXObject) {
                PDFormXObject form = (PDFormXObject) xobject;
                showForm(form);
            }
        } else {
            super.processOperator(operator, operands);
        }
    }

    public List<Image> getImages() {
        return images;
    }
}