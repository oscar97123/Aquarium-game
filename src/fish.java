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
    private int randomSpeed;
    private int fish_direction = -1;
    //    private ImageIcon fish1_left, fish1_right, fish2_left, fish2_right, fish3_left, fish3_right;
    private SecureRandom secureRandom = new SecureRandom();
    private Image fish_left, fish_right;
    private int fish_width = 70; //魚的最小闊度為70
    private int fish_height = 60; //魚的最小高度為60
    private int x_position_now;

    public fish(int x, int y, JLabel Fish_JLabel, JPanel fishBowl_JPanel){
        this.fishBowl_JPanel = fishBowl_JPanel;

        this.Fish_JLabel = Fish_JLabel;

        fish_width += secureRandom.nextInt(40);
        fish_height += secureRandom.nextInt(40);
        fish_direction = secureRandom.nextInt(2);

        random_fish_type(secureRandom.nextInt(3));
        random_fish_size_n_direction(fish_width, fish_height, fish_direction);
        randomSpeed = secureRandom.nextInt(80) + 10;
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

        x_position_now = mouse_X_point;

        System.out.println("Fish X: " + mouse_X_point);
        System.out.println("Fish Y: " + mouse_Y_point);

//        System.out.println("fishbowl width: " + fishBowl_JPanel.getWidth());
//        System.out.println("fishbowl height: " + fishBowl_JPanel.getHeight());

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
        try {
            while (true) {

//                System.out.println("x_position_now: " + x_position_now);
//                System.out.println("y_position_now: " + mouse_Y_point);

                if (fish_direction == 1) { //魚面向右面
                    Fish_JLabel.setBounds(x_position_now, mouse_Y_point, 100, 95);
                    x_position_now++;
                } else {
                    Fish_JLabel.setBounds(x_position_now, mouse_Y_point, 100, 95);
                    x_position_now--;
                }

                fish_reach_border();

//                Thread.sleep(secureRandom.nextInt(200));
                Thread.sleep(randomSpeed);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void fish_reach_border() {
        if (x_position_now > fishBowl_JPanel.getWidth() - fish_width){ //扣掉魚的闊度 才能在魚頭碰到邊緣時馬上回頭
            fish_direction = 0; //往右遊
            random_fish_size_n_direction(fish_width, fish_height, fish_direction); //套回function 改變魚頭的方向
        }

        if (x_position_now < 0){
            fish_direction = 1;
            random_fish_size_n_direction(fish_width, fish_height, fish_direction);
        }
    }
}
