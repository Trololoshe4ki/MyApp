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
	//Обяъявление переменных
	//List parametrs Labels
	
	//список с классом содержащим карты с цветами профилей
	private List<MyColor> listMyColors;
	private List<MyLength> listMyLength;
	// Список с типами этикеток
	private List<MyLabel> listLabels;
	
	//Карта значений выгруженная из файла конфиг.
	private Map<String, String> mapConfigRun;
	
	//Список длинн которые будут предложены для выбора
	private ObservableList <Integer> length = FXCollections.observableArrayList();

	//Список цветов которые будут предложены для выбора
	private ObservableList <String> color = FXCollections.observableArrayList();
	
	//Список типов этикеток которые будут предложены для выбора
	private ObservableList <String> type = FXCollections.observableArrayList();

	// Список параметров печати для каждого типа этикетки создан отдельный 
	//класс MyLabel на основе .xml файла 
	private MyLabel labelRun;
	
	//Полный список загруженных профилей 
	private ObservableList <Profile> profileData = FXCollections.observableArrayList();
	
	// Картра значений отправляемых на принтер через класс SendToPrint
	private Map <String, String> mapSendToZebra;
	
	
	
	// переменные для отображение кнопок, список и прочего все которые
	//помиечены @FXML доступны из fxml файда
	
	//Поле ввода для фильтрации
	@FXML
	private TextField filterField;
	
	// Таблица загруженых профилей
	@FXML
	private TableView <Profile> profileTable;
	
	//Левый столбец таблицы, Тип профиля
	@FXML
	private TableColumn <Profile, String> typeColumn;
	
	// Правый столбец профиля, Полное имя прфоиля
	@FXML
	private TableColumn <Profile, String> fullNameColumn;
	
	//Имя которое будет показано при выборе профиля.
	@FXML
	private Label showName;
	
	// Выпадающий бокс выбора типа этикетки.
	@FXML
	private ChoiceBox<String> choiceBoxLabelType =
			new ChoiceBox <String>();

	//Выпадающий бокс выбора длинны			
	@FXML
	private ChoiceBox<Integer> choiceBoxLengthData = 
			new ChoiceBox <Integer>();
	
	//Выпадающий бокс выбора цвета
	@FXML
	private ChoiceBox <String> choiceBoxColorData =
			new	ChoiceBox <String>();
	
	//Кнопка выбор типа профиля (надо переименовать) Кнопка загрузки
	//профилей выбранного типа(Грайс лука)
	
	
	private Map<String, String> colorRun;
	private Map<Integer, String> lengthRun;
	//метод вызывающийся при выборе типа этикеток (Luka | Grais)
	private void setChoiceType(String str) {
		profileData.clear();
		ReadFiles rf = new ReadFiles();
		System.out.println(str);
		profileData.addAll(rf.getProfileListData(mapConfigRun.get(str)));
		ReadFiles rfLabel = new ReadFiles();
		System.out.println(mapConfigRun.get("Labels"));
		listLabels = rfLabel.getMyLabel(mapConfigRun.get("Labels"));
		
		// поиск по имени этикетки
		try {
			
			for (MyLabel ml : listLabels) {
				//проверка наличия ключей 
			 	if (ml.getMap().containsKey("name") & ml.getMap().containsKey("dpi")) {
					if (ml.getMap().get("name".toLowerCase()).equalsIgnoreCase(str)) {
						
						if(ml.getMap().get("dpi".toLowerCase()).equalsIgnoreCase(mapConfigRun.get("dpiPrinter"))) {
						//Если есть совпадения то выход.
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
			messages.setHeaderText("Не найдено значений для ключей \"name\" или \"dpi\" \n" + errors.toString());
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
		
	//  Проверка выбора пораметров этикеток работет Но надо сделать загрузку из файлов, поэтому использую эту кнопку.
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
	
	// ЗАгрузка конфига и запись в карту активного конфига  переменные. 
	//Не привязана к кнопке
	
	// 
	@FXML 
	private void handleDownloadConfigure(ActionEvent event) {
		
		ReadFiles rf = new ReadFiles();
		try {
			mapConfigRun =rf.getMapconfig();
		
			
		} catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Application send about Error");
			alert.setHeaderText("Не удалось загрзуить конфиг");
			alert.showAndWait();
		}
	}
	
	//Кнопка применить
	@FXML
	private void handleButtonPrimenit (ActionEvent event) throws InterruptedException {
		Alert messages = new Alert(AlertType.INFORMATION);
			messages.setTitle("Jdite");
			messages.setHeaderText("Пытаюсь отпаврить задание на принтер");
			messages.show();
		System.out.println(choiceBoxLengthData.getValue());
	}
	
	//кнопка Сделать заебись
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
			
			//создаёт класс который отправляет на принетер этикетку
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
		messages.masthead("Пытаюсь отпаврить задание на принтер");
		messages.showInformation();
		*/
		
		
	//	Alert messages = new Alert(AlertType.INFORMATION);
	//	messages.setTitle("Jdite");
	//	messages.setHeaderText("Пытаюсь отпаврить задание на принтер");
	//	messages.show();
		
		
	}
	
	//инициализация контроллера 
	@FXML
	private void initialize() throws IOException {
		
		
		
		ReadFiles rf = new ReadFiles();
		//Чтение и запись начального конфига.
		mapConfigRun =rf.getMapconfig();
		System.out.println(rf);
		System.out.println(mapConfigRun);
		//Запись значений в боксы выбора из коллекций для показа пользователям 
		choiceBoxLengthData.setItems(getLengths());
		choiceBoxColorData.setItems(getColors());
		choiceBoxLabelType.setItems(getTypes());
		
		
		//Прослушивание выбора типа этикетки
		choiceBoxLabelType.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setChoiceType(newValue));
		//Запись значений в столбцы показа в таблице
		choiceBoxColorData.getSelectionModel().selectedItemProperty().addListener(
				(obsevable, oldValue, newValue) -> setLabelColor(newValue));
		choiceBoxLengthData.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setLabelLength(newValue));
		
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
		fullNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
		
		FilteredList<Profile> filteredData = new FilteredList<>(profileData, p -> true);

		//Фильтр 
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
		
		//Слушатель выбранного профиля
		profileTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setLabel(newValue));
		// Если не выбран никакой профиль
		setLabel(new Profile("test", "Выбери этикетку"));
	//}
	
	//public void setHelloWorld(HelloWorld helloWorld) {
	//	this.helloWorld = helloWorld;
		
	//	profileTable.setItems(helloWorld.getProfileData());
	}
	
	
	//Set value for type Labels
	//Заполнение коллекции type списком типов этикеток значениями из файла.
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
	
	//заполнение коллецкции цветов, и возврат её ( надо сделать из xml файла 
	private  ObservableList <String> getColors () {
	
		color.add(new String("91"));
		
		
		return color;
	}
	//заполнение коллецкции длинн ( надо сделать из xml файла 
	private  ObservableList <Integer> getLengths () {
	
	
		length.add(900);
		
		
		return length;
	}
	
	//set label color
	//изменение цвета в зависиомсти от выбранного 
	
	private void setLabelColor (String str) {
		showColor.textProperty().bind(new SimpleStringProperty (colorRun.get(str)));
	}
	
	//set label length
	private void setLabelLength(int choiceLength) {
		showLength.textProperty().bind(new SimpleStringProperty (lengthRun.get(choiceLength)));
	
	}
	
	//set label type
	// изменение названия профиля в зависимости от выбранного профиля
	public void setLabel (Profile profile) {
		if (profile != null) {
			showName.textProperty().bind(profile.fullNameProperty());
		} else {
			System.out.println("ololo");
			setLabel(new Profile("test", "Выбери этикетку"));
		}
		
	}
	//Set List MyLabesl
	//Заполнение списка с параметрами этикеток
	
/*	private void setMyLabelsList() {
	
		ReadFiles rf = new ReadFiles("C:/1/MyLabels.xml");
		MyLabelList = (rf.getMyLabel());
	}
*/	
	
}
