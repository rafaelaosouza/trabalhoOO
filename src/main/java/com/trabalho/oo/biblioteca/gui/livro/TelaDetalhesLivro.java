package com.trabalho.oo.biblioteca.gui.livro;

import com.trabalho.oo.biblioteca.model.Livro;

import javax.swing.*;
import java.awt.*;

public class TelaDetalhesLivro extends JFrame {
	public TelaDetalhesLivro(Livro livro) {
		setTitle("Detalhes do Livro");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.CENTER;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.1;
		JLabel tituloLabel = new JLabel("Título: " + livro.getTitulo());
		panel.add(tituloLabel, gbc);

		gbc.gridy = 1;
		JLabel autorLabel = new JLabel("Autor: " + livro.getAutor());
		panel.add(autorLabel, gbc);

		gbc.gridy = 2;
		JLabel isbnLabel = new JLabel("ISBN: " + livro.getIsbn());
		panel.add(isbnLabel, gbc);

		gbc.gridy = 3;
		JLabel categoriaLabel = new JLabel("Categoria: " + livro.getCategoria().getNome());
		panel.add(categoriaLabel, gbc);

		gbc.gridy = 4;
		JLabel anoLancamentoLabel = new JLabel("Ano de Lançamento: " + livro.getAnoLancamento());
		panel.add(anoLancamentoLabel, gbc);

		gbc.gridy = 6;
		JLabel descricaoLabel = new JLabel("Descrição: " + livro.getDescricao());
		panel.add(descricaoLabel, gbc);

		gbc.gridy = 7;
		JLabel quantidadeLabel = new JLabel("Quantidade disponível: " + livro.getQuantidadeDisponivel());
		panel.add(quantidadeLabel, gbc);

		gbc.gridy = 8;
		JLabel avaliacaoLabel = new JLabel("Avaliação: " + getAvaliacaoLabel(livro));
		panel.add(avaliacaoLabel, gbc);

		gbc.gridy = 9;
		JButton fecharButton = new JButton("Fechar");
		fecharButton.addActionListener(e -> dispose());
		panel.add(fecharButton, gbc);

		add(panel);
	}

	private String getAvaliacaoLabel(Livro livro) {
		return livro.calcularMediaAvaliacoes() == -1 ? "Sem avaliações" : livro.calcularMediaAvaliacoes() + "/5";
	}
}
