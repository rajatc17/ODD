/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package odd;

import java.awt.Container;
import java.awt.Toolkit;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;

/**
 *
 * @author rajat
 */
public class CUST_REQ extends javax.swing.JFrame implements Connectivity{

    /**
     * Creates new form CUST_REQ
     */
    public JTextField createFilteredField() {
        JTextField field = new JTextField();
        AbstractDocument document = (AbstractDocument) field.getDocument();
        final int maxCharacters = 10;
        document.setDocumentFilter(new DocumentFilter() {
            public void replace(DocumentFilter.FilterBypass fb, int offs, int length,
                    String str, AttributeSet a) throws BadLocationException {

                String text = fb.getDocument().getText(0,
                        fb.getDocument().getLength());
                text += str;
                if ((fb.getDocument().getLength() + str.length() - length) <= maxCharacters
                        && text.matches("^[0-9]+[.]?[0-9]{0,1}$")) {
                    super.replace(fb, offs, length, str, a);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            public void insertString(DocumentFilter.FilterBypass fb, int offs, String str,
                    AttributeSet a) throws BadLocationException {

                String text = fb.getDocument().getText(0,
                        fb.getDocument().getLength());
                text += str;
                if ((fb.getDocument().getLength() + str.length()) <= maxCharacters
                        && text.matches("^[0-9]+[.]?[0-9]{0,1}$")) {
                    super.insertString(fb, offs, str, a);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        return field;
    }
    java.util.ArrayList<String> coid = new ArrayList<>();
         
    Map<String,Integer> taken = new HashMap<String,Integer>();
          
    private void initList()
    {
        for(int i=1;i<=100;i++)
        {
            coid.add("CO"+i);
        }
        this.coid=coid;
        
        for (int i = 0; i < this.coid.size();i++) { 		      
	          System.out.println(this.coid.get(i)+" "); 		
	} 
    }
    private void add_to_map()
    {
        
        try
        { 
            Connection cn=Connectivity.getConnection();
            PreparedStatement ps=cn.prepareStatement("select ConnID from connection");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                taken.put(rs.getString("ConnID"),1);
            }
        }
    catch(Exception e)
    {   
        JOptionPane.showMessageDialog(this,e);
    }
    }

     private void add_to_conn()
    {
         add_to_map();
         String co_id=null;
         for(String str:this.coid)
         {
             if(!taken.containsKey(str) || taken.isEmpty())
             {
                co_id=str;
                break;
             }
         }
         
         try
         {
            Connection con=Connectivity.getConnection();
            String sql ="INSERT INTO connection(ConnID,CID,WID,WPID,Request,Period,Start,Special,Status) VALUES(?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,co_id);
            ps.setString(2,this.cid);
            ps.setString(3,this.wid);
            ps.setString(4,this.wpid);
            ps.setString(5,SPACE.getText());
            ps.setString(6,TIME.getText()+" "+TCB.getSelectedItem().toString());
            ps.setDate(7, new java.sql.Date(DATE1.getDate().getTime()));
            ps.setString(8,SP.getText());
            ps.setString(9,"AWAITING");
            ps.execute();
            JOptionPane.showMessageDialog(this,"YOUR REQUEST IS REGISTERED WITH REQUEST ID:#"+co_id+"\nPLEASE AWAIT FOR THE REPLY.");
            this.dispose();
            pre.setVisible(true);
            SPACE.setText("");
            TIME.setText("");
            DATE1.setDate(null);
                
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,e);
        }
    }
    public CUST_REQ(String wid,String cid,String name,Container pre,String wpid) {
        initComponents();
        initList();
        DATE1.setMinSelectableDate(new Date());
        this.wid = wid;
        this.cid = cid;
        this.name = name;
        this.pre = pre;
        this.wpid = wpid;
        
        try{
            Connection cn=Connectivity.getConnection();
            PreparedStatement ps=null;
            ResultSet rs=null;
            ps=cn.prepareStatement("select * from warehouse where WID='"+this.wid+"';");
            rs=ps.executeQuery();
            rs.next();
            
            String dist = rs.getString("District");
            String area = rs.getString("PArea");
            String rate = rs.getString("RentSQF");
            String add = rs.getString("Address");
            
            this.area = Float.parseFloat(area);
            
            A.setText(area+" Square Feet");
            R.setText(rate+" ₹ per Sq. Feet");
            ADD.setText(add);
            
            WAR.setText(area+" Sq. Feet SPACE IN "+dist+" WPID:"+wpid+" WID:"+wid);
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,e);
        }
        
        CUS.setText("REQUEST INITIATION FOR "+name+" CID: "+cid);
        
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
        jPanel2 = new javax.swing.JPanel();
        CUS = new javax.swing.JLabel();
        WAR = new javax.swing.JLabel();
        A = new javax.swing.JLabel();
        R = new javax.swing.JLabel();
        ADD = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        TIME = new IntegerField();
        jLabel5 = new javax.swing.JLabel();
        TCB = new javax.swing.JComboBox<>();
        LIM = new javax.swing.JLabel();
        DATE1 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SP = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        SPACE = this.createFilteredField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        CUS.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        CUS.setForeground(new java.awt.Color(255, 255, 255));
        CUS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        WAR.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        WAR.setForeground(new java.awt.Color(255, 255, 255));
        WAR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CUS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(WAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CUS, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(WAR, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        A.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        A.setText("AREA");

        R.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        R.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R.setText("RENT");

        ADD.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        ADD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ADD.setText("ADD");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TOTAL AREA");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("RENT RATE");
        jLabel2.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(51, 51, 51));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("REQUIRED SPACE");
        jLabel4.setOpaque(true);

        TIME.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N

        jLabel5.setBackground(new java.awt.Color(51, 51, 51));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("EXPECTED STORAGE PERIOD");
        jLabel5.setOpaque(true);

        TCB.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        TCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAYS", "WEEKS", "MONTHS", "YEARS" }));
        TCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCBActionPerformed(evt);
            }
        });

        LIM.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        LIM.setForeground(new java.awt.Color(255, 0, 0));
        LIM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LIM.setText("Maximum 1000 Days");

        DATE1.setDateFormatString("dd-MMM-yyyy");
        DATE1.setFocusTraversalPolicyProvider(true);
        DATE1.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("EXPECTED START DATE");
        jLabel6.setOpaque(true);

        jLabel7.setBackground(new java.awt.Color(51, 51, 51));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("SPECIAL COMMENTS");
        jLabel7.setOpaque(true);

        SP.setColumns(20);
        SP.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        SP.setRows(5);
        jScrollPane1.setViewportView(SP);

        jButton1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        jButton1.setText("CONFIRM");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        jButton2.setText("GO BACK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        SPACE.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(A, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(R, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(239, 239, 239))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(ADD, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(304, 304, 304)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(TIME, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(TCB, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(SPACE, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(DATE1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LIM, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(A)
                    .addComponent(R))
                .addGap(18, 18, 18)
                .addComponent(ADD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(LIM, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(SPACE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TIME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TCB, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(15, 15, 15)
                        .addComponent(DATE1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1016, 859));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCBActionPerformed
        if(TCB.getSelectedIndex()==0)
            LIM.setText("Maximum "+1000+" Days");
        if(TCB.getSelectedIndex()==1)
            LIM.setText("Maximum "+200+" Weeks");
        if(TCB.getSelectedIndex()==2)
            LIM.setText("Maximum "+25+" Months");
        if(TCB.getSelectedIndex()==3)
            LIM.setText("Maximum "+3+" Years");
    }//GEN-LAST:event_TCBActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       this.dispose();
       pre.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       String space = SPACE.getText();
       String time = TIME.getText();
       System.out.println(TCB.getSelectedIndex()+" "+Integer.parseInt(time));
       if(space.equals("") || time.equals("") || DATE1.getDate()==null){
           JOptionPane.showMessageDialog(this,"PLEASE ENTER ALL FIELDS");
           SPACE.setText("");
           TIME.setText("");
           DATE1.setDate(null);
       }
       else if(Float.parseFloat(space) > this.area){
           JOptionPane.showMessageDialog(this,"REQUESTED AREA SHOULD BE WITHIN WAREHOUSE LIMITS!");
           SPACE.setText("");
           TIME.setText("");
           DATE1.setDate(null);
       }
       else if(TCB.getSelectedIndex()==0 && Integer.parseInt(time)>1000){
           JOptionPane.showMessageDialog(this,"MAX ALLOWED DAYS = 1000!");
           SPACE.setText("");
           TIME.setText("");
           DATE1.setDate(null);
       }
       else if(TCB.getSelectedIndex()==1 && Integer.parseInt(time)>200){
           JOptionPane.showMessageDialog(this,"MAX ALLOWED WEEKS = 200!");
           SPACE.setText("");
           TIME.setText("");
           DATE1.setDate(null);
       }
       else if(TCB.getSelectedIndex()==2 && Integer.parseInt(time)>25){
           JOptionPane.showMessageDialog(this,"MAX ALLOWED MONTHS = 25!");
           SPACE.setText("");
           TIME.setText("");
           DATE1.setDate(null);
       }
       else if(TCB.getSelectedIndex()==3 && Integer.parseInt(time)>3){
           JOptionPane.showMessageDialog(this,"MAX ALLOWED YEARS = 3!");
           SPACE.setText("");
           TIME.setText("");
           DATE1.setDate(null);
       }
       else{
           add_to_conn();
       }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(CUST_REQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CUST_REQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CUST_REQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CUST_REQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CUST_REQ(wid,cid,name,pre,wpid).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel A;
    private javax.swing.JLabel ADD;
    private javax.swing.JLabel CUS;
    private com.toedter.calendar.JDateChooser DATE1;
    private javax.swing.JLabel LIM;
    private javax.swing.JLabel R;
    private javax.swing.JTextArea SP;
    private javax.swing.JTextField SPACE;
    private javax.swing.JComboBox<String> TCB;
    private javax.swing.JTextField TIME;
    private javax.swing.JLabel WAR;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    // End of variables declaration//GEN-END:variables
    private static String wid;
    private static String cid;
    private static String name;
    private static Container pre;
    private static String wpid;
    private static float area;
}

