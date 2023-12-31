import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

/*
 * 資管4A
 * 105403031  莫智堯
 */

public class Fishing implements Runnable{
    private final JPanel fishBowl_JPanel;
    private final JLabel Fishing_JLabel;
    private final int mouse_X_point, mouse_Y_point;
    private int randomSpeed;
    private int turtle_direction;
    private Image turtle_left, turtle_right;
    private int x_position_now ,y_position_now;
    private SecureRandom secureRandom = new SecureRandom();
    private boolean turtle_touch_the_ground = false; //一開始鳥龜還未接觸到地面
    private int count = 0;
    private boolean goingup;

    public Fishing(int x, int y, JLabel Fishing_JLabel, JPanel fishBowl_JPanel){
        this.fishBowl_JPanel = fishBowl_JPanel;

        this.Fishing_JLabel = Fishing_JLabel;

        mouse_X_point = x; //按下mouse 時的x點坐標
        mouse_Y_point = y; //按下mouse 時的y點坐標

        x_position_now = mouse_X_point;
        y_position_now = mouse_Y_point;


        turtle_direction = secureRandom.nextInt(2);

        read_turtle_image();
        random_turtle_direction_n_rescale(turtle_direction);

        randomSpeed = secureRandom.nextInt(80) + 5; //隨機烏龜的速度
    }

    private void random_turtle_direction_n_rescale(int direction_int) {

        turtle_left = turtle_left.getScaledInstance(50, 50, Image.SCALE_DEFAULT); //Resize 圖片
        turtle_right = turtle_right.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        switch (direction_int){
            case 0:
                Fishing_JLabel.setIcon(new ImageIcon(turtle_left));
                break;

            case 1:
                Fishing_JLabel.setIcon(new ImageIcon(turtle_right));
                break;
        }
    }

    private void read_turtle_image() {
        try {

            turtle_left = ImageIO.read(new File("src/fishing.png")); //讀入烏龜的左 & 右 圖片
            turtle_right = ImageIO.read(new File("src/fishing.png"));

        } catch (IOException e) {

            System.err.println("No such file.");

        }
    }

    @Override
    public void run() {
        Fishing_JLabel.setBounds(mouse_X_point, y_position_now, 96, 74);//顯示在panel上
        drop(); //下墜method

        try {
            while (!turtle_touch_the_ground) {

                drop();

                Thread.sleep(randomSpeed); //以thread sleep來控制烏龜的速度
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void drop() {

        while (goingup){
            Fishing_JLabel.setBounds(mouse_X_point, y_position_now, 96, 74);
            --y_position_now;

            count++;
            if (count == 30){
                goingup = false;
                count = 0;
            }

            try
            {
                Thread.sleep(100);//下墜速度
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }

        while (!goingup){
            Fishing_JLabel.setBounds(mouse_X_point, y_position_now, 96, 74);
            ++y_position_now;

            count++;
            if (count == 30){
                goingup = true;
                count = 0;
            }

            try
            {
                Thread.sleep(100);//下墜速度
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("loop exit");
        System.out.println("mouse_Y_point:" + mouse_Y_point);
        System.out.println("y_position_now:" + y_position_now);


        turtle_touch_the_ground = false;
    }

    private void turtle_reach_border() {
        if (x_position_now > fishBowl_JPanel.getWidth() - Fishing_JLabel.getWidth()){ //扣掉烏龜的闊度 才能在烏龜碰到邊緣時馬上回頭
            turtle_direction = 0; //往右遊
            random_turtle_direction_n_rescale(turtle_direction); //套回function 改變烏龜面向的方向
        }

        if (x_position_now < 0){
            turtle_direction = 1; //往左遊
            random_turtle_direction_n_rescale(turtle_direction); //套回function 改變烏龜面向的方向
        }

    }
}
