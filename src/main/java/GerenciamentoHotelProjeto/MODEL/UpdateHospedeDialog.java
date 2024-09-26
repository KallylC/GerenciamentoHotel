package GerenciamentoHotelProjeto.MODEL;

import javax.swing.*;
import java.awt.*;

public class UpdateHospedeDialog extends JDialog {
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField enderecoField;
    private JTextField contatoField;
    private boolean okPressed = false;

    public UpdateHospedeDialog(Frame owner, Hospede hospede) {
        super(owner, "Atualizar Informações do Hóspede", true);
        setLayout(new GridLayout(5, 2));
        setSize(400, 300);
        setLocationRelativeTo(owner);

        nomeField = new JTextField(hospede.getNome());
        dataNascimentoField = new JTextField(hospede.getDataNascimento());
        enderecoField = new JTextField(hospede.getEndereco());
        contatoField = new JTextField(hospede.getContato());

        add(new JLabel("Nome:"));
        add(nomeField);
        add(new JLabel("Data de Nascimento:"));
        add(dataNascimentoField);
        add(new JLabel("Endereço:"));
        add(enderecoField);
        add(new JLabel("Contato:"));
        add(contatoField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            okPressed = true;
            setVisible(false);
        });

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel);

        setVisible(true);
    }

    public boolean isOkPressed() {
        return okPressed;
    }

    public String getNome() {
        return nomeField.getText();
    }

    public String getDataNascimento() {
        return dataNascimentoField.getText();
    }

    public String getEndereco() {
        return enderecoField.getText();
    }

    public String getContato() {
        return contatoField.getText();
    }
}
