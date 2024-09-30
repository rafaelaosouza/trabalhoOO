package com.trabalho.oo.biblioteca.gui.usuario;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.model.Administrador;
import com.trabalho.oo.biblioteca.model.Leitor;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.utils.UsuarioJaCadastradoException;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastro extends JFrame {
	private JTextField nomeField;
	private JTextField cpfField;
	private JButton cadastrarButton;
	private JButton voltarParaMenuButton;
	private JButton irParaLoginButton;

	private Sistema sistema;

	public TelaCadastro(Sistema sistema, Boolean isAdministrador) {
		this.sistema = sistema;

		setTitle("Cadastro de Usuário");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		JLabel nomeLabel = new JLabel("Nome:");
		nomeLabel.setBounds(20, 20, 100, 30);
		add(nomeLabel);

		nomeField = new JTextField();
		nomeField.setBounds(120, 20, 200, 30);
		add(nomeField);

		JLabel cpfLabel = new JLabel("CPF:");
		cpfLabel.setBounds(20, 60, 100, 30);
		add(cpfLabel);

		cpfField = new JTextField();
		cpfField.setBounds(120, 60, 200, 30);
		add(cpfField);

		JLabel senhaUsuarioLabel = new JLabel("Senha:");
		senhaUsuarioLabel.setBounds(20, 100, 100, 30);
		add(senhaUsuarioLabel);

		JPasswordField senhaUsuarioField = new JPasswordField();
		senhaUsuarioField.setBounds(120, 100, 200, 30);
		add(senhaUsuarioField);

		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(145, 140, 150, 30);
		cadastrarButton.setForeground(Color.WHITE);
		cadastrarButton.setBackground(Color.BLUE);
		add(cadastrarButton);

		if (isAdministrador) {
			voltarParaMenuButton = new JButton("Voltar para menu");
			voltarParaMenuButton.setBounds(145, 180, 150, 30);
			add(voltarParaMenuButton);

			voltarParaMenuButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					TelaMenu telaMenu = new TelaMenu(sistema);
					telaMenu.setVisible(true);
				}
			});
		} else {
			irParaLoginButton = new JButton("Ir para Login");
			irParaLoginButton.setBounds(145, 180, 150, 30);
			add(irParaLoginButton);

			irParaLoginButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					TelaLogin telaLogin = new TelaLogin(sistema);
					telaLogin.setVisible(true);
				}
			});
		}

		cadastrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = nomeField.getText();
				String cpf = cpfField.getText();
				String senha = new String(senhaUsuarioField.getPassword());
				TelaLogin telaLogin = new TelaLogin(sistema);
				TelaMenu telaMenu = new TelaMenu(sistema);
				JFrame proximaTela;

				try {
					if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
						throw new IllegalArgumentException("Preencha todos os campos!");
					}

					if (isAdministrador) {
						Administrador administrador = new Administrador(nome, cpf, senha);
						sistema.cadastrarUsuario(administrador);
						proximaTela = telaMenu;
					} else {
						Leitor leitor = new Leitor(nome, cpf, senha);
						sistema.cadastrarUsuario(leitor);
						proximaTela = telaLogin;
					}
					JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
					dispose();
					proximaTela.setVisible(true);
				} catch (ValorInvalidoException | UsuarioJaCadastradoException | IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
				}
			}
		});
	}
}
