/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.miempresa.petshop;

/**
 *
 * @author terra
 */


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetShop extends JFrame {
    private JTextField codigoField;
    private JTextField tipoField;
    private JTextField edadField; 
    private JTextField nombreField;
    private JTextField colorField;

    public PetShop() {
        setTitle("Formulario de Mascotas - PetShop");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel petPanel = new JPanel();
        add(petPanel);
        placePetComponents(petPanel);

        setVisible(true);
    }

    private void placePetComponents(JPanel petPanel) {
        petPanel.setLayout(null);

        JLabel codigoLabel = new JLabel("Código:");
        codigoLabel.setBounds(10, 20, 80, 25);
        petPanel.add(codigoLabel);

        codigoField = new JTextField(20);
        codigoField.setBounds(150, 20, 200, 25);
        petPanel.add(codigoField);

        JLabel tipoLabel = new JLabel("Tipo de Mascota:");
        tipoLabel.setBounds(10, 60, 120, 25);
        petPanel.add(tipoLabel);

        tipoField = new JTextField(20);
        tipoField.setBounds(150, 60, 200, 25);
        petPanel.add(tipoField);

        JLabel edadLabel = new JLabel("Edad:");
        edadLabel.setBounds(10, 100, 80, 25);
        petPanel.add(edadLabel);

        edadField = new JTextField(20);  // Permitir letras y números
        edadField.setBounds(150, 100, 200, 25);
        petPanel.add(edadField);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 140, 80, 25);
        petPanel.add(nombreLabel);

        nombreField = new JTextField(20);
        nombreField.setBounds(150, 140, 200, 25);
        petPanel.add(nombreField);

        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setBounds(10, 180, 80, 25);
        petPanel.add(colorLabel);

        colorField = new JTextField(20);
        colorField.setBounds(150, 180, 200, 25);
        petPanel.add(colorField);

        JButton altaButton = new JButton("Alta");
        altaButton.setBounds(30, 230, 100, 25);
        petPanel.add(altaButton);
        altaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarMascota();
            }
        });

        JButton bajaButton = new JButton("Baja");
        bajaButton.setBounds(140, 230, 100, 25);
        petPanel.add(bajaButton);
        bajaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarMascota();
            }
        });

        JButton cambioButton = new JButton("Cambio");
        cambioButton.setBounds(250, 230, 100, 25);
        petPanel.add(cambioButton);
        cambioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarMascota();
            }
        });

        JButton cargarButton = new JButton("Cargar");
        cargarButton.setBounds(150, 270, 100, 25);
        petPanel.add(cargarButton);
        cargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMascota();
            }
        });
    }

    private void guardarMascota() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MascotasUMG;encrypt=true;trustServerCertificate=true;", "sa", "admin123")) {
            String sql = "INSERT INTO Mascotas (codigo, tipo, edad, nombre, color) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, codigoField.getText());
            pstmt.setString(2, tipoField.getText());
            pstmt.setString(3, edadField.getText());  // Edad como String
            pstmt.setString(4, nombreField.getText());
            pstmt.setString(5, colorField.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Mascota guardada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la mascota: " + e.getMessage());
        }
    }

    private void eliminarMascota() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MascotasUMG;encrypt=true;trustServerCertificate=true;", "sa", "admin123")) {
            String sql = "DELETE FROM Mascotas WHERE codigo = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, codigoField.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Mascota eliminada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar la mascota: " + e.getMessage());
        }
    }

    private void actualizarMascota() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MascotasUMG;encrypt=true;trustServerCertificate=true;", "sa", "admin123")) {
            String sql = "UPDATE Mascotas SET tipo = ?, edad = ?, nombre = ?, color = ? WHERE codigo = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tipoField.getText());
            pstmt.setString(2, edadField.getText());  // Edad como String
            pstmt.setString(3, nombreField.getText());
            pstmt.setString(4, colorField.getText());
            pstmt.setString(5, codigoField.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Mascota actualizada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la mascota: " + e.getMessage());
        }
    }

    private void cargarMascota() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MascotasUMG;encrypt=true;trustServerCertificate=true;", "sa", "admin123")) {
            String sql = "SELECT * FROM Mascotas WHERE codigo = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, codigoField.getText());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tipoField.setText(rs.getString("tipo"));
                edadField.setText(rs.getString("edad"));  // Edad como String
                nombreField.setText(rs.getString("nombre"));
                colorField.setText(rs.getString("color"));
            } else {
                JOptionPane.showMessageDialog(null, "Mascota no encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar la mascota: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PetShop());
    }
}

