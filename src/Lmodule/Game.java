package Lmodule;

import Controler.Config;
import Controler.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Klasa realizuj&#x105;ca modu&#x142; gry.
 */
public class Game {
    /**
     * Zmienna informuj&#x105;ca o stanie gry.
     */
    boolean runningLVL;
    /**
     * Zmienna, kt&oacute;ra utrzmuje referencje do w&#x105;tku odpowiedzialnego za naliczanie punkt&oacute;w
     */

    Thread scoreThread;
    /**
     * Zmienna, kt&oacute;ra utrzmuje referencje do w&#x105;tku odpowiedzialnego za ruch map&#x105;
     */
    Thread mapRefresh;

    /**
     * Zmienna, kt&oacute;ra zawiera obraz pe&#x142;nej mapy aktualnego poziomu
     */
    public BufferedImage mapImg;
    /**
     * Zmienna informuj&#x105;ca o ile mapa si&#x119; przesun&#x119;&#x142;a
     */
    public int threadTimes;

    /**
     * Zmieraj&#x105;ca informacje dotycz&#x105;ce konfiguracji
     */
    Config config;
    /**
     * Aktualny wynik
     */
    public int score;
    /**
     * Maksymalna liczba poziom&oacute;w do rozegrania
     */
    int maxLvl;
    /**
     * Numer aktualnego poziomu
     */
    int acutalLVL;
    /**
     * Zmienna z referencj&#x105; do Controlera
     */
    Controler c;

    /**
     * Liczba pozosta&#x142;ych &#x17c;y&#x107;
     */
    public int lifes;
    /**
     * Aktualnie u&#x17c;ywana mapa
     */
    LvlMap actualMap;
    /**
     * wsp&oacute;&#x142;rz&#x119;dna x postaci
     */
    public int xp;
    /**
     * wsp&oacute;&#x142;rz&#x119;dna y postaci
     */
    public int yp;
    /**
     * wsp&oacute;&#x142;rz&#x119;dna x postaci przemapowana na piksele
     */
    public int pxp;
    /**
     * wsp&oacute;&#x142;rz&#x119;dna y postaci przemapowana na piksele
     */
    public int pyp;

    /**
     * Tworzy klas&#x119;&nbsp;game oraz &#x142;aduje konfiguracj&#x119;.
     */
    public Game() {
        this.score = 0;
        this.threadTimes = 0;
        this.config = new Config();
        this.lifes = this.config.getLifeNumber();
        this.maxLvl = config.getLevelNumber();
        this.runningLVL =false;

    }

    /**
     * Inicjuje wszystkie elementy gry
     * @param c u&#x17c;ywany kontorler
     */
    public void gameInit(Controler c ){
        this.threadTimes=0;
        this.acutalLVL= 0;
        this.score =0;
        this.c = c;
        this.startLVL(0);
    }

//

    /**
     * metoda &#x142;aduj&#x105;ca nowe poziom
     * @param lvlNumber numer poziomu
     */
    public void startLVL(int lvlNumber){

        this.actualMap = new LvlMap( this.config.lvlMap.get(lvlNumber));

         this.loadLvl();

    }
    private void loadLvl() {
        this.runningLVL = true;
        this.mapRefresh = new Thread(new TMapRefresh(this.actualMap, this.c,0));
        this.scoreThread = new Thread(new Tscore(c));
        this.mapRefresh.setPriority(10);
        this.xp = this.actualMap.rowSize/2;
        this.yp = 5;
        this.pxp = this.xp*64;
        this.pyp = this.yp *64;
        this.mapRefresh.start();
        this.scoreThread.start();

    }


    /**
     * Metoda sprawdzaj&#x105;ca, czy nie dosz&#x142;o do zderzenia samochodu z elementami nieporz&#x105;danymi
     */
     public void check() {
        boolean is = false;
        if (runningLVL) {
        int rpositiony = (this.actualMap.rowSize * 64 - this.pxp) / 64;
            rpositiony = this.actualMap.rowSize - 1 - rpositiony;
            int rpositionx = (-this.pyp + this.threadTimes + 10 * 64) / 64;

            int rpositiony2 = (this.actualMap.rowSize * 64 - this.pxp - 39) / 64;
            rpositiony = this.actualMap.rowSize - 1 - rpositiony;
            int rpositionx2 = (-this.pyp - 70 + this.threadTimes + 10 * 64) / 64;
            if (this.actualMap.intMap[rpositionx][(rpositiony)] == 1) {
                is = true;

            }
            if (this.actualMap.intMap[rpositionx][rpositiony2] == 1) {
                is = true;
            }

            if (this.actualMap.intMap[rpositionx2][rpositiony] == 1) {
                is = true;
                }
            if (this.actualMap.intMap[rpositionx2][rpositiony2] == 1) {
                is = true;
            }

            if (this.actualMap.intMap[rpositionx][(rpositiony)] == 3) {
                this.bill(rpositionx,rpositiony);

            }
            if (this.actualMap.intMap[rpositionx][rpositiony2] == 3) {
                this.bill(rpositionx,rpositiony2);

            }

            if (this.actualMap.intMap[rpositionx2][rpositiony] == 3) {
                this.bill(rpositionx2,rpositiony);

            }
            if (this.actualMap.intMap[rpositionx2][rpositiony2] == 3) {
                this.bill(rpositionx2,rpositiony2);

            }

            if (is) {
                    this.lvlNotFinished();

            }

        }
    }


    private void bill(int rpositionx, int rpositiony) {
        Graphics2D g2 = mapImg.createGraphics();
        BufferedImage img2 = null;
        try {
            img2 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_07.png/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(img2,rpositionx,rpositiony,null);
        this.score = this.score - 50;
        this.actualMap.intMap[rpositionx][(rpositiony)] =0;
        this.allMap();

    }

    public void moveUp() {
        if(this.runningLVL) {
            if (pyp > 0 ){
                this.pyp =this.pyp -5 ;
                this.yp = pyp / 64;
            }
            this.check();
        }
    }
    public void moveDown() {
        if(this.runningLVL) {
                if(pyp < 570 ){
                this.pyp = this.pyp + 5;
                this.yp = pyp / 64;

            }
    }

        this.check();
    }
    public void moveRight() {
        if(this.runningLVL) {
            if (pxp < 64 *this.actualMap.rowSize - 39 ) {

                this.pxp = pxp +5;
                this.xp = pxp / 64;

            }
        }

        this.check();
    }
    public void  moveLeft() {
        if(this.runningLVL) {
            if (pxp > 0) {
                this.pxp = pxp -5;
                this.xp = pxp / 64;

            }

        }

        this.check();
    }
    public void pause() {
        if(this.runningLVL){
            this.runningLVL =false;
            this.mapRefresh.interrupt();
          //  this.movMap.interrupt();
           // this.scoreThread.interrupt();
            while(!this.mapRefresh.isInterrupted()){}
            this.c.pauseWindow();


        }
        else{
            //this.movMap = new Thread(new TmapMove(this.actualMap, this.c,this.threadTimes));
            this.mapRefresh = new Thread(new TMapRefresh(this.actualMap, this.c,this.threadTimes));
            //this.scoreThread = new Thread(new Tscore(this.c));
            this.runningLVL =true;
            this.mapRefresh.setPriority(10);
            //this.movMap.start();
            this.mapRefresh.start();
           // this.scoreThread.start();

        }

    }
    /**
     * Metoda, kt&oacute;ra jest wykonywana po zako&#x144;czeniu gry
     */
    public void gameOver() {
        this.scoreThread.interrupt();
        System.out.println("GAME OVER");
       // this.config.checkScore(this.score);
        c.gameOver();
    }


    private BufferedImage[][] changeForBufferedImage(int[][] x,int rows) {

        BufferedImage[][] bImap;
        bImap = new BufferedImage[12][rows];
        try {
            BufferedImage img1 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_01.png/"));
            BufferedImage img2 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_07.png/"));
            BufferedImage img3 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/car_black_small_3.png"));
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < rows; j++) {
                    if (x[i][j] == 1) {
                        bImap[i][j] = img1;
                    }
                    if (x[i][j] == 0) {
                            bImap[i][j] = img2;
                        }
                    }

                }


        }catch (IOException e) {
            e.printStackTrace();
        }


        return bImap;
    }

    /**
     * Metoda, kt&oacute;ra jest wykonywana podczas sko&#x144;czenia poziomu, &#x142;aduje ona mape nowego poziomu
     */
    synchronized public void lvlFinished() {
        if (this.runningLVL) {
            System.out.println("lvl finished");
            this.mapRefresh.interrupt();
            while (!this.mapRefresh.isInterrupted()) {
            }
            //this.actualMap.intMap[xp][yp] = 0;
            this.threadTimes = 0;
            this.xp = this.actualMap.rowSize / 2;
            this.yp = 5;
            this.pxp = this.xp * 64;
            this.pyp = this.yp * 64;

            this.lifes++;
            this.acutalLVL++;
//        if(acutalLVL == this.config.getLevelNumber()){
//            this.runningLVL = false;
//            this.mapRefresh.interrupt();
//            this.movMap.interrupt();
//            this.gameOver();
//            return;
//        }
            this.runningLVL = false;
            this.startLVL(this.acutalLVL % (this.config.mapNumber - 1));
        }
    }

    /**
     * Metoda, kt&oacute;ra jest wykonywana podczas zderzenia, &#x142;aduje ona aktualn&#x105; map&#x119; od pocz&#x105;tku
     */
    synchronized  public void lvlNotFinished()  {
        if (this.runningLVL) {
            this.runningLVL = false;

            this.mapRefresh.interrupt();
            this.scoreThread.interrupt();
            this.mapRefresh.isDaemon();
            this.scoreThread.isDaemon();


            this.threadTimes = 0;
            this.xp = this.actualMap.rowSize / 2;
            this.yp = 5;
            this.pxp = this.xp * 64;
            this.pyp = this.yp * 64;

            this.lifes--;
            if (this.lifes == 0) {
                this.gameOver();
            } else {
                this.startLVL(this.acutalLVL);
            }
        }
    }


    /**
     * Metoda generuj&#x105;ca map&#x119; kt&oacute;ra jest wy&#x15b;wietlana na ekranie
     * @return obraz mapy do wy&#x15b;wietlenia w oknie
     */
    public BufferedImage generatemap() {

        BufferedImage bufferedImage = new BufferedImage(36*this.actualMap.rowSize,36*this.actualMap.mapSize,TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();


        BufferedImage[][] bImap;
        bImap = new BufferedImage[this.actualMap.mapSize][this.actualMap.rowSize];
        try {
            BufferedImage img1 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_01.png/"));
            BufferedImage img2 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_07.png/"));
            BufferedImage img3 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/car_black_small_3.png"));
            for (int i = 0; i < this.actualMap.mapSize; i++) {
                for (int j = 0; j < this.actualMap.rowSize; j++) {
                    if (this.actualMap.intMap[i][j] == 1) {
                        bImap[i][j] = img1;
                    }
                    if (this.actualMap.intMap[i][j] == 0) {
                        bImap[i][j] = img2;
                    }
//                    if (this.actualMap.intMap[i][j] == 3) {
//                        bImap[i][j] = img3;
//                    }


                    g2.drawImage(bImap[i][j],36*11 -36*i,36*j,null);
                }
            }
                g2.drawImage(img3,this.pxp,this.pyp,null);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;

//        for (int i = 0; i < this.actualMap.mapSize; i++) {
//            for (int j = 0; j < this.actualMap.rowSize; j++) {
//                //c = bImap[actualMap.mapSize - i][this.actualMap.rowSize - 1 - j];
//                //full.createGraphics().drawImage(
//            }
//        }
    }

    /**
     * Metoda od&#x15b;wie&#x17c;aj&#x105;ca okno gry
     * @param x obraz, kt&oacute;ry ma zosta&#x107; wy&#x15b;wietlony w oknie gry
     */
    public void reload(BufferedImage x){
        this.c.reload(x);

    }

    private BufferedImage partMap(int [] [] map) {

        BufferedImage bufferedImage = new BufferedImage(64*this.actualMap.rowSize,64*12,TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();

        BufferedImage bImap = null;
        try {
            BufferedImage img1 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_01.png/"));
            BufferedImage img2 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_07.png/"));
            BufferedImage img3 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/car_black_small_3.png"));
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < this.actualMap.rowSize; j++) {
                    if (this.actualMap.intMap[i][j] == 1) {
                        bImap = img1;
                    }
                    if (this.actualMap.intMap[i][j] == 0) {
                        bImap = img2;
                    }
                    g2.drawImage(bImap,this.actualMap.rowSize*64-64 - 64*j,11*64-64*i,null);
                }
            }
            g2.drawImage(img3,this.actualMap.rowSize*64-this.pxp,11*64-this.pyp,null);

        }catch (IOException e) {
            e.printStackTrace();
        }
        this.mapImg = bufferedImage;
        return bufferedImage;

    }

    public BufferedImage allMap() {

        BufferedImage bufferedImage = new BufferedImage(64*this.actualMap.rowSize,64*this.actualMap.mapSize,TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();

        BufferedImage bImap = null;
        try {
            BufferedImage img1 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_01.png/"));
            BufferedImage img2 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/Tiles/tile_07.png/"));
            BufferedImage img3 = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/bill.png/"));
            for (int i = 0; i < this.actualMap.mapSize; i++) {
                for (int j = 0; j < this.actualMap.rowSize; j++) {
                    if (this.actualMap.intMap[i][j] == 1) {
                        bImap = img1;
                    }
                    if (this.actualMap.intMap[i][j] == 0) {
                        bImap = img2;
                    }
                    if(this.actualMap.intMap[i][j] == 3){
                        g2.drawImage(img2,this.actualMap.rowSize*64-64 - 64*j,this.actualMap.mapSize*64-64-64*i,null);
                        bImap = img3;

                    }
                    g2.drawImage(bImap,this.actualMap.rowSize*64-64 - 64*j,this.actualMap.mapSize*64-64-64*i,null);
                }
            }
           // g2.drawImage(img3,this.actualMap.rowSize*64-this.pxp,11*64-this.pyp,null);

        }catch (IOException e) {
            e.printStackTrace();
        }
        this.mapImg = bufferedImage;
        return bufferedImage;

    }



}