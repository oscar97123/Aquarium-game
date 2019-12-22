import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.RejectedExecutionException;

/*
 * 資管4A
 * 105403031  莫智堯
 */

public class fish implements Runnable {
    private final JPanel fishBowl_JPanel;
    private final JLabel Fish_JLabel;
    private final int mouse_X_point, mouse_Y_point;
    private int randomSpeed;
    private int fish_direction = -1;
    private SecureRandom secureRandom = new SecureRandom();
    private Image fish_left, fish_right;
    private int fish_width = 70; //魚的最小闊度為70
    private int fish_height = 60; //魚的最小高度為60
    private int x_position_now;
    private int count;

    public fish(int x, int y, JLabel Fish_JLabel, JPanel fishBowl_JPanel){
        this.fishBowl_JPanel = fishBowl_JPanel;

        this.Fish_JLabel = Fish_JLabel;

        fish_width += secureRandom.nextInt(40); //隨機魚的闊度
        fish_height += secureRandom.nextInt(40); //隨機魚的高度
        fish_direction = secureRandom.nextInt(2); //隨機魚的方向

        random_fish_type(secureRandom.nextInt(3)); //隨機魚的類型
        random_fish_size_n_direction(fish_width, fish_height, fish_direction); //隨機魚的大小 ＋ 方向
        randomSpeed = secureRandom.nextInt(80) + 5; //隨機魚的速度

        mouse_X_point = x; //按下mouse 時的x點坐標
        mouse_Y_point = y; //按下mouse 時的y點坐標

        x_position_now = mouse_X_point; //初始化現在的x位置 用於決定魚要在那出現

        System.out.println("Fish X: " + mouse_X_point);
        System.out.println("Fish Y: " + mouse_Y_point);
    }

    private void random_fish_size_n_direction(int width, int height, int direction_int) {

        if (direction_int == 0) { //如果input數字是0，魚頭向左

            fish_left = fish_left.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            Fish_JLabel.setIcon(new ImageIcon(fish_left));

        }else { //反之，魚頭向右

            fish_right = fish_right.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            Fish_JLabel.setIcon(new ImageIcon(fish_right));
        }
    }

    private void random_fish_type(int num) {
        try {
            switch (num) {
                case 0:
                    fish_left = ImageIO.read(new File("src/2.png")); //讀入該魚的左 & 右 圖片
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
            while (true) { //不停執行 讓魚能移動

                if (fish_direction == 1) { //魚面向右面
                    Fish_JLabel.setBounds(x_position_now, mouse_Y_point, 100, 95); //產生魚的位置
                    x_position_now++; //向右移動
                } else {
                    Fish_JLabel.setBounds(x_position_now, mouse_Y_point, 100, 95);
                    x_position_now--;//向左移動
                }

                fish_reach_border(); //當魚到達邊界時

//                Thread.sleep(secureRandom.nextInt(200));
                count++; //記錄執行了多少次

                if (count == 250){ //隔一段時間重新 random 速度 (每執行第250次)
                    randomSpeed = secureRandom.nextInt(80) + 5;

                    //重新決定方向
                    fish_direction = secureRandom.nextInt(2);
                    random_fish_size_n_direction(fish_width, fish_height, fish_direction); //套回function 改變魚頭的方向
                    //

                    count = 0; //重算
                }

                Thread.sleep(randomSpeed); //以thread sleep來控制魚的速度
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
            fish_direction = 1; //往左遊
            random_fish_size_n_direction(fish_width, fish_height, fish_direction);
        }
    }
}
