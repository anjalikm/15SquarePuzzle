package application;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class puzzleButton extends Button{
	private final int SIZE = 40;
	public puzzleButton(){
		this.setStyle("#b6e7c9;");
		this.setMaxSize(SIZE,SIZE);
		this.setMinSize(SIZE, SIZE);
	}
	
	
	
	DropShadow shadow = new DropShadow();
	EventHandler<MouseEvent> mouseEnterHandler = new EventHandler<MouseEvent>() {
	    	 
	        @Override
	        public void handle(MouseEvent mouseEvent) {
	           // System.out.println(mouseEvent.getEventType());
	            Button mouseOnButton = (Button) mouseEvent.getSource();
	            mouseOnButton.setEffect(shadow);
	             

	        }
	     
	    };
	    
	    EventHandler<MouseEvent> mouseExitHandler = new EventHandler<MouseEvent>() {
	    	 
	        @Override
	        public void handle(MouseEvent mouseEvent) {
	           // System.out.println(mouseEvent.getEventType());
	            Button mouseOnButton = (Button) mouseEvent.getSource();
	            mouseOnButton.setEffect(null);
	             

	        }
	     
	    };

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
