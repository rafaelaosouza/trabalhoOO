package com.trabalho.oo.biblioteca.gui.livro;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.model.Categoria;
import com.trabalho.oo.biblioteca.model.Livro;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroLivros extends JFrame {
	private JTextField tituloField;
	private JTextField autorField;
	private JTextField isbnField;
	private JSpinner quantidadeField;
	private JTextArea descricaoArea;
	private JTextField anoLancamentoField;
	private JComboBox<Categoria> categoriaComboBox;
	private JButton cadastrarButton;

	private Sistema sistema;

	public TelaCadastroLivros(Sistema sistema) {
		this.sistema = sistema;

		setTitle("Cadastrar Livro");
		setSize(400, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		JButton voltarMenuButton = new JButton("Voltar");
		voltarMenuButton.setBounds(0, 0, 150, 30);
		add(voltarMenuButton);

		voltarMenuButton.addActionListener(e -> {
			new TelaMenu(sistema).setVisible(true);
			dispose();
		});

		JLabel tituloLabel = new JLabel("Título:");
		tituloLabel.setBounds(20, 40, 100, 30);
		add(tituloLabel);

		tituloField = new JTextField();
		tituloField.setBounds(120, 40, 200, 30);
		add(tituloField);

		JLabel autorLabel = new JLabel("Autor:");
		autorLabel.setBounds(20, 80, 100, 30);
		add(autorLabel);

		autorField = new JTextField();
		autorField.setBounds(120, 80, 200, 30);
		add(autorField);

		JLabel isbnLabel = new JLabel("ISBN:");
		isbnLabel.setBounds(20, 120, 100, 30);
		add(isbnLabel);

		isbnField = new JTextField();
		isbnField.setBounds(120, 120, 200, 30);
		add(isbnField);

		JLabel categoriaLabel = new JLabel("Categoria:");
		categoriaLabel.setBounds(20, 160, 100, 30);
		add(categoriaLabel);

		categoriaComboBox = new JComboBox<>(sistema.getCategorias().toArray(new Categoria[0]));
		categoriaComboBox.setBounds(120, 160, 200, 30);
		add(categoriaComboBox);

		JLabel quantidadeLabel = new JLabel("Quantidade:");
		quantidadeLabel.setBounds(20, 200, 100, 30);
		add(quantidadeLabel);

		quantidadeField = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
		quantidadeField.setBounds(120, 200, 200, 30);
		add(quantidadeField);

		JLabel anoLancamentoLabel = new JLabel("Ano de Lançamento:");
		anoLancamentoLabel.setBounds(20, 240, 150, 30);
		add(anoLancamentoLabel);

		anoLancamentoField = new JTextField();
		anoLancamentoField.setBounds(170, 240, 150, 30);
		add(anoLancamentoField);

		JLabel descricaoLabel = new JLabel("Descrição:");
		descricaoLabel.setBounds(20, 280, 100, 30);
		add(descricaoLabel);

		descricaoArea = new JTextArea();
		descricaoArea.setBounds(120, 280, 200, 80);
		add(descricaoArea);

		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(120, 360, 100, 30);
		cadastrarButton.setBackground(Color.BLUE);
		cadastrarButton.setForeground(Color.WHITE);
		add(cadastrarButton);

		cadastrarButton.addActionListener(e -> {
			String titulo = tituloField.getText().trim();
			String autor = autorField.getText().trim();
			String isbn = isbnField.getText().trim();
			String descricao = descricaoArea.getText().trim();
			String quantidadeTexto = quantidadeField.getValue().toString();
			String anoLancamentoTexto = anoLancamentoField.getText().trim();
			int quantidade;
			try {
				quantidade = Integer.parseInt(quantidadeTexto);
				if (quantidade < 0) {
					JOptionPane.showMessageDialog(null, "A quantidade deve ser maior ou igual a zero.");
					return;
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Quantidade deve ser um número inteiro.");
				return;
			}
			int anoLancamento;
			try {
				anoLancamento = Integer.parseInt(anoLancamentoTexto);
				int anoAtual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
				if (anoLancamento < 0 || anoLancamento > anoAtual) {
					JOptionPane.showMessageDialog(null, "O ano de lançamento deve ser um ano válido (ex: 2021) e não pode ser maior que o ano atual.");
					return;
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Ano de lançamento deve ser um número inteiro.");
				return;
			}

			if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty() || descricao.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios corretamente!");
				return;
			}

			try {
				Categoria categoriaSelecionada = (Categoria) categoriaComboBox.getSelectedItem();
				Livro livro = null;
				livro = new Livro(titulo, autor, isbn, categoriaSelecionada, quantidade, anoLancamento, descricao);
				sistema.adicionarLivro(livro);
				JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
				TelaMenu telaMenu = new TelaMenu(sistema);
				telaMenu.setVisible(true);
				dispose();
			} catch (ValorInvalidoException ex) {
				throw new RuntimeException(ex);
			}
		});
	}
}
