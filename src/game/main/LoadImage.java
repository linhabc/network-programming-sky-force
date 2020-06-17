package game.main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LoadImage {
    public static BufferedImage image;
    public static BufferedImage entities;
    public static BufferedImage enemy, player;

    public static void init() {
       image = imageLoader("../resources/whte.png");
       entities = imageLoader("../resources/airplane.png");
       enemy = entities.getSubimage(0, 0,85, 90);
       player = entities.getSubimage(5, 10, 290, 240);
    }

    private static BufferedImage imageLoader(String path) {
        try {
            return ImageIO.read(LoadImage.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}