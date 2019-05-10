package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.source.doctree.SinceTree;

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

	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml");
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	private synchronized void loadView(String absoluteName) {
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

		} catch (IOException e) {
			Alerts.showAlert("IOEXCEPTION", "Error loader view", e.getMessage(), AlertType.ERROR);

		}
	}
}
