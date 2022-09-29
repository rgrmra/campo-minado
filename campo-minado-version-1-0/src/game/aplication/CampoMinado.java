package game.aplication;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * <h1>Campo Minado</h1>
 * Essa aplicação simnula um campo minado em Java utilizando JFrame.
 *
 * @author Roger de Moura
 * @version 1.0.0
 * @since 09-2012
 */

public class CampoMinado extends JFrame implements WindowListener, ActionListener, MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
		
	// ATRIBUTOS
	JButton[] burron = new JButton[100];
	int bombas[] = new int[101];
	int dburron = 85, tbombas = 15, rbombas = 15, flags = 15, abombas =tbombas, enabled = 0, fim = 0;
		
	// ATRIBUTOS DO MENU
	JMenuBar menu = new JMenuBar();
	JMenu menuOption = new JMenu("Configurações");
	JMenuItem menuFacil = new JMenuItem("Fácil");
	JMenuItem menuMedio = new JMenuItem("Médio");
	JMenuItem menuDificil = new JMenuItem("Difícil");
	JMenuItem menuOneClick = new JMenuItem("OneClick");
	JMenuItem menuCustom = new JMenuItem("Custom");
	JMenuItem menuSair = new JMenuItem("Sair");

	// IMAGENS DO JOGO
	ClassLoader classLoader = getClass().getClassLoader();  
	ImageIcon bandeira = new ImageIcon(classLoader.getResource("game/imagens/bandeira.png"));
	ImageIcon space = new ImageIcon(classLoader.getResource("game/imagens/space.png")); 
	ImageIcon mina = new ImageIcon(classLoader.getResource("game/imagens/mina.png"));  
	ImageIcon num1 = new ImageIcon(classLoader.getResource("game/imagens/num1.png")); 
	ImageIcon num2 = new ImageIcon(classLoader.getResource("game/imagens/num2.png")); 
	ImageIcon num3 = new ImageIcon(classLoader.getResource("game/imagens/num3.png")); 
	ImageIcon num4 = new ImageIcon(classLoader.getResource("game/imagens/num4.png")); 
	ImageIcon num5 = new ImageIcon(classLoader.getResource("game/imagens/num5.png")); 
	ImageIcon num6 = new ImageIcon(classLoader.getResource("game/imagens/num6.png")); 
	ImageIcon num7 = new ImageIcon(classLoader.getResource("game/imagens/num7.png")); 
	ImageIcon num8 = new ImageIcon(classLoader.getResource("game/imagens/num8.png")); 
	
	// BOTÃO NOVO JOGO
	JButton newgame = new JButton("Novo Jogo");
	
	// PAINEIS
	JPanel panel = new JPanel();
	JPanel panelNorth = new JPanel();
	JPanel panelWest = new JPanel();
	JPanel panelEast = new JPanel();
	JPanel panelSouth = new JPanel();
	
	// BASE DO PROGRAMA
	public CampoMinado() {
		super("Campo Minado");
		setSize(344, 424);
		setResizable(false);
		setLocationRelativeTo(null);
		addKeyListener(this);
 
		// ICONE DA JANELA
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage(classLoader.getResource("game/imagens/icone.png"));
		setIconImage(img);
		
		for (int i = 0; i < 100; i++) {
			burron[i] = new JButton("", space);
			burron[i].setBackground(new Color(102, 102, 102));
			burron[i].setPreferredSize(new Dimension(30, 30));
		}		

		distribuirBombas();

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

		// PAINEL PARA APLICAÇÃO DAS BOMBAS
		panel.setLayout(new GridLayout(10, 10, 2, 2));

		// ADICIONA OS BOTÕES (BOMBAS) AO PAINEL
		for (int i = 0; i < 100; i++) {
			panel.add(burron[i]);
			burron[i].setFocusable(false);
			burron[i].addActionListener(this);
			burron[i].addMouseListener(this);
		}
	}

	// TORNA A APLICAÇÃO VISIVEL
	public static void main(String[] args) {
		CampoMinado supergame = new CampoMinado();
		supergame.setVisible(true);
	}

	// DISTRIBUIR BOMBAS
	private void distribuirBombas() {
		fim = 0;
		dburron = 100 - tbombas;
		rbombas = tbombas;
		flags = tbombas;	

		for (int i = 0; i < 100; i++) {
			bombas[i] = 0;
			burron[i].setText("");
			burron[i].setIcon(space);
			burron[i].setBackground(new Color(102, 102, 102));
			burron[i].setPreferredSize(new Dimension(30, 30));
			burron[i].setEnabled(true);
		}

		// INSTANCIA O OBJETO RANDOM PARA DISTRIBUIÇÃO DAS BOMBAS
		Random random = new Random();
		
		// DEFINE QUAIS CAMPOS TERÃO BOMBAS
		for (int i = 0; i < tbombas; i++) {
			int x = random.nextInt(100);
			if (bombas[x] == 1) {
				i -= 1;
			} else {
				bombas[x] = 1;
			}
		}
		
		// BOMBA RESERVA
		bombas[100] = 0;
	}		

	// CUSTOM BOMBAS
	private void customBombas(String valor) {
		try {
			valor = JOptionPane.showInputDialog(this, 
					"<html>Com quantas bombas deseja jogar?<br>Valido de 10 � 99</html>", 
					"Custom", JOptionPane.PLAIN_MESSAGE);
			if (valor != null) {
				tbombas = Integer.parseInt(valor);
				if (tbombas < 10) {
					JOptionPane.showMessageDialog(this, 
							"<html>Digite um valor maior que 10!</html>", 
							"Erro", JOptionPane.PLAIN_MESSAGE);
					tbombas = 10;
					customBombas(null);
				} else if (tbombas > 99) {
					JOptionPane.showMessageDialog(this, 
							"<html>Digite um valor menor que 99!</html>", 
							"Erro", JOptionPane.PLAIN_MESSAGE);
					tbombas = 10;
					customBombas(null);
				} else {
					abombas = tbombas;
					distribuirBombas();
				}
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"<html>Digite um valor Valido!</html>", 
					"Erro", JOptionPane.PLAIN_MESSAGE);
			customBombas(null);			
		}		
	}

	// LOCALIZA TODAS AS BOMBAS
	private void mostre(int i) {

		burron[i].setEnabled(false);
		
		// BOMBA SUPERIOR ESQUERDA
		if (i - 11 >= 0 & i % 10 != 0) {
			takeOut(i - 11);
		}

		// BOMBA SUPERIOR
		if (i - 10 >= 0) {
			takeOut(i - 10);
		}

		// BOMBA SUPERIOR DIREITA
		if (i - 9 >= 0 & i % 10 != 9) {
			takeOut(i - 9);
		}

		// BOMBA ESQUERDA
		if (i - 1 >= 0 & i % 10 != 0) {
			takeOut(i - 1);
		}

		// BOMBA DIREITA
		if (i + 1 < 100 & i % 10 != 9) {
			takeOut(i + 1);
		}

		// BOMBA INFERIOR ESQUERDA
		if (i + 9 < 100 && i % 10 != 0) {
			takeOut(i + 9);
		}

		// BOMBA INFERIOR
		if (i + 10 < 100) {
			takeOut(i + 10);
		}

		// BOMBA INFERIOR DIREITA
		if (i + 11 < 100 && i % 10 != 9) {
			takeOut(i + 11);
		}
	}

	private void takeOut(int i){
		int numerador = localizador(i);
		for (int j = 0; j < 9; j++) {
			if (numerador == j) {
				if (burron[i].isEnabled() == true) { 
					burron[i].doClick(0);						
				} else {
					if (bombas[i] == 2) {
						burron[i].setIcon(space);
						burron[i].setEnabled(true);
						bombas[i] -= 2;
						burron[i].doClick(0);
						burron[i].setBackground(new Color(215,238,244));
						flags += 1;
					}
				}				
			}
		}
	}
	
	// ADCIONA OS NÙMEROS DE BOMBAS NAS PRÒXIMIDADES
	private int localizador(int i) {
		
		int numerador = 0;

		// BOMBA SUPERIOR ESQUERDA
		if (i - 11 >= 0 & i % 10 != 0) {
			if (bombas[i - 11] == 1 || bombas[i - 11] == 3) {
				numerador += 1;
			}
		}

		// BOMBA SUPERIOR
		if (i - 10 >= 0) {
			if (bombas[i - 10] == 1 || bombas[i - 10] == 3) {
				numerador += 1;
			}
		}

		// BOMBA SUPERIOR DIREITA
		if (i - 9 >= 0 & i % 10 != 9) {
			if (bombas[i - 9] == 1 || bombas[i - 9] == 3) {
				numerador += 1;
			}
		}

		// BOMBA ESQUERDA
		if (i - 1 >= 0 & i % 10 != 0) {
			if (bombas[i - 1] == 1 || bombas[i - 1] == 3) {
				numerador += 1;
			}
		}

		// BOMBA DIREITA
		if (i + 1 < 100 & i % 10 != 9) {
			if (bombas[i + 1] == 1 || bombas[i + 1] == 3) {
				numerador += 1;
			}
		}

		// BOMBA INFERIOR ESQUERDA
		if (i + 9 < 100 && i % 10 != 0) {
			if (bombas[i + 9] == 1 || bombas[i + 9] == 3) {
				numerador += 1;
			}
		}

		// BOMBA INFERIOR
		if (i + 10 < 100) {
			if (bombas[i + 10] == 1 || bombas[i + 10] == 3) {
				numerador += 1;
			}
		}

		// BOMBA INFERIOR DIREITA
		if (i + 11 < 100 && i % 10 != 9) {
			if (bombas[i + 11] == 1 || bombas[i + 11] == 3) {
				numerador += 1;
			}
		}

		return numerador;

	}
	
	private void fimDeJogo(int j) {		
		for (int i = 0; i < 100; i++) {
			if (burron[i].isEnabled()) {
				if (bombas[j] == 1) {
					fim = 1;
					if (bombas[i] == 1) {
						burron[i].setIcon(mina);
						burron[i].setEnabled(false);
						burron[i].setBackground(new Color(212, 0, 0));
					} else {
						burron[i].setEnabled(false);
					}
				} else {
					fim = 1;
					if (bombas[i] == 1) {
						burron[i].setIcon(mina);
						burron[i].setEnabled(false);
						burron[i].setBackground(new Color(0, 170, 0));
					} else {
						if (j == 100) {
							for (int k = 0; k < 100; k++) {
								burron[i].doClick(0);
								burron[i].setEnabled(false);
							}
						}
					}
				}
			} else {
				if (bombas[i] == 3) {
					burron[i].setIcon(mina);
					burron[i].setEnabled(false);
				} else {
					burron[i].setEnabled(false);
				}
			}
		}		
	}

	// FUNÇÕES (CLIQUES)
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menuFacil)) {
			tbombas = 10;
			abombas = 10;
			distribuirBombas();
		}
		if (e.getSource().equals(menuMedio)) {
			tbombas = 15;
			abombas = 15;
			distribuirBombas();
		}
		if (e.getSource().equals(menuDificil)) {
			tbombas = 20;
			abombas = 20;
			distribuirBombas();
		}
		if (e.getSource().equals(menuOneClick)) {
			tbombas = 99;
			abombas = 99;
			distribuirBombas();
		}
		if (e.getSource().equals(menuCustom)) {
			customBombas(null);
		}
		if (e.getSource().equals(menuSair)) {
			System.exit(0);
		}	
		if (e.getSource().equals(newgame)) {
			distribuirBombas();
		}
		for (int i = 0; i < 100; i++) {
			if (e.getSource().equals(burron[i])) {
				if (burron[i].isEnabled()){
					if (bombas[i] == 1) {			
						fimDeJogo(i);		
						if (enabled == 0) {														
							JOptionPane.showMessageDialog(this,
									"<html><span style='color: rgb(212,0,0);'>BOMBA</span>!<br>VOC� PERDEU</html>",
									"Fim de Jogo", JOptionPane.PLAIN_MESSAGE);
						}
					} else if (bombas[i] == 0) {
						int numerador = 0;
						numerador = localizador(i);	
						burron[i].setBackground(new Color(238, 238, 238));						
						dburron -= 1;
						if (dburron == 0) {
							fimDeJogo(i);	
							if (enabled == 0) {
								JOptionPane.showMessageDialog(this,
										"<html><span style='color: rgb(0,170,0);'>PARAB�NS</span>!<br>VOC� VENCEU</html>",
										"Fim de Jogo", JOptionPane.PLAIN_MESSAGE);
							}
						}
						if (numerador > 0) {
							if (numerador == 1) {
								burron[i].setIcon(num1);								
							} else if (numerador == 2) {
								burron[i].setIcon(num2);
							} else if (numerador == 3) {
								burron[i].setIcon(num3);
							} else if (numerador == 4) {
								burron[i].setIcon(num4);
							} else if (numerador == 5) {
								burron[i].setIcon(num5);
							} else if (numerador == 6) {
								burron[i].setIcon(num6);
							} else if (numerador == 7) {
								burron[i].setIcon(num7);
							} else if (numerador == 8) {
								burron[i].setIcon(num8);
							}
							burron[i].setEnabled(false);
						} else {
							mostre(i);
						}
					}
				}
			}
		}
	}

	// FECHA A APLICAÇÃO
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

	// BOTÃO DIREITO (BANDEIRINHAS)
	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < 100; i++) {
			if (e.getSource().equals(burron[i])) {			
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (burron[i].isEnabled()) {
						if (bombas[i] == 0 || bombas[i] == 1) {
							if (flags != 0) {
								if (bombas[i] == 1){
									rbombas -= 1;
									if (rbombas ==  0) {
										fimDeJogo(100);
										burron[i].setIcon(mina);
										burron[i].setBackground(new Color(99, 184, 255));
									} 
								}
								if (fim == 0){
									burron[i].setIcon(bandeira);
									burron[i].setEnabled(false);
									burron[i].setBackground(new Color(99, 184, 255));
									bombas[i] += 2;
									flags -= 1;
								}
							}							
						}
					} else if (bombas[i] == 2 || bombas[i] == 3) {
						if (fim == 0) {
							if (bombas[i] == 3) {
								rbombas += 1;
							}
							burron[i].setIcon(space);
							burron[i].setEnabled(true);
							burron[i].setBackground(new Color(102, 102, 102));
							bombas[i] -= 2;
							flags += 1;
						}			
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F1) {
			JOptionPane.showMessageDialog(this,
					"<html>Desenvolvimento e Elaboração de:<br><br>Roger Moura</html>",
					"Desenvolvedor", JOptionPane.PLAIN_MESSAGE);
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			distribuirBombas();			
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			if (enabled == 0) {
				enabled = 1;
			} else {
				enabled = 0;
			}
		}
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
}
