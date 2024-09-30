package com.trabalho.oo.biblioteca.gui.livro;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.model.Categoria;
import com.trabalho.oo.biblioteca.model.Livro;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.utils.NaoPodeSerExcluidoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaListagemLivros extends JFrame {
	private Sistema sistema;
	private JTable tabelaLivros;
	private DefaultTableModel modeloTabela;
	private JTextField campoPesquisa;
	private JComboBox<String> comboCategorias;

	public TelaListagemLivros(Sistema sistema) {
		this.sistema = sistema;

		setTitle("Listagem de Livros");
		setSize(600, 400);
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

		JLabel pesquisaLabel = new JLabel("Pesquisar:");
		pesquisaLabel.setBounds(20, 40, 100, 30);
		add(pesquisaLabel);

		campoPesquisa = new JTextField();
		campoPesquisa.setBounds(100, 40, 200, 30);
		add(campoPesquisa);

		JLabel categoriaLabel = new JLabel("Categoria:");
		categoriaLabel.setBounds(320, 40, 100, 30);
		add(categoriaLabel);

		comboCategorias = new JComboBox<>();
		comboCategorias.setBounds(400, 40, 150, 30);
		add(comboCategorias);

		JButton botaoPesquisar = new JButton("Pesquisar");
		botaoPesquisar.setBounds(450, 80, 120, 30);
		botaoPesquisar.setForeground(Color.WHITE);
		botaoPesquisar.setBackground(Color.BLUE);
		add(botaoPesquisar);

		JButton botaoDetalhes = new JButton("Ver Detalhes");
		botaoDetalhes.setBounds(20, 330, 120, 30);
		botaoDetalhes.setForeground(Color.WHITE);
		botaoDetalhes.setBackground(Color.GRAY);
		add(botaoDetalhes);

		JButton botaoExcluir = new JButton("Excluir");
		botaoExcluir.setBounds(160, 330, 120, 30);
		botaoExcluir.setForeground(Color.WHITE);
		botaoExcluir.setBackground(Color.RED);
		add(botaoExcluir);

		JButton botaoEditar = new JButton("Editar");
		botaoEditar.setBounds(300, 330, 120, 30);
		botaoEditar.setForeground(Color.WHITE);
		botaoEditar.setBackground(Color.ORANGE);
		add(botaoEditar);

		modeloTabela = new DefaultTableModel();
		modeloTabela.addColumn("Título");
		modeloTabela.addColumn("Autor");
		modeloTabela.addColumn("Categoria");

		tabelaLivros = new JTable(modeloTabela);
		JScrollPane scrollPane = new JScrollPane(tabelaLivros);
		scrollPane.setBounds(20, 110, 550, 250);
		add(scrollPane);

		sistema.carregarCategorias();
		preencherComboCategorias();

		botaoPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarLivros(campoPesquisa.getText(), (String) comboCategorias.getSelectedItem());
			}
		});

		botaoDetalhes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaLivros.getSelectedRow();
				if (linhaSelecionada != -1) {
					Livro livroSelecionado = sistema.getLivros().get(linhaSelecionada);
					new TelaDetalhesLivro(livroSelecionado).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um livro para ver os detalhes.");
				}
			}
		});

		if(!sistema.getUsuarioLogado().isAdmin()) {
			botaoExcluir.setVisible(false);
			botaoEditar.setVisible(false);
		}
		botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaLivros.getSelectedRow();
				if (linhaSelecionada != -1) {
					Livro livroSelecionado = sistema.getLivros().get(linhaSelecionada);
					int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o livro " + livroSelecionado.getTitulo() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						try {
							sistema.excluirLivro(livroSelecionado);
							listarLivros("", "Todas");
							JOptionPane.showMessageDialog(null, "Livro excluído com sucesso.");
						} catch (NaoPodeSerExcluidoException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um livro para excluir.");
				}
			}
		});

		botaoEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaLivros.getSelectedRow();
				if (linhaSelecionada != -1) {
					Livro livroSelecionado = sistema.getLivros().get(linhaSelecionada);
					new TelaEdicaoLivro(sistema, livroSelecionado).setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um livro para editar.");
				}
			}
		});

		listarLivros("", "Todas");
	}

	private void preencherComboCategorias() {
		List<Categoria> categorias = sistema.getCategorias();

		this.comboCategorias.addItem("Todas");
		for (Categoria categoria : categorias) {
			comboCategorias.addItem(categoria.getNome());
		}
	}

	public void listarLivros(String filtro, String categoriaFiltro) {
		modeloTabela.setRowCount(0);

		List<Livro> livros = sistema.getLivros();

		for (Livro livro : livros) {
			boolean incluiFiltro = livro.getTitulo().toLowerCase().contains(filtro.toLowerCase());
			boolean incluiCategoria = categoriaFiltro == null || categoriaFiltro.equals("Todas") ||
					livro.getCategoria().getNome().equals(categoriaFiltro);

			if (incluiFiltro && incluiCategoria) {
				modeloTabela.addRow(new Object[]{livro.getTitulo(), livro.getAutor(), livro.getCategoria().getNome()});
			}
		}
	}
}
