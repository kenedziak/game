package Controler;
/**
 * Klasa reprezentuj&#x105;ca map&#x119; &#x142;adowan&#x105; z pliku konfiguracyjnego
 **/
public class LvlMap{
        /**
         * Zmienna reprezentuj&#x105;ca ca&#x142;kowit&#x105; map&#x119;&nbsp;
         */

        public int [] [] intMap;
        /**
         * Zmienna reprezentuj&#x105;ca szeroko&#x15b;&#x107; mapy&nbsp;
         */

        public int rowSize;
        /**
         * Zmienna reprezentuj&#x105;ca d&#x142;ugo&#x15b;&#x107; mapy&nbsp;
         */

        public int mapSize;
        /**
         *  Tworzy now&#x105; klas&#x119; reprezentuj&#x105;c&#x105; map&#x119;
         *  @param map mapa int&oacute;w reprezentuj&#x105;ca map&#x119;
         *  @param mapSize d&#x142;ugo&#x15b;&#x107; mapy
         *  @param rowSize szeroko&#x15b;&#x107; mapy
         */

        public LvlMap(int rowSize ,int mapSize, int [] [] map){
                this.intMap = map;
                this.rowSize =rowSize;
                this.mapSize = mapSize;


        }
        /**
         *  Konstruktor kopiuj&#x105;cy
         *  @param  an element do kopiowania
         */

        public LvlMap(LvlMap an) {
                this.mapSize = an.mapSize;
                this.rowSize = an.rowSize;
                this.intMap= new int[an.mapSize][an.rowSize];
                for(int i = 0 ; i < an.mapSize;i++) {
                        for (int j = 0; j < an.rowSize; j++) {
                        this.intMap[i][j] = an.intMap[i][j];

                        }
                }
                }


        }


