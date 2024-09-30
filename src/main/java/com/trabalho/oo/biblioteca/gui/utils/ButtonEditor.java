package com.trabalho.oo.biblioteca.gui.utils;

import com.trabalho.oo.biblioteca.gui.emprestimo.TelaEdicaoEmprestimo;
import com.trabalho.oo.biblioteca.gui.emprestimo.TelaHistoricoEmprestimo;
import com.trabalho.oo.biblioteca.gui.livro.categoria.TelaEdicaoCategoria;
import com.trabalho.oo.biblioteca.gui.livro.categoria.TelaListagemCategorias;
import com.trabalho.oo.biblioteca.gui.usuario.TelaEdicaoUsuario;
import com.trabalho.oo.biblioteca.gui.usuario.TelaListagemUsuarios;
import com.trabalho.oo.biblioteca.model.Emprestimo;
import com.trabalho.oo.biblioteca.service.Sistema;
import com.trabalho.oo.biblioteca.model.Usuario;
import com.trabalho.oo.biblioteca.model.Categoria;
import com.trabalho.oo.biblioteca.utils.NaoPodeSerExcluidoException;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
	protected JButton button;
	private String label;
	private boolean isPushed;
	private JFrame parentFrame;
	private Sistema sistema;

	public ButtonEditor(JCheckBox checkBox, JFrame parentFrame, Sistema sistema) {
		super(checkBox);
		this.parentFrame = parentFrame;
		this.sistema = sistema;
		button = new JButton();
		button.setOpaque(true);
		button.setPreferredSize(new Dimension(50, 30));

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		isPushed = true;
		return button;
	}

	private JTable findTable(Component component) {
		if (component instanceof JTable) {
			return (JTable) component;
		} else if (component instanceof Container) {
			for (Component child : ((Container) component).getComponents()) {
				JTable table = findTable(child);
				if (table != null) {
					return table;
				}
			}
		}
		return null;
	}

	@Override
	public Object getCellEditorValue() {
		if (isPushed) {
			JTable table = findTable(parentFrame);
			int row = table.getSelectedRow();

			if (parentFrame instanceof TelaHistoricoEmprestimo && "Editar".equals(label)) {
				Emprestimo emprestimo = sistema.getEmprestimosDoLeitor(sistema.getUsuarioLogado()).get(row);
				new TelaEdicaoEmprestimo(sistema, emprestimo).setVisible(true);
			}
			if (parentFrame instanceof TelaListagemUsuarios && "Editar".equals(label)) {
				String cpf = (String) table.getValueAt(row, 1);
				Usuario usuario = sistema.getUsuarioPorCpf(cpf);
				new TelaEdicaoUsuario(sistema, usuario).setVisible(true);
			} else if (parentFrame instanceof TelaListagemUsuarios && "Excluir".equals(label)) {
				int confirmacao = JOptionPane.showConfirmDialog(
						parentFrame,
						"Tem certeza que deseja excluir este usuário?",
						"Confirmação de Exclusão",
						JOptionPane.YES_NO_OPTION
				);
				if (confirmacao == JOptionPane.YES_OPTION) {
					try {
						((TelaListagemUsuarios) parentFrame).removerUsuario(row);
						JOptionPane.showMessageDialog(parentFrame, "Usuário excluído com sucesso.");

					} catch (ValorInvalidoException e) {
						JOptionPane.showMessageDialog(parentFrame, e.getMessage());
					}
				}
			} else if (parentFrame instanceof TelaListagemCategorias && "Editar".equals(label)) {
				String categoriaNome = (String) table.getValueAt(row, 0);
				Categoria categoria = sistema.getCategoriaPorNome(categoriaNome);
				new TelaEdicaoCategoria(sistema, categoria).setVisible(true);
			} else if (parentFrame instanceof TelaListagemCategorias && "Excluir".equals(label)) {
				int confirmacao = JOptionPane.showConfirmDialog(
						parentFrame,
						"Tem certeza que deseja excluir esta categoria?",
						"Confirmação de Exclusão",
						JOptionPane.YES_NO_OPTION
				);
				if (confirmacao == JOptionPane.YES_OPTION) {
					try {
						((TelaListagemCategorias) parentFrame).removerCategoria(row);
					} catch (NaoPodeSerExcluidoException e) {
						javax.swing.JOptionPane.showMessageDialog(parentFrame, e.getMessage());
					}
				}
			}
		}
		isPushed = false;
		return label;
	}

	@Override
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}
