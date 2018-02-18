package ru.vinogorova;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sound.midi.*;
import javax.swing.*;


public class UserInterface  {
	
	ArrayList<JCheckBox> checkBoxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame frame;
	JPanel mainPanel;
	JPanel background;
	
	String[] instrumentNames = {
			"Bass Drum", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", 
			"Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi-Conga"
			};
	int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};
					  	

	public static void main(String[] args) {
		new UserInterface().go();
	}	
	
	public void go() {
		frame = new JFrame("Cyber BeatBox");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		checkBoxList = new ArrayList<JCheckBox>();
		
		addButtons();
		addLabels();
		addCheckBoxes();
		frame.getContentPane().add(background);
		setUpMidi();
		frame.setBounds(50, 50, 300, 300);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void addLabels() {
		Box nameBox = new Box(BoxLayout.Y_AXIS);	
		
		for (int i = 0; i < instrumentNames.length; i++) {
			nameBox.add(new JLabel(instrumentNames[i]));
		}
		background.add(BorderLayout.WEST, nameBox);
		}
	
	private void addButtons() {
		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		JButton startButton = new JButton("Start");
		JButton stopButton = new JButton("Stop");
		JButton upButton = new JButton("Tempo Up");
		JButton downButton = new JButton("Tempo Down");
		
		buttonBox.add(startButton);
		buttonBox.add(stopButton);
		buttonBox.add(upButton);
		buttonBox.add(downButton);
		
		startButton.addActionListener(new StartButtonListener());
		stopButton.addActionListener(new StopButtonListener());
		upButton.addActionListener(new UpButtonListener());
		downButton.addActionListener(new DownButtonListener());
		
		background.add(BorderLayout.EAST, buttonBox);
		}
	
	private void addCheckBoxes() {
		GridLayout grid = new GridLayout(16, 16);
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER, mainPanel);
				
		for(int i = 1; i <= 16*16; i++) {
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkBoxList.add(c);
			mainPanel.add(c);
		}
	}
	
	class StartButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			buildTrackAndStart();
		}
		
	}
	
	class StopButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			sequencer.stop();
			for(int i = 0; i < checkBoxList.size(); i++) {
				if (checkBoxList.get(i).isSelected()) {
					checkBoxList.get(i).setSelected(false);
				}
			}
		}
}

	class UpButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 1.03));
		}
		
	}
	
	class DownButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * .97));
		}
		
	}

	public void setUpMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buildTrackAndStart() {
		int[] trackList = null;
		try {
			sequence = new Sequence(Sequence.PPQ, 4);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		if (track != null) {
		sequence.deleteTrack(track);
		track = sequence.createTrack();
		}
		else {
			track = sequence.createTrack();
		}
		for (int i = 0; i < 16; i++) {
			trackList = new int[16];
			
			int key = instruments[i];
			
			for (int j = 0; j < 16; j++) {
				JCheckBox jc = (JCheckBox) checkBoxList.get(j + (16*i));
				if (jc.isSelected()) {
					trackList[j] = key;
				}else {
					trackList[j] = 0;
				}
			}
			makeTracks(trackList);
			track.add(makeEvent(176, 1, 127, 0, 16));
		}
		track.add(makeEvent(192, 9, 1, 0, 15));
		try {
		sequencer.setSequence(sequence);
		sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
		sequencer.start();
		sequencer.setTempoInBPM(120);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void makeTracks(int[] list) {
		for (int i = 0; i < 16; i++) {
			int key = list[i];
			if(key != 0) {
				track.add(makeEvent(144, 9, key, 100, i));
				track.add(makeEvent(128, 9, key, 100, i+1));
			}
		}
	}

	private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage msg = new ShortMessage();
			msg.setMessage(comd, chan, one, two);
			event = new MidiEvent(msg, tick);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}


}









