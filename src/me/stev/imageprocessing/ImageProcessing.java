package me.stev.imageprocessing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessing {

    public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
    public static final String DESTINATION_FILE = "./out/fuck-you-many-flowers.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage originImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originImage.getWidth(), originImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();

//        recolorSingleThreaded(originImage, resultImage);

        System.out.println("Cores : " + Runtime.getRuntime().availableProcessors());

        recolorMultiThreaded(originImage, resultImage, Runtime.getRuntime().availableProcessors());

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        File outputFile = new File(DESTINATION_FILE);

        ImageIO.write(resultImage, "jpg", outputFile);

        System.out.println("Duration : " + (duration / 1000d));
    }

    public static void recolorMultiThreaded(BufferedImage originImage, BufferedImage resultImage, int numOfThreads) {
        List<Thread> threads = new ArrayList<>();
        int width = originImage.getWidth();
        int height = originImage.getHeight() / numOfThreads;

        for (int i = 0; i < numOfThreads; i++) {
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
                int leftCorner = 0;
                int topCorner = height * threadMultiplier;

                recolorImage(originImage, resultImage, leftCorner, topCorner, width, height);
            });

            threads.add(thread);
        }

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void recolorSingleThreaded(BufferedImage originImage, BufferedImage resultImage) {
        recolorImage(originImage, resultImage, 0, 0, originImage.getWidth(), originImage.getHeight());
    }

    public static void recolorImage(BufferedImage originImage, BufferedImage resultImage, int leftCorner, int topCorner,
                                    int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < originImage.getWidth(); x++) {
            for (int y = topCorner; y < topCorner + height && y < originImage.getHeight(); y++) {
                recolorPixel(originImage, resultImage, x, y);
            }
        }
    }

    public static void recolorPixel(BufferedImage originImage, BufferedImage resultImage, int x, int y) {
        int rgb = originImage.getRGB(x,y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if (isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);

    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }


    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;
        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }
}
