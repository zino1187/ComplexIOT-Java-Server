package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//��ȭ�� �ְ�ޱ� ���� ������
public class MessageThread extends Thread{
	ServerMain serverMain;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	boolean flag=true;
	
	
	public MessageThread(ServerMain serverMain, Socket socket) {
		this.serverMain=serverMain;
		this.socket=socket;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���ϴ°�
	public void send(String msg) {
		try {
			for(int i=0;i<serverMain.list.size();i++) {
				MessageThread messageThread=(MessageThread)serverMain.list.get(i);
				 
				messageThread.buffw.write(msg);
				messageThread.buffw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	//��°�
	public void listen() {
		try {
			while(flag){
				String msg=buffr.readLine();
				serverMain.area.append(msg+"\n");
				
				send(msg);//Ŭ���̾�Ʈ�� ������
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//run
	@Override
	public void run() {
		listen();	
	}
}






