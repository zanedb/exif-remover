package org.openssf.exif_remover;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Drivers {
	
	static String path;
	static JFrame frame = new JFrame();
	static JPanel panel = new JPanel();
	static File file;
	static String filefolder;
	static JLabel header = new JLabel("Select a file");
	
	public static void main(String[] args) {
		startGui();
	}
	
	public static void startGui() {
		JButton remove = new JButton("Remove EXIF");
		remove.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            removeEXIF();
	         }          
	    });
		JButton select_file = new JButton("Select File");
		select_file.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	            JFileChooser chooser = new JFileChooser();
	            if(filefolder == null) {
	            	chooser.setCurrentDirectory(new java.io.File("."));
	            } else {
	            	chooser.setCurrentDirectory(new java.io.File(filefolder));
	            }
	            chooser.setDialogTitle("");
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(false);
	            
	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	                path = chooser.getSelectedFile().toString();
	                file = chooser.getSelectedFile();
	                header.setText("Ready to remove EXIF!");
	            } else {
	                header.setText("No selection! Try again.");
	            }
	        }
	    });
		
		panel.setPreferredSize(new Dimension(400, 50));
		panel.add(header);
		panel.add(select_file);
		panel.add(remove);
		frame.setContentPane(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setTitle("EXIF Remover");
	}

	public static void removeEXIF() {
		if(path == null) {
			JOptionPane.showMessageDialog(null, "Uh-oh! You didn't make a selection!", "Oops!", JOptionPane.WARNING_MESSAGE);
		} else {
			String extension = "";
			int i = file.getName().lastIndexOf('.');
			if (i > 0) {
			    extension = file.getName().substring(i+1);
			    BufferedImage image;
				try {
					image = ImageIO.read(file);
					ImageIO.write(image, extension, new File(path));  
					success();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "An unexpected error occurred. Is this an image?", "Error!", JOptionPane.ERROR_MESSAGE);
				}  
			} else {
				JOptionPane.showMessageDialog(null, "This file doesn't have an extension. Try again.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void success() {
		header.setText("Removed EXIF.");
		path = null;
		filefolder = file.getPath().toString().replaceAll(file.getName().toString(), "");
		file = null;
	}
}
