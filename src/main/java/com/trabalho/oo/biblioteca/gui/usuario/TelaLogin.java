package com.trabalho.oo.biblioteca.gui.usuario;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.model.Usuario;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {
	private JTextField cpfField;
	private JButton loginButton;
	private JButton cadastroButton;

	private Sistema sistema;

	public TelaLogin(Sistema sistema) {
		this.sistema = sistema;

		setTitle("Login de Usuário");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		JLabel cpfLabel = new JLabel("CPF:");
		cpfLabel.setBounds(20, 20, 100, 30);
		add(cpfLabel);

		cpfField = new JTextField();
		cpfField.setBounds(120, 20, 200, 30);
		add(cpfField);

		JLabel senhaUsuarioLabel = new JLabel("Senha:");
		senhaUsuarioLabel.setBounds(20, 60, 100, 30);
		add(senhaUsuarioLabel);

		JPasswordField senhaUsuarioField = new JPasswordField();
		senhaUsuarioField.setBounds(120, 60, 200, 30);
		add(senhaUsuarioField);

		loginButton = new JButton("Login");
		loginButton.setBounds(120, 100, 200, 30);
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(Color.BLUE);
		add(loginButton);

		cadastroButton = new JButton("Ir para Cadastro");
		cadastroButton.setBounds(120, 140, 200, 30);
		add(cadastroButton);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cpf = cpfField.getText();
				String senha = new String(senhaUsuarioField.getPassword());

				try {
					Usuario usuario = sistema.login(cpf, senha);
					JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
					usuario.acessarSistema();
					dispose();
					TelaMenu telaMenu = new TelaMenu(sistema);
					telaMenu.setVisible(true);
				} catch (ValorInvalidoException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
				}
			}
		});

		cadastroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				TelaCadastro telaCadastro = new TelaCadastro(sistema, false);
				telaCadastro.setVisible(true);
				setVisible(false);
			}
		});
	}
}
