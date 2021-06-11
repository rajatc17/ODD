/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package odd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author rajat
 */
public class CUST_DASHBOARD extends javax.swing.JFrame {

    /**
     * Creates new form CUST_DASHBOARD
     */
    FlowLayout layout = new FlowLayout();
    Dimension d = new Dimension(900,330);
    
    public JTextField createFilteredField() {
        JTextField field = new JTextField();
        AbstractDocument document = (AbstractDocument) field.getDocument();
        final int maxCharacters = 10;
        document.setDocumentFilter(new DocumentFilter() {
            public void replace(FilterBypass fb, int offs, int length,
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

            public void insertString(FilterBypass fb, int offs, String str,
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
    private void setStateList(){
        try{
            Connection con=Connectivity.getConnection();
            PreparedStatement ps=con.prepareStatement("SELECT * FROM all_states;");
            ResultSet rs=ps.executeQuery();
            
            while(rs.next())
            {
                StateCB.addItem(rs.getString("state_name")+" "+rs.getString("state_code"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,e);
        }
            
    }
    private void upd(JPanel panel)
    {
        panel.repaint();
        panel.revalidate();
    }
    private void perform(Object ob){
        JButton b = (JButton)ob;
        JPanel parent = (JPanel)b.getParent();
        String id = parent.getToolTipText();
        try{
            Connection cn=Connectivity.getConnection();
            PreparedStatement ps=null;
            ResultSet rs=null;
            ps=cn.prepareStatement("select * from warehouse where WID='"+id+"';");
            rs=ps.executeQuery();
            rs.next();
            String dist = rs.getString("District");
            String add = rs.getString("Address");
            String wpid = rs.getString("WPID");
            
            ps=cn.prepareStatement("select * from all_states where state_code="+rs.getString("State_Code")+";");
            rs=ps.executeQuery();
            rs.next();
            
            //JOptionPane.showMessageDialog(this,"Warehouse ID:"+id+"\nDistrict: "+dist+"\nState: "+rs.getString("state_name")+"\nAddress: "+add);
            
            int ch = JOptionPane.showConfirmDialog(this, "Warehouse ID:"+id+"\nDistrict: "+dist+"\nState: "+rs.getString("state_name")+"\nAddress: "+add+"\nDO YOU WANT TO CONTINUE?");
            
            if(ch==JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(this,"REQUESTING\n Warehouse ID:"+id+"\nDistrict: "+dist+"\nState: "+rs.getString("state_name")+"\nAddress: "+add);
                
                CUST_REQ fr = new CUST_REQ(id,this.id,name,this,wpid);
                fr.setVisible(true);
                this.setVisible(false);
            }
            else if(ch==JOptionPane.NO_OPTION){
                
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,e);
        }
        
 
    }
    private void populate(JPanel parent,int def,String scode,String city)
    {
        layout.setAlignment(FlowLayout.LEFT);
        parent.setLayout(layout);
        String wid="null";
        String wpid="null";
        String type="null";
        String p="null";
        String c="null";
        String o="null";
        String height="null";
        String rent="null";
        String dist="null";
        String state="null";
        String address="null";
        String verified="null";
        int i=0;
        try
        { 
            JPanel[] panels = new JPanel[100];
            JLabel[] a_label = new JLabel[100];
            JLabel[] b_label = new JLabel[100];
            JLabel[] c_label = new JLabel[100];
            JLabel[] d_label = new JLabel[100];
            JLabel[] e_label = new JLabel[100];
            JLabel[] f_label = new JLabel[100];
            JLabel[] g_label = new JLabel[100];
            JLabel[] h_label = new JLabel[100];
            JLabel[] i_label = new JLabel[100];
            JLabel[] j_label = new JLabel[100];
            JLabel[] k_label = new JLabel[100];
            JLabel[] l_label = new JLabel[100];
            JButton[] inter = new JButton[100];
            JPanel button = new JPanel();
            
            JLabel[] head = new JLabel[100];
            
            GridBagConstraints gbc = new GridBagConstraints();
            
            Connection cn=Connectivity.getConnection();
            PreparedStatement ps=null;
            ResultSet rs=null;
            
            if(def==1){
                System.out.println("select * from warehouse where State_Code="+scode+" and District='"+city+"';");
                ps=cn.prepareStatement("select * from warehouse where State_Code="+scode+" and District='"+city+"';");
                rs=ps.executeQuery();
            }
            else if(def==2){
                ps=cn.prepareStatement("select * from warehouse where State_Code="+scode+";");
                rs=ps.executeQuery();
            }
            else{
                ps=cn.prepareStatement("select * from warehouse;");
                rs=ps.executeQuery();
            }
            Border blackline = BorderFactory.createLineBorder(Color.black);
            while(rs.next())
            {   
                wid=rs.getString(1);
                wpid=rs.getString(2);
                type=rs.getString(3);
                p=rs.getString(4);
                c=rs.getString(5);
                o=rs.getString(6);
                height=rs.getString(7);
                rent=rs.getString(8);
                dist=rs.getString(9);
                state=rs.getString(10);
                address=rs.getString(11);
                verified=rs.getString(12);
                
                inter[i] = new JButton("I AM INTERESTED");
                //inter[i].setSize(800,90);
                inter[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                inter[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                inter[i].setBackground(Color.GRAY);
                
                
                PreparedStatement _ps=cn.prepareStatement("select * from all_states where state_code="+rs.getString("State_Code")+";");
                ResultSet _rs=_ps.executeQuery();
                _rs.next();
                head[i] = new JLabel(p+" Sq. Feet Warehouse in "+dist+", "+_rs.getString("state_name"));
                //head[i].setSize(800,70);
                head[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                head[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                head[i].setBorder(blackline);
                
                a_label[i] = new JLabel("#"+wid);
                //a_label[i].setSize(70,70);
                a_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                a_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                a_label[i].setBorder(blackline);
                
                b_label[i] = new JLabel("#"+wpid);
                //b_label[i].setSize(70,70);
                b_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                b_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                b_label[i].setBorder(blackline);
                
                c_label[i] = new JLabel(type);
                //c_label[i].setSize(70,70);
                c_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                c_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                c_label[i].setBorder(blackline);
                
                d_label[i] = new JLabel(p+" Sq. Feet");
                //d_label[i].setSize(70,70);
                d_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                d_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                d_label[i].setBorder(blackline);
                
                e_label[i] = new JLabel(c+" Sq. Feet");
                //e_label[i].setSize(70,70);
                e_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                e_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                e_label[i].setBorder(blackline);
                
                f_label[i] = new JLabel(o+" Sq. Feet");
                //f_label[i].setSize(70,70);
                f_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                f_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                e_label[i].setBorder(blackline);
                
                g_label[i] = new JLabel(height+" Feet");
                //g_label[i].setSize(70,70);
                g_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                g_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                g_label[i].setBorder(blackline);
                
                h_label[i] = new JLabel(rent+p+" ₹ per Sq. Feet");
               // h_label[i].setSize(70,70);
                h_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                h_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                h_label[i].setBorder(blackline);
                
                i_label[i] = new JLabel(dist);
                //i_label[i].setSize(70,70);
                i_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                i_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                i_label[i].setBorder(blackline);
                
                j_label[i] = new JLabel(state);
                //j_label[i].setSize(70,70);
                j_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                j_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                j_label[i].setBorder(blackline);
                
                k_label[i] = new JLabel(address);
                //k_label[i].setSize(70,70);
                k_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                k_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                k_label[i].setBorder(blackline);
                
                l_label[i] = new JLabel(verified);
                //l_label[i].setSize(70,70);
                l_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                l_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                l_label[i].setBorder(blackline);
                l_label[i].setBackground(Color.GRAY);
                l_label[i].setOpaque(true);
                if(verified.equals("YES")){
                    l_label[i].setBackground(Color.green);
                }
                else{
                    l_label[i].setBackground(Color.red);
                }
                
                inter[i].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perform(evt.getSource());
            }
        });
   
                panels[i] = new JPanel();
                panels[i].setLayout(new GridBagLayout());
                panels[i].setBorder(blackline);
                panels[i].setPreferredSize(d);
               
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(30,30,30,30);
                gbc.gridwidth = 3;
                gbc.weightx = 3;
                panels[i].add(head[i],gbc);
                upd(panels[i]);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 0.5;
                panels[i].add(d_label[i],gbc);
                upd(panels[i]);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 0.5;
                panels[i].add(c_label[i],gbc);
                upd(panels[i]);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 2;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 0.5;
                panels[i].add(g_label[i],gbc);
                upd(panels[i]);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                gbc.weightx = 0.5;
                panels[i].add(h_label[i],gbc);
                upd(panels[i]);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 2;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.weightx = 0.5;
                panels[i].add(l_label[i],gbc);
                upd(panels[i]);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.weightx = 0;
                gbc.gridwidth = 3;
                panels[i].add(inter[i],gbc);
                upd(panels[i]);
                panels[i].setToolTipText(wid);
                panels[i].setVisible(true);
                
                parent.add(panels[i]);
                upd(parent);
                                
                i++;
            }
            Double cv=Math.floor(i);
            //cv++;
            System.out.println(cv+" "+cv.intValue());
            parent.setPreferredSize(new Dimension(980,(cv.intValue())*350));
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,e);
        }
    }
    private void populate2(JPanel parent,int def,String scode,String city)
    {
        layout.setAlignment(FlowLayout.LEFT);
        parent.setLayout(layout);
        String ConnID="null";
        String CID="null";
        String WID="null";
        String WPID="null";
        String Request="null";
        String Period="null";
        String Start="null";
        String Special="null";
        String Status="null";
        int i=0;
        try
        { 
            JPanel[] panels = new JPanel[100];
            JLabel[] a_label = new JLabel[100];
            JLabel[] b_label = new JLabel[100];
            JLabel[] c_label = new JLabel[100];
            JLabel[] d_label = new JLabel[100];
            JLabel[] e_label = new JLabel[100];
            JLabel[] f_label = new JLabel[100];
            JLabel[] g_label = new JLabel[100];
            JLabel[] h_label = new JLabel[100];
            JLabel[] i_label = new JLabel[100];
            
            JLabel[] ee = new JLabel[100];
            JLabel[] ff = new JLabel[100];
            JLabel[] gg = new JLabel[100];
            JLabel[] hh = new JLabel[100];
            JLabel[] ii = new JLabel[100];
            
            JLabel[] head = new JLabel[100];
            
            GridBagConstraints gbc = new GridBagConstraints();
            
            Connection cn=Connectivity.getConnection();
            PreparedStatement ps=null;
            ResultSet rs=null;
            ps=cn.prepareStatement("select * from connection where CID='"+this.id+"';");
            rs=ps.executeQuery();
            
            Border blackline = BorderFactory.createLineBorder(Color.black);
            while(rs.next())
            {   
                ConnID=rs.getString(1);
                CID=rs.getString(2);
                WID=rs.getString(3);
                WPID=rs.getString(4);
                Request=rs.getString(5);
                Period=rs.getString(6);
                Start=rs.getString(7);
                Special=rs.getString(8);
                Status=rs.getString(9);
                
                PreparedStatement _ps=cn.prepareStatement("select warehouse.parea,warehouse.district,all_states.state_name from warehouse inner join all_states on warehouse.state_code=all_states.state_code where WID='"+WID+"';");
                ResultSet _rs=_ps.executeQuery();
                _rs.next();
                
                head[i] = new JLabel(_rs.getString(1)+" Sq. Feet Warehouse in "+_rs.getString(2)+", "+_rs.getString(3));
                //a_label[i].setSize(70,70);
                head[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                head[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                head[i].setBorder(blackline);
                head[i].setOpaque(true);
                head[i].setForeground(Color.white);
                head[i].setBackground(Color.black);
                
                a_label[i] = new JLabel("#"+ConnID);
                //a_label[i].setSize(70,70);
                a_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                a_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                a_label[i].setBorder(blackline);
                
                b_label[i] = new JLabel("#"+CID);
                //a_label[i].setSize(70,70);
                b_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                b_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                b_label[i].setBorder(blackline);
                
                c_label[i] = new JLabel("#"+WID);
                //a_label[i].setSize(70,70);
                c_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                c_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                c_label[i].setBorder(blackline);
                
                d_label[i] = new JLabel("#"+WPID);
                //a_label[i].setSize(70,70);
                d_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                d_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                d_label[i].setBorder(blackline);
                
                e_label[i] = new JLabel(Request+" Sq. Feet");
                //a_label[i].setSize(70,70);
                e_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                e_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                e_label[i].setBorder(blackline);
                
                ee[i] = new JLabel("REQUESTED SPACE");
                //a_label[i].setSize(70,70);
                ee[i].setFont(new java.awt.Font("Tahoma", 1, 11));
                ee[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                ee[i].setOpaque(true);
                ee[i].setForeground(Color.white);
                ee[i].setBackground(Color.black);
                
                f_label[i] = new JLabel(Period);
                //a_label[i].setSize(70,70);
                f_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                f_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                f_label[i].setBorder(blackline);
                
                ff[i] = new JLabel("TIME PERIOD");
                //a_label[i].setSize(70,70);
                ff[i].setFont(new java.awt.Font("Tahoma", 1, 11));
                ff[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                ff[i].setOpaque(true);
                ff[i].setForeground(Color.white);
                ff[i].setBackground(Color.black);
                
                 g_label[i] = new JLabel(Start);
                //a_label[i].setSize(70,70);
                g_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                g_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                g_label[i].setBorder(blackline);
                
                gg[i] = new JLabel("EXPECTED START DATE");
                //a_label[i].setSize(70,70);
                gg[i].setFont(new java.awt.Font("Tahoma", 1, 11));
                gg[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                gg[i].setOpaque(true);
                gg[i].setForeground(Color.white);
                gg[i].setBackground(Color.black);
                
                h_label[i] = new JLabel(Special);
                //a_label[i].setSize(70,70);
                h_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                h_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                h_label[i].setBorder(blackline);
                
                hh[i] = new JLabel("SPECIAL COMMENTS");
                //a_label[i].setSize(70,70);
                hh[i].setFont(new java.awt.Font("Tahoma", 1, 11));
                hh[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                hh[i].setOpaque(true);
                hh[i].setForeground(Color.white);
                hh[i].setBackground(Color.black);
                
                i_label[i] = new JLabel(Status);
                //a_label[i].setSize(70,70);
                i_label[i].setFont(new java.awt.Font("Bahnschrift", 1, 20));
                i_label[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                i_label[i].setBorder(blackline);
                i_label[i].setOpaque(true);
                
                ii[i] = new JLabel("STATUS");
                //a_label[i].setSize(70,70);
                ii[i].setFont(new java.awt.Font("Tahoma", 1, 11));
                ii[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                ii[i].setOpaque(true);
                ii[i].setForeground(Color.white);
                ii[i].setBackground(Color.black);
                
                if(Status.equals("AWAITING")){
                    i_label[i].setBackground(Color.yellow);
                }
                else if(Status.equals("ACCEPTED")){
                    i_label[i].setBackground(Color.green);
                }
                else{
                    i_label[i].setBackground(Color.red);
                }
      
   
                panels[i] = new JPanel();
                panels[i].setLayout(new GridBagLayout());
                panels[i].setBorder(blackline);
                panels[i].setPreferredSize(d);
               
                /*
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(30,30,30,30);
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(a_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(e_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(f_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 2;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(g_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 3;
                gbc.weightx = 3;
                panels[i].add(h_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 3;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(i_label[i],gbc);
                upd(panels[i]);
                */
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.insets = new Insets(5,5,5,5);
                gbc.gridwidth = 2;
                gbc.weightx = 3;
                panels[i].add(head[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(a_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(ee[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(e_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(ff[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(f_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 2;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(gg[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 2;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(g_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 3;
                gbc.weightx = 3;
                panels[i].add(hh[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 3;
                gbc.weightx = 3;
                panels[i].add(h_label[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 5;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(ii[i],gbc);
                upd(panels[i]);
                
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 6;
                gbc.gridwidth = 1;
                gbc.weightx = 3;
                panels[i].add(i_label[i],gbc);
                upd(panels[i]);
                
                
                panels[i].setToolTipText(ConnID);
                panels[i].setVisible(true);
                
                parent.add(panels[i]);
                upd(parent);
                                
                i++;
            }
            Double cv=Math.floor(i);
            //cv++;
            System.out.println(cv+" "+cv.intValue());
            parent.setPreferredSize(new Dimension(980,(cv.intValue())*350));
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,e);
        }
    }
    public CUST_DASHBOARD(String id,String name) {
        this.id = id;
        this.name = name;
        initComponents();
        setStateList();
        populate(SW,0,null,null);
        populate2(SC,0,null,null);
        
        welcome.setText("WELCOME "+name);
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
        jPanel3 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        C = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        WELCOME1 = new javax.swing.JLabel();
        A = new javax.swing.JButton();
        B = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        welcome = new javax.swing.JLabel();
        D1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        HEADER = new javax.swing.JLabel();
        PANEL = new javax.swing.JPanel();
        AS = new javax.swing.JSplitPane();
        SPA = new javax.swing.JScrollPane();
        SW = new javax.swing.JPanel();
        SR = new javax.swing.JPanel();
        StateCB = new javax.swing.JComboBox<>();
        CityCB = new javax.swing.JComboBox<>();
        GO = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        BC = new javax.swing.JScrollPane();
        SC = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        C.setBackground(new java.awt.Color(0, 0, 0));
        C.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        C.setForeground(new java.awt.Color(255, 255, 255));
        C.setText("ODD Support");
        C.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CActionPerformed(evt);
            }
        });

        WELCOME1.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        WELCOME1.setForeground(new java.awt.Color(255, 255, 255));
        WELCOME1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        A.setBackground(new java.awt.Color(0, 0, 0));
        A.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        A.setForeground(new java.awt.Color(255, 255, 255));
        A.setText("Search Warehouses");
        A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AActionPerformed(evt);
            }
        });

        B.setBackground(new java.awt.Color(0, 0, 0));
        B.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        B.setForeground(new java.awt.Color(255, 255, 255));
        B.setText("Connections");
        B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BActionPerformed(evt);
            }
        });

        welcome.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        welcome.setForeground(new java.awt.Color(255, 255, 255));
        welcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        D1.setBackground(new java.awt.Color(0, 0, 0));
        D1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        D1.setForeground(new java.awt.Color(255, 255, 255));
        D1.setText("LOGOUT");
        D1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(welcome, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGap(76, 76, 76)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(74, 74, 74))
                        .addComponent(WELCOME1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(A, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(B, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(C, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(36, 36, 36))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(75, 75, 75)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(72, 72, 72)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(75, 75, 75)))
                    .addGap(8, 8, 8)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(welcome, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
                .addComponent(D1)
                .addGap(152, 152, 152))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(145, 145, 145)
                    .addComponent(WELCOME1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addComponent(A)
                    .addGap(18, 18, 18)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(B, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(C, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(274, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 740));

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        HEADER.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        HEADER.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HEADER.setText("MENU");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HEADER, javax.swing.GroupLayout.DEFAULT_SIZE, 956, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(HEADER)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 980, 80));

        PANEL.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        PANEL.setLayout(new java.awt.CardLayout());

        AS.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        SW.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout SWLayout = new javax.swing.GroupLayout(SW);
        SW.setLayout(SWLayout);
        SWLayout.setHorizontalGroup(
            SWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 974, Short.MAX_VALUE)
        );
        SWLayout.setVerticalGroup(
            SWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 654, Short.MAX_VALUE)
        );

        SPA.setViewportView(SW);

        AS.setRightComponent(SPA);

        StateCB.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        StateCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));
        StateCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StateCBActionPerformed(evt);
            }
        });

        CityCB.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        CityCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));
        CityCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CityCBActionPerformed(evt);
            }
        });

        GO.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        GO.setText("GO");
        GO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GOActionPerformed(evt);
            }
        });

        jButton2.setText("RESET");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SRLayout = new javax.swing.GroupLayout(SR);
        SR.setLayout(SRLayout);
        SRLayout.setHorizontalGroup(
            SRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SRLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(StateCB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167)
                .addGroup(SRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SRLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(SRLayout.createSequentialGroup()
                        .addComponent(CityCB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                        .addComponent(GO, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))))
        );
        SRLayout.setVerticalGroup(
            SRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SRLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StateCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GO, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        AS.setLeftComponent(SR);

        PANEL.add(AS, "card3");

        SC.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout SCLayout = new javax.swing.GroupLayout(SC);
        SC.setLayout(SCLayout);
        SCLayout.setHorizontalGroup(
            SCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 976, Short.MAX_VALUE)
        );
        SCLayout.setVerticalGroup(
            SCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 755, Short.MAX_VALUE)
        );

        BC.setViewportView(SC);

        PANEL.add(BC, "card3");

        jPanel1.add(PANEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 980, 660));

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

        setSize(new java.awt.Dimension(1381, 784));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BActionPerformed
        HEADER.setText("Manage your ongoing connections");
        PANEL.removeAll();
        PANEL.add(BC);
        PANEL.repaint();
        PANEL.revalidate();
    }//GEN-LAST:event_BActionPerformed

    private void AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AActionPerformed
        HEADER.setText("Search for the optimal property");
        PANEL.removeAll();
        PANEL.add(AS);
        PANEL.repaint();
        PANEL.revalidate();
    }//GEN-LAST:event_AActionPerformed

    private void CActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CActionPerformed
        HEADER.setText("Get in touch");
        PANEL.removeAll();
        // PANEL.add(SPC);
        PANEL.repaint();
        PANEL.revalidate();
    }//GEN-LAST:event_CActionPerformed

    private void StateCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StateCBActionPerformed
        int code = StateCB.getSelectedIndex();
        CityCB.removeAllItems();
        CityCB.addItem("None");
        try{
            Connection con=Connectivity.getConnection();
            PreparedStatement ps=con.prepareStatement("SELECT * FROM all_cities WHERE state_code="+code+" ORDER BY city_name;");
            ResultSet rs=ps.executeQuery();
            
            while(rs.next())
            {
                CityCB.addItem(rs.getString("city_name"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,e);
        }
    }//GEN-LAST:event_StateCBActionPerformed

    private void GOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GOActionPerformed
        int code = StateCB.getSelectedIndex();
        String city = CityCB.getSelectedItem().toString();
        if(code>0 && code<36 && !city.equals("None")){
            SW.removeAll();
            JOptionPane.showMessageDialog(this,"SEARCHING FOR "+city+", "+StateCB.getSelectedItem().toString());
            //System.out.print(code+" "+city);
            populate(SW,1,Integer.toString(code),city);
            upd(SW);
            upd(PANEL);
        }
        else if(code>0 && code<36 && city.equals("None")){
            SW.removeAll();
            JOptionPane.showMessageDialog(this,"SEARCHING FOR "+StateCB.getSelectedItem().toString());
            populate(SW,2,Integer.toString(code),null);
            upd(SW);
            upd(PANEL);
        }
        else{
            JOptionPane.showMessageDialog(this,"PLEASE SPECIFY STATE & CITY");
        }
    }//GEN-LAST:event_GOActionPerformed

    private void CityCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CityCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CityCBActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        StateCB.setSelectedIndex(0);
        CityCB.setSelectedIndex(0);
        SW.removeAll();
        populate(SW,0,null,null);
        upd(SW);
        upd(PANEL);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void D1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D1ActionPerformed
        this.dispose();
        HOME ob = new HOME();
        ob.setVisible(true);
    }//GEN-LAST:event_D1ActionPerformed

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
            java.util.logging.Logger.getLogger(CUST_DASHBOARD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CUST_DASHBOARD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CUST_DASHBOARD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CUST_DASHBOARD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CUST_DASHBOARD(id,name).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton A;
    private javax.swing.JSplitPane AS;
    private javax.swing.JButton B;
    private javax.swing.JScrollPane BC;
    private javax.swing.JButton C;
    private javax.swing.JComboBox<String> CityCB;
    private javax.swing.JButton D1;
    private javax.swing.JButton GO;
    private javax.swing.JLabel HEADER;
    private javax.swing.JPanel PANEL;
    private javax.swing.JPanel SC;
    private javax.swing.JScrollPane SPA;
    private javax.swing.JPanel SR;
    private javax.swing.JPanel SW;
    private javax.swing.JComboBox<String> StateCB;
    private javax.swing.JLabel WELCOME1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel welcome;
    // End of variables declaration//GEN-END:variables
    private static String id;
    private static String name;
}
