import java.awt.*;

public class HealPod {

    private int x, y, width, height;

    public HealPod(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void checkCollision(Player player) {
        if(player.x <= x + width &&
                player.x + player.width >= x &&
                player.y <= y + height &&
                player.y + player.height >= y) {
            player.heal(2);
        }
    }

    @SuppressWarnings("Duplicates")
    public Rectangle getClip(int offset) {

        int clipX = x - offset;
        int clipY = y - offset;
        int clipWidth = width + offset;
        int clipHeight = height + offset;

        return new Rectangle(clipX, clipY, clipWidth, clipHeight);
    }

    public void paint(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x,y,width,height);
    }
}
