
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import dao.project.JobDAO;
import dao.project.MySQLJobDAO;
import display.MainWindow;
import display.SettingsFrame;

import model.Job;
import model.ServerInfo;

import plugins.FolderProcessingPlugins;
import plugins.FolderProcessingPlugins.FolderStructure;
import settings.SystemSettings;
import tools.cluster.condor.CondorUtils;
import tools.cluster.condor.CondorUtils.Arch;
import tools.cluster.condor.CondorUtils.OS;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JCheckBox;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class restingState implements FolderProcessingPlugins  {
	private JFrame frame;
	private String title = "Resting State ";
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_4;
	private JPanel panel;
	private JLabel lblOrientation;
	private JLabel lblPresubstractPhaseAnd;
	private JCheckBox chckbx;
	private JCheckBox chckbx_1;
	private JButton btnSelect_1;
	private JButton btnSelect;
	private JPanel panel_1;
	private JLabel lblNslices;
	private JLabel lblTr;
	private JLabel lblTa;
	private JLabel lblSo;
	private JLabel lblRefslice;
	private JPanel panel_2;
	private JLabel lblFwhm;
	private JPanel panel_3;
	private JButton btnOk;
	private JButton btnClose;
	private ArrayList<File> folders;


	@Override
	public PluginCategory getCategory() {
		return PluginCategory.BatchImageProcessing;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Resting State";
	}

	public String actionOnFolders(ArrayList<File> folders, FolderStructure structure){
		createAndShowGUI(folders);
		return null;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createAndShowGUI(final ArrayList<File> folders){
		frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800,450);
		frame.setTitle(title);
		frame.setLocationRelativeTo(null);//(WindowManager.MAINWINDOW.getLocation());
		frame.setIconImage(new ImageIcon(this.getClass().getResource("/images/logo32.png")).getImage());
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[][][][][53.00][][30.00,grow]"));
		frame.setVisible(true);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Optionnal option", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][246.00][][grow][][]", "[][][][][]"));

		lblOrientation = new JLabel("Check for reorientation");
		panel.add(lblOrientation, "cell 1 1");
		chckbx = new JCheckBox("");
		



		panel.add(chckbx, "cell 2 1");

		textField_7 = new JTextField();
		textField_7.setVisible(false);
		panel.add(textField_7, "cell 3 1,growx");
		textField_7.setColumns(10);

		lblPresubstractPhaseAnd = new JLabel("Check for resubstract phase and magnitude");
		panel.add(lblPresubstractPhaseAnd, "cell 1 3");
		chckbx_1 = new JCheckBox("");
		panel.add(chckbx_1, "cell 2 3");

		textField = new JTextField();
		textField.setVisible(false);
		panel.add(textField, "cell 3 3,growx");
		textField.setColumns(10);
		ImageIcon icon=new ImageIcon(MainWindow.class.getResource("/images/folder.png"));
		Image img = icon.getImage();  
		Image newimg = img.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);  
		ImageIcon icon2 = new ImageIcon(newimg); 
		btnSelect_1 = new JButton(icon2);
		btnSelect_1.setVisible(false);
		panel.add(btnSelect_1, "cell 4 3");
		btnSelect = new JButton(icon2);
		btnSelect.setVisible(false);
		panel.add(btnSelect, "cell 4 1");

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Slice timing", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_1, "cell 0 2,grow");
		panel_1.setLayout(new MigLayout("", "[][246.00][44.00][grow][][]", "[][][][][]"));

		lblNslices = new JLabel("nSlices");
		panel_1.add(lblNslices, "cell 1 0");

		textField_1 = new JTextField();
		panel_1.add(textField_1, "cell 2 0");
		textField_1.setColumns(10);

		lblTr = new JLabel("Tr");
		panel_1.add(lblTr, "cell 1 1");

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		panel_1.add(textField_2, "cell 2 1");

		lblTa = new JLabel("Ta");
		panel_1.add(lblTa, "cell 1 2");

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		panel_1.add(textField_3, "cell 2 2");

		lblSo = new JLabel("So");
		panel_1.add(lblSo, "cell 1 3");
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		panel_1.add(textField_4, "cell 2 3 3 1,growx");

		lblRefslice = new JLabel("RefSlice");
		panel_1.add(lblRefslice, "cell 1 4");

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		panel_1.add(textField_5, "cell 2 4");

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Smooth", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_2, "cell 0 4,grow");
		panel_2.setLayout(new MigLayout("", "[][246.00][44.00][grow][][]", "[][][][][]"));

		lblFwhm = new JLabel("Fwhm");
		panel_2.add(lblFwhm, "cell 1 0");

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		panel_2.add(textField_6, "cell 2 0");

		panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, "cell 0 6,grow");
		panel_3.setLayout(new MigLayout("", "[397.00,grow][360.00,grow]", "[]"));

		btnOk = new JButton("OK");
		panel_3.add(btnOk, "cell 0 0,growx");

		btnClose = new JButton("Close");
		panel_3.add(btnClose, "cell 1 0,growx");

		btnSelect_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField.setText(file.getAbsolutePath());
				}
			}
		});

		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_7.setText(file.getAbsolutePath());
				}
			}
		});
		
		chckbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JCheckBox) e.getSource()).isSelected())
				{
					textField_7.setVisible(true);
					btnSelect.setVisible(true);
				}
				else
				{
					textField_7.setVisible(false);
					btnSelect.setVisible(false);
				}
			}
		});

		chckbx_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JCheckBox) e.getSource()).isSelected())
				{
					textField.setVisible(true);
					btnSelect_1.setVisible(true);
				}
				else
				{
					textField.setVisible(false);
					btnSelect_1.setVisible(false);
				}
			}
		});
		
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createMatlabAndBashFiles(folders);
				frame.dispose();
			}
		});
		
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
	}
	public void createMatlabAndBashFiles(ArrayList<File> folders){
		Long time=System.nanoTime();
		String nom="job_"+time.toString();
		File dir=new File(SystemSettings.APP_DIR.toString()+File.separator+ServerInfo.CONDOR_JOB_DIR_NAME);
		try {
			BufferedReader in  = new BufferedReader(new FileReader("C:\\Users\\Administrateur\\Documents\\MATLAB\\Joris\\test_java\\batch_restingState.m"));
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir+File.separator+nom+".m")));
			String line;
			int i = 0;
			for(int j=0;j<folders.size();j++){
				while ((line = in.readLine()) != null) {
					if(chckbx.isSelected())
						line=line.replace("#1#", "0");
					else
						line=line.replace("#1#", "1");
					line=line.replace("#2#", folders.get(j).toString());
					if(chckbx_1.isSelected())
						line=line.replace("#3#", "0");
					else
						line=line.replace("#3#", "1");
					line=line.replace("#4#", "aa");
					line=line.replace("#5#", "bb");
					line=line.replace("#6#", textField.getText());
					line=line.replace("#7#", textField_1.getText());
					line=line.replace("#8#", textField_2.getText());
					line=line.replace("#9#", textField_3.getText());
					line=line.replace("#10#", textField_4.getText());
					line=line.replace("#11#", textField_5.getText());
					line=line.replace("#12#", textField_6.getText());
					writer.write(line+"\n");
					i++;
				}
			}
			in.close();
			writer.close();
			writer = new BufferedWriter(new FileWriter(new File(dir+File.separator+nom+".bat")));
			writer.write("echo \"%1 %2\"\n");
			writer.write("\"%1 %2\" -logfile matlablog.log -nodesktop -nosplash -r "+nom+"\n");
			writer.close();
			ArrayList<File> a = new ArrayList<>();
			a.add((new File(dir+File.separator+nom+".m")));
			CondorUtils.submitJob(dir, a, new File(dir+File.separator+nom+".bat"), 2, 3, OS.WINDOWS, Arch.X86_64,"test");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}