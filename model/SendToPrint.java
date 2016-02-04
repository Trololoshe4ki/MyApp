package ru.luka.sendtozpl.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;


public class SendToPrint {
	private Map<String, String> mapToZPL;
	
	
	public SendToPrint (Map <String,String> mapToZPL) {
		this.mapToZPL = mapToZPL;
	}
	
	private void doSendZPL () throws IOException {
		//Zamenit na okno messages    mapToZPL.get("")   leftPicture
		System.out.println("Gotowly");
		Socket s = new Socket ("10.13.0.25", 9100); 
			OutputStream out = s.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);
			String ZPLString = 
					"^XA"+
					"^FO"+mapToZPL.get("leftPicture")+","+ mapToZPL.get("topPicture")+"^IMR:test.png^FS"+
					"^FO"+ mapToZPL.get("leftLength")+","+ mapToZPL.get("topLength")+"^ADN,30,10^FD"+mapToZPL.get("length")+"^FS"+
					"^FO"+ mapToZPL.get("leftColor")+","+ mapToZPL.get("topColor")+"^ADN,30,10^FD"+mapToZPL.get("color")+"^FS"+
					"^FO"+ mapToZPL.get("leftFirstName")+","+ mapToZPL.get("topFirstName")+"^ADN,30,10^FD"+mapToZPL.get("name")+"^FS"+
					"^XZ";
			writer.println(ZPLString);
			writer.flush();
			System.out.println(ZPLString);
			s.close();
		
	}
	public void getDoSendZPL() throws IOException {
		doSendZPL();
	}
}

