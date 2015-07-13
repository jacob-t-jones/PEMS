import java.awt.Graphics;


public interface IScreen {
	
	void paintComponent(Graphics g);

	void constructInstructionsLabel();
	
	void constructCaseNumField();
	
	void constructContinueButton();
	
}
