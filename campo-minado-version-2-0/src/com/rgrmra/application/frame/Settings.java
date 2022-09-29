package com.rgrmra.application.frame;

public class Settings {

	// ATRIBUTOS DE CONFIGURAÇÕES DA APLICAÇÃO
	private boolean enabled = true;
	private int totalMines = 15;
	private int remainingFlags = 15;
	private int remainingMines = 15;
	private int remainingFields = 85;

	// GETTERS E SETTERS
	protected boolean isEnabled() {
		return enabled;
	}

	protected void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	protected int getTotalMines() {
		return totalMines;
	}

	protected void setTotalMines(int totalMines) {
		this.totalMines = totalMines;
	}

	protected int getRemainingFlags() {
		return remainingFlags;
	}

	protected void setRemainingFlags(int remainingFlags) {
		this.remainingFlags = remainingFlags;
	}

	protected int getRemainingMines() {
		return remainingMines;
	}

	protected void setRemainingMines(int remainingMines) {
		this.remainingMines = remainingMines;
	}

	protected int getRemainingFields() {
		return remainingFields;
	}

	protected void setRemainingFields(int remainingFields) {
		this.remainingFields = remainingFields;
	}

}
