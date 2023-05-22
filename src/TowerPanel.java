import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Stack;

public class TowerPanel extends JPanel {
    private JTable[] tables;
    private Stack<Integer>[] towers;
    private int numDiscos;
    private int movimientos;
    private int minMovimientos;
    private MovimientosListener movimientosListener;

    public TowerPanel() {
        towers = new Stack[3];
        for (int i = 0; i < 3; i++) {
            towers[i] = new Stack<>();
        }
        setLayout(new GridLayout(2, 3));

        tables = new JTable[3];
        for (int i = 0; i < 3; i++) {
            DefaultTableModel model = new DefaultTableModel(10, 1);
            model.setColumnIdentifiers(new Object[]{"Torre " + (i + 1)});
            tables[i] = new JTable(model);
            tables[i].setRowHeight(20);
            tables[i].setEnabled(false);
            tables[i].setShowGrid(false);
            tables[i].setIntercellSpacing(new Dimension(0, 0));
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            tables[i].getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            add(new JScrollPane(tables[i]));
        }

        for (int i = 0; i < 3; i++) {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 2));
            for (int j = 0; j < 2; j++) {
                int targetTower = (i + j + 1) % 3;
                JButton moveButton = new JButton(Integer.toString(targetTower + 1));
                int finalI = i;
                moveButton.addActionListener(e -> moveDisk(finalI, targetTower));
                buttonPanel.add(moveButton);
            }
            add(buttonPanel);
        }
    }

    public void iniciar(int numDiscos) {
        this.numDiscos = numDiscos;
        reiniciar();
        minMovimientos = (int) Math.pow(2, numDiscos) - 1;
        if (movimientosListener != null) {
            movimientosListener.onMinMovimientosChanged(minMovimientos);
        }
    }

    public void reiniciar() {
        for (int i = 0; i < 3; i++) {
            towers[i].clear();
            for (int j = 0; j < 10; j++) {
                tables[i].setValueAt("", j, 0);
            }
        }

        for (int i = numDiscos; i >= 1; i--) {
            int towerIndex = 0;
            towers[towerIndex].push(i);
            int rowIndex = 10 - towers[towerIndex].size();
            tables[towerIndex].setValueAt("=".repeat(i), rowIndex, 0);
        }

        movimientos = 0;
        if (movimientosListener != null) {
            movimientosListener.onMovimientosChanged(movimientos);
        }
    }

    public void resolver() {
        resolverRecursivo(numDiscos, 0, 1, 2);
        resolverRecursivo(numDiscos, 1, 2, 0);
    }

    private void resolverRecursivo(int n, int from, int to, int aux) {
        if (n == 0) {
            return;
        }

        resolverRecursivo(n - 1, from, aux, to);

        int result = JOptionPane.showConfirmDialog(this, "Paso " + (movimientos + 1) + ". ¿Continuar con el paso " + (movimientos + 2) + "?", "Resolver", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            moveDisk(from, to);
            resolverRecursivo(n - 1, aux, to, from);
        }
    }


    private void moveDisk(int from, int to) {
        if (towers[from].isEmpty()) {
            return;
        }
        if (!towers[to].isEmpty() && towers[from].peek() > towers[to].peek()) {
            return;
        }
        int disco = towers[from].pop();
        towers[to].push(disco);
        movimientos++;
        if (movimientosListener != null) {
            movimientosListener.onMovimientosChanged(movimientos);
        }
        tables[from].setValueAt("", 10 - towers[from].size() - 1, 0);
        tables[to].setValueAt("=".repeat(disco), 10 - towers[to].size(), 0);
    }

    public void setMovimientosListener(MovimientosListener listener) {
        this.movimientosListener = listener;
    }

    public interface MovimientosListener {
        void onMovimientosChanged(int movimientos);

        void onMinMovimientosChanged(int minMovimientos);
    }
}









