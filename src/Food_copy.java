import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.security.*;

public class Food_copy implements Runnable{
    //-----------------------------------		//承接reference
    private static final SecureRandom random = new SecureRandom();
    private final JPanel bowl;
    private final JLabel foodlabel;
    //-----------------------------------		//龜屬性
    private int x;
    private int y;
    private int x_position_now ,y_position_now;
    public int speed;
    public int time=0;
    public static int count =0;
    public Image fishright;
    public Image fishleft;
    public double sizeratio;
    public boolean faceright;
    public boolean interrupted = false;
    public int error;//修正每隻烏龜底下的空白處
    private int width, height;

    public Food_copy(int x, int y, JLabel foodlabel, JPanel bowl)
    {
        x_position_now = x; //按下mouse 時的x點坐標
        y_position_now = y; //按下mouse 時的y點坐標
        this.bowl = bowl;
        this.foodlabel = foodlabel;
        //count++;
        kind();//載入圖片
        size(random.nextInt(40)+30);//random大小
        dir(random.nextBoolean());//random方向
        speed = random.nextInt(10)+10;//random速度
        error = 0;//得烏龜空白
    }

    public void run()
    {
        try
        {
            int i = 0;
            while(true)
            {

                clock();


                fixPositionMethod();//修正邊界變化時位置調整
                if(interrupted) {
                    throw new InterruptedException();
                }


                foodlabel.setBounds(x_position_now, y_position_now, 50, 50);//顯示在panel上
                //positionNext();//到下一個點
                Thread.sleep(speed);//控制速度


                i++;
                if(i > random.nextInt(40)+40)//不定期random速度跟方向
                {
                    speed = random.nextInt(50)+50;
                    dir(random.nextBoolean());
                    i = 0;
                }
            }
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();

        }
    }

    public void drop()//下墜
    {
        foodlabel.setBounds(x_position_now, y_position_now, 50, 50);//顯示在panel上
        while(!reachground())//沒有觸地
        {
            foodlabel.setBounds(x_position_now, y_position_now, 50, 50);
            y_position_now++;
            try
            {
                Thread.sleep(15);//下墜速度
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void elevate()//抬升至地面
    {
        //tlabel.setBounds(positionNow.x, positionNow.y, width, height);//顯示在panel上
        while(reachborder())//在地底下
        {
            foodlabel.setBounds(x_position_now, y_position_now, 50, 50);
            y_position_now--;
            try
            {
                Thread.sleep(0);//抬升速度
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void kind()//載入圖片
    {
        try
        {

            fishright = ImageIO.read(new File("cookie.png"));
            fishleft = ImageIO.read(new File("cookie.png"));
            sizeratio = 145/113;
        }
        catch(Exception e)
        {
            System.out.println("No File");
        }
    }

    public void size(int w)//決定大小
    {
        width = w;
        height = (int)(width * sizeratio);
        fishright = fishright.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        fishleft = fishleft.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        x_position_now = x_position_now-width/2;
        y_position_now = y_position_now-height/2;
//        positionNow = new Point(x_position_now-width/2, y_position_now-height/2);
    }

    public void dir(boolean face)//決定方向
    {
        faceright = face;
        if(faceright)
            foodlabel.setIcon(new ImageIcon(fishright));
        else
            foodlabel.setIcon(new ImageIcon(fishleft));
    }

    public void positionNext()
    {
        int x = x_position_now;
        int y = y_position_now;

        if(faceright)		//龜往右爬
            x_position_now++;
        else				//龜往左爬
            x_position_now--;
        if(reachborder())
        {
            x_position_now = x;
            y_position_now = y;
            faceright = !faceright;//轉向
            if(faceright)
                foodlabel.setIcon(new ImageIcon(fishright));
            else
                foodlabel.setIcon(new ImageIcon(fishleft));
        }
    }

    public boolean reachborder()//碰到或超過上下左右邊界
    {
        if((x_position_now <= 0) || (x_position_now + width >= bowl.getBounds().getWidth()))
            return true;
        if(y_position_now + height >= bowl.getBounds().getHeight()+error)
            return true;

        return false;
    }

    public boolean reachground()//觸地
    {
        if(y_position_now + height >= bowl.getBounds().getHeight()+error)
            return true;
        return false;
    }

    public void fixPositionMethod()//修正邊界變化時位置調整
    {
        drop();
        while(reachborder())//碰到或超過上下左右邊界
        {
            foodlabel.setBounds(x_position_now, y_position_now, 50, 50);
            if((x_position_now <= 0) || (x_position_now + width >= bowl.getBounds().getWidth()))
                x_position_now--;
            if(y_position_now + height >= bowl.getBounds().getHeight()+error)
               y_position_now--;
            try
            {
                Thread.sleep(0);//移動速度
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void clock() {
        if(reachground()!=true) {
            time++;
        }

        if(time>=50) {
            count--;
//            FishBowl.changeStatusString(FishBowl.fn, FishBowl.tn, FishBowl.buttonNumber);
            foodlabel.setVisible(false);
            interrupted = true;
        }

    }
/*
    public Point getPositionNow() {
        return positionNow.getLocation();
    }

 */
}
