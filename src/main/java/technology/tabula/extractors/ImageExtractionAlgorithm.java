package technology.tabula.extractors;

import technology.tabula.Image;
import technology.tabula.Page;

import java.io.IOException;
import java.util.List;

/**
 * pdf的图片解析器
 */
public interface ImageExtractionAlgorithm {

    List<Image> extract(Page page) throws IOException;
}
