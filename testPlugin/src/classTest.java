import java.io.File;
import java.util.ArrayList;

import plugins.FolderProcessingPlugins;


public class classTest implements FolderProcessingPlugins {
 


	@Override
	public PluginCategory getCategory() {
		return PluginCategory.BatchImageProcessing;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "plugin de test";
	}

	@Override
	public String actionOnFolders(ArrayList<File> folders, FolderStructure structure) {
		for(File fi : folders)
			System.out.println(fi.getAbsolutePath());
		return null;
	}
 
}