package com.trabalho.oo.biblioteca.gui.usuario;

import com.trabalho.oo.biblioteca.gui.TelaMenu;
import com.trabalho.oo.biblioteca.gui.utils.ButtonEditor;
import com.trabalho.oo.biblioteca.gui.utils.ButtonRenderer;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.model.Usuario;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaListagemUsuarios extends JFrame {
	private JTable tabelaUsuarios;
	private JButton voltarButton;
	private JButton atualizarButton;
	private Sistema sistema;
	private Usuario usuarioLogado;

	public TelaListagemUsuarios(Sistema sistema, Usuario usuarioLogado) {
		this.sistema = sistema;
		this.usuarioLogado = usuarioLogado;

		setTitle("Listagem de Usuários");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		voltarButton = new JButton("Voltar");
		voltarButton.setBounds(10, 10, 150, 30);
		add(voltarButton);

		atualizarButton = new JButton("Atualizar");
		atualizarButton.setBounds(170, 10, 150, 30);
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
				((DefaultTableModel) tabelaUsuarios.getModel()).setRowCount(0);
				carregarUsuarios((DefaultTableModel) tabelaUsuarios.getModel());
			}
		});

		String[] colunas = {"Nome", "CPF", "Tipo", "Editar", "Excluir"};

		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3 || column == 4;
			}
		};

		tabelaUsuarios = new JTable(modeloTabela);
		JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
		scrollPane.setBounds(10, 50, 560, 300);
		add(scrollPane);

		carregarUsuarios(modeloTabela);

		tabelaUsuarios.getColumn("Editar").setCellRenderer(new ButtonRenderer(Color.YELLOW, Color.BLACK));
		tabelaUsuarios.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), this, sistema));

		tabelaUsuarios.getColumn("Excluir").setCellRenderer(new ButtonRenderer(Color.RED, Color.WHITE));
		tabelaUsuarios.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), this, sistema));
	}

	private void carregarUsuarios(DefaultTableModel modeloTabela) {
		List<Usuario> usuarios = sistema.getUsuarios();
		for (Usuario usuario : usuarios) {
			String tipo = usuario.isAdmin() ? "Administrador" : "Leitor";
			modeloTabela.addRow(new Object[]{
					usuario.getNome(),
					usuario.getCpf(),
					tipo,
					"Editar",
					"Excluir"
			});
		}
	}

	public void removerUsuario(int linhaSelecionada) throws ValorInvalidoException {
		String cpf = (String) tabelaUsuarios.getValueAt(linhaSelecionada, 1);
		if (cpf.equals(usuarioLogado.getCpf())) {
			throw new ValorInvalidoException("Você não pode remover seu próprio usuário!");
		} else {
			sistema.removerUsuario(cpf);
			((DefaultTableModel) tabelaUsuarios.getModel()).removeRow(linhaSelecionada);
		}
	}
}
