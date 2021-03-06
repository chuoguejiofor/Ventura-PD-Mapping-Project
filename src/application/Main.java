//TODO Address Cleaner: Needs Testing
//TODO City Cleaner: May need to remove spacing on city names pending testing
//TODO A Readme summary of the project
//TODO Need to do an updated JAR

package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	// Hello Github!
	AddressCleanerStage acStage;
	CityInserterStage ciStage;
	CommunityCodeGetterStage ccgStage;
	APPSFormatterStage appsfStage;
	NarcFormatterStage narcStage;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("V.C. Data Automator");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(35, 0, 0, 0));
		grid.setPadding(new Insets(35, 25, 0, 25));
		grid.setStyle("-fx-background-color: BEIGE;");
		// grid.setGridLinesVisible(true);

		Text header = new Text("V.C. Data Automator");
		header.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));

		HBox col1 = new HBox();
		HBox col2 = new HBox();
		HBox row3 = new HBox();
		
		Button aCleaner = new Button();
		aCleaner.setText("Address Cleaner");
		aCleaner.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				acStage = new AddressCleanerStage();
			}

		});

		Button cInserter = new Button();
		cInserter.setText("City Inserter");
		cInserter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ciStage = new CityInserterStage();
			}

		});
		
		col1.getChildren().addAll(aCleaner, cInserter);
		col1.setSpacing(65.0);
		
		Button ccGetter = new Button();
		ccGetter.setText("Community Code Getter");
		ccGetter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ccgStage = new CommunityCodeGetterStage();
			}
			
		});
		
		Button aFormatter = new Button();
		aFormatter.setText("APPS Formatter");
		aFormatter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				appsfStage = new APPSFormatterStage();
			}
			
		});

		col2.getChildren().addAll(ccGetter, aFormatter);
		col2.setSpacing(22.0);
		
		Button narcFormatter = new Button();
		narcFormatter.setText("Narc Data Formatter");
		narcFormatter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				narcStage = new NarcFormatterStage();
			}
			
		});
		
		row3.getChildren().addAll(narcFormatter);
		
		ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
		Text signature = new Text("Created by Henry Bulmer and Chu Oguejiofor");
		signature.setFill(Color.LIGHTGRAY);
		
		grid.add(image,0, 0);
		grid.add(header, 1, 0);
		grid.setVgap(10);
		grid.add(col1, 1, 3);
		grid.add(col2, 1, 5);
		grid.add(row3, 1, 7);
		grid.add(signature, 1, 11);
		grid.setHalignment(cInserter, HPos.CENTER);
		grid.setValignment(cInserter, VPos.CENTER);
		primaryStage.setScene(new Scene(grid, 500, 350));
		
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public String formatList(ArrayList<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str + "\n");
		}
		return sb.toString();
	}

class AddressCleanerStage {
	Stage primaryStage;
	FileChooser fileChooser;
	File file;
	Button btn, btn2, btnSettings;

	public AddressCleanerStage() {
		primaryStage = new Stage();
		AddressCleaner cleaner = new AddressCleaner();
		cleaner.addStartList("#", "deleteW");
		cleaner.addFindList("/", "replaceW", "&");
		cleaner.addStartList("Bogus", "deleteC");

		primaryStage.setTitle("Address Cleaner");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(15);
		grid.setPadding(new Insets(-15, 25, 0, 25));

		Text header = new Text("Address Cleaner");
		header.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

		btn = new Button();
		btn.setText("Choose File");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Opens a File Dialog. And sets active file to the one chose.
				System.out.println("File Chooser Opened");
				fileChooser = new FileChooser();
				fileChooser.setTitle("File Chooser");
				file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					btn2.setDisable(false);
					HBox hbFileNames = new HBox();
					GridPane.clearConstraints(hbFileNames);
					Label fileName = new Label(file.getName());
					hbFileNames.getChildren().addAll(btn, fileName);
					hbFileNames.setSpacing(40.0);
					grid.add(hbFileNames, 0, 1);
				}
			}
		});

		btn2 = new Button();
		btn2.setText("Clean");
		btn2.setDisable(true);
		btn2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ArrayList<String> addresses = cleaner.getCleanedAddresses(file);
				showAddresses(addresses);
			}

		});

		btnSettings = new Button();
		btnSettings.setText("Settings");
		btnSettings.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				stage.setTitle("Settings");

				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25, 25, 25, 25));

				ArrayList<String[]> settings = cleaner.getSettings();
				int i = 0;
				for (String[] setting : settings) {

					ObservableList<String> options1 = FXCollections.observableArrayList("Starts With", "Find");
					ComboBox comboBox1 = new ComboBox(options1);
					TextField textField1 = new TextField();
					ObservableList<String> options2 = FXCollections.observableArrayList("Replace", "Delete",
							"Delete Cell");
					ComboBox comboBox2 = new ComboBox(options2);
					TextField textField2 = new TextField();

					switch (setting[0]) {
					case "start":
						comboBox1.setValue("Starts With");
						break;
					case "find":
						comboBox1.setValue("Find");
						break;
					}
					textField1.setText(setting[1]);
					switch (setting[2]) {
					case "replaceW":
						comboBox2.setValue("Replace");
						break;
					case "deleteW":
						comboBox2.setValue("Delete");
						break;
					case "deleteC":
						comboBox2.setValue("Delete Cell");
						break;
					}
					textField2.setText(setting[3]);

					Button deleteButton = new Button();
					deleteButton.setText("X");

					deleteButton.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							final int i = grid.getRowIndex(deleteButton);
							ObservableList<Node> childrens = grid.getChildren();
							ArrayList<Node> deleteList = new ArrayList<>();
							ArrayList<Node> moveList = new ArrayList<>();
							for (Node node : childrens) {
								if (grid.getRowIndex(node) == i) {
									deleteList.add(node);
								}
								if (grid.getRowIndex(node) > i) {
									moveList.add(node);
								}
							}
							for (Node node : deleteList) {
								grid.getChildren().remove(node);
							}
							for (Node node : moveList) {
								int rowIndex = grid.getRowIndex(node);
								int columnIndex = grid.getColumnIndex(node);
								grid.getChildren().remove(node);
								grid.add(node, columnIndex, rowIndex - 2);
							}
							stage.setScene(new Scene(grid, 900, 450));
							stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
							stage.show();
						}

					});
					grid.add(comboBox1, 0, i);
					grid.add(textField1, 4, i);
					grid.add(comboBox2, 8, i);
					grid.add(textField2, 12, i);
					grid.add(deleteButton, 16, i);
					i += 2;
				}
				Button btnAdd = new Button();
				btnAdd.setText("Add");
				Button btnSave = new Button();
				btnSave.setText("Save");

				grid.add(btnAdd, 0, i);
				grid.add(btnSave, 16, i);
				stage.setScene(new Scene(grid, 900, 450));
				stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
				stage.show();

				btnAdd.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						ObservableList<String> options1 = FXCollections.observableArrayList("Starts With", "Find");
						ComboBox comboBox1 = new ComboBox(options1);
						TextField textField1 = new TextField();
						ObservableList<String> options2 = FXCollections.observableArrayList("Replace", "Delete",
								"Delete Cell");
						ComboBox comboBox2 = new ComboBox(options2);
						TextField textField2 = new TextField();

						int i = grid.getRowIndex(btnAdd);
						grid.add(comboBox1, 0, i);
						grid.add(textField1, 4, i);
						grid.add(comboBox2, 8, i);
						grid.add(textField2, 12, i);

						Button deleteButton = new Button();
						deleteButton.setText("X");

						deleteButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								final int i = grid.getRowIndex(deleteButton);
								ObservableList<Node> childrens = grid.getChildren();
								ArrayList<Node> deleteList = new ArrayList<>();
								ArrayList<Node> moveList = new ArrayList<>();
								for (Node node : childrens) {
									if (grid.getRowIndex(node) == i) {
										deleteList.add(node);
									}
									if (grid.getRowIndex(node) > i) {
										moveList.add(node);
									}
								}
								for (Node node : deleteList) {
									grid.getChildren().remove(node);
								}
								for (Node node : moveList) {
									int rowIndex = grid.getRowIndex(node);
									int columnIndex = grid.getColumnIndex(node);
									grid.getChildren().remove(node);
									grid.add(node, columnIndex, rowIndex - 2);
								}
								stage.setScene(new Scene(grid, 900, 450));
								stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
								stage.show();
							}

						});

						grid.add(deleteButton, 16, i);
						grid.setRowIndex(btnAdd, i + 2);
						grid.setRowIndex(btnSave, i + 2);
						stage.setScene(new Scene(grid, 900, 450));
						stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
						stage.show();
					}

				});
				btnSave.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						String[] setting = new String[4];
						int k = grid.getRowIndex(btnSave);
						int counter = 0;
						cleaner.clearSettings();
						ObservableList<Node> childrens = grid.getChildren();
						for (Node node : childrens) {
							if (grid.getRowIndex(node) < k) {
								counter += 1;
								System.out.println("ENTERING LOOP");
								switch (grid.getColumnIndex(node)) {
								case 0:
									System.out.println("Saving Setting 0");
									switch ((String) ((ComboBox) node).getValue()) {
									case "Starts With":
										setting[0] = "start";
										break;
									case "Find":
										setting[0] = "find";
										break;
									}
									break;
								case 4:
									System.out.println("Saving Setting 1");
									setting[1] = ((TextField) node).getText();
									break;
								case 8:
									System.out.println("Saving Setting 2");
									switch ((String) ((ComboBox) node).getValue()) {
									case "Replace":
										setting[2] = "replaceW";
										break;
									case "Delete":
										setting[2] = "deleteW";
										break;
									case "Delete Cell":
										setting[2] = "deleteC";
										break;
									}
									break;
								case 12:
									System.out.println("Saving Setting 3");
									setting[3] = ((TextField) node).getText();
									break;
								}
							}
							if (counter == 5) {
								System.out.println("Settings: " + setting[0] + setting[1] + setting[2] + setting[3]);
								if (setting[3] == null)
									setting[3] = "";
								cleaner.addList(setting[0], setting[1], setting[2], setting[3]);
								setting = new String[4];
								counter = 0;
							}
						}
					}
				});
			}
		});

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.CENTER);
		grid.getColumnConstraints().add(column1);
		grid.add(header, 0, 0);
		grid.add(btn, 0, 1);
		grid.add(btn2, 0, 3);
		grid.add(btnSettings, 0, 4);
		Text type = new Text("Insert a .txt file");
		type.setFont(Font.font("Verdana", 9));
		grid.add(type, 0, 5);
		primaryStage.setScene(new Scene(grid, 300, 250));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
		primaryStage.show();
	}

	public void showAddresses(ArrayList<String> addresses) {
		Stage stage = new Stage();
		stage.setTitle("Addresses");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		String addressString = formatList(addresses);

		TextArea addressesText = new TextArea(addressString);
		addressesText.setEditable(false);
		grid.add(addressesText, 0, 0);

		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(addressString);
		clipboard.setContent(content);

		stage.setScene(new Scene(grid, 450, 450));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
		stage.show();
	}

	public String formatList(ArrayList<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str + "\n");
		}
		return sb.toString();
	}
}

class CityInserterStage {
	Label beatFileName = new Label("");
	Label cityFileName = new Label("");
	HBox hbFileNames = new HBox();
	private File file1 = null;
	private File file2 = null;

	public CityInserterStage() {
		Stage stage = new Stage();
		stage.setTitle("City Inserter");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10); // change
		grid.setVgap(20); // change
		grid.setPadding(new Insets(-35, 25, 0, 25));

		CityInserter cInsert = new CityInserter();
		Text header = new Text("City Inserter");
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 30)); 

		Button btn1 = new Button();
		btn1.setText("Select City File");

		Button btn2 = new Button();
		btn2.setDisable(true);
		btn2.setText("Select Beat File");

		Button btn3 = new Button();
		btn3.setDisable(true);
		btn3.setText("Apply");// change
		btn3.setMaxWidth(100); // change

		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				System.out.println("File Chooser Opened");
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("File Chooser");
				file1 = fileChooser.showOpenDialog(stage);
				if (file1 != null) {
					btn2.setDisable(false);
					cityFileName = new Label("");
					hbFileNames = new HBox();
					hbFileNames.setSpacing(120.0);
					cityFileName = new Label(file1.getName());
					hbFileNames.getChildren().addAll(cityFileName, beatFileName);
					grid.add(hbFileNames, 0, 3);
				}
			}

		});
		btn2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("File Chooser Opened");
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("File Chooser");
				file2 = fileChooser.showOpenDialog(stage);
				if (file2 != null) {
					btn3.setDisable(false);
					hbFileNames = new HBox();
					hbFileNames.setSpacing(120.0);
					beatFileName = new Label(file2.getName());
					hbFileNames.getChildren().addAll(cityFileName, beatFileName);
					grid.add(hbFileNames, 0, 3);
				}
			}

		});
		btn3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showCities(cInsert.insertCities(file1, file2), cInsert.getFlags());
			}

		});
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.CENTER);
		grid.getColumnConstraints().add(column1);

		HBox hbButtons = new HBox(); // change
		hbButtons.setSpacing(40.0); // change
		hbButtons.getChildren().addAll(btn1, btn2); // change

		grid.add(header, 0, 0); // change
		grid.add(hbButtons, 0, 2); // change
		grid.add(btn3, 0, 4); // change
		Text type = new Text("Insert a .txt file");
		type.setFont(Font.font("Verdana", 9));
		grid.add(type, 0, 5);
		stage.setScene(new Scene(grid, 300, 250));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
		stage.show();

	}

	public void showCities(ArrayList<String> cityList, ArrayList<Integer> flagsList) {
		Stage stage = new Stage();
		stage.setTitle("Cities");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		String cityString = formatList(cityList);
		TextArea cityText = new TextArea(cityString);
		cityText.setEditable(false);
		grid.add(cityText, 0, 0);

		String flagsString = formatIList(flagsList);
		TextArea flagText = new TextArea(flagsString);
		flagText.setEditable(false);
		grid.add(flagText, 0, 8);

		Label flagMsg = new Label("Unable to find cities in row(s):");
		grid.add(flagMsg, 0, 7);
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(cityString);
		clipboard.setContent(content);

		stage.setScene(new Scene(grid, 450, 450));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
		stage.show();
	}

	public String formatList(ArrayList<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str + "\n");
		}
		return sb.toString();
	}

	public String formatIList(ArrayList<Integer> list) {
		StringBuilder sb = new StringBuilder();
		for (int i : list) {
			sb.append(i + "\n");
		}
		return sb.toString();
	}
}

class CommunityCodeGetterStage {
	File file;
	Stage stage;
	Label fileName;
	ArrayList<String> communityCodes;
	public CommunityCodeGetterStage() {
		stage = new Stage();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		CommunityCodeGetter ccg = new CommunityCodeGetter();
		
		Text header = new Text("Community Code Getter");
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		Button btn = new Button();
		btn.setText("Select File");
		
		Button runBtn = new Button();
		runBtn.setText("Run");
		runBtn.setDisable(true);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("File Chooser");
				file = fileChooser.showOpenDialog(stage);
				if(file != null) {
					runBtn.setDisable(false);
					fileName = new Label(file.getName());
					grid.add(fileName, 0, 3);
					GridPane.setHalignment(fileName, HPos.CENTER);
				}				
			}
			
		});
		runBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage secondaryStage = new Stage();
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25, 25, 25, 25));
				
				communityCodes = ccg.getCommunityCodes(file);
				String communityCodesString = formatList(communityCodes);
				TextArea communityCodesText = new TextArea(communityCodesString);
				communityCodesText.setEditable(false);
				
				final Clipboard clipboard = Clipboard.getSystemClipboard();
				final ClipboardContent content = new ClipboardContent();
				content.putString(communityCodesString);
				clipboard.setContent(content);
				
				grid.add(communityCodesText, 0, 0);
				
				secondaryStage.setScene(new Scene(grid, 450, 450));
				secondaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
				secondaryStage.show();
			}
			
		});
		grid.add(header, 0, 0);
		grid.add(btn, 0,2);
		grid.setHalignment(btn, HPos.CENTER);
		grid.add(runBtn, 0, 4);
		grid.setHalignment(runBtn, HPos.CENTER);
		Text type = new Text("Insert a .txt file");
		type.setFont(Font.font("Verdana", 9));
		grid.add(type, 0, 5);
		grid.setHalignment(type, HPos.CENTER);
		stage.setScene(new Scene(grid, 300, 250));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
		stage.show();
	}
	
	
	}

	class APPSFormatterStage {
		File file;
		Stage stage;
		Label fileName;
			
		APPSFormatterStage() {
			stage = new Stage();
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
				
			APPSFormatter appsf = new APPSFormatter();
				
			Text header = new Text("APPS Formatter");
			header.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
				
			Button btn = new Button();
			btn.setText("Select File");
			
			Button runBtn = new Button();
			runBtn.setText("Run");
			runBtn.setDisable(true);
			
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("File Chooser");
					file = fileChooser.showOpenDialog(stage);
					if(file != null) {
						runBtn.setDisable(false);
						fileName = new Label(file.getName());
						grid.add(fileName, 0, 3);
						GridPane.setHalignment(fileName, HPos.CENTER);
					}				
				}
				
			});
			
			runBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					Stage dialog = new Stage();
					dialog.initStyle(StageStyle.UTILITY);
					Scene scene = null;
					try {
						scene = new Scene(new Group(new Text(25, 25, appsf.Formatter(file))));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dialog.setTitle("Success");
					dialog.setHeight(100);
					dialog.setWidth(200);
					dialog.setScene(scene);
					dialog.show();
				}
			});
			
			grid.add(header, 0, 0);
			grid.add(btn, 0,2);
			grid.setHalignment(btn, HPos.CENTER);
			grid.add(runBtn, 0, 4);
			grid.setHalignment(runBtn, HPos.CENTER);
			Text type = new Text("Insert a .xssf (excel) file");
			type.setFont(Font.font("Verdana", 9));
			grid.add(type, 0, 5);
			grid.setHalignment(type, HPos.CENTER);
			stage.setScene(new Scene(grid, 300, 250));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
			stage.show();
		}
	}
	
	class NarcFormatterStage {
		File file;
		Stage stage;
		Label fileName;
			
		NarcDataFormatter narc;
		
		NarcFormatterStage() {
			stage = new Stage();
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
				
			Text header = new Text("Narc Data Formatter");
			header.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
				
			Button btn = new Button();
			btn.setText("Select File");
			
			Button runBtn = new Button();
			runBtn.setText("Run");
			runBtn.setDisable(true);
			
			TextField rowText = new TextField();
			rowText.setMinWidth(50);
			rowText.setPrefWidth(50);
			rowText.setMaxWidth(400);
			
			Button restoreBtn = new Button();
			restoreBtn.setText("Restore");
			restoreBtn.setDisable(true);
			
			HBox hb = new HBox();
			hb.getChildren().addAll(restoreBtn, rowText);
			hb.setPadding(new Insets(0, 0, 0, 50));
			
			Button demoBtn = new Button();
			demoBtn.setText("Demographics");
			demoBtn.setDisable(true);
			
			HBox hb2 = new HBox();
			hb2.getChildren().addAll(demoBtn);
			hb2.setPadding(new Insets(0, 0, 0, 60));
			
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("File Chooser");
					file = fileChooser.showOpenDialog(stage);
					if(file != null) {
						runBtn.setDisable(false);
						fileName = new Label(file.getName());
						grid.add(fileName, 0, 3);
						GridPane.setHalignment(fileName, HPos.CENTER);
						
						try {
							narc = new NarcDataFormatter(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}				
				}
				
			});
			
			runBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					GridPane grid = new GridPane();
					grid.setAlignment(Pos.CENTER);
					grid.setHgap(10);
					grid.setVgap(10);
					grid.setPadding(new Insets(25, 25, 25, 25));

					String narcString = ("");
					try {
						narcString = narc.Formatter() + formatList(narc.getFlags());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					TextArea cityText = new TextArea(narcString);
					cityText.setEditable(false);
					grid.add(cityText, 0, 0);
					
					Stage dialog = new Stage();					
					dialog.initStyle(StageStyle.UTILITY);
					Scene scene = null;

					dialog.setTitle("Success");
					dialog.setHeight(300);
					dialog.setWidth(300);
					dialog.setScene(new Scene(grid, 600,600));
					dialog.show();
					
					//this is a bandaid fix but if the person ran it twice the file would get deleted
					runBtn.setDisable(true);
					restoreBtn.setDisable(false);
					demoBtn.setDisable(false);
				}
			});
			
			restoreBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					Stage dialog = new Stage();
					dialog.initStyle(StageStyle.UTILITY);
					Scene scene = null;
					try {
						scene = new Scene(new Group(new Text(25, 25, narc.restore(rowText.getText()))));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dialog.setTitle("Success");
					dialog.setHeight(100);
					dialog.setWidth(200);
					dialog.setScene(scene);
					dialog.show();
				}
			});
			
			demoBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					Stage dialog = new Stage();
					dialog.initStyle(StageStyle.UTILITY);
					Scene scene = null;
					scene = new Scene(new Group(new Text(25, 25, narc.getDemographics())));
					dialog.setTitle("Success");
					dialog.setHeight(300);
					dialog.setWidth(250);
					dialog.setScene(scene);
					dialog.show();
				}
			});
			
			grid.add(header, 0, 0);
			grid.add(btn, 0,2);
			grid.setHalignment(btn, HPos.CENTER);
			grid.add(runBtn, 0, 4);
			grid.setHalignment(runBtn, HPos.CENTER);
			Text type = new Text("Insert a .xssf (excel) file");
			type.setFont(Font.font("Verdana", 9));
			grid.add(type, 0, 5);
			grid.setHalignment(type, HPos.CENTER);
			grid.add(hb, 0, 7);
			grid.add(hb2, 0, 9);
			
			stage.setScene(new Scene(grid, 300, 250));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/badge.png"), 100, 100, true, true));
			stage.show();
		}
		
		public String formatList(ArrayList<Integer> list) {
			StringBuilder sb = new StringBuilder();
			sb.append("\n");
			sb.append("Changes made in the following rows: \n");
			for (int str : list) {
				sb.append(str + "\n");
			}
			return sb.toString();
		}
	}
}
