package SeaBattleV01.Controller;

import SeaBattleV01.Model.GameField;
import SeaBattleV01.Model.ModelInterface;
import SeaBattleV01.Model.Point;
import SeaBattleV01.View.SeaBattleView;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameController implements Observer, IController {

    final int TURN_DELAY_MS = 500; // milliseconds
    private ModelInterface gamerField;
    private ModelInterface compField;
    private SeaBattleView view;
    private final String saveFileName = "d:\\GameState.dat";
    private boolean isNetworkMode = false;
    private volatile Stack<Point> myShot = new Stack<>();
    private volatile Stack<Point> enemyShot = new Stack<>();
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean connectionEstablished;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public GameController(ModelInterface gamerField, ModelInterface compField) {
        this.gamerField = gamerField;
        this.compField = compField;
        view = new SeaBattleView(this);
        view.createView(gamerField.getFieldSize());
    }

    @Override
    public void startNewGame() {
        isNetworkMode = false;
        gamerField.registerObserver(this);
        compField.registerObserver(this);
        gamerField.startNewGame();
        compField.startNewGame();
        executorService.execute(new MyShotsHandler());
        executorService.execute(new EnemyShotsHandler());
    }

    @Override
    public void startNewNetworkGame() {
        isNetworkMode = true;
        gamerField.registerObserver(this);
        gamerField.startNewGame();
        establishConnection();

    }

    @Override
    public void doShoot(int x, int y) {
        int fieldSize = compField.getFieldSize();

        if (myShot.isEmpty()) {
            myShot.add(new Point(x, y));
            if (!isNetworkMode) {
                if (enemyShot.isEmpty()) {
                    Random random = new Random();
                    enemyShot.add(new Point(random.nextInt(fieldSize), random.nextInt(fieldSize)));
                }
            }
        }
    }

    @Override
    public void updateView() {
        updateField(gamerField, view.gamerButtons);
        updateField(compField, view.compButtons);
    }

    @Override
    public void update() {
        updateView();
    }

    @Override
    public void saveGame() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(saveFileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gamerField);
            objectOutputStream.writeObject(compField);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadGame() {
        File file = new File(saveFileName);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "File d:\\GameState.dat is not exists! Load is canceled!");
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(saveFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            gamerField = (GameField) objectInputStream.readObject();
            compField = (GameField) objectInputStream.readObject();
            gamerField.registerObserver(this);
            compField.registerObserver(this);
            view.clearGameLog();
            update();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void unsubscribeController() {
        gamerField.removeObserver(this);
        compField.removeObserver(this);
    }

    private void updateField(ModelInterface field, final JButton[][] buttons) {
        int fieldSize = field.getFieldSize();
        Point[][] points = field.getField();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                switch (points[i][j].getPointType()) {
                    case ALIVE:
                        if (field.isNeedToHideShips()) {
                            buttons[i][j].setText(".");
                            buttons[i][j].setBackground(Color.LIGHT_GRAY);
                        } else {
                            buttons[i][j].setText("X");
                            buttons[i][j].setBackground(Color.CYAN);
                        }
                        break;
                    case DEAD:
                        buttons[i][j].setText("V");
                        buttons[i][j].setBackground(Color.PINK);
                        break;
                    case EMPTY:
                        buttons[i][j].setText(".");
                        buttons[i][j].setBackground(Color.LIGHT_GRAY);
                        break;
                    case USED:
                        buttons[i][j].setText("-");
                        buttons[i][j].setBackground(Color.GRAY);
                        break;
                    case BUSY:
                        if (field.isNeedToHideShips()) {
                            buttons[i][j].setText(".");
                            buttons[i][j].setBackground(Color.LIGHT_GRAY);
                        } else {
                            buttons[i][j].setText("*");
                            buttons[i][j].setBackground(Color.ORANGE);
                        }
                        break;
                }
            }
        }
    }

    public boolean isGameOver() {
        return gamerField.isGameOver() || compField.isGameOver();
    }

    @Override
    public void connectToNetworkGame() {
        try {
            Socket socket = new Socket("127.0.0.1", IController.NETWORK_PORT);
            view.addTextToGameLog("Connection established.");
            isNetworkMode = true;
            gamerField.registerObserver(this);
            gamerField.startNewGame();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            compField = (GameField) objectInputStream.readObject();
            registerCompFieldObserver();
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(gamerField);
            update();
            connectionEstablished = true;
            executorService.execute(new MyShotsHandler());
            executorService.execute(new EnemyShotsHandler());

        } catch (IOException e) {
            view.addTextToGameLog("Couldn't connect. Server is not ready.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void establishConnection() {
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(IController.NETWORK_PORT);
                    view.addTextToGameLog("Waiting connection...");
                    Socket socket = serverSocket.accept();
                    view.addTextToGameLog("Connection established.");
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(gamerField);
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    compField = (GameField) objectInputStream.readObject();
                    registerCompFieldObserver();
                    connectionEstablished = true;

                    executorService.execute(new MyShotsHandler());
                    executorService.execute(new EnemyShotsHandler());

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(thread).start();
    }

    private void registerCompFieldObserver() {
        compField.registerObserver(this);
        update();
    }

    private class MyShotsHandler implements Runnable {
        @Override
        public void run() {
            while (!isGameOver()) {
                if (!myShot.isEmpty()) {
                    Point tempPoint = myShot.pop();
                    StringBuilder log = new StringBuilder();
                    ModelInterface.shootResult shootResult = compField.doShoot(tempPoint.getX(), tempPoint.getY());
                    if (isNetworkMode) {
                        try {
                            objectOutputStream.writeObject(tempPoint);
                            System.out.println("object is written");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    log.append("Gamer shoots on x:").append(tempPoint.getX()).append("  y:").append(tempPoint.getY()).append("  result:").append(shootResult);
                    view.addTextToGameLog(log.toString());
                    if (compField.isGameOver()) {
                        view.addTextToGameLog("\nGamer is winner!!!");
                        unsubscribeController();
                    }
                }
                //delay 0,5 sec
                try {
                    Thread.sleep(TURN_DELAY_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class EnemyShotsHandler implements Runnable {
        @Override
        public void run() {
            Point tempPoint;
            while (!isGameOver()) {
                if (!enemyShot.isEmpty()) {
                    tempPoint = enemyShot.pop();
                    StringBuilder log = new StringBuilder();
                    ModelInterface.shootResult shootResult = gamerField.doShoot(tempPoint.getX(), tempPoint.getY());
                    log.append("Comp shoots on x:").append(tempPoint.getX()).append("  y:").append(tempPoint.getY()).append("  result:").append(shootResult);
                    view.addTextToGameLog(log.toString());
                    if (gamerField.isGameOver()) {
                        view.addTextToGameLog("\nComp is winner!!!");
                        unsubscribeController();
                    }
                } else {
                    if (isNetworkMode) {
                        try {
                            tempPoint = (Point) objectInputStream.readObject();
                            if (tempPoint != null) {
                                enemyShot.push(tempPoint);
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    Thread.sleep(TURN_DELAY_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}