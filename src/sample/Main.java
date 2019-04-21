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


public class Main extends Application {

    private Label lblTiempo = new Label();
    private Label lblPoints = new Label();
    private Label lblVidas = new Label();
    private Label lblTextDead = new Label();
    private Label lblFinalText = new Label();
    private Timeline timeline = new Timeline();
    private static final Integer TIEMPO = 50;
    protected IntegerProperty segundos = new SimpleIntegerProperty(TIEMPO);
    private boolean comienzaJuego = false;
    private boolean terminado = false;
    private int puntos;
    private int vidas;
    Group panel = new Group();


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Jugador chiqui = new Jugador();

        //Crea el Laberinto con las paredes
        Laberinto laberinto1 = new Laberinto();

        ImageView imgViewChiqui = new ImageView(chiqui.getImgJugadorFront());
        imgViewChiqui.setTranslateX(325);
        imgViewChiqui.setTranslateY(10);

        // Asocia el jugador al laberinto
        laberinto1.setJugador(chiqui);


        panel.getChildren().add(laberinto1.getInicio());
        panel.getChildren().add(laberinto1.getLlegada());
        for(Rectangle rect : laberinto1.getParedes()){
            panel.getChildren().add(rect);
        }
        panel.getChildren().addAll(laberinto1.generarMonedas());
        panel.getChildren().addAll(laberinto1.generarBombas());
        panel.getChildren().add(imgViewChiqui);
        panel.getChildren().add(lblTiempo);
        panel.getChildren().add(lblPoints);
        panel.getChildren().add(lblVidas);
        panel.getChildren().add(lblTextDead);
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
                    laberinto1.setTimeOver(true);

                    lblFinalText.setText("Time is Over");
                    lblFinalText.setTextFill(Color.RED);
                    lblFinalText.setStyle("-fx-font-size: 6em;");
                    lblFinalText.setTranslateY(200);
                    lblFinalText.setTranslateX(240);
                    panel.getChildren().add(lblFinalText);

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
                lblFinalText.setText("Winner");
                lblFinalText.setTextFill(Color.GREEN);
                lblFinalText.setStyle("-fx-font-size: 9em;");
                lblFinalText.setTranslateY(200);
                lblFinalText.setTranslateX(250);
                panel.getChildren().add(lblFinalText);
                try {
                    Stage stg = new Stage();
                    stg.setUserData(chiqui);
                } catch (Exception ex) {
                }

            }
            if (laberinto1.isTimeOver() == false && terminado == false  && laberinto1.getVidas()>=1){
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
                puntos = laberinto1.getPuntos();
                lblPoints.setText("Points " + puntos);
                lblPoints.setTextFill(Color.BLUE);
                lblPoints.setStyle("-fx-font-size: 4em;");
                lblPoints.setTranslateY(500);

                vidas = laberinto1.getVidas();
                lblVidas.setText("Vidas " + vidas);
                lblVidas.setTextFill(Color.LIGHTGREEN);
                lblVidas.setStyle("-fx-font-size: 4em;");
                lblVidas.setTranslateY(500);
                lblVidas.setTranslateX(350);

                lblTiempo.textProperty().bind(segundos.asString());
                lblTiempo.setTextFill(Color.BLUE);
                lblTiempo.setStyle("-fx-font-size: 4em;");
                segundos.set(TIEMPO);
                lblTiempo.setTranslateY(500);
                lblTiempo.setTranslateX(800);
            }
            if(laberinto1.monedaObtenida(imgViewChiqui)) {
                chiqui.setPuntaje(chiqui.getPuntaje() + 1);
            }
            if(laberinto1.bombaObtenida(imgViewChiqui)) {
                chiqui.setPuntaje(chiqui.getPuntaje() + 1);
            }
            if (laberinto1.getVidas() == 0){
                lblTextDead.setText("\n" + "you have died stronzo");
                lblTextDead.setTextFill(Color.RED);
                lblTextDead.setStyle("-fx-font-size: 6em;");
                lblTextDead.setTranslateY(100);
                lblTextDead.setTranslateX(130);
            }
        });

        primaryStage.setTitle("Laberinto!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // bloquea el tamano de la ventana
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
