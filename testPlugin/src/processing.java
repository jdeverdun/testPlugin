
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import net.miginfocom.swing.MigLayout;
import plugins.FolderProcessingPlugins;
import settings.SystemSettings;
import settings.WindowManager;
import tools.cluster.condor.CondorUtils;
import tools.cluster.condor.CondorUtils.Arch;
import tools.cluster.condor.CondorUtils.OS;
import display.MainWindow;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class processing implements FolderProcessingPlugins {
	private JFrame frame;
	private String title = "Resting State ";
	private String txt;
	private String desc_1 = "Patient : \"name\" \nAcquisition Date : \"date\"\n";
	private String desc_2 = "Patient : \"name\" \n";
	private JPanel panel_Option;
	private JPanel panel_SliceTiming;
	private JPanel panel_Smooth;
	private JPanel panel_OkClose;
	private JPanel panel_ParamCondor;
	private JPanel panel_Norm;
	private JPanel panel_ParamLocal;
	private JTextField textField_NSlice;
	private JTextField textField_Tr;
	private JTextField textField_Ta;
	private JTextField textField_So;
	private JTextField textField_RefSlice;
	private JTextField textField_Fwhm;
	private JTextField textField_Reor;
	private JTextField textField_JobPath;
	private JTextField textField_NbCpu;
	private JTextField textField_NbMem;
	private JTextField textField_NormTempl;
	private JTextField textField_Vox;
	private JTextField textField_Interp;
	private JTextField textField_filter;
	private JTextField textField_Presub;
	private JTextField textField_16;
	private JTextField textField_RmImage;
	private JTextField textField_ExeMatlab;
	private JLabel lblReorientation;
	private JLabel lblPresub;
	private JLabel lblNslices;
	private JLabel lblTr;
	private JLabel lblTa;
	private JLabel lblSo;
	private JLabel lblRefslice;
	private JLabel lblVox;
	private JLabel lblInterp;
	private JLabel lblFwhm;
	private JLabel lblJobPath;
	private JLabel lblNbCpu;
	private JLabel lblNbMem;
	private JLabel lblOp;
	private JLabel lblArch;
	private JLabel lblNormTempl;
	private JLabel lblReorientation2;
	private JLabel Filter;
	private JLabel lblPresub2;
	private JLabel lblMachine;
	private JLabel lblDescription;
	private JLabel HowRun;
	private JLabel lblNewLabel;
	private JLabel lblRmImage;
	private JLabel lblExeMatlab;
	private JButton btnOk;
	private JButton btnClose;
	private JButton btnSelect_Reor;
	private JButton btnSelect_Presub;
	private JButton btnSelect_JobPath;
	private JButton btnSelect_NormTempl;
	private JButton btnSelect_ExeMatlab;
	private JButton btn_cancel;
	private JCheckBox chckbx_Reor;
	private JCheckBox chckbx_Presub;
	private JCheckBox chckbx_NormTempl;
	private JCheckBox chckbx_Reor2;
	private JCheckBox chckbx_Presub2;
	private JCheckBox chckbx_Interlaced;
	private JCheckBox chckbx_NonInterlaced;
	private JCheckBox chckbx_Condor;
	private JCheckBox chckbx_Local;
	private JComboBox<String> comboBox_Op;
	private JComboBox<String> comboBox_Arch;
	private JComboBox<String> comboBox_Filter;
	private JComboBox<String> comboBox_Machine;
	private boolean isSubmissionDone = false;
	private JScrollPane scrollPane;
	private JTextArea textArea_desc;
	private JProgressBar progressBar;
	private ImageIcon icon;
	private Image img;
	private Image newimg;
	private ImageIcon icon2;

	@Override
	public PluginCategory getCategory() {
		return PluginCategory.BatchImageProcessing;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Processing";
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
		frame.setSize(800, 930);
		frame.setTitle(title);
		frame.setLocationRelativeTo(null);// (WindowManager.MAINWINDOW.getLocation());
		frame.setIconImage(new ImageIcon(this.getClass().getResource(
				"/images/logo32.png")).getImage());
		frame.getContentPane().setLayout(new MigLayout("", "[grow]","[112.00,grow][97.00,grow][68.00,grow][53.00,grow][240.00,grow][34.00,grow]"));
		frame.setVisible(true);

		icon = new ImageIcon(MainWindow.class.getResource("/images/folder.png"));
		img = icon.getImage();
		newimg = img.getScaledInstance(20, 20,java.awt.Image.SCALE_SMOOTH);
		icon2 = new ImageIcon(newimg);

		panel_ParamLocal = new JPanel();
		panel_ParamLocal.setBorder(new TitledBorder(null, "Paramaters",TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_ParamLocal, "cell 0 4,grow");
		panel_ParamLocal.setLayout(new MigLayout("","[15.00][246.00][44.00,grow,leading][grow][][]","[][][][][][grow]"));
		lblExeMatlab = new JLabel("Give the Matlab executable path");
		panel_ParamLocal.add(lblExeMatlab, "cell 1 0");
		textField_ExeMatlab = new JTextField();
		textField_ExeMatlab.setName("S_ExePath");
		panel_ParamLocal.add(textField_ExeMatlab, "cell 2 0 2 1,growx");
		textField_ExeMatlab.setColumns(10);
		btnSelect_ExeMatlab = new JButton(icon2);
		panel_ParamLocal.add(btnSelect_ExeMatlab, "cell 4 0");
		frame.remove(panel_ParamLocal);
		frame.repaint();

		panel_Option = new JPanel();
		panel_Option.setBorder(new TitledBorder(null, "Optionnal option",TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_Option, "cell 0 0,grow");
		panel_Option.setLayout(new MigLayout("", "[15.00][246.00][44.00][grow][][]","[][][][10][][-12.00][]"));

		HowRun = new JLabel("How would you run the batch?");
		panel_Option.add(HowRun,"cell 1 0");

		chckbx_Condor = new JCheckBox("Condor");
		panel_Option.add(chckbx_Condor, "flowx,cell 2 0,alignx center");
		chckbx_Condor.setSelected(true);
		chckbx_Local = new JCheckBox("Local");
		panel_Option.add(chckbx_Local, "cell 3 0");
		ButtonGroup group2 = new ButtonGroup();
		group2.add(chckbx_Condor);
		group2.add(chckbx_Local);

		Filter = new JLabel("Filter");
		panel_Option.add(Filter,"cell 1 1");

		comboBox_Filter = new JComboBox<String>();
		comboBox_Filter.removeAllItems();
		if (structure.equals(FolderStructure.PatDatProtSer)){
			comboBox_Filter.addItem("");
			comboBox_Filter.addItem("Patient");
			comboBox_Filter.addItem("Date");
			comboBox_Filter.addItem("Protocol");
			comboBox_Filter.addItem("Serie");
		}
		else if(structure.equals(FolderStructure.PatDatSer)){
			comboBox_Filter.addItem("");
			comboBox_Filter.addItem("Patient");
			comboBox_Filter.addItem("Date");
			comboBox_Filter.addItem("Serie");
		}
		else if(structure.equals(FolderStructure.PatProtSer)){
			comboBox_Filter.addItem("");
			comboBox_Filter.addItem("Patient");
			comboBox_Filter.addItem("Protocol");
			comboBox_Filter.addItem("Serie");
		}
		else if(structure.equals(FolderStructure.PatSer)){
			comboBox_Filter.addItem("");
			comboBox_Filter.addItem("Patient");
			comboBox_Filter.addItem("Serie");
		}
		panel_Option.add(comboBox_Filter, "cell 2 1, growx");
		textField_filter = new JTextField();
		textField_filter.setName("S_Filter");
		panel_Option.add(textField_filter,"cell 3 1");
		textField_filter.setColumns(10);

		lblRmImage =new JLabel("Doesn't take the x first resting state image");
		panel_Option.add(lblRmImage, "cell 1 2");
		textField_RmImage = new JTextField("");
		textField_RmImage.setName("N_RmImage");
		panel_Option.add(textField_RmImage,"cell 2 2, growx");
		textField_RmImage.setColumns(10);

		lblReorientation = new JLabel("Check for reorientation");
		panel_Option.add(lblReorientation, "cell 1 3");
		chckbx_Reor = new JCheckBox("");

		panel_Option.add(chckbx_Reor, "cell 2 3,alignx center");

		textField_Reor = new JTextField();
		textField_Reor.setName("S_Reor");
		textField_Reor.setVisible(false);
		panel_Option.add(textField_Reor, "cell 3 3,growx");
		textField_Reor.setColumns(10);

		lblReorientation2 = new JLabel(
				"Check if you want use existing reorientation files");
		panel_Option.add(lblReorientation2, "cell 1 4");

		chckbx_Reor2 = new JCheckBox("");
		panel_Option.add(chckbx_Reor2, "cell 2 4,alignx center");

		lblPresub = new JLabel(
				"Check for presubstract phase and magnitude");
		panel_Option.add(lblPresub, "cell 1 5");
		chckbx_Presub = new JCheckBox("");
		panel_Option.add(chckbx_Presub, "cell 2 5,alignx center");


		txt = "(matlabroot)" + File.separator + "toolbox" + File.separator
				+ "FieldMap" + File.separator + "pm_defaults_skyra.m";
		textField_Presub = new JTextField("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m");
		textField_Presub.setName("S_Presub");
		textField_Presub.setVisible(false);
		panel_Option.add(textField_Presub, "cell 3 5,growx");
		textField_Presub.setColumns(10);
		btnSelect_Reor = new JButton(icon2);
		btnSelect_Reor.setVisible(false);
		panel_Option.add(btnSelect_Reor, "cell 4 3");
		btnSelect_Presub = new JButton(icon2);
		btnSelect_Presub.setVisible(false);
		panel_Option.add(btnSelect_Presub, "cell 4 5");

		lblPresub2 = new JLabel(
				"Check if you want use existing presubstract files");
		panel_Option.add(lblPresub2, "cell 1 6");

		chckbx_Presub2 = new JCheckBox("");
		panel_Option.add(chckbx_Presub2, "cell 2 6,alignx center");

		panel_SliceTiming = new JPanel();
		panel_SliceTiming.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Slice timing",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_SliceTiming, "cell 0 1,grow");
		panel_SliceTiming.setLayout(new MigLayout("", "[15.00][246.00][44.00][grow][][]",
				"[][][][][]"));

		lblNslices = new JLabel("nSlices");
		panel_SliceTiming.add(lblNslices, "cell 1 0");

		textField_NSlice = new JTextField();
		textField_NSlice.setName("N_nSlices");

		textField_NSlice.setText("39");
		panel_SliceTiming.add(textField_NSlice, "cell 2 0");
		textField_NSlice.setColumns(10);
		chckbx_Interlaced = new JCheckBox("Interlaced");
		panel_SliceTiming.add(chckbx_Interlaced, "flowx,cell 3 0,alignx center");
		chckbx_Interlaced.setSelected(true);
		chckbx_NonInterlaced = new JCheckBox("Non-interlaced");
		panel_SliceTiming.add(chckbx_NonInterlaced, "cell 3 0");
		final ButtonGroup group = new ButtonGroup();
		group.add(chckbx_Interlaced);
		group.add(chckbx_NonInterlaced);

		lblTr = new JLabel("Tr");
		panel_SliceTiming.add(lblTr, "cell 1 1");

		textField_Tr = new JTextField();
		textField_Tr.setName("S_Tr");
		textField_Tr.setText("2.4");
		textField_Tr.setColumns(10);
		panel_SliceTiming.add(textField_Tr, "cell 2 1");

		lblTa = new JLabel("Ta");
		panel_SliceTiming.add(lblTa, "cell 1 2");

		textField_Ta = new JTextField();
		textField_Ta.setName("S_Ta");
		textField_Ta.setText("2.339");
		textField_Ta.setColumns(10);
		panel_SliceTiming.add(textField_Ta, "cell 2 2");

		lblSo = new JLabel("So");
		panel_SliceTiming.add(lblSo, "cell 1 3");

		textField_So = new JTextField();
		textField_So.setName("S_So");
		textField_So
		.setText("1 3 5 7 9 11 13 15 17 19 21 23 25 27 29 31 33 35 37 39 2 4 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38");
		textField_So.setColumns(10);
		panel_SliceTiming.add(textField_So, "cell 2 3 3 1,growx");

		lblRefslice = new JLabel("RefSlice");
		panel_SliceTiming.add(lblRefslice, "cell 1 4");

		textField_RefSlice = new JTextField();
		textField_RefSlice.setName("N_RefSlice");
		textField_RefSlice.setText("39");
		textField_RefSlice.setColumns(10);
		panel_SliceTiming.add(textField_RefSlice, "cell 2 4");


		panel_Norm = new JPanel();
		panel_Norm.setBorder(new TitledBorder(null, "Normalisation",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_Norm, "cell 0 2,grow");
		panel_Norm.setLayout(new MigLayout("", "[15.00][246.00][44.00][grow][][]",
				"[][][]"));

		lblNormTempl = new JLabel("Check to change the default spm template");
		panel_Norm.add(lblNormTempl, "cell 1 0");

		chckbx_NormTempl = new JCheckBox("");
		panel_Norm.add(chckbx_NormTempl, "cell 2 0,alignx center");

		textField_NormTempl = new JTextField();
		textField_NormTempl.setName("S_NormTempl");
		textField_NormTempl.setVisible(false);
		panel_Norm.add(textField_NormTempl, "cell 3 0,growx");
		textField_NormTempl.setColumns(10);

		btnSelect_NormTempl = new JButton(icon2);
		btnSelect_NormTempl.setVisible(false);
		panel_Norm.add(btnSelect_NormTempl, "cell 4 0");

		lblVox = new JLabel("Vox");
		panel_Norm.add(lblVox, "cell 1 1");

		textField_Vox = new JTextField();
		textField_Vox.setName("S_Vox");
		textField_Vox.setText("2 2 2");
		panel_Norm.add(textField_Vox, "cell 2 1");
		textField_Vox.setColumns(10);

		lblInterp = new JLabel("Interp");
		panel_Norm.add(lblInterp, "cell 1 2");

		textField_Interp = new JTextField();
		textField_Interp.setName("N_Interp");
		textField_Interp.setText("1");
		panel_Norm.add(textField_Interp, "cell 2 2");
		textField_Interp.setColumns(10);

		panel_Smooth = new JPanel();
		panel_Smooth.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Smooth",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_Smooth, "cell 0 3,grow");
		panel_Smooth.setLayout(new MigLayout("", "[15.00][246.00][44.00][grow][][]",
				"[][][][][]"));

		lblFwhm = new JLabel("Fwhm");
		lblFwhm.setName("");
		panel_Smooth.add(lblFwhm, "cell 1 0");

		textField_Fwhm = new JTextField();
		textField_Fwhm.setName("S_Fwhm");
		textField_Fwhm.setText("6 6 6");
		textField_Fwhm.setColumns(10);
		panel_Smooth.add(textField_Fwhm, "cell 2 0");

		panel_ParamCondor = new JPanel();
		panel_ParamCondor.setBorder(new TitledBorder(null, "Paramaters of the submit",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_ParamCondor, "cell 0 4,grow");
		panel_ParamCondor.setLayout(new MigLayout("",
				"[15.00][246.00][44.00,grow,leading][grow][][]",
				"[][][][][][grow]"));

		lblJobPath = new JLabel("Path where are created jobs files");
		panel_ParamCondor.add(lblJobPath, "cell 1 0");

		textField_JobPath = new JTextField(SystemSettings.SERVER_INFO.getCondorJobDir().toString());
		textField_JobPath.setName("S_JobPath");
		panel_ParamCondor.add(textField_JobPath, "cell 2 0 2 1,growx");
		textField_JobPath.setColumns(10);

		btnSelect_JobPath = new JButton(icon2);
		panel_ParamCondor.add(btnSelect_JobPath, "cell 4 0");

		lblNbCpu = new JLabel("Number of cpus");
		panel_ParamCondor.add(lblNbCpu, "cell 1 1");

		textField_NbCpu = new JTextField();
		textField_NbCpu.setName("N_NbCpu");
		textField_NbCpu.setText("2");
		panel_ParamCondor.add(textField_NbCpu, "cell 2 1");
		textField_NbCpu.setColumns(10);

		lblNbMem = new JLabel("Number of memory");
		panel_ParamCondor.add(lblNbMem, "cell 1 2");

		textField_NbMem = new JTextField();
		textField_NbMem.setName("N_NbMem");
		textField_NbMem.setText("3000");
		panel_ParamCondor.add(textField_NbMem, "cell 2 2");
		textField_NbMem.setColumns(10);

		lblOp = new JLabel("Choose the operating system");
		panel_ParamCondor.add(lblOp, "cell 1 3");

		comboBox_Op = new JComboBox<String>();
		comboBox_Op.removeAllItems();
		comboBox_Op.addItem("WINDOWS");
		comboBox_Op.addItem("UNIX");
		panel_ParamCondor.add(comboBox_Op, "cell 2 3");

		lblArch = new JLabel("Choose the architecture");
		panel_ParamCondor.add(lblArch, "cell 1 4");

		comboBox_Arch = new JComboBox<String>();
		comboBox_Arch.removeAllItems();
		comboBox_Arch.addItem("X86_64");
		comboBox_Arch.addItem("INTEL");
		panel_ParamCondor.add(comboBox_Arch, "cell 2 4");

		lblMachine = new JLabel("Choose the machine to run job (optionnal)");
		panel_ParamCondor.add(lblMachine, "cell 1 5");

		/*textField_16 = new JTextField();
		textField_16.setText("ARNGDC2-DATA");
		panel_4.add(textField_16, "cell 2 5");
		textField_10.setColumns(10);*/
		comboBox_Machine = new JComboBox<String>();
		comboBox_Machine.removeAllItems();
		comboBox_Machine.addItem("");
		try {
			java.lang.Runtime cs = java.lang.Runtime.getRuntime();
			java.lang.Process p = cs.exec("condor_status -autoformat Machine");
			//System.out.println("Process exited with code = " + p.exitValue());
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			// And print each line
			String ligne = null;
			ArrayList<String> sortie_condorq = new ArrayList<String>();
			try {
				while ((ligne = reader.readLine()) != null) {
					if (ligne.isEmpty()==false){
						comboBox_Machine.addItem(ligne);;}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			p.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			WindowManager.mwLogger.log(Level.SEVERE, "Error : cannot run condor_q",e);
			e.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		panel_ParamCondor.add(comboBox_Machine, "cell 2 5");

		lblDescription = new JLabel("Description");
		panel_ParamCondor.add(lblDescription, "cell 1 6");

		scrollPane = new JScrollPane();
		panel_ParamCondor.add(scrollPane, "cell 2 6,grow");

		textArea_desc = new JTextArea();
		scrollPane.setViewportView(textArea_desc);
		DocumentFilter dfilter = new Filter();
		DocumentFilter dfilter2 = new Filter2();
		if (structure.equals(FolderStructure.PatDatSer)
				|| structure.equals(FolderStructure.PatDatProtSer)) {
			textArea_desc.setText(desc_1);
			((AbstractDocument) textArea_desc.getDocument())
			.setDocumentFilter(dfilter);
		} else {
			textArea_desc.setText(desc_2);
			((AbstractDocument) textArea_desc.getDocument())
			.setDocumentFilter(dfilter2);
		}

		panel_OkClose = new JPanel();
		frame.getContentPane().add(panel_OkClose, "cell 0 5,grow");
		panel_OkClose.setLayout(new MigLayout("", "[397.00,grow][360.00,grow]", "[][]"));

		btnOk = new JButton("OK");
		panel_OkClose.add(btnOk, "cell 0 0,growx");
		// btnOk.setEnabled(false);

		btnClose = new JButton("Close");
		panel_OkClose.add(btnClose, "cell 1 0,growx");

		btnSelect_Reor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_Reor.setText(file.getAbsolutePath());
				}
			}
		});
		btnSelect_Presub.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_Presub.setText(file.getAbsolutePath());
				}
			}
		});
		btnSelect_JobPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_JobPath.setText(file.getAbsolutePath());
				}
			}
		});

		btnSelect_NormTempl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_NormTempl.setText(file.getAbsolutePath());
				}
			}
		});
		btnSelect_ExeMatlab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_ExeMatlab.setText(file.getAbsolutePath());
				}
			}
		});

		final MyVerifier verifier = new MyVerifier();
		chckbx_Reor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_Reor.setVisible(true);
					btnSelect_Reor.setVisible(true);
					chckbx_Reor2.setEnabled(false);
					lblReorientation2.setEnabled(false);
				} else {
					textField_Reor.setVisible(false);
					btnSelect_Reor.setVisible(false);
					chckbx_Reor2.setEnabled(true);
					lblReorientation2.setEnabled(true);
				}
			}
		});
		chckbx_Presub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_Presub.setVisible(true);
					btnSelect_Presub.setVisible(true);
					chckbx_Presub2.setEnabled(false);
					lblPresub2.setEnabled(false);
				} else {
					textField_Presub.setVisible(false);
					btnSelect_Presub.setVisible(false);
					chckbx_Presub2.setEnabled(true);
					lblPresub2.setEnabled(true);
				}
			}
		});
		chckbx_NormTempl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_NormTempl.setVisible(true);
					btnSelect_NormTempl.setVisible(true);
				} else {
					textField_NormTempl.setVisible(false);
					btnSelect_NormTempl.setVisible(false);
				}
			}
		});
		chckbx_Reor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_Reor.setEnabled(false);
					btnSelect_Reor.setEnabled(false);
					lblReorientation.setEnabled(false);
					chckbx_Reor.setEnabled(false);
				} else {
					textField_Reor.setEnabled(true);
					btnSelect_Reor.setEnabled(true);
					lblReorientation.setEnabled(true);
					chckbx_Reor.setEnabled(true);
				}
			}
		});
		chckbx_Presub2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					textField_Presub.setEnabled(false);
					btnSelect_Presub.setEnabled(false);
					lblPresub.setEnabled(false);
					chckbx_Presub.setEnabled(false);
				} else {
					textField_Presub.setEnabled(true);
					btnSelect_Presub.setEnabled(true);
					lblPresub.setEnabled(true);
					chckbx_Presub.setEnabled(true);
				}
			}
		});
		chckbx_Interlaced.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				So(Integer.parseInt(textField_NSlice.getText()),0);
			}
		});
		chckbx_NonInterlaced.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				So(Integer.parseInt(textField_NSlice.getText()),1);	
			}
		});

		textField_NSlice.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if(!textField_NSlice.getText().isEmpty() && !textField_Tr.getText().isEmpty() && !textField_NSlice.getText().equals("0") && !textField_Tr.getText().equals("0"))
					Ta(Integer.parseInt(textField_NSlice.getText()), Float.parseFloat(textField_Tr.getText()));
				group.clearSelection();
			}
		});
		textField_Tr.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if(!textField_NSlice.getText().isEmpty() && !textField_Tr.getText().isEmpty() && !textField_NSlice.getText().equals("0") && !textField_Tr.getText().equals("0"))
					Ta(Integer.parseInt(textField_NSlice.getText()), Float.parseFloat(textField_Tr.getText()));
			}
		});

		chckbx_Condor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				frame.remove(panel_ParamLocal);
				frame.getContentPane().add(panel_ParamCondor, "cell 0 4,grow");
				frame.setSize(800, 930);
				frame.repaint();
			}
		});
		chckbx_Local.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				frame.remove(panel_ParamCondor);
				frame.getContentPane().add(panel_ParamLocal, "cell 0 4,grow");
				frame.setSize(800,780);
				frame.repaint();
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
				if(VerifyJText()){
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
		String date="";
		String machine = comboBox_Machine.getSelectedItem().toString();
		String description="";
		File dir = new File(textField_JobPath.getText());
		Long time;
		String nom="";
		if (structure.equals(FolderStructure.PatDatSer)
				|| structure.equals(FolderStructure.PatDatProtSer)) {
			for (int j = 0; j < folders.size(); j++) {
				subdir=findFiles(folders.get(j).toString(),0);
				if(comboBox_Filter.getSelectedItem().equals("Patient")){
					dossier_filtre.clear();
					dossier_filtre.add(folders.get(j).getName());
				}

				for (int i = 0; i < subdir.size(); i++) {	
					if(comboBox_Filter.getSelectedItem().equals("Date")){
						if(subdir.get(i).matches("(.*)"+textField_filter.getText()+"(.*)"))
							filt2=true;
						else 
							filt2=false;
					}
					else if(comboBox_Filter.getSelectedItem().equals("Protocol")){
						path_ss_dossier=findFiles2(folders.get(j).toString(),0);
						dossier_filtre=findFiles(path_ss_dossier.get(i).toString(),0);
					}
					else if(comboBox_Filter.getSelectedItem().equals("Serie") && structure.equals(FolderStructure.PatDatProtSer)){
						path_ss_dossier2=findFiles2(folders.get(j).toString(),0);
						path_ss_dossier=findFiles2(path_ss_dossier2.get(i).toString(),0);
						dossier_filtre.clear();
						for (int k = 0; k < path_ss_dossier.size(); k++) {
							dossier_filtre.addAll(findFiles(path_ss_dossier.get(k).toString(),0));
						}
					}
					else if(comboBox_Filter.getSelectedItem().equals("Serie") && structure.equals(FolderStructure.PatDatSer)){
						path_ss_dossier=findFiles2(folders.get(j).toString(),0);
						dossier_filtre=findFiles(path_ss_dossier.get(i).toString(),0);
					}
					filt=false;
					for (int k = 0; k < dossier_filtre.size(); k++) {

						if(dossier_filtre.get(k).matches("(.*)"+textField_filter.getText()+"(.*)"))
						{
							filt=true;
						}
					}
					if(textField_filter.getText().isEmpty()) {
						time = System.nanoTime();
						nom = "job_" + time.toString();

						try {
							BufferedReader in = new BufferedReader(new FileReader(
									SystemSettings.APP_DIR + File.separator + "lib"
											+ File.separator + "MATLAB"
											+ File.separator
											+ "batch.m"));
							BufferedWriter writer = new BufferedWriter(
									new FileWriter(new File(dir + File.separator
											+ nom + ".m")));
							String line;
							while ((line = in.readLine()) != null) {
								if (chckbx_Reor.isSelected())
									line = line.replace("#1#", "0");
								else if (chckbx_Reor2.isSelected())
									line = line.replace("#1#", "2");
								else
									line = line.replace("#1#", "1");

								line = line.replace("#2#", textField_Reor.getText());
								if (chckbx_Presub.isSelected())
									line = line.replace("#3#", "0");
								else if (chckbx_Presub2.isSelected())
									line = line.replace("#3#", "2");
								else
									line = line.replace("#3#", "1");
								if (textField_Presub.getText().equals(txt))
									line = line.replace("#17#", "1");
								else {
									line = line.replace("#4#", textField_Presub.getText());
									line = line.replace("#17#", "0");
								}
								line = line.replace("#5#", textField_NSlice.getText());
								line = line.replace("#6#", textField_Tr.getText());
								line = line.replace("#7#", textField_Ta.getText());
								line = line.replace("#8#", textField_So.getText());
								line = line.replace("#9#", textField_RefSlice.getText());
								line = line.replace("#10#", textField_Fwhm.getText());
								line = line.replace("#11#", textField_NormTempl.getText());
								line = line.replace("#12#", textField_Vox.getText());
								line = line.replace("#13#", textField_Interp.getText());
								if (chckbx_NormTempl.isSelected())
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
								if(textField_RmImage.getText().isEmpty())
									line = line.replace("#21#", "1");
								else 
									line = line.replace("#21#", "0");
								line = line.replace("#22#", textField_RmImage.getText());
								writer.write(line + "\n");
							}
							in.close();
							writer.close();
							ArrayList<File> files = new ArrayList<>();
							files.add((new File(dir + File.separator + nom + ".m")));
							//files.add((new File(dir + File.separator + nom + ".bat")));
							if(!textField_Reor.getText().isEmpty())
								files.add((new File(textField_Reor.getText())));
							if(!textField_Presub.getText().equals("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m"))
								files.add((new File(textField_Presub.getText())));
							if(!textField_NormTempl.getText().isEmpty())
								files.add((new File(textField_NormTempl.getText())));
							if(chckbx_Condor.isSelected()){
								if(comboBox_Op.getSelectedItem().equals("WINDOWS"))
								{writer = new BufferedWriter(new FileWriter(new File(dir
										+ File.separator + nom + ".bat")));}
								else
								{writer = new BufferedWriter(new FileWriter(new File(dir
										+ File.separator + nom + ".sh")));
								writer.write("#!/bin/sh");}
								writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
								writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
										+ nom + "\n");
								writer.write("exit\n");
								writer.close();
								String desc = textArea_desc.getText(desc_1.length(),
										textArea_desc.getText().length() - desc_1.length());
								description = "Patient : "
										+ folders.get(j).getName() + "\n"
										+ "Acquisition date : " + date + "\n" + desc;
								if (comboBox_Op.getSelectedItem().equals("WINDOWS")&& comboBox_Arch.getSelectedItem().equals("X86_64"))
									CondorUtils.submitJob(dir, files, new File(dir
											+ File.separator + nom + ".bat"),
											Integer.parseInt(textField_NbCpu.getText()),
											Integer.parseInt(textField_NbMem.getText()),
											OS.WINDOWS, Arch.X86_64, description, machine);
								if (comboBox_Op.getSelectedItem().equals("WINDOWS") && comboBox_Arch.getSelectedItem().equals("INTEL"))
									CondorUtils.submitJob(dir, files, new File(dir
											+ File.separator + nom + ".bat"),
											Integer.parseInt(textField_NbCpu.getText()),
											Integer.parseInt(textField_NbMem.getText()),
											OS.WINDOWS, Arch.INTEL, description, machine);
								if (comboBox_Op.getSelectedItem().equals("UNIX")&& comboBox_Arch.getSelectedItem().equals("X86_64"))
									CondorUtils.submitJob(dir, files, new File(dir
											+ File.separator + nom + ".bat"),
											Integer.parseInt(textField_NbCpu.getText()),
											Integer.parseInt(textField_NbMem.getText()),
											OS.UNIX, Arch.X86_64, description, machine);
								if (comboBox_Op.getSelectedItem().equals("UNIX")&& comboBox_Arch.getSelectedItem().equals("INTEL"))
									CondorUtils.submitJob(dir, files, new File(dir
											+ File.separator + nom + ".bat"),
											Integer.parseInt(textField_NbCpu.getText()),
											Integer.parseInt(textField_NbMem.getText()),
											OS.UNIX, Arch.INTEL, description, machine);
							}
							else if(chckbx_Local.isSelected()){
								CondorUtils.submitJobLocal(dir,files,textField_ExeMatlab.getText());
							}
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
											+ "batch.m"));
							BufferedWriter writer = new BufferedWriter(
									new FileWriter(new File(dir + File.separator
											+ nom + ".m")));
							String line;
							while ((line = in.readLine()) != null) {
								if (chckbx_Reor.isSelected())
									line = line.replace("#1#", "0");
								else if (chckbx_Reor2.isSelected())
									line = line.replace("#1#", "2");
								else
									line = line.replace("#1#", "1");

								line = line.replace("#2#", textField_Reor.getText());
								if (chckbx_Presub.isSelected())
									line = line.replace("#3#", "0");
								else if (chckbx_Presub2.isSelected())
									line = line.replace("#3#", "2");
								else
									line = line.replace("#3#", "1");
								if (textField_Presub.getText().equals(txt))
									line = line.replace("#17#", "1");
								else {
									line = line.replace("#4#", textField_Presub.getText());
									line = line.replace("#17#", "0");
								}
								line = line.replace("#5#", textField_NSlice.getText());
								line = line.replace("#6#", textField_Tr.getText());
								line = line.replace("#7#", textField_Ta.getText());
								line = line.replace("#8#", textField_So.getText());
								line = line.replace("#9#", textField_RefSlice.getText());
								line = line.replace("#10#", textField_Fwhm.getText());
								line = line.replace("#11#", textField_NormTempl.getText());
								line = line.replace("#12#", textField_Vox.getText());
								line = line.replace("#13#", textField_Interp.getText());
								if (chckbx_NormTempl.isSelected())
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
								if(textField_RmImage.getText().isEmpty())
									line = line.replace("#21#", "1");
								else 
									line = line.replace("#21#", "0");
								line = line.replace("#22#", textField_RmImage.getText());
								writer.write(line + "\n");
							}
							in.close();
							writer.close();
							ArrayList<File> files = new ArrayList<>();
							files.add((new File(dir + File.separator + nom + ".m")));
							if(!textField_Reor.getText().isEmpty())
								files.add((new File(textField_Reor.getText())));
							//files.add((new File(dir + File.separator + nom + ".bat")));
							if(!textField_Presub.getText().equals("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m"))
								files.add((new File(textField_Presub.getText())));
							if(!textField_NormTempl.getText().isEmpty())
								files.add((new File(textField_NormTempl.getText())));
							if(chckbx_Condor.isSelected()){
								if(comboBox_Op.getSelectedItem().equals("WINDOWS"))
								{writer = new BufferedWriter(new FileWriter(new File(dir
										+ File.separator + nom + ".bat")));}
								else
								{writer = new BufferedWriter(new FileWriter(new File(dir
										+ File.separator + nom + ".sh")));
								writer.write("#!/bin/sh");}
								writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
								writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
										+ nom + "\n");
								writer.write("exit\n");
								writer.close();
								String desc = textArea_desc.getText(desc_1.length(),
										textArea_desc.getText().length() - desc_1.length());
								description = "Patient : "
										+ folders.get(j).getName() + "\n"
										+ "Acquisition date : " + date + "\n" + desc;
								if (comboBox_Op.getSelectedItem().equals("WINDOWS")&& comboBox_Arch.getSelectedItem().equals("X86_64"))
									CondorUtils.submitJob(dir, files, new File(dir
											+ File.separator + nom + ".bat"),
											Integer.parseInt(textField_NbCpu.getText()),
											Integer.parseInt(textField_NbMem.getText()),
											OS.WINDOWS, Arch.X86_64, description, machine);
								if (comboBox_Op.getSelectedItem().equals("WINDOWS") && comboBox_Arch.getSelectedItem().equals("INTEL"))
									CondorUtils.submitJob(dir, files, new File(dir
											+ File.separator + nom + ".bat"),
											Integer.parseInt(textField_NbCpu.getText()),
											Integer.parseInt(textField_NbMem.getText()),
											OS.WINDOWS, Arch.INTEL, description, machine);
								if (comboBox_Op.getSelectedItem().equals("UNIX")&& comboBox_Arch.getSelectedItem().equals("X86_64"))
									CondorUtils.submitJob(dir, files, new File(dir
											+ File.separator + nom + ".bat"),
											Integer.parseInt(textField_NbCpu.getText()),
											Integer.parseInt(textField_NbMem.getText()),
											OS.UNIX, Arch.X86_64, description, machine);
								if (comboBox_Op.getSelectedItem().equals("UNIX")&& comboBox_Arch.getSelectedItem().equals("INTEL"))
									CondorUtils.submitJob(dir, files, new File(dir
											+ File.separator + nom + ".bat"),
											Integer.parseInt(textField_NbCpu.getText()),
											Integer.parseInt(textField_NbMem.getText()),
											OS.UNIX, Arch.INTEL, description, machine);
							}
							else if(chckbx_Local.isSelected()){
								CondorUtils.submitJobLocal(dir,files,textField_ExeMatlab.getText());
							}
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
				if(comboBox_Filter.getSelectedItem().equals("Patient")){
					dossier_filtre.clear();
					dossier_filtre.add(folders.get(j).getName());
				}
				else if(comboBox_Filter.getSelectedItem().equals("Protocol")){
					dossier_filtre=findFiles(folders.get(j).toString(),0);
				}
				else if(comboBox_Filter.getSelectedItem().equals("Serie") && structure.equals(FolderStructure.PatProtSer)){
					path_ss_dossier=findFiles2(folders.get(j).toString(),0);
					dossier_filtre.clear();
					for (int k = 0; k < path_ss_dossier.size(); k++) {
						dossier_filtre.addAll(findFiles(path_ss_dossier.get(k).toString(),0));
					}
				}
				else if(comboBox_Filter.getSelectedItem().equals("Serie") && structure.equals(FolderStructure.PatSer)){
					dossier_filtre=findFiles(folders.get(j).toString(),0);
				}
				filt=false;
				for (int k = 0; k < dossier_filtre.size(); k++) {

					if(dossier_filtre.get(k).matches("(.*)"+textField_filter.getText()+"(.*)"))
					{
						filt=true;
					}
				}
				if(textField_filter.getText().isEmpty()) {
					time = System.nanoTime();
					nom = "job_" + time.toString();
					try {
						BufferedReader in = new BufferedReader(new FileReader(
								SystemSettings.APP_DIR + File.separator + "lib"
										+ File.separator + "MATLAB"
										+ File.separator + "batch.m"));
						BufferedWriter writer = new BufferedWriter(new FileWriter(
								new File(dir + File.separator + nom + ".m")));
						String line;
						while ((line = in.readLine()) != null) {
							if (chckbx_Reor.isSelected())
								line = line.replace("#1#", "0");
							else if (chckbx_Reor2.isSelected())
								line = line.replace("#1#", "2");
							else
								line = line.replace("#1#", "1");
							line = line.replace("#2#", textField_Reor.getText());
							if (chckbx_Presub.isSelected())
								line = line.replace("#3#", "0");
							else if (chckbx_Presub2.isSelected())
								line = line.replace("#3#", "2");
							else
								line = line.replace("#3#", "1");
							if (textField_Presub.getText().equals(txt))
								line = line.replace("#17#", "1");
							else {
								line = line.replace("#4#", textField_Presub.getText());
								line = line.replace("#17#", "0");
							}
							line = line.replace("#5#", textField_NSlice.getText());
							line = line.replace("#6#", textField_Tr.getText());
							line = line.replace("#7#", textField_Ta.getText());
							line = line.replace("#8#", textField_So.getText());
							line = line.replace("#9#", textField_RefSlice.getText());
							line = line.replace("#10#", textField_Fwhm.getText());
							line = line.replace("#11#", textField_NormTempl.getText());
							line = line.replace("#12#", textField_Vox.getText());
							line = line.replace("#13#", textField_Interp.getText());
							if (chckbx_NormTempl.isSelected())
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
							if(textField_RmImage.getText().isEmpty())
								line = line.replace("#21#", "1");
							else 
								line = line.replace("#21#", "0");
							line = line.replace("#22#", textField_RmImage.getText());
							writer.write(line + "\n");
						}
						in.close();
						writer.close();
						ArrayList<File> files = new ArrayList<>();
						files.add((new File(dir + File.separator + nom + ".m")));
						if(!textField_Reor.getText().isEmpty())
							files.add((new File(textField_Reor.getText())));
						if(!textField_Presub.getText().equals("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m"))
							files.add((new File(textField_Presub.getText())));
						if(!textField_NormTempl.getText().isEmpty())
							files.add((new File(textField_NormTempl.getText())));
						if(chckbx_Condor.isSelected()){
							if(comboBox_Op.getSelectedItem().equals("WINDOWS"))
							{writer = new BufferedWriter(new FileWriter(new File(dir
									+ File.separator + nom + ".bat")));}
							else
							{writer = new BufferedWriter(new FileWriter(new File(dir
									+ File.separator + nom + ".sh")));
							writer.write("#!/bin/sh");}
							writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
							writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
									+ nom + "\n");
							writer.write("exit\n");
							writer.close();
							String desc = textArea_desc.getText(desc_2.length(), textArea_desc
									.getText().length() - desc_2.length());
							description = "Patient : "
									+ folders.get(j).getName() + "\n" + desc;
							if (comboBox_Op.getSelectedItem().equals("WINDOWS")&& comboBox_Arch.getSelectedItem().equals("X86_64"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_NbCpu.getText()),
										Integer.parseInt(textField_NbMem.getText()),
										OS.WINDOWS, Arch.X86_64, description, machine);
							if (comboBox_Op.getSelectedItem().equals("WINDOWS") && comboBox_Arch.getSelectedItem().equals("INTEL"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_NbCpu.getText()),
										Integer.parseInt(textField_NbMem.getText()),
										OS.WINDOWS, Arch.INTEL, description, machine);
							if (comboBox_Op.getSelectedItem().equals("UNIX")&& comboBox_Arch.getSelectedItem().equals("X86_64"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_NbCpu.getText()),
										Integer.parseInt(textField_NbMem.getText()),
										OS.UNIX, Arch.X86_64, description, machine);
							if (comboBox_Op.getSelectedItem().equals("UNIX")&& comboBox_Arch.getSelectedItem().equals("INTEL"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_NbCpu.getText()),
										Integer.parseInt(textField_NbMem.getText()),
										OS.UNIX, Arch.INTEL, description, machine);
						}
						else if(chckbx_Local.isSelected()){
							CondorUtils.submitJobLocal(dir,files,textField_ExeMatlab.getText());
						}
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
										+ File.separator + "batch.m"));
						BufferedWriter writer = new BufferedWriter(new FileWriter(
								new File(dir + File.separator + nom + ".m")));
						String line;
						while ((line = in.readLine()) != null) {
							if (chckbx_Reor.isSelected())
								line = line.replace("#1#", "0");
							else if (chckbx_Reor2.isSelected())
								line = line.replace("#1#", "2");
							else
								line = line.replace("#1#", "1");
							line = line.replace("#2#", textField_Reor.getText());
							if (chckbx_Presub.isSelected())
								line = line.replace("#3#", "0");
							else if (chckbx_Presub2.isSelected())
								line = line.replace("#3#", "2");
							else
								line = line.replace("#3#", "1");
							if (textField_Presub.getText().equals(txt))
								line = line.replace("#17#", "1");
							else {
								line = line.replace("#4#", textField_Presub.getText());
								line = line.replace("#17#", "0");
							}
							line = line.replace("#5#", textField_NSlice.getText());
							line = line.replace("#6#", textField_Tr.getText());
							line = line.replace("#7#", textField_Ta.getText());
							line = line.replace("#8#", textField_So.getText());
							line = line.replace("#9#", textField_RefSlice.getText());
							line = line.replace("#10#", textField_Fwhm.getText());
							line = line.replace("#11#", textField_NormTempl.getText());
							line = line.replace("#12#", textField_Vox.getText());
							line = line.replace("#13#", textField_Interp.getText());
							if (chckbx_NormTempl.isSelected())
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
							if(textField_RmImage.getText().isEmpty())
								line = line.replace("#21#", "1");
							else 
								line = line.replace("#21#", "0");
							line = line.replace("#22#", textField_RmImage.getText());
							writer.write(line + "\n");
						}
						in.close();
						writer.close();
						ArrayList<File> files = new ArrayList<>();
						files.add((new File(dir + File.separator + nom + ".m")));
						if(!textField_Reor.getText().isEmpty())
							files.add((new File(textField_Reor.getText())));
						if(!textField_Presub.getText().equals("(matlabroot)\\toolbox\\FieldMap\\pm_defaults_skyra.m"))
							files.add((new File(textField_Presub.getText())));
						if(!textField_NormTempl.getText().isEmpty())
							files.add((new File(textField_NormTempl.getText())));
						if(chckbx_Condor.isSelected()){
							if(comboBox_Op.getSelectedItem().equals("WINDOWS"))
							{writer = new BufferedWriter(new FileWriter(new File(dir
									+ File.separator + nom + ".bat")));}
							else
							{writer = new BufferedWriter(new FileWriter(new File(dir
									+ File.separator + nom + ".sh")));
							writer.write("#!/bin/sh");}
							writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
							writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
									+ nom + "\n");
							writer.write("exit\n");
							writer.close();
							String desc = textArea_desc.getText(desc_2.length(), textArea_desc
									.getText().length() - desc_2.length());
							description = "Patient : "
									+ folders.get(j).getName() + "\n" + desc;
							if (comboBox_Op.getSelectedItem().equals("WINDOWS")&& comboBox_Arch.getSelectedItem().equals("X86_64"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_NbCpu.getText()),
										Integer.parseInt(textField_NbMem.getText()),
										OS.WINDOWS, Arch.X86_64, description, machine);
							if (comboBox_Op.getSelectedItem().equals("WINDOWS") && comboBox_Arch.getSelectedItem().equals("INTEL"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_NbCpu.getText()),
										Integer.parseInt(textField_NbMem.getText()),
										OS.WINDOWS, Arch.INTEL, description, machine);
							if (comboBox_Op.getSelectedItem().equals("UNIX")&& comboBox_Arch.getSelectedItem().equals("X86_64"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_NbCpu.getText()),
										Integer.parseInt(textField_NbMem.getText()),
										OS.UNIX, Arch.X86_64, description, machine);
							if (comboBox_Op.getSelectedItem().equals("UNIX")&& comboBox_Arch.getSelectedItem().equals("INTEL"))
								CondorUtils.submitJob(dir, files, new File(dir
										+ File.separator + nom + ".bat"),
										Integer.parseInt(textField_NbCpu.getText()),
										Integer.parseInt(textField_NbMem.getText()),
										OS.UNIX, Arch.INTEL, description, machine);
						}
						else if(chckbx_Local.isSelected()){
							CondorUtils.submitJobLocal(dir,files,textField_ExeMatlab.getText());
						}
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
		public boolean verify(Component component) {
			String text = null;

			if (component instanceof JTextField) {
				text = ((JTextField) component).getText();
			}
			try {
				Integer.parseInt(text);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}

		public boolean verify2(Component component) {
			String text = null;

			if (component instanceof JTextField) {
				text = ((JTextField) component).getText();
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

	public static ArrayList<String> findFiles(String directoryPath, int n) {
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
				if(n==0){
					if (subfiles[i].isDirectory()) {
						subdir.add(subfiles[i].getName());
						//System.out.println(subfiles[i].getName());
					}
				}
				else {
					subdir.add(subfiles[i].getName());
				}
			}
		}
		return subdir;
	}
	public ArrayList<String> findFiles2(String directoryPath, int n) {
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
				if(n==0){
					if (subfiles[i].isDirectory()) {
						subdir.add(subfiles[i].getAbsolutePath());
						//System.out.println(subfiles[i].getName());
					}
				}
				else {
					subdir.add(subfiles[i].getAbsolutePath());
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

		textField_So.setText(res);
		textField_RefSlice.setText(res2);
	}
	private void Ta(int nb,float tr)
	{
		float res;
		res=tr-tr/nb;
		textField_Ta.setText(String.format("%.3f", res));
	}
	/*private void RemoveImage(ArrayList<File> folders, FolderStructure structure){
		ArrayList<String> subdir = new ArrayList<String>();
		ArrayList<String> subdir1 = new ArrayList<String>();
		ArrayList<String> subdir2 = new ArrayList<String>();
		ArrayList<String> subdir3 = new ArrayList<String>();
		if(structure.equals(FolderStructure.PatDatProtSer)) {
			for(int i=0;i<folders.size();i++)
			{
				//subdir.clear();
				subdir.addAll(findFiles2(folders.get(i).toString(),0));
				//System.out.println(folders.get(i).toString());
				//System.out.println("1 : "+subdir);
				for(int j=0;j<subdir.size();j++)
				{
					//subdir1.clear();
					subdir1.addAll(findFiles2(subdir.get(j).toString(),0));
					//System.out.println("2 : "+subdir1);
				}
			}
			System.out.println("1 : "+subdir);
			System.out.println(subdir.size());
			System.out.println("2 : "+subdir1);
			System.out.println(subdir1.size());
		}
		else if(structure.equals(FolderStructure.PatDatSer)) {

		}
		else if(structure.equals(FolderStructure.PatProtSer)) {

		}
		else if(structure.equals(FolderStructure.PatSer)) {

		}
	}*/
	private boolean VerifyJText(){
		ArrayList<String> error =new ArrayList<String>();
		MyVerifier verifier = new MyVerifier();
		String errorText="";
		for(int i=0;i<panel_Option.getComponentCount();i++){
			if (panel_Option.getComponent(i) instanceof JTextField) {
				String[] NS=panel_Option.getComponent(i).getName().split("_");
				if(NS[0].equals("S")){
					if(!verifier.verify2(panel_Option.getComponent(i))){
						if(panel_Option.getComponent(i).getName().equals("S_Filter") && !comboBox_Filter.getSelectedItem().equals(""))
							error.add("Filter is empty");
						if(panel_Option.getComponent(i).getName().equals("S_Reor") && chckbx_Reor.isSelected())
							error.add("The anterior commissure path is empty");
						if(panel_Option.getComponent(i).getName().equals("S_Presub") && chckbx_Presub.isSelected())
							error.add("The pm_defaults path is empty");
					}
				}
			}
		}
		for(int i=0;i<panel_SliceTiming.getComponentCount();i++){
			if (panel_SliceTiming.getComponent(i) instanceof JTextField) {
				String[] NS=panel_SliceTiming.getComponent(i).getName().split("_");
				if(NS[0].equals("N")){
					if(!verifier.verify(panel_SliceTiming.getComponent(i))){
						if(panel_SliceTiming.getComponent(i).getName().equals("N_nSlices"))
							error.add("NSlices is empty or not valid");
						if(panel_SliceTiming.getComponent(i).getName().equals("N_RefSlice"))
							error.add("RefSlice is empty or not valid");
					}
				}
				else{
					if(!verifier.verify2(panel_SliceTiming.getComponent(i))){
						if(panel_SliceTiming.getComponent(i).getName().equals("S_Tr"))
							error.add("Tr is empty or not valid");
						if(panel_SliceTiming.getComponent(i).getName().equals("S_Ta"))
							error.add("Ta is empty or not valid");
						if(panel_SliceTiming.getComponent(i).getName().equals("S_So"))
							error.add("So is empty or not valid");
					}
				}
			}
		}
		for(int i=0;i<panel_Norm.getComponentCount();i++){
			if (panel_Norm.getComponent(i) instanceof JTextField) {
				String[] NS=panel_Norm.getComponent(i).getName().split("_");
				if(NS[0].equals("N")){
					if(!verifier.verify(panel_Norm.getComponent(i))){
						if(panel_Norm.getComponent(i).getName().equals("N_Interp"))
							error.add("Interp is empty or not valid");
					}
				}
				else{
					if(!verifier.verify2(panel_Norm.getComponent(i))){
						if(panel_Norm.getComponent(i).getName().equals("S_NormTempl") && chckbx_NormTempl.isSelected())
							error.add("The normalisation template path is empty");
						if(panel_Norm.getComponent(i).getName().equals("S_Vox"))
							error.add("Vox is empty or not valid");
					}
				}
			}
		}
		for(int i=0;i<panel_Smooth.getComponentCount();i++){
			if (panel_Smooth.getComponent(i) instanceof JTextField) {
				if(!verifier.verify2(panel_Smooth.getComponent(i))){
					if(panel_Smooth.getComponent(i).getName().equals("S_Fwhm"))
						error.add("Fwhm is empty or not valid");
				}
			}
		}
		if(chckbx_Condor.isSelected()){
			for(int i=0;i<panel_ParamCondor.getComponentCount();i++){
				if (panel_ParamCondor.getComponent(i) instanceof JTextField) {
					String[] NS=panel_ParamCondor.getComponent(i).getName().split("_");
					if(NS[0].equals("N")){
						if(!verifier.verify(panel_ParamCondor.getComponent(i))){
							if(panel_ParamCondor.getComponent(i).getName().equals("N_NbCpu"))
								error.add("The number of CPUs is empty or not valid");
							if(panel_ParamCondor.getComponent(i).getName().equals("N_NbMem"))
								error.add("The number of memory is empty or not valid");
						}
					}
					else{
						if(!verifier.verify2(panel_ParamCondor.getComponent(i))){
							error.add("The jobs path is empty");
						}
					}
				}
			}
		}
		else if(chckbx_Local.isSelected()){
			for(int i=0;i<panel_ParamLocal.getComponentCount();i++){
				if (panel_ParamLocal.getComponent(i) instanceof JTextField) {
					if(!verifier.verify2(panel_ParamLocal.getComponent(i))){
						error.add("The Matlab excutable path is empty");
					}
				}
			}
		}
		for(int i=0;i<error.size();i++){
			errorText = errorText+"\n"+error.get(i);
			System.out.println(errorText);
		}
		if(!error.isEmpty()){
			JOptionPane.showMessageDialog(frame,errorText,"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else
			return true;
	}
}
