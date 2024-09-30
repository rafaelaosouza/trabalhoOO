package com.trabalho.oo.biblioteca.gui.livro;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.model.Categoria;
import com.trabalho.oo.biblioteca.model.Livro;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaEdicaoLivro extends JFrame {
	private JTextField tituloField;
	private JTextField autorField;
	private JTextField isbnField;
	private JTextField quantidadeField;
	private JTextArea descricaoArea;
	private JTextField anoLancamentoField;
	private JComboBox<Categoria> categoriaComboBox;
	private JButton salvarButton;

	private Sistema sistema;
	private Livro livro;

	private void carregarCategorias() {
		List<Categoria> categorias = sistema.getCategorias();
		for (Categoria categoria : categorias) {
			categoriaComboBox.addItem(categoria);
		}
	}

	public TelaEdicaoLivro(Sistema sistema, Livro livro) {
		this.sistema = sistema;
		this.livro = livro;

		setTitle("Editar Livro");
		setSize(400, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		JLabel tituloLabel = new JLabel("Título:");
		tituloLabel.setBounds(20, 40, 100, 30);
		add(tituloLabel);

		tituloField = new JTextField(livro.getTitulo());
		tituloField.setBounds(120, 40, 200, 30);
		add(tituloField);

		JLabel autorLabel = new JLabel("Autor:");
		autorLabel.setBounds(20, 80, 100, 30);
		add(autorLabel);

		autorField = new JTextField(livro.getAutor());
		autorField.setBounds(120, 80, 200, 30);
		add(autorField);

		JLabel isbnLabel = new JLabel("ISBN:");
		isbnLabel.setBounds(20, 120, 100, 30);
		add(isbnLabel);

		isbnField = new JTextField(livro.getIsbn());
		isbnField.setBounds(120, 120, 200, 30);
		add(isbnField);

		JLabel categoriaLabel = new JLabel("Categoria:");
		categoriaLabel.setBounds(20, 160, 100, 30);
		add(categoriaLabel);

		categoriaComboBox = new JComboBox<>(sistema.getCategorias().toArray(new Categoria[0]));
		categoriaComboBox.setBounds(120, 160, 200, 30);
		categoriaComboBox.setSelectedItem(livro.getCategoria());
		add(categoriaComboBox);

		JLabel quantidadeLabel = new JLabel("Quantidade:");
		quantidadeLabel.setBounds(20, 200, 100, 30);
		add(quantidadeLabel);

		quantidadeField = new JTextField(String.valueOf(livro.getQuantidadeDisponivel()));
		quantidadeField.setBounds(120, 200, 200, 30);
		add(quantidadeField);

		JLabel anoLancamentoLabel = new JLabel("Ano de Lançamento:");
		anoLancamentoLabel.setBounds(20, 240, 150, 30);
		add(anoLancamentoLabel);

		anoLancamentoField = new JTextField(String.valueOf(livro.getAnoLancamento()));
		anoLancamentoField.setBounds(170, 240, 150, 30);
		add(anoLancamentoField);

		JLabel descricaoLabel = new JLabel("Descrição:");
		descricaoLabel.setBounds(20, 280, 100, 30);
		add(descricaoLabel);

		descricaoArea = new JTextArea(livro.getDescricao());
		descricaoArea.setBounds(120, 280, 200, 80);
		add(descricaoArea);

		salvarButton = new JButton("Salvar");
		salvarButton.setBounds(120, 370, 100, 30);
		add(salvarButton);

		salvarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titulo = tituloField.getText();
				String autor = autorField.getText();
				String isbn = isbnField.getText();
				String descricao = descricaoArea.getText();
				String quantidadeTexto = quantidadeField.getText();
				String anoLancamentoTexto = anoLancamentoField.getText();

				int quantidade = 0;
				try {
					quantidade = Integer.parseInt(quantidadeTexto);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Quantidade deve ser um número inteiro.");
					return;
				}

				int anoLancamento = 0;
				try {
					anoLancamento = Integer.parseInt(anoLancamentoTexto);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Ano de lançamento deve ser um número inteiro.");
					return;
				}

					Categoria categoriaSelecionada = (Categoria) categoriaComboBox.getSelectedItem();
					try {
						livro.setTitulo(titulo);
						livro.setAutor(autor);
						livro.setIsbn(isbn);
						livro.setCategoria(categoriaSelecionada);
						livro.setQuantidadeDisponivel(quantidade);
						livro.setAnoLancamento(anoLancamento);
						livro.setDescricao(descricao);
						JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!");
						TelaListagemLivros telaListagemLivros = new TelaListagemLivros(sistema);
						telaListagemLivros.setVisible(true);
						dispose();
					} catch (ValorInvalidoException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Erro ao atualizar livro!");
					}
				}
		});
	}
}
