package com.trabalho.oo.biblioteca.main;

import com.trabalho.oo.biblioteca.gui.usuario.TelaCadastro;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.utils.UsuarioJaCadastradoException;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;

public class Main {
	public static void main(String[] args) throws ValorInvalidoException, UsuarioJaCadastradoException {
		Sistema sistema = new Sistema();

		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TelaCadastro telaCadastro = new TelaCadastro(sistema, false);
		telaCadastro.setVisible(true);
	}
}
