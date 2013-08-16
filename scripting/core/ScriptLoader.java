package scripting.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import scripting.ScriptingMod;
import scripting.core.script.BasicScript;
import scripting.core.script.JSScript;
import scripting.core.script.FilterScript;
import scripting.utils.Utils;

public class ScriptLoader {

	private static final FileFilter CLIENT_FILTER = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isFile() && file.getName().endsWith(".js");
		}
	};
	
	private static final FileFilter SERVER_FILTER = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isFile() && (file.getName().endsWith(".js") || file.getName().endsWith(".filter"));
		}
	};

	public static Map<String, JSScript> loadAllScripts(File dir, boolean client) {
		Map<String, JSScript> scripts = new HashMap<String, JSScript>();
		if (!dir.exists())
			dir.mkdirs();
		else {
			for (File f : dir.listFiles(client ? CLIENT_FILTER : SERVER_FILTER)) {
				if (f.canRead()) {
					try {
						String name = f.getName();
						String source = readScript(f);
						if (name.endsWith(".filter") && name.contains(" ")) {
							System.err.println("[SCRIPTS] Illegal filter name \"" + name + "\"");
							continue;
						}
						scripts.put(name, (name.endsWith(".filter")) ? new FilterScript(name, source) : new BasicScript(name, source));
						System.out.println("[SCRIPTS] Succesfully read " + getSimpleName(f, client));
					}
					catch(IOException e) {
						System.err.println("[SCRIPTS] Error reading " + getSimpleName(f, client));
						e.printStackTrace();
					}
				}
				else
					System.err.println("[SCRIPTS] Skipping " + getSimpleName(f, client));
			}
		}
		return scripts;
	}
	
	private static String getSimpleName(File script, boolean client) {
		return (client) ? "client script \"" + script.getName() + "\"": "server script \"" + script.getName() + "\"";
	}

	private static String readScript(File f) throws IOException {
		String source = "";
		String line = "";
		FileReader in = null;
		BufferedReader reader = null;
		try {
			in = new FileReader(f);
			reader = new BufferedReader(in);
			while((line = reader.readLine()) != null)
				source += line + '\n';
			return source;
		}
		finally {
			Utils.closeSilently(reader);
			Utils.closeSilently(in);
		}
	}
}
