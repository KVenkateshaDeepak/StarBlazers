import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final Map<String, Image> imageCache = new HashMap<>();

    public static Image loadImage(String path) {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        }

        File file = new File(path);
        if (!file.exists()) {
            System.err.println("Image file not found: " + path);
            return null;
        }

        Image image = new ImageIcon(path).getImage();
        imageCache.put(path, image);
        return image;
    }
}
