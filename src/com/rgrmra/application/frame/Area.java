package com.rgrmra.application.frame;

import javax.swing.JButton;

public class Area extends JButton {

	private static final long serialVersionUID = 1L;

	// ATRIBUTOS DO JBUTTON ESPECIAL
	private JButton button = new JButton();
	private boolean mined = false;
	private boolean flagged = false;

	// GETTERS E SETTERS
	protected JButton getButton() {
		return button;
	}

	protected void setButton(JButton button) {
		this.button = button;
	}

	protected boolean isMined() {
		return mined;
	}

	protected void setMined(boolean mined) {
		this.mined = mined;
	}

	protected boolean isFlagged() {
		return flagged;
	}

	protected void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
}
