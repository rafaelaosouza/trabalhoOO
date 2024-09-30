package com.trabalho.oo.biblioteca.gui.emprestimo;

import com.trabalho.oo.biblioteca.model.Emprestimo;
import com.trabalho.oo.biblioteca.service.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaEdicaoEmprestimo extends JFrame {
	private Sistema sistema;
	private Emprestimo emprestimo;
	private JSpinner avaliacaoSpinner;

	public TelaEdicaoEmprestimo(Sistema sistema, Emprestimo emprestimo) {
		this.sistema = sistema;
		this.emprestimo = emprestimo;

		setTitle("Editar Empréstimo - " + emprestimo.getLivro().getTitulo());
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3, 2, 10, 10));
		setLocationRelativeTo(null);

		JLabel labelAvaliacao = new JLabel("Avaliação:");
		add(labelAvaliacao);
		avaliacaoSpinner = new JSpinner(new SpinnerNumberModel(emprestimo.getAvaliacao() == -1 ? 1 : emprestimo.getAvaliacao(), 1, 5, 1));
		add(avaliacaoSpinner);

		JButton salvarButton = new JButton("Salvar");
		add(salvarButton);

		JButton cancelarButton = new JButton("Cancelar");
		add(cancelarButton);

		salvarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int novaAvaliacao = (Integer) avaliacaoSpinner.getValue();
				sistema.atualizarEmprestimo(emprestimo, novaAvaliacao);
				JOptionPane.showMessageDialog(TelaEdicaoEmprestimo.this, "Avaliação atualizada com sucesso!");
				dispose();
			}
		});

		cancelarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
