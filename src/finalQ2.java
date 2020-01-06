import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 資管4A
 * 105403031  莫智堯
 */

public class finalQ2 extends JFrame{
    private final JButton newFish_Btn;
    private final JButton newTurtle_Btn;
    private final JButton removeSelected_Btn;
    private final JButton removeAllElement_Btn;
    private JLabel status_JLabel;
    private JPanel fishBowl_JPanel;
    private JPanel menu_JPanel;
    private ExecutorService executorService = Executors.newCachedThreadPool(); // 建立 ExecutorService 以管理 threads
    private int mouse_X_point, mouse_Y_point;
    private int current_function = -1; //記錄User 按下那個按鈕  0：新增魚 1：新增烏龜 2：移除選取 3：移除全部
    private ArrayList<JLabel> fish_ArrayList = new ArrayList<JLabel>(); //用ArrayList儲存所有fish
    private ArrayList<JLabel> turtle_ArrayList = new ArrayList<JLabel>();
    private int fish_count = 0; //記錄建立了多少條魚
    private int Turtle_count = 0;

    public finalQ2(){
        super("FishBowl");

        fishBowl_JPanel = new JPanel();
        fishBowl_JPanel.setLayout(null);
        fishBowl_JPanel.setBackground(Color.decode("#84D8F7")); //設定背景顏色，讓水族箱的顏色像海水顏色
        fishBowl_JPanel_Mouse_tracking();

        menu_JPanel = new JPanel();
        menu_JPanel.setLayout(new GridLayout(3, 2));

        newFish_Btn = new JButton("新增魚");
        newTurtle_Btn = new JButton("新增烏龜");
        removeSelected_Btn = new JButton("移除選取");
        removeAllElement_Btn = new JButton("移除全部");
        status_JLabel = new JLabel("目前功能：null            魚數量：0  烏龜數量：0");

        button_OnClick();

        menu_JPanel.add(newFish_Btn);
        menu_JPanel.add(removeSelected_Btn);
        menu_JPanel.add(newTurtle_Btn);
        menu_JPanel.add(removeAllElement_Btn);
        menu_JPanel.add(status_JLabel);

        add(menu_JPanel, BorderLayout.NORTH);
        add(fishBowl_JPanel, BorderLayout.CENTER);
    }

    private void fishBowl_JPanel_Mouse_tracking() {
        fishBowl_JPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getPoint());
                mouse_X_point = e.getX(); //記錄按下時 X點位標
                mouse_Y_point = e.getY(); //記錄按下時 Y點位標

                switch (current_function){
                    case 0:
                        JLabel Fish_JLabel = new JLabel();
                        fish fish = new fish(mouse_X_point, mouse_Y_point, Fish_JLabel, fishBowl_JPanel);
                        fishBowl_JPanel.add(Fish_JLabel); //把魚的JLabel放在水族箱JPanel裡
                        Fish_JLabel.setVisible(true);
                        fish_ArrayList.add(Fish_JLabel); //用ArrayList把新的fish 記錄起來
                        executorService.execute(fish);
                        fish_count++;
                        status_JLabel.setText("目前功能：新增魚            魚數量：" + fish_count + "   烏龜數量：" + Turtle_count);
                        break;

                    case 1:
                        JLabel Turtle_JLabel = new JLabel();
                        turtle turtle = new turtle(mouse_X_point, mouse_Y_point, Turtle_JLabel, fishBowl_JPanel);
                        fishBowl_JPanel.add(Turtle_JLabel); //把魚的JLabel放在水族箱JPanel裡
                        turtle_ArrayList.add(Turtle_JLabel); //用ArrayList把新的turtle 記錄起來
                        executorService.execute(turtle);
                        Turtle_count++;
                        status_JLabel.setText("目前功能：新增烏龜         魚數量：" + fish_count + "   烏龜數量：" + Turtle_count);
                        break;

                    case 2:
                        status_JLabel.setText("目前功能：移除選取         魚數量：" + fish_count + "   烏龜數量：" + Turtle_count);
                        break;

                    case 3:
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void button_OnClick() {
        newFish_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_function = 0;
                setBtnColor();
                status_JLabel.setText("目前功能：新增魚            魚數量：" + fish_count + "   烏龜數量：" + Turtle_count);
            }
        });

        newTurtle_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_function = 1;
                setBtnColor();
                status_JLabel.setText("目前功能：新增烏龜         魚數量：" + fish_count + "   烏龜數量：" + Turtle_count);
            }
        });

        removeSelected_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_function = 2;
                setBtnColor();
                status_JLabel.setText("目前功能：移除選取         魚數量：" + fish_count + "   烏龜數量：" + Turtle_count);
            }
        });

        removeAllElement_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_function = 3;
                setBtnColor();
                fish_count = 0;
                Turtle_count = 0;
                fish_ArrayList.clear();
                turtle_ArrayList.clear();
                fishBowl_JPanel.removeAll();
                fishBowl_JPanel.repaint();
                executorService.shutdownNow();
                executorService = Executors.newCachedThreadPool();
                status_JLabel.setText("目前功能：移除全部         魚數量：" + fish_count + "   烏龜數量：" + Turtle_count);
            }
        });
    }

    private void setBtnColor() {
        newFish_Btn.setForeground(Color.BLACK);
        newTurtle_Btn.setForeground(Color.BLACK);
        removeSelected_Btn.setForeground(Color.BLACK);
        removeAllElement_Btn.setForeground(Color.BLACK);


        switch (current_function){
            case 0:
                newFish_Btn.setForeground(Color.RED);
                break;

            case 1:
                newTurtle_Btn.setForeground(Color.RED);
                break;

            case 2:
                removeSelected_Btn.setForeground(Color.RED);
                break;

            case 3:
                removeAllElement_Btn.setForeground(Color.RED);
                break;
        }
    }
}
