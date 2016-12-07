import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TopicLabel extends JLabel{

	private int w, h;
	
	public TopicLabel(String topic, int w, int h){
		super(topic);
		this.w = w;
		this.h = h;
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setFont(GameUtils.TOPIC_FONT);
	}
	
	public void paintComponent(Graphics g){
		  super.paintComponent(g);
		  int x = (this.getWidth() - w) / 2;
		    int y = (this.getHeight() - h) / 2;
		  g.drawImage(GameUtils.resize(GameUtils.disabled, w, h).getImage(), x, y, this);
	  }
	
}
