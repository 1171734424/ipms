package com.ideassoft.core.serial;



import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ideassoft.core.constants.SystemConstants;

public class SerialCommunication {
	
	public String PortName;
	public CommPortIdentifier portId;
	public SerialPort serialPort;
	public OutputStream out;
	public InputStream in;
	
	public int portStatus = 1;
	
	public SerialCommunication(int portId) {
		PortName = "COM" + portId;
		init();
	}
	
	public int init() {
		portStatus = SystemConstants.PortStatus.PORT_NORMAL;

		try {
			portId = CommPortIdentifier.getPortIdentifier(PortName);
			serialPort = (SerialPort) portId.open("SerialComm", 2000);
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, 
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			in = serialPort.getInputStream();
			out = serialPort.getOutputStream();
		} catch (PortInUseException e) {
			if (!portId.getCurrentOwner().equals("Serial_Communication")) {
				//port in use
				portStatus = SystemConstants.PortStatus.PORT_INUSE;
			}
		} catch (IOException e) {
			//port no available
			portStatus = SystemConstants.PortStatus.PORT_INAVAILABLE;
		} catch (UnsupportedCommOperationException e) {
			//port unsupported
			portStatus = SystemConstants.PortStatus.PORT_UNSUPPORTED;
		} catch (NoSuchPortException e) {
			//port not exists
			portId = null; 
			portStatus = SystemConstants.PortStatus.PORT_NOTEXISTS;
		} 

		return portStatus;
	}

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
			case SerialPortEvent.BI:
			case SerialPortEvent.OE:
			case SerialPortEvent.FE:
			case SerialPortEvent.PE:
			case SerialPortEvent.CD:
			case SerialPortEvent.CTS:
			case SerialPortEvent.DSR:
			case SerialPortEvent.RI:
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				break;
			case SerialPortEvent.DATA_AVAILABLE:
				byte[] readBuffer = new byte[20];
				try {
					while (in.available() > 0) {
						in.read(readBuffer);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
	}
	
	public String readPort() {
		StringBuilder result = new StringBuilder();
		int c;
		
		try {
			if (in != null) {
				Character d;
				while (in.available() > 0) {
					c = in.read();
					d = new Character((char) c);
					result.append(d.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public void writePort(String msg) {
		try {
			if (out != null) {
				for (int i = 0; i < msg.length(); i++)
					out.write(msg.charAt(i));
			}
		} catch (IOException e) {
			return;
		}
	}

	public void closePort() {
		serialPort.close();
		serialPort = null;
		
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
