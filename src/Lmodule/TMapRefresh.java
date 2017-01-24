package Lmodule;

import Controler.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;


/**
 * Klasa implementuj&#x105;ca interfejs Runnable (W&#x105;tek) kt&oacute;ra ma za zadanie od&#x15b;wierza&#x107; obraz okna gry
 */
public class TMapRefresh implements Runnable {
    private BufferedImage img3;

   private LvlMap map;
    /**
     * Aktualna gra
     */
    Game g;
    BufferedImage tmpimg;
    private int tmp;
    private int tTimes;
    private int pxp;
    private int pyp;
    private int nref;
    private BufferedImage mapImg;
    /**
     * Parametr defniuj&#x105;cym dzia&#x142;anie w&#x105;tka
     */
    public boolean go = true;

    public TMapRefresh(LvlMap map, Controler c, int tTimes) {
        try {
            this.img3 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/car_black_small_3.png"));
            this.tmpimg = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_12.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.mapImg = c.g.allMap();
        this.nref = tTimes;
        this.nref = 0;

        this.go = true;
        this.map = map;
        this.tTimes = tTimes;
        this.g = c.g;

    }
    @Override
    public void run() {
        try {

            this.go = g.runningLVL;
            int x;

            BufferedImage partMap;
            BufferedImage copyOfImage = new BufferedImage(64 * this.g.actualMap.rowSize, 640, TYPE_INT_RGB);
            while (go) {

                this.mapImg = this.g.mapImg;
                this.go = g.runningLVL;

                x = 64 * (this.g.actualMap.mapSize - 10) - nref;

                try {
                    partMap = this.mapImg.getSubimage(0, x, this.g.actualMap.rowSize * 64, 640);
                    Graphics g = copyOfImage.createGraphics();
                    g.drawImage(partMap, 0, 0, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Graphics2D g2 = copyOfImage.createGraphics();
                g2.drawImage(this.img3, g.pxp, g.pyp, null);


                if (this.g.runningLVL) {
                    this.g.c.reload(copyOfImage);
                } else {
                    Thread.interrupted();
                }


                Thread.sleep(30);

                this.pxp = this.g.pxp;
                this.pyp = this.g.pyp;
                g.threadTimes = this.nref;
                this.nref++;
                if (nref >= (this.g.actualMap.mapSize - 10) * 64) {
                    g.lvlFinished();
                    break;
                }
                this.g.check();

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        }
    }
}