import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class CatchTheBall extends JPanel implements ActionListener, KeyListener {
    private int basketX=220, ballX, ballY=0, ballSpeed=5, score=0;
    private final int basketY=500, basketWidth=100, basketHeight=20, ballSize=30;
    private boolean gameOver=false;
    private final Timer timer=new Timer(20,this);
    private final Random random=new Random();
    public CatchTheBall(){setPreferredSize(new Dimension(500,600));setBackground(Color.BLACK);addKeyListener(this);setFocusable(true);resetBall();timer.start();}
    private void resetBall(){ballX=random.nextInt(470);ballY=0;}
    protected void paintComponent(Graphics g){super.paintComponent(g);g.setColor(Color.RED);g.fillOval(ballX,ballY,ballSize,ballSize);g.setColor(Color.GREEN);g.fillRect(basketX,basketY,basketWidth,basketHeight);g.setColor(Color.WHITE);g.setFont(new Font("Arial",Font.BOLD,20));g.drawString("Score: "+score,20,30);if(gameOver){g.setFont(new Font("Arial",Font.BOLD,40));g.drawString("GAME OVER",120,270);g.setFont(new Font("Arial",Font.BOLD,20));g.drawString("Press ENTER to restart",145,320);}}
    public void actionPerformed(ActionEvent e){if(gameOver)return;ballY+=ballSpeed;if(ballY+ballSize>=basketY&&ballX+ballSize>=basketX&&ballX<=basketX+basketWidth){score++;if(score%5==0)ballSpeed++;resetBall();}if(ballY>getHeight()){gameOver=true;timer.stop();}repaint();}
    private void restartGame(){score=0;ballSpeed=5;basketX=220;gameOver=false;resetBall();timer.start();requestFocusInWindow();repaint();}
    public void keyPressed(KeyEvent e){if(e.getKeyCode()==KeyEvent.VK_LEFT)basketX=Math.max(0,basketX-20);else if(e.getKeyCode()==KeyEvent.VK_RIGHT)basketX=Math.min(getWidth()-basketWidth,basketX+20);else if(e.getKeyCode()==KeyEvent.VK_ENTER&&gameOver)restartGame();repaint();}
    public void keyReleased(KeyEvent e){} public void keyTyped(KeyEvent e){}
    public static void main(String[] args){JFrame f=new JFrame("Catch the Ball Game");CatchTheBall g=new CatchTheBall();f.add(g);f.pack();f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);f.setLocationRelativeTo(null);f.setResizable(false);f.setVisible(true);g.requestFocusInWindow();}
}
