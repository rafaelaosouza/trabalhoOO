package com.trabalho.oo.biblioteca.gui.emprestimo;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.model.Emprestimo;
import com.trabalho.oo.biblioteca.model.Leitor;
import com.trabalho.oo.biblioteca.model.Livro;
import com.trabalho.oo.biblioteca.service.Sistema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TelaDevolucao extends JFrame {
	private JComboBox<Livro> emprestimoComboBox;
	private JTextField avaliacaoField;
	private JButton devolverButton;
	private Sistema sistema;

	public TelaDevolucao(Sistema sistema) {
		this.sistema = sistema;

		setTitle("Devolução");
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

		JLabel emprestimoLabel = new JLabel("Selecione o Empréstimo:");
		emprestimoLabel.setBounds(20, 40, 200, 30);
		add(emprestimoLabel);

		emprestimoComboBox = new JComboBox<>(getLivrosEmprestados().toArray(new Livro[0]));
		emprestimoComboBox.setBounds(180, 40, 200, 30);
		add(emprestimoComboBox);

		JLabel avaliacaoLabel = new JLabel("Avaliação (1 a 5):");
		avaliacaoLabel.setBounds(20, 90, 150, 30);
		add(avaliacaoLabel);

		avaliacaoField = new JTextField();
		avaliacaoField.setBounds(200, 90, 150, 30);
		add(avaliacaoField);

		devolverButton = new JButton("Devolver");
		devolverButton.setBounds(150, 140, 100, 40);
		add(devolverButton);

		devolverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Livro livroSelecionado = (Livro) emprestimoComboBox.getSelectedItem();
				Emprestimo emprestimoSelecionado = sistema.getEmprestimoPorLeitorELivroEStatus(sistema.getUsuarioLogado(),
						livroSelecionado);

				if (emprestimoSelecionado != null) {
					String avaliacaoTexto = avaliacaoField.getText();
					try {
						int avaliacao = Integer.parseInt(avaliacaoTexto);
						if (avaliacao < 1 || avaliacao > 5) {
							JOptionPane.showMessageDialog(null, "Avaliação deve ser entre 1 e 5.");
							return;
						}
						sistema.devolverLivro(emprestimoSelecionado, avaliacao); // Adicione este método no sistema
						JOptionPane.showMessageDialog(null, "Livro devolvido com sucesso!");
						TelaMenu telaMenu = new TelaMenu(sistema);
						telaMenu.setVisible(true);
						dispose();
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para a avaliação.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um empréstimo!");
				}
			}
		});
	}

	private List<Livro> getLivrosEmprestados() {
		List<Emprestimo> emprestimos = sistema.getEmprestimosPorLeitor(sistema.getUsuarioLogado());
		List<Livro> livros = new ArrayList<>();

		System.out.println(emprestimos);
		for (Emprestimo emprestimo : emprestimos) {
			if(emprestimo.isDevolucaoPendente()) {
				livros.add(emprestimo.getLivro());
			}
		}
		return livros;
	}
}
