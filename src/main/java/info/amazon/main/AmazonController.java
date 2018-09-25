package info.amazon.main;

import static info.amazon.validate.DataChecker.*;
import static java.util.concurrent.CompletableFuture.runAsync;

import info.amazon.models.User;
import info.amazon.services.AmazonService;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AmazonController {
	private AmazonService amazonService = new AmazonService();
	private User user;

	@FXML
	private TextField fieldFindProductBy;
	@FXML
	private Button buttonFindProductBy;
	@FXML
	private TextField fieldRegUserName;
	@FXML
	private TextField fieldRegUserEmail;
	@FXML
	private TextField fieldRegUserPassword;
	@FXML
	private Button buttonRegNewUser;
	@FXML
	private TextField fieldAddToCartEmail;
	@FXML
	private TextField fieldAddToCartPassword;
	@FXML
	private TextField fieldAddToCartProduct;
	@FXML
	private Button buttonAddToCart;
	@FXML
	private Label labelFindProduct;
	@FXML
	private Label labelRegUserName;
	@FXML
	private Label labelRegEmail;
	@FXML
	private Label labelRegPassword;
	@FXML
	private Label labelAddProdEmail;
	@FXML
	private Label labelAddProdPassword;
	@FXML
	private Label labelAddProdProduct;

	@FXML
	public void initialize() {
		fieldFindProductBy.focusedProperty().addListener((v, w, n) -> {
			if (n) {
				labelFindProduct.setText("");
			}
		});
		fieldRegUserName.focusedProperty().addListener((v, w, n) -> {
			if (n) {
				labelRegUserName.setText("");
			}
		});

		fieldRegUserEmail.focusedProperty().addListener((v, w, n) -> {
			if (n) {
				labelRegEmail.setText("");
			}
		});

		fieldRegUserPassword.focusedProperty().addListener((v, w, n) -> {
			if (n) {
				labelRegPassword.setText("");
			}
		});

		fieldAddToCartEmail.focusedProperty().addListener((v, w, n) -> {
			if (n) {
				labelAddProdEmail.setText("");
			}
		});

		fieldAddToCartPassword.focusedProperty().addListener((v, w, n) -> {
			if (n) {
				labelAddProdPassword.setText("");
			}
		});

		fieldAddToCartProduct.focusedProperty().addListener((v, w, n) -> {
			if (n) {
				labelAddProdProduct.setText("");
			}
		});
	}

	@FXML
	public void findProduct() {
		String line = fieldFindProductBy.getText();
		
		if (checkURL(line)) {
			runTask(() -> amazonService.findProductByURL(line), buttonFindProductBy);			
		} else if (checkASIN(line)) {
			runTask(() -> amazonService.findProductByASIN(line), buttonFindProductBy);
		} else {
			labelFindProduct.setText("Invaild data");
		}
	}

	@FXML
	public void regNewUser() {
		String name = fieldRegUserName.getText();
		String email = fieldRegUserEmail.getText();
		String password = fieldRegUserPassword.getText();
		
		if (!checkFirstName(name)) {
			labelRegUserName.setText("Inalid name");
			return;
		}
		if (!checkEmail(email)) {
			labelRegEmail.setText("Inalid email");
			return;
		}
		if (!checkPassword(password)) {
			labelRegPassword.setText("Inalid password");
			return;
		}
		
		runTask(() -> {
			user = new User(name, email, password);
			amazonService.regNewUser(user);
		}, buttonRegNewUser);
	}

	@FXML
	public void addProductToCart() {
		String email = fieldAddToCartEmail.getText();
		String password = fieldAddToCartPassword.getText();
		String product = fieldAddToCartProduct.getText();
		
		if (!checkEmail(email)) {
			labelAddProdEmail.setText("Inalid email");
			return;
		}
		if (!checkPassword(password)) {
			labelAddProdPassword.setText("Inalid password");
			return;
		}
		if (!checkProduct(product)) {
			labelAddProdProduct.setText("Inalid product");
			return;
		}

		runTask(() -> {
			user = new User(email, password);
			amazonService.addProductToCart(user, product);
		}, buttonAddToCart);
	}

	public void destroy() {
		amazonService.closeDriver();
	}

	private void runTask(Runnable task, Node button) {
		button.setDisable(true);
		runAsync(task).whenComplete((a, b) -> button.setDisable(false));
	}
}
