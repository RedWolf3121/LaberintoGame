package sample;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Laberinto {
    private Image imgtile;
    private Jugador jugador;
    private Rectangle inicio;
    private Rectangle llegada;
    private List<Rectangle> paredes;
    private List<ImageView> listaMonedas;
    private List<ImageView> listBomba;
    private int puntos = 0;
    private int vidas = 3;
    private boolean timeOver;





    int[][] tiles = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0,0,0,1,1,1,1,1,1,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
            {1,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0,1},
            {1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,0,1,0,0,0,1,0,0,1},
            {1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,0,1,1,1,1,1,0,0,1},
            {1,0,0,1,0,0,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,1},
            {1,0,0,0,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,1},
            {1,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,1,0,0,1},
            {1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };



    int tileSize = 20;

    public Laberinto() throws FileNotFoundException {

        listaMonedas = new ArrayList<>();
        listBomba = new ArrayList<>();
        paredes = new ArrayList<>();


        for (int i = 0; i <tiles.length ; i++) {
            for (int j = 0; j <tiles[i].length ; j++) {
                if (tiles[i][j] == 1){
                    //paredes.add(gc.drawImage(imgtile, j*tileSize, i*tileSize));
                    paredes.add(new Rectangle(j*tileSize, i*tileSize, tileSize, tileSize));
                } else if(tiles[i][j] == 2){
                    inicio  = new Rectangle(j*tileSize,i*tileSize,tileSize,tileSize);
                    inicio.setFill(Color.GREENYELLOW);
                }  else if(tiles[i][j] == 3){
                    llegada  = new Rectangle(j*tileSize,i*tileSize,tileSize,tileSize);
                    llegada.setFill(Color.GREENYELLOW);
                }
            }
        }

    }

    public List<Rectangle> getParedes() {
        return paredes;
    }

    public void setParedes(List<Rectangle> paredes) {
        this.paredes = paredes;
    }

    public List<ImageView> getListaMonedas() {
        return listaMonedas;
    }

    public void setListaMonedas(List<ImageView> monedas) {
        this.listaMonedas = monedas;
    }

    public Rectangle getInicio() {
        return inicio;
    }

    public void setInicio(Rectangle inicio) {
        this.inicio = inicio;
    }

    public Rectangle getLlegada() {
        return llegada;
    }

    public void setLlegada(Rectangle llegada) {
        this.llegada = llegada;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean isTimeOver() {
        return timeOver;
    }

    public void setTimeOver(boolean timeOver) {
        this.timeOver = timeOver;
    }

    public List<ImageView> getListBomba() {
        return listBomba;
    }

    public void setListBomba(List<ImageView> listBomba) {
        this.listBomba = listBomba;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    /**
     * Genera las posiciones de las monedas dentro del laberinto
     * de forma aleatoria
     * @return
     */
    public List<ImageView> generarMonedas() {
        Image moneda = new Image("resources/moneda-pixel.png");
        Random generador = new Random();

        for (int i = 1; i<=30; i++){
            Double coordX = 1000 * generador.nextDouble() % (880 - 25) + 25;
            Double coordY = 1000 * generador.nextDouble() % (420 - 75) + 75;
            ImageView imgViewMoneda = new ImageView (moneda);
            imgViewMoneda.setTranslateX(coordX);
            imgViewMoneda.setTranslateY(coordY);
            if(comprobarColision(imgViewMoneda)){
                i--;
            } else {
                listaMonedas.add(imgViewMoneda);
            }
        }
        return listaMonedas;
    }

    /**
     * Comprueba si los bordes de la imagen colisionan con cualquiera de los
     * rectangulos
     * @param imgView componente con la imagen
     * @return true si se detecta alguna colision, sino falso
     */
    public boolean comprobarColision(ImageView imgView) {
        for(Rectangle pared : this.getParedes()) {
            if(imgView.getBoundsInParent().intersects(pared.getBoundsInParent())){
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo encargado de validar si el jugador toca la moneda
     * @param imgView
     * @return
     */
    public boolean monedaObtenida(ImageView imgView) {
        for(ImageView moneda : listaMonedas) {
            if(imgView.getBoundsInParent().intersects(moneda.getBoundsInParent()) && moneda.isVisible()){
                moneda.setVisible(false);

                puntos++;

                System.out.println("Puntos: " + puntos);
                return true;
            }
        }
        return false;
    }

    /**
     * Genera las posiciones de las bombas dentro del laberinto
     * de forma aleatoria
     * @return
     */
    public List<ImageView> generarBombas() {
        Image bomba = new Image("resources/bomba.png");
        Random generador = new Random();

        for (int i = 1; i<=5; i++){
            Double coordX = 1000 * generador.nextDouble() % (880 - 25) + 25;
            Double coordY = 1000 * generador.nextDouble() % (420 - 75) + 75;
            ImageView imgViewBomba = new ImageView (bomba);
            imgViewBomba.setTranslateX(coordX);
            imgViewBomba.setTranslateY(coordY);
            if(comprobarColision(imgViewBomba)){
                i--;
            } else {
                listBomba.add(imgViewBomba);
            }
        }
        return listBomba;
    }

    public boolean bombaObtenida(ImageView imgView) {
        for(ImageView bomba : listBomba) {
            if(imgView.getBoundsInParent().intersects(bomba.getBoundsInParent()) && bomba.isVisible()){
                bomba.setVisible(false);
                vidas--;
                System.out.println("Vidas: " + vidas);

                return true;
            }
        }
        return false;
    }
}