package com.rgrmra.application.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class Field extends JFrame implements WindowListener, ActionListener, MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private Functions function = new Functions();

	// ATRIBUTOS DO MENU
	private JMenuBar menu = new JMenuBar();
	private JMenu menuOption = new JMenu("Configurações");
	private JMenuItem menuFacil = new JMenuItem("Fácil");
	private JMenuItem menuMedio = new JMenuItem("Médio");
	private JMenuItem menuDificil = new JMenuItem("Difícil");
	private JMenuItem menuOneClick = new JMenuItem("OneClick");
	private JMenuItem menuCustom = new JMenuItem("Custom");
	private JMenuItem menuSair = new JMenuItem("Sair");

	// BOTÃO NOVO JOGO
	private JButton newgame = new JButton("Novo Jogo");

	// PAINEIS
	private JPanel panel = new JPanel();
	private JPanel panelNorth = new JPanel();
	private JPanel panelWest = new JPanel();
	private JPanel panelEast = new JPanel();
	private JPanel panelSouth = new JPanel();

	public Field() {
		super("Campo Minado");
		setSize(344, 424);
		setResizable(false);
		setLocationRelativeTo(null);
		addKeyListener(this);

		// ICONE DA JANELA
		setIconImage(function.img);

		for (int i = 0; i < 100; i++) {
			function.button[i].setButton(new JButton("", function.icon[0]));
		}

		function.distribuirbutton(function.settings.getTotalMines());

		// ADICIONA OS PAINEIS AO FRAME
		setLayout(new BorderLayout());
		add(panelNorth, BorderLayout.NORTH);
		add(panelWest, BorderLayout.WEST);
		add(panel, BorderLayout.CENTER);
		add(panelEast, BorderLayout.EAST);
		add(panelSouth, BorderLayout.SOUTH);
		addWindowListener(this);
		addMouseListener(this);

		// ADICIONA O MENU AO FRAME
		setJMenuBar(menu);
		menu.add(menuOption);
		menuOption.add(menuFacil);
		menuOption.add(menuMedio);
		menuOption.add(menuDificil);
		menuOption.add(menuOneClick);
		menuOption.add(new JSeparator());
		menuOption.add(menuCustom);
		menuOption.add(new JSeparator());
		menuOption.add(menuSair);
		menuOption.setPreferredSize(new Dimension(95, 25));
		menuFacil.setPreferredSize(new Dimension(92, 25));
		menuMedio.setPreferredSize(new Dimension(92, 25));
		menuDificil.setPreferredSize(new Dimension(92, 25));
		menuOneClick.setPreferredSize(new Dimension(92, 25));
		menuCustom.setPreferredSize(new Dimension(92, 25));
		menuSair.setPreferredSize(new Dimension(92, 25));

		menuFacil.addActionListener(this);
		menuMedio.addActionListener(this);
		menuDificil.addActionListener(this);
		menuOneClick.addActionListener(this);
		menuCustom.addActionListener(this);
		menuSair.addActionListener(this);

		// ADICIONA UMA FUNÇÃO AO BOTÃO "NOVO JOGO"
		newgame.setFocusable(false);
		newgame.addActionListener(this);
		panelSouth.add(newgame);

		// PAINEL PARA APLICAÇÃO DAS button
		panel.setLayout(new GridLayout(10, 10, 2, 2));

		// ADICIONA OS BOTÕES (button) AO PAINEL
		for (int i = 0; i < 100; i++) {
			panel.add(function.button[i]);
			function.button[i].setFocusable(false);
			function.button[i].addActionListener(this);
			function.button[i].addMouseListener(this);
		}
	}

	// FUNÇÕES (CLIQUES)
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menuFacil)) {
			function.distribuirbutton(10);
		}

		if (e.getSource().equals(menuMedio)) {
			function.distribuirbutton(15);
		}

		if (e.getSource().equals(menuDificil)) {
			function.distribuirbutton(20);
		}

		if (e.getSource().equals(menuOneClick)) {
			function.distribuirbutton(99);
		}

		if (e.getSource().equals(menuCustom)) {
			function.customButton(null);
		}

		if (e.getSource().equals(menuSair)) {
			System.exit(0);
		}

		if (e.getSource().equals(newgame)) {
			function.distribuirbutton(function.settings.getTotalMines());
		}

		for (int i = 0; i < 100; i++) {
			if (e.getSource().equals(function.button[i]) && function.button[i].isEnabled()) {
				if (function.button[i].isMined()) {
					function.endGame(i);
					if (function.settings.isEnabled()) {
						JOptionPane.showMessageDialog(this,
								"<html><span style='color: rgb(212,0,0);'>BOMBA</span>!<br>VOCÊ PERDEU</html>",
								"Fim de Jogo", JOptionPane.PLAIN_MESSAGE);
					}
				} else {
					int numerador = 0;
					numerador = function.localizador(i);
					function.button[i].setBackground(new Color(238, 238, 238));
					function.settings.setRemainingFields(function.settings.getRemainingFields() - 1);
					if (function.settings.getRemainingFields() == 0) {
						function.endGame(100);
						if (function.settings.isEnabled()) {
							JOptionPane.showMessageDialog(this,
									"<html><span style='color: rgb(0,170,0);'>PARABÉNS</span>!<br>VOCÊ VENCEU</html>",
									"Fim de Jogo", JOptionPane.PLAIN_MESSAGE);
						}
					}

					if (numerador > 0) {
						function.button[i].setIcon(function.icon[numerador]);
						function.button[i].setEnabled(false);
					} else {
						function.mostre(i);
					}
				}
			}
		}
	}

	// FECHA A APLICAÇÃO
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowActivated(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
	}

	// BOTÃO DIREITO (BANDEIRINHAS)
	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < 100; i++) {
			if (e.getSource().equals(function.button[i]) && e.getButton() == MouseEvent.BUTTON3) {
				if (function.button[i].isEnabled()) {
					if (function.settings.getRemainingFlags() != 0) {
						function.setButtonLayout(i, 10, false, true, new Color(99, 184, 255), -1);
						if (function.button[i].isMined() && function.settings.getRemainingMines() == 0) {
							function.endGame(100);
							function.setButtonLayout(i, 9, false, true, new Color(99, 184, 255), 0);
						}
					}
				} else {
					if (function.button[i].isFlagged() && function.settings.getRemainingFields() != 0) {
						function.setButtonLayout(i, 0, true, false, new Color(102, 102, 102), 1);
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F1) {
			JOptionPane.showMessageDialog(this,
					"<html>Desenvolvimento e Elaboração de:<br><br>Roger Moura</html>",
					"Desenvolvedores", JOptionPane.PLAIN_MESSAGE);
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			function.distribuirbutton(function.settings.getTotalMines());
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			if (function.settings.isEnabled() == false) {
				function.settings.setEnabled(true);
			} else {
				function.settings.setEnabled(false);
			}
		}
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
}
