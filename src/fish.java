import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.RejectedExecutionException;

public class fish implements Runnable {
    private final JPanel fishBowl_JPanel;
    private final JLabel Fish_JLabel;
    private final int mouse_X_point, mouse_Y_point;
//    private ImageIcon fish1_left, fish1_right, fish2_left, fish2_right, fish3_left, fish3_right;
    private SecureRandom secureRandom = new SecureRandom();
    private Image fish_left, fish_right;
    private int fish_width = 70; //魚的最小闊度為70
    private int fish_height = 60; //魚的最小高度為60
    private int x_position_now;
    private int y_position_now;

    public fish(int x, int y, JLabel Fish_JLabel, JPanel fishBowl_JPanel){
        this.fishBowl_JPanel = fishBowl_JPanel;
        this.Fish_JLabel = Fish_JLabel;

        fish_width += secureRandom.nextInt(40);
        fish_height += secureRandom.nextInt(40);

        random_fish_type(secureRandom.nextInt(3));
        random_fish_size_n_direction(fish_width, fish_height, secureRandom.nextInt(2));
/*
        fish1_left = new ImageIcon("src/2.png");
        fish1_right = new ImageIcon("src/1.png");
        fish2_left = new ImageIcon("src/4.png");
        fish2_right = new ImageIcon("src/3.png");
        fish3_left = new ImageIcon("src/6.png");
        fish3_right = new ImageIcon("src/5.png");

 */

        mouse_X_point = x;
        mouse_Y_point = y;

        System.out.println("Fish X: " + mouse_X_point);
        System.out.println("Fish Y: " + mouse_Y_point);

        System.out.println("fishbowl width: " + fishBowl_JPanel.getWidth());
        System.out.println("fishbowl height: " + fishBowl_JPanel.getHeight());

    }

    private void random_fish_size_n_direction(int width, int height, int direction_int) {
        if (direction_int == 0) {
            fish_left = fish_left.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            Fish_JLabel.setIcon(new ImageIcon(fish_left));
        }else {

            fish_right = fish_right.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            Fish_JLabel.setIcon(new ImageIcon(fish_right));
        }
    }

    private void random_fish_type(int num) {
        try {
            switch (num) {
                case 0:
                    fish_left = ImageIO.read(new File("src/2.png"));
                    fish_right = ImageIO.read(new File("src/1.png"));
                    break;

                case 1:
                    fish_left = ImageIO.read(new File("src/4.png"));
                    fish_right = ImageIO.read(new File("src/3.png"));
                    break;

                case 2:
                    fish_left = ImageIO.read(new File("src/6.png"));
                    fish_right = ImageIO.read(new File("src/5.png"));
                    break;
            }
        }catch (IOException e){
            System.err.println("No such file.");
        }
    }

    @Override
    public void run() throws RejectedExecutionException {
        //Fish_JLabel.setBounds(mouse_X_point, mouse_Y_point, fishBowl_JPanel.getWidth(), fishBowl_JPanel.getHeight());//顯示在panel上
        while (true) {
            Fish_JLabel.setBounds(mouse_X_point, mouse_Y_point, 100, 95);//顯示在panel上

            x_position_now = Fish_JLabel.getLocationOnScreen().x;
            y_position_now = Fish_JLabel.getLocationOnScreen().y;

            //Fish_JLabel.setLocation(Fish_JLabel.getLocationOnScreen().x + 1, Fish_JLabel.getLocationOnScreen().y);
            Fish_JLabel.setLocation(x_position_now, y_position_now);
            x_position_now++;
            y_position_now++;
        }
    }
}
