package ru.vinogorova;

import java.awt.*;
import javax.sound.midi.*;
import javax.swing.*;

public class MiniMusicApp {
	
	static JFrame frame = new JFrame("My first music video");
	static MyDrawPanel m1;
	
	public static void main(String[] args) throws MidiUnavailableException {
		MiniMusicApp  musicApp = new MiniMusicApp();
		musicApp.play();
	}
	
	public void setUpGui() {
		m1 = new MyDrawPanel();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(m1);
		frame.setBounds(30, 30, 300, 300);
		frame.setVisible(true);
	}
	
	
	public void play() {
		setUpGui();
		
		try {
			Sequencer player = MidiSystem.getSequencer();
			player.open();
			
			player.addControllerEventListener(m1, new int[] {128});
			
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track t = seq.createTrack();
		
			//t.add(createEvent(192, 1, 102, 0, 1));
			
			int r = 0;
			for (int i = 5; i < 60; i+= 4) {
				r = (int)((Math.random()*50) + 1);
				t.add(createEvent(144, 1, r, 100, i));
				t.add(createEvent(176, 1, 128, 0, i));
				t.add(createEvent(128, 1, r, 80, i+2));	
			}
			
			player.setSequence(seq);
			player.start();
			player.setTempoInBPM(220);
			
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			System.out.println("Неудача");
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	
	public MidiEvent createEvent (int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
		ShortMessage a = new ShortMessage();
		a.setMessage(comd, chan, one, two);
		event = new MidiEvent(a, tick);
		}
		catch (Exception e){}
		return event;
	}

	
	class MyDrawPanel extends JPanel implements ControllerEventListener {
		
		boolean msg = false;
		
		@Override
		public void controlChange(ShortMessage event) {
			System.out.println("la");
			msg = true;
			repaint();
		}
		
		public void paintCompomemt(Graphics g) {
			if(msg) {
				
				Graphics2D g2d = (Graphics2D)g;
				g.setColor(Color.BLUE);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				
				int height = (int)((Math.random()*120)+10);
				int width = (int)((Math.random()*120)+10);
				int x = (int) ((Math.random()*40) + 10);
				int y = (int) ((Math.random()*40) + 10);
				g2d.fillRect(x, y, width, height);
				msg = false;				
			}
		}
		
		private Color randomColor() {
			int red = (int) (Math.random()*250);
			int blue = (int) (Math.random()*250);
			int green = (int) (Math.random()*250);
			
			Color color = new Color(red, green, blue);
			return color;
		}
		
	}

}
