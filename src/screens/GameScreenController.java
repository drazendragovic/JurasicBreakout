/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import Interface.iScreenControlable;
import Models.Ball;
import Models.BasicBrick;
import Models.Boulders;
import Models.Brick;
import Utilities.ScreensController;
import Models.Paddle;
import Models.PowerUp;
import Models.Powers;
import Models.SoundClip;
import NetworkGame.Chat.Chat;
import NetworkGame.Chat.ChatClient;
import NetworkGame.Chat.ChatListener;
import NetworkGame.Chat.ChatServer;
import Utilities.FileFactory;
import Utilities.GameRecorder.GamePlayer;
import Utilities.GameRecorder.GameRecorder;
import Utilities.Timer;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javax.xml.bind.JAXBException;
import jurasicbreakout.JurasicBreakout;
import static jurasicbreakout.JurasicBreakout.getMediaPlayer;

/**
 * FXML Controller class
 *
 * @author Drazen Dragovic
 */
public class GameScreenController implements Initializable, iScreenControlable, ChatListener {

    ScreensController controller;

    private static GameScreenController gsc;

    public static GameScreenController getGsc() {
        return gsc;
    }

    public static void setGsc(GameScreenController gsc) {
        GameScreenController.gsc = gsc;
    }

    public static final double WIDTH = 997;
    public static final double HEIGHT = 619;
    public static final int FPS = 60;
    public static final int MILLSEC_DELAY = 1000 / FPS;
    public static final double SEC_DELAY = 1.0 / FPS;
    public static final double PADDLE_WIDTH = 75;
    public static final double PADDLE_HEIGHT = 20;
    public static final double BRICK_SEPARATION = 1;
    public static final int NUM_COLUMN = 12;
    public static final int NUM_ROW = 7;
    public static final double BRICK_WIDTH = (WIDTH - (NUM_COLUMN - 1) * BRICK_SEPARATION) / NUM_COLUMN;
    public static final double BRICK_HEIGHT = 50;
    public static final double PADDLE_SPEED = 10.0;
    public static final double GAME_SPEED = 200.0;
    public static final double POWERS = .1;
    public static final double BOULDER = .1;
    public static final int LIFE = 4;

    private Group gameGroup = new Group();
    private ArrayList<Brick> bricks = new ArrayList<>();
    private ArrayList<Paddle> paddles = new ArrayList<>();
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Powers> powers = new ArrayList<Powers>();
    private Set<KeyCode> activeKeys;
    private Timeline animation;
    private List<Object> data;
    private List<Object> netData;

    private final String saveLoadDir = "src/Save_Load/";
    private final String recDir = "src/Recordings/";
    private static String filename = "";

    private Paddle paddle1;
    private Paddle paddle2;
    private int level = 1;
    private double ballHeight;
    private double ballWidth;
    private int p1Score = 0;
    private int p2Score = 0;
    private String score1;
    private String score2;

    private final Image bal1 = new Image("/Images/ball1.png");
    private final Image life = new Image("/Images/life.png");

    private final String padpl1 = "/Images/paddle1.png";
    private final String padpl2 = "/Images/paddle2.png";
    private final String balpl1 = "/Images/ball1.png";
    private final String balpl2 = "/Images/ball2.png";
    private final String lifeImg = "/Images/life.png";

    private final SoundClip bounce = new SoundClip("/Sounds/footstep.wav");
    private final SoundClip rapcall = new SoundClip("/Sounds/dilo1_short.wav");
    private final SoundClip rapcall2 = new SoundClip("/Sounds/rapcall_short.wav");

    private ImageView ivlife1;
    private ImageView ivlife2;
    private Timer timerPl1;
    private Timer timerPl2;
    private String pl1Name = "Player 1";
    private String pl2Name = "Player 2";
    private String currentPlayer;

    private boolean game = false;
    private boolean start = false;
    private boolean recIsStarted = false;

    final double minX = -428;
    final double maxX = 420;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @FXML
    private Pane pnGame;

    @FXML
    private StackPane spInfo;

    @FXML
    private StackPane spGameOver;

    @FXML
    private Button btnGOPlay;

    @FXML
    private Button btnGOQuit;

    @FXML
    private HBox hbP1Life;

    @FXML
    private HBox hbP2Life;

    @FXML
    private Label lblPl1Name;

    @FXML
    private Label lblPl2Name;

    @FXML
    private TextField tfP1Score;

    @FXML
    private TextField tfP2Score;

    @FXML
    private StackPane spSelect;

    @FXML
    private TextField tfScore1;

    @FXML
    private TextField tfScore2;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnPrint;

    @FXML
    private Button btnEsc;

    @FXML
    private Button btnPlay;

    @FXML
    private StackPane spFileSave;

    @FXML
    private TextField tfFileName;

    @FXML
    private Button btnFileOk;

    @FXML
    private Button btnFileCancle;

    @FXML
    private StackPane spLoadGame;

    @FXML
    private Button btnLoadOk;

    @FXML
    private Button btnLoadCancel;

    @FXML
    private ListView<String> lwLoad;

    @FXML
    private Label lblCountPl1;

    @FXML
    private Label lblCountPl12;

    @FXML
    private StackPane spPlayers;

    @FXML
    private TextField tfPl1Name;

    @FXML
    private TextField tfPl2Name;

    @FXML
    private Button btnPlOk;

    @FXML
    private Button btnRecordings;

    @FXML
    private StackPane spRecords;

    @FXML
    private Button btnRecordPlay;

    @FXML
    private ListView<String> lwRecord;

    @FXML
    private Button btnRecordCancel;

    @FXML
    private Circle ciRecording;

    @FXML
    private FlowPane flpMessages;

    @FXML
    private ScrollPane spMessage;

    @FXML
    private TextField tfChatMessage;

    @FXML
    private Button btnChatSend;

    private Chat chatService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGsc(this);
    }

    public void Init() {
        if (!game) {
            setGame();
            showPlayersPanel();
        } else {
            startGame();
        }
    }

    public void startNetGame(boolean server) {
        if (server) {
            setGame();
            start = true;
            currentPlayer = "Player1";
            initChat(server);
            startGame();
        } else {
            setScorePane();
            preparePaddles();
            currentPlayer = "Player2";
            initChat(server);
        }
    }

    public void initChat(boolean server) {
        spMessage.setDisable(false);
        flpMessages.setDisable(false);
        tfChatMessage.setDisable(false);
        btnChatSend.setDisable(false);

        if (server) {
            chatService = new ChatServer();
            ((ChatServer) chatService).StartServer();
            chatService.addChatListener(this);
        } else {
            chatService = new ChatClient();
            ((ChatClient) chatService).StartClient();
            chatService.addChatListener(this);
        }

    }

    private void setGame() {

        setScorePane();
        prepareBricks();
        preparePaddles();
        prepareBalls();
        prepareActionHandlers();

        pnGame.getChildren().add(gameGroup);
    }

    private void startGame() {

        if (start) {

            KeyFrame mainFrame;
            mainFrame = new KeyFrame(Duration.millis(MILLSEC_DELAY), (ActionEvent e) -> {
                mainStep(SEC_DELAY);
            });
            animation = new Timeline();
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.getKeyFrames().add(mainFrame);
            animation.play();
        }
    }

    private void mainStep(double time) {
        getMediaPlayer().stop();

        handleKeyPadInput();
        moveBalls(time);
        checkForPaddleCollisions();

        checkForBrickCollisions();
        checkForPowerCollisions();
        dropPowerUp(time);

        checkEndGame();
    }

    private void setScorePane() {

        timerPl1 = new Timer(lblCountPl1);
        timerPl2 = new Timer(lblCountPl12);

        tfPl1Name.setText(pl1Name);
        score1 = new String("0");
        tfP1Score.setText(score1);

        tfPl2Name.setText(pl2Name);
        score2 = new String("0");
        tfP2Score.setText(score2);

        for (int i = 0; i < LIFE; i++) {
            ivlife1 = new ImageView(life);
            ivlife2 = new ImageView(life);
            hbP1Life.getChildren().add(ivlife1);
            hbP2Life.getChildren().add(ivlife2);
        }
    }

    private void showPlayersPanel() {

        pnGame.setFocusTraversable(false);
        spPlayers.setVisible(true);
        spPlayers.setFocusTraversable(true);

        tfPl1Name.setText("");
        tfPl2Name.setText("");

        tfPl1Name.isFocused();

        btnPlOk.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                lblPl1Name.setText(tfPl1Name.getText());
                lblPl2Name.setText(tfPl2Name.getText());

                spPlayers.setFocusTraversable(false);
                spPlayers.setVisible(false);

                showInfoPanel();
            }
        ;
    }

    );
    }

    private void showInfoPanel() {

        pnGame.setFocusTraversable(false);
        spInfo.setVisible(true);
        spInfo.setFocusTraversable(true);

        spInfo.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.SPACE) {
                    pnGame.setFocusTraversable(true);
                    spInfo.setFocusTraversable(false);
                    spInfo.setVisible(false);
                    start = true;
                    game = true;
                    startGame();
                    timerPl1.start();
                    timerPl2.start();
                }
            }
        });
    }

    private void restartGame() {

        timerPl1.reset();
        timerPl2.reset();

        score1 = new String("0");
        tfP1Score.setText(score1);
        tfScore1.setText("0");
        p1Score = 0;
        score2 = new String("0");
        tfP2Score.setText(score2);
        tfScore2.setText("0");
        p2Score = 0;

        hbP1Life.getChildren().clear();
        hbP2Life.getChildren().clear();
        bricks.removeAll(bricks);
        balls.removeAll(balls);
        paddles.removeAll(paddles);
        powers.removeAll(powers);

        gameGroup.getChildren().clear();
        pnGame.getChildren().clear();
    }

    private void prepareBricks() {
        bricks = getBrick(bricks, level);

        for (int i = 0; i < bricks.size(); i++) {
            gameGroup.getChildren().add(bricks.get(i).getRectangle());
        }
    }

    private void prepareBalls() {
        ballHeight = bal1.getHeight();
        ballWidth = bal1.getWidth();
        balls = getBalls(ballWidth, ballHeight, balls);

        gameGroup.getChildren().add(balls.get(0).getRectangle());
        gameGroup.getChildren().add(balls.get(1).getRectangle());
    }

    private void preparePaddles() {
        paddle1 = new Paddle(1, WIDTH / 2, HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_SPEED, padpl1);
        paddle1.setPosY(paddle1.getPosY() - paddle1.getHeight());
        paddle1.setPosX(paddle1.getPosX() + 50 - paddle1.getWidth() / 2);
        paddles.add(paddle1);

        paddle2 = new Paddle(2, WIDTH / 2, HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_SPEED, padpl2);
        paddle2.setPosY(paddle2.getPosY() - paddle2.getHeight());
        paddle2.setPosX(paddle2.getPosX() - 50 - paddle2.getWidth() / 2);
        paddles.add(paddle2);

        gameGroup.getChildren().add(paddle1.getRectangle());
        gameGroup.getChildren().add(paddle2.getRectangle());
    }

    private void prepareActionHandlers() {

        activeKeys = new ConcurrentSkipListSet<>();

        pnGame.setOnKeyPressed((KeyEvent event) -> {
            activeKeys.add(event.getCode());
        });
        pnGame.setOnKeyReleased((KeyEvent event) -> {
            activeKeys.remove(event.getCode());
        });
    }

    private void handleKeyPadInput() {
        if (activeKeys.contains(KeyCode.RIGHT) && (paddle1.getPosX() + paddle1.getWidth()) < WIDTH) {
            for (int x = 0; x < balls.size(); x++) {
                if (balls.get(x).getHit() == 2) {
                }
            }
            paddle1.setPosX(paddle1.getPosX() + paddle1.getSpeed());
        }

        if (activeKeys.contains(KeyCode.LEFT) && paddle1.getPosX() > 0) {
            for (int x = 0; x < balls.size(); x++) {
                if (balls.get(x).getHit() == 2) {
                }
            }
            paddle1.setPosX(paddle1.getPosX() - paddle1.getSpeed());

        }

        if (activeKeys.contains(KeyCode.D) && (paddle2.getPosX() + paddle2.getWidth()) < WIDTH) {
            for (int x = 0; x < balls.size(); x++) {
                if (balls.get(x).getHit() == 2) {
                }
            }
            paddle2.setPosX(paddle2.getPosX() + paddle2.getSpeed());
        }

        if (activeKeys.contains(KeyCode.A) && paddle2.getPosX() > 0) {
            for (int x = 0; x < balls.size(); x++) {
                if (balls.get(x).getHit() == 2) {
                }
            }
            paddle2.setPosX(paddle2.getPosX() - paddle2.getSpeed());
        }

        if (activeKeys.contains(KeyCode.ENTER)) {
            if (!start) {
                startGame();
            } else {
                restartGame();
            }
        }

        if (activeKeys.contains(KeyCode.R)) {
            
            GameRecorder gc = new GameRecorder();
            
            if (!recIsStarted) {
                
                ciRecording.setVisible(true);
                recIsStarted = true;

                new Thread(new Runnable() {
                    public void run() {
                        do {
                            try {
                                gc.startRecording(getGameData());
                            } catch (JAXBException ex) {
                                Logger.getLogger(GameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(GameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } while (game);
                    }
                }).start();

            } else {
                ciRecording.setVisible(false);
                recIsStarted = false;

//                try {
////                    gc.stopRecording();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(GameScreenController.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        }

        if (activeKeys.contains(KeyCode.ESCAPE)) {
            animation.pause();
            spSelect.toFront();
            spSelect.setVisible(true);
            spSelect.setFocusTraversable(true);
            pnGame.setFocusTraversable(false);

            btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    spSelect.setFocusTraversable(false);
                    spSelect.setVisible(false);
                    spFileSave.setFocusTraversable(true);
                    spFileSave.setVisible(true);
                    tfFileName.setText("");
                    tfFileName.isFocused();
                    spFileSave.toFront();

                    btnFileOk.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            saveFile();
                        }
                    });

                    btnFileCancle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            spSelect.setFocusTraversable(true);
                            spSelect.setVisible(true);
                            spFileSave.setFocusTraversable(false);
                            spFileSave.setVisible(false);
                        }
                    });
                }
            ;
            });

                    
                btnLoad.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    spSelect.setFocusTraversable(false);
                    spSelect.setVisible(false);
                    spLoadGame.setFocusTraversable(true);
                    spLoadGame.setVisible(true);
                    spLoadGame.toFront();

                    //get all the files from a directory
                    File load = new File(saveLoadDir);
                    File[] fList = load.listFiles();

                    lwLoad.getItems().clear();

                    for (File file : fList) {
                        if (file.isFile()) {
                            lwLoad.getItems().add(file.getName());
                        }
                    }

                    lwLoad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> ov,
                                final String oldvalue, final String newvalue) {
                            filename = newvalue;
                        }
                    });

                    btnLoadOk.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            spLoadGame.setFocusTraversable(false);
                            spLoadGame.setVisible(false);
                            spSelect.setFocusTraversable(false);
                            spSelect.setVisible(false);
                            pnGame.setFocusTraversable(true);
                            if (filename.isEmpty()) {
                                spLoadGame.setFocusTraversable(false);
                                spLoadGame.setVisible(false);
                                spSelect.setFocusTraversable(true);
                                spSelect.setVisible(true);

                            } else {
                                restartGame();
                                loadFile();
                            }

                        }
                    });

                    btnLoadCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            spLoadGame.setFocusTraversable(false);
                            spLoadGame.setVisible(false);
                            spSelect.setFocusTraversable(true);
                            spSelect.setVisible(true);
                        }
                    });
                }
            });

            btnRecordings.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    spSelect.setFocusTraversable(false);
                    spSelect.setVisible(false);
                    spRecords.setFocusTraversable(true);
                    spRecords.setVisible(true);
                    spRecords.toFront();

                    //get all the files from a directory
                    File load = new File(recDir);
                    File[] fList = load.listFiles();

                    lwRecord.getItems().clear();

                    for (File file : fList) {
                        if (file.isFile()) {
                            lwRecord.getItems().add(file.getName());
                        }
                    }

                    lwRecord.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> ov,
                                final String oldvalue, final String newvalue) {
                            filename = newvalue;
                        }
                    });

                    btnRecordPlay.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            spRecords.setFocusTraversable(false);
                            spRecords.setVisible(false);
                            spSelect.setFocusTraversable(false);
                            spSelect.setVisible(false);
                            pnGame.setFocusTraversable(true);

                            if (filename.isEmpty()) {
                                spRecords.setFocusTraversable(false);
                                spRecords.setVisible(false);
                                spSelect.setFocusTraversable(true);
                                spSelect.setVisible(true);

                            } else {
                                try {
                                    playFile(filename);
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(GameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (JAXBException ex) {
                                    Logger.getLogger(GameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }
                    });

                    btnRecordCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            spRecords.setFocusTraversable(false);
                            spRecords.setVisible(false);
                            spSelect.setFocusTraversable(true);
                            spSelect.setVisible(true);
                        }
                    });
                }
            });

            btnPrint.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    FileFactory.printReflection();
                }
            });

            btnPlay.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    animation.play();
                    spSelect.setVisible(false);
                    spSelect.setFocusTraversable(false);
                    pnGame.setFocusTraversable(true);
                }
            });

            btnEsc.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    restartGame();
                    spSelect.setVisible(false);
                    spSelect.setFocusTraversable(false);
                    pnGame.setFocusTraversable(true);
                    controller.unloadScreen("game");
                    controller.setScreen(JurasicBreakout.mainScrID);
                }
            });

        }
    }

    private void moveBalls(double time) {

        if (start) {
            for (int x = 0; x < balls.size(); x++) {
                balls.get(x).setPosX(balls.get(x).getPosX() + balls.get(x).getSpeX() * time);
                balls.get(x).setPosY(balls.get(x).getPosY() + balls.get(x).getSpeY() * time);
                if (balls.get(x).getPosY() < 0) {
                    balls.get(x).setSpeY(-1 * balls.get(x).getSpeY());
                }
                if (balls.get(x).getPosX() >= WIDTH - ballWidth || balls.get(x).getPosX() < 0) {
                    balls.get(x).setSpeX(-1 * balls.get(x).getSpeX());
                } else {
                    ballOutOfBounds();
                }
            }
        }
    }

    private void checkForBrickCollisions() {
        for (int i = 0; i < balls.size(); i++) {
            for (int j = 0; j < bricks.size(); j++) {
                boolean x_overlap = balls.get(i).getPosX() + ballWidth >= bricks.get(j).getPosX() && balls.get(i).getPosX() <= bricks.get(j).getPosX() + BRICK_WIDTH;
                boolean y_overlap = balls.get(i).getPosY() + ballHeight >= bricks.get(j).getPosY() && balls.get(i).getPosY() <= bricks.get(j).getPosY() + BRICK_HEIGHT;
                if (x_overlap && y_overlap) {
                    brickCollision(balls.get(i), bricks.get(j));
                }
            }
        }
    }

    private void brickCollision(Ball b, Brick brick) {

        if (b.getPlayer() == 1) {
            if (b.getPosX() >= brick.getPosX() || b.getPosY() + b.getHeight() <= brick.getPosX()) {
                b.setSpeY(-1 * b.getSpeY());

                tfP1Score.setText(Integer.toString(p1Score += 50));
            }
            if (b.getPosY() <= brick.getPosY() + brick.getHeight() / 2 || b.getPosY() >= brick.getPosY() + 2 * brick.getHeight() / 2 && b.getPlayer() == 1) {
                b.setSpeX(-1 * b.getSpeX());
                b.setSpeY(-1 * b.getSpeY());
                rapcall.play();
                tfP1Score.setText(Integer.toString(p1Score += 50));
            }
            if (b.getPosX() + b.getWidth() >= brick.getPosY() + brick.getHeight() && b.getPlayer() == 1) {
                b.setSpeX(1 * b.getSpeX());
                b.setSpeY(1 * b.getSpeY());
                rapcall2.play();
                tfP1Score.setText(Integer.toString(p1Score += 50));
            }
        } else if (b.getPlayer() == 2) {
            if (b.getPosX() >= brick.getPosX() || b.getPosY() + b.getHeight() <= brick.getPosX()) {
                b.setSpeY(-1 * b.getSpeY());

                tfP2Score.setText(Integer.toString(p2Score += 100));
            }
            if (b.getPosY() <= brick.getPosY() + brick.getHeight() / 2 || b.getPosY() >= brick.getPosY() + 2 * brick.getHeight() / 2 && b.getPlayer() == 1) {
                b.setSpeX(-1 * b.getSpeX());
                b.setSpeY(-1 * b.getSpeY());
                rapcall.play();
                tfP2Score.setText(Integer.toString(p2Score += 100));
            }
            if (b.getPosX() + b.getWidth() >= brick.getPosY() + brick.getHeight() && b.getPlayer() == 1) {
                b.setSpeX(1 * b.getSpeX());
                b.setSpeY(1 * b.getSpeY());
                rapcall2.play();
                tfP2Score.setText(Integer.toString(p2Score += 100));

            }
        }

        if ((brick.getClass().equals(PowerUp.class
        ))) {
            Powers p = new Powers(b.getHit(), brick.getPosX(), brick.getPosY(), b.getPlayer());
            p.setBall(b);

            if (p.getPower().equals("addBall")) {
                Ball ball = new Ball(3, b.getPosX(), b.getPosY(), ballWidth, ballHeight, balpl1, b.getHit(), -1 * b.getSpeX());
                balls.add(ball);
                gameGroup.getChildren().add(ball.getRectangle());
            } else {
                powers.add(p);
                gameGroup.getChildren().add(p.getRectangle());
            }

        }

        brick.setHits(brick.getHits() - 1);

        if (brick.getHits() == 0) {
            bricks.remove(brick);
            gameGroup.getChildren().remove(brick.getRectangle());
        }
    }

    private void checkForPaddleCollisions() {
        for (int x = 0; x < balls.size(); x++) {
            for (int y = 0; y < paddles.size(); y++) {
                boolean x_overlap = (balls.get(x).getPosX() + ballWidth >= paddles.get(y).getPosX()) && (balls.get(x).getPosX() <= paddles.get(y).getPosX() + paddles.get(y).getWidth());
                boolean y_overlap = (balls.get(x).getPosY() + ballHeight >= paddles.get(y).getPosY()) && (balls.get(x).getPosY() <= paddles.get(y).getPosY() + paddles.get(y).getHeight());
                if (x_overlap && y_overlap) {
                    paddleCollision(balls.get(x), paddles.get(y));
                }
            }
        }
    }

    private void paddleCollision(Ball b, Paddle p) {

        b.setHit(p.getPlayer());

        if (p.getPlayer() == 1 && b.getPlayer() == 1 || p.getPlayer() == 2 && b.getPlayer() == 2) {
            if (b.getPosX() <= p.getPosX() + p.getWidth() / 3 || b.getPosX() >= p.getPosX() + 2 * p.getWidth() / 3) {
                b.setSpeX(-1 * b.getSpeX());
                b.setSpeY(-1 * b.getSpeY());
                bounce.play();
            } else {
                b.setSpeY(-1 * b.getSpeY());
                bounce.play();
            }
        } else {
            b.setSpeX(1 * b.getSpeX());
            b.setSpeY(1 * b.getSpeY());
        }
    }

    private void dropPowerUp(double time) {
        for (int x = 0; x < powers.size(); x++) {
            powers.get(x).setPosY(powers.get(x).getPosY() + powers.get(x).getSpeed() * time);
            powers.get(x).setPosX(powers.get(x).getPosX());
        }
    }

    private void checkForPowerCollisions() {
        for (int i = 0; i < powers.size(); i++) {
            for (int j = 0; j < paddles.size(); j++) {
                boolean x_overlap = powers.get(i).getPosX() >= paddles.get(j).getPosX() && powers.get(i).getPosX() <= paddles.get(j).getPosX() + paddles.get(j).getWidth();
                boolean y_overlap = powers.get(i).getPosY() <= paddles.get(j).getPosY() + paddles.get(j).getHeight() && powers.get(i).getPosY() + powers.get(i).getHeight() >= paddles.get(j).getPosY();
                if (x_overlap && y_overlap) {
                    usePower(powers.get(i), paddles.get(j));
                    break;
                }
            }
        }
    }

    private void usePower(Powers pow, Paddle pad) {

        if (pow.getPower().equals("bigPaddle")) {

            if (pad.getPlayer() == 1) {
                paddle1 = new Paddle(1, pad.getPosX(), pad.getPosY(), pad.getWidth() * 1.3, pad.getHeight(), pad.getSpeed(), pad.getImagePath());
                gameGroup.getChildren().add(paddle1.getRectangle());
                paddles.add(paddle1);
            } else {
                paddle2 = new Paddle(2, pad.getPosX(), pad.getPosY(), pad.getWidth() * 1.3, pad.getHeight(), pad.getSpeed(), pad.getImagePath());
                gameGroup.getChildren().add(paddle2.getRectangle());
                paddles.add(paddle2);
            }
            gameGroup.getChildren().remove(pad.getRectangle());
            paddles.remove(pad);
        }

        if (pow.getPower().equals("smallPaddle")) {

            if (pad.getPlayer() == 1) {
                paddle1 = new Paddle(1, pad.getPosX(), pad.getPosY(), pad.getWidth() / 1.3, pad.getHeight(), pad.getSpeed(), pad.getImagePath());
                gameGroup.getChildren().add(paddle1.getRectangle());
                paddles.add(paddle1);
            } else {
                paddle2 = new Paddle(2, pad.getPosX(), pad.getPosY(), pad.getWidth() / 1.3, pad.getHeight(), pad.getSpeed(), pad.getImagePath());
                gameGroup.getChildren().add(paddle2.getRectangle());
                paddles.add(paddle2);
            }
            gameGroup.getChildren().remove(pad.getRectangle());
            paddles.remove(pad);
        }

        if (pow.getPower().equals("fastBall")) {
            pow.getBall().setSpeX(2.5 * pow.getBall().getSpeX());
            pow.getBall().setSpeY(2.5 * pow.getBall().getSpeY());
        }

        if (pow.getPower().equals("slowBall")) {
            pow.getBall().setSpeX(.3 * pow.getBall().getSpeX());
            pow.getBall().setSpeY(.3 * pow.getBall().getSpeY());
        }
        powers.remove(pow);
        gameGroup.getChildren().remove(pow.getRectangle());
    }

    private ArrayList<Ball> getBalls(double ballWidth, double ballHeight, ArrayList<Ball> balls) {

        double x1 = paddle2.getPosX() + paddle2.getWidth() + 50;
        double y1 = paddle2.getPosY() - paddle2.getHeight() / 2 - 10;
        Ball ballP1 = new Ball(1, x1, y1, ballWidth, ballHeight, balpl1, 1, GAME_SPEED);

        double x2 = paddle1.getPosX() - paddle1.getWidth() + 10;
        double y2 = paddle1.getPosY() - paddle1.getHeight() / 2 - 10;
        Ball ballP2 = new Ball(2, x2, y2, ballWidth, ballHeight, balpl2, 2, GAME_SPEED);

        balls.add(ballP1);
        balls.add(ballP2);

        return balls;
    }

    public ArrayList<Brick> getBrick(ArrayList<Brick> bricks, int level) {
        for (int i = 0; i < NUM_ROW; i++) {
            for (int j = 0; j < NUM_COLUMN; j++) {
                double rand = Math.random();
                double xloc = (BRICK_WIDTH) * j;
                double yloc = (BRICK_HEIGHT) * i;

                if (rand < POWERS * level) {
                    String image = "/Images/din4.png";
                    bricks.add(new PowerUp(xloc, yloc, BRICK_WIDTH, BRICK_HEIGHT, image));
                }
                if (rand > POWERS) {
                    String image1 = "/Images/din2.png";
                    bricks.add(new BasicBrick(xloc, yloc, BRICK_WIDTH, BRICK_HEIGHT, image1));
                } else {
                    bricks.add(new Boulders(xloc, yloc, BRICK_WIDTH, BRICK_HEIGHT, rand));
                }
            }
        }
        return bricks;
    }

    public List<Object> getGameData() {

        List<Object> data = new ArrayList<>();

        bricks.forEach(b -> data.add(b));
        powers.forEach(p -> data.add(p));
        balls.forEach(b -> data.add(b));
        paddles.forEach(p -> data.add(p));

        return data;
    }

    public void showGameState(List<Object> gameState) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                start = true;
                pnGame.setFocusTraversable(true);

                for (Object ob : gameState) {
                    if (ob instanceof BasicBrick) {
                        BasicBrick bbr = (BasicBrick) ob;
                        String s = bbr.getImagePath();
                        bbr.SetRectangleWithImage(new Image(s));
                        bricks.add(bbr);
                    }
                    if (ob instanceof PowerUp) {
                        PowerUp pu = (PowerUp) ob;
                        String s = pu.getImagePath();
                        pu.SetRectangleWithImage(new Image(s));
                        bricks.add(pu);
                    }
                    if (ob instanceof Boulders) {
                        Boulders bo = (Boulders) ob;
                        String s = bo.getImagePath();
                        bo.SetRectangleWithImage(new Image(s));
                        bricks.add(bo);
                    }
                    if (ob instanceof Powers) {
                        Powers pow = (Powers) ob;
                        String s = pow.getImagePath();
                        pow.SetRectangleWithImage(new Image(s));
                        powers.add(pow);
                    }
                    if (ob instanceof Ball) {
                        Ball ball = (Ball) ob;
                        String s = ball.getImagePath();
                        ball.SetRectangleWithImage(new Image(s));
                        balls.add(ball);
                    }
                }

                for (int i = 0; i < bricks.size(); i++) {
                    gameGroup.getChildren().add(bricks.get(i).getRectangle());
                }

                for (int i = 0; i < powers.size(); i++) {
                    gameGroup.getChildren().add(powers.get(i).getRectangle());
                }

                for (int i = 0; i < balls.size(); i++) {
                    gameGroup.getChildren().add(balls.get(i).getRectangle());
                }

                prepareActionHandlers();

                pnGame.getChildren().add(gameGroup);

                startGame();
            }
        });

    }

    private void saveFile() {

        filename = tfFileName.getText();
        spFileSave.setFocusTraversable(false);
        spFileSave.setVisible(false);
        spSelect.setFocusTraversable(true);
        spSelect.setVisible(true);

        data = new ArrayList<>();

        bricks.forEach(b -> data.add(b));
        powers.forEach(p -> data.add(p));

        FileFactory.saveGame(saveLoadDir + filename + ".dat", data);
    }

    private void loadFile() {

        start = true;
        spInfo.setVisible(false);
        spInfo.setFocusTraversable(false);
        pnGame.setFocusTraversable(true);

        restartGame();

        List<Object> loadData = FileFactory.loadGame(saveLoadDir + filename);

        for (Object ob : loadData) {
            if (ob instanceof BasicBrick) {
                BasicBrick bbr = (BasicBrick) ob;
                bricks.add(bbr);
            }
            if (ob instanceof PowerUp) {
                PowerUp pu = (PowerUp) ob;
                bricks.add(pu);
            }
            if (ob instanceof Boulders) {
                Boulders bo = (Boulders) ob;
                bricks.add(bo);
            }
            if (ob instanceof Powers) {
                Powers pow = (Powers) ob;
                powers.add(pow);
            }
        }

        for (int i = 0; i < bricks.size(); i++) {
            gameGroup.getChildren().add(bricks.get(i).getRectangle());
        }

        for (int i = 0; i < powers.size(); i++) {
            gameGroup.getChildren().add(powers.get(i).getRectangle());
        }

        setScorePane();
        preparePaddles();
        prepareBalls();
        prepareActionHandlers();

        pnGame.getChildren().add(gameGroup);

        startGame();
    }

    public void loadGame(String file) {
        filename = file;
        game = true;
        loadFile();
    }

    private void playFile(String filename) throws FileNotFoundException, JAXBException {

        GamePlayer gp = new GamePlayer();
        List<Object> gamestate = gp.startReading(filename);

        for (Object ob : gamestate) {
            System.out.println(ob.getClass().getName());
        }

    }

    private boolean checkWiner() {
        if (!hbP1Life.getChildren().contains(ivlife1)) {
            p2Score++;
            tfScore2.setText(Integer.toString(p2Score));
            return true;
        }
        if (!hbP2Life.getChildren().contains(ivlife2)) {
            p1Score++;
            tfScore1.setText(Integer.toString(p1Score));
            return true;
        }
        return false;
    }

    private void ballOutOfBounds() {

        ArrayList<Ball> copy = new ArrayList<>(balls);

        for (Ball b : copy) {
            if (b.getPosY() > HEIGHT && b.getPlayer() == 1) {
                hbP1Life.getChildren().remove(0, 1);
                gameGroup.getChildren().remove(b.getRectangle());
                balls.remove(b);

                double x1 = paddle2.getPosX() + paddle2.getWidth() + 50;
                double y1 = paddle2.getPosY() - paddle2.getHeight() / 2 - 10;
                Ball bPl1 = new Ball(1, x1, y1, ballWidth, ballHeight, balpl1, 1, GAME_SPEED);
                balls.add(bPl1);
                gameGroup.getChildren().add(bPl1.getRectangle());

            }
            if (b.getPosY() > HEIGHT && b.getPlayer() == 2) {
                hbP2Life.getChildren().remove(0, 1);
                gameGroup.getChildren().remove(b.getRectangle());
                balls.remove(b);

                double x2 = paddle1.getPosX() - paddle1.getWidth() + 10;
                double y2 = paddle1.getPosY() - paddle1.getHeight() / 2 - 10;
                Ball bPl2 = new Ball(2, x2, y2, ballWidth, ballHeight, balpl2, 2, GAME_SPEED);
                balls.add(bPl2);
                gameGroup.getChildren().add(bPl2.getRectangle());
            }
        }

    }

    private void checkEndGame() {

        if (checkWiner()) {

            level += 1;
            animation.stop();
            restartGame();

            spGameOver.setVisible(true);
            spGameOver.setFocusTraversable(true);

            btnGOPlay.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    spGameOver.setVisible(false);
                    pnGame.getChildren().remove(spGameOver);
                    pnGame.setFocusTraversable(true);
                    setGame();
                    startGame();
                    timerPl1.start();
                    timerPl2.start();
                }
            });

            btnGOQuit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    restartGame();
                    spGameOver.setVisible(false);
                    pnGame.getChildren().remove(spGameOver);
                    pnGame.setFocusTraversable(true);
                    controller.setScreen(JurasicBreakout.mainScrID);
                    game = false;
                    Init();
                }
            });
        }

    }

    @Override
    public void MessageReceived(String playerName, String message) {
        Platform.runLater(() -> {
            StackPane pane = new StackPane();
            pane.setAlignment(Pos.BASELINE_LEFT);
            Label l = new Label();
            l.setText(playerName + ": " + message);
            l.setFont(Font.font("Arial", 12));
            l.setTextFill(Color.DARKRED);
            pane.getChildren().add(l);
            StackPane.setMargin(l, new Insets(5, 10, 5, 10));
            flpMessages.getChildren().add(pane);
            pane.setPrefWidth(((FlowPane) pane.getParent()).getWidth());
        });
    }

    @FXML
    void btnChatSend_OnMouseClicked(MouseEvent event) {
        if (chatService != null) {
            try {
                chatService.sendMessageToRemote(currentPlayer, tfChatMessage.getText());

                StackPane pane = new StackPane();
                pane.setAlignment(Pos.BASELINE_LEFT);

                Label l = new Label();
                l.setText("You: " + tfChatMessage.getText());
                l.setFont(Font.font("Arial", 12));
                l.setTextFill(Color.WHITE);
                pane.getChildren().add(l);
                StackPane.setMargin(l, new Insets(5, 10, 5, 10));
                flpMessages.getChildren().add(pane);
                pane.setPrefWidth(((FlowPane) pane.getParent()).getWidth());
                tfChatMessage.setText("");
            } catch (Exception ex) {
                Logger.getLogger(GameScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
