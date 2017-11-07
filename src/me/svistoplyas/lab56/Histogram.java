package me.svistoplyas.lab56;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Alexander on 02.11.2017.
 */
public class Histogram extends JPanel {
    int[] rhist, ghist, bhist;
    int[] realhist = new int[256];
    int[] realrhist = new int[256];
    int[] realghist = new int[256];
    int[] realbhist = new int[256];
    Painting paint;

    Histogram(Painting pnt) {
        paint = pnt;
    }

    public void fillHist() {
        rhist = new int[256];
        ghist = new int[256];
        bhist = new int[256];
        BufferedImage im = paint.getCurimage();
        int sY = 0, sX = 0;
        int eY = im.getHeight(), eX = im.getWidth();

        Color curcolor;
        int valueB, valueG, valueR;
        for (int i = sY; i < eY; i++)
            for (int j = sX; j < eX; j++) {
                curcolor = new Color(im.getRGB(j, i));
                valueB = curcolor.getBlue();
                valueG = curcolor.getGreen();
                valueR = curcolor.getRed();

                rhist[valueR]++;
                ghist[valueG]++;
                bhist[valueB]++;
            }


        double scale;

        int rmax = -1, gmax = -1, bmax = -1, globalmax = -1;
        for (int i = 0; i < 256; i++) {
            rmax = Math.max(rmax, rhist[i]);
            gmax = Math.max(gmax, ghist[i]);
            bmax = Math.max(bmax, bhist[i]);
        }
        globalmax = rmax + gmax + bmax;

        for (int i = 0; i < 256; i++) {
//            realhist[i] = (int)(scale * (rhist[i] + ghist[i] + bhist[i])) > 255 ? 255 : (int)(scale * (rhist[i] + ghist[i] + bhist[i]));
//            realrhist[i] = (int)(3*scale * rhist[i]) > 255 ? 255 : (int)(3*scale * rhist[i]);
//            realghist[i] = (int)(3*scale * ghist[i]) > 255 ? 255 : (int)(3*scale * ghist[i]);
//            realbhist[i] = (int)(3*scale * bhist[i]) > 255 ? 255 : (int)(3*scale * bhist[i]);

            realhist[i] = (int) (256.0 / globalmax * (rhist[i] + ghist[i] + bhist[i]));
            realrhist[i] = (int) (256.0 / rmax * rhist[i]);
            realghist[i] = (int) (256.0 / gmax * ghist[i]);
            realbhist[i] = (int) (256.0 / bmax * bhist[i]);
        }
    }

    @Override
    public void paint(Graphics g) {
        fillHist();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 532, 532);

        g.setColor(Color.black);
        for (int i = 0; i < 256; i++) {
            g.drawLine(i, 256, i, 256 - realhist[i]);
        }

        g.setColor(Color.red);
        for (int i = 0; i < 256; i++) {
            g.drawLine(i + 266, 256, i + 266, 256 - realrhist[i]);
        }

        g.setColor(Color.green);
        for (int i = 0; i < 256; i++) {
            g.drawLine(i, 532, i, 532 - realghist[i]);
        }

        g.setColor(Color.blue);
        for (int i = 0; i < 256; i++) {
            g.drawLine(i + 266, 532, i + 266, 532 - realbhist[i]);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(532, 532);
    }
}
