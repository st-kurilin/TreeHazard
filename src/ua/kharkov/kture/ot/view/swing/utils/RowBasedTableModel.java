package ua.kharkov.kture.ot.view.swing.utils;

import com.google.common.base.Preconditions;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import static com.google.common.base.Preconditions.checkPositionIndex;
import static com.google.common.collect.Lists.newLinkedList;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 20.05.11
 */
public class RowBasedTableModel<R> extends AbstractTableModel {
    List<R> data;
    List<ColumnDescriber<R, ?>> columns;

    protected RowBasedTableModel(List<R> data, List<ColumnDescriber<R, ?>> columns) {
        this.data = data;
        this.columns = columns;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        checkPositionIndex(rowIndex, data.size());
        R row = data.get(rowIndex);
        return columns.get(columnIndex).valueAccessor.getValue(row);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        checkPositionIndex(rowIndex, data.size());
        R row = data.get(rowIndex);
        ColumnDescriber<R, Object> describer = (ColumnDescriber<R, Object>) columns.get(columnIndex);
        /*if(describer.valueType.equals(Probability.class)){
            NumberFormatter.
            describer.valueAccessor.setValue(row, Probability.commonForm(Double.parseDouble(value.toString())));
        } */
        describer.valueAccessor.setValue(row, value);
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        checkPositionIndex(columnIndex, columns.size());
        return columns.get(columnIndex).valueType;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).title;
    }

    public static <R> Builder<R> builder() {
        return new Builder<R>();
    }

    public static class Builder<R> {
        List<R> data;
        List<ColumnDescriber<R, ?>> columns = newLinkedList();

        public <V> ColumnDescriber<R, V> editedColumn(String title, Class<V> valueType) {
            ColumnDescriber<R, V> columnDescriber = new ColumnDescriber<R, V>(title, this, valueType);
            columns.add(columnDescriber);
            return columnDescriber;
        }

        public Builder setData(List<R> data) {
            this.data = data;
            return this;
        }

        public RowBasedTableModel<R> build() {
            return new RowBasedTableModel<R>(data, columns);
        }
    }

    public static class ColumnDescriber<R, V> {
        String title;
        Class<V> valueType;
        ValueAccessor<R, V> valueAccessor;
        Builder parent;

        public ColumnDescriber(String title, Builder parent, Class<V> valueType) {
            this.title = title;
            this.parent = parent;
            this.valueType = valueType;
        }


        public ColumnDescriber<R, V> accessor(ValueAccessor<R, V> accessor) {
            this.valueAccessor = accessor;
            return this;
        }

        public Builder end() {
            Preconditions.checkNotNull(valueAccessor);
            return parent;
        }
    }

    public static interface ValueAccessor<R, V> {
        V getValue(R row);

        void setValue(R row, V newValue);
    }
}
