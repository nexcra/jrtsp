package examples.jovc;


import heye.rtsp.net.RtspUrl;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import java.awt.Dimension;
import com.borland.jbcl.layout.BoxLayout2;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 * <p>Title: JOVC</p>
 *
 * <p>Description: OVS test tool</p>
 *
 * <p>Copyright: Copyright (c) Jiang Ying 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Jiang Ying
 * @version 0.10.1
 */
public class MainFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	// UI 
	BorderLayout borderLayout1 = new BorderLayout();
    JPanel mainPanel = new JPanel();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JComboBox portBox = new JComboBox();
    JComboBox videoBox = new JComboBox();
    JComboBox hostBox = new JComboBox();
    //JPanel logPanel = new JPanel();
    JScrollPane logPanel = new JScrollPane();
    JPanel statusPanel = new JPanel();
    JButton playBtn = new JButton();
    JButton pauseBtn = new JButton();
    JButton rewardBtn = new JButton();
    JButton forwardBtn = new JButton();
    JButton stopBtn = new JButton();
    JPanel jPanel4 = new JPanel();
    JPanel serverPanel = new JPanel();
    JPanel clientPanel = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JLabel jLabel4 = new JLabel();
    JComboBox clientBox = new JComboBox();
    JLabel jLabel5 = new JLabel();
    JComboBox clientPortBox = new JComboBox();
    BoxLayout2 boxLayout21 = new BoxLayout2();
    BoxLayout2 boxLayout22 = new BoxLayout2();
    JButton aboutBtn = new JButton();
    JPanel functionPanel = new JPanel();
    BoxLayout2 boxLayout23 = new BoxLayout2();
    JTextArea log = new JTextArea();
    GridLayout gridLayout1 = new GridLayout();
    TitledBorder titledBorder1 = new TitledBorder("");
    // *************
    
    
    // *************
    private CoshipClientSupport client;
    private RtspUrl context;
    // *************
    
	
	private Process jvlc  = null;
    
    public MainFrame() {
        try {
            jbInit();
            client = new CoshipClientSupport(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(borderLayout1);
        jLabel2.setToolTipText("");
        jLabel2.setText(" Port ");
        jLabel3.setText(" Video ");
        jLabel1.setToolTipText("");
        jLabel1.setIconTextGap(5);
        videoBox.setPreferredSize(new Dimension(80, 23));
        videoBox.setEditable(true);
        videoBox.setMaximumRowCount(10);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        portBox.setPreferredSize(new Dimension(60, 23));
        portBox.setToolTipText("");
        portBox.setEditable(true);
        hostBox.setPreferredSize(new Dimension(120, 23));
        hostBox.setEditable(true);
        playBtn.setText("Play");
        playBtn.addActionListener(new MainFrame_playBtn_actionAdapter(this));
        pauseBtn.setText("Pause");
        pauseBtn.addActionListener(new MainFrame_pauseBtn_actionAdapter(this));
        rewardBtn.setText("<<");
        rewardBtn.addActionListener(new MainFrame_rewardBtn_actionAdapter(this));
        forwardBtn.setText(">>");
        forwardBtn.addActionListener(new MainFrame_forwardBtn_actionAdapter(this));
        stopBtn.setText("Stop");
        stopBtn.addActionListener(new MainFrame_stopBtn_actionAdapter(this));
        jLabel4.setToolTipText("");
        jLabel4.setText("Client IP ");
        jLabel5.setToolTipText("");
        jLabel5.setText(" Client Port ");
        clientBox.setPreferredSize(new Dimension(120, 23));
        clientBox.setEditable(true);
        clientPortBox.setPreferredSize(new Dimension(60, 23));
        clientPortBox.setEditable(true);
        serverPanel.setLayout(boxLayout21);
        clientPanel.setLayout(boxLayout22);
        aboutBtn.setText("About");
        aboutBtn.addActionListener(new MainFrame_aboutBtn_actionAdapter(this));
        functionPanel.setLayout(boxLayout23);
        //logPanel.setLayout(gridLayout1);
        //logPanel.setLayout(java.awt.BorderLayout.CENTER);
        log.setBorder(BorderFactory.createLoweredBevelBorder());
        log.setToolTipText("");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.add(serverPanel);
        mainPanel.add(clientPanel);
        mainPanel.add(functionPanel);
        serverPanel.add(jLabel1);
        serverPanel.add(hostBox);
        serverPanel.add(jLabel2);
        serverPanel.add(portBox);
        serverPanel.add(jLabel3);
        serverPanel.add(videoBox);
        clientPanel.add(jLabel4);
        clientPanel.add(clientBox);
        clientPanel.add(jLabel5);
        clientPanel.add(clientPortBox);
        this.getContentPane().add(mainPanel, java.awt.BorderLayout.NORTH);

        functionPanel.add(playBtn, null);
        functionPanel.add(pauseBtn, null);
        functionPanel.add(stopBtn, null);
        functionPanel.add(rewardBtn, null);
        functionPanel.add(forwardBtn, null);
        functionPanel.add(aboutBtn, null);
        //logPanel.add(log, null);
        logPanel.getViewport().add(log);
        this.getContentPane().add(logPanel, java.awt.BorderLayout.CENTER);

        this.getContentPane().add(statusPanel, java.awt.BorderLayout.SOUTH);
        jLabel1.setText("Server IP ");
        mainPanel.setLayout(verticalFlowLayout1);
        this.setTitle("JOVC 0.11.1");
        
    	this.playBtn.setEnabled(true);
		this.pauseBtn.setEnabled(false);
		this.rewardBtn.setEnabled(false);
		this.forwardBtn.setEnabled(false);
		this.stopBtn.setEnabled(false);
			
        this.addWindowListener(new WindowOpened(this));
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.pack();

        // Center Window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
        frame.setSize(new Dimension(600,300));

    }

    public void playBtn_actionPerformed(ActionEvent e) {
        //hostBox.addItem("abc");
        //JOptionPane.showMessageDialog(this,(String)clientBox.getSelectedItem());
    	if (! this.stopBtn.isEnabled()) {
	    	try {
	    		context = new RtspUrl((String)hostBox.getSelectedItem(), Integer.parseInt((String)portBox.getSelectedItem()), (String)videoBox.getSelectedItem());
	    		if (context == null) {
	    			log("Server IP:PORT Error!\n");
	    			return;
	    		}
	    		client.connect(context, (String)clientBox.getSelectedItem(), (String)clientPortBox.getSelectedItem());
	    		if (hostBox.getSelectedIndex()<0)
	    			hostBox.addItem(hostBox.getSelectedItem());
	    		if (portBox.getSelectedIndex()<0)
	    			portBox.addItem(portBox.getSelectedItem());
	    		if (videoBox.getSelectedIndex()<0) 
	    			videoBox.addItem(videoBox.getSelectedItem());
	    		if (clientBox.getSelectedIndex()<0) 
	    			clientBox.addItem(clientBox.getSelectedItem());
	    		if (clientPortBox.getSelectedIndex()<0) 
	    			clientPortBox.addItem(clientPortBox.getSelectedItem());
	    	} catch (Exception ex) {
	    		log("Server IP:PORT Error!" + ex.toString() + "\n");
	    		ex.printStackTrace();
	    	}
    	} else {
    		client.play(context, 1);
    	}
    }

    public void pauseBtn_actionPerformed(ActionEvent e) {
    	client.pause(context);
    }

    public void stopBtn_actionPerformed(ActionEvent e) {
    	client.teardown(this.context);
    }

    public void rewardBtn_actionPerformed(ActionEvent e) {
    	client.scale(context, -4);
    }

    public void forwardBtn_actionPerformed(ActionEvent e) {
    	client.scale(context, 4);
    }

    public void aboutBtn_actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this,
                                      "JOVC 0.11.1\n"+
                                      "\tCopyright (c) Jiang Ying 2007\n" + 
                                      "\tjy@iptv.com.ru");
    }
    
    public void onWindowOpened(WindowEvent e) {
    	try {
    		String hostname = InetAddress.getLocalHost().getHostName();
			InetAddress[] address = InetAddress.getAllByName(hostname);
			for (int i=0; i<address.length; i++) {
				clientBox.addItem(address[i].getHostAddress());
			}
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		clientPortBox.addItem("11111");
		portBox.addItem("5555");
		portBox.addItem("5004");
		portBox.addItem("4005");
		portBox.addItem("554");
    }
    
    public void showButton(boolean playBtn, boolean pauseBtn, boolean rewardBtn, boolean forwardBtn, boolean stopBtn) {
		this.playBtn.setEnabled(playBtn);
		this.pauseBtn.setEnabled(pauseBtn);
		this.rewardBtn.setEnabled(rewardBtn);
		this.forwardBtn.setEnabled(forwardBtn);
		this.stopBtn.setEnabled(stopBtn);
    }
    
	public void connect(RtspUrl context) {
		// TODO Auto-generated method stub
			/*
			try {
				jvlc = Runtime.getRuntime().exec("C:\\jvlc-0.9.0-20070310-win32\\jvlc-0.9.0-20070310\\vlc -I dummy udp:@:"+(String)clientPortBox.getSelectedItem());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	}

	public void disconnect() {
		// TODO Auto-generated method stub
		
		if (jvlc!=null) {
			try {
				jvlc.destroy();
			} catch (Exception e) {
				
			} finally {
				jvlc = null;
			}
		}
	}


	public void log(String s) {
		// TODO Auto-generated method stub	
		log.append(s);
		log.setSelectionStart(log.getText().length());
		log.setSelectionEnd(log.getText().length());
	}

}


class MainFrame_aboutBtn_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_aboutBtn_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.aboutBtn_actionPerformed(e);
    }
}


class MainFrame_forwardBtn_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_forwardBtn_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.forwardBtn_actionPerformed(e);
    }
}


class MainFrame_rewardBtn_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_rewardBtn_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rewardBtn_actionPerformed(e);
    }
}


class MainFrame_stopBtn_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_stopBtn_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.stopBtn_actionPerformed(e);
    }
}


class MainFrame_playBtn_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_playBtn_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.playBtn_actionPerformed(e);
    }
}


class MainFrame_pauseBtn_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_pauseBtn_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.pauseBtn_actionPerformed(e);
    }
}

class WindowOpened extends WindowAdapter {
	private MainFrame adaptee;
	
	WindowOpened(MainFrame adaptee) {
		this.adaptee = adaptee;
	}
    public void windowOpened(WindowEvent e) {
    	adaptee.onWindowOpened(e);
    }
}
