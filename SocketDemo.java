package com.it.gui.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDemo {

	public static void main(String[] args) throws IOException {

		// 1.创建ServerSocket
		ServerSocket server = new ServerSocket(8088);

		System.out.println("服务器已经启动成功....");

		while (true) {
			// 2.接收客户端的连接
			Socket socket = server.accept();

			// 3.读取本地的test.html文件
			String path = "C:/Users/zhangxj/Desktop/test.js";
			File file = new File(path);
			System.out.println(file.exists());
			FileInputStream in = new FileInputStream(file);

			// 4.构建数据输出通道
			OutputStream out = socket.getOutputStream();

			// 5.发送数据
			byte[] buf = new byte[1024];
			//读取到有多少的1024字节长度的数组
			//把读到的内容放到len中
			int len = 0;
			//每次读取1024字节大小的内容放到len中
			while ((len = in.read(buf)) != -1) {
				System.out.println(len);
				for(int i=0;i<5;i++)
					System.out.print(buf[i] + " ");
				out.write(buf, 0, len);
			}

			// 6.关闭资源
			out.close();
			in.close();
			socket.close();
		}
	}
	/**
	 * 如何访问？启动当前程序，当前程序作为socket服务器端。
	 * 网络通信基础是通过ip和端口来发送和接收二进制数据，网络通讯和具体的开发语言无关。
	 * 故该服务器端用java语言开发，可用浏览器作为socket客户端
	 * 打开任浏览器，作为socket客户端，输入http://localhost:8088/即可
	 */

}
