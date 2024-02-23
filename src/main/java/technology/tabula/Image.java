package technology.tabula;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

public class Image extends Rectangle{
    private byte[] bytes;

    private String name;
    private Matrix matrix;
    private PDImageXObject pdImageXobject;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMatrix(Matrix ctmNew) {
        this.matrix = ctmNew;
    }

    public void setPDImageXObject(PDImageXObject image) {
        this.pdImageXobject = image;
    }
}
