package com.rgrmra.application.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Objects;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Functions extends JFrame {
	private static final long serialVersionUID = 1L;

	// INSTANCIA AS VARIAVEIS DA CLASSE
	protected Area[] button = new Area[101];
	protected Settings settings = new Settings();

	private final ClassLoader classLoader = getClass().getClassLoader();
	protected ImageIcon[] icon = new ImageIcon[11];

	private final Toolkit kit = Toolkit.getDefaultToolkit();
	public Image img = kit.getImage(classLoader.getResource("com/rgrmra/application/images/icone.png"));

	// CONSTRUTOR E DECLARAÇÃO DAS VARIÁVEIS
	protected Functions() {
		icon[0] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/space.png")));
		icon[1] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/num1.png")));
		icon[2] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/num2.png")));
		icon[3] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/num3.png")));
		icon[4] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/num4.png")));
		icon[5] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/num5.png")));
		icon[6] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/num6.png")));
		icon[7] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/num7.png")));
		icon[8] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/num8.png")));
		icon[9] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/mina.png")));
		icon[10] = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/rgrmra/application/images/bandeira.png")));

		for (int i = 0; i < 101; i++) {
			button[i] = new Area();
		}
	}

	// DEFINE O LAYOUT DO BOTÃO
	protected void setButtonLayout(int i, int icon, boolean enable, boolean flagged, Color color, int action) {
		button[i].setIcon(this.icon[icon]);
		button[i].setEnabled(enable);
		button[i].setBackground(color);
		button[i].setFlagged(flagged);
		if (button[i].isMined() && button[i].isFlagged()) {
			settings.setRemainingMines(settings.getRemainingMines() + action);
			settings.setRemainingFlags(settings.getRemainingFlags() + action);
		} else if (button[i].isMined() && !button[i].isFlagged()) {
			settings.setRemainingMines(settings.getRemainingMines() + action);
			settings.setRemainingFlags(settings.getRemainingFlags() + action);
		} else {
			settings.setRemainingFlags(settings.getRemainingFlags() + action);
		}
	}

	// DEFINE A POSIÇÃO DAS MINAS
	protected void setMined() {
		// INSTANCIA O OBJETO RANDOM PARA DISTRIBUIÇÃO DAS MINAS
		Random random = new Random();

		int chosenNumber = random.nextInt(100);
		if (button[chosenNumber].isMined()) {
			setMined();
		} else {
			button[chosenNumber].setMined(true);
		}
	}

	// DISTRIBUI OS BOTÕES
	protected void distribuirbutton(int bombas) {
		settings.setTotalMines(bombas);
		settings.setRemainingMines(bombas);
		settings.setRemainingFields(100 - settings.getTotalMines());
		settings.setRemainingFlags(settings.getTotalMines());

		// PREPARA OS BOTÕES
		for (int sequence = 0; sequence < 100; sequence++) {
			button[sequence].setPreferredSize(new Dimension(30, 30));
			button[sequence].setMined(false);
			this.setButtonLayout(sequence, 0, true, false, new Color(102, 102, 102), 0);
		}

		// DEFINE QUAIS BOTÕES TERÃO MINAS
		for (int sequence = 0; sequence < settings.getTotalMines(); sequence++) {
			this.setMined();
		}
	}

	// CUSTOMIZA O NÚMERO DE BOMBAS
	protected void customButton() {
		try {

			// JANELA PARA OBTER QUANTIDADE DE BOMBAS
			String valor = JOptionPane.showInputDialog(this,
					"<html>Com quantas button deseja jogar?<br>Valido de 10 à 99</html>", "Custom",
					JOptionPane.PLAIN_MESSAGE);

			if (valor != null) {
				if (Integer.parseInt(valor) < 10) {
					// JANELA DE ERRO COM VALOR MENOR QUE 10
					JOptionPane.showMessageDialog(this, "<html>Digite um valor maior que 10!</html>", "Erro",
							JOptionPane.PLAIN_MESSAGE);
					customButton();

				} else if (Integer.parseInt(valor) > 99) {
					// JANELA DE ERRO COM VALOR MAIOR QUE 99
					JOptionPane.showMessageDialog(this, "<html>Digite um valor menor que 99!</html>", "Erro",
							JOptionPane.PLAIN_MESSAGE);
					customButton();
				} else {
					// DISTRIBUI AS BOMBAS COM O VALOR CUSTOMIZADO
					distribuirbutton(Integer.parseInt(valor));
				}
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "<html>Digite um valor Valido!</html>", "Erro",
					JOptionPane.PLAIN_MESSAGE);
			customButton();
		}
	}

	// LOCALIZA TODAS AS MINAS NAS PRÓXIMIDADES
	protected void mostre(int selectedButton) {

		button[selectedButton].setEnabled(false);

		// MINA SUPERIOR ESQUERDA
		if (selectedButton - 11 >= 0 && selectedButton % 10 != 0) {
			reveal(selectedButton - 11);
		}

		// MINA SUPERIOR
		if (selectedButton - 10 >= 0) {
			reveal(selectedButton - 10);
		}

		// MINA SUPERIOR DIREITA
		if (selectedButton - 9 >= 0 && selectedButton % 10 != 9) {
			reveal(selectedButton - 9);
		}

		// MINA ESQUERDA
		if (selectedButton - 1 >= 0 && selectedButton % 10 != 0) {
			reveal(selectedButton - 1);
		}

		// MINA DIREITA
		if (selectedButton + 1 < 100 && selectedButton % 10 != 9) {
			reveal(selectedButton + 1);
		}

		// MINA INFERIOR ESQUERDA
		if (selectedButton + 9 < 100 && selectedButton % 10 != 0) {
			reveal(selectedButton + 9);
		}

		// MINA INFERIOR
		if (selectedButton + 10 < 100) {
			reveal(selectedButton + 10);
		}

		// MINA INFERIOR DIREITA
		if (selectedButton + 11 < 100 && selectedButton % 10 != 9) {
			reveal(selectedButton + 11);
		}
	}

	// REVELA OS CAMPOS VAZIOS
	protected void reveal(int selectedButton) {
		if (button[selectedButton].isEnabled()) {
			button[selectedButton].doClick(0);
		} else {
			if (button[selectedButton].isFlagged()) {
				setButtonLayout(selectedButton, 0, true, false, new Color(215, 238, 244), 1);
				button[selectedButton].doClick(0);
				button[selectedButton].setBackground(new Color(215, 238, 244));
			}
		}
	}

	// ADCIONA OS NÚMEROS DE MINAS NAS PRÓXIMIDADES
	protected int localizador(int selectedButton) {
		int numerador = 0;

		// BOMBA SUPERIOR ESQUERDA
		if (selectedButton - 11 >= 0 && selectedButton % 10 != 0) {
			if (button[selectedButton - 11].isMined()) {
				numerador += 1;
			}
		}

		// BOMBA SUPERIOR
		if (selectedButton - 10 >= 0) {
			if (button[selectedButton - 10].isMined()) {
				numerador += 1;
			}
		}

		// BOMBA SUPERIOR DIREITA
		if (selectedButton - 9 >= 0 && selectedButton % 10 != 9) {
			if (button[selectedButton - 9].isMined()) {
				numerador += 1;
			}
		}

		// BOMBA ESQUERDA
		if (selectedButton - 1 >= 0 && selectedButton % 10 != 0) {
			if (button[selectedButton - 1].isMined()) {
				numerador += 1;
			}
		}

		// BOMBA DIREITA
		if (selectedButton + 1 < 100 && selectedButton % 10 != 9) {
			if (button[selectedButton + 1].isMined()) {
				numerador += 1;
			}
		}

		// BOMBA INFERIOR ESQUERDA
		if (selectedButton + 9 < 100 && selectedButton % 10 != 0) {
			if (button[selectedButton + 9].isMined()) {
				numerador += 1;
			}
		}

		// BOMBA INFERIOR
		if (selectedButton + 10 < 100) {
			if (button[selectedButton + 10].isMined()) {
				numerador += 1;
			}
		}

		// BOMBA INFERIOR DIREITA
		if (selectedButton + 11 < 100 && selectedButton % 10 != 9) {
			if (button[selectedButton + 11].isMined()) {
				numerador += 1;
			}
		}

		return numerador;
	}

	// DECLARA O FINAL DO JOGO
	protected void endGame(int clickedButton) {
		for (int i = 0; i < 100; i++) {
			if (button[i].isEnabled()) {
				if (button[clickedButton].isMined() && button[i].isMined()) {
					this.setButtonLayout(i, 9, false, false, new Color(212, 0, 0), 0);
				} else {
					if (button[i].isMined()) {
						this.setButtonLayout(i, 9, false, false, new Color(0, 170, 0), 0);
					} else if (clickedButton == 100) {
						button[i].doClick(0);
					}
				}
			} else {
				if (button[i].isFlagged() && !button[i].isMined()) {
					this.setButtonLayout(i, 10, false, false, new Color(212, 0, 0), 0);
				} else if (button[i].isMined()) {
					button[i].setIcon(icon[9]);
					button[i].setEnabled(false);
				} else {
					button[i].setEnabled(false);
				}
			}
			button[i].setEnabled(false);
		}
	}
}
