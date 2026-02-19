package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Icons {
    public static final BufferedImage LOGO = load("res//logo.png");
    public static final BufferedImage PEN  = load("res//pen-icon2.png");

    private static BufferedImage load(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path);
        }
    }
}
