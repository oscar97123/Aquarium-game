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

public class turtle implements Runnable{
    private final JPanel fishBowl_JPanel;
    private final JLabel Turtle_JLabel;
    private final int mouse_X_point, mouse_Y_point;
    private int turtle_width = 70; //鳥龜的最小闊度為70
    private int turtle_height = 60; //鳥龜的最小高度為60
    private int randomSpeed;
    private int turtle_direction;
    private Image turtle_left, turtle_right;
    private int x_position_now ,y_position_now;
    private SecureRandom secureRandom = new SecureRandom();
    private boolean turtle_touch_the_ground = false; //一開始鳥龜還未接觸到地面
    private int count = 0;

    public turtle(int x, int y, JLabel Turtle_JLabel, JPanel fishBowl_JPanel){
        this.fishBowl_JPanel = fishBowl_JPanel;

        this.Turtle_JLabel = Turtle_JLabel;

        mouse_X_point = x; //按下mouse 時的x點坐標
        mouse_Y_point = y; //按下mouse 時的y點坐標

        x_position_now = mouse_X_point;
        y_position_now = mouse_Y_point;


        turtle_width += secureRandom.nextInt(40); //隨機鳥龜的闊度
        turtle_height += secureRandom.nextInt(40); //隨機鳥龜的高度
        turtle_direction = secureRandom.nextInt(2);

        read_turtle_image();
        random_turtle_direction_n_rescale(turtle_width, turtle_height, turtle_direction);

        randomSpeed = secureRandom.nextInt(80) + 5; //隨機烏龜的速度
    }

    private void random_turtle_direction_n_rescale(int width, int height, int direction_int) {

        turtle_left = turtle_left.getScaledInstance(width, height, Image.SCALE_DEFAULT); //Resize 圖片
        turtle_right = turtle_right.getScaledInstance(width, height, Image.SCALE_DEFAULT);

        switch (direction_int){
            case 0:
                Turtle_JLabel.setIcon(new ImageIcon(turtle_left));
                break;

            case 1:
                Turtle_JLabel.setIcon(new ImageIcon(turtle_right));
                break;
        }
    }

    private void read_turtle_image() {
        try {

            turtle_left = ImageIO.read(new File("src/w2.png")); //讀入烏龜的左 & 右 圖片
            turtle_right = ImageIO.read(new File("src/w.png"));

        } catch (IOException e) {

            System.err.println("No such file.");

        }
    }

    @Override
    public void run() {
        Turtle_JLabel.setBounds(mouse_X_point, y_position_now, 96, 74);//顯示在panel上
        drop(); //下墜method

        try {
            while (turtle_touch_the_ground) {

                if (turtle_direction == 1) { //烏龜面向右面
                    Turtle_JLabel.setBounds(x_position_now, fishBowl_JPanel.getHeight() - Turtle_JLabel.getHeight() + 17, 96, 74); //產生烏龜的位置
                    x_position_now++; //向右移動
                } else {
                    Turtle_JLabel.setBounds(x_position_now, fishBowl_JPanel.getHeight() - Turtle_JLabel.getHeight() + 17, 96, 74);
                    x_position_now--;//向左移動
                }

                turtle_reach_border(); //當烏龜到達邊界時

                if (count == 200){ //隔一段時間重新 random 速度 (每執行第200次)

                    turtle_width = turtle_width -10;
                    turtle_height = turtle_height -10;
                    random_turtle_direction_n_rescale(turtle_width, turtle_height, turtle_direction); //套回function 改變烏龜面向的方向

                    count = 0; //重算
                }

                Thread.sleep(randomSpeed); //以thread sleep來控制烏龜的速度
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void drop() {

        while(y_position_now < fishBowl_JPanel.getHeight() - Turtle_JLabel.getHeight() + 17)//沒有觸地
        // PS.  減掉 Turtle_JLabel.getHeight() + 17  是因為讓烏龜的腳一碰到地面就停止
        {
            Turtle_JLabel.setBounds(mouse_X_point, y_position_now, 96, 74);
            y_position_now++;

            try
            {
                Thread.sleep(3);//下墜速度
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }

        turtle_touch_the_ground = true;
    }

    private void turtle_reach_border() {
        if (x_position_now > fishBowl_JPanel.getWidth() - Turtle_JLabel.getWidth()){ //扣掉烏龜的闊度 才能在烏龜碰到邊緣時馬上回頭
            turtle_direction = 0; //往右遊
            random_turtle_direction_n_rescale(turtle_width, turtle_height, turtle_direction); //套回function 改變烏龜面向的方向
        }

        if (x_position_now < 0){
            turtle_direction = 1; //往左遊
            random_turtle_direction_n_rescale(turtle_width, turtle_height, turtle_direction); //套回function 改變烏龜面向的方向
        }
    }
}
