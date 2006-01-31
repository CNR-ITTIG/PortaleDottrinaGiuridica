package searchInterface;

import java.io.IOException;
import java.io.InputStream;

class LettoreBufferThread implements Runnable {
	Thread t;

	boolean exitflag = false;

	boolean stopped = false; // Diventa vero quando il thread ha finito di

	// leggere il buffer

	private InputStream lt_outputSwishe = null;

	// Creo uno StringBuffer
	StringBuffer bufferOutputSwishe = new StringBuffer();

	Process lt_myProcess = null;

	LettoreBufferThread(Process myProcess, InputStream outputSwishe) {
		t = new Thread(this, "Lettore output di swish-e");
		// InputStream che verrà letto dal thread principale
		lt_outputSwishe = outputSwishe;
		// Processo swish-e
		lt_myProcess = myProcess;
		// Ottengo il flusso di output di swish-e
		lt_outputSwishe = lt_myProcess.getInputStream();
		t.start();
	}

	public void run() {
		int icaratteriDisponibili = 99;// Valore fittizio maggiore di zero
		while (!exitflag || (icaratteriDisponibili > 0)) {
			try {
				int val = 0;
				val = lt_outputSwishe.read();
				//TODO: traduzione caratteri
				bufferOutputSwishe.append((char) val);
				icaratteriDisponibili = lt_outputSwishe.available();
				
//				TODO: Leggere i caratteri accentati correttamente
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