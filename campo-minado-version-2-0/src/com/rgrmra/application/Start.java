package com.rgrmra.application;

import com.rgrmra.application.frame.Field;

/**
 * <h1>Campo Minado</h1>
 * Essa aplicação simula um Campo Minado em Java utilizando JFrame.
 *
 * @author Roger de Moura
 * @version 2.0.0
 * since 09-2021
 */

public final class Start {

	public static void main(String[] args) {		
		// INICIA O JOGO
		new Field().setVisible(true);
	}

}
