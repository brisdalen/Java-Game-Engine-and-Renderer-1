import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Engine extends Timer {

    public Window.DrawPanel drawPanel;
    public Player player;
    public ArrayList<Lazer> lazers;
    public ArrayList<HealPod> healPods;
    public int currentWindowWidth;
    public int currentWindowHeight;
    public Random randomer;
    public int totalTime = -20;

    public Engine(Window.DrawPanel drawPanel, Player player) {
        this.drawPanel = drawPanel;
        currentWindowWidth = drawPanel.getWidth();
        currentWindowHeight = drawPanel.getHeight();
        randomer = new Random();

        this.player = player;

        lazers = new ArrayList<>();
        healPods = new ArrayList<>();

        healPods.add(new HealPod(currentWindowWidth/2-20, currentWindowHeight/2-20,
                40, 40));

        scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                player.getInputs();
                if(player.x <= 0) {
                    player.x = 0;
                }
                if(player.x + player.width >= currentWindowWidth) {
                    player.x = currentWindowWidth - player.width-1;
                }
                if(player.y <= 0) {
                    player.y = 0;
                }
                if(player.y + player.height >= currentWindowHeight) {
                    player.y = currentWindowHeight - player.height-1;
                }
                drawPanel.repaint(getBigClipX1(), getBigClipY1(),
                        getBigClipX2(), getBigClipY2());
                //Sett alle lasere i bevegelse
                for(Lazer l : lazers) {
                    if(l.getDirection() == 0 || l.getDirection() == 2) {
                        l.roamX(100);
                    } else {
                        l.roamY(100);
                    }
                }

                checkAllLazers(lazers, drawPanel);
                checkAllHealpods(healPods, drawPanel);
            }
        }, 0, 40);
        scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(totalTime);
                totalTime++;

                    if (totalTime == -13) {
                        spawnNewLazer();
                    }
                    if (totalTime == -8) {
                        spawnManyLazers(3);
                    }
                    if (totalTime == -6) {
                        spawnManyLazers(6);
                    }
                    if (totalTime == -3) {
                        System.out.println("CLEARED");
                        clearLazers();
                    }
                    if(totalTime == 0) {
                        spawnManyLazers(25);
                    }
                    if (totalTime % 10 == 0 && totalTime >= 0) {
                        clearLazers();
                        spawnManyLazers(25);
                    }

            }
        }, 1000, 1000);
    }

    private void checkAllLazers(ArrayList<Lazer> lazers, Window.DrawPanel drawPanel) {
        for(Lazer l : lazers) {
            l.checkCollision(player);
            drawPanel.repaint(l.getClip(3));
        }
    }
    private void checkAllHealpods(ArrayList<HealPod> healPods, Window.DrawPanel drawPanel) {
        for (HealPod hp : healPods) {
            hp.checkCollision(player);
            drawPanel.repaint(hp.getClip(3));
        }
    }

    public void clearLazers() {
        lazers.clear();
        drawPanel.repaint();
    }

    public void spawnManyLazers(int amount) {
        for(int i = 0; i < amount; i++) {
            spawnNewLazer();
        }
    }

    public void spawnNewLazer() {
        int type = randomer.nextInt(4);
        int width = 0;
        int height = 0;
        int x = 0;
        int y = 0;
        switch(type) {
            case 0:
                width = 100;
                height = 20;
                x = randomer.nextInt(currentWindowWidth-width);
                y = randomer.nextInt(currentWindowHeight-height);
                lazers.add(new Lazer(x, y, width, height,
                        getNewDirection(x, y, width, height)));
                break;
            case 1:
                width = 20;
                height = 100;
                x = randomer.nextInt(currentWindowWidth-width);
                y = randomer.nextInt(currentWindowHeight-height);
                lazers.add(new Lazer(x, y, width, height,
                        getNewDirection(x, y, width, height)));
                break;
            case 2:
                width = 80;
                height = 140;
                x = randomer.nextInt(currentWindowWidth-width);
                y = randomer.nextInt(currentWindowHeight-height);
                lazers.add(new Lazer(x, y, width, height,
                        getNewDirection(x, y, width, height)));
                break;
            case 3:
                width = 140;
                height = 80;
                x = randomer.nextInt(currentWindowWidth-width);
                y = randomer.nextInt(currentWindowHeight-height);
                lazers.add(new Lazer(x, y, width, height,
                        getNewDirection(x, y, width, height)));
                break;
        }
    }

    public int getNewDirection(int x, int y, int width, int height) {
        int direction = 0;
        //Typ 1 og 2
        if(height > width) {
            if(x - width < currentWindowWidth/2) {
                direction = 2;
            } else {
                direction = 0;
            }
        //Typ 0
        } else {
            if(y - height < currentWindowHeight/2) {
                direction = 3;
            } else {
                direction = 1;
            }
        }

        return direction;
    }

    public Player getPlayer() {
        return player;
    }
    public ArrayList<Lazer> getLazers() {
        return lazers;
    }
    public ArrayList<HealPod> getHealPods() {
        return healPods;
    }

    public int getBigClipX1() {
        return player.x - player.width*2;
    }
    public int getBigClipX2() {
        return player.width*6;
    }
    public int getBigClipY1() {
        return player.y - player.height*2;
    }
    public int getBigClipY2() {
        return player.height*6;
    }
}
