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

public class Food implements Runnable{
    private final JPanel fishBowl_JPanel;
    private final JLabel Food_JLabel;
    private final int mouse_X_point, mouse_Y_point;
    private int randomSpeed;
    private int turtle_direction;
    private Image turtle_left, turtle_right;
    private int x_position_now ,y_position_now;
    private SecureRandom secureRandom = new SecureRandom();
    private boolean turtle_touch_the_ground = false; //一開始鳥龜還未接觸到地面
    private int count = 0;

    public Food(int x, int y, JLabel Food_JLabel, JPanel fishBowl_JPanel){
        this.fishBowl_JPanel = fishBowl_JPanel;

        this.Food_JLabel = Food_JLabel;

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
                Food_JLabel.setIcon(new ImageIcon(turtle_left));
                break;

            case 1:
                Food_JLabel.setIcon(new ImageIcon(turtle_right));
                break;
        }
    }

    private void read_turtle_image() {
        try {

            turtle_left = ImageIO.read(new File("src/cookie.png")); //讀入烏龜的左 & 右 圖片
            turtle_right = ImageIO.read(new File("src/cookie.png"));

        } catch (IOException e) {

            System.err.println("No such file.");

        }
    }

    @Override
    public void run() {
        Food_JLabel.setBounds(mouse_X_point, y_position_now, 96, 74);//顯示在panel上
        drop(); //下墜method

        try {
            while (turtle_touch_the_ground) {

                if (turtle_direction == 1) { //烏龜面向右面
                    Food_JLabel.setBounds(x_position_now, fishBowl_JPanel.getHeight() - Food_JLabel.getHeight() + 17, 96, 74); //產生烏龜的位置
                } else {
                    Food_JLabel.setBounds(x_position_now, fishBowl_JPanel.getHeight() - Food_JLabel.getHeight() + 17, 96, 74);
                }

                turtle_reach_border(); //當烏龜到達邊界時
/*
                if (count == 300){ //執行第300次, 飼料消失


                }
                
 */

                Thread.sleep(randomSpeed); //以thread sleep來控制烏龜的速度
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void drop() {

        while(y_position_now < fishBowl_JPanel.getHeight() - Food_JLabel.getHeight() + 17)//沒有觸地
        // PS.  減掉 Turtle_JLabel.getHeight() + 17  是因為讓烏龜的腳一碰到地面就停止
        {
            Food_JLabel.setBounds(mouse_X_point, y_position_now, 96, 74);
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
        if (x_position_now > fishBowl_JPanel.getWidth() - Food_JLabel.getWidth()){ //扣掉烏龜的闊度 才能在烏龜碰到邊緣時馬上回頭
            turtle_direction = 0; //往右遊
            random_turtle_direction_n_rescale(turtle_direction); //套回function 改變烏龜面向的方向
        }

        if (x_position_now < 0){
            turtle_direction = 1; //往左遊
            random_turtle_direction_n_rescale(turtle_direction); //套回function 改變烏龜面向的方向
        }

    }
}
