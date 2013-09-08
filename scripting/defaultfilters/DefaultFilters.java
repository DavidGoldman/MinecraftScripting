package scripting.defaultfilters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import scripting.Config;
import scripting.utils.Utils;

public class DefaultFilters {
	
	public static final String[] FILTERS = { "AddAttribute", "AddPotionEffect", 
		"ChangeMobs", "ChangeSpawners", "CreateGearedMobs", "CreateShops", "CreateSpawners", "FillDispensers", 
		"RemoveBlocks", "RemoveEntities", "ReplaceBlocks", "StackEntities" };

	public static void init(File dir) {
		if (!Config.defaultFilters)
			return;
		dir.mkdirs();
		for (String filter : FILTERS) {
			filter += ".filter";
			File file = new File(dir, filter);
			if (!file.exists()) {
				try {
					writeScript(file, readScript(filter));
					System.out.println("[SCRIPTS] Successfully created default Filter \"" + filter + "\"");
				}
				catch(IOException e) {
					System.out.println("[SCRIPTS] Unable to create default Filter \"" + filter + "\"");
					e.printStackTrace();
				}
			}
		}
	}
	
	private static List<String> readScript(String name) throws IOException {
		List<String> source = new ArrayList<String>();
		String line = "";
		InputStream in = null;
		InputStreamReader is = null;
		BufferedReader reader = null;
		try {
			in = DefaultFilters.class.getResourceAsStream(name);
			is = new InputStreamReader(in, "UTF8");
			reader = new BufferedReader(is);
			while((line = reader.readLine()) != null)
				source.add(line);
			return source;
		}
		finally {
			Utils.closeSilently(reader);
			Utils.closeSilently(is);
			Utils.closeSilently(in);
		}
	}
	
	private static void writeScript(File script, List<String> source) throws IOException {
		FileOutputStream out = null;
		OutputStreamWriter os = null;
		BufferedWriter writer = null;
		try {
			out = new FileOutputStream(script);
			os = new OutputStreamWriter(out, "UTF8");
			writer = new BufferedWriter(os);
			for (String line : source) {
				writer.write(line);
				writer.newLine();
			}
		}
		finally {
			Utils.closeSilently(writer);
			Utils.closeSilently(os);
			Utils.closeSilently(out);
		}
	}
}
