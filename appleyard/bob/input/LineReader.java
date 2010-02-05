package appleyard.bob.input;

import java.io.*;

public class LineReader {
	private Reader reader;
	
	public LineReader(Reader r) {
		reader = r;
	}
	
	public String readLine() throws IOException {
		int i;
		char c;
		String res = "";
		
		do {
			i = reader.read();
			if (i == -1) {
				return res;
			}
			c = (char) i;
			if (c == '\n') {
				return res;
			}
			res += c;		
		} while (true);	
	}
}
