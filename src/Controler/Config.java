package Controler;

import java.io.*;
import java.util.ArrayList;
/**
 * Klasa odpowiedzialna, za &#x142;adowanie pliku konfiguracyjnego. &#x141;aduje wszystkie informacje odno&#x15b;nie gry.
 */

public class Config {
    private int lifeNumber;
    private int levelNumber;
    private int[] bestScore;
    /**
     * Zmienna okre&#x15b;laj&#x105;ca ilo&#x15b;&#x107; map wczytanych z pliku konfiguracyjnego
     */
    public int mapNumber;
    /**
     * Zmienna przechowuj&#x105;ca mapy wczytane z pliku konfiguracyjnego
     */
    public ArrayList<LvlMap> lvlMap;


    /**
     * Tworzy now&#x105; klas&#x119; Config, kt&oacute;ra ma za&#x142;adowan&#x105; konfiguracje z pliku configuration.txt
     */
    public Config() {
        this.bestScore = new int[3];
        this.lvlMap = new ArrayList<LvlMap>();
        this.loadConfiguration();
    }

    /**
     * Metoda analizuje plik configuration.txt oraz na jego podstawie ustawia warto&#x15b;ci w klasie Config
     */

    public void loadConfiguration() {
        String configPath = Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "configuration.txt/";

        try {
            int rowSizeMap = 0;

            BufferedReader br = null;
            String sCurrentLine = null;
            br = new BufferedReader(new FileReader(configPath));
            sCurrentLine = br.readLine();
            if (sCurrentLine != null) {

                String[] splited = sCurrentLine.split(" ");
                this.lifeNumber = Integer.parseInt(splited[1]);
            }
            sCurrentLine = br.readLine();
            if (sCurrentLine != null) {
                String[] splited = sCurrentLine.split(" ");
                this.levelNumber = Integer.parseInt(splited[1]);
            }
            for (int i = 0; i < 3; i++) {
                sCurrentLine = br.readLine();
                if (sCurrentLine != null) {
                    String[] splited = sCurrentLine.split(" ");
                    this.bestScore[i] = Integer.parseInt(splited[1]);
                } else {
                    this.bestScore[i] = 0;
                }
            }

            while (!br.readLine().equals("-")) {
                br.readLine();
            }
            this.mapNumber = Integer.parseInt(br.readLine());

            for(int c=0 ; c < this.mapNumber;c++) {
                ArrayList<ArrayList> rows = new ArrayList<ArrayList>();
                rows.clear();
                ArrayList<Integer> colum = new ArrayList<Integer>();
                colum.clear();
                while (!br.readLine().equals("/")) {
                    br.readLine();
                }
                rowSizeMap = Integer.parseInt(br.readLine());
                String x = "";
                x = br.readLine();
                while (!x.equals("//")) {
                    colum = new ArrayList<Integer>();

                    String[] splited = x.split(" ");
                    for (int j = 0; j < rowSizeMap; j++) {
                        colum.add(Integer.parseInt(splited[j]));
                    }
                    rows.add(colum);
                    x = br.readLine();

                }

                int[][] map = new int[rows.size()][rowSizeMap];

                for (int i = 0; i < rows.size(); i++) {
                    ArrayList<Integer> show = rows.get(i);
                    for (int j = 0; j < rowSizeMap; j++) {
                        map[i][j] = show.get(j);
                    }
                }
                this.lvlMap.add(new LvlMap(rowSizeMap,rows.size(), map));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Nie moge otworzyc pliku configuracyjnego!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Metoda pomocnicza, kt&oacute;ra na STDOUT wypisuje za&#x142;adowan&#x105; konfiguracje
     */

    public void showConfiguration() {
        System.out.println("Ilosc zyc " + this.lifeNumber);
        System.out.println("Ilosc poziomow " + this.levelNumber);
        System.out.println("Najlepsze wyniki:" + this.bestScore[0] + " " + this.bestScore[1] + " " + this.bestScore[2]);
        LvlMap tmp;
        for(int i = 0 ; i < this.mapNumber;i++){
            tmp = this.lvlMap.get(i);
            System.out.println("MAPA "+i +" "+tmp.mapSize + " "+ tmp.rowSize);
            for(int c = 0; c < tmp.mapSize ; c++){
                for(int j = 0 ; j < tmp.rowSize;j++){
                    System.out.print(tmp.intMap[c][j]);
                }

                System.out.println("  "+ c);

            }


        }


    }

    /**
     * Metoda zrwazaj&#x105;ca ilo&#x15b;&#x107; &#x17c;y&#x107;
     * @return lifenumber
     */

    public int getLifeNumber() {
        return lifeNumber;
    }
    /**
     * Metoda zwracaj&#x105;ca 3 najwy&#x17c;sze wyniki uzyskane do tej pory w grach
     * @return bestScore
     */

    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Metoda zwracaj&#x105;ca 3 najwy&#x17c;sze wyniki uzyskane do tej pory w grach
     * @return bestScore
     */

    public int[] getBestScore() {
        return bestScore;
    }

    private void checkScore(int score) {
        int tmp=0;
        for(int i = 0 ; i  < 3 ;i++){
            if(score > this.bestScore[i]){
                tmp = bestScore[i];
                bestScore[i] = score;
                checkScore(tmp);
                break;
            }

        }
        String configPath = Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "configuration.txt/";
        PrintWriter br;
        try {
            br = new PrintWriter(configPath);
            br.println();
            br.println();
            br.write("Wynik_1 "+ this.bestScore[0]);
            br.println();
            br.write("Wynik_2 "+ this.bestScore[1]);
            br.println();
            br.write("Wynik_3 "+ this.bestScore[2] +"\n");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}


