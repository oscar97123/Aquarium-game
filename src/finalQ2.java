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
    private final JButton newFood_Btn;
    private final JButton removeAllElement_Btn;
    private final JButton fishing_Btn;
    private JPanel topPanel;
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
    private int Food_count = 0;
    private int Fishing_count = 0;
    private String current_function_name;
    private int FishGetCaught_count = 0;
    private int TurtleGetCaught_count = 0;

    public finalQ2(){
        super("FishBowl");

        fishBowl_JPanel = new JPanel();
        fishBowl_JPanel.setLayout(null);
        fishBowl_JPanel.setBackground(Color.decode("#84D8F7")); //設定背景顏色，讓水族箱的顏色像海水顏色
        fishBowl_JPanel_Mouse_tracking();

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        menu_JPanel = new JPanel();
        menu_JPanel.setLayout(new GridLayout(3, 2));

        newFish_Btn = new JButton("新增魚");
        newTurtle_Btn = new JButton("新增烏龜");
        newFood_Btn = new JButton("新增飼料");
        removeAllElement_Btn = new JButton("移除全部");
        fishing_Btn = new JButton("新增釣竿");
        status_JLabel = new JLabel("目前功能：null            魚數量：0  烏龜數量：0  飼料數量：0  釣竿數量：0  已釣到的魚數量：0" +
                "  已釣到烏龜數量：0");

        button_OnClick();

        menu_JPanel.add(newFish_Btn);
        menu_JPanel.add(newFood_Btn);
        menu_JPanel.add(newTurtle_Btn);
        menu_JPanel.add(removeAllElement_Btn);
        menu_JPanel.add(fishing_Btn);

        topPanel.add(menu_JPanel, BorderLayout.CENTER);
        topPanel.add(status_JLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
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
                        status_JLabelsetText();
                        break;

                    case 1:
                        JLabel Turtle_JLabel = new JLabel();
                        turtle turtle = new turtle(mouse_X_point, mouse_Y_point, Turtle_JLabel, fishBowl_JPanel);
                        fishBowl_JPanel.add(Turtle_JLabel); //把魚的JLabel放在水族箱JPanel裡
                        turtle_ArrayList.add(Turtle_JLabel); //用ArrayList把新的turtle 記錄起來
                        executorService.execute(turtle);
                        Turtle_count++;
                        status_JLabelsetText();
                        break;

                    case 2:
                        JLabel Food_JLabel = new JLabel();
                        Food food = new Food(mouse_X_point, mouse_Y_point, Food_JLabel, fishBowl_JPanel);
                        fishBowl_JPanel.add(Food_JLabel); //把魚的JLabel放在水族箱JPanel裡
                        turtle_ArrayList.add(Food_JLabel); //用ArrayList把新的turtle 記錄起來
                        executorService.execute(food);
                        status_JLabelsetText();
                        Food_count++;
                        break;

                    case 3:
                        status_JLabelsetText();
                        break;

                    case 4:
                        status_JLabelsetText();
                        Fishing_count++;
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
                current_function_name = "新增魚";
                status_JLabelsetText();
            }
        });

        newTurtle_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_function = 1;
                setBtnColor();
                current_function_name = "新增烏龜";
                status_JLabelsetText();
            }
        });

        newFood_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_function = 2;
                setBtnColor();
                current_function_name = "新增飼料";
                status_JLabelsetText();
            }
        });

        removeAllElement_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_function = 3;
                setBtnColor();
                fish_count = 0;
                Turtle_count = 0;
                Food_count = 0;
                FishGetCaught_count = 0;
                TurtleGetCaught_count = 0;
                Fishing_count = 0;
                fish_ArrayList.clear();
                turtle_ArrayList.clear();
                fishBowl_JPanel.removeAll();
                fishBowl_JPanel.repaint();
                executorService.shutdownNow();
                executorService = Executors.newCachedThreadPool();
                current_function_name = "移除全部";
                status_JLabelsetText();
            }
        });

        fishing_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_function = 4;
                setBtnColor();
                current_function_name = "新增釣竿";
                status_JLabelsetText();
            }
        });
    }

    private void status_JLabelsetText() {
        status_JLabel.setText("目前功能："+ current_function_name + "            魚數量：" + fish_count + "  烏龜數量：" + Turtle_count
        + "  飼料數量：" + Food_count + "  釣竿數量：" + Fishing_count + "  已釣到的魚數量：" + FishGetCaught_count + "  已釣到烏龜數量：" + TurtleGetCaught_count);
    }

    private void setBtnColor() {
        newFish_Btn.setForeground(Color.BLACK);
        newTurtle_Btn.setForeground(Color.BLACK);
        newFood_Btn.setForeground(Color.BLACK);
        removeAllElement_Btn.setForeground(Color.BLACK);
        fishing_Btn.setForeground(Color.BLACK);


        switch (current_function){
            case 0:
                newFish_Btn.setForeground(Color.RED);
                break;

            case 1:
                newTurtle_Btn.setForeground(Color.RED);
                break;

            case 2:
                newFood_Btn.setForeground(Color.RED);
                break;

            case 3:
                removeAllElement_Btn.setForeground(Color.RED);
                break;

            case 4:
                fishing_Btn.setForeground(Color.RED);
                break;
        }
    }
}
