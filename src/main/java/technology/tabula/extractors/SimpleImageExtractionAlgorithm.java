package technology.tabula.extractors;

import technology.tabula.Image;
import technology.tabula.Page;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleImageExtractionAlgorithm implements ImageExtractionAlgorithm {

    public List<Image> extract(Page page) throws IOException {
        GetImageLocationsAndSize getImageLocationsAndSize = new GetImageLocationsAndSize();
        double height = page.getHeight();
        getImageLocationsAndSize.processPage(page.getPDPage());

        List<Image> images = getImageLocationsAndSize.getImages();
//        if (images != null) {
//            return images.stream().map(x -> {
//                x.setTop((float) (height - x.getTop()));
//                return x;
//            }).collect(Collectors.toList());
//        }
        return images;
    }
}
