package com.example.demo.smswake;

import com.example.demo.smswake.model.CMPPException;
import com.example.demo.smswake.model.CMPPRequestPacket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * 建立和网关的socket连接
 * 
 * @author David Yin
 * @version 1.0 (2013-11-21 22:52:58)
 */
public class CMPPSocket {
	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory.getLog(CMPPSocket.class);

	/**
	 * 网关主机地址
	 */
	private String host;

	/**
	 * 监听端口
	 */
	private int port;

	/**
	 * 建立起的socket端
	 */
	private Socket socket;

	/**
	 * 输出流
	 */
	private OutputStream os;

	/**
	 * 输入流
	 */
	private DataInputStream is;

	/**
	 * 构造函数
	 * 
	 * @param host
	 *            网关主机地址
	 * @param port
	 *            监听端口
	 */
	public CMPPSocket(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * 得到输入流
	 * 
	 * @return DataInputStream
	 */
	public DataInputStream getInputStream() {
		return this.is;
	}

	/**
	 * 初始化socket连接,设定超时时间为30秒 <br>
	 * 使用cmpp协议各命令之前,必须调用此方法
	 * 
	 * @throws CMPPException
	 *             封装连接时抛出的UnknownHostException以及IOException
	 */
	public void initialSock() throws IOException {
		try {
			if (socket == null) {
				socket = new Socket(host, port);
			}
			//socket的读写是阻塞的，soTimeout是socket读写超时，而不是链接超时。
			//connection reset是由于在一个半关闭的connection上读取数据导致，client已经关闭但是server端没有关闭。
		    //如果超过超时值，将引发 java.net.SocketTimeoutException，虽然 Socket 仍旧有效。选项必须在进入阻塞操作前被启用才能生效。  
		    //超时值必须是 > 0 的数。超时值为 0 被解释为无穷大超时值。
			socket.setSoTimeout(30 * 1000);
			os = socket.getOutputStream();
			is = new DataInputStream(socket.getInputStream());
			//log.info("成功建立起和网关的socket连接");
		} catch (UnknownHostException e) {
			log.error("地址\"" + host + "\"未知" + e.toString());
			throw e;
		} catch (IOException e) {
			log.error("建立socket IO异常:" + e.toString());
			throw e;
		}

	}

	/**
	 * 关闭socket连接
	 * 
	 * @throws CMPPException
	 *             封装关闭连接时抛出的IOException
	 */
	public void closeSock() throws IOException {
		try {
			if (socket != null) {
				socket.close();
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			}
			socket = null;
			os = null;
			is = null;
			//log.info("socket连接关闭成功");
		} catch (IOException e) {
			log.error("socket关闭异常:" + e.toString());
			throw e;
		}
	}

	/**
	 * socket连接上发送请求包
	 * 
	 * @throws IOException
	 */
	void write(CMPPRequestPacket cmpppacket) throws IOException {
		byte[] packets = cmpppacket.getRequestPacket();
		os.write(packets);
		os.flush();
		//log.info(cmpppacket.getRequestBody().getClass().getName() + " 发送包体成功");
	}

	/**
	 * socket连接上读取输入流
	 * 
	 * @return 输入流的字节形式
	 * @throws IOException
	 */
	public byte[] read() throws CMPPException, IOException {
		// 包头
		byte[] head = new byte[12];
		byte[] packet = null;
		int reads = -1;
		// 应该这样读,先读12个字节,然后解析后得到包长,再读包长的长度
		// try {
		reads = is.read(head, 0, 12);
		// 没有读到的话
		if (reads == -1) {
			throw new CMPPException("读包头时输入流已空,没有读到数据!");
		}
		// 得到输入流的总长度，应该和is.available的一样
		byte[] length = new byte[4];
		System.arraycopy(head, 0, length, 0, 4);
		int packetlen = Common.bytes4ToInt(length);

		// 整个输入流的字节形式
		packet = new byte[packetlen];
		// 将包头独到整个输入流的字节形式中
		System.arraycopy(head, 0, packet, 0, 12);
		// 如果输入流还有包体
		if (packetlen - 12 != 0) {
			is.readFully(packet, 12, packetlen - 12);
		}
		//log.info("本次输入流读取完毕,totalLength=" + packetlen);
		return packet;
	}
}