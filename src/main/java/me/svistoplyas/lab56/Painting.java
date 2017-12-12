package me.svistoplyas.lab56;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Random;

/**
 * Created by Alexander on 10.10.2017.
 */
public class Painting extends JPanel {
    private final int L = 256;
    private double binaryBorder = 122.5;
    private BufferedImage image;
    private BufferedImage curimage;
    private BufferedImage mask;
    private int sX, sY, tmpX, tmpY, eX, eY;
    private boolean selecting = false;

    Painting() {
        try {
            image = ImageIO.read(new File("resources/", "Source.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                sX = e.getX();
                sY = e.getY();
                selecting = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                eX = e.getX();
                eY = e.getY();
                selecting = false;
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                tmpX = e.getX();
                tmpY = e.getY();
                repaint();
            }
        });

        dropCoordinates();
    }

    void dropCoordinates() {
        sX = sY = 0;
        eX = image.getWidth();
        eY = image.getHeight();

        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        curimage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(curimage, 0, 0, null);

        if (selecting) {
            g.setColor(Color.WHITE);
            g.drawRect(sX, sY, tmpX - sX, tmpY - sY);
        }

        g.drawImage(mask, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }


    public void addContrastFilter(int correction) {
        //палитра
        int[] b = new int[L];

        int lAB = 0;
        int valueB;
        int valueG;
        int valueR;

        Color curcolor;
        //Находим яркость всех пикселей
        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                curcolor = new Color(curimage.getRGB(j, i));
                valueB = curcolor.getBlue();
                valueG = curcolor.getGreen();
                valueR = curcolor.getRed();

                lAB += (int) (valueR * 0.299 + valueG * 0.587 + valueB * 0.114);
            }

        //средняя яркость всех пикселей
        lAB /= (eY - sY) * (eX - sX);

        //Коэффициент коррекции
        double k = 1.0 + (correction) / 100.0;

        //RGB алгоритм изменения контраста
        for (int i = 0; i < L; i++) {
            int delta = i - lAB;
            int temp = (int) (lAB + k * delta);

            if (temp < 0)
                temp = 0;

            if (temp >= 255)
                temp = 255;
            b[i] = temp;
        }

        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                curcolor = new Color(curimage.getRGB(j, i));
                curimage.setRGB(j, i, new Color(b[curcolor.getRed()], b[curcolor.getGreen()], b[curcolor.getBlue()]).getRGB());
            }

    }

    public void addBrightnessFilter(int brightness) {
        //палитра
        int[] b = new int[L];

        Color curcolor;

        //Коэффициент коррекции
        double k = 1.0 + (brightness) / 100.0;

        //RGB алгоритм изменения контраста
        for (int i = 0; i < L; i++) {
            int temp = (int) (i * k);

            if (temp < 0)
                temp = 0;

            if (temp >= 255)
                temp = 255;
            b[i] = temp;
        }

        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                curcolor = new Color(curimage.getRGB(j, i));
                curimage.setRGB(j, i, new Color(b[curcolor.getRed()], b[curcolor.getGreen()], b[curcolor.getBlue()]).getRGB());
            }

    }

    public void addBinaryFilter() {
        Color curcolor;

        int cur;
        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                curcolor = new Color(curimage.getRGB(j, i));
                cur = (int) (curcolor.getRed() * 0.299 + curcolor.getGreen() * 0.587 + curcolor.getBlue() * 0.114);
//                cur = (int)(curcolor.getRed() * 0.333 + curcolor.getGreen() * 0.333 + curcolor.getBlue() * 0.33);
                curimage.setRGB(j, i, new Color(cur, cur, cur).getRGB());
            }

    }

    public void modifyBinaryFilter(int mod) {
        binaryBorder += mod;

        if (binaryBorder < 0)
            binaryBorder = 0;
        else if (binaryBorder > 255)
            binaryBorder = 255;
    }

    public void addBlackAndWhiteFilter() {
        Color curcolor;

        int cur;
        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                curcolor = new Color(curimage.getRGB(j, i));
                cur = (int) (curcolor.getRed() * 0.299 + curcolor.getGreen() * 0.587 + curcolor.getBlue() * 0.114);
                if (cur > binaryBorder)
                    curimage.setRGB(j, i, new Color(255, 255, 255).getRGB());
                else
                    curimage.setRGB(j, i, new Color(0, 0, 0).getRGB());
            }

    }

    public void addNegativeFilter() {
        Color curcolor;

        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                curcolor = new Color(curimage.getRGB(j, i));
                curimage.setRGB(j, i, new Color(255 - curcolor.getRed(), 255 - curcolor.getGreen(), 255 - curcolor.getBlue()).getRGB());
            }

    }

    public void addNoiseFilter() {
        int wid = this.image.getWidth();
        int heig = this.image.getHeight();
        Graphics g = curimage.getGraphics();

        Random r = new Random();

        int dots = r.nextInt(5000) + 5000;
        int x, y;

        g.setColor(Color.BLACK);
        for (int i = 0; i < dots; i++) {
            x = r.nextInt(wid);
            y = r.nextInt(heig);
            g.drawLine(x, y, x, y);
        }

        int lines = r.nextInt(100) + 100;
        for (int i = 0; i < lines; i++) {
            g.drawLine(r.nextInt(wid), r.nextInt(heig), r.nextInt(wid), r.nextInt(heig));
        }

        int circles = r.nextInt(100) + 100;
        for (int i = 0; i < circles; i++) {
            g.drawOval(r.nextInt(wid), r.nextInt(heig), r.nextInt(100), r.nextInt(100));
        }
    }

    private void imageMultiplying(double[][] matrix, int r, boolean tisn) {
        int wid = this.image.getWidth();
        int heig = this.image.getHeight();
        BufferedImage ans = new BufferedImage(eX - sX, eY - sY, BufferedImage.TYPE_INT_RGB);

        Color curcolor;
        double red, gre, blu;
        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                red = gre = blu = 0;
                for (int k = -r; k <= r; k++)
                    for (int l = -r; l <= r; l++) {
                        //Случай выхода за границу
                        if (j - l < 0 || i - k < 0 || j - l >= wid || i - k >= heig) {
                            curcolor = new Color(curimage.getRGB(j, i));

                            red += curcolor.getRed() * matrix[r + l][r + k];
                            gre += curcolor.getGreen() * matrix[r + l][r + k];
                            blu += curcolor.getBlue() * matrix[r + l][r + k];
                            continue;
                        }

                        curcolor = new Color(curimage.getRGB(j - l, i - k));

                        red += curcolor.getRed() * matrix[r + l][r + k];
                        gre += curcolor.getGreen() * matrix[r + l][r + k];
                        blu += curcolor.getBlue() * matrix[r + l][r + k];
                    }

                if (tisn) {
                    red += 128;
                    gre += 128;
                    blu += 128;
                }

                if (red < 0)
                    red = 0;
                else if (red > 255)
                    red = 255;
                if (gre < 0)
                    gre = 0;
                else if (gre > 255)
                    gre = 255;
                if (blu < 0)
                    blu = 0;
                else if (blu > 255)
                    blu = 255;

                Color newcolor = new Color((int) (red), (int) (gre), (int) (blu));
                ans.setRGB(j - sX, i - sY, newcolor.getRGB());
            }

        curimage.getGraphics().drawImage(ans, sX, sY, null);
    }

    public void addEvenDistribution() {
        int n = 3, r = n / 2;
        double[][] even = new double[n][n];

        int sqr = n * n;
        for (int i = 0; i < sqr; i++) {
            even[i / n][i % n] = 1.0 / (sqr);
        }

        imageMultiplying(even, r, false);
    }

    public void addGaussDistribution() {
        int n = 5, r = n / 2;
        double sigma = 2;
        double[][] matrix = new double[n][n];

        for (int k = -r; k <= r; k++)
            for (int l = -r; l <= r; l++) {
                matrix[r + k][r + l] = Math.exp(-(l * l + k * k) / (2 * sigma * sigma));
            }

        double A = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                A += matrix[i][j];
        }
        A = 1.0 / A;

        for (int k = -r; k <= r; k++)
            for (int l = -r; l <= r; l++) {
                matrix[r + k][r + l] *= A;
            }

        imageMultiplying(matrix, r, false);
    }

    public void addSharpness() {
        int n = 3, r = n / 2, K = 2;
        double[][] matrix = new double[n][n];

        for (int k = -r; k <= r; k++)
            for (int l = -r; l <= r; l++) {
                matrix[r + k][r + l] = -K * 1.0 / 8;
            }
        matrix[r][r] = K + 1;
        imageMultiplying(matrix, r, false);
    }

    public void addBorder() {
        int n = 3, r = n / 2;
        double c = 0.7;
//        double[][] matrix = {{0, 1, 0}, {1, 0, -1}, {0, -1, 0}};
//        double[][] matrix = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        double[][] matrix = {{c, c, c}, {c, c, c}, {-2 * c, -2 * c, -2 * c}};

        imageMultiplying(matrix, r, true);
    }

    public void addWaves() {
        BufferedImage ans = new BufferedImage(eX - sX, eY - sY, BufferedImage.TYPE_INT_RGB);

        int wid = ans.getWidth();

        Color curcolor;
        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                curcolor = new Color(curimage.getRGB(j, i));

                int x = (int) (j + 20 * Math.sin(2 * Math.PI * i / 128));

                if (x - sX >= wid)
                    x = eX - 1;
                else if (x - sX < 0)
                    x = sX;


                ans.setRGB(x - sX, i-sY, curcolor.getRGB());

            }

        curimage.getGraphics().drawImage(ans, sX, sY, null);
    }

    public void addGlass() {
        ColorModel cm = curimage.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = curimage.copyData(null);
        BufferedImage ans = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
//        BufferedImage ans = new BufferedImage(eX - sX, eY - sY, BufferedImage.TYPE_INT_RGB);

        Random r = new Random();
        int wid = ans.getWidth();
        int hei = ans.getHeight();

        Color curcolor;
        Graphics g = ans.getGraphics();
        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                curcolor = new Color(curimage.getRGB(j, i));

                double din;
                if(r.nextBoolean())
                    din = 0.5;
                else
                    din = -0.5;
                int x = (int) (j + din*10);

                if(r.nextBoolean())
                    din = 0.5;
                else
                    din = -0.5;
                int y = (int) (i + din*10);

                if (x>= wid)
                    x = wid - 1;
                else if (x < 0)
                    x = 0;

                if (y>= hei)
                    y = hei-1;
                else if (y < 0)
                    y = 0;

                g.setColor(curcolor);
                g.drawLine(x, y,x, y);

            }

//        curimage.getGraphics().drawImage(ans, sX, sY, null);
        curimage = ans;
    }


    public BufferedImage getCurimage() {
        return this.curimage;
    }
}
