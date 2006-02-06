package searchInterface;

import java.io.IOException;
import java.io.*;

class LettoreBufferThread implements Runnable {
	Thread t;

	private boolean exitflag = false;

	private boolean stopped = false; // Diventa vero quando il thread ha finito di

	// leggere il buffer

	private InputStream lt_outputSwishe = null;
	private InputStreamReader isr_outputSwishe = null;

	// Creo uno StringBuffer
	private StringBuffer bufferOutputSwishe = new StringBuffer();

	private Process lt_myProcess = null;

	LettoreBufferThread(Process myProcess, InputStream outputSwishe) {
		t = new Thread(this, "Lettore output di swish-e");
		// InputStream che verrà letto dal thread principale
		lt_outputSwishe = outputSwishe;
		// Processo swish-e
		lt_myProcess = myProcess;
		// Ottengo il flusso di output di swish-e
		lt_outputSwishe = lt_myProcess.getInputStream();
		//An InputStreamReader is a bridge from byte streams to character streams:
		//  It reads bytes and decodes them into characters using a specified charset. 
		//Istanzion un lettore per l'oggetto InputStream
		isr_outputSwishe=new InputStreamReader(outputSwishe);
		t.start();
	}

	public void run() {
		int icaratteriDisponibili = 99;// Valore fittizio maggiore di zero
		while (!exitflag || (icaratteriDisponibili > 0)) {
			try {
				int val = 0;
				val = isr_outputSwishe.read();
				bufferOutputSwishe.append((char) val);
				icaratteriDisponibili = lt_outputSwishe.available();
			} catch (IOException excp) {
				System.out.print("\n" + excp.getLocalizedMessage());
			}
			try {
				icaratteriDisponibili = lt_outputSwishe.available();
			} catch (IOException excp) {
				System.out.print("\n" + excp.getLocalizedMessage());
			}
		}
		stopped = true;
	}

	public void stop() {
		exitflag = true;
	}

	public StringBuffer getStreamSwishe() {
		return bufferOutputSwishe;
	}

	// True se ha finito, false se ancora lavora
	public boolean done() {
		if (stopped)
			return true;
		else
			return false;
	}
}