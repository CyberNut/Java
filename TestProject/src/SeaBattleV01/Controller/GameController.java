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

public class GameController implements Observer, IController {

    private ModelInterface gamerField;
    private ModelInterface compField;
    private SeaBattleView view;
    private final String saveFileName = "d:\\GameState.dat";
    private boolean isNetworkMode;
    private volatile boolean isGameReady = false;
    private volatile boolean isMyTurn = false;
    private volatile boolean connectionEstablished = false;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public GameController(ModelInterface gamerField, ModelInterface compField) {
        this.gamerField = gamerField;
        this.compField = compField;
        view = new SeaBattleView(this);
        view.createView(gamerField.getFieldSize());
    }

    @Override
    public void startNewGame() {
        gamerField.registerObserver(this);
        compField.registerObserver(this);
        gamerField.startNewGame();
        compField.startNewGame();
        setNetworkMode(false);
    }

    @Override
    public void startNewNetworkGame() {
        gamerField.registerObserver(this);
        gamerField.startNewGame();
        establishConnection();
//        while(!connectionEstablished);
//        compField.registerObserver(this);
//        setNetworkMode(true);
//        update();
    }

    @Override
    public void connectToNetworkGame() {
        try {
//            if (serverSocket != null) {
//                serverSocket.close();
//            }
            Socket socket = new Socket("127.0.0.1", IController.NETWORK_PORT);
            view.addTextToGameLog("Connection established.");
            startNewGame();
            setNetworkMode(true);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            compField = (GameField) objectInputStream.readObject();
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(gamerField);
            isGameReady = true;
            update();
        } catch (IOException e) {
            view.addTextToGameLog("Couldn't connect. Server is not ready.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doShoot(final int x, final int y) {
        if (!isGameReady) {
            return;
        }

        if(!isMyTurn) {
            return;
        }


        Runnable turnHandler = new Runnable() {
            @Override
            public void run() {
                compField.doShoot(x, y);
                Point currentPoint = new Point(x, y);
                try {
                    objectOutputStream.writeObject(currentPoint);
                    isMyTurn = false;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };


        int fieldSize = compField.getFieldSize();
        int randomX = new Random().nextInt(fieldSize);
        int randomY = new Random().nextInt(fieldSize);
        ModelInterface.shootResult shootResult;

        if (x >= 0 && x <= fieldSize && y >= 0 && y <= fieldSize) {
            shootResult = compField.doShoot(x, y);
            view.addTextToGameLog("Gamer shoots on x:" + x + "  y:" + y + "  result:" + shootResult);
        }
        shootResult = gamerField.doShoot(randomX, randomY);
        view.addTextToGameLog("Comp shoots on x:" + x + "  y:" + y + "  result:" + shootResult);
        if (compField.isGameOver()) {
            view.addTextToGameLog("\nGamer is winner!!!");
            unsubscribeController();
        }
        if (gamerField.isGameOver()) {
            view.addTextToGameLog("\nComp is winner!!!");
            unsubscribeController();
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

    public boolean isNetworkMode() {
        return isNetworkMode;
    }

    public void setNetworkMode(boolean networkMode) {
        isNetworkMode = networkMode;
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
                    isGameReady = true;

                    ShootHandler shootHandler = new ShootHandler();
                    Thread turnHandler = new Thread(shootHandler);
                    turnHandler.start();

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

    private boolean isGameOver() {
        return gamerField.isGameOver() || compField.isGameOver();
    }

    private class ShootHandler implements Runnable {
        @Override
        public void run() {
            while (!isGameOver()) {
                if(isMy                                            Turn) {
                    doNetworkShoot();
                }
            }

        }
    }
}