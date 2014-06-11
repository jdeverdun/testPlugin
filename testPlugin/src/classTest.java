import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import plugins.FolderProcessingPlugins;
import plugins.FolderProcessingPlugins.FolderStructure;
import settings.SystemSettings;
import settings.WindowManager;
import tools.cluster.condor.CondorUtils;
import tools.cluster.condor.CondorUtils.Arch;
import tools.cluster.condor.CondorUtils.OS;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import display.MainWindow;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class classTest implements FolderProcessingPlugins {

	private JFrame frame;
	private String title="General Batch";
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField textField_batch;
	private JTextField textField_pathJob;
	private JTextField textField_nbCpu;
	private JTextField textField_nbMem;
	private JLabel label_Op;
	private JLabel label_batch;
	private JLabel lblFilesToTransfert;
	private JLabel label_pathJob;
	private JLabel label_nbCpu;
	private JLabel label_nbMem;
	private JLabel label_Arch;
	private JLabel label_desc;
	private JComboBox<String> comboBox_op;
	private JComboBox<String> comboBox_arch;
	private JScrollPane scrollPane;
	private JTextArea textArea_desc;
	private JButton btnSelect;
	private JButton btnSelect_1;
	private JButton btn_OK;
	private JButton btn_close;
	private JButton btn_cancel;
	private ImageIcon icon;
	private Image img;
	private Image newimg;
	private ImageIcon icon2;
	private String desc_1 = "Patient : \"name\" \nAcquisition Date : \"date\"\n";
	private String desc_2 = "Patient : \"name\" \n";
	private JProgressBar progressBar;
	private JScrollPane scrollPane_1;
	private JList<File> list;
	private JButton btn_Add;
	private JButton btn_Delete;
	private DefaultListModel<File> model;

	@Override
	public PluginCategory getCategory() {
		return PluginCategory.BatchImageProcessing;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "General Batch";
	}

	@Override
	public String actionOnFolders(ArrayList<File> folders, FolderStructure structure) {
		createAndShowGUI(folders, structure);
		return null;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createAndShowGUI(final ArrayList<File> folders,
			final FolderStructure structure) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setTitle(title);
		frame.setLocationRelativeTo(null);// (WindowManager.MAINWINDOW.getLocation());
		frame.setVisible(true);
		frame.setIconImage(new ImageIcon(this.getClass().getResource(
				"/images/logo32.png")).getImage());
		frame.getContentPane().setLayout(new MigLayout("", "[][172.00,grow][][grow][]", "[][43.00,grow][grow][grow]"));

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_2, "cell 1 1 3 1,grow");
		panel_2.setLayout(new MigLayout("", "[200][][105,grow][grow][]", "[][grow][]"));

		label_batch = new JLabel("Choose your matlab batch");
		panel_2.add(label_batch, "cell 0 0");

		textField_batch = new JTextField();
		panel_2.add(textField_batch, "cell 2 0 2 1,growx");
		textField_batch.setColumns(10);

		lblFilesToTransfert = new JLabel("Files to transfert");
		panel_2.add(lblFilesToTransfert, "cell 0 1");

		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Parameters of the submit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel, "cell 1 2 3 1,grow");
		panel.setLayout(new MigLayout("", "[200][][172.00][grow][]", "[][][][][-7.00][93.00]"));

		label_pathJob = new JLabel("Path where are created jobs files");
		panel.add(label_pathJob, "cell 0 0");

		textField_pathJob = new JTextField(SystemSettings.SERVER_INFO.getCondorJobDir().toString());
		textField_pathJob.setColumns(10);
		panel.add(textField_pathJob, "cell 2 0 2 1,growx");



		label_nbCpu = new JLabel("Number of cpus");
		panel.add(label_nbCpu, "cell 0 1");

		textField_nbCpu = new JTextField();
		textField_nbCpu.setText("2");
		textField_nbCpu.setColumns(10);
		panel.add(textField_nbCpu, "cell 2 1");

		label_nbMem = new JLabel("Number of memory");
		panel.add(label_nbMem, "cell 0 2");

		textField_nbMem = new JTextField();
		textField_nbMem.setText("3000");
		textField_nbMem.setColumns(10);
		panel.add(textField_nbMem, "cell 2 2");

		label_Op = new JLabel("Choose the operating system");
		panel.add(label_Op, "cell 0 3");

		comboBox_op = new JComboBox<String>();
		comboBox_op.removeAllItems();
		comboBox_op.addItem("WINDOWS");
		comboBox_op.addItem("UNIX");
		panel.add(comboBox_op, "cell 2 3");

		label_Arch = new JLabel("Choose the architecture");
		panel.add(label_Arch, "cell 0 4");

		comboBox_arch = new JComboBox<String>();
		comboBox_arch.removeAllItems();
		comboBox_arch.addItem("X86_64");
		comboBox_arch.addItem("INTEL");
		panel.add(comboBox_arch, "cell 2 4");

		label_desc = new JLabel("Description");
		panel.add(label_desc, "cell 0 5");

		scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 2 5,grow");

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

		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, "cell 1 3 3 1,grow");
		panel_1.setLayout(new MigLayout("", "[grow][grow]", "[]"));

		icon = new ImageIcon(MainWindow.class.getResource("/images/folder.png"));
		img = icon.getImage();
		newimg = img.getScaledInstance(20, 20,java.awt.Image.SCALE_SMOOTH);
		icon2 = new ImageIcon(newimg);

		btnSelect = new JButton(icon2);
		panel_2.add(btnSelect, "cell 4 0");
		
		scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, "cell 2 1 2 2,grow");
		
		model = new DefaultListModel<File>();
		list = new JList<File>();
		list.setModel(model);
		scrollPane_1.setViewportView(list);
		
		btn_Add = new JButton("Add");
		panel_2.add(btn_Add, "cell 4 1,growx");
		
		btn_Delete = new JButton("Delete");
		panel_2.add(btn_Delete, "cell 4 2,growx");

		btnSelect_1 = new JButton(icon2);
		panel.add(btnSelect_1, "cell 4 0");

		btn_OK = new JButton("OK");
		panel_1.add(btn_OK, "cell 0 0,growx");

		btn_close = new JButton("Close");
		panel_1.add(btn_close, "cell 1 0,growx");

		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					textField_batch.setText(file.getAbsolutePath());
				}
			}
		});
		btnSelect_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					textField_pathJob.setText(file.getAbsolutePath());
				}
			}
		});
		btn_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int retval = fc.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					model.addElement(file);
				}
			}
		});
		btn_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] a= list.getSelectedIndices();
				for(int i=a.length-1;i>=0;i--)
					{
					model.removeElementAt(a[i]);
					a= list.getSelectedIndices();
					}
			}
		});
		final JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setTitle(title);
		f.setSize(300, 90);
		f.setLocationRelativeTo(null);// (WindowManager.MAINWINDOW.getLocation());
		f.setIconImage(new ImageIcon(this.getClass().getResource(
				"/images/logo32.png")).getImage());
		f.getContentPane().setLayout(new MigLayout("", "[grow]", "[][10][]"));
		
		JLabel lblNewLabel = new JLabel("Wait while creating jobs :");
		f.getContentPane().add(lblNewLabel, "cell 0 0");
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		f.getContentPane().add(progressBar, "cell 0 2,growx");
		
		btn_cancel = new JButton("Cancel");
		f.getContentPane().add(btn_cancel, "cell 0 2,alignx center");
		
		btn_OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				final Thread tr = new Thread(new Runnable() {

					@Override
					public void run() {
						try{
							f.setVisible(true);
							createMatlabAndBashFiles(folders, structure);
							f.dispose();
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
		frame.getRootPane().setDefaultButton(btn_OK);
		btn_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});	
	}

	public void createMatlabAndBashFiles(ArrayList<File> folders, FolderStructure structure) {
		ArrayList<String> subdir = new ArrayList<String>();
		File dir = new File(textField_pathJob.getText());
		Long time;
		String nom="";
		String machine ="";
		if (structure.equals(FolderStructure.PatDatSer) || structure.equals(FolderStructure.PatDatProtSer)) {
			for (int j = 0; j < folders.size(); j++) {
				subdir=CopyOfrestingState.findFiles(folders.get(j).toString());
				for (int i = 0; i < subdir.size(); i++) {	
					time = System.nanoTime();
					nom = "job_" + time.toString();
					try {
						BufferedReader in = new BufferedReader(new FileReader(textField_batch.getText()));
						BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir + File.separator+ nom + ".m")));
						String line;
						while ((line = in.readLine()) != null) {
							line = line.replace("#15#", folders.get(j).toString()+ File.separator+ subdir.get(i).toString());
							line = line.replace("#16#", folders.get(j).getName());
							line = line.replace("#18#", "0");
							line = line.replace("#19#", subdir.get(i).toString());
							if (structure.equals(FolderStructure.PatDatProtSer))
								line = line.replace("#20#", "0");
							else
								line = line.replace("#20#", "1");
							writer.write(line + "\n");
						}
						in.close();
						writer.close();
						if(comboBox_op.getSelectedItem().equals("WINDOWS")){
							writer = new BufferedWriter(new FileWriter(new File(dir+ File.separator + nom + ".bat")));
						}
						else{
							writer = new BufferedWriter(new FileWriter(new File(dir+ File.separator + nom + ".sh")));
							writer.write("#!/bin/sh");
						}
						writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
						writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
								+ nom + "\n");
						writer.write("exit\n");
						writer.close();
						String desc = textArea_desc.getText(desc_1.length(),textArea_desc.getText().length() - desc_1.length());
						String date = subdir.get(i).toString();
						date = date.substring(0, 4) + "/"+ date.substring(4, 6) + "/"+ date.substring(6, 8);
						String description = "Patient : "+ folders.get(j).getName() + "\n"+ "Acquisition date : " + date + "\n" + desc;
						
						ArrayList<File> files = new ArrayList<>();
						files.add((new File(dir + File.separator + nom + ".m")));
						for (int k=0;k<model.getSize();k++)
							files.add(model.getElementAt(k));
						if (comboBox_op.getSelectedItem().equals("WINDOWS") && comboBox_arch.getSelectedItem().equals("X86_64"))
							CondorUtils.submitJob(dir, files, new File(dir + File.separator + nom + ".bat"),Integer.parseInt(textField_nbCpu.getText()),Integer.parseInt(textField_nbMem.getText()),OS.WINDOWS, Arch.X86_64, description, machine);
						if (comboBox_op.getSelectedItem().equals("WINDOWS")&& comboBox_arch.getSelectedItem().equals("INTEL"))
							CondorUtils.submitJob(dir, files, new File(dir + File.separator + nom + ".bat"),Integer.parseInt(textField_nbCpu.getText()),Integer.parseInt(textField_nbMem.getText()),OS.WINDOWS, Arch.INTEL, description, machine);
						if (comboBox_op.getSelectedItem().equals("UNIX")&& comboBox_arch.getSelectedItem().equals("X86_64"))
							CondorUtils.submitJob(dir, files, new File(dir + File.separator + nom + ".bat"),Integer.parseInt(textField_nbCpu.getText()),Integer.parseInt(textField_nbMem.getText()),OS.UNIX, Arch.X86_64, description, machine);
						if (comboBox_op.getSelectedItem().equals("UNIX")&& comboBox_arch.getSelectedItem().equals("INTEL"))
							CondorUtils.submitJob(dir, files, new File(dir + File.separator + nom + ".bat"),Integer.parseInt(textField_nbCpu.getText()),Integer.parseInt(textField_nbMem.getText()),OS.UNIX, Arch.INTEL, description, machine);
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
		else {
			for (int j = 0; j < folders.size(); j++) {
				time = System.nanoTime();
				nom  = "job_" + time.toString();
				try {
					BufferedReader in = new BufferedReader(new FileReader(textField_batch.getText()));
					BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir + File.separator + nom + ".m")));
					String line;
					while ((line = in.readLine()) != null) {
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
					if(comboBox_op.getSelectedItem().equals("WINDOWS")){
						writer = new BufferedWriter(new FileWriter(new File(dir+ File.separator + nom + ".bat")));
						}
					else{
						writer = new BufferedWriter(new FileWriter(new File(dir+ File.separator + nom + ".sh")));
						writer.write("#!/bin/sh");
						}
					writer.write("echo \"%1 %2 %3 %4 %5 %6 %7 %8 %9\"\n");
					writer.write("\"%1 %2 %3 %4 %5 %6 %7 %8 %9\" -logfile matlablog.log -nodesktop -nosplash -r "
							+ nom + "\n");
					writer.write("exit\n");
					writer.close();
					String desc = textArea_desc.getText(desc_2.length(), textArea_desc
							.getText().length() - desc_2.length());
					String description = "Patient : "
							+ folders.get(j).getName() + "\n" + desc;
					ArrayList<File> files = new ArrayList<>();
					files.add((new File(dir + File.separator + nom + ".m")));
					for (int k=0;k<model.getSize();k++)
						files.add(model.getElementAt(k));
					if (comboBox_op.getSelectedItem().equals("WINDOWS") && comboBox_arch.getSelectedItem().equals("X86_64"))
						CondorUtils.submitJob(dir, files, new File(dir + File.separator + nom + ".bat"),Integer.parseInt(textField_nbCpu.getText()),Integer.parseInt(textField_nbMem.getText()),OS.WINDOWS, Arch.X86_64, description, machine);
					if (comboBox_op.getSelectedItem().equals("WINDOWS")&& comboBox_arch.getSelectedItem().equals("INTEL"))
						CondorUtils.submitJob(dir, files, new File(dir + File.separator + nom + ".bat"),Integer.parseInt(textField_nbCpu.getText()),Integer.parseInt(textField_nbMem.getText()),OS.WINDOWS, Arch.INTEL, description, machine);
					if (comboBox_op.getSelectedItem().equals("UNIX")&& comboBox_arch.getSelectedItem().equals("X86_64"))
						CondorUtils.submitJob(dir, files, new File(dir + File.separator + nom + ".bat"),Integer.parseInt(textField_nbCpu.getText()),Integer.parseInt(textField_nbMem.getText()),OS.UNIX, Arch.X86_64, description, machine);
					if (comboBox_op.getSelectedItem().equals("UNIX")&& comboBox_arch.getSelectedItem().equals("INTEL"))
						CondorUtils.submitJob(dir, files, new File(dir + File.separator + nom + ".bat"),Integer.parseInt(textField_nbCpu.getText()),Integer.parseInt(textField_nbMem.getText()),OS.UNIX, Arch.INTEL, description, machine);
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

}