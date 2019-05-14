package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentServices;

public class MainViewController implements Initializable {
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
     //Ação de Inicialização como parametro(Consumer) Exp Lambda
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller)->{
			controller.setDepartmentService(new DepartmentServices());
			controller.updateTableView();
		
		});
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	private synchronized <T> void loadView(String absoluteName , Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();

			Scene mainScene = Main.getMainsScene();
			// Olhar Hierarquia do XML para entender isso aqui
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			// Pega o primeiro filho do VBox
			Node mainMenu = mainVbox.getChildren().get(0);
			// Limpa todos os filhos do mainVbox
			mainVbox.getChildren().clear();
			//Adiciona um mainMenu a ao mainVbox
			mainVbox.getChildren().add(mainMenu);
			//Adiciona os filhos de newVbox
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
			//Comando maluco para ativar o Consumer
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IOEXCEPTION", "Error loader view", e.getMessage(), AlertType.ERROR);

		}
	}
	/*
	private synchronized void loadView2(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();

			Scene mainScene = Main.getMainsScene();
			// Olhar Hierarquia do XML para entender isso aqui
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			// Pega o primeiro filho do VBox
			Node mainMenu = mainVbox.getChildren().get(0);
			// Limpa todos os filhos do mainVbox
			mainVbox.getChildren().clear();
			//Adiciona um mainMenu a ao mainVbox
			mainVbox.getChildren().add(mainMenu);
			//Adiciona os filhos de newVbox
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
			DepartmentListController controller = loader.getController();
			controller.setDepartmentService(new DepartmentServices());
			controller.updateTableView();

		} catch (IOException e) {
			Alerts.showAlert("IOEXCEPTION", "Error loader view", e.getMessage(), AlertType.ERROR);

		}
		*/
	}
	
	

