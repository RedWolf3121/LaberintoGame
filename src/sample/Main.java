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
import sample.noUtilizado.EndGameScreen;

import java.io.FileNotFoundException;


/**
 *
 * @author ener2
 */
public class Main extends Application {

    private Label lblTiempo = new Label();
    private Timeline timeline = new Timeline();
    private static final Integer TIEMPO = 60;
    private IntegerProperty segundos = new SimpleIntegerProperty(TIEMPO);
    private boolean comienzaJuego = false;
    private boolean terminado = false;


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Jugador chiqui = new Jugador();

        ImageView imgViewChiqui = new ImageView(chiqui.getImgJugadorFront());
        imgViewChiqui.setTranslateX(325);
        imgViewChiqui.setTranslateY(10);

        lblTiempo.textProperty().bind(segundos.asString());
        lblTiempo.setTextFill(Color.RED);
        lblTiempo.setStyle("-fx-font-size: 4em;");
        segundos.set(TIEMPO);

        Group panel = new Group();

        //Crea el Laberinto con las paredes
        Laberinto laberinto1 = new Laberinto();

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

        Scene scene = new Scene(panel, 600, 600);

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
                        new EndGameScreen().start(stg);
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
                    new EndGameScreen().start(stg);
                } catch (Exception ex) {
                }
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


//package sample;
//
//import javafx.animation.AnimationTimer;
//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import javafx.stage.Stage;
//
//import java.util.ArrayList;
//
//public class Main extends Application {
//
//    int X = 60;
//    int Y = 60;
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        Group root = new Group();
//        Scene sc = new Scene( root );
//        stage.setScene( sc );
//
//        Canvas canvas = new Canvas( 600 , 600 );
//        root.getChildren().add( canvas );
//
//        ArrayList<String> input = new ArrayList<String>();
//
//        sc.setOnKeyPressed(
//                e -> {
//                    String code = e.getCode().toString();
//
//                    // only add once... prevent duplicates
//                    if ( !input.contains(code) )
//                        input.add( code );
//                });
//
//        sc.setOnKeyReleased(
//                e -> {
//                    String code = e.getCode().toString();
//                    input.remove( code );
//                });
//
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//        Image left = new Image( "resources/spriteleft.png" );
//        Image right = new Image( "resources/spriteright.png" );
//
//        new AnimationTimer()
//        {
//            public void handle(long currentNanoTime)
//            {
//                // Clear the canvas
//                gc.clearRect(0,0, 600, 600);
//                gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
////                gc.fillRect(10, 100, 10, 480);
////                gc.fillRect(580, 100, 10, 480-right.getWidth());
////                gc.fillRect(10, 100, 580, 10);
////                gc.fillRect(10, 580, 580, 10);
//
//                int[] listaX = {10,10,360,10,380,300,300,200,520,10,40,210,330,10,60,60,80,120,10,260,95,500,440,500,590,545,70,10,350,10,10,350,260,555,500,510,360};
//                int[] listaY = {60,60,60,120,390,180,180,290,500,350,440,480,120,350,180,180,440,450,580,350,240,320,250,230,60,60,390,330,380,280,580,500,120,120,460,450,580};
//                int[] anchos = {300,10,240,180,180,100,230,160,80,150,120,120,160,200,190,10,220,10,50,150,170,10,10,10,10,10,10,10,10,10,300,70,10,10,10,20,240};
//                int[] largos = {10,300,10,10,10,10,10,10,10,10,10,10,10,10,10,130,10,100,10,10,10,20,250,130,500,10,100,100,40,290,10,10,290,330,100,10,10,500};
//
//                Laberinto laberinto1 = new Laberinto(listaX, listaY, anchos, largos);
//
//                if (input.contains("LEFT")) {
//                    X--;
//                    if (X <= 0) {
//                        gc.drawImage(left, X++, Y);
//                    } else {
//                    System.out.println("x:"+X);
//                        gc.drawImage(left, X, Y);
//                    }
//                } else if (input.contains("RIGHT")) {
//                    X++;
//                    //if (X >= ) {
//                   // gc.drawImage(right, X--, Y);
//                   // } else {
//                        System.out.println("x:" + X);
//                        gc.drawImage(right, X, Y);
//                   // }
//                } else if (input.contains("UP")) {
//                    Y--;
//                    gc.drawImage(left, X, Y);
//                } else if (input.contains("DOWN")) {
//                    Y++;
//                    gc.drawImage(right, X, Y);
//                }else {
//                    gc.drawImage(right, X, Y);
//                }
//
//            }
//        }.start();
//
//        stage.show();
//    }
//
//    public void generatemap(GraphicsContext graphicsContext){
//
//        graphicsContext.strokeRect(0, 0, 10, 50);
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
