package ru.luka.sendtozpl.model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


//Сделать чтобы если не отправлять параметров в значение пути к файлу указывается директория запуска

public class ReadFiles {
		
	private File fileProfileData;
	//private File fileConfig = new File("");
	private Map <String, String> mapConfig = new LinkedHashMap <String, String>();
	
	private List<Profile> ProfileListData;
	private List<MyLabel>  MyLabelListData = new ArrayList<MyLabel>();
	private List<MyColor> myColorListData = new ArrayList<MyColor>();
	private List<MyLength> myLengthListData = new ArrayList<MyLength>();
	private List<String> typeFile = new ArrayList<String>();
	private List<String> fullNameFile = new ArrayList<String>();
	
	private Map <String, String> mapProfile = new LinkedHashMap<String,String>();
	
	
	// Конструктор записывающий использующий полученную строку 
	//как абсолютный путь к файлу
	
	// Конструктор по умолчаню используется для чтения конфига(должен лежать в папке запуска программы)
	public ReadFiles() {
		
	}
	
	
	// помощник, построчно читает файл.
	private String[] readLines(String filename) throws IOException {
	      FileReader fileReader = new FileReader(filename);
	      BufferedReader bufferedReader = new BufferedReader(fileReader);
	      List<String> lines = new ArrayList<String>();
	      String line = null;
	      while ((line = bufferedReader.readLine()) != null) {
	          lines.add(line);
	      }
	      
	      bufferedReader.close();
	      return lines.toArray(new String[lines.size()]);
	}
	
	
	// download config from MyApp.conf
	private void loadMapConfig() throws IOException {
		 try { 
			//found current directory start and read MyApp.conf in this
			Path currentRelativePath = Paths.get("");
			String path = currentRelativePath.toAbsolutePath().toString();
			//System.out.println("Current relative path is: " + path+"\\MyApp.conf");
			String[] lines = readLines(path+"\\MyApp.conf");
			//System.out.println(lines);
		 	for (String line : lines) {

		 		String[] valeu = line.split(" = ");
		 		mapConfig.put(valeu[0], valeu[1]);
		 	}
		 } catch (Exception e) {
			 
		 }
		 
			
	}
	
	
	

	private void loadProfileDataFromFile(String str) {
		File targetFile = new File(str);
		try {
			JAXBContext context = JAXBContext
					.newInstance(ProfileListWrapper.class);
		    Unmarshaller um = context.createUnmarshaller();

		    // Reading XML from the file and unmarshalling.
		    ProfileListWrapper wrapper = (ProfileListWrapper) um.unmarshal(targetFile);
		      
		    ProfileListData = wrapper.getProfile();

		    // Save the file path to the registry.
		    //  setPersonsFilePath(file);

		    } catch (Exception e) { // catches ANY exception
		        //Dialogs.create()
		       //         .title("Error")
		         //       .masthead("Could not load data from file:\n" + file.getPath())
		         //       .showException(e);
		    }
	} 
	
	
	private void loadMyLabelDataFromFile(String str)  {
		File targetFile = new File(str);
		/*
		MyLabelListData.add(new MyLabel());
		MyLabelListData.add(new MyLabel());
		MyLabelListData.add(new MyLabel());
		MyLabelListData.add(new MyLabel());
		
		for (MyLabel ml : MyLabelListData) {
			
			ml.setMap(new HashMap<String, String>());
			ml.getMap().put("k 1", "value 1");
			
			ml.gettMap().put("k 2", "value 2");
		}
		
		    try {
		        JAXBContext context = JAXBContext
		                .newInstance(MyLabelListWrapper.class);
		        Marshaller m = context.createMarshaller();
		        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		        // Wrapping our person data.
		        MyLabelListWrapper wrapper = new MyLabelListWrapper();
		        wrapper.setMyLabels(MyLabelListData);

		        // Marshalling and saving XML to the file.
		        m.marshal(wrapper, fileMyLabels);

		        // Save the file path to the registry.
		       // setPersonFilePath(fileMyLabels);
		    } catch (Exception e) { // catches ANY exception
		       System.out.println(e);
		    }

		*/
		/*
		JAXBContext jc = JAXBContext.newInstance(TestMyLabel.class);
		Strine1 = "value 1";
		String strValue2 = "value 2";
		
		TestMyLabel tml = new TestMyLabel();
		tml.gettestMap().put("key1 ", strValue1);
		tml.gettestMap().put("key 2", strValue2);
		
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
		marshaller.marshal(tml, System.out);
		*/
		
		/*
		try {
			JAXBContext jc = JAXBContext.newInstance(TestMyLabel.class);
			
			Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
			//TestMyLabel tml = (TestMyLabel) jaxbUnmarshaller.unmarshal(fileMyLabels);
			//MyLabelListData = tml.gettestMap();
			 MyLabelListWrapper wrapper = (MyLabelListWrapper) jaxbUnmarshaller.unmarshal(fileMyLabels);
		      
			 MyLabelListData = wrapper.getMyLabels();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		*/
		
		
		try {
			 
		JAXBContext context = JAXBContext
				.newInstance(MyLabelListWrapper.class);
	    Unmarshaller um = context.createUnmarshaller();

	    // Reading XML from the file and unmarshalling.
	  
	    MyLabelListWrapper wrapper = (MyLabelListWrapper) um.unmarshal(targetFile);
	    //MyLabelListData.clear();
	    MyLabelListData.addAll(wrapper.getMyLabels());
	 
		} catch (Exception e) {
			System.out.println(e.toString());
		}
			
	}
	
	private void loadMyColorDataFromFile(String str) {
		
		File targetFile = new File(str);
		
		try {
		
			JAXBContext context = JAXBContext
					.newInstance(MyColorWrapper.class);
		    Unmarshaller um = context.createUnmarshaller();
	
		    // Reading XML from the file and unmarshalling.
		    
		   
		   
			MyColorWrapper wrapper = (MyColorWrapper) um.unmarshal(targetFile);
		
			
		    //MyLabelListData.clear();
			myColorListData.addAll(wrapper.getMyColors());
		  
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			System.out.println(e.toString()+ errors.toString());
		}
		
		
		
		
	}
	
	private void loadMyLengthDataFromFile(String str) {
		File targetFile = new File(str);
		try {
			JAXBContext context = JAXBContext
					.newInstance(MyLengthWrapper.class);
		    Unmarshaller um = context.createUnmarshaller();

		    // Reading XML from the file and unmarshalling.
		   
		    MyLengthWrapper wrapper = (MyLengthWrapper) um.unmarshal(targetFile);
		    
		    //myLengthListData.clear();
		    myLengthListData.addAll(wrapper.getMyLength());
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public List<MyLength> getMyLength(String str) {
		loadMyLengthDataFromFile(str);
		return myLengthListData;
	}
	public List<MyLabel> getMyLabel (String str)  {
		loadMyLabelDataFromFile(str);
		return MyLabelListData;
	}
	
	  
	public List<Profile> getProfileListData (String str) {
		loadProfileDataFromFile(str);
		return ProfileListData;
	}
	
	public List<MyColor> getMyColor(String str) {
		loadMyColorDataFromFile(str);
		
		return myColorListData;
	}
	public Map<String, String> getMapconfig() throws IOException {
		loadMapConfig();
		return mapConfig;
	}  	
	  

	
}
