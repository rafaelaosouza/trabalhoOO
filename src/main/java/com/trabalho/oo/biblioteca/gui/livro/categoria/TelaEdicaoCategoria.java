package com.trabalho.oo.biblioteca.gui.livro.categoria;

import com.trabalho.oo.biblioteca.model.Categoria;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaEdicaoCategoria extends JFrame {
	private JTextField nomeField;
	private JButton salvarButton;
	private Categoria categoria;
	private Sistema sistema;

	public TelaEdicaoCategoria(Sistema sistema, Categoria categoria) {
		this.sistema = sistema;
		this.categoria = categoria;

		setTitle("Edição de Categoria");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		JLabel nomeLabel = new JLabel("Nome da Categoria:");
		nomeLabel.setBounds(20, 20, 150, 30);
		add(nomeLabel);

		nomeField = new JTextField(categoria.getNome());
		nomeField.setBounds(170, 20, 200, 30);
		add(nomeField);

		salvarButton = new JButton("Salvar");
		salvarButton.setBounds(170, 70, 100, 30);
		add(salvarButton);

		salvarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String novoNome = nomeField.getText();
				try {
					categoria.setNome(novoNome);
					JOptionPane.showMessageDialog(null, "Categoria editada com sucesso!");
					dispose();
				} catch (ValorInvalidoException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao editar categoria!");
				}
			}
		});
	}
}
