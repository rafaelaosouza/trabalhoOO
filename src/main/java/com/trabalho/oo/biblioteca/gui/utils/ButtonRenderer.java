package com.trabalho.oo.biblioteca.gui.utils;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {

	public ButtonRenderer(Color backGraundcolor, Color foreGraundcolor) {
		setOpaque(true);
		setBackground(backGraundcolor);
		setForeground(foreGraundcolor);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setText((value == null) ? "" : value.toString());
		return this;
	}
}
