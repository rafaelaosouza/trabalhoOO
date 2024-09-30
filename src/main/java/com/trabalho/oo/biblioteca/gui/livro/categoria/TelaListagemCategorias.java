package com.trabalho.oo.biblioteca.gui.livro.categoria;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.gui.utils.ButtonEditor;
import com.trabalho.oo.biblioteca.gui.utils.ButtonRenderer;
import com.trabalho.oo.biblioteca.model.Categoria;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.utils.NaoPodeSerExcluidoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaListagemCategorias extends JFrame {
	private JTable table;
	private DefaultTableModel tableModel;
	private Sistema sistema;

	public TelaListagemCategorias(Sistema sistema) {
		this.sistema = sistema;
		setTitle("Listagem de Categorias");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton voltarMenuButton = new JButton("Voltar");
		buttonPanel.add(voltarMenuButton);

		JButton atualizarButton = new JButton("Atualizar");
		buttonPanel.add(atualizarButton);

		add(buttonPanel, BorderLayout.NORTH);

		voltarMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TelaMenu(sistema).setVisible(true);
				dispose();
			}
		});

		atualizarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				carregarCategorias();
			}
		});

		String[] colunas = {"Nome", "Editar", "Excluir"};
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);

		table.getColumn("Editar").setCellRenderer(new ButtonRenderer(Color.YELLOW, Color.BLACK));
		table.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), this, sistema));
		table.getColumn("Excluir").setCellRenderer(new ButtonRenderer(Color.RED, Color.WHITE));
		table.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), this, sistema));

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		carregarCategorias();

		setVisible(true);
	}

	private void carregarCategorias() {
		tableModel.setRowCount(0);
		List<Categoria> categorias = sistema.getCategorias();
		for (Categoria categoria : categorias) {
			Object[] linha = {categoria.getNome(), "Editar", "Excluir"};
			tableModel.addRow(linha);
		}
	}

	public void removerCategoria(int row) throws NaoPodeSerExcluidoException {
		String nome = (String) tableModel.getValueAt(row, 0);
		sistema.removerCategoriaPorNome(nome);
		tableModel.removeRow(row);
		JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso.");
	}
}
