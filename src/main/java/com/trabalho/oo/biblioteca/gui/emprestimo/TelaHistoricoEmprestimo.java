package com.trabalho.oo.biblioteca.gui.emprestimo;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.gui.utils.ButtonEditor;
import com.trabalho.oo.biblioteca.gui.utils.ButtonRenderer;
import com.trabalho.oo.biblioteca.model.Emprestimo;
import com.trabalho.oo.biblioteca.model.Usuario;
import com.trabalho.oo.biblioteca.service.Sistema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaHistoricoEmprestimo extends JFrame {
	private Sistema sistema;
	private Usuario leitor;
	private JTable table;
	private DefaultTableModel tableModel;

	public TelaHistoricoEmprestimo(Sistema sistema, Usuario leitor) {
		this.sistema = sistema;
		this.leitor = leitor;

		setTitle("Histórico de Empréstimos - " + leitor.getNome());
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		JButton voltarButton = new JButton("Voltar");
		voltarButton.setBounds(10, 10, 100, 30);
		add(voltarButton);

		JButton atualizarButton = new JButton("Atualizar");
		atualizarButton.setBounds(140, 10, 100, 30);
		add(atualizarButton);

		voltarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TelaMenu(sistema).setVisible(true);
				dispose();
			}
		});

		atualizarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.setRowCount(0);
				carregarHistoricoEmprestimos();
			}
		});

		// Configuração da tabela
		String[] colunas = {"Livro", "Data de Empréstimo", "Data de Devolução", "Status", "Avaliação", "Ação"};
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 50, 560, 300);
		add(scrollPane);

		carregarHistoricoEmprestimos();
	}

	private void carregarHistoricoEmprestimos() {
		List<Emprestimo> emprestimos = sistema.getEmprestimosDoLeitor(leitor);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (Emprestimo emprestimo : emprestimos) {
			String dataEmprestimo = sdf.format(emprestimo.getDataEmprestimo());
			String dataDevolucao = (emprestimo.getDataDevolucao() != null) ? sdf.format(emprestimo.getDataDevolucao()) : "N/A";
			String status = emprestimo.isDevolucaoPendente() ? "Pendente" : "Devolvido";
			int avaliacao = emprestimo.getAvaliacao();

			Object[] rowData = {
					emprestimo.getLivro().getTitulo(),
					dataEmprestimo,
					dataDevolucao,
					status,
					avaliacao == -1 ? "N/A" : avaliacao,
					"Editar"
			};

			tableModel.addRow(rowData);
		}

		table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer(Color.YELLOW, Color.BLACK));
		table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), this, sistema));
	}
}
