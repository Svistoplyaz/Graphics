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
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alexander on 10.10.2017.
 */
public class Painting extends JPanel{
    final int L = 256;
    int correction = 0;
    BufferedImage image;
    BufferedImage curimage;
    int sX, sY, tmpX, tmpY, eX, eY;
    boolean selecting = false;

//    BufferedImage im = new BufferedImage();

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
                correction = 0;
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
//                if(startX != -1 && !selected) {

                System.out.println(e.getY() +" " +e.getX());
                tmpX = e.getX();
                tmpY = e.getY();
                repaint();
//                }
            }
        });

        dropCoordinates();
    }

    public void dropCoordinates(){
        sX = sY = 0;
        eX = image.getWidth();
        eY = image.getHeight();

        correction = 0;

        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        curimage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }


    @Override
    public void paint(Graphics g){


        if(selecting) {
            g.drawImage(curimage, 0, 0, null);
            g.setColor(Color.WHITE);
            g.drawRect(sX, sY, tmpX - sX, tmpY - sY);
        }else{
            g.drawImage(addContrastFilter1(),0,0, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }


    private BufferedImage addContrastFilter()
    {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        BufferedImage ans = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

        //палитра
        int[] b = new int[L];

        //Число строк и столбцов
        int imageRows = image.getHeight();
        int imageCols = image.getWidth();

        int lAB = 0;
        int valueB;
        int valueG;
        int valueR;

        Color curcolor;
        //Находим яркость всех пикселей
        for(int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++)
            {
                curcolor = new Color(ans.getRGB(j,i));
                valueB = curcolor.getBlue();
                valueG = curcolor.getGreen();
                valueR = curcolor.getRed();

                lAB += (int)(valueR * 0.299 + valueG * 0.587 + valueB * 0.114);
            }

        //средняя яркость всех пикселей
        lAB /= (eY - sY) * (eX - sX);

        //Коэффициент коррекции
        double k = 1.0 + correction / 100.0;

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
                curcolor = new Color(ans.getRGB(j,i));
                ans.setRGB(j,i,new Color(b[curcolor.getRed()], b[curcolor.getGreen()], b[curcolor.getBlue()]).getRGB());
            }

        return ans;
    }

    private BufferedImage addContrastFilter1()
    {
        //палитра
        int[] b = new int[L];

        //Число строк и столбцов
        int imageRows = image.getHeight();
        int imageCols = image.getWidth();

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
        double k = 1.0 + correction / 100.0;

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

        return curimage;
    }
}
