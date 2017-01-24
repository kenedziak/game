package Lmodule;

import Controler.Controler;

import javax.naming.ldap.Control;

/**
 * Klasa implementujaca interfejs Runnable (jest w&#x105;tkiem) odpowiedzialna jest za zliczanie Score
 * */

public class Tscore implements Runnable {
    /**
     * Liczba reprezentująca wynik
     */
    int score;
    /**
     * Aktualna gra
     */
    Game g;

    /**
     * Tworzy klase Tscore, kt&oacute;ra pocz&#x105;tkowy score ustawia taki jaki jest w aktualnej grze
     * @param c controler, kt&oacute;ry posiada referencje aktualnej gry
     */
    public Tscore(Controler c) {
        this.g = c.g;
        this.score = c.g.score;
    }

    @Override
    public void run() {
            try {
                while (g.runningLVL) {
                    this.score = g.score;
                    this.score += 1;
                    g.score = this.score;

                    Thread.sleep(300);

                }
            }catch (Exception e ){
                Thread.currentThread().interrupt();

            }
    }
}