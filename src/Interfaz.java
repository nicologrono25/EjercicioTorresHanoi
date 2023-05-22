import javax.swing.*;
import java.awt.*;

public class Interfaz extends JFrame {
    private TowerPanel towerPanel;
    private JComboBox comboBox1;
    private JButton I;
    private JTextField textField1;
    private JTextField textField2;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a3Button1;
    private JButton a1Button;
    private JButton a1Button1;
    private JButton a2Button1;

    public Interfaz() {
        setTitle("Torres de Hanoi");
        setLayout(new BorderLayout());

        towerPanel = new TowerPanel();
        add(towerPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JComboBox<Integer> numDiscosComboBox = new JComboBox<>();
        for (int i = 3; i <= 10; i++) {
            numDiscosComboBox.addItem(i);
        }
        controlPanel.add(new JLabel("Número de discos:"));
        controlPanel.add(numDiscosComboBox);

        JButton iniciarButton = new JButton("Iniciar");
        iniciarButton.addActionListener(e -> towerPanel.iniciar((int) numDiscosComboBox.getSelectedItem()));
        controlPanel.add(iniciarButton);

        JButton reiniciarButton = new JButton("Reiniciar");
        reiniciarButton.addActionListener(e -> towerPanel.reiniciar());
        controlPanel.add(reiniciarButton);

        JButton resolverButton = new JButton("Resolver");
        resolverButton.addActionListener(e -> towerPanel.resolver());
        controlPanel.add(resolverButton);

        JLabel minMovimientosLabel = new JLabel("Mínimo de movimientos: 0");
        controlPanel.add(minMovimientosLabel);

        JLabel movimientosLabel = new JLabel("Movimientos: 0");
        controlPanel.add(movimientosLabel);

        add(controlPanel, BorderLayout.NORTH);

        towerPanel.setMovimientosListener(new TowerPanel.MovimientosListener() {
            @Override
            public void onMovimientosChanged(int movimientos) {
                movimientosLabel.setText("Movimientos: " + movimientos);
            }

            @Override
            public void onMinMovimientosChanged(int minMovimientos) {
                minMovimientosLabel.setText("Mínimo de movimientos: " + minMovimientos);
            }
        });
    }
}









