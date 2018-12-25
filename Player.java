import java.awt.*;

public class Player {
    int x, y, width, height;
    Color color = Color.red;
    private int direction = 2; // 0 = left, 1 = up, 2 = right, 3 = down
    public int health = 700;
    public int movementSpeedStart = 4;
    public int movementSpeed = 4;
    public Controller controller;
    public boolean firing;
    private int maxHealth = 1000;

    private int fixedHealAmount = 50;
    private int fixedDamageAmount = 50;

    public Player(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.firing = false;
        this.controller = new Controller(this);
    }

    public void changeDirection(int direction) {
        if (direction > 0 || direction <= 3) {
            this.direction = direction;
        }
    }

    public void takeDamage(int damage) {
        if(health - damage >= 0 + damage) {
            health -= damage;
        } else {
            health = 0;
        }
    }
    public void takeDamage() {
        if(health - fixedDamageAmount >= 0 + fixedDamageAmount) {
            health -= fixedDamageAmount;
        } else {
            health = 0;
        }
    }

    public void heal(int healAmount) {
        if(health + healAmount <= maxHealth - healAmount) {
            health += healAmount;
        } else {
            health = 1000;
        }
    }
    public void heal() {
        if(health + fixedHealAmount <= maxHealth - fixedHealAmount) {
            health += fixedHealAmount;
        } else {
            health = 1000;
        }
    }

    public void getInputs() {
        if(controller.left) {
            changeDirection(0);
            x -= movementSpeed;
        }
        if(controller.up) {
            changeDirection(1);
            y -= movementSpeed;
        }
        if(controller.right) {
            changeDirection(2);
            x += movementSpeed;
        }
        if(controller.down) {
            changeDirection(3);
            y += movementSpeed;
        }
        if(controller.space) {
            startWeapon();
        } else {
            stopWeapon();
        }
    }

    public void startWeapon() {
        firing = true;
    }
    public void stopWeapon() {
        firing = false;
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);

        if(direction == 0) {
            g.fillRect(x-width, y+(height/4), width, width/2);
        }
        if(direction == 1) {
            g.fillRect(x+(width/4), y-height, width/2, width);
        }
        if(direction == 2) {
            g.fillRect(x+width, y+(height/4), width, width/2);
        }
        if(direction == 3) {
            g.fillRect(x+(width/4), y+height, width/2, width);
        }

        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }

    public void paintHealthbar(Graphics g, int x, int y, int width, int height) {
        double percentage = width;
        percentage /= 100;
        percentage *= (health/10);
        /**
         * Percentages:       1% = (width/400)
         *                  100% = (width/400) * 100
         */
        g.setColor(Color.black);
        g.fillRect(x-2, y-2, width+4, height+4);
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
        g.setColor(Color.green);
        g.fillRect(x, y, (int)Math.ceil(percentage), height);
    }
}
