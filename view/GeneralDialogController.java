package ru.luka.sendtozpl.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ru.luka.sendtozpl.model.MyColor;
import ru.luka.sendtozpl.model.Profile;
import ru.luka.sendtozpl.model.SendToPrint;
import ru.luka.sendtozpl.model.ReadFiles;
import ru.luka.sendtozpl.model.MyLabel;
import ru.luka.sendtozpl.model.MyLength;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class GeneralDialogController {
	
	//Declaring Variables
	//����������� ����������
	//List parametrs Labels
	
	//������ � ������� ���������� ����� � ������� ��������
	private List<MyColor> listMyColors;
	private List<MyLength> listMyLength;
	// ������ � ������ ��������
	private List<MyLabel> listLabels;
	
	//����� �������� ����������� �� ����� ������.
	private Map<String, String> mapConfigRun;
	
	//������ ����� ������� ����� ���������� ��� ������
	private ObservableList <Integer> length = FXCollections.observableArrayList();

	//������ ������ ������� ����� ���������� ��� ������
	private ObservableList <String> color = FXCollections.observableArrayList();
	
	//������ ����� �������� ������� ����� ���������� ��� ������
	private ObservableList <String> type = FXCollections.observableArrayList();

	// ������ ���������� ������ ��� ������� ���� �������� ������ ��������� 
	//����� MyLabel �� ������ .xml ����� 
	private MyLabel labelRun;
	
	//������ ������ ����������� �������� 
	private ObservableList <Profile> profileData = FXCollections.observableArrayList();
	
	// ������ �������� ������������ �� ������� ����� ����� SendToPrint
	private Map <String, String> mapSendToZebra;
	
	
	
	// ���������� ��� ����������� ������, ������ � ������� ��� �������
	//��������� @FXML �������� �� fxml �����
	
	//���� ����� ��� ����������
	@FXML
	private TextField filterField;
	
	// ������� ���������� ��������
	@FXML
	private TableView <Profile> profileTable;
	
	//����� ������� �������, ��� �������
	@FXML
	private TableColumn <Profile, String> typeColumn;
	
	// ������ ������� �������, ������ ��� �������
	@FXML
	private TableColumn <Profile, String> fullNameColumn;
	
	//��� ������� ����� �������� ��� ������ �������.
	@FXML
	private Label showName;
	
	// ���������� ���� ������ ���� ��������.
	@FXML
	private ChoiceBox<String> choiceBoxLabelType =
			new ChoiceBox <String>();

	//���������� ���� ������ ������			
	@FXML
	private ChoiceBox<Integer> choiceBoxLengthData = 
			new ChoiceBox <Integer>();
	
	//���������� ���� ������ �����
	@FXML
	private ChoiceBox <String> choiceBoxColorData =
			new	ChoiceBox <String>();
	
	//������ ����� ���� ������� (���� �������������) ������ ��������
	//�������� ���������� ����(����� ����)
	
	
	private Map<String, String> colorRun;
	private Map<Integer, String> lengthRun;
	//����� ������������ ��� ������ ���� �������� (Luka | Grais)
	private void setChoiceType(String str) {
		profileData.clear();
		ReadFiles rf = new ReadFiles();
		System.out.println(str);
		profileData.addAll(rf.getProfileListData(mapConfigRun.get(str)));
		ReadFiles rfLabel = new ReadFiles();
		System.out.println(mapConfigRun.get("Labels"));
		listLabels = rfLabel.getMyLabel(mapConfigRun.get("Labels"));
		
		// ����� �� ����� ��������
		try {
			
			for (MyLabel ml : listLabels) {
				//�������� ������� ������ 
			 	if (ml.getMap().containsKey("name") & ml.getMap().containsKey("dpi")) {
					if (ml.getMap().get("name".toLowerCase()).equalsIgnoreCase(str)) {
						
						if(ml.getMap().get("dpi".toLowerCase()).equalsIgnoreCase(mapConfigRun.get("dpiPrinter"))) {
						//���� ���� ���������� �� �����.
							labelRun = ml;
							System.out.println(labelRun.getMap());
							break;
						}
					}
				} 
			}
			ReadFiles rfss = new ReadFiles();
			listMyColors = rfss.getMyColor(mapConfigRun.get("Colors"));
			for (MyColor mc : listMyColors) {
				
				if (mc.getTypeLabel().equals(str)) {
					color.clear();
					colorRun = mc.getMap();
					for (Map.Entry <String, String> entry: mc.getMap().entrySet()) {
						
						color.add(entry.getKey());
					
					}
					choiceBoxColorData.setItems(getColors());
				}
			}
			
			
			listMyLength = rf.getMyLength(mapConfigRun.get("Lengths"));
			for (MyLength ml :listMyLength) {
				if (ml.getTypeLabel().equals(str)) {
					length.clear();
					lengthRun = ml.getMap();
					for (Map.Entry<Integer, String> entry : ml.getMap().entrySet()) {
						
						length.add(entry.getKey());
					
						
					}
					
				}
			}
			
			
			
					
		
			
		} catch (NullPointerException e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			Alert messages = new Alert(AlertType.ERROR);
			messages.setTitle("Error");
			messages.setHeaderText("�� ������� �������� ��� ������ \"name\" ��� \"dpi\" \n" + errors.toString());
			messages.show();
		
		}
	}
	
	@FXML
	private Label showColor;
	
	@FXML
	private Label showLength;
	
	@FXML
	private void handleChoiceType (ActionEvent event) throws Exception {
		
		
		Alert messages = new Alert(AlertType.INFORMATION);
		messages.setTitle("Jdite");
		messages.setHeaderText(labelRun.toString()+labelRun.getMap().get("dpi"));
		messages.show();
	//System.out.println(choiceBoxLengthData.getValue());
		/*
		profileData.clear();
		ReadFiles rf = new ReadFiles(mapConfigRun.get(choiceBoxLabelType.getValue()));
		profileData.addAll(rf.getProfileListData());
		
		Map <String, String> testMapSettings;
		MyLabelList =  new ReadFiles("C:\\1\\java\\test.xml").getMyLabel();
		System.out.println("110" + MyLabelList);
		for (MyLabel ml: MyLabelList) 
			System.out.println("111" + ml.gettestMap());
		/*profileData.clear();
	
		*/
		
	//  �������� ������ ���������� �������� ������� �� ���� ������� �������� �� ������, ������� ��������� ��� ������.
	/*	setMyLabelsList();
		for (MyLabel mylabel : MyLabelList) {
			System.out.println(mylabel);
			//chek Tyepe labels
			if (choiceBoxLabelType.getValue().equals(mylabel.getTypeLabel())) {
				//chek dpiPrinter
				if(mapConfigRun.get("dpiPrinter").equals(String.valueOf(mylabel.getDpiLabel()))) {
				System.out.println("matches is found");
				MyLabel matchesLabel = mylabel;
				System.out.println("123" + matchesLabel);
				}
			}
		}
		*/
		
		
	}
	
	// �������� ������� � ������ � ����� ��������� �������  ����������. 
	//�� ��������� � ������
	
	// 
	@FXML 
	private void handleDownloadConfigure(ActionEvent event) {
		
		ReadFiles rf = new ReadFiles();
		try {
			mapConfigRun =rf.getMapconfig();
		
			
		} catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Application send about Error");
			alert.setHeaderText("�� ������� ��������� ������");
			alert.showAndWait();
		}
	}
	
	//������ ���������
	@FXML
	private void handleButtonPrimenit (ActionEvent event) throws InterruptedException {
		Alert messages = new Alert(AlertType.INFORMATION);
			messages.setTitle("Jdite");
			messages.setHeaderText("������� ��������� ������� �� �������");
			messages.show();
		System.out.println(choiceBoxLengthData.getValue());
	}
	
	//������ ������� �������
	@FXML
	private void handleButtonGoPrint(ActionEvent event) {
		
		//generate Map to Zebra key: name, color, length....
		mapSendToZebra = new HashMap <String, String>();
		//mapSendToZebra.put("length",choiceBoxLengthData.getValue());
		mapSendToZebra.put("color",choiceBoxColorData.getValue());
		mapSendToZebra.put("name",showName.getText());
		
		
		try {
			Map <String, String> mapToZPL;
		
			mapToZPL = labelRun.getMap();
			mapToZPL.put("name", showName.getText());
			mapToZPL.put("color", showColor.getText());
			mapToZPL.put("length", showLength.getText());
		//	mapToPrint.put("color", showColor.getText());
			System.out.println("to Ptint map" + mapToZPL);
			
			//������ ����� ������� ���������� �� �������� ��������
			SendToPrint sp = new SendToPrint( mapToZPL);
			sp.getDoSendZPL();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Application send about Error");
			alert.setHeaderText("Slomal.... malodec....");
			alert.setContentText(e.toString());
		
		
			alert.showAndWait();
		}
		/*
		messages.create();
		messages.owner(stage);
		messages.title("Jdite");
		messages.masthead("������� ��������� ������� �� �������");
		messages.showInformation();
		*/
		
		
	//	Alert messages = new Alert(AlertType.INFORMATION);
	//	messages.setTitle("Jdite");
	//	messages.setHeaderText("������� ��������� ������� �� �������");
	//	messages.show();
		
		
	}
	
	//������������� ����������� 
	@FXML
	private void initialize() throws IOException {
		
		
		
		ReadFiles rf = new ReadFiles();
		//������ � ������ ���������� �������.
		mapConfigRun =rf.getMapconfig();
		System.out.println(rf);
		System.out.println(mapConfigRun);
		//������ �������� � ����� ������ �� ��������� ��� ������ ������������� 
		choiceBoxLengthData.setItems(getLengths());
		choiceBoxColorData.setItems(getColors());
		choiceBoxLabelType.setItems(getTypes());
		
		
		//������������� ������ ���� ��������
		choiceBoxLabelType.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setChoiceType(newValue));
		//������ �������� � ������� ������ � �������
		choiceBoxColorData.getSelectionModel().selectedItemProperty().addListener(
				(obsevable, oldValue, newValue) -> setLabelColor(newValue));
		choiceBoxLengthData.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setLabelLength(newValue));
		
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
		fullNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
		
		FilteredList<Profile> filteredData = new FilteredList<>(profileData, p -> true);

		//������ 
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(profile -> {
				if(newValue == null|| newValue.isEmpty()) {
					return true;
				}
			
				String lowerCaseFilter = newValue.toLowerCase();
				if(profile.getType().toLowerCase().contains(lowerCaseFilter)) {
					return true;
			//	} else if (profile.getFullName().toLowerCase().contains(lowerCaseFilter)) {
			//		return true;
				}
				return false;
			});
		});
		SortedList<Profile> sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(profileTable.comparatorProperty());
		
		profileTable.setItems(sortedData);
		
		//��������� ���������� �������
		profileTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setLabel(newValue));
		// ���� �� ������ ������� �������
		setLabel(new Profile("test", "������ ��������"));
	//}
	
	//public void setHelloWorld(HelloWorld helloWorld) {
	//	this.helloWorld = helloWorld;
		
	//	profileTable.setItems(helloWorld.getProfileData());
	}
	
	
	//Set value for type Labels
	//���������� ��������� type ������� ����� �������� ���������� �� �����.
	private  ObservableList <String> getTypes () {
	
		for (Map.Entry<String, String> entry: mapConfigRun.entrySet()) {
			//Download only if vale ending ".xml"
			if (entry.getValue().matches(".+\\.xml")) {
				
				
				type.add(entry.getKey());
			} else {
				//Nothing
			}
		}
		return type;
	}
	
	//���������� ���������� ������, � ������� � ( ���� ������� �� xml ����� 
	private  ObservableList <String> getColors () {
	
		color.add(new String("91"));
		
		
		return color;
	}
	//���������� ���������� ����� ( ���� ������� �� xml ����� 
	private  ObservableList <Integer> getLengths () {
	
	
		length.add(900);
		
		
		return length;
	}
	
	//set label color
	//��������� ����� � ����������� �� ���������� 
	
	private void setLabelColor (String str) {
		showColor.textProperty().bind(new SimpleStringProperty (colorRun.get(str)));
	}
	
	//set label length
	private void setLabelLength(int choiceLength) {
		showLength.textProperty().bind(new SimpleStringProperty (lengthRun.get(choiceLength)));
	
	}
	
	//set label type
	// ��������� �������� ������� � ����������� �� ���������� �������
	public void setLabel (Profile profile) {
		if (profile != null) {
			showName.textProperty().bind(profile.fullNameProperty());
		} else {
			System.out.println("ololo");
			setLabel(new Profile("test", "������ ��������"));
		}
		
	}
	//Set List MyLabesl
	//���������� ������ � ����������� ��������
	
/*	private void setMyLabelsList() {
	
		ReadFiles rf = new ReadFiles("C:/1/MyLabels.xml");
		MyLabelList = (rf.getMyLabel());
	}
*/	
	
}
