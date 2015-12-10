package com.text.Editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JPanel implements ActionListener{
	JLabel userL=new JLabel("choose a user name:");
	JTextField userTF=new JTextField();
	JLabel passL=new JLabel("Password");
	JPasswordField passTF=new JPasswordField();
	JLabel passLC=new JLabel("confirm password");
	JPasswordField passC=new JPasswordField();
	JButton register=new JButton("register");
	JButton back=new JButton("back");
	public Register() {
		JPanel loginp=new JPanel();
		loginp.setLayout(new GridLayout(4,2));
		loginp.add(userL);
		loginp.add(userTF);
		loginp.add(passL);
		loginp.add(passTF);
		loginp.add(passTF);
		loginp.add(passLC);
		loginp.add(passC);
		loginp.add(register);
		loginp.add(back);
		register.addActionListener(this);
		back.addActionListener(this);
		add(loginp);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==register && passTF.getPassword().length>0 && userTF.getText().length()>0){
			String pass=new String(passTF.getPassword());
			String confirm=new String(passC.getPassword());
			if(pass.equals(confirm)){
				try {
					BufferedReader input=new BufferedReader(new FileReader("password.txt"));
					String line=input.readLine();
					while(line !=null){
						StringTokenizer st=new StringTokenizer(line);
						if(userTF.getText().equals(st.nextToken())){
							System.out.println("user already exist");
							return;
						}
						line=input.readLine();
					}
					input.close();
					MessageDigest md=MessageDigest.getInstance("SHA-256");
					md.update(pass.getBytes());
					byte bytedata[]=md.digest();
					StringBuffer sb=new StringBuffer();
					for(int i=0;i<bytedata.length;i++){
						sb.append(Integer.toString((bytedata[i] & 0xFF)+0x100,16).substring(1));
					}
					BufferedWriter output=new BufferedWriter(new FileWriter("password.txt"));
					output.write(userTF.getText()+" "+sb.toString()+"\n");
					output.close();
					Login login=(Login) getParent();
					login.cl.show(login,"login");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource()==back){
			Login login=(Login) getParent();
			login.cl.show(login,"login");
		}
		
	}
}
