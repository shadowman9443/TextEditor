package com.text.Editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FileBrowser extends JPanel implements ActionListener{
	JLabel label=new JLabel("File List");
	JTextField newfileTf=new JTextField(10);
	JButton newFile=new JButton("new file");
	JButton open=new JButton("open");
	ButtonGroup bg;
	File directory;
	public FileBrowser(String dir) {
		directory=new File(dir);
		directory.mkdir();
		JPanel fileList=new JPanel(new GridLayout(directory.listFiles().length+3,1));
		fileList.add(label);
		bg=new ButtonGroup();
		for(File file:directory.listFiles()){
			JRadioButton radio=new JRadioButton(file.getName());
			radio.setActionCommand(file.getName());
			bg.add(radio);
			fileList.add(radio);
		}
		JPanel newP=new JPanel();
		newP.add(newfileTf);
		newP.add(newFile);
		newFile.addActionListener(this);
		open.addActionListener(this);
		fileList.add(open);
		fileList.add(newP);
		add(fileList);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Login login=(Login) getParent();
		if(e.getSource()==open){
			login.add(new Editor(directory.getName()+"\\"+bg.getSelection().getActionCommand()));
			login.cl.show(login,"editor");
		}
		if(e.getSource()==newFile){
			String file=directory.getName()+"\\"+newfileTf.getText()+".txt";
			if(newfileTf.getText().length()>0 && !(new File(file).exists())){
				login.add(new Editor(file),"editor");
				login.cl.show(login,"editor");
			}
		}
	}

}
