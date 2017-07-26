/*
 * Created By: Duaa S
 * Creation Date: 18/Nov/2014
 * Description: Home Page for the student, 
 * where he/she can Sync data from FTP and access learning material
*/
package lms;

/*Imports*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


/**
 *
 * @author dell
 */
public class HomePage extends javax.swing.JFrame {

   String str_Driver;
   String str_ConnectionStr;
   String str_Username;
   String str_Password;
   Connection new_Connection;
   String str_FtpServer;
   int int_Port;
   String str_ftpUsername;
   String str_ftpPassword;;
   String str_PathFile;
   PreparedStatement ps;
   ResultSet rs;
   
   
    public HomePage() { 
            initComponents();
            this.setContentPane(jPanel1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblStartCourse = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuSync = new javax.swing.JMenu();
        menuLogOut = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblStartCourse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lms/CourseBG.jpg"))); // NOI18N
        lblStartCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStartCourseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStartCourse)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStartCourse, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
        );

        menuSync.setText("SYNC");
        menuSync.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuSyncMouseClicked(evt);
            }
        });
        menuSync.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSyncActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuSync);

        menuLogOut.setText("LOG OUT");
        menuLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuLogOutMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuLogOut);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblStartCourseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStartCourseMouseClicked
        boolean result=getCourses();
        if (result==false)
        {
            JOptionPane.showMessageDialog(lblStartCourse, "Please Sync data first.","Sync Required" ,JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            this.setVisible(false);
            new List_of_Courses().setVisible(true);
        }
    }//GEN-LAST:event_lblStartCourseMouseClicked

    /*Get files from the database*/
    private boolean getCourses()
    {
        int flag=0;
        try
        {
            Class.forName(str_Driver);
            new_Connection = DriverManager.getConnection(str_ConnectionStr, str_Username, str_Password);
            ps=new_Connection.prepareStatement("SELECT * FROM COURSE_MASTER");
            rs = ps.executeQuery();
            while(rs.next())
            {
                flag=1;                             
            } 
            rs.close();
            ps.close();
            new_Connection.close();
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Exception: " + e);
        }
        if(flag==0) {
            return true;
        }
        else {
            return false;
        }       
    }
    
    /*Access values from the Properties File */
    public void getPropValues() throws IOException {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "Connection_Props.properties";
            inputStream = getClass().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file: '" + propFileName + "' not found in the classpath");
            }
            str_Driver=prop.getProperty("db_driver");
            str_ConnectionStr=prop.getProperty("connection_str");
            str_Username=prop.getProperty("username");
            str_Password=prop.getProperty("password");
            str_FtpServer=prop.getProperty("ftp_server");
            int_Port=Integer.parseInt(prop.getProperty("port"));
            str_ftpUsername=prop.getProperty("ftp_username");
            str_ftpPassword=prop.getProperty("ftp_password");
            str_PathFile=prop.getProperty("path_file");
            inputStream.close();
           
	} catch (IOException | NumberFormatException e) {
            System.out.println("Exception: " + e);
	} 		
	}
    
    private void menuSyncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSyncActionPerformed
       
    }//GEN-LAST:event_menuSyncActionPerformed

    private void menuLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLogOutMouseClicked
        this.setVisible(false);
        new Login().setVisible(true);
    }//GEN-LAST:event_menuLogOutMouseClicked

    private void menuSyncMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSyncMouseClicked
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            getPropValues();
            connectFTP();
        } catch (IOException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuSyncMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage().setVisible(true);
               
            }
        });
    }
    
    /*Connect to FTP to download files*/
    public void connectFTP() throws ClassNotFoundException, SQLException
    {
        JFrame frmSync=new JFrame("FTP Download");
        JLabel lblProgStatus=new JLabel("Downloading Files.... ");
        frmSync.add(lblProgStatus);
        frmSync.setVisible(true);
        frmSync.setSize(200, 200);
        //frmSync.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File pathFile=new File(str_PathFile);
        if(!pathFile.exists())
        {
            try {
               boolean result=pathFile.mkdirs();
               pathFile.setWritable(true);
               
               System.out.println(result);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } 
        }
        
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(str_FtpServer, int_Port);
            ftpClient.login(str_ftpUsername, str_ftpPassword);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            deleteCourses();        
            
            FTPFile[] ftpFiles = ftpClient.listFiles();
            OutputStream output;
          
            if (ftpFiles != null && ftpFiles.length > 0) 
            {
             
                //loop through files
                for (FTPFile file : ftpFiles) {
                    if (!file.isFile()) {
                        continue;
                    }
                    output = new FileOutputStream(str_PathFile  + file.getName());
                    //get the file from the remote system
                    ftpClient.retrieveFile(file.getName(), output);
                    saveCourses(file.getName(), str_PathFile+file.getName());
                    output.close();
                    System.out.println("File ="+file.getName());
                   
                }                
             lblProgStatus.setText("Sync Complete.");
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }      
    }  
  
    /*Delete old files from the database*/
    private void deleteCourses()
    {
        try
        {
            Class.forName(str_Driver);
            new_Connection = DriverManager.getConnection(str_ConnectionStr,str_Username, str_Password);
            ps=new_Connection.prepareStatement("DELETE FROM COURSE_MASTER");
            ps.executeUpdate();
            ps.close();
            new_Connection.close();
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            System.out.println(ex);
        }
           
    }
    
    /*Save files in to the database*/
    private void saveCourses(String fileName,String filePath)
    {
        try
        {
            Class.forName(str_Driver);
            new_Connection = DriverManager.getConnection(str_ConnectionStr, str_Username, str_Password);
            ps = new_Connection.prepareStatement("INSERT INTO COURSE_MASTER VALUES(?,?)");
            ps.setString(1, fileName);
            ps.setString(2, filePath);
            ps.executeUpdate();
            ps.close();
            new_Connection.close();           
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            System.out.println(ex);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblStartCourse;
    private javax.swing.JMenu menuLogOut;
    private javax.swing.JMenu menuSync;
    // End of variables declaration//GEN-END:variables
}
