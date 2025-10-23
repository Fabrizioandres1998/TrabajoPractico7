import Modelo.Alumno;
import Modelo.Inscripcion;
import Persistencia.AlumnoData;
import Persistencia.InscripcionData;
import modelo.Conexion;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ListarInscripciones extends javax.swing.JInternalFrame {

    private Conexion con = new Conexion("jdbc:mariadb://localhost:3306/universidad", "root", "");
    private InscripcionData inscData = new InscripcionData(con);
    private AlumnoData ad = new AlumnoData(con);
    private DefaultTableModel modelo = new DefaultTableModel();

    public ListarInscripciones() {
        initComponents();
        armarCabecera();
        cargarAlumnos();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Alumno = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); 
        jLabel2.setForeground(new java.awt.Color(153, 0, 153));
        jLabel2.setText("Alumno:");

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); 
        jButton1.setForeground(new java.awt.Color(153, 0, 153));
        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); 
        jLabel1.setForeground(new java.awt.Color(153, 0, 153));
        jLabel1.setText("Listar Inscripciones");

        Alumno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID.Inscripción", "Materia", "Año", "Nota"}
        ));
        jScrollPane1.setViewportView(Alumno);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel2)
                        .addGap(27, 27, 27)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addGap(22, 22, 22)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        Alumno seleccionado = (Alumno) jComboBox1.getSelectedItem();
        if (seleccionado != null) {
            borrarFilasTabla();
            List<Inscripcion> inscripciones = inscData.obtenerInscripcionesPorAlumno(seleccionado.getIdAlumno());
            for (Inscripcion i : inscripciones) {
                modelo.addRow(new Object[]{
                    i.getIdInscripto(),
                    i.getMateria().getNombre(),
                    i.getMateria().getAño(),
                    i.getNota()
                });
            }
        }
    }                                           

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        dispose();    
    }                                         

    private void armarCabecera() {
        modelo.addColumn("ID.Inscripción");
        modelo.addColumn("Materia");
        modelo.addColumn("Año");
        modelo.addColumn("Nota");
        Alumno.setModel(modelo);
    }

    private void cargarAlumnos() {
        List<Alumno> alumnos = ad.listarAlumnos();
        for (Alumno a : alumnos) {
            jComboBox1.addItem(a);
        }
    }

    private void borrarFilasTabla() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }

    private javax.swing.JTable Alumno;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<Alumno> jComboBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
}
