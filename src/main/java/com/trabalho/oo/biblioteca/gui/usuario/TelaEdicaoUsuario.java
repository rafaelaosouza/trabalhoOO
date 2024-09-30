package com.trabalho.oo.biblioteca.gui.usuario;

import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.model.Usuario;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaEdicaoUsuario extends JFrame {
	private JTextField nomeField;
	private JTextField cpfField;
	private JComboBox<String> tipoComboBox;
	private JButton salvarButton;

	private Sistema sistema;
	private Usuario usuario;

	public TelaEdicaoUsuario(Sistema sistema, Usuario usuario) {
		this.sistema = sistema;
		this.usuario = usuario;

		setTitle("Editar Usuário");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		JLabel nomeLabel = new JLabel("Nome:");
		nomeLabel.setBounds(20, 20, 100, 30);
		add(nomeLabel);

		nomeField = new JTextField(usuario.getNome());
		nomeField.setBounds(120, 20, 200, 30);
		add(nomeField);

		JLabel cpfLabel = new JLabel("CPF:");
		cpfLabel.setBounds(20, 60, 100, 30);
		add(cpfLabel);

		cpfField = new JTextField(usuario.getCpf());
		cpfField.setBounds(120, 60, 200, 30);
		cpfField.setEditable(false);
		add(cpfField);

		JLabel tipoLabel = new JLabel("Tipo:");
		tipoLabel.setBounds(20, 100, 100, 30);
		add(tipoLabel);

		tipoComboBox = new JComboBox<>(new String[]{"Leitor", "Administrador"});
		tipoComboBox.setBounds(120, 100, 200, 30);
		tipoComboBox.setSelectedItem(usuario.isAdmin() ? "Administrador" : "Leitor");
		add(tipoComboBox);

		salvarButton = new JButton("Salvar");
		salvarButton.setBounds(120, 150, 100, 30);
		add(salvarButton);

		salvarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = nomeField.getText();
				String tipoSelecionado = (String) tipoComboBox.getSelectedItem();
				boolean isAdmin = tipoSelecionado.equals("Administrador");
				try {
					 sistema.atualizarUsuario(usuario, nome, isAdmin);
					JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");
					dispose();
				} catch (ValorInvalidoException | IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
				}
			}
		});
	}
}
