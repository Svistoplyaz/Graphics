package me.svistoplyas.lab5;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

/**
 * Created by Alexander on 10.10.2017.
 */
public class Painting extends JPanel{
    private final int L = 256;
    private BufferedImage image;
    private BufferedImage curimage;
    private int sX, sY, tmpX, tmpY, eX, eY;
    private boolean selecting = false;

    Painting(){
        try {
            image = ImageIO.read(new File("resources/", "Source.jpg"));
        }catch (Exception e){
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
                System.out.println(e.getY() +" " +e.getX());
                tmpX = e.getX();
                tmpY = e.getY();
                repaint();
            }
        });

        dropCoordinates();
    }

    void dropCoordinates(){
        sX = sY = 0;
        eX = image.getWidth();
        eY = image.getHeight();

        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        curimage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }


    @Override
    public void paint(Graphics g){
        g.drawImage(curimage, 0, 0, null);

        if(selecting) {
            g.setColor(Color.WHITE);
            g.drawRect(sX, sY, tmpX - sX, tmpY - sY);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }


    public void addContrastFilter(int correction)
    {

        //палитра
        int[] b = new int[L];

        int lAB = 0;
        int valueB;
        int valueG;
        int valueR;

        Color curcolor;
        //Находим яркость всех пикселей
        for(int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++)
            {
                curcolor = new Color(curimage.getRGB(j,i));
                valueB = curcolor.getBlue();
                valueG = curcolor.getGreen();
                valueR = curcolor.getRed();

                lAB += (int)(valueR * 0.299 + valueG * 0.587 + valueB * 0.114);
            }

        //средняя яркость всех пикселей
        lAB /= (eY - sY) * (eX - sX);

        //Коэффициент коррекции
        double k = 1.0 + (correction) / 100.0;

        //RGB алгоритм изменения контраста
        for (int i = 0; i < L; i++)
        {
            int delta = i - lAB;
            int temp  = (int)(lAB + k *delta);

            if (temp < 0)
                temp = 0;

            if (temp >= 255)
                temp = 255;
            b[i] = temp;
        }

        for(int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++)
            {
                curcolor = new Color(curimage.getRGB(j,i));
                curimage.setRGB(j,i,new Color(b[curcolor.getRed()], b[curcolor.getGreen()], b[curcolor.getBlue()]).getRGB());
            }

    }

    public void addBrightnessFilter(int brightness)
    {

        //палитра
        int[] b = new int[L];

        Color curcolor;

        //Коэффициент коррекции
        double k = 1.0 + (brightness) / 100.0;

        //RGB алгоритм изменения контраста
        for (int i = 0; i < L; i++)
        {
            int temp  = (int)(i*k);

            if (temp < 0)
                temp = 0;

            if (temp >= 255)
                temp = 255;
            b[i] = temp;
        }

        for(int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++)
            {
                curcolor = new Color(curimage.getRGB(j,i));
                curimage.setRGB(j,i,new Color(b[curcolor.getRed()], b[curcolor.getGreen()], b[curcolor.getBlue()]).getRGB());
            }

    }
}
