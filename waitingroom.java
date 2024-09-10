package com.example.fightthelandlord;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.text.html.ImageView;

import java.awt.Toolkit;


public class waitingroom {
    private boolean isReady = false;
    private final JFrame frame = new JFrame("Waiting Room");
    private final JPanel panel = new JPanel();
    private final JLabel bg;
    private final JPanel btnGroup;
    private  JLabel player1;
    private  JLabel player2;
    private JLabel playerUnready;
    private JLabel noPlayers;
    private final JButton btnReady;
    private final JButton btnQuit;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    private Toolkit toolkit;
    private final int screenWidth;
    private final int screenHeight;

    public waitingroom(int x, int y) {

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/image/desk.jpg"));

        this.bg = new JLabel(backgroundImage);
        this.player1 = new JLabel(new ImageIcon(getClass().getResource("/image/GG_ready1.png")));
        this.player2 = new JLabel(new ImageIcon(getClass().getResource("/image/GG_ready2.png")));
        this.playerUnready = new JLabel(new ImageIcon(getClass().getResource("/image/meS.png")));
        this.noPlayers = new JLabel(new ImageIcon(getClass().getResource("/image/defaultHeader.png")));
        this.btnQuit = new JButton(new ImageIcon(getClass().getResource("/image/quit.png")));
        this.btnReady = new JButton(new ImageIcon(getClass().getResource("/image/ready.png")));
        this.btnGroup = new JPanel();

        this.x = x;
        this.y = y;
        this.width = 650;
        this.height = 450;


        //this.screenWidth = 1200;
        //this.screenHeight = 800;
        this.toolkit = Toolkit.getDefaultToolkit();
        this.screenWidth = toolkit.getScreenSize().width;
        this.screenHeight = toolkit.getScreenSize().height;
    }

    public void show(final Player player) {
        // 设置按钮图标
        this.btnReady.setIcon(createScaledIcon("/image/ready.png", 100, 50));
        this.btnQuit.setIcon(createScaledIcon("/image/quit.png", 100, 50));

        // 设置背景图标
        this.bg.setIcon(createScaledIcon("/image/desk.jpg", this.x, this.y));

        //添加人物
        this.player1.setBounds(70,70,150,320);
        this.player1.setOpaque(false);
        this.panel.add(this.player1);

        this.player2.setBounds(970,70,150,320);
        this.player2.setOpaque(false);
        this.panel.add(this.player2);

        this.playerUnready.setBounds(70,400,150,320);
        this.playerUnready.setOpaque(false);
        this.panel.add(this.playerUnready);

        // 设置布局和添加组件
        this.panel.setLayout(new BorderLayout());
        this.panel.setBounds(0, 0, this.width, this.height);

        this.btnGroup.add(this.btnReady);
        this.btnGroup.add(this.btnQuit);
        this.btnGroup.setOpaque(false);

        this.panel.add(this.bg, BorderLayout.CENTER);
        this.panel.add(this.btnGroup, BorderLayout.SOUTH);

        this.btnReady.setContentAreaFilled(false);
        this.btnReady.setBorderPainted(false);
        this.btnQuit.setContentAreaFilled(false);
        this.btnQuit.setBorderPainted(false);

        //设置页面布局
        this.frame.add(this.panel);
        this.frame.setBounds(this.screenWidth/4, this.screenHeight/4, this.x, this.y);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                player.setState("quit");
            }
        });

        // 添加按钮事件监听器
        this.btnReady.addActionListener(e -> {
            if (!this.isReady) {
                player.setState("ready");
                this.isReady = true;
                this.btnReady.setIcon(createScaledIcon("/image/unready.png", 100, 50));
                //this.playerUnready.setIcon(createScaledIcon("/image/GG_ready1.png", 120, 160));
                this.playerUnready.setIcon(new ImageIcon(getClass().getResource("/image/me.png")));
            } else {
                player.setState("unready");
                this.isReady = false;
                this.btnReady.setIcon(createScaledIcon("/image/ready.png", 100, 50));
                //this.playerUnready.setIcon(createScaledIcon("/image/GG.png", 120, 160));
                this.playerUnready.setIcon(new ImageIcon(getClass().getResource("/image/meS.png")));
            }
        });

        this.btnQuit.addActionListener(e -> player.setState("quit"));
    }

    private ImageIcon createScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public void close() {
        this.frame.setVisible(false);
    }

    public void refresh(String s) {
        if (s.charAt(0) == '0') {
            this.player1.setIcon(new ImageIcon(getClass().getResource("/image/defaultHeader.png")));
        } else if (s.charAt(0) == '1') {
            this.player1.setIcon(new ImageIcon(getClass().getResource("/image/leftShadow.png")));
        } else if (s.charAt(0) == '2') {
            this.player1.setIcon(new ImageIcon(getClass().getResource("/image/left.png")));
        }
        if (s.charAt(1) == '0') {
            this.player2.setIcon(new ImageIcon(getClass().getResource("/image/defaultHeader.png")));
        } else if (s.charAt(1) == '1') {
            this.player2.setIcon(new ImageIcon(getClass().getResource("/image/rightShadow.png")));
        } else if (s.charAt(1) == '2') {
            this.player2.setIcon(new ImageIcon(getClass().getResource("/image/right.png")));
        }
    }
}