package com.trabalho.oo.biblioteca.gui.emprestimo;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.gui.livro.TelaDetalhesLivro;
import com.trabalho.oo.biblioteca.model.Livro;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.utils.LivroIndisponivelException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaEmprestimo extends JFrame {
	private JComboBox<Livro> livroComboBox;
	private JButton emprestarButton;
	private JButton detalhesButton;
	private Sistema sistema;

	public TelaEmprestimo(Sistema sistema) {
		this.sistema = sistema;

		setTitle("Empréstimo");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		JButton voltarMenuButton = new JButton("Voltar");
		voltarMenuButton.setBounds(0, 0, 150, 30);
		add(voltarMenuButton);

		voltarMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TelaMenu(sistema).setVisible(true);
				dispose();
			}
		});

		JLabel livroLabel = new JLabel("Selecione o Livro:");
		livroLabel.setBounds(20, 40, 150, 40);
		add(livroLabel);

		livroComboBox = new JComboBox<>(sistema.getLivros().toArray(new Livro[0]));
		livroComboBox.setBounds(150, 40, 200, 40);
		add(livroComboBox);

		detalhesButton = new JButton("Ver detalhes");
		detalhesButton.setBounds(170, 100, 150, 30);
		add(detalhesButton);

		detalhesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Livro livroSelecionado = (Livro) livroComboBox.getSelectedItem();
				if (livroSelecionado != null) {
					new TelaDetalhesLivro(livroSelecionado).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um livro!");
				}
			}
		});

		emprestarButton = new JButton("Pegar Emprestado");
		emprestarButton.setBounds(170, 140, 150, 30);
		emprestarButton.setForeground(Color.WHITE);
		emprestarButton.setBackground(Color.BLUE);
		add(emprestarButton);

		emprestarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Livro livro = (Livro) livroComboBox.getSelectedItem();
				if (livro != null) {
					boolean resp = false;
					try {
						resp = sistema.emprestarLivro(sistema.getUsuarioLogado(), livro);
						if (resp) {
							JOptionPane.showMessageDialog(null, "Livro emprestado com sucesso! " +
									"Lembre-se você deve devolver o livro em até 30 dias.");
							new TelaMenu(sistema).setVisible(true);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Selecione um livro!");
						}
					} catch (LivroIndisponivelException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}
		});
	}
}
