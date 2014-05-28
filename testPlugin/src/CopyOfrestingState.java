import ij.gui.ProgressBar;

import java.awt.BorderLayout;
import java.awt.CheckboxGroup;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.apache.commons.lang3.text.translate.EntityArrays;

import model.ServerInfo;
import net.miginfocom.swing.MigLayout;
import plugins.FolderProcessingPlugins;
import settings.SystemSettings;
import settings.WindowManager;
import tools.cluster.condor.CondorUtils;
import tools.cluster.condor.CondorUtils.Arch;
import tools.cluster.condor.CondorUtils.OS;
import display.MainWindow;
import display.containers.ProgressPanel;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CopyOfrestingState implements FolderProcessingPlugins {
	private JFrame frame;
	private String title = "Resting State ";
	private JTextField textField_15;
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
	private JPanel panel_4;
	private JLabel lblPathWhereAre;
	private JTextField textField_8;
	private JButton btnSelect_2;
	private JLabel lblNumberOfCpus;
	private JTextField textField_9;
	private JLabel lblNumberOfMemory;
	private JTextField textField_10;
	private JLabel lblChooseYourOperating;
	private JComboBox<String> comboBox;
	private JLabel lblChooseTheArchicture;
	private JComboBox<String> comboBox_1;
	private JPanel panel_5;
	private JLabel lblTemplate;
	private JTextField textField_11;
	private JLabel lblVox;
	private JTextField textField_12;
	private JLabel lblInterp;
	private JTextField textField_13;
	private JButton btnSelect_3;
	private JCheckBox chckbx_2;
	private JCheckBox chckbx_3;
	private JLabel lblCheckIfYou;
	private JCheckBox chckbx_4;
	private JLabel lblCheckIfYou_1;
	private String txt;
	private JLabel lblDescription;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private String desc_1 = "Patient : \"name\" \nAcquisition Date : \"date\"\n";
	private String desc_2 = "Patient : \"name\" \n";
	private JTextField textField_14;
	private JLabel filtre;
	private JComboBox<String> comboBox_2;
	private JCheckBox chckbx_5;
	private JCheckBox chckbx_6;
	private boolean isSubmissionDone = false;
	private JProgressBar progressBar;
	private JLabel lblNewLabel; 
	private JButton btn_cancel;

	@Override
	public PluginCategory getCategory() {
		return PluginCategory.BatchImageProcessing;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Resting State";
	}

	public String actionOnFolders(ArrayList<File> folders,
			FolderStructure structure) {
		createAndShowGUI(folders, structure);
		return null;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void createAndShowGUI(final ArrayList<File> folders,
			final FolderStructure structure) {
		frame = new JFrame();
		isSubmissionDone = false;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 860);
		frame.setTitle(title);
		frame.setLocationRelativeTo(null);// (WindowManager.MAINWINDOW.getLocation());
		frame.setIconImage(new ImageIcon(this.getClass().getResource(
				"/images/logo32.png")).getImage());
		frame.getContentPane()
		.setLayout(
				new MigLayout("", "[grow]",
						"[112.00,grow][97.00,grow][68.00,grow][53.00,grow][240.00,grow][34.00,grow]"));
		frame.setVisible(true);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Optionnal option",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[15.00][246.00][44.00][grow][][]",
				"[][][][10][][-12.00][]"));

		filtre = new JLabel("Filtre");
		panel.add(filtre,"cell 1 1");
		
		
		    
		comboBox_2 = new JComboBox<String>();
		comboBox_2.removeAllItems();
		if (structure.equals(FolderStructure.PatDatProtSer)){
			comboBox_2.addItem("");
			comboBox_2.addItem("Patient");
			comboBox_2.addItem("Date");
			comboBox_2.addItem("Protocol");
			comboBox_2.addItem("Serie");
		}
		else if(structure.equals(FolderStructure.PatDatSer)){
			comboBox_2.addItem("");
			comboBox_2.addItem("Patient");
			comboBox_2.addItem("Date");
			comboBox_2.addItem("Serie");
		}
		else if(structure.equals(FolderStructure.PatProtSer)){
			comboBox_2.addItem("");
			comboBox_2.addItem("Patient");
			comboBox_2.addItem("Protocol");
			comboBox_2.addItem("Serie");
		}
		else if(structure.equals(FolderStructure.PatSer)){
			comboBox_2.addItem("");
			comboBox_2.addItem("Patient");
			comboBox_2.addItem("Serie");
		}
		panel.add(comboBox_2, "cell 2 1");
		textField_14 = new JTextField();
		panel.add(textField_14,"cell 3 1");
		textField_14.setColumns(10);
		lblOrientation = new JLabel("Check for reorientation");
		panel.add(lblOrientation, "cell 1 2");
		chckbx = new JCheckBox("");

		panel.add(chckbx, "cell 2 2,alignx center");

		textField_7 = new JTextField();
		textField_7.setVisible(false);
		panel.add(textField_7, "cell 3 2,growx");
		textField_7.setColumns(10);

		lblCheckIfYou = new JLabel(
				"Check if you want use existing reorientation files");
		panel.add(lblCheckIfYou, "cell 1 3");

		chckbx_3 = new JCheckBox("");
		panel.add(chckbx_3, "cell 2 3,alignx center");

		lblPresubstractPhaseAnd = new JLabel(
				"Check for presubstract phase and magnitude");
		panel.add(lblPresubstractPhaseAnd, "cell 1 4");
		chckbx_1 = new JCheckBox("");
		panel.add(chckbx_1, "cell 2 4,alignx center");

		ImageIcon icon = new ImageIcon(
				MainWindow.class.getResource("/images/folder.png"));
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(20, 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon icon2 = new ImageIcon(newimg);
		txt = "(matlabroot)" + File.separator + "toolbox" + File.separator
				+ "FieldMap" + File.separator + "pm_defaults_skyra.m";
		textField_15 = new JTextField("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m");
		textField_15.setVisible(false);
		panel.add(textField_15, "cell 3 4,growx");
		textField_15.setColumns(10);
		btnSelect = new JButton(icon2);
		btnSelect.setVisible(false);
		panel.add(btnSelect, "cell 4 2");
		btnSelect_1 = new JButton(icon2);
		btnSelect_1.setVisible(false);
		panel.add(btnSelect_1, "cell 4 4");

		lblCheckIfYou_1 = new JLabel(
				"Check if you want use existing presubstract files");
		panel.add(lblCheckIfYou_1, "cell 1 5");

		chckbx_4 = new JCheckBox("");
		panel.add(chckbx_4, "cell 2 5,alignx center");

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Slice timing",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[15.00][246.00][44.00][grow][][]",
				"[][][][][]"));

		lblNslices = new JLabel("nSlices");
		panel_1.add(lblNslices, "cell 1 0");

		textField_1 = new JTextField();

		textField_1.setText("39");
		panel_1.add(textField_1, "cell 2 0");
		textField_1.setColumns(10);
		chckbx_5 = new JCheckBox("Entrelace");
		panel_1.add(chckbx_5, "flowx,cell 3 0,alignx center");
		chckbx_5.setSelected(true);
		chckbx_6 = new JCheckBox("Non entrelacet");
		panel_1.add(chckbx_6, "cell 3 0");
		final ButtonGroup group = new ButtonGroup();
		group.add(chckbx_5);
		group.add(chckbx_6);

		lblTr = new JLabel("Tr");
		panel_1.add(lblTr, "cell 1 1");

		textField_2 = new JTextField();
		textField_2.setText("2.4");
		textField_2.setColumns(10);
		panel_1.add(textField_2, "cell 2 1");

		lblTa = new JLabel("Ta");
		panel_1.add(lblTa, "cell 1 2");

		textField_3 = new JTextField();
		textField_3.setText("2.339");
		textField_3.setColumns(10);
		panel_1.add(textField_3, "cell 2 2");

		lblSo = new JLabel("So");
		panel_1.add(lblSo, "cell 1 3");

		textField_4 = new JTextField();
		textField_4
		.setText("1 3 5 7 9 11 13 15 17 19 21 23 25 27 29 31 33 35 37 39 2 4 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38");
		textField_4.setColumns(10);
		panel_1.add(textField_4, "cell 2 3 3 1,growx");

		lblRefslice = new JLabel("RefSlice");
		panel_1.add(lblRefslice, "cell 1 4");

		textField_5 = new JTextField();
		textField_5.setText("39");
		textField_5.setColumns(10);
		panel_1.add(textField_5, "cell 2 4");


		panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Normalisation",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_5, "cell 0 2,grow");
		panel_5.setLayout(new MigLayout("", "[15.00][246.00][44.00][grow][][]",
				"[][][]"));

		lblTemplate = new JLabel("Check to change the default spm template");
		panel_5.add(lblTemplate, "cell 1 0");

		chckbx_2 = new JCheckBox("");
		panel_5.add(chckbx_2, "cell 2 0,alignx center");

		textField_11 = new JTextField();
		textField_11.setVisible(false);
		panel_5.add(textField_11, "cell 3 0,growx");
		textField_11.setColumns(10);

		btnSelect_3 = new JButton(icon2);
		btnSelect_3.setVisible(false);
		panel_5.add(btnSelect_3, "cell 4 0");

		lblVox = new JLabel("Vox");
		panel_5.add(lblVox, "cell 1 1");

		textField_12 = new JTextField();
		textField_12.setText("2 2 2");
		panel_5.add(textField_12, "cell 2 1");
		textField_12.setColumns(10);

		lblInterp = new JLabel("Interp");
		panel_5.add(lblInterp, "cell 1 2");

		textField_13 = new JTextField();
		textField_13.setText("1");
		panel_5.add(textField_13, "cell 2 2");
		textField_13.setColumns(10);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Smooth",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_2, "cell 0 3,grow");
		panel_2.setLayout(new MigLayout("", "[15.00][246.00][44.00][grow][][]",
				"[][][][][]"));

		lblFwhm = new JLabel("Fwhm");
		panel_2.add(lblFwhm, "cell 1 0");

		textField_6 = new JTextField();
		textField_6.setText("6 6 6");
		textField_6.setColumns(10);
		panel_2.add(textField_6, "cell 2 0");

		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Paramaters of the submit",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_4, "cell 0 4,grow");
		panel_4.setLayout(new MigLayout("",
				"[15.00][246.00][44.00,grow,leading][grow][][]",
				"[][][][][][grow]"));

		lblPathWhereAre = new JLabel("Path where are created jobs files");
		panel_4.add(lblPathWhereAre, "cell 1 0");

		textField_8 = new JTextField(SystemSettings.SERVER_INFO.getCondorJobDir().toString());
		panel_4.add(textField_8, "cell 2 0 2 1,growx");
		textField_8.setColumns(10);

		btnSelect_2 = new JButton(icon2);
		panel_4.add(btnSelect_2, "cell 4 0");

		lblNumberOfCpus = new JLabel("Number of cpus");
		panel_4.add(lblNumberOfCpus, "cell 1 1");

		textField_9 = new JTextField();
		textField_9.setText("2");
		panel_4.add(textField_9, "cell 2 1");
		textField_9.setColumns(10);

		lblNumberOfMemory = new JLabel("Number of memory");
		panel_4.add(lblNumberOfMemory, "cell 1 2");

		textField_10 = new JTextField();
		textField_10.setText("3000");
		panel_4.add(textField_10, "cell 2 2");
		textField_10.setColumns(10);

		lblChooseYourOperating = new JLabel("Choose the operating system");
		panel_4.add(lblChooseYourOperating, "cell 1 3");

		comboBox = new JComboBox<String>();
		comboBox.removeAllItems();
		comboBox.addItem("WINDOWS");
		comboBox.addItem("UNIX");
		panel_4.add(comboBox, "cell 2 3");

		lblChooseTheArchicture = new JLabel("Choose the architecture");
		panel_4.add(lblChooseTheArchicture, "cell 1 4");

		comboBox_1 = new JComboBox<String>();
		comboBox_1.removeAllItems();
		comboBox_1.addItem("X86_64");
		comboBox_1.addItem("INTEL");
		panel_4.add(comboBox_1, "cell 2 4");

		lblDescription = new JLabel("Description");
		panel_4.add(lblDescription, "cell 1 5");

		scrollPane = new JScrollPane();
		panel_4.add(scrollPane, "cell 2 5,grow");

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		DocumentFilter dfilter = new Filter();
		DocumentFilter dfilter2 = new Filter2();
		if (structure.equals(FolderStructure.PatDatSer)
				|| structure.equals(FolderStructure.PatDatProtSer)) {
			textArea.setText(desc_1);
			((AbstractDocument) textArea.getDocument())
			.setDocumentFilter(dfilter);
		} else {
			textArea.setText(desc_2);
			((AbstractDocument) textArea.getDocument())
			.setDocumentFilter(dfilter2);
		}

		panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, "cell 0 5,grow");
		panel_3.setLayout(new MigLayout("", "[397.00,grow][360.00,grow]", "[][]"));

		btnOk = new JButton("OK");
		panel_3.add(btnOk, "cell 0 0,growx");
		// btnOk.setEnabled(false);

		btnClose = new JButton("Close");
		panel_3.add(btnClose, "cell 1 0,growx");

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
		btnSelect_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_15.setText(file.getAbsolutePath());
				}
			}
		});
		btnSelect_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_8.setText(file.getAbsolutePath());
				}
			}
		});

		btnSelect_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_11.setText(file.getAbsolutePath());
				}
			}
		});
		final MyVerifier verifier = new MyVerifier();
		chckbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_7.setVisible(true);
					btnSelect.setVisible(true);
					chckbx_3.setEnabled(false);
					lblCheckIfYou.setEnabled(false);
					if (chckbx_1.isSelected()) {
						if (verifier.verify2(textField_7)
								&& verifier.verify2(textField_15))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else if (chckbx_2.isSelected()) {
						if (verifier.verify2(textField_7)
								&& verifier.verify2(textField_11))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else {
						if (verifier.verify2(textField_7))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					}
				} else {
					textField_7.setVisible(false);
					btnSelect.setVisible(false);
					chckbx_3.setEnabled(true);
					lblCheckIfYou.setEnabled(true);
					if (chckbx_1.isSelected()) {
						if (verifier.verify(textField_1)
								&& verifier.verify2(textField_2)
								&& verifier.verify2(textField_3)
								&& verifier.verify2(textField_4)
								&& verifier.verify(textField_5)
								&& verifier.verify2(textField_12)
								&& verifier.verify(textField_13)
								&& verifier.verify2(textField_6)
								&& verifier.verify(textField_9)
								&& verifier.verify(textField_10)
								&& verifier.verify2(textField_8)
								&& verifier.verify2(textField_15))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else if (chckbx_2.isSelected()) {
						if (verifier.verify(textField_1)
								&& verifier.verify2(textField_2)
								&& verifier.verify2(textField_3)
								&& verifier.verify2(textField_4)
								&& verifier.verify(textField_5)
								&& verifier.verify2(textField_12)
								&& verifier.verify(textField_13)
								&& verifier.verify2(textField_6)
								&& verifier.verify(textField_9)
								&& verifier.verify(textField_10)
								&& verifier.verify2(textField_8)
								&& verifier.verify2(textField_11))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else {
						if (verifier.verify(textField_1)
								&& verifier.verify2(textField_2)
								&& verifier.verify2(textField_3)
								&& verifier.verify2(textField_4)
								&& verifier.verify(textField_5)
								&& verifier.verify2(textField_12)
								&& verifier.verify(textField_13)
								&& verifier.verify2(textField_6)
								&& verifier.verify(textField_9)
								&& verifier.verify(textField_10)
								&& verifier.verify2(textField_8))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					}
				}
			}
		});
		chckbx_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_15.setVisible(true);
					btnSelect_1.setVisible(true);
					chckbx_4.setEnabled(false);
					lblCheckIfYou_1.setEnabled(false);
					if (chckbx.isSelected()) {
						if (verifier.verify2(textField_15)
								&& verifier.verify2(textField_7))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else if (chckbx_2.isSelected()) {
						if (verifier.verify2(textField_15)
								&& verifier.verify2(textField_11))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else {
						if (verifier.verify2(textField_15))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					}
				} else {
					if (chckbx.isSelected()) {
						if (verifier.verify(textField_1)
								&& verifier.verify2(textField_2)
								&& verifier.verify2(textField_3)
								&& verifier.verify2(textField_4)
								&& verifier.verify(textField_5)
								&& verifier.verify2(textField_12)
								&& verifier.verify(textField_13)
								&& verifier.verify2(textField_6)
								&& verifier.verify(textField_9)
								&& verifier.verify(textField_10)
								&& verifier.verify2(textField_8)
								&& verifier.verify2(textField_7))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else if (chckbx_2.isSelected()) {
						if (verifier.verify(textField_1)
								&& verifier.verify2(textField_2)
								&& verifier.verify2(textField_3)
								&& verifier.verify2(textField_4)
								&& verifier.verify(textField_5)
								&& verifier.verify2(textField_12)
								&& verifier.verify(textField_13)
								&& verifier.verify2(textField_6)
								&& verifier.verify(textField_9)
								&& verifier.verify(textField_10)
								&& verifier.verify2(textField_8)
								&& verifier.verify2(textField_11))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else {
						if (verifier.verify(textField_1)
								&& verifier.verify2(textField_2)
								&& verifier.verify2(textField_3)
								&& verifier.verify2(textField_4)
								&& verifier.verify(textField_5)
								&& verifier.verify2(textField_12)
								&& verifier.verify(textField_13)
								&& verifier.verify2(textField_6)
								&& verifier.verify(textField_9)
								&& verifier.verify(textField_10)
								&& verifier.verify2(textField_8))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					}
					textField_15.setVisible(false);
					btnSelect_1.setVisible(false);
					chckbx_4.setEnabled(true);
					lblCheckIfYou_1.setEnabled(true);
				}
			}
		});
		chckbx_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_11.setVisible(true);
					btnSelect_3.setVisible(true);
					if (chckbx.isSelected()) {
						if (verifier.verify2(textField_11)
								&& verifier.verify2(textField_7))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else if (chckbx_1.isSelected()) {
						if (verifier.verify2(textField_11)
								&& verifier.verify2(textField_15))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else {
						if (verifier.verify2(textField_11))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					}
				} else {
					textField_11.setVisible(false);
					btnSelect_3.setVisible(false);
					if (chckbx.isSelected()) {
						if (verifier.verify(textField_1)
								&& verifier.verify2(textField_2)
								&& verifier.verify2(textField_3)
								&& verifier.verify2(textField_4)
								&& verifier.verify(textField_5)
								&& verifier.verify2(textField_12)
								&& verifier.verify(textField_13)
								&& verifier.verify2(textField_6)
								&& verifier.verify(textField_9)
								&& verifier.verify(textField_10)
								&& verifier.verify2(textField_8)
								&& verifier.verify2(textField_7))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else if (chckbx_1.isSelected()) {
						if (verifier.verify(textField_1)
								&& verifier.verify2(textField_2)
								&& verifier.verify2(textField_3)
								&& verifier.verify2(textField_4)
								&& verifier.verify(textField_5)
								&& verifier.verify2(textField_12)
								&& verifier.verify(textField_13)
								&& verifier.verify2(textField_6)
								&& verifier.verify(textField_9)
								&& verifier.verify(textField_10)
								&& verifier.verify2(textField_8)
								&& verifier.verify2(textField_15))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					} else {
						if (verifier.verify(textField_1)
								&& verifier.verify2(textField_2)
								&& verifier.verify2(textField_3)
								&& verifier.verify2(textField_4)
								&& verifier.verify(textField_5)
								&& verifier.verify2(textField_12)
								&& verifier.verify(textField_13)
								&& verifier.verify2(textField_6)
								&& verifier.verify(textField_9)
								&& verifier.verify(textField_10)
								&& verifier.verify2(textField_8))
							btnOk.setEnabled(true);
						else
							btnOk.setEnabled(false);
					}
				}
			}
		});
		chckbx_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_7.setEnabled(false);
					btnSelect.setEnabled(false);
					lblOrientation.setEnabled(false);
					chckbx.setEnabled(false);
				} else {
					textField_7.setEnabled(true);
					btnSelect.setEnabled(true);
					lblOrientation.setEnabled(true);
					chckbx.setEnabled(true);
				}
			}
		});
		chckbx_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_15.setEnabled(false);
					btnSelect.setEnabled(false);
					lblPresubstractPhaseAnd.setEnabled(false);
					chckbx_1.setEnabled(false);
				} else {
					textField_15.setEnabled(true);
					btnSelect.setEnabled(true);
					lblPresubstractPhaseAnd.setEnabled(true);
					chckbx_1.setEnabled(true);
				}
			}
		});
		chckbx_5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				So(Integer.parseInt(textField_1.getText()),0);
			}
		});
		chckbx_6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				So(Integer.parseInt(textField_1.getText()),1);	
			}
		});

		textField_1.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if(!textField_1.getText().isEmpty() && !textField_2.getText().isEmpty() && !textField_1.getText().equals("0") && !textField_2.getText().equals("0"))
					Ta(Integer.parseInt(textField_1.getText()), Float.parseFloat(textField_2.getText()));
				group.clearSelection();
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_2.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if(!textField_1.getText().isEmpty() && !textField_2.getText().isEmpty() && !textField_1.getText().equals("0") && !textField_2.getText().equals("0"))
					Ta(Integer.parseInt(textField_1.getText()), Float.parseFloat(textField_2.getText()));
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_3.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_4.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_5.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_12.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_13.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_6.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_9.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_10.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_15.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_7.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_11.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		textField_8.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (chckbx.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_7))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_1.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_15))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else if (chckbx_2.isSelected()) {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8)
							&& verifier.verify2(textField_11))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				} else {
					if (verifier.verify(textField_1)
							&& verifier.verify2(textField_2)
							&& verifier.verify2(textField_3)
							&& verifier.verify2(textField_4)
							&& verifier.verify(textField_5)
							&& verifier.verify2(textField_12)
							&& verifier.verify(textField_13)
							&& verifier.verify2(textField_6)
							&& verifier.verify(textField_9)
							&& verifier.verify(textField_10)
							&& verifier.verify2(textField_8))
						btnOk.setEnabled(true);
					else
						btnOk.setEnabled(false);
				}
			}
		});
		/*
		 * InputVerifier verifier = new MyNumericVerifier();
		 * textField_1.setInputVerifier(verifier);
		 */

		final JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setTitle(title);
		f.setSize(300, 90);
		f.setLocationRelativeTo(null);// (WindowManager.MAINWINDOW.getLocation());
		f.setIconImage(new ImageIcon(this.getClass().getResource(
				"/images/logo32.png")).getImage());
		f.getContentPane().setLayout(new MigLayout("", "[grow]", "[][10][]"));
		
		lblNewLabel = new JLabel("Wait while creating jobs :");
		f.getContentPane().add(lblNewLabel, "cell 0 0");
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		f.getContentPane().add(progressBar, "cell 0 2,growx");
		
		btn_cancel = new JButton("Cancel");
		f.getContentPane().add(btn_cancel, "cell 0 2,alignx center");
		
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				final Thread tr = new Thread(new Runnable() {

					@Override
					public void run() {
						try{
							f.setVisible(true);
							createMatlabAndBashFiles(folders, structure);
							f.setVisible(false);
						}catch (Exception e){
							e.printStackTrace();
							WindowManager.mwLogger.log(Level.SEVERE, "Error to create submit files",e);
						}
						
					}
				});
				tr.start();
				btn_cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tr.stop();
						f.dispose();
					}
				});
				
			}
		});
		frame.getRootPane().setDefaultButton(btnOk);
		
		
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
	}

	public void createMatlabAndBashFiles(ArrayList<File> folders,
			FolderStructure structure) {
		ArrayList<String> subdir = new ArrayList<String>();
		ArrayList<String> dossier_filtre = new ArrayList<String>();
		ArrayList<String> path_ss_dossier = new ArrayList<String>();
		ArrayList<String> path_ss_dossier2 = new ArrayList<String>();
		Boolean filt=false;
		Boolean filt2=false;
		File dir = new File(textField_8.getText());
		Long time;
		String nom="";
		if (structure.equals(FolderStructure.PatDatSer)
				|| structure.equals(FolderStructure.PatDatProtSer)) {
			for (int j = 0; j < folders.size(); j++) {
				subdir=findFiles(folders.get(j).toString());
				if(comboBox_2.getSelectedItem().equals("Patient")){
					dossier_filtre.clear();
					dossier_filtre.add(folders.get(j).getName());
				}

				for (int i = 0; i < subdir.size(); i++) {	
					if(comboBox_2.getSelectedItem().equals("Date")){
						if(subdir.get(i).matches("(.*)"+textField_14.getText()+"(.*)"))
							filt2=true;
						else 
							filt2=false;
					}
					else if(comboBox_2.getSelectedItem().equals("Protocol")){
						path_ss_dossier=findFiles2(folders.get(j).toString());
						dossier_filtre=findFiles(path_ss_dossier.get(i).toString());
					}
					else if(comboBox_2.getSelectedItem().equals("Serie") && structure.equals(FolderStructure.PatDatProtSer)){
						path_ss_dossier2=findFiles2(folders.get(j).toString());
						path_ss_dossier=findFiles2(path_ss_dossier2.get(i).toString());
						dossier_filtre.clear();
						for (int k = 0; k < path_ss_dossier.size(); k++) {
							dossier_filtre.addAll(findFiles(path_ss_dossier.get(k).toString()));
						}
					}
					else if(comboBox_2.getSelectedItem().equals("Serie") && structure.equals(FolderStructure.PatDatSer)){
						path_ss_dossier=findFiles2(folders.get(j).toString());
						dossier_filtre=findFiles(path_ss_dossier.get(i).toString());
					}
					filt=false;
					for (int k = 0; k < dossier_filtre.size(); k++) {

						if(dossier_filtre.get(k).matches("(.*)"+textField_14.getText()+"(.*)"))
						{
							filt=true;
						}
					}
					if(textField_14.getText().isEmpty()) {
						time = System.nanoTime();
						nom = "job_" + time.toString();

						try {
							BufferedReader in = new BufferedReader(new FileReader(
									SystemSettings.APP_DIR + File.separator + "lib"
											+ File.separator + "MATLAB"
											+ File.separator
											+ "batch_restingState.m"));
							BufferedWriter writer = new BufferedWriter(
									new FileWriter(new File(dir + File.separator
											+ nom + ".m")));
							String line;
							while ((line = in.readLine()) != null) {
								if (chckbx.isSelected())
									line = line.replace("#1#", "0");
								else if (chckbx_3.isSelected())
									line = line.replace("#1#", "2");
								else
									line = line.replace("#1#", "1");

								line = line.replace("#2#", textField_7.getText());
								if (chckbx_1.isSelected())
									line = line.replace("#3#", "0");
								else if (chckbx_4.isSelected())
									line = line.replace("#3#", "2");
								else
									line = line.replace("#3#", "1");
								if (textField_15.getText().equals(txt))
									line = line.replace("#17#", "1");
								else {
									line = line.replace("#4#", textField_15.getText());
									line = line.replace("#17#", "0");
								}
								line = line.replace("#5#", textField_1.getText());
								line = line.replace("#6#", textField_2.getText());
								line = line.replace("#7#", textField_3.getText());
								line = line.replace("#8#", textField_4.getText());
								line = line.replace("#9#", textField_5.getText());
								line = line.replace("#10#", textField_6.getText());
								line = line.replace("#11#", textField_11.getText());
								line = line.replace("#12#", textField_12.getText());
								line = line.replace("#13#", textField_13.getText());
								if (chckbx_2.isSelected())
									line = line.replace("#14#", "0");
								else
									line = line.replace("#14#", "1");
								line = line.replace("#15#", folders.get(j)
										.toString()
										+ File.separator
										+ subdir.get(i).toString());
								line = line.replace("#16#", folders.get(j)
										.getName());
								line = line.replace("#18#", "0");
								line = line.replace("#19#", subdir.get(i)
										.toString());
								if (structure.equals(FolderStructure.PatDatProtSer))
									line = line.replace("#20#", "0");
								else
									line = line.replace("#20#", "1");
								writer.write(line + "\n");
							}
							in.close();
							writer.close();
							if(comboBox.getSelectedItem().equals("WINDOWS"))
							{writer = new BufferedWriter(new FileWriter(new File(dir
									+ File.separator + nom + ".bat")));}
							else
							{writer = new BufferedWriter(new FileWriter(new File(dir
									+ File.separator + nom + ".sh")));
							writer.write("#!/bin/sh");}
							writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
							writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
									+ nom + ".m\n");
							writer.write("exit\n");
							writer.close();
							String desc = textArea.getText(desc_1.length(),
									textArea.getText().length() - desc_1.length());
							String date = subdir.get(i).toString();
							date = date.substring(0, 4) + "/"
									+ date.substring(4, 6) + "/"
									+ date.substring(6, 8);
							String description = "Patient : "
									+ folders.get(j).getName() + "\n"
									+ "Acquisition date : " + date + "\n" + desc;

							ArrayList<File> files = new ArrayList<>();
							files.add((new File(dir + File.separator + nom + ".m")));
							//files.add((new File(dir + File.separator + nom + ".bat")));
							if(!textField_7.getText().isEmpty())
								files.add((new File(textField_7.getText())));
							if(!textField_15.getText().equals("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m"))
								files.add((new File(textField_15.getText())));
							if(!textField_11.getText().isEmpty())
								files.add((new File(textField_11.getText())));
							if (comboBox.getSelectedItem().equals("WINDOWS")
									&& comboBox_1.getSelectedItem()
									.equals("X86_64"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_9.getText()),
										Integer.parseInt(textField_10.getText()),
										OS.WINDOWS, Arch.X86_64, description);
							if (comboBox.getSelectedItem().equals("WINDOWS")
									&& comboBox_1.getSelectedItem().equals("INTEL"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_9.getText()),
										Integer.parseInt(textField_10.getText()),
										OS.WINDOWS, Arch.INTEL, description);
							if (comboBox.getSelectedItem().equals("UNIX")
									&& comboBox_1.getSelectedItem()
									.equals("X86_64"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_9.getText()),
										Integer.parseInt(textField_10.getText()),
										OS.UNIX, Arch.X86_64, description);
							if (comboBox.getSelectedItem().equals("UNIX")
									&& comboBox_1.getSelectedItem().equals("INTEL"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_9.getText()),
										Integer.parseInt(textField_10.getText()),
										OS.UNIX, Arch.INTEL, description);
						} catch (IOException e) {
							e.printStackTrace();
							WindowManager.mwLogger.log(Level.SEVERE, "Error : cannot create .m file for "+folders.get(j).getName(),e);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {if(filt || filt2) {
						time = System.nanoTime();
						nom = "job_" + time.toString();
						try {
							BufferedReader in = new BufferedReader(new FileReader(
									SystemSettings.APP_DIR + File.separator + "lib"
											+ File.separator + "MATLAB"
											+ File.separator
											+ "batch_restingState.m"));
							BufferedWriter writer = new BufferedWriter(
									new FileWriter(new File(dir + File.separator
											+ nom + ".m")));
							String line;
							while ((line = in.readLine()) != null) {
								if (chckbx.isSelected())
									line = line.replace("#1#", "0");
								else if (chckbx_3.isSelected())
									line = line.replace("#1#", "2");
								else
									line = line.replace("#1#", "1");

								line = line.replace("#2#", textField_7.getText());
								if (chckbx_1.isSelected())
									line = line.replace("#3#", "0");
								else if (chckbx_4.isSelected())
									line = line.replace("#3#", "2");
								else
									line = line.replace("#3#", "1");
								if (textField_15.getText().equals(txt))
									line = line.replace("#17#", "1");
								else {
									line = line.replace("#4#", textField_15.getText());
									line = line.replace("#17#", "0");
								}
								line = line.replace("#5#", textField_1.getText());
								line = line.replace("#6#", textField_2.getText());
								line = line.replace("#7#", textField_3.getText());
								line = line.replace("#8#", textField_4.getText());
								line = line.replace("#9#", textField_5.getText());
								line = line.replace("#10#", textField_6.getText());
								line = line.replace("#11#", textField_11.getText());
								line = line.replace("#12#", textField_12.getText());
								line = line.replace("#13#", textField_13.getText());
								if (chckbx_2.isSelected())
									line = line.replace("#14#", "0");
								else
									line = line.replace("#14#", "1");
								line = line.replace("#15#", folders.get(j)
										.toString()
										+ File.separator
										+ subdir.get(i).toString());
								line = line.replace("#16#", folders.get(j)
										.getName());
								line = line.replace("#18#", "0");
								line = line.replace("#19#", subdir.get(i)
										.toString());
								if (structure.equals(FolderStructure.PatDatProtSer))
									line = line.replace("#20#", "0");
								else
									line = line.replace("#20#", "1");
								writer.write(line + "\n");
							}
							in.close();
							writer.close();
							if(comboBox.getSelectedItem().equals("WINDOWS"))
							{writer = new BufferedWriter(new FileWriter(new File(dir
									+ File.separator + nom + ".bat")));}
							else
							{writer = new BufferedWriter(new FileWriter(new File(dir
									+ File.separator + nom + ".sh")));
							writer.write("#!/bin/sh");}
							writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
							writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
									+ nom + ".m\n");
							writer.write("exit\n");
							writer.close();
							String desc = textArea.getText(desc_1.length(),
									textArea.getText().length() - desc_1.length());
							String date = subdir.get(i).toString();
							date = date.substring(0, 4) + "/"
									+ date.substring(4, 6) + "/"
									+ date.substring(6, 8);
							String description = "Patient : "
									+ folders.get(j).getName() + "\n"
									+ "Acquisition date : " + date + "\n" + desc;

							ArrayList<File> files = new ArrayList<>();
							files.add((new File(dir + File.separator + nom + ".m")));
							if(!textField_7.getText().isEmpty())
								files.add((new File(textField_7.getText())));
							//files.add((new File(dir + File.separator + nom + ".bat")));
							if(!textField_15.getText().equals("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m"))
								files.add((new File(textField_15.getText())));
							if(!textField_11.getText().isEmpty())
								files.add((new File(textField_11.getText())));
							if (comboBox.getSelectedItem().equals("WINDOWS")
									&& comboBox_1.getSelectedItem()
									.equals("X86_64"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_9.getText()),
										Integer.parseInt(textField_10.getText()),
										OS.WINDOWS, Arch.X86_64, description);
							if (comboBox.getSelectedItem().equals("WINDOWS")
									&& comboBox_1.getSelectedItem().equals("INTEL"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_9.getText()),
										Integer.parseInt(textField_10.getText()),
										OS.WINDOWS, Arch.INTEL, description);
							if (comboBox.getSelectedItem().equals("UNIX")
									&& comboBox_1.getSelectedItem()
									.equals("X86_64"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_9.getText()),
										Integer.parseInt(textField_10.getText()),
										OS.UNIX, Arch.X86_64, description);
							if (comboBox.getSelectedItem().equals("UNIX")
									&& comboBox_1.getSelectedItem().equals("INTEL"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_9.getText()),
										Integer.parseInt(textField_10.getText()),
										OS.UNIX, Arch.INTEL, description);
						} catch (IOException e) {
							e.printStackTrace();
							WindowManager.mwLogger.log(Level.SEVERE, "Error : cannot create .m file for "+folders.get(j).getName(),e);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					}
				}
			}
		} else {
			for (int j = 0; j < folders.size(); j++) {
				if(comboBox_2.getSelectedItem().equals("Patient")){
					dossier_filtre.clear();
					dossier_filtre.add(folders.get(j).getName());
				}
				else if(comboBox_2.getSelectedItem().equals("Protocol")){
					dossier_filtre=findFiles(folders.get(j).toString());
				}
				else if(comboBox_2.getSelectedItem().equals("Serie") && structure.equals(FolderStructure.PatProtSer)){
					path_ss_dossier=findFiles2(folders.get(j).toString());
					dossier_filtre.clear();
					for (int k = 0; k < path_ss_dossier.size(); k++) {
						dossier_filtre.addAll(findFiles(path_ss_dossier.get(k).toString()));
					}
				}
				else if(comboBox_2.getSelectedItem().equals("Serie") && structure.equals(FolderStructure.PatSer)){
					dossier_filtre=findFiles(folders.get(j).toString());
				}
				filt=false;
				for (int k = 0; k < dossier_filtre.size(); k++) {

					if(dossier_filtre.get(k).matches("(.*)"+textField_14.getText()+"(.*)"))
					{
						filt=true;
					}
				}
				if(textField_14.getText().isEmpty()) {
					time = System.nanoTime();
					nom = "job_" + time.toString();
					try {
						BufferedReader in = new BufferedReader(new FileReader(
								SystemSettings.APP_DIR + File.separator + "lib"
										+ File.separator + "MATLAB"
										+ File.separator + "batch_restingState.m"));
						BufferedWriter writer = new BufferedWriter(new FileWriter(
								new File(dir + File.separator + nom + ".m")));
						String line;
						while ((line = in.readLine()) != null) {
							if (chckbx.isSelected())
								line = line.replace("#1#", "0");
							else if (chckbx_3.isSelected())
								line = line.replace("#1#", "2");
							else
								line = line.replace("#1#", "1");
							line = line.replace("#2#", textField_7.getText());
							if (chckbx_1.isSelected())
								line = line.replace("#3#", "0");
							else if (chckbx_4.isSelected())
								line = line.replace("#3#", "2");
							else
								line = line.replace("#3#", "1");
							if (textField_15.getText().equals(txt))
								line = line.replace("#17#", "1");
							else {
								line = line.replace("#4#", textField_15.getText());
								line = line.replace("#17#", "0");
							}
							line = line.replace("#5#", textField_1.getText());
							line = line.replace("#6#", textField_2.getText());
							line = line.replace("#7#", textField_3.getText());
							line = line.replace("#8#", textField_4.getText());
							line = line.replace("#9#", textField_5.getText());
							line = line.replace("#10#", textField_6.getText());
							line = line.replace("#11#", textField_11.getText());
							line = line.replace("#12#", textField_12.getText());
							line = line.replace("#13#", textField_13.getText());
							if (chckbx_2.isSelected())
								line = line.replace("#14#", "0");
							else
								line = line.replace("#14#", "1");
							line = line.replace("#15#", folders.get(j).toString());
							line = line.replace("#16#", folders.get(j).getName());
							line = line.replace("#18#", "1");
							line = line.replace("#19#", "");
							if (structure.equals(FolderStructure.PatProtSer))
								line = line.replace("#20#", "0");
							else
								line = line.replace("#20#", "1");
							writer.write(line + "\n");
						}
						in.close();
						writer.close();
						if(comboBox.getSelectedItem().equals("WINDOWS"))
						{writer = new BufferedWriter(new FileWriter(new File(dir
								+ File.separator + nom + ".bat")));}
						else
						{writer = new BufferedWriter(new FileWriter(new File(dir
								+ File.separator + nom + ".sh")));
						writer.write("#!/bin/sh");}
						writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
						writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
								+ nom + ".m\n");
						writer.write("exit\n");
						writer.close();
						String desc = textArea.getText(desc_2.length(), textArea
								.getText().length() - desc_2.length());
						String description = "Patient : "
								+ folders.get(j).getName() + "\n" + desc;
						ArrayList<File> files = new ArrayList<>();
						files.add((new File(dir + File.separator + nom + ".m")));
						if(!textField_7.getText().isEmpty())
							files.add((new File(textField_7.getText())));
						if(!textField_15.getText().equals("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m"))
							files.add((new File(textField_15.getText())));
						if(!textField_11.getText().isEmpty())
							files.add((new File(textField_11.getText())));
						if (comboBox.getSelectedItem().equals("WINDOWS")
								&& comboBox_1.getSelectedItem().equals("X86_64"))
							CondorUtils.submitJob(dir, files, new File(dir
									+ File.separator + nom + ".bat"),
									Integer.parseInt(textField_9.getText()),
									Integer.parseInt(textField_10.getText()),
									OS.WINDOWS, Arch.X86_64, description);
						if (comboBox.getSelectedItem().equals("WINDOWS")
								&& comboBox_1.getSelectedItem().equals("INTEL"))
							CondorUtils.submitJob(dir, files, new File(dir
									+ File.separator + nom + ".bat"),
									Integer.parseInt(textField_9.getText()),
									Integer.parseInt(textField_10.getText()),
									OS.WINDOWS, Arch.INTEL, description);
						if (comboBox.getSelectedItem().equals("UNIX")
								&& comboBox_1.getSelectedItem().equals("X86_64"))
							CondorUtils.submitJob(dir, files, new File(dir
									+ File.separator + nom + ".bat"),
									Integer.parseInt(textField_9.getText()),
									Integer.parseInt(textField_10.getText()),
									OS.UNIX, Arch.X86_64, description);
						if (comboBox.getSelectedItem().equals("UNIX")
								&& comboBox_1.getSelectedItem().equals("INTEL"))
							CondorUtils.submitJob(dir, files, new File(dir
									+ File.separator + nom + ".bat"),
									Integer.parseInt(textField_9.getText()),
									Integer.parseInt(textField_10.getText()),
									OS.UNIX, Arch.INTEL, description);
					} catch (IOException e) {
						e.printStackTrace();
						WindowManager.mwLogger.log(Level.SEVERE, "Error : cannot create .m file for "+folders.get(j).getName(),e);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(filt){
					time = System.nanoTime();
					nom  = "job_" + time.toString();
					try {
						BufferedReader in = new BufferedReader(new FileReader(
								SystemSettings.APP_DIR + File.separator + "lib"
										+ File.separator + "MATLAB"
										+ File.separator + "batch_restingState.m"));
						BufferedWriter writer = new BufferedWriter(new FileWriter(
								new File(dir + File.separator + nom + ".m")));
						String line;
						while ((line = in.readLine()) != null) {
							if (chckbx.isSelected())
								line = line.replace("#1#", "0");
							else if (chckbx_3.isSelected())
								line = line.replace("#1#", "2");
							else
								line = line.replace("#1#", "1");
							line = line.replace("#2#", textField_7.getText());
							if (chckbx_1.isSelected())
								line = line.replace("#3#", "0");
							else if (chckbx_4.isSelected())
								line = line.replace("#3#", "2");
							else
								line = line.replace("#3#", "1");
							if (textField_15.getText().equals(txt))
								line = line.replace("#17#", "1");
							else {
								line = line.replace("#4#", textField_15.getText());
								line = line.replace("#17#", "0");
							}
							line = line.replace("#5#", textField_1.getText());
							line = line.replace("#6#", textField_2.getText());
							line = line.replace("#7#", textField_3.getText());
							line = line.replace("#8#", textField_4.getText());
							line = line.replace("#9#", textField_5.getText());
							line = line.replace("#10#", textField_6.getText());
							line = line.replace("#11#", textField_11.getText());
							line = line.replace("#12#", textField_12.getText());
							line = line.replace("#13#", textField_13.getText());
							if (chckbx_2.isSelected())
								line = line.replace("#14#", "0");
							else
								line = line.replace("#14#", "1");
							line = line.replace("#15#", folders.get(j).toString());
							line = line.replace("#16#", folders.get(j).getName());
							line = line.replace("#18#", "1");
							line = line.replace("#19#", "");
							if (structure.equals(FolderStructure.PatProtSer))
								line = line.replace("#20#", "0");
							else
								line = line.replace("#20#", "1");
							writer.write(line + "\n");
						}
						in.close();
						writer.close();
						if(comboBox.getSelectedItem().equals("WINDOWS"))
						{writer = new BufferedWriter(new FileWriter(new File(dir
								+ File.separator + nom + ".bat")));}
						else
						{writer = new BufferedWriter(new FileWriter(new File(dir
								+ File.separator + nom + ".sh")));
						writer.write("#!/bin/sh");}
						writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
						writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
								+ nom + ".m\n");
						writer.write("exit\n");
						writer.close();
						String desc = textArea.getText(desc_2.length(), textArea
								.getText().length() - desc_2.length());
						String description = "Patient : "
								+ folders.get(j).getName() + "\n" + desc;
						ArrayList<File> files = new ArrayList<>();
						files.add((new File(dir + File.separator + nom + ".m")));
						if(!textField_7.getText().isEmpty())
							files.add((new File(textField_7.getText())));
						if(!textField_15.getText().equals("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m"))
							files.add((new File(textField_15.getText())));
						if(!textField_11.getText().isEmpty())
							files.add((new File(textField_11.getText())));
						if (comboBox.getSelectedItem().equals("WINDOWS")
								&& comboBox_1.getSelectedItem().equals("X86_64"))
							CondorUtils.submitJob(dir, files, new File(dir
									+ File.separator + nom + ".bat"),
									Integer.parseInt(textField_9.getText()),
									Integer.parseInt(textField_10.getText()),
									OS.WINDOWS, Arch.X86_64, description);
						if (comboBox.getSelectedItem().equals("WINDOWS")
								&& comboBox_1.getSelectedItem().equals("INTEL"))
							CondorUtils.submitJob(dir, files, new File(dir
									+ File.separator + nom + ".bat"),
									Integer.parseInt(textField_9.getText()),
									Integer.parseInt(textField_10.getText()),
									OS.WINDOWS, Arch.INTEL, description);
						if (comboBox.getSelectedItem().equals("UNIX")
								&& comboBox_1.getSelectedItem().equals("X86_64"))
							CondorUtils.submitJob(dir, files, new File(dir
									+ File.separator + nom + ".bat"),
									Integer.parseInt(textField_9.getText()),
									Integer.parseInt(textField_10.getText()),
									OS.UNIX, Arch.X86_64, description);
						if (comboBox.getSelectedItem().equals("UNIX")
								&& comboBox_1.getSelectedItem().equals("INTEL"))
							CondorUtils.submitJob(dir, files, new File(dir
									+ File.separator + nom + ".bat"),
									Integer.parseInt(textField_9.getText()),
									Integer.parseInt(textField_10.getText()),
									OS.UNIX, Arch.INTEL, description);
					} catch (IOException e) {
						e.printStackTrace();
						WindowManager.mwLogger.log(Level.SEVERE, "Error : cannot create .m file for "+folders.get(j).getName(),e);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public class MyVerifier /* extends InputVerifier */{
		public boolean verify(JComponent input) {
			String text = null;

			if (input instanceof JTextField) {
				text = ((JTextField) input).getText();
			}
			try {
				Integer.parseInt(text);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}

		public boolean verify2(JComponent input) {
			String text = null;

			if (input instanceof JTextField) {
				text = ((JTextField) input).getText();
			}

			if (text.isEmpty())
				return false;
			else
				return true;
		}

		/*
		 * @Override public boolean shouldYieldFocus(JComponent input) { boolean
		 * valid = verify(input); if (!valid) {
		 * JOptionPane.showMessageDialog(null, "Invalid data"); } return valid;
		 * }
		 */
	}

	public static ArrayList<String> findFiles(String directoryPath) {
		File directory = new File(directoryPath);
		ArrayList<String> subdir = new ArrayList<String>();
		if (!directory.exists()) {
			System.out.println("Le fichier/r�pertoire '" + directoryPath
					+ "' n'existe pas");
		} else if (!directory.isDirectory()) {
			System.out.println("Le chemin '" + directoryPath
					+ "' correspond � un fichier et non � un r�pertoire");
		} else {
			File[] subfiles = directory.listFiles();
			for (int i = 0; i < subfiles.length; i++) {
				if (subfiles[i].isDirectory()) {
					subdir.add(subfiles[i].getName());
					//System.out.println(subfiles[i].getName());
				}
			}
		}
		return subdir;
	}
	public ArrayList<String> findFiles2(String directoryPath) {
		File directory = new File(directoryPath);
		ArrayList<String> subdir = new ArrayList<String>();
		if (!directory.exists()) {
			System.out.println("Le fichier/r�pertoire '" + directoryPath
					+ "' n'existe pas");
		} else if (!directory.isDirectory()) {
			System.out.println("Le chemin '" + directoryPath
					+ "' correspond � un fichier et non � un r�pertoire");
		} else {
			File[] subfiles = directory.listFiles();
			for (int i = 0; i < subfiles.length; i++) {
				if (subfiles[i].isDirectory()) {
					subdir.add(subfiles[i].getAbsolutePath());
					//System.out.println(subfiles[i].getName());
				}
			}
		}
		return subdir;
	}

	private class Filter extends DocumentFilter {
		public void insertString(final FilterBypass fb, final int offset,
				final String string, AttributeSet attr)
						throws BadLocationException {
			if (offset >= desc_1.length() && offset <= 80) {
				super.insertString(fb, offset, string, attr);
			}
		}

		public void remove(final FilterBypass fb, final int offset,
				final int length) throws BadLocationException {
			if (offset >= desc_1.length() && offset <= 80) {
				super.remove(fb, offset, length);
			}
		}

		public void replace(final FilterBypass fb, final int offset,
				final int length, final String text, final AttributeSet attrs)
						throws BadLocationException {

			if (offset >= desc_1.length() && offset <= 80) {
				super.replace(fb, offset, length, text, attrs);
			}
		}
	}

	private class Filter2 extends DocumentFilter {
		public void insertString(final FilterBypass fb, final int offset,
				final String string, AttributeSet attr)
						throws BadLocationException {
			if (offset >= desc_2.length()) {
				super.insertString(fb, offset, string, attr);
			}
		}

		public void remove(final FilterBypass fb, final int offset,
				final int length) throws BadLocationException {
			if (offset >= desc_2.length()) {
				super.remove(fb, offset, length);
			}
		}

		public void replace(final FilterBypass fb, final int offset,
				final int length, final String text, final AttributeSet attrs)
						throws BadLocationException {
			if (text.contains("\n")) {
				if (offset >= desc_2.length() && offset < 20) {
					super.replace(fb, offset, length, text, attrs);
				}
			} else {
				if (offset >= desc_2.length() && offset < 80) {
					super.replace(fb, offset, length, text, attrs);
				}
			}
		}
	}
	private void So(int nb,int e){
		String res = "";
		String res2 = "";
		int i=0;
		int j=0;
		if(e==0){
			for(i=1;i<=nb;i=i+2){
				res=res+i+" ";
				res2=""+i;
			}
			for(i=2;i<=nb;i=i+2){
				if(i==2)
					res=res+i;
				else
					res=res+" "+i;
			}
		}
		else
		{
			for(i=1;i<=nb;i++){
				if(i==1)
					res=res+i;
				else
					res=res+" "+i;
			}
			j=nb/2;
			res2=""+j;
		}

		textField_4.setText(res);
		textField_5.setText(res2);
	}
	private void Ta(int nb,float tr)
	{
		float res;
		res=tr-tr/nb;
		textField_3.setText(String.format("%.3f", res));
	}
}
