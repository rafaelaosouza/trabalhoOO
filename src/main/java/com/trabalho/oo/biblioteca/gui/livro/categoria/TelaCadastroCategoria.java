package com.trabalho.oo.biblioteca.gui.livro.categoria;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.model.Categoria;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroCategoria extends JFrame {
	private JTextField nomeField;
	private JButton cadastrarButton;

	private Sistema sistema;

	public TelaCadastroCategoria(Sistema sistema) {
		this.sistema = sistema;

		setTitle("Cadastro de Categoria");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);
		JButton voltarMenuButton = new JButton("Voltar");
		voltarMenuButton.setBounds(0, 0, 100, 30);
		add(voltarMenuButton);

		voltarMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TelaMenu(sistema).setVisible(true);
				dispose();
			}
		});

		JLabel nomeLabel = new JLabel("Nome da Categoria:");
		nomeLabel.setBounds(20, 40, 150, 30);
		add(nomeLabel);

		nomeField = new JTextField();
		nomeField.setBounds(170, 40, 200, 30);
		add(nomeField);

		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(200, 100, 100, 30);
		cadastrarButton.setBackground(Color.BLUE);
		cadastrarButton.setForeground(Color.WHITE);
		add(cadastrarButton);

		cadastrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = nomeField.getText();
					try {
						Categoria categoria = new Categoria(nome);
						sistema.adicionarCategoria(categoria);
						JOptionPane.showMessageDialog(null, "Categoria cadastrada com sucesso!");
						TelaMenu telaMenu = new TelaMenu(sistema);
						telaMenu.setVisible(true);
						dispose();
					} catch (ValorInvalidoException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Erro ao cadastrar categoria!");
					}
			}
		});
	}
}
