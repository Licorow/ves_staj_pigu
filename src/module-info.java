module PiGu {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens app to javafx.graphics, javafx.fxml;
}
