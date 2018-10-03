package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerMain extends JFrame{

	JPanel panel;
	JTextField t_port;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	
	/*네트워크 관련*/
	ServerSocket serverSocket;
	Thread networkThread;
	
	/*접속자가 다수일수 있으므로...*/
	ArrayList list = new ArrayList<MessageThread>();
	
	public ServerMain() {
		panel = new JPanel();
		t_port = new JTextField("7777",15);
		bt  = new JButton("서버가동");
		panel.add(t_port);
		panel.add(bt);
		add(panel, BorderLayout.NORTH);
		area = new JTextArea();
		scroll = new JScrollPane(area);
		add(scroll);
		setVisible(true);
		setSize(300,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startServer();
			}
		});
	}
	
	public void startServer() {
		networkThread = new Thread() {
			public void run() {
				try {
					serverSocket = new ServerSocket(Integer.parseInt(t_port.getText()));
					
					area.append("서버소켓 생성\n");
					while(true) {
						Socket socket=serverSocket.accept();
						//서버 가동 중 로그에 남기기 
						area.append("접속자 발견...\n");
						MessageThread messageThread = new MessageThread(ServerMain.this , socket);
						messageThread.start(); //쓰레드 동작 시작~!!!
						list.add(messageThread);
						area.append("현재 총 "+list.size()+" 접속 중...\n");
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		networkThread.start();
	}
	
	public static void main(String[] args) {
		new ServerMain();

	}

}







