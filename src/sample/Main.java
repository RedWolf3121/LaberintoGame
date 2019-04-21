package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;


/**
 *
 * @author ener2
 */
public class Main extends Application {

    private Label lblTiempo = new Label();
    private Label lblPoints = new Label();
    private Timeline timeline = new Timeline();
    private static final Integer TIEMPO = 60;
    private IntegerProperty segundos = new SimpleIntegerProperty(TIEMPO);
    private boolean comienzaJuego = false;
    private boolean terminado = false;
    int puntos;



    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Jugador chiqui = new Jugador();

        //Crea el Laberinto con las paredes
        Laberinto laberinto1 = new Laberinto();

        ImageView imgViewChiqui = new ImageView(chiqui.getImgJugadorFront());
        imgViewChiqui.setTranslateX(325);
        imgViewChiqui.setTranslateY(10);

        lblTiempo.textProperty().bind(segundos.asString());
        lblTiempo.setTextFill(Color.RED);
        lblTiempo.setStyle("-fx-font-size: 4em;");
        segundos.set(TIEMPO);

        puntos = laberinto1.getPuntos();
        lblPoints.setText("Points" + puntos);
        lblPoints.setTextFill(Color.RED);
        lblPoints.setStyle("-fx-font-size: 4em;");



        Group panel = new Group();


        // Asocia el jugador al laberinto
        laberinto1.setJugador(chiqui);

        // Crea el inicio
//        Rectangle inicio = new Rectangle(300,60,60,10);
//        inicio.setFill(Color.GREENYELLOW);
//        laberinto1.setInicio(inicio);

        // Crea la llegada
//        Rectangle llegada = new Rectangle(300,580,60,10);
//        llegada.setFill(Color.GREENYELLOW);
//        laberinto1.setLlegada(llegada);

        panel.getChildren().add(laberinto1.getInicio());
        panel.getChildren().add(laberinto1.getLlegada());
        for(Rectangle rect : laberinto1.getParedes()){
            panel.getChildren().add(rect);
        }
        panel.getChildren().addAll(laberinto1.generarMonedas());
        panel.getChildren().add(imgViewChiqui);
        panel.getChildren().add(lblTiempo);
        panel.getChildren().add(lblPoints);
        Scene scene = new Scene(panel, 900, 600);

        scene.setOnMouseClicked((MouseEvent evt ) -> {
            System.out.println("Click!");
            System.out.println("(" + evt.getX() + ", " + evt.getY() + ")");
        });

        scene.setOnKeyPressed((KeyEvent evt)->{

            if(imgViewChiqui.getBoundsInParent().intersects(laberinto1.getInicio().getBoundsInParent()) && !comienzaJuego) {
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(TIEMPO + 1),
                                new KeyValue(segundos, 0)));
                timeline.playFromStart();
                comienzaJuego = true;
                timeline.playFromStart();
                timeline.setOnFinished((ActionEvent fin)->{
                    System.out.println("Se acabó el tiempo!!!!!!");
                    chiqui.setGanador(false);
                    try {
                        Stage stg = new Stage();
                        stg.setUserData(chiqui);
                    } catch (Exception ex) {
                    }
                });

            }

            if(imgViewChiqui.getBoundsInParent().intersects(laberinto1.getLlegada().getBoundsInParent()) && !terminado) {
                timeline.stop();
                terminado = true;
                chiqui.setGanador(true);
                try {
                    Stage stg = new Stage();
                    stg.setUserData(chiqui);
                } catch (Exception ex) {
                }
                System.exit(0);
            }

            switch(evt.getCode()) {
                case UP:

                    imgViewChiqui.setTranslateY(imgViewChiqui.getTranslateY()-5);
                    imgViewChiqui.setImage(chiqui.getImgJugadorBack());

                    if(laberinto1.comprobarColision(imgViewChiqui)) {
                        imgViewChiqui.setTranslateY(imgViewChiqui.getTranslateY()+5);
                    }
                    break;
                case DOWN:

                    imgViewChiqui.setTranslateY(imgViewChiqui.getTranslateY()+5);
                    imgViewChiqui.setImage(chiqui.getImgJugadorFront());

                    if(laberinto1.comprobarColision(imgViewChiqui)) {
                        imgViewChiqui.setTranslateY(imgViewChiqui.getTranslateY()-5);
                    }

                    break;
                case LEFT:
                    imgViewChiqui.setTranslateX(imgViewChiqui.getTranslateX()-5);
                    imgViewChiqui.setImage (chiqui.getImgJugadorLeft());

                    if(laberinto1.comprobarColision(imgViewChiqui)) {
                        imgViewChiqui.setTranslateX(imgViewChiqui.getTranslateX()+5);
                    }
                    break;
                case RIGHT:
                    imgViewChiqui.setTranslateX(imgViewChiqui.getTranslateX()+5);
                    imgViewChiqui.setImage (chiqui.getImgJugadorRight());

                    if(laberinto1.comprobarColision(imgViewChiqui)) {
                        imgViewChiqui.setTranslateX(imgViewChiqui.getTranslateX()-5);
                    }
                    break;
            }
            if(laberinto1.monedaObtenida(imgViewChiqui)) {
                chiqui.setPuntaje(chiqui.getPuntaje() + 1);
            }
            //System.out.println("(" + imgViewChiqui.getTranslateX() + ", " + imgViewChiqui.getTranslateY() + ")");
        });

        primaryStage.setTitle("Laberinto!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // bloquea el tamano de la ventana
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
